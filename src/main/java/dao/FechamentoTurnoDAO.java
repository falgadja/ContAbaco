package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import model.FechamentoTurno;

public class FechamentoTurnoDAO {

    // CREATE - INSERIR FECHAMENTO DE TURNO
    public int inserir(FechamentoTurno fechamentoTurno) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int idGerado = -1;
        String sql = "INSERT INTO FECHAMENTO_TURNO(lote, id_funcionario, data, id_leitura) " +
                "VALUES (?, ?, ?, ?) RETURNING id";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, fechamentoTurno.getLote());
            pst.setInt(2, fechamentoTurno.getIdFuncionario());
            pst.setTimestamp(3, Timestamp.valueOf(fechamentoTurno.getData().atStartOfDay()));
            pst.setInt(4, fechamentoTurno.getIdLeitura());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idGerado = rs.getInt("id");
                fechamentoTurno.setId(idGerado);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return idGerado; // Retorna ID gerado ou -1 se falhar
    }

    // READ - SELECIONAR FECHAMENTO DE TURNO PELO ID
    public FechamentoTurno selecionarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        FechamentoTurno fechamentoTurno = null;
        String sql = "SELECT * FROM FECHAMENTO_TURNO WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                fechamentoTurno = new FechamentoTurno(
                        rs.getInt("id"),
                        rs.getInt("id_leitura"),
                        rs.getTimestamp("data").toLocalDateTime().toLocalDate(),
                        rs.getInt("lote"),
                        rs.getInt("id_funcionario")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return fechamentoTurno;
    }

    // READ - SELECIONAR FECHAMENTOS DE TURNO POR PERÍODO E EMPRESA
    public List<FechamentoTurno> selecionarPorPeriodoEempresa(Date dataInicio, Date dataFim, int idEmpresa) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<FechamentoTurno> fechamentosTurnos = new ArrayList<>();
        String sql = "SELECT ft.* FROM FECHAMENTO_TURNO ft " +
                "JOIN funcionario f ON f.id = ft.id_funcionario " +
                "JOIN empresa e ON e.id = f.id_empresa " +
                "WHERE ft.data BETWEEN ? AND ? AND e.id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, dataInicio);
            pst.setDate(2, dataFim);
            pst.setInt(3, idEmpresa);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                fechamentosTurnos.add(new FechamentoTurno(
                        rs.getInt("id"),
                        rs.getInt("id_leitura"),
                        rs.getTimestamp("data").toLocalDateTime().toLocalDate(),
                        rs.getInt("lote"),
                        rs.getInt("id_funcionario")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return fechamentosTurnos;
    }

    // READ - SELECIONAR FECHAMENTOS DE TURNO PELO ID DO FUNCIONARIO
    public List<FechamentoTurno> selecionarPorIdFuncionario(int idFuncionario) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<FechamentoTurno> fechamentosTurnos = new ArrayList<>();
        String sql = "SELECT * FROM FECHAMENTO_TURNO WHERE id_funcionario = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, idFuncionario);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                fechamentosTurnos.add(new FechamentoTurno(
                        rs.getInt("id"),
                        rs.getInt("id_leitura"),
                        rs.getTimestamp("data").toLocalDateTime().toLocalDate(),
                        rs.getInt("lote"),
                        rs.getInt("id_funcionario")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return fechamentosTurnos;
    }

    // READ - SELECIONAR FECHAMENTO DE TURNO PELO LOTE
    public FechamentoTurno selecionarPorLote(int lote) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        FechamentoTurno fechamentoTurno = null;
        String sql = "SELECT * FROM FECHAMENTO_TURNO WHERE lote = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, lote);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                fechamentoTurno = new FechamentoTurno(
                        rs.getInt("id"),
                        rs.getInt("id_leitura"),
                        rs.getTimestamp("data").toLocalDateTime().toLocalDate(),
                        rs.getInt("lote"),
                        rs.getInt("id_funcionario")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return fechamentoTurno;
    }

    // READ - SELECIONAR FECHAMENTOS DE TURNO POR EMPRESA
    public List<FechamentoTurno> selecionarPorEmpresa(int idEmpresa) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<FechamentoTurno> fechamentosTurnos = new ArrayList<>();
        String sql = "SELECT ft.* FROM FECHAMENTO_TURNO ft " +
                "JOIN funcionario f ON f.id = ft.id_funcionario " +
                "JOIN empresa e ON e.id = f.id_empresa " +
                "WHERE e.id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, idEmpresa);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                fechamentosTurnos.add(new FechamentoTurno(
                        rs.getInt("id"),
                        rs.getInt("id_leitura"),
                        rs.getTimestamp("data").toLocalDateTime().toLocalDate(),
                        rs.getInt("lote"),
                        rs.getInt("id_funcionario")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return fechamentosTurnos;
    }

    // READ - LISTAR TODOS FECHAMENTOS DE TURNO
    public List<FechamentoTurno> listar() {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<FechamentoTurno> fechamentosTurnos = new ArrayList<>();
        String sql = "SELECT * FROM FECHAMENTO_TURNO";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                fechamentosTurnos.add(new FechamentoTurno(
                        rs.getInt("id"),
                        rs.getInt("id_leitura"),
                        rs.getTimestamp("data").toLocalDateTime().toLocalDate(),
                        rs.getInt("lote"),
                        rs.getInt("id_funcionario")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return fechamentosTurnos;
    }

    // UPDATE - ATUALIZAR FECHAMENTO DE TURNO
    public int atualizar(FechamentoTurno fechamentoTurno) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;
        String sql = "UPDATE FECHAMENTO_TURNO SET id_funcionario = ?, lote = ?, data = ?, id_leitura = ? WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, fechamentoTurno.getIdFuncionario());
            pst.setInt(2, fechamentoTurno.getLote());
            pst.setTimestamp(3, Timestamp.valueOf(fechamentoTurno.getData().atStartOfDay()));
            pst.setInt(4, fechamentoTurno.getIdLeitura());
            pst.setInt(5, fechamentoTurno.getId());

            retorno = pst.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // DELETE - DELETAR FECHAMENTO DE TURNO
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;
        String sql = "DELETE FROM FECHAMENTO_TURNO WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            retorno = pst.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }
}
