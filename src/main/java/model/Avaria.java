package model;

public class Avaria {
    //ATRIBUTOS
    private String nome;
    private String descricao;
    private int id;

    //CONSTRUTORES
    public Avaria(String nome, String descricao,int id) {
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
