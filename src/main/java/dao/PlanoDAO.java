package dao;

import conexao.Conexao;
import model.Plano;
import model.Setor;

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
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, plano.getId());
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                p = new Plano(
                        rset.getInt("ID"),
                        rset.getString("NOME"),
                        rset.getDouble("PRECO")
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
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, plano.getNome());
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                p = new Plano(
                        rset.getInt("ID"),
                        rset.getString("NOME"),
                        rset.getDouble("PRECO")
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
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, plano.getPreco());
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                p = new Plano(
                        rset.getInt("ID"),
                        rset.getString("NOME"),
                        rset.getDouble("PRECO")
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
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                Plano p = new Plano(
                        rset.getInt("ID"),
                        rset.getString("NOME"),
                        rset.getDouble("PRECO")
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
}
