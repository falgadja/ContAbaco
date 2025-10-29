package dao;

import conexao.Conexao;
import model.Setor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SetorDAO {

    // CREATE - Inserir SETOR
    public int inserir(Setor setor) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "INSERT INTO SETOR (NOME) VALUES (?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, setor.getNome());

            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(conn);
        }

        return retorno;
    }

    // READ - Buscar SETOR por ID
    public Setor buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Setor setor = null;

        try {
            String sql = "SELECT * FROM SETOR WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                setor = new Setor(
                        rs.getInt("ID"),
                        rs.getString("NOME")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return setor;
    }

    // READ - Listar todos os SETORES
    public List<Setor> listarTodos() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Setor> setores = new ArrayList<>();

        try {
            String sql = "SELECT * FROM SETOR";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                setores.add(new Setor(
                        rs.getInt("ID"),
                        rs.getString("NOME")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return setores;
    }

    // UPDATE - Atualizar NOME do SETOR
    public int atualizar(int id, String novoNome) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "UPDATE SETOR SET NOME = ? WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, novoNome);
            pst.setInt(2, id);

            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(conn);
        }

        return retorno;
    }

    // DELETE - Deletar SETOR
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "DELETE FROM SETOR WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);

            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(conn);
        }

        return retorno;
    }
}
