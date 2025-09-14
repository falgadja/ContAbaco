package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Funcionario {
    private int id;
    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;

    // CONSTRUTOR

    public Funcionario(int id, String nome, LocalDate dataNascimento, String sobrenome) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sobrenome = sobrenome;
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
                ",Nome: '" + nome + '\'' +
                ", Data de nascimento: " + dataNascimento +
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
