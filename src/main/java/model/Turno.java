package model;

import java.time.LocalDateTime;

public class Turno {
    private int qtdFuncionarios;
    private int id;
    private LocalDateTime tempoDuracao;

    // CONSTRUTOR

    public Turno(int qtdFuncionarios, LocalDateTime tempoDuracao, int id) {
        this.qtdFuncionarios = qtdFuncionarios;
        this.tempoDuracao = tempoDuracao;
        this.id = id;
    }

    // GETTERS

    public int getQtdFuncionarios() {
        return qtdFuncionarios;
    }

    public LocalDateTime getTempoDuracao() {
        return tempoDuracao;
    }

    public int getId() {
        return id;
    }

    // SETTERS

    public void setQtd_funcionarios(int qtd_funcionarios) {
        this.qtdFuncionarios = qtd_funcionarios;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTempo_duracao(LocalDateTime tempo_duracao) {
        this.tempoDuracao = tempo_duracao;
    }

    // TO STRING

    @Override
    public String toString() {
        return "Turno{" +
                "quantidade de funcionarios=" + qtdFuncionarios +
                ", id=" + id +
                ", tempo de duracao=" + tempoDuracao +
                '}';
    }
}
