package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {

    // ATRIBUTOS
    private int id;
    private LocalTime tempoDuracao;
    private int qtdFuncionarios;
    private LocalDate data;

    // CONSTRUTOR VAZIO
    public Turno() {}

    // CONSTRUTOR SEM ID (para novos registros)
    public Turno(LocalTime tempoDuracao, int qtdFuncionarios, LocalDate data) {
        this.tempoDuracao = tempoDuracao;
        this.qtdFuncionarios = qtdFuncionarios;
        this.data = data;
    }

    // CONSTRUTOR COMPLETO (quando for carregar do BD)
    public Turno(int id, LocalTime tempoDuracao, int qtdFuncionarios, LocalDate data) {
        this.id = id;
        this.tempoDuracao = tempoDuracao;
        this.qtdFuncionarios = qtdFuncionarios;
        this.data = data;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public LocalTime getTempoDuracao() {
        return tempoDuracao;
    }

    public int getQtdFuncionarios() {
        return qtdFuncionarios;
    }

    public LocalDate getData() {
        return data;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setTempoDuracao(LocalTime tempoDuracao) {
        this.tempoDuracao = tempoDuracao;
    }

    public void setQtdFuncionarios(int qtdFuncionarios) {
        this.qtdFuncionarios = qtdFuncionarios;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    // TO STRING
    @Override
    public String toString() {
        return "-- Turno --" +
                "\nID: " + id +
                "\nTempo de Duração: " + tempoDuracao +
                "\nQuantidade de Funcionários: " + qtdFuncionarios +
                "\nData: " + data;
    }
}
