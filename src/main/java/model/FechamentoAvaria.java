package model;

public class FechamentoAvaria {

    // ATRIBUTOS
    private int id;
    private int idAvaria;
    private int idFechamento;
    private int quantidade;

    // CONSTRUTOR VAZIO
    public FechamentoAvaria() {}

    // CONSTRUTOR SEM ID (para novos registros)
    public FechamentoAvaria(int idAvaria, int idFechamento, int quantidade) {
        this.idAvaria = idAvaria;
        this.idFechamento = idFechamento;
        this.quantidade = quantidade;
    }

    // CONSTRUTOR COMPLETO (quando for carregar do BD)
    public FechamentoAvaria(int id, int idAvaria, int idFechamento, int quantidade) {
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

    public void setIdAvaria(int idAvaria) {
        this.idAvaria = idAvaria;
    }

    public void setIdFechamento(int idFechamento) {
        this.idFechamento = idFechamento;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
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
