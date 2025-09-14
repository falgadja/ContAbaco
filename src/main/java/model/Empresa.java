package model;

import java.util.List;

public class Empresa {
    private long cpnj;
    private String nome;
    private Login login;
    private int qtdFuncionarios;
    private List<Funcionario> funcionarios;
    private List<Integer> idsPagamentos;
    private List<Integer> idsLogins;

    //CONSTRUTOR

    public Empresa(long cpnj, List<Integer> idsLogins, List<Integer> idsPagamentos, List<Funcionario> funcionarios, int qtdFuncionarios, Login login, String nome) {
        this.cpnj = cpnj;
        this.idsLogins = idsLogins;
        this.idsPagamentos = idsPagamentos;
        this.funcionarios = funcionarios;
        this.qtdFuncionarios = qtdFuncionarios;
        this.login = login;
        this.nome = nome;
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
                ", idsPagamentos=" + idsPagamentos +
                ", idsLogins=" + idsLogins +
                '}';
    }
}
