package dao;

import conexao.Conexao;
import model.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdmDAO {

    // CREATE - INSERIR ADMINISTRADOR
    public int inserir(Administrador adm) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int idGerado = -1;
        String sql = "INSERT INTO administrador (email, senha) VALUES (?, ?) RETURNING id";

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

        return idGerado; // Retorna o ID gerado ou -1 se falhar
    }

    // READ - BUSCAR ADMINISTRADOR PELO ID
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

        return adm; // retorna null se não encontrar
    }

    // READ - BUSCAR ADMINISTRADOR PELO EMAIL
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

    // READ - BUSCAR HASH DA SENHA PELO EMAIL
    public String buscarHashPorEmail(String email) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        String sql = "SELECT senha FROM administrador WHERE email = ?";
        String hash = null;

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                hash = rs.getString("senha");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar hash do administrador: " + e.getMessage());
        }

        return hash;
    }

    // READ - LISTAR TODOS OS ADMINISTRADORES
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

    // UPDATE - ATUALIZAR ADMINISTRADOR
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

        return retorno; // retorna número de linhas alteradas ou -1 em caso de erro
    }

    // DELETE - DELETAR ADMINISTRADOR
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

        return retorno; // retorna número de linhas deletadas ou -1 se falhar
    }
}
