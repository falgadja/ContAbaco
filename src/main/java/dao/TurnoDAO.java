package dao;

import conexao.Conexao;
import model.Turno;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAO {

    // CREATE - Inserir TURNO
    public int inserir(Turno turno) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "INSERT INTO TURNO (TEMPO_DURACAO, QTD_FUNCIONARIOS, DATA) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setTime(1, Time.valueOf(turno.getTempoDuracao()));
            pst.setInt(2, turno.getQtdFuncionarios());
            pst.setDate(3, Date.valueOf(turno.getData()));

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

    // READ - Buscar TURNO por ID
    public Turno buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Turno turno = null;

        try {
            String sql = "SELECT * FROM TURNO WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                turno = new Turno(
                        rs.getInt("ID"),
                        rs.getObject("TEMPO_DURACAO", LocalTime.class),
                        rs.getInt("QTD_FUNCIONARIOS"),
                        rs.getObject("DATA", LocalDate.class)
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return turno;
    }

    // READ - Buscar TURNO por DATA e EMPRESA
    public List<Turno> buscarPorData(LocalDate dataInicio, LocalDate dataFim, String nomeEmpresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Turno> turnos = new ArrayList<>();

        try {
            String sql = "SELECT T.* " +
                    "FROM TURNO T " +
                    "JOIN FUNCIONARIO_TURNO FT ON T.ID = FT.ID_TURNO " +
                    "JOIN FUNCIONARIO F ON FT.ID_FUNCIONARIO = F.ID " +
                    "JOIN EMPRESA E ON F.ID_EMPRESA = E.ID " +
                    "WHERE T.DATA BETWEEN ? AND ? AND E.NOME = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(dataInicio));
            pst.setDate(2, Date.valueOf(dataFim));
            pst.setString(3, nomeEmpresa);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                turnos.add(new Turno(
                        rs.getInt("ID"),
                        rs.getObject("TEMPO_DURACAO", LocalTime.class),
                        rs.getInt("QTD_FUNCIONARIOS"),
                        rs.getObject("DATA", LocalDate.class)
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return turnos;
    }

    // READ - Listar todos os TURNOS
    public List<Turno> listarTodos() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Turno> turnos = new ArrayList<>();

        try {
            String sql = "SELECT * FROM TURNO";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                turnos.add(new Turno(
                        rs.getInt("ID"),
                        rs.getObject("TEMPO_DURACAO", LocalTime.class),
                        rs.getInt("QTD_FUNCIONARIOS"),
                        rs.getObject("DATA", LocalDate.class)
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return turnos;
    }

    // UPDATE - Atualizar TURNO
    public int atualizar(Turno turno) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "UPDATE TURNO SET QTD_FUNCIONARIOS = ?, TEMPO_DURACAO = ?, DATA = ? WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, turno.getQtdFuncionarios());
            pst.setTime(2, Time.valueOf(turno.getTempoDuracao()));
            pst.setDate(3, Date.valueOf(turno.getData()));
            pst.setInt(4, turno.getId());

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

    // DELETE - Deletar TURNO
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "DELETE FROM TURNO WHERE ID = ?";
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
