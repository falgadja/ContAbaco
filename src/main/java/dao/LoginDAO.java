package dao;

import model.Login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public LoginDAO() {
    }

    // CREATE - INSERIR UM LOGIN
    public int inserirLogin(Login login) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO login (email, password) VALUES (?, ?)");
            ps.setString(1, login.getEmail());
            ps.setString(2, login.getPassword());

            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = 0;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }
    //READ-LER OS LOGIN
    public Login buscarPorID(int id)throws SQLException {
        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM LOGIN WHERE ID=?");
            ps.setString(1, login.getID());

    // READ- BUSCAR USUARIO PELO EMAIL
    public int buscarPorEmail(Login login,String email) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM login WHERE email=?");
            ps.setString(1, login.getEmail());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                login.setEmail(rs.getString("email"));
                login.setPassword(rs.getString("password"));
                retorno = 1; // encontrado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = 0;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // UPDATE - Atualizar email/senha
    public int alterar(Login login, String antigoEmail) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement ps = con.prepareStatement("UPDATE login SET email=?, password=? WHERE email=?");
            ps.setString(1, login.getEmail());
            ps.setString(2, login.getPassword());
            ps.setString(3, antigoEmail);

            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = 0;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // DELETE - Excluir por email
    public int excluir(Login login) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement psm = con.prepareStatement("DELETE FROM login WHERE email=?");
            psm.setString(1, login.getEmail());

            int linhas = psm.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = 0;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }
}
