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
        String sql = "INSERT INTO adm (email, senha) VALUES (?, ?) RETURNING id";
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

    // READ - BUSCAR Administrador PELO EMAIL
    public Administrador buscarPorEmail(String email) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Administrador adm = null;
        String sql = "SELECT * FROM adm WHERE email = ?";

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

        return adm; // se não encontrar retorna null, se encontrar retorna o objeto
    }

    // READ - BUSCAR Administrador PELO EMAIL E SENHA
    public Administrador buscarPorEmailSenha(String email, String senha) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Administrador adm = null;
        String sql = "SELECT * FROM administrador WHERE email = ? AND senha = ?";


        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, senha);

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

        return adm; // se não encontrar retorna null, se encontrar retorna o objeto
    }

    // READ - LISTAR TODOS OS Administradores
    public List<Administrador> listar() {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Administrador> adms = new ArrayList<>();
        String sql = "SELECT * FROM adm";

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

        return adms; // retorna a lista, vazia se não encontrar nada
    }

    // UPDATE - ATUALIZAR Administrador
    public int atualizar(Administrador adm) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno;
        String sql = "UPDATE adm SET email=?, senha=? WHERE id=?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, adm.getEmail());
            pst.setString(2, adm.getSenha());
            pst.setInt(3, adm.getId());

            retorno = pst.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1; // Se ocorreu alguma exceção retorna -1
        } finally {
            conexao.desconectar(con);
        }

        return retorno; // Se tiver atualizado o Administrador, retorna o numero de linhas alteradas, se não retorna 0
    }

    // DELETE - DELETAR Administrador
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno;
        String sql = "DELETE FROM adm WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);

            retorno = pst.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1; // Se ocorreu alguma exceção retorna -1
        } finally {
            conexao.desconectar(con);
        }

        return retorno; // Se tiver deletado o Administrador, retorna o numero de linhas alteradas, se não retorna 0
    }
}
