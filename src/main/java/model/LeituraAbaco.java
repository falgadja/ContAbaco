package model;

import java.time.LocalDateTime;

public class LeituraAbaco {

    // ATRIBUTOS
    private int id;
    private LocalDateTime dataHora;
    private byte[] imagem;

    // CONSTRUTOR VAZIO
    public LeituraAbaco() {}

    // CONSTRUTOR SEM ID (para novos registros)
    public LeituraAbaco(LocalDateTime dataHora, byte[] imagem) {
        this.dataHora = dataHora;
        this.imagem = imagem;
    }

    // CONSTRUTOR COMPLETO (quando for carregar do BD)
    public LeituraAbaco(int id, LocalDateTime dataHora, byte[] imagem) {
        this.id = id;
        this.dataHora = dataHora;
        this.imagem = imagem;
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

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    // TO STRING
    @Override
    public String toString() {
        return "-- LeituraAbaco --" +
                "\nID: " + id +
                "\nData e Hora: " + dataHora +
                "\nImagem: " + imagem;
    }
}
