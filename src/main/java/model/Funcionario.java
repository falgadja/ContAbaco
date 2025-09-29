package model;

import java.time.LocalDate;

public class Funcionario {
    private int id;
    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;
    private String email;
    private String senha;
    private int idSetor;
    private int idEmpresa;

    public Funcionario() {}

    // CONSTRUTOR

    public Funcionario(int id, String nome, String sobrenome, LocalDate dataNascimento, String email, String senha, int idEmpresa, int idSetor) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
        this.idEmpresa = idEmpresa;
        this.idSetor = idSetor;
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getSenha() {
        return senha;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public int getIdSetor() {
        return idSetor;
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

    public void setIdSetor(int idSetor) {
        this.idSetor = idSetor;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
