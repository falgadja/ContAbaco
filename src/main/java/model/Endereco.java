package model;

public class Endereco {
    //ATRIBUTOS
    private int id;
    private String pais;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private int numero;
    private String cep;
    private int idEmpresa;

    //CONSTRUTOR
    public  Endereco(int id, String pais, String estado, String cidade, String bairro, String rua, int numero, String cep, int idEmpresa) {
        this.id = id;
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
        this.idEmpresa = idEmpresa;
    }

    //GETTERS
    public int getId() {
        return id;
    }
    public String getPais() {
        return pais;
    }
    public String getEstado() {
        return estado;
    }
    public String getCidade() {
        return cidade;
    }
    public String getBairro() {
        return bairro;
    }
    public String getRua() {
        return rua;
    }
    public int getNumero() {
        return numero;
    }
    public String getCep() {
        return cep;
    }
    public int getIdEmpresa() {
        return idEmpresa;
    }

    //SETTERS
    public void setId(int id) {
        this.id = id;
    }
    public  void setPais(String pais) {
        this.pais = pais;
    }
    public  void setEstado(String estado) {
        this.estado = estado;
    }
    public  void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public  void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public  void setRua(String rua) {
        this.rua = rua;
    }
    public  void setNumero(int numero) {
        this.numero = numero;
    }
    public  void setCep(String cep) {
        this.cep = cep;
    }
    public  void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    //TO STRING
    @Override
    public String toString() {
        return "-- Endereco --" +
                "\nID: " + id +
                "\nPaís: " + pais +
                "\nEstado: " + estado +
                "\nCidade: " + cidade +
                "\nBairro: " + bairro +
                "\nRua: " + rua +
                "\nNúmero: " + numero +
                "\nCEP: " + cep +
                "\nID da Empresa: " + idEmpresa;
    }



}