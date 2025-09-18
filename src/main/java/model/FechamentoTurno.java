package model;

import java.time.LocalDate;
import java.util.List;

public class FechamentoTurno {
    private int id;
    private String descricao;
    private LocalDate dataRegistro;
    private int lote;
    private int quantidadeAvarias;
    private List<Integer> idsAvarias;
    private int idLeiturasAbacos;


    // CONSTRUTOR

    public FechamentoTurno(int id, String descricao, LocalDate dataRegistro, int lote, int quantidadeAvarias, List<Integer> idsAvarias,int idLeiturasAbacos) {
        this.id = id;
        this.descricao = descricao;
        this.dataRegistro = dataRegistro;
        this.lote=lote;
        this.quantidadeAvarias=quantidadeAvarias;
        this.idsAvarias = idsAvarias;
        this.idLeiturasAbacos=idLeiturasAbacos;
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
        return "FechamentoTurno{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
             ", dataRegistro=" + dataRegistro +
                ", lote=" + lote +
                ", quantidadeAvarias=" + quantidadeAvarias +
                ", idsAvarias=" + idsAvarias +
                ", idsLeiturasAbacos=" + idLeiturasAbacos +
                '}';
    }
}
