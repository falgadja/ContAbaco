package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Turno {
    private int qtdFuncionarios;
    private int id;
    private LocalDateTime tempoDuracao;
    private List<Integer> idsFuncionarios;

    // CONSTRUTOR

    public Turno(int id, LocalDateTime tempoDuracao, List<Integer> idsFuncionarios) {
        this.id = id;
        this.tempoDuracao = tempoDuracao;
        this.idsFuncionarios = idsFuncionarios;
        this.qtdFuncionarios = (idsFuncionarios != null) ? idsFuncionarios.size() : 0;
    }


    public Turno(int id, LocalDateTime tempoDuracao, int qtdFuncionarios) {
        this.id = id;
        this.tempoDuracao = tempoDuracao;
        this.qtdFuncionarios = qtdFuncionarios;
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

    public void setQtdFuncionarios(int qtd_funcionarios) {
        this.qtdFuncionarios = qtd_funcionarios;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTempoDuracao(LocalDateTime tempo_duracao) {
        this.tempoDuracao = tempo_duracao;
    }

    // TO STRING

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", tempoDuracao=" + tempoDuracao +
                ", qtdFuncionarios=" + qtdFuncionarios +
                ", idsFuncionarios=" + idsFuncionarios +
                '}';
    }
}
