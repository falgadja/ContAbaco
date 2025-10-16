package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import model.Setor;

public class SetorDAO {
    // CREATE - Inserir SETOR

    public int inserir(Setor setor) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "INSERT INTO SETOR (NOME) VALUES (?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, setor.getNome());

            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }
        return retorno;
    }

    // READ - Buscar Setor por ID

    public Setor buscarPorID(Setor setor) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Setor s = null;

        try {
            String sql = "SELECT * FROM SETOR WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, setor.getId());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                s = new Setor(
                        rs.getInt("ID"),
                        rs.getString("NOME")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return s;
    }

    // READ - Buscar por NOME

    public Setor buscarPorNome(Setor setor, String nomeEmpresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Setor s = null;

        try {
             String sql = "SELECT S.* " +
                     "FROM SETOR S " +
                     "JOIN FUNCIONARIO F ON S.ID = F.ID_SETOR " +
                     "JOIN EMPRESA E ON F.ID_EMPRESA = E.ID " +
                     "WHERE S.NOME = ? AND E.NOME = ?";
             PreparedStatement pst = conn.prepareStatement(sql);
             pst.setString(1, setor.getNome());
             pst.setString(2,nomeEmpresa);
             ResultSet rs = pst.executeQuery();

             while (rs.next()) {
                 s = new Setor(
                         rs.getInt("ID"),
                         rs.getString("NOME")
                 );
             }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return s;
    }

    // READ - Listar todos os Setores

    public List<Setor> listarTodos(Setor setor) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Setor> setores = new ArrayList<>();

        try {
            String sql = "SELECT S.* FROM SETOR S " +
                    "JOIN FUNCIONARIO F ON S.ID = F.ID_SETOR " +
                    "JOIN EMPRESA E ON F.ID_EMPRESA = E.ID " +
                    "WHERE S.ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, setor.getId());
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {
                Setor s = new Setor(
                        rs.getInt("ID"),
                        rs.getString("NOME")
                );
                setores.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return setores;
    }

    // UPDATE - Alterar NOME

    public int atualizar(Setor setor, String nomeSetor) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "UPDATE SETOR SET NOME = ? WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nomeSetor);
            pst.setInt(2, setor.getId());

            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(conn);
        }
        return retorno;
    }

    // DELETE - Deletar SETOR

    public int deletar(Setor setor) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "DELETE FROM SETOR WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, setor.getId());

            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(conn);
        }
        return retorno;
    }
}
