package model;

public class Avaria {
    private int id;
    private String nome;
    private String descricao;
    private int id;

    // CONSTRUTOR

    public Avaria(String nome, int id, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    //GETTERS
    public String getNome() {
        return nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public int getId() {
        return id;
    }

    //SETTERS
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    //TO STRING
    public String toString(){
        return "-- Avaria--"+
                "\nID: "+id+
                "\nNOME: "+nome+
                "\nDESCRIÇÃO: "+descricao;
    }

}
