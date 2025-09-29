package model;

import java.time.LocalDateTime;
import java.util.Arrays;

public class LeituraAbaco {
    private int id;
    private LocalDateTime dataHora;
    private byte[] imagem;

    // CONSTRUTOR

    public LeituraAbaco( byte[] imagem, LocalDateTime dataHora) {
        this.imagem = imagem;
        this.dataHora = dataHora;
    }
    public LeituraAbaco(int id, LocalDateTime dataHora, byte[] imagem) {
        this.id = id;
        this.dataHora = dataHora;

    }

    // GETTERS

    public int getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public byte[] getImagem() {
        return imagem;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    // TO STRING

    @Override
    public String toString() {
     return "-- LeituraAbaco --"+
             "\nID: "+id+
             "\nDATA E HORA: "+ dataHora+
             "\nIMAGEM: "+imagem;
    }
}
