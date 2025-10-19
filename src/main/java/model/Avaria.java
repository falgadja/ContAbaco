package model;

public class Avaria {

    // ATRIBUTOS
    private int id;
    private String nome;
    private String descricao;

    // CONSTRUTOR VAZIO
    public Avaria() {}

    // CONSTRUTOR SEM ID (para novos registros)
    public Avaria(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // CONSTRUTOR COMPLETO (quando for carregar do BD)
    public Avaria(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // TO STRING
    @Override
    public String toString() {
        return "-- Avaria --" +
                "\nID: " + id +
                "\nNome: " + nome +
                "\nDescrição: " + descricao;
    }
}
