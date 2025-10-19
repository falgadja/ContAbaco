package model;

public class Administrador {

    // ATRIBUTOS
    private int id;
    private String email;
    private String senha;

    // CONSTRUTOR VAZIO
    public Administrador() {}

    // CONSTRUTOR SEM ID (para novos registros)
    public Administrador(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // CONSTRUTOR COMPLETO (quando for carregar do BD)
    public Administrador(int id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // TO STRING
    @Override
    public String toString() {
        return "ID: " + id +
                "\nEmail: " + email +
                "\nSenha: " + senha;
    }

    // VERIFICAR SENHA
    public boolean verificarSenha(String senhaDigitada) {
        return this.senha.equals(senhaDigitada);
    }

    // VERIFICAR EMAIL
    public boolean verificarEmail(String email) {
        return this.email.equals(email);
    }

    // ALTERAR SENHA
    public boolean alterarSenha(String senhaDigitada) {
        if (this.verificarSenha(senhaDigitada)) {
            this.senha = senhaDigitada;
            return true;
        }
        return false;
    }

    // ALTERAR EMAIL
    public boolean alterarEmail(String email) {
        if (this.verificarEmail(email)) {
            this.email = email;
            return true;
        }
        return false;
    }
}
