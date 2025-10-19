package model;

public class Plano {

    // ATRIBUTOS
    private int id;
    private String nome;
    private double preco;

    // CONSTRUTOR VAZIO
    public Plano() {}

    // CONSTRUTOR SEM ID (para novos registros)
    public Plano(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // CONSTRUTOR COMPLETO (quando for carregar do BD)
    public Plano(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
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
        return "-- Plano --" +
                "\nID: " + id +
                "\nNome: " + nome +
                "\nPreço: " + preco;
    }
}
