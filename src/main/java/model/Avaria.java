package model;

public class Avarias {
    private int id;
    private String nome;
    private String descricao;

    // CONSTRUTOR

    public Avarias(String nome, int id, String descricao) {
        this.nome = nome;
        this.id = id;
        this.descricao = descricao;
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
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

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // TO STRING

    @Override
    public String toString() {
        return "Avarias{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}