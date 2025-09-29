package dao;

import conexao.Conexao;
import model.Turno;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAO {
    // CREATE - Inserir TURNO

    public int inserir(Turno turno) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Turno t = null;
        int retorno = 0;

        try {
            String sql = "INSERT INTO turno (TEMPO_DURACAO, QTD_FUNCIONARIOS, DATA) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setTime(1, Time.valueOf(turno.getTempoDuracao()));
            pstmt.setInt(2, turno.getQtdFuncionarios());
            pstmt.setDate(3, Date.valueOf(turno.getData()));

            int linhas = pstmt.executeUpdate();
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

    // READ - Buscar Turno por ID

    public Turno buscarPorID(Turno turno) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Turno t = null;

        try {
            String sql = "SELECT * FROM TURNO WHERE ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, turno.getId());
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                t = new Turno(
                        rset.getInt("ID"),
                        rset.getObject("TEMPO_DURACAO", java.time.LocalTime.class),
                        rset.getInt("QTD_FUNCIONARIOS"),
                        rset.getObject("DATA", java.time.LocalDate.class)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return t;
    }

    // READ - Buscar por DATA

    public Turno buscarPorData(Turno turno, LocalDate dataExtra, String nomeEmpresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Turno t = null;

        try {
            String sql = "SELECT T.*, F.NOME FROM TURNO T " +
                    "JOIN FUNCIONARIO_TURNO FT ON T.ID = FT.ID_TURNO " +
                    "JOIN FUNCIONARIO F ON FT.ID_FUNCIONARIO = F.ID " +
                    "JOIN EMPRESA E ON F.ID_EMPRESA = E.ID " +
                    "WHERE DATA BETWEEN ? AND ? " +
                    "AND E.NOME = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(turno.getData()));
            pstmt.setDate(2, Date.valueOf(dataExtra));
            pstmt.setString(3, nomeEmpresa);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                t = new Turno(
                        rset.getInt("ID"),
                        rset.getObject("TEMPO_DURACAO", java.time.LocalTime.class),
                        rset.getInt("QTD_FUNCIONARIOS"),
                        rset.getObject("DATA", java.time.LocalDate.class)
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return t;
    }

    // READ - Listar todos os Turnos

    public List<Turno> listarTodos(Turno turno) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Turno> turnos = new ArrayList<>();

        try {
            String sql = "SELECT T.*, F.NOME FROM TURNO T " +
                    "JOIN FUNCIONARIO_TURNO FT ON T.ID = FT.ID_TURNO " +
                    "JOIN FUNCIONARIO F ON FT.ID_FUNCIONARIO = F.ID " +
                    "JOIN EMPRESA E ON F.ID_EMPRESA = E.ID " +
                    "WHERE T.ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, turno.getId());
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                Turno t = new Turno(
                        rset.getInt("ID"),
                        rset.getObject("TEMPO_DURACAO", java.time.LocalTime.class),
                        rset.getInt("QTD_FUNCIONARIOS"),
                        rset.getObject("DATA", java.time.LocalDate.class)
                );
                turnos.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return turnos;
    }

    // UPDATE - Alterar TURNO

    public int atualizar(Turno turno) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "UPDATE TURNO SET QTD_FUNICIONARIOS = ?, TEMPO_DURACAO = ?, DATA = ?  WHERE ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, turno.getQtdFuncionarios());
            pstmt.setTime(2, Time.valueOf(turno.getTempoDuracao()));
            pstmt.setDate(3,Date.valueOf(turno.getData()));
            pstmt.setInt(4, turno.getId());

            int linhas = pstmt.executeUpdate();
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

    // DELETE - Deletar TURNO

    public int deletar(Turno turno) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "DELETE FROM TURNO WHERE ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, turno.getId());

            int linhas = pstmt.executeUpdate();
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
