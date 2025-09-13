package dao;

import model.Pagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PagamentoDAO {

    public PagamentoDAO() {
    }

    // CREATE - Inserir pagamento
    public int inserir(Pagamento pagamento) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO pagamento (tipo, total, data) VALUES (?, ?, ?)"
            );
            pst.setString(1, pagamento.getTipo());
            pst.setDouble(2, pagamento.getTotal());
            pst.setTimestamp(3, java.sql.Timestamp.valueOf(pagamento.getData().atStartOfDay()));

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

    // READ - Buscar por ID
    public int buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "SELECT * FROM pagamento WHERE id = ?"
            );
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                retorno = 1; // encontrado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = 0;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // READ - Buscar por Data
    public int buscarPorData(Pagamento pagamento) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "SELECT * FROM pagamento WHERE data = ?"
            );
            pst.setTimestamp(1, java.sql.Timestamp.valueOf(pagamento.getData().atStartOfDay()));

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                retorno = 1; // encontrado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = 0;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
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
