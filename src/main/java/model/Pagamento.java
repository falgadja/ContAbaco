package model;

import java.time.LocalDate;

public class Pagamento {
    private int id;
    private String tipo;
    private double total;
    private LocalDate data;

    // CONSTRUTOR

    public Pagamento(int id, LocalDate data, double total, String tipo) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.tipo = tipo;
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

    public LocalDate getData() {
        return data;
    }

    public double getTotal() {
        return total;
    }

    public String getTipo() {
        return tipo;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // TO STRING

    @Override
    public String toString() {
        return "Pagamento{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", total=" + total +
                ", data=" + data +
                '}';
    }
}
