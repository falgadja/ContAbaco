package model;

public class FechamentoAvaria{
    private int id;
    private int idAvaria;
    private int idFechamento;
    private int quantidade;

    // CONSTRUTOR


    public FechamentoAvaria( int id,int idAvaria, int idFechamento, int quantidade) {
        this.id = id;
        this.idAvaria = idAvaria;
        this.idFechamento = idFechamento;
        this.quantidade = quantidade;
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public int getIdAvaria() {
        return idAvaria;
    }

    public int getIdFechamento() {
        return idFechamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setIdFechamento(int idFechamento) {
        this.idFechamento = idFechamento;
    }

    public void setIdAvaria(int idAvaria) {
        this.idAvaria = idAvaria;
    }

    // TO STRING

    @Override
    public String toString() {
        return "FechamentoAvaria{" +
                "id=" + id +
                ", idAvaria=" + idAvaria +
                ", idFechamento=" + idFechamento +
                ", quantidade=" + quantidade +
                '}';
    }
}