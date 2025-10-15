package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import model.Turno;

public class TurnoDAO {
    // CREATE - Inserir TURNO

    public int inserir(Turno turno) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Turno t = null;
        int retorno = 0;

        try {
            String sql = "INSERT INTO turno (TEMPO_DURACAO, QTD_FUNCIONARIOS, DATA) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setTime(1, Time.valueOf(turno.getTempoDuracao()));
            pst.setInt(2, turno.getQtdFuncionarios());
            pst.setDate(3, Date.valueOf(turno.getData()));

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

    // READ - Buscar Turno por ID

    public Turno buscarPorID(Turno turno) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Turno t = null;

        try {
            String sql = "SELECT * FROM TURNO WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, turno.getId());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                t = new Turno(
                        rs.getInt("ID"),
                        rs.getObject("TEMPO_DURACAO", java.time.LocalTime.class),
                        rs.getInt("QTD_FUNCIONARIOS"),
                        rs.getObject("DATA", java.time.LocalDate.class)
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
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(turno.getData()));
            pst.setDate(2, Date.valueOf(dataExtra));
            pst.setString(3, nomeEmpresa);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                t = new Turno(
                        rs.getInt("ID"),
                        rs.getObject("TEMPO_DURACAO", java.time.LocalTime.class),
                        rs.getInt("QTD_FUNCIONARIOS"),
                        rs.getObject("DATA", java.time.LocalDate.class)
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
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, turno.getId());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Turno t = new Turno(
                        rs.getInt("ID"),
                        rs.getObject("TEMPO_DURACAO", java.time.LocalTime.class),
                        rs.getInt("QTD_FUNCIONARIOS"),
                        rs.getObject("DATA", java.time.LocalDate.class)
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
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, turno.getQtdFuncionarios());
            pst.setTime(2, Time.valueOf(turno.getTempoDuracao()));
            pst.setDate(3,Date.valueOf(turno.getData()));
            pst.setInt(4, turno.getId());

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

    // DELETE - Deletar TURNO

    public int deletar(Turno turno) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "DELETE FROM TURNO WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, turno.getId());

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
