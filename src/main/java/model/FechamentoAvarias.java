package model;

public class FechamentoAvarias{
    private int id;
    private int idAvaria;
    private int idFechamento;
    private int quantidade;

    //CONSTRUTOR
    public FechamentoAvarias(int id, int idAvaria, int idFechamento, int quantidade) {
        this.id = id;
        this.idAvaria = idAvaria;
        this.idFechamento = idFechamento;
        this.quantidade = quantidade;
    }
    //MÉTODO GET
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
    //MÉTODO SET
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


    //TO STRING
    public String toString() {
        return "--Fechamento Avarias--"+
                "\nID: "+id+
                "\n ID AVARIA: "+ idAvaria
                + "\n ID FECHAMENTO: "+ idFechamento
                + "\n QUANTIDADE: "+ quantidade;
    }

}