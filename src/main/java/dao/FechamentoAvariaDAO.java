package dao;

import conexao.Conexao;
import model.FechamentoAvaria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FechamentoAvariaDAO {

    // CREATE - Inserir FechamentoAvaria
    public int inserir(FechamentoAvaria fechamentoAvaria) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int idGerado = -1;

        String sql = "INSERT INTO FECHAMENTO_AVARIA (ID_FECHAMENTO, ID_AVARIA, QUANTIDADE) " +
                "VALUES (?, ?, ?) RETURNING ID";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, fechamentoAvaria.getIdFechamento());
            pst.setInt(2, fechamentoAvaria.getIdAvaria());
            pst.setInt(3, fechamentoAvaria.getQuantidade());

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    idGerado = rs.getInt("ID");
                    fechamentoAvaria.setId(idGerado); // atualiza o objeto com o ID gerado
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return idGerado;
    }

    // READ - Buscar por ID
    public FechamentoAvaria buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        FechamentoAvaria fechamentoAvaria = null;

        try {
            String sql = "SELECT * FROM FECHAMENTO_AVARIA WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                fechamentoAvaria = new FechamentoAvaria(
                        rs.getInt("ID"),
                        rs.getInt("ID_AVARIA"),
                        rs.getInt("ID_FECHAMENTO"),
                        rs.getInt("QUANTIDADE")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return fechamentoAvaria;
    }

    // READ - Buscar por ID_FECHAMENTO
    public List<FechamentoAvaria> buscarPorIdFechamento(int idFechamento) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<FechamentoAvaria> fechamentoAvarias = new ArrayList<>();

        try {
            String sql = "SELECT * FROM FECHAMENTO_AVARIA WHERE ID_FECHAMENTO = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idFechamento);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                fechamentoAvarias.add(new FechamentoAvaria(
                        rs.getInt("ID"),
                        rs.getInt("ID_AVARIA"),
                        rs.getInt("ID_FECHAMENTO"),
                        rs.getInt("QUANTIDADE")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return fechamentoAvarias;
    }

    // READ - Listar todos os FechamentoAvaria
    public List<FechamentoAvaria> listar() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<FechamentoAvaria> fechamentoAvarias = new ArrayList<>();

        try {
            String sql = "SELECT * FROM FECHAMENTO_AVARIA";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                fechamentoAvarias.add(new FechamentoAvaria(
                        rs.getInt("ID"),
                        rs.getInt("ID_AVARIA"),
                        rs.getInt("ID_FECHAMENTO"),
                        rs.getInt("QUANTIDADE")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return fechamentoAvarias;
    }

    // UPDATE - Atualizar FechamentoAvaria
    public int atualizar(FechamentoAvaria fechamentoAvaria) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "UPDATE FECHAMENTO_AVARIA SET QUANTIDADE = ?, ID_AVARIA = ?, ID_FECHAMENTO = ? WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, fechamentoAvaria.getQuantidade());
            pst.setInt(2, fechamentoAvaria.getIdAvaria());
            pst.setInt(3, fechamentoAvaria.getIdFechamento());
            pst.setInt(4, fechamentoAvaria.getId());

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

    // DELETE - Deletar FechamentoAvaria
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "DELETE FROM FECHAMENTO_AVARIA WHERE ID = ?";
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
