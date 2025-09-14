package model;

public class Login {
    private String email;
    private String senha;
    private int id;

    // CONSTRUTOR

    public Login(String email, String senha, int id) {
        this.email = email;
        this.senha = senha;
        this.id = id;
    }

    // GETTERS

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public int getId() {
        return id;
    }

    // SETTERS

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // TO STRING

    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", ID=" + id +
                '}';
    }

    //Método verificar email
    public static boolean validarEmail(String email) {
        if (email == null) { //Verifica se o email esta null//
            return false;
        }
        //Posição do @
        int posicao = email.indexOf('@');
        //Verifica se o @ esta na primeira ou ultima posição//
        return posicao > 0 && posicao != email.length() - 1;
    }

    //Método verificar senha
    public static boolean validarSenha(String password) {
        return password != null && password.length() >= 8; //Verifica se esta null e se tem mais de 8 caracteres//
    }

}

