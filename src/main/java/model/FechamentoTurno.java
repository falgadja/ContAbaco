package model;

import java.time.LocalDate;

public class FechamentoTurno {
    private int id;
    private String descricao;
    private LocalDate dataRegistro;
    private int lote;
    private int quantidadeAvarias;


    // CONSTRUTOR

    public FechamentoTurno(int id, String descricao, LocalDate dataRegistro, int lote, int quantidadeAvarias) {
        this.id = id;
        this.descricao = descricao;
        this.dataRegistro = dataRegistro;
        this.lote=lote;
        this.quantidadeAvarias=quantidadeAvarias;
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

    public int getLote() {
        return lote;
    }

    public int getQuantidadeAvarias() {
        return quantidadeAvarias;
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

    public void setLote(int lote) {
        this.lote = lote;
    }

    public void setQuantidadeAvarias(int quantidadeAvarias) {
        this.quantidadeAvarias = quantidadeAvarias;
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
