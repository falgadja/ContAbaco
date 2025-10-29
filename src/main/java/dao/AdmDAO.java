package dao;

// IMPORTS DA CLASSE
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import model.Administrador;

public class AdmDAO {

    // CREATE - INSERIR Administrador
    public int inserir(Administrador adm) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        String sql = "INSERT INTO administrador (email, senha) VALUES (?, ?) RETURNING id";
        int idGerado = -1;

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, adm.getEmail());
            pst.setString(2, adm.getSenha());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idGerado = rs.getInt("id");
                adm.setId(idGerado);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return idGerado; // Retorna o ID gerado se der certo, se falhar retorna -1
    }

    // READ - BUSCAR Administrador PELO ID
    public Administrador buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Administrador adm = null;
        String sql = "SELECT * FROM administrador WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                adm = new Administrador(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return adm; // se não encontrar retorna null
    }
    // READ - Buscar e retornar o HASH
    public String buscarHashPorEmail(String email) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        String sql = "SELECT senha FROM administrador WHERE email = ?";
        String hash = null;

        try (
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                hash = rs.getString("senha");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar hash do administrador: " + e.getMessage());
        }

        return hash;
    }

    // READ - BUSCAR Administrador PELO EMAIL
    public Administrador buscarPorEmail(String email) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Administrador adm = null;
        String sql = "SELECT * FROM administrador WHERE email = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                adm = new Administrador(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return adm;
    }

    // READ - LISTAR TODOS OS Administradores
    public List<Administrador> listar() {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Administrador> adms = new ArrayList<>();
        String sql = "SELECT * FROM administrador";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                adms.add(new Administrador(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("senha")
                ));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return adms;
    }

    // UPDATE - ATUALIZAR Administrador
    public int atualizar(Administrador adm) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno;
        String sql = "UPDATE administrador SET email = ?, senha = ? WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, adm.getEmail());
            pst.setString(2, adm.getSenha());
            pst.setInt(3, adm.getId());

            retorno = pst.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // DELETE - DELETAR Administrador
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno;
        String sql = "DELETE FROM administrador WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            retorno = pst.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }
}
