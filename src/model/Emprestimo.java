package model;

public class Emprestimo {
    private final Livro livro;
    private final String nomeUsuario;
    private boolean ativo;

    public Emprestimo(Livro livro, String nomeUsuario){
        this.livro = livro;
        this.nomeUsuario = nomeUsuario;
        this.ativo = true;
    }

    public Livro getLivro() {return livro;}
    public String getNomeUsuario() {return nomeUsuario;}
    public boolean isAtivo() {return ativo;}

    public void setAtivo(boolean ativo) {this.ativo = ativo;}
}

