package dao;

import conexao.Conexao;
import model.FechamentoAvaria;
import model.Funcionario;

import java.sql.*;
import java.util.List;

public class FechamentoAvariaDAO {

    // CREATE - Inserir a tabela de ligação entre FECHAMENTO de TURNO e AVARIA
    public int inserir(FechamentoAvaria fechamentoAvaria) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO FECHAMENTO_AVARIA(id, id_fechamento, id_avaria, quantidade) VALUES (?, ?, ?, ?)"
            );
            pst.setInt(1, fechamentoAvaria.getId());
            pst.setInt(2, fechamentoAvaria.getIdFechamento());
            pst.setInt(3, fechamentoAvaria.getIdAvaria());
            pst.setInt(4, fechamentoAvaria.getQuantidade());
            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
                return retorno;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // READ - Buscar por ID
    public FechamentoAvaria buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        FechamentoAvaria fechamentoAvaria = null;

        try {
            String sql = "SELECT * FROM FECHAMENTO_AVARIA WHERE ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                fechamentoAvaria = new FechamentoAvaria(
                        rset.getInt("ID"),
                        rset.getInt("id_avaria"),
                        rset.getInt("id_fechamento"),
                        rset.getInt("quantidade"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return fechamentoAvaria;
    }

    // READ - Buscar por ID do FECHAMENTO de TURNO
    public List<FechamentoAvaria> buscarPorIdFechamentoTurno(int idFechamentoTurno) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<FechamentoAvaria> fechamentoAvarias = null;

        try {
            String sql = "SELECT F.* FROM FECHAMENTO_AVARIA F JOIN FECHAMENTO_TURNO FT ON FT.ID=F.ID_FECHAMENTO WHERE ID_FECHAMENTO = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idFechamentoTurno);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                fechamentoAvarias.add(new FechamentoAvaria(
                        rset.getInt("ID"),
                        rset.getInt("id_avaria"),
                        rset.getInt("id_fechamento"),
                        rset.getInt("quantidade")));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return fechamentoAvarias;
    }

    // READ - Listar FECHAMENTO de TURNO
    public List<FechamentoAvaria> listar() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<FechamentoAvaria> fechamentoAvarias = null;

        try {
            String sql = "SELECT * FROM FECHAMENTO_AVARIA";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                fechamentoAvarias.add(new FechamentoAvaria(
                        rset.getInt("ID"),
                        rset.getInt("id_avaria"),
                        rset.getInt("id_fechamento"),
                        rset.getInt("quantidade")));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return fechamentoAvarias;
    }

    // DELETE - Deletar Funcionario
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int deletado = 0;

        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM FECHAMENTO_AVARIA WHERE id = ?");
            pst.setInt(1, id);

            deletado = pst.executeUpdate();
            if (deletado > 0) {
                deletado = 1;
                return deletado;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            conexao.desconectar(con);
        }

        return deletado;
    }
}