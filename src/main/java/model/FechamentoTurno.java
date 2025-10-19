package model;

import java.time.LocalDate;

public class FechamentoTurno {

    // ATRIBUTOS
    private int id;
    private int idFuncionario;
    private LocalDate data;
    private int lote;
    private int idLeitura;

    // CONSTRUTOR VAZIO
    public FechamentoTurno() {}

    // CONSTRUTOR SEM ID (para novos registros)
    public FechamentoTurno(int idFuncionario, LocalDate data, int lote, int idLeitura) {
        this.idFuncionario = idFuncionario;
        this.data = data;
        this.lote = lote;
        this.idLeitura = idLeitura;
    }

    // CONSTRUTOR COMPLETO (quando for carregar do BD)
    public FechamentoTurno(int id, int idFuncionario, LocalDate data, int lote, int idLeitura) {
        this.id = id;
        this.idFuncionario = idFuncionario;
        this.data = data;
        this.lote = lote;
        this.idLeitura = idLeitura;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public LocalDate getData() {
        return data;
    }

    public int getLote() {
        return lote;
    }

    public int getIdLeitura() {
        return idLeitura;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }

    public void setIdLeitura(int idLeitura) {
        this.idLeitura = idLeitura;
    }

    // TO STRING
    @Override
    public String toString() {
        return "FechamentoTurno{" +
                "id=" + id +
                ", idFuncionario=" + idFuncionario +
                ", data=" + data +
                ", lote=" + lote +
                ", idLeitura=" + idLeitura +
                '}';
    }
}
