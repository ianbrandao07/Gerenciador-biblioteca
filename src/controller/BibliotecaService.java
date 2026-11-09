package controller;

import model.Emprestimo;
import model.Livro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Concentra os dados (em memoria) e as regras de negocio da biblioteca.
 * A tela (view) apenas chama estes metodos e mostra mensagens.
 * Quando uma regra e violada, o metodo lanca IllegalArgumentException
 * com a mensagem que deve ser exibida ao usuario.
 */
public class BibliotecaService {

    private final List<Livro> livros = new ArrayList<>();
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    // =======================================================
    // LIVROS
    // =======================================================

    public void cadastrarLivro(String codigo, String titulo, String autor, String categoria, int quantidade) {
        if (vazio(codigo) || vazio(titulo) || vazio(autor)) {
            throw new IllegalArgumentException("Por favor, preencha todos os campos obrigatórios!");
        }
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa!");
        }
        if (buscarLivro(codigo) != null) {
            throw new IllegalArgumentException("Já existe um livro cadastrado com este código!");
        }

        livros.add(new Livro(
                codigo.trim(),
                titulo.trim(),
                autor.trim(),
                categoria == null ? "" : categoria.trim(),
                quantidade));
    }

    public void atualizarLivro(String codigo, String titulo, String autor, String categoria, int novaQuantidade) {
        Livro livro = buscarLivro(codigo);
        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado!");
        }
        if (vazio(titulo) || vazio(autor)) {
            throw new IllegalArgumentException("Título e autor são obrigatórios!");
        }
        if (novaQuantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa!");
        }

        int emprestados = livro.getQuantTotal() - livro.getQuantDisponivel();
        if (novaQuantidade < emprestados) {
            throw new IllegalArgumentException(
                    "A nova quantidade não pode ser menor do que a quantidade de livros já emprestados (" + emprestados + ")!");
        }

        int diferenca = novaQuantidade - livro.getQuantTotal();
        livro.setTitulo(titulo.trim());
        livro.setAutor(autor.trim());
        livro.setCategoria(categoria == null ? "" : categoria.trim());
        livro.setQuantTotal(novaQuantidade);
        livro.setQuantDisponivel(livro.getQuantDisponivel() + diferenca);
    }

    public void excluirLivro(String codigo) {
        Livro livro = buscarLivro(codigo);
        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado!");
        }

        int ativos = contarEmprestimosAtivos(livro);
        if (ativos > 0) {
            throw new IllegalArgumentException(
                    "Não é possível excluir! Este livro possui " + ativos + " empréstimo(s) ativo(s).");
        }

        livros.remove(livro);
    }

    /** Retorna null se nao encontrar. */
    public Livro buscarLivro(String codigo) {
        if (codigo == null) return null;
        for (Livro l : livros) {
            if (l.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return l;
            }
        }
        return null;
    }

    /** Pesquisa por trecho do codigo ou do titulo. Termo vazio devolve todos. */
    public List<Livro> pesquisarLivros(String termo) {
        if (vazio(termo)) {
            return listarLivros();
        }
        String t = termo.trim().toLowerCase();
        List<Livro> resultado = new ArrayList<>();
        for (Livro l : livros) {
            if (l.getCodigo().toLowerCase().contains(t) || l.getTitulo().toLowerCase().contains(t)) {
                resultado.add(l);
            }
        }
        return resultado;
    }

    public List<Livro> listarLivros() {
        return Collections.unmodifiableList(livros);
    }

    // =======================================================
    // EMPRESTIMOS
    // =======================================================

    public void registrarEmprestimo(String codigo, String nomeUsuario) {
        if (vazio(codigo) || vazio(nomeUsuario)) {
            throw new IllegalArgumentException("Preencha o código do livro e o nome do usuário!");
        }

        Livro livro = buscarLivro(codigo);
        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado!");
        }
        if (livro.getQuantDisponivel() <= 0) {
            throw new IllegalArgumentException("Não há exemplares disponíveis deste livro no momento!");
        }

        emprestimos.add(new Emprestimo(livro, nomeUsuario.trim()));
        livro.setQuantDisponivel(livro.getQuantDisponivel() - 1);
    }

    /**
     * Devolve o emprestimo pela posicao na lista de emprestimos
     * (a mesma ordem exibida na tabela da tela).
     */
    public void registrarDevolucao(int indice) {
        if (indice < 0 || indice >= emprestimos.size()) {
            throw new IllegalArgumentException("Empréstimo inválido!");
        }

        Emprestimo emp = emprestimos.get(indice);
        if (!emp.isAtivo()) {
            throw new IllegalArgumentException("Este livro já foi devolvido anteriormente!");
        }

        emp.setAtivo(false);
        Livro livro = emp.getLivro();
        livro.setQuantDisponivel(livro.getQuantDisponivel() + 1);
    }

    public List<Emprestimo> listarEmprestimos() {
        return Collections.unmodifiableList(emprestimos);
    }

    // =======================================================
    // RELATORIO
    // =======================================================

    public int getTotalTitulos() {
        return livros.size();
    }

    public int getTotalExemplares() {
        int total = 0;
        for (Livro l : livros) {
            total += l.getQuantTotal();
        }
        return total;
    }

    public int getTotalDisponiveis() {
        int total = 0;
        for (Livro l : livros) {
            total += l.getQuantDisponivel();
        }
        return total;
    }

    public int getTotalEmprestimosAtivos() {
        int total = 0;
        for (Emprestimo emp : emprestimos) {
            if (emp.isAtivo()) {
                total++;
            }
        }
        return total;
    }

    // =======================================================
    // AUXILIARES
    // =======================================================

    private int contarEmprestimosAtivos(Livro livro) {
        int total = 0;
        for (Emprestimo emp : emprestimos) {
            if (emp.isAtivo() && emp.getLivro() == livro) {
                total++;
            }
        }
        return total;
    }

    private boolean vazio(String s) {
        return s == null || s.trim().isEmpty();
    }
}

