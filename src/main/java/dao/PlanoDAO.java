package dao;

import conexao.Conexao;
import model.Plano;
import model.Setor;
import model.Turno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanoDAO {
    // READ - Buscar por ID

    public Plano buscarPorId(Plano plano) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Plano p = null;

        try {
            String sql = "SELECT * FROM PLANO WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, plano.getId());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                p = new Plano(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("PRECO")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return p;
    }

    // READ - Buscar por NOME

    public Plano buscarPorNome(Plano plano) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Plano p = null;

        try {
            String sql = "SELECT * FROM PLANO WHERE NOME = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, plano.getNome());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                p = new Plano(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("PRECO")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return p;
    }

    // READ - Buscar por PRECO

    public Plano buscarPorPreco(Plano plano) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Plano p = null;

        try {
            String sql = "SELECT * FROM PLANO WHERE PRECO = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDouble(1, plano.getPreco());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                p = new Plano(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("PRECO")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return p;
    }

    // READ - Listar todos os Planos

    public List<Plano> listarTodos() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Plano> planos = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PLANO ";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Plano p = new Plano(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("PRECO")
                );
                planos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return planos;
    }

    // UPDATE - Atualizar PLANO

    public int atualizar(Plano plano) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "UPDATE PLANO SET NOME=?, PRECO=? WHERE ID=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, plano.getNome());
            pst.setDouble(2, plano.getPreco());

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

    // DELETE - Apagar PLANO

    public int deletar(Plano plano) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "DELETE FROM PLANO WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, plano.getId());

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
