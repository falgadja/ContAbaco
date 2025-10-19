package model;

import java.time.LocalDate;

public class Funcionario {

    // ATRIBUTOS
    private int id;
    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;
    private String email;
    private String senha;
    private int idSetor;
    private int idEmpresa;

    // CONSTRUTOR VAZIO
    public Funcionario() {}

    // CONSTRUTOR SEM ID (para novos registros)
    public Funcionario(String nome, String sobrenome, LocalDate dataNascimento, String email, String senha, int idSetor, int idEmpresa) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
        this.idSetor = idSetor;
        this.idEmpresa = idEmpresa;
    }

    // CONSTRUTOR COMPLETO (quando for carregar do BD)
    public Funcionario(int id, String nome, String sobrenome, LocalDate dataNascimento, String email, String senha, int idSetor, int idEmpresa) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
        this.idSetor = idSetor;
        this.idEmpresa = idEmpresa;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public int getIdSetor() {
        return idSetor;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setIdSetor(int idSetor) {
        this.idSetor = idSetor;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    // TO STRING
    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", idSetor=" + idSetor +
                ", idEmpresa=" + idEmpresa +
                '}';
    }
}
