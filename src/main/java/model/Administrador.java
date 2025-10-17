package model;

public class Administrador {
    //ATRIBUTOS
    private int id;
    private String email;
    private String senha;

    //CONSTRUTOR
    public Administrador(int id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }
    //GETT
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getSenha() {
        return senha;
    }
    //SET
    public void setId(int id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    //TO STRING
    public String toString() {
        return "Email: " + email + "\nSenha: " + senha;
    }

    //VERIFICAR SENHA
    public boolean verificarSenha(String senhaDigitada) {
        return this.senha.equals(senhaDigitada); //
    }

    //VERIFICAR EMAIL
    public boolean verificarEmail(String email) {
        return this.email.equals(email);
    }
    //ALTERAR SENHA
    public boolean alterarSenha(String senhaDigitada) {
        if (this.verificarSenha(senhaDigitada)) {
            this.senha = senhaDigitada;
            return true;
        }
        return false;
    }
    //ALTERAR EMAIL
    public boolean alterarEmail(String email) {
        if (this.verificarEmail(email)) {
            this.email = email;
            return true;
        }
        return false;
    }




}