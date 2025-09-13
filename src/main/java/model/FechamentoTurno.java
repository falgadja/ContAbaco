package model;

import java.time.LocalDate;

public class FechamentoTurno {
    private int id;
    private String descricao;
    private LocalDate dataRegistro;
    private int idFuncionario;
    private int idTurno;

    // CONSTRUTOR
    public FechamentoTurno(int id, String descricao, LocalDate dataRegistro, int idFuncionario, int idTurno) {
        this.id = id;
        this.descricao = descricao;
        this.dataRegistro = dataRegistro;
        this.idFuncionario = idFuncionario;
        this.idTurno = idTurno;
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

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public int getIdTurno() {
        return idTurno;
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

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    // TO STRING

    @Override
    public String toString() {
        return "Fechamento do Turno{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", dataRegistro=" + dataRegistro +
                ", idFuncionario=" + idFuncionario +
                ", idTurno=" + idTurno +
                '}';
    }
}
