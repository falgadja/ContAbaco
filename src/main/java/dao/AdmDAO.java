package dao;

import conexao.Conexao;
import model.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmDAO {

    // CREATE - Inserir Administrador
    public int inserir(Administrador adm) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int idGerado = -1;
        String sql = "INSERT INTO ADM (EMAIL, SENHA) VALUES (?, ?) RETURNING id";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, adm.getEmail());
            pst.setString(2, adm.getSenha());

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    idGerado = rs.getInt("id");
                    adm.setId(idGerado); // Atualiza o objeto com o ID gerado
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return idGerado;
    }

    // READ - Buscar por Email e Senha
    public Administrador buscarPorEmailSenha(String email, String senha) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Administrador adm = null;
        String sql = "SELECT * FROM ADM WHERE EMAIL = ? AND SENHA = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, email);
            pst.setString(2, senha);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    adm = new Administrador(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return adm;
    }

    // UPDATE - Atualizar Administrador
    public int atualizar(Administrador adm) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;
        String sql = "UPDATE ADM SET EMAIL = ?, SENHA = ? WHERE ID = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, adm.getEmail());
            pst.setString(2, adm.getSenha());
            pst.setInt(3, adm.getId());

            int linhas = pst.executeUpdate();
            if (linhas > 0) retorno = 1;

        } catch (SQLException e) {
            e.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // DELETE - Deletar Administrador
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;
        String sql = "DELETE FROM ADM WHERE ID = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            int linhas = pst.executeUpdate();
            if (linhas > 0) retorno = 1;

        } catch (SQLException e) {
            e.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }
}
