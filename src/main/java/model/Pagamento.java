package model;

import java.time.LocalDate;

public class Pagamento {
    private int id;
    private String tipoPagto;
    private double total;
    private LocalDate data;
    private byte[] comprovante;
    private int idEmpresa;

    // CONSTRUTOR

    public Pagamento(int id, String tipoPagto, double total, LocalDate data, byte[] comprovante, int idEmpresa) {
        this.id = id;
        this.tipoPagto = tipoPagto;
        this.total = total;
        this.data = data;
        this.comprovante = comprovante;
        this.idEmpresa = idEmpresa;
    }
    public Pagamento(LocalDate data, double total, String tipo) {
        this.data = data;
        this.total = total;
        this.tipo = tipo;
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

    public byte[] getComprovante() {
        return comprovante;
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

    public void setComprovante(byte[] comprovante) {
        this.comprovante = comprovante;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    // TO STRING
    @Override
    public String toString() {
        return " -- Pagamento -- " +
                "\nID: " + id +
                "\nTipo do pagamento: " + tipoPagto +
                "\nTotal: " + total +
                "\nData: " + data +
                "\nID da Empresa" + idEmpresa;
    }
}

