package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Funcionario {
    private int id;
    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;
    private List<Integer> idsTurnos;
    private List<Integer> idsFechamentoTurnos;

    // CONSTRUTOR

    public Funcionario(int id, String nome, String sobrenome, LocalDate dataNascimento, List<Integer> idsTurnos, List<Integer> idsFechamentoTurnos) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.idsTurnos = idsTurnos;
        this.idsFechamentoTurnos = idsFechamentoTurnos;
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    // TO STRING

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", idsTurnos=" + idsTurnos +
                ", idsFechamentoTurnos=" + idsFechamentoTurnos +
                '}';
    }

    //Método para verificar data de nascimento
    public boolean verificaDataNascimento(LocalDateTime dataNascimento) {
        LocalDateTime agora = LocalDateTime.now(); //Instanciar data de agora
        System.out.println(agora);
        //Verifica se a data de agora é igual a data de nascimento
        if (agora.isBefore(dataNascimento)) { //Verifica se a data de agora é antes da data de nascimento
            return false;
        } else return !agora.isEqual(dataNascimento);
    }
}
