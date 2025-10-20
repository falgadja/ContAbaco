package dao;

import conexao.Conexao;
import model.Pagamento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {

    // CREATE - INSERIR PAGAMENTO
    public int inserir(Pagamento pagamento) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = -1;

        try {
            String sql = "INSERT INTO PAGAMENTO (TIPO_PAGTO, TOTAL, DATA_PAGTO, COMPROVANTE, ID_EMPRESA) VALUES (?, ?, ?, ?, ?) RETURNING ID";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, pagamento.getTipoPagto());
            pst.setDouble(2, pagamento.getTotal());
            pst.setDate(3, Date.valueOf(pagamento.getData()));
            pst.setBytes(4, pagamento.getComprovante());
            pst.setInt(5, pagamento.getIdEmpresa());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                retorno = rs.getInt("ID");
                pagamento.setId(retorno);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return retorno;
    }

    // READ - BUSCAR PAGAMENTO PELO ID
    public Pagamento buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Pagamento pagamento = null;

        try {
            String sql = "SELECT * FROM PAGAMENTO WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                pagamento = new Pagamento(
                        rs.getInt("ID"),
                        rs.getString("TIPO_PAGTO"),
                        rs.getDouble("TOTAL"),
                        rs.getObject("DATA_PAGTO", LocalDate.class),
                        rs.getBytes("COMPROVANTE"),
                        rs.getInt("ID_EMPRESA")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return pagamento;
    }

    // READ - BUSCAR POR TIPO DE PAGAMENTO
    public List<Pagamento> buscarPorTipo(String tipoPagto, int idEmpresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Pagamento> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PAGAMENTO WHERE TIPO_PAGTO = ? AND ID_EMPRESA = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tipoPagto);
            pst.setInt(2, idEmpresa);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                lista.add(new Pagamento(
                        rs.getInt("ID"),
                        rs.getString("TIPO_PAGTO"),
                        rs.getDouble("TOTAL"),
                        rs.getObject("DATA_PAGTO", LocalDate.class),
                        rs.getBytes("COMPROVANTE"),
                        rs.getInt("ID_EMPRESA")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return lista;
    }

    // READ - BUSCAR POR DATA
    public List<Pagamento> buscarPorData(LocalDate dataInicio, LocalDate dataFim, int idEmpresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Pagamento> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PAGAMENTO WHERE DATA_PAGTO BETWEEN ? AND ? AND ID_EMPRESA = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(dataInicio));
            pst.setDate(2, Date.valueOf(dataFim));
            pst.setInt(3, idEmpresa);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                lista.add(new Pagamento(
                        rs.getInt("ID"),
                        rs.getString("TIPO_PAGTO"),
                        rs.getDouble("TOTAL"),
                        rs.getObject("DATA_PAGTO", LocalDate.class),
                        rs.getBytes("COMPROVANTE"),
                        rs.getInt("ID_EMPRESA")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return lista;
    }

    // READ - BUSCAR POR EMPRESA
    public List<Pagamento> listarPorEmpresa(int idEmpresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Pagamento> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PAGAMENTO WHERE ID_EMPRESA = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idEmpresa);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                lista.add(new Pagamento(
                        rs.getInt("ID"),
                        rs.getString("TIPO_PAGTO"),
                        rs.getDouble("TOTAL"),
                        rs.getObject("DATA_PAGTO", LocalDate.class),
                        rs.getBytes("COMPROVANTE"),
                        rs.getInt("ID_EMPRESA")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return lista;
    }

    // READ - LISTAR TODOS OS PAGAMENTOS
    public List<Pagamento> listar() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Pagamento> pagamentos= new ArrayList<>();

        try {
            String sql = "SELECT * FROM PAGAMENTO";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                pagamentos.add(new Pagamento(
                        rs.getInt("ID"),
                        rs.getString("TIPO_PAGTO"),
                        rs.getDouble("TOTAL"),
                        rs.getObject("DATA_PAGTO", LocalDate.class),
                        rs.getBytes("COMPROVANTE"),
                        rs.getInt("ID_EMPRESA")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return pagamentos;
    }

    // UPDATE - ATUALIZAR PAGAMENTO
    public int atualizar(Pagamento pagamento) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "UPDATE PAGAMENTO SET TIPO_PAGTO = ?, TOTAL = ?, DATA_PAGTO = ?, COMPROVANTE = ? WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, pagamento.getTipoPagto());
            pst.setDouble(2, pagamento.getTotal());
            pst.setDate(3, Date.valueOf(pagamento.getData()));
            pst.setBytes(4, pagamento.getComprovante());
            pst.setInt(5, pagamento.getId());

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

    // DELETE - DELETAR PAGAMENTO
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "DELETE FROM PAGAMENTO WHERE ID = ?";
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
