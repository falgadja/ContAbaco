package model;

public class Setor {

    // ATRIBUTOS
    private int id;
    private String nome;

    // CONSTRUTOR VAZIO
    public Setor() {}

    // CONSTRUTOR SEM ID (para novos registros)
    public Setor(String nome) {
        this.nome = nome;
    }

    // CONSTRUTOR COMPLETO (quando for carregar do BD)
    public Setor(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // GETTERS
    public int getId() {
        return id;
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

    // TO STRING
    @Override
    public String toString() {
        return "-- Setor --" +
                "\nID: " + id +
                "\nNome: " + nome;
    }
}
