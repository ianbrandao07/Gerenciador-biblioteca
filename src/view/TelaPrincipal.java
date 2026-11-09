package view;

import controller.BibliotecaService;
import model.Emprestimo;
import model.Livro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaPrincipal extends JFrame {

    // Toda a logica e os dados ficam no service; a tela so exibe e chama os metodos dele
    private final BibliotecaService service = new BibliotecaService();

    // Componentes da aba de Cadastro
    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtCategoria;
    private JTextField txtQuantidade;
    private JButton btnSalvarLivro;
    private JButton btnLimparCampos;

    // Componentes da aba de Consulta
    private JTextField txtPesquisa;
    private JButton btnBuscar;
    private JButton btnListarTodos;
    private JTable tabelaLivros;
    private DefaultTableModel modeloTabelaLivros;
    private JButton btnEditarLivro;
    private JButton btnExcluirLivro;

    // Componentes da aba de Emprestimos
    private JTextField txtCodigoEmprestimo;
    private JTextField txtNomeUsuario;
    private JButton btnRegistrarEmprestimo;
    private JTable tabelaEmprestimos;
    private DefaultTableModel modeloTabelaEmprestimos;
    private JButton btnDevolver;

    // Componentes da aba de Relatorio
    private JLabel lblTotalTitulos;
    private JLabel lblTotalExemplares;
    private JLabel lblExemplaresDisponiveis;
    private JLabel lblEmprestimosAtivos;
    private JButton btnAtualizarRelatorio;

    public TelaPrincipal() {
        setTitle("Sistema de Gerenciamento de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();

        abas.addTab("Cadastro de Livros", criarAbaCadastro());
        abas.addTab("Consulta de Livros", criarAbaConsulta());
        abas.addTab("Empréstimos e Devoluções", criarAbaEmprestimos());
        abas.addTab("Relatório", criarAbaRelatorio());

        // Atualiza o relatorio quando o usuario clicar na aba dele
        abas.addChangeListener(e -> {
            if (abas.getSelectedIndex() == 3) {
                atualizarRelatorio();
            }
        });

        add(abas);
    }

    // =======================================================
    // 1. ABA DE CADASTRO
    // =======================================================
    private JPanel criarAbaCadastro() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTituloAba = new JLabel("Cadastro de Novo Livro", JLabel.CENTER);
        lblTituloAba.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(lblTituloAba, BorderLayout.NORTH);

        JPanel painelForm = new JPanel(new GridLayout(6, 2, 10, 15));

        painelForm.add(new JLabel("Código do Livro:"));
        txtCodigo = new JTextField();
        painelForm.add(txtCodigo);

        painelForm.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        painelForm.add(txtTitulo);

        painelForm.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        painelForm.add(txtAutor);

        painelForm.add(new JLabel("Categoria:"));
        txtCategoria = new JTextField();
        painelForm.add(txtCategoria);

        painelForm.add(new JLabel("Quantidade de Exemplares:"));
        txtQuantidade = new JTextField();
        painelForm.add(txtQuantidade);

        btnLimparCampos = new JButton("Limpar");
        btnSalvarLivro = new JButton("Cadastrar");

        painelForm.add(btnLimparCampos);
        painelForm.add(btnSalvarLivro);

        painel.add(painelForm, BorderLayout.CENTER);

        btnSalvarLivro.addActionListener(e -> cadastrarLivro());
        btnLimparCampos.addActionListener(e -> limparCamposCadastro());

        return painel;
    }

    private void cadastrarLivro() {
        String qtdStr = txtQuantidade.getText().trim();

        if (qtdStr.isEmpty()) {
            mostrarAviso("Por favor, preencha todos os campos obrigatórios!");
            return;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(qtdStr);
        } catch (NumberFormatException ex) {
            mostrarErro("A quantidade deve ser um número inteiro!");
            return;
        }

        try {
            service.cadastrarLivro(
                    txtCodigo.getText(),
                    txtTitulo.getText(),
                    txtAutor.getText(),
                    txtCategoria.getText(),
                    quantidade);
        } catch (IllegalArgumentException ex) {
            mostrarErro(ex.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
        limparCamposCadastro();
        atualizarTabelaLivros(service.listarLivros());
    }

    private void limparCamposCadastro() {
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtCategoria.setText("");
        txtQuantidade.setText("");
    }

    // =======================================================
    // 2. ABA DE CONSULTA E LISTAGEM
    // =======================================================
    private JPanel criarAbaConsulta() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelPesquisa.add(new JLabel("Pesquisar (Código ou Título):"));
        txtPesquisa = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        btnListarTodos = new JButton("Listar Todos");

        painelPesquisa.add(txtPesquisa);
        painelPesquisa.add(btnBuscar);
        painelPesquisa.add(btnListarTodos);

        painel.add(painelPesquisa, BorderLayout.NORTH);

        String[] colunas = {"Código", "Título", "Autor", "Categoria", "Qtd. Total", "Qtd. Disponível"};
        modeloTabelaLivros = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaLivros = new JTable(modeloTabelaLivros);
        painel.add(new JScrollPane(tabelaLivros), BorderLayout.CENTER);

        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEditarLivro = new JButton("Editar Selecionado");
        btnExcluirLivro = new JButton("Excluir Selecionado");
        painelAcoes.add(btnEditarLivro);
        painelAcoes.add(btnExcluirLivro);
        painel.add(painelAcoes, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> pesquisarLivro());
        btnListarTodos.addActionListener(e -> {
            txtPesquisa.setText("");
            atualizarTabelaLivros(service.listarLivros());
        });
        btnEditarLivro.addActionListener(e -> editarLivro());
        btnExcluirLivro.addActionListener(e -> excluirLivro());

        return painel;
    }

    private void pesquisarLivro() {
        String termo = txtPesquisa.getText();
        List<Livro> resultado = service.pesquisarLivros(termo);

        atualizarTabelaLivros(resultado);

        if (resultado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum livro encontrado com o termo informado.");
        }
    }

    private void atualizarTabelaLivros(List<Livro> lista) {
        modeloTabelaLivros.setRowCount(0);
        for (Livro l : lista) {
            Object[] linha = {
                    l.getCodigo(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getCategoria(),
                    l.getQuantTotal(),
                    l.getQuantDisponivel()
            };
            modeloTabelaLivros.addRow(linha);
        }
    }

    private void editarLivro() {
        int linhaSelecionada = tabelaLivros.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um livro na tabela para editar.");
            return;
        }

        String codigo = (String) modeloTabelaLivros.getValueAt(linhaSelecionada, 0);
        Livro livro = service.buscarLivro(codigo);
        if (livro == null) return;

        String novoTitulo = JOptionPane.showInputDialog(this, "Novo título:", livro.getTitulo());
        if (novoTitulo == null || novoTitulo.trim().isEmpty()) return;

        String novoAutor = JOptionPane.showInputDialog(this, "Novo autor:", livro.getAutor());
        if (novoAutor == null || novoAutor.trim().isEmpty()) return;

        String novaCategoria = JOptionPane.showInputDialog(this, "Nova categoria:", livro.getCategoria());
        if (novaCategoria == null) novaCategoria = "";

        String novaQtdStr = JOptionPane.showInputDialog(this, "Nova quantidade total:", livro.getQuantTotal());
        if (novaQtdStr == null) return;

        try {
            int novaQtd = Integer.parseInt(novaQtdStr.trim());
            service.atualizarLivro(codigo, novoTitulo, novoAutor, novaCategoria, novaQtd);

            JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!");
            atualizarTabelaLivros(service.listarLivros());

        } catch (NumberFormatException ex) {
            mostrarErro("Número inválido informado para quantidade!");
        } catch (IllegalArgumentException ex) {
            mostrarErro(ex.getMessage());
        }
    }

    private void excluirLivro() {
        int linhaSelecionada = tabelaLivros.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro na tabela para excluir!");
            return;
        }

        String codigo = (String) modeloTabelaLivros.getValueAt(linhaSelecionada, 0);
        Livro livro = service.buscarLivro(codigo);
        if (livro == null) return;

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o livro '" + livro.getTitulo() + "'?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                service.excluirLivro(codigo);
                atualizarTabelaLivros(service.listarLivros());
                JOptionPane.showMessageDialog(this, "Livro excluído com sucesso!");
            } catch (IllegalArgumentException ex) {
                mostrarAviso(ex.getMessage());
            }
        }
    }

    // =======================================================
    // 3. ABA DE EMPRÉSTIMOS E DEVOLUÇÕES
    // =======================================================
    private JPanel criarAbaEmprestimos() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel painelNovo = new JPanel(new GridLayout(3, 2, 10, 10));
        painelNovo.setBorder(BorderFactory.createTitledBorder("Registrar Empréstimo"));

        painelNovo.add(new JLabel("Código do Livro:"));
        txtCodigoEmprestimo = new JTextField();
        painelNovo.add(txtCodigoEmprestimo);

        painelNovo.add(new JLabel("Nome do Usuário / Aluno:"));
        txtNomeUsuario = new JTextField();
        painelNovo.add(txtNomeUsuario);

        painelNovo.add(new JLabel(""));
        btnRegistrarEmprestimo = new JButton("Pegar Livro Emprestado");
        painelNovo.add(btnRegistrarEmprestimo);

        painel.add(painelNovo, BorderLayout.NORTH);

        String[] colunas = {"Código Livro", "Título do Livro", "Usuário", "Status"};
        modeloTabelaEmprestimos = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEmprestimos = new JTable(modeloTabelaEmprestimos);
        JScrollPane scroll = new JScrollPane(tabelaEmprestimos);
        scroll.setBorder(BorderFactory.createTitledBorder("Empréstimos Realizados"));
        painel.add(scroll, BorderLayout.CENTER);

        JPanel painelSul = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnDevolver = new JButton("Registrar Devolução do Selecionado");
        painelSul.add(btnDevolver);
        painel.add(painelSul, BorderLayout.SOUTH);

        btnRegistrarEmprestimo.addActionListener(e -> registrarEmprestimo());
        btnDevolver.addActionListener(e -> registrarDevolucao());

        return painel;
    }

    private void registrarEmprestimo() {
        String usuario = txtNomeUsuario.getText().trim();

        try {
            service.registrarEmprestimo(txtCodigoEmprestimo.getText(), usuario);
        } catch (IllegalArgumentException ex) {
            mostrarAviso(ex.getMessage());
            return;
        }

        txtCodigoEmprestimo.setText("");
        txtNomeUsuario.setText("");

        atualizarTabelaEmprestimos();
        atualizarTabelaLivros(service.listarLivros());
        JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso para " + usuario + "!");
    }

    private void registrarDevolucao() {
        int linhaSelecionada = tabelaEmprestimos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um empréstimo na tabela para devolver.");
            return;
        }

        try {
            // A tabela mostra os emprestimos na mesma ordem da lista do service,
            // entao a linha selecionada e o indice do emprestimo.
            service.registrarDevolucao(linhaSelecionada);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            return;
        }

        atualizarTabelaEmprestimos();
        atualizarTabelaLivros(service.listarLivros());
        JOptionPane.showMessageDialog(this, "Devolução concluída com sucesso!");
    }

    private void atualizarTabelaEmprestimos() {
        modeloTabelaEmprestimos.setRowCount(0);
        for (Emprestimo emp : service.listarEmprestimos()) {
            Object[] linha = {
                    emp.getLivro().getCodigo(),
                    emp.getLivro().getTitulo(),
                    emp.getNomeUsuario(),
                    emp.isAtivo() ? "Ativo" : "Devolvido"
            };
            modeloTabelaEmprestimos.addRow(linha);
        }
    }

    // =======================================================
    // 4. ABA DE RELATÓRIO
    // =======================================================
    private JPanel criarAbaRelatorio() {
        JPanel painel = new JPanel(new BorderLayout(15, 15));
        painel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel lblTitulo = new JLabel("Relatório Geral da Biblioteca", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelDados = new JPanel(new GridLayout(5, 1, 10, 10));

        lblTotalTitulos = new JLabel("Total de títulos cadastrados: 0");
        lblTotalTitulos.setFont(new Font("Arial", Font.PLAIN, 15));

        lblTotalExemplares = new JLabel("Total de exemplares no acervo: 0");
        lblTotalExemplares.setFont(new Font("Arial", Font.PLAIN, 15));

        lblExemplaresDisponiveis = new JLabel("Exemplares disponíveis: 0");
        lblExemplaresDisponiveis.setFont(new Font("Arial", Font.PLAIN, 15));

        lblEmprestimosAtivos = new JLabel("Empréstimos ativos no momento: 0");
        lblEmprestimosAtivos.setFont(new Font("Arial", Font.PLAIN, 15));

        btnAtualizarRelatorio = new JButton("Atualizar Dados");

        painelDados.add(lblTotalTitulos);
        painelDados.add(lblTotalExemplares);
        painelDados.add(lblExemplaresDisponiveis);
        painelDados.add(lblEmprestimosAtivos);
        painelDados.add(btnAtualizarRelatorio);

        painel.add(painelDados, BorderLayout.CENTER);

        btnAtualizarRelatorio.addActionListener(e -> atualizarRelatorio());

        return painel;
    }

    private void atualizarRelatorio() {
        lblTotalTitulos.setText("Total de títulos cadastrados: " + service.getTotalTitulos());
        lblTotalExemplares.setText("Total de exemplares no acervo: " + service.getTotalExemplares());
        lblExemplaresDisponiveis.setText("Exemplares disponíveis: " + service.getTotalDisponiveis());
        lblEmprestimosAtivos.setText("Empréstimos ativos no momento: " + service.getTotalEmprestimosAtivos());
    }

    // =======================================================
    // AUXILIARES DE MENSAGEM
    // =======================================================
    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}