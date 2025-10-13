package model;

public class Plano {
    private int id;
    private String nome;
    private double preco;

    //CONSTRUTOR

    public Plano(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    // GETTERS


    public int getId() {
        return id;
    }

    public double getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    // TO STRING

    @Override
    public String toString() {
        return " -- Plano -- " +
                "\nID: " + id +
                "\nNome: " + nome +
                "\nPreço: " + preco;
    }
}
