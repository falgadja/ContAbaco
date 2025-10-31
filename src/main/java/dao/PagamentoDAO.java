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
        int idGerado = -1;

        try {
            String sql = "INSERT INTO PAGAMENTO (TIPO_PAGTO, TOTAL, DATA_PAGTO, ID_EMPRESA) VALUES (?, ?, ?, ?) RETURNING ID";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, pagamento.getTipoPagto());
            pst.setDouble(2, pagamento.getTotal());
            pst.setDate(3, Date.valueOf(pagamento.getData()));
            pst.setInt(4, pagamento.getIdEmpresa());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idGerado = rs.getInt("ID");
                pagamento.setId(idGerado);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return idGerado;
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
        int retorno;

        try {
            String sql = "UPDATE PAGAMENTO SET TIPO_PAGTO = ?, TOTAL = ?, DATA_PAGTO = ? WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, pagamento.getTipoPagto());
            pst.setDouble(2, pagamento.getTotal());
            pst.setDate(3, Date.valueOf(pagamento.getData()));
            pst.setInt(4, pagamento.getId());

            retorno = pst.executeUpdate();

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
        int retorno;

        try {
            String sql = "DELETE FROM PAGAMENTO WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);

            retorno = pst.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(conn);
        }

        return retorno;
    }
}
