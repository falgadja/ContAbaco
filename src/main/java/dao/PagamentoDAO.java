package dao;

import conexao.Conexao;
import model.Pagamento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {
    // CREATE - Inserir PAGAMENTO

    public int inserir(Pagamento pagamento) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "INSERT INTO PAGAMENTO (TIPO_PAGTO, TOTAL, DATA_PAGTO, COMPROVANTE, ID_EMPRESA) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, pagamento.getTipoPagto());
            pst.setDouble(2, pagamento.getTotal());
            pst.setDate(3, Date.valueOf(pagamento.getData()));

            retorno = pst.executeUpdate();
            return retorno;
        } catch (SQLException e) {
            e.printStackTrace();
            return retorno;
        } finally {
            conexao.desconectar(conn);
        }
    }

    // READ - Buscar por ID

    public Pagamento buscarPorId(Pagamento pagamento) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Pagamento p = null;

        try {
            String sql = "SELECT * FROM PAGAMENTO WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, pagamento.getId());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                p = new Pagamento(rs.getInt("ID"),
                        rs.getString("TIPO_PAGTO"),
                        rs.getDouble("TOTAL"),
                        rs.getObject("DATA", java.time.LocalDate.class),
                        rs.getBytes("COMPROVANTE"),
                        rs.getInt("ID_EMPRESA"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return p;
    }

    // READ - Buscar por TIPO PAGAMENTO

    public List<Pagamento> buscarPorTipo(Pagamento pagamento, String nomeEmpresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Pagamento> lista = new ArrayList<>();

        try {
            String sql = "SELECT P.* FROM PAGAMENTO P " +
                    "JOIN EMPRESA E ON P.ID_EMPRESA = E.NOME " +
                    "WHERE TIPO_PAGTO = ? AND E.ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, pagamento.getTipoPagto());
            pst.setString(2, nomeEmpresa);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Pagamento p = new Pagamento(
                        rs.getInt("ID"),
                        rs.getString("TIPO_PAGTO"),
                        rs.getDouble("TOTAL"),
                        rs.getObject("DATA", java.time.LocalDate.class),
                        rs.getBytes("COMPROVANTE"),
                        rs.getInt("ID_EMPRESA")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return lista;
    }

    // READ - Buscar por DATA

    public List<Pagamento> buscarPorData(Pagamento pagamento, LocalDate data, String nomeEmpresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Pagamento> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PAGAMENTO P " +
                    "JOIN EMPRESA E ON P.ID_EMPRESA = E.ID " +
                    "WHERE DATA BETWEEN ? AND ? " +
                    "AND E.NOME = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, pagamento.getData());
            pst.setDate(2, Date.valueOf(data));
            pst.setString(3, nomeEmpresa);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Pagamento p = new Pagamento(
                        rs.getInt("ID"),
                        rs.getString("TIPO_PAGTO"),
                        rs.getDouble("TOTAL"),
                        rs.getObject("DATA", java.time.LocalDate.class),
                        rs.getBytes("COMPROVANTE"),
                        rs.getInt("ID_EMPRESA")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return lista;
    }

    // READ - Buscar por ID EMPRESA

    public Pagamento buscarPorEmpresa(Pagamento pagamento) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Pagamento p = null;

        try {
            String sql = "SELECT * FROM PAGAMENTO WHERE ID_EMPRESA = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, pagamento.getIdEmpresa());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) { // só retorna o primeiro que encontrar
                p = new Pagamento(
                        rs.getInt("ID"),
                        rs.getString("TIPO_PAGTO"),
                        rs.getDouble("TOTAL"),
                        rs.getObject("DATA", java.time.LocalDate.class),
                        rs.getBytes("COMPROVANTE"),
                        rs.getInt("ID_EMPRESA")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return p;
    }


    // READ - Listar todos os Pagamentos

    public List<Pagamento> listarTodos(Pagamento pagamento) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Pagamento> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PAGAMENTO WHERE ID_EMPRESA = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,pagamento.getIdEmpresa());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Pagamento p = new Pagamento(
                        rs.getInt("ID"),
                        rs.getString("TIPO_PAGTO"),
                        rs.getDouble("TOTAL"),
                        rs.getObject("DATA", java.time.LocalDate.class),
                        rs.getBytes("COMPROVANTE"),
                        rs.getInt("ID_EMPRESA")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return lista;
    }
}
