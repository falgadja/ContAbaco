package model;

import java.util.List;

public class Empresa {
    private long cpnj;
    private String nome;
    private Login login;
    private int qtdFuncionarios;
    private List<Funcionario> funcionarios;

    //CONSTRUTOR

    public Empresa(long cpnj, String nome, Login login, int qtdFuncionarios, List<Funcionario> funcionarios) {
        this.cpnj = cpnj;
        this.nome = nome;
        this.login = login;
        this.qtdFuncionarios = qtdFuncionarios;
        this.funcionarios = funcionarios;
    }

    // GETTERS

    public long getCpnj() {
        return cpnj;
    }

    public String getNome() {
        return nome;
    }

    public Login getLogin() {
        return login;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public int getQtdFuncionarios() {
        return qtdFuncionarios;
    }
    
    // SETTERS

    public void setCpnj(long cpnj) {
        this.cpnj = cpnj;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public void setQtdFuncionarios(int qtdFuncionarios) {
        this.qtdFuncionarios = qtdFuncionarios;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    // TO STRING

    @Override
    public String toString() {
        return "Empresa{" +
                "cpnj=" + cpnj +
                ", nome='" + nome + '\'' +
                ", login=" + login +
                ", qtdFuncionarios=" + qtdFuncionarios +
                ", funcionarios=" + funcionarios +
                '}';
    }
}
