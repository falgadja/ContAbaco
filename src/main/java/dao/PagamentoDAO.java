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
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pagamento.getId());
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                p = new Pagamento(rset.getInt("ID"),
                        rset.getString("TIPO_PAGTO"),
                        rset.getDouble("TOTAL"),
                        rset.getObject("DATA", java.time.LocalDate.class),
                        rset.getBytes("COMPROVANTE"),
                        rset.getInt("ID_EMPRESA"));
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
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pagamento.getTipoPagto());
            pstmt.setString(2, nomeEmpresa);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                Pagamento p = new Pagamento(
                        rset.getInt("ID"),
                        rset.getString("TIPO_PAGTO"),
                        rset.getDouble("TOTAL"),
                        rset.getObject("DATA", java.time.LocalDate.class),
                        rset.getBytes("COMPROVANTE"),
                        rset.getInt("ID_EMPRESA")
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
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, pagamento.getData());
            pstmt.setDate(2, Date.valueOf(data));
            pstmt.setString(3, nomeEmpresa);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                Pagamento p = new Pagamento(
                        rset.getInt("ID"),
                        rset.getString("TIPO_PAGTO"),
                        rset.getDouble("TOTAL"),
                        rset.getObject("DATA", java.time.LocalDate.class),
                        rset.getBytes("COMPROVANTE"),
                        rset.getInt("ID_EMPRESA")
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
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pagamento.getIdEmpresa());
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) { // só retorna o primeiro que encontrar
                p = new Pagamento(
                        rset.getInt("ID"),
                        rset.getString("TIPO_PAGTO"),
                        rset.getDouble("TOTAL"),
                        rset.getObject("DATA", java.time.LocalDate.class),
                        rset.getBytes("COMPROVANTE"),
                        rset.getInt("ID_EMPRESA")
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
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,pagamento.getIdEmpresa());
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                Pagamento p = new Pagamento(
                        rset.getInt("ID"),
                        rset.getString("TIPO_PAGTO"),
                        rset.getDouble("TOTAL"),
                        rset.getObject("DATA", java.time.LocalDate.class),
                        rset.getBytes("COMPROVANTE"),
                        rset.getInt("ID_EMPRESA")
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

    // DELETE - Excluir por ID
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "DELETE FROM pagamento WHERE id = ?"
            );
            pst.setInt(1, id);
            int linhas = pst.executeUpdate();

            if (linhas > 0) {
                retorno = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = 0;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // UPDATE - Atualizar tipo e valor
    public int atualizar(Pagamento pagamento) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "UPDATE pagamento SET tipo=?, total=?, data=? WHERE id=?"
            );
            pst.setString(1, pagamento.getTipo());
            pst.setDouble(2, pagamento.getTotal());
            pst.setTimestamp(3, java.sql.Timestamp.valueOf(pagamento.getData().atStartOfDay()));
            pst.setInt(4, pagamento.getId());

            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = 0;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }
}
