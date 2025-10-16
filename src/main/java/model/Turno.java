package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {
    private int id;
    private LocalTime tempoDuracao;
    private int qtdFuncionarios;
    private LocalDate data;

    // CONSTRUTOR

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

    public void setTempoDuracao(LocalTime tempo_duracao) {
        this.tempoDuracao = tempo_duracao;
    }

    public void setQtdFuncionarios(int qtd_funcionarios) {
        this.qtdFuncionarios = qtd_funcionarios;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    // TO STRING

    @Override
    public String toString() {
        return " -- Turno -- " +
                "\nID: " + id +
                "\nTempo de Duração: " + tempoDuracao +
                "\nQuantidade de Funcionários: " + qtdFuncionarios;
    }
}
