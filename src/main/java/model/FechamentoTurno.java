package model;

import java.time.LocalDate;

public class FechamentoTurno {
    private int id;
    private String descricao;
    private LocalDate dataRegistro;
    private int lote;
    private int quantidadeAvarias;


    // CONSTRUTOR

    public FechamentoTurno(int id, String descricao, LocalDate dataRegistro) {
        this.id = id;
        this.descricao = descricao;
        this.dataRegistro = dataRegistro;
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    // TO STRING

    @Override
    public String toString() {
        return "Fechamento do Turno{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", dataRegistro=" + dataRegistro +
                '}';
    }
}
