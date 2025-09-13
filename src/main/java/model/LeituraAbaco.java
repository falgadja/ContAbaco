package model;

import java.time.LocalDateTime;
import java.util.Arrays;

public class LeituraAbaco {
    private int id;
    private LocalDateTime dataHora;
    private byte[] imagem;

    // CONSTRUTOR

    public LeituraAbaco(int id, byte[] imagem, LocalDateTime dataHora) {
        this.id = id;
        this.imagem = imagem;
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
        return "LeituraAbaco{" +
                "ID=" + id +
                ", dataHora=" + dataHora +
                ", imagem=" + Arrays.toString(imagem) +
                '}';
    }
}
