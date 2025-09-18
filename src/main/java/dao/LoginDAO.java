package dao;

import conexao.Conexao;
import model.Login;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO {
    // CONEXÃO COM O BANCO
    private Connection connection;

    public LoginDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE - Inserir Login
    public int inserir(Login login) throws SQLException {

        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;
        if(login.validarEmail(login.getEmail())==true && login.validarSenha(login.getSenha())==true) {
            try {
                PreparedStatement pst = con.prepareStatement(
                        "INSERT INTO login (email, senha) VALUES (?, ?)"
                );
                pst.setString(1, login.getEmail());
                pst.setString(2, login.getSenha());

                int linhas = pst.executeUpdate();
                if (linhas > 0) {
                    retorno = 1;
                    return retorno;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                retorno= -1;
            } finally {
                conexao.desconectar(con);
            }
        }
        else {
            retorno = 0;
        }
        return retorno;
    }

    // READ - Buscar por ID
    public Login buscarPorId(int id) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Login login = null;

        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM login WHERE id = ?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                login = new Login(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return login;
    }

    // READ - Buscar por Email
    public Login buscarPorEmail(String email) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Login login = null;

        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM login WHERE email ILIKE ?");
            pst.setString(1, "%" + email + "%");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                login = new Login(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return login;
    }

    // READ - Listar todos
    public List<Login> listarTodos() throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Login> logins = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM login");

            while (rs.next()) {
                logins.add(new Login(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("senha")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return logins;
    }

    // UPDATE - Alterar Login
    public int atualizar(Login login) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "UPDATE login SET email = ?, senha = ? WHERE id = ?"
            );
            pst.setString(1, login.getEmail());
            pst.setString(2, login.getSenha());
            pst.setInt(3, login.getId());

            retorno = pst.executeUpdate();
            if (retorno > 0) {
                retorno = 1;
                return retorno;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return retorno;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // DELETE - Deletar Login
    public int deletar(int id) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int deletado = 0;

        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM login WHERE id = ?");
            pst.setInt(1, id);
            deletado = pst.executeUpdate();
            if (deletado > 0) {
                deletado = 1;
                return deletado;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return deletado;
        } finally {
            conexao.desconectar(con);
        }

        return deletado;
    }
}
