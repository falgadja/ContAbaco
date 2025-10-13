package model;

public class Empresa {
    private int id;
    private String cpnj;
    private String nome;
    private String email;
    private String senha;
    private int idPlano;
    private int qntdFuncionarios;

    //CONSTRUTOR

    public Empresa(int id, String cpnj, String nome, String email, String senha, int idPlano, int qntdFuncionarios) {
        this.id = id;
        this.cpnj = cpnj;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idPlano = idPlano;
        this.qntdFuncionarios = qntdFuncionarios;
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getCpnj() {
        return cpnj;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public int getIdPlano() {
        return idPlano;
    }

    public int getQntdFuncionarios() {
        return qntdFuncionarios;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setCpnj(String cpnj) {
        this.cpnj = cpnj;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

    public void setQntdFuncionarios(int qntdFuncionarios) {
        this.qntdFuncionarios = qntdFuncionarios;
    }

    // TO STRING

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", cpnj=" + cpnj +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", idPlano=" + idPlano +
                ", qntdFuncionarios=" + qntdFuncionarios +
                '}';
    }
}
