package model;

public class Livro {

    private final String codigo;
    private String titulo;
    private String autor;
    private String categoria;
    private int quantTotal;
    private int quantDisponivel;

    public Livro(String codigo, String titulo, String autor, String categoria, int quantidade){
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.quantTotal = quantidade;
        this.quantDisponivel = quantidade;
    }

        public String getCodigo() {return codigo;}
        public String getTitulo() {return titulo;}
        public String getAutor() {return autor;}
        public String getCategoria() {return categoria;}
        public int getQuantTotal() {return quantTotal;}
        public int getQuantDisponivel() {return quantDisponivel;}

        public void setTitulo(String titulo) {this.titulo = titulo;}
        public void setAutor(String autor) {this.autor = autor;}
        public void setCategoria(String categoria) {this.categoria = categoria;}
        public void setQuantTotal(int quantTotal) {this.quantTotal = quantTotal;}
        public void setQuantDisponivel(int quantDisponivel) {this.quantDisponivel = quantDisponivel;}
}
