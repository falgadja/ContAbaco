package model;

import java.time.LocalDate;

public class Pagamento {

    // ATRIBUTOS
    private int id;
    private String tipoPagto;
    private double total;
    private LocalDate data;
    private int idEmpresa;

    // CONSTRUTOR VAZIO
    public Pagamento() {}

    // CONSTRUTOR SEM ID (para novos registros)
    public Pagamento(String tipoPagto, double total, LocalDate data,  int idEmpresa) {
        this.tipoPagto = tipoPagto;
        this.total = total;
        this.data = data;
        this.idEmpresa = idEmpresa;
    }

    // CONSTRUTOR COMPLETO (quando for carregar do BD)
    public Pagamento(int id, String tipoPagto, double total, LocalDate data, int idEmpresa) {
        this.id = id;
        this.tipoPagto = tipoPagto;
        this.total = total;
        this.data = data;
        this.idEmpresa = idEmpresa;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getTipoPagto() {
        return tipoPagto;
    }

    public double getTotal() {
        return total;
    }

    public LocalDate getData() {
        return data;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setTipoPagto(String tipoPagto) {
        this.tipoPagto = tipoPagto;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    // TO STRING
    @Override
    public String toString() {
        return "-- Pagamento --" +
                "\nID: " + id +
                "\nTipo do Pagamento: " + tipoPagto +
                "\nTotal: " + total +
                "\nData: " + data +
                "\nID da Empresa: " + idEmpresa;
    }
}
