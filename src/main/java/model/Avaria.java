package model;

public class Avaria {
    private int id;
    private String nome;
    private String descricao;

    // CONSTRUTOR

    public Avaria(String nome, String descricao, int id) {
        this.nome = nome;
        this.descricao = descricao;
        this.id=id;
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
    public void setId(int id) {
        this.id = id;
    }

    //TO STRING
    public String toString(){
        return "-- Avaria--"+
                "\nID: "+id+
                "\nNOME: "+nome+
                "\nDESCRIÇÃO: "+descricao;
    }


}
