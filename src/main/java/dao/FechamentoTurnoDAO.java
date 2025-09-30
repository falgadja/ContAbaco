package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import conexao.Conexao;
import model.FechamentoTurno;

public class FechamentoTurnoDAO {

    // CREATE - Inserir FECHAMENTO de TURNO
    public int inserir(FechamentoTurno fechamentoTurno) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO turno(id, lote, id_funcionario, data, id_leitura) VALUES (?, ?, ?, ?, ?)"
            );
            pst.setInt(1, fechamentoTurno.getId());
            pst.setInt(2, fechamentoTurno.getIdFuncionario());
            pst.setInt(3, fechamentoTurno.getLote());
            pst.setTimestamp(4, Timestamp.valueOf(fechamentoTurno.getData().atStartOfDay()));
            pst.setInt(5, fechamentoTurno.getIdLeitura());
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
    public FechamentoTurno buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        FechamentoTurno fechamentoTurno = null;

        try {
            String sql = "SELECT * FROM FECHAMENTO_TURNO WHERE ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                fechamentoTurno = new FechamentoTurno(
                        rset.getInt("ID"),
                        rset.getInt("ID_LEITURA"),
                        rset.getTimestamp("DATA").toLocalDateTime().toLocalDate(),
                        rset.getInt("LOTE"),
                        rset.getInt("ID_FUNCIONARIO")

                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return fechamentoTurno;
    }

    // READ - Buscar por DATA
    public List<FechamentoTurno> buscarPorNome(Date dataInicio, Date dataFim, int idEmpresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<FechamentoTurno> fechamentosTurnos = null;

        try {
            String sql = "SELECT * FROM FECHAMENTO_TURNO ft join funcionario f on f.id=ft.id_funcionario join empresa e on e.id=f.id_empresa WHERE ft.DATA BETWEEN ? AND ? AND e.id= ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, dataInicio);
            pstmt.setDate(2, dataFim);
            pstmt.setInt(3, idEmpresa);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                fechamentosTurnos.add(new FechamentoTurno(
                        rset.getInt("ID"),
                        rset.getInt("ID_LEITURA"),
                        rset.getTimestamp("DATA").toLocalDateTime().toLocalDate(),
                        rset.getInt("LOTE"),
                        rset.getInt("ID_FUNCIONARIO")

                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return fechamentosTurnos;
    }

    // READ - Buscar por ID do FUNCIONARIO
    public List<FechamentoTurno> buscarPorIdFuncionario(int idFuncionario) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<FechamentoTurno> fechamentosTurnos = null;

        try {
            String sql = "SELECT * FROM FECHAMENTO_TURNO WHERE ID_FUNCIONARIO = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idFuncionario);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                fechamentosTurnos.add(new FechamentoTurno(
                        rset.getInt("ID"),
                        rset.getInt("ID_LEITURA"),
                        rset.getTimestamp("DATA").toLocalDateTime().toLocalDate(),
                        rset.getInt("LOTE"),
                        rset.getInt("ID_FUNCIONARIO")

                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return fechamentosTurnos;
    }

    // READ - Buscar por LOTE
    public FechamentoTurno buscarPorLote(int lote) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        FechamentoTurno fechamentoTurno = null;

        try {
            String sql = "SELECT * FROM FECHAMENTO_TURNO WHERE lote = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lote);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                fechamentoTurno = new FechamentoTurno(
                        rset.getInt("ID"),
                        rset.getInt("ID_LEITURA"),
                        rset.getTimestamp("DATA").toLocalDateTime().toLocalDate(),
                        rset.getInt("LOTE"),
                        rset.getInt("ID_FUNCIONARIO")

                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return fechamentoTurno;
    }

    // READ - Buscar por empresa
    public List<FechamentoTurno> buscarPorEmpresa(int idEmpresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<FechamentoTurno> fechamentosTurnos = null;

        try {
            String sql = "SELECT * FROM FECHAMENTO_TURNO ft join funcionario f on f.id=ft.id_funcionario join empresa e on e.id=f.id_empresa WHERE e.id= ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idEmpresa);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                fechamentosTurnos.add(new FechamentoTurno(
                        rset.getInt("ID"),
                        rset.getInt("ID_LEITURA"),
                        rset.getTimestamp("DATA").toLocalDateTime().toLocalDate(),
                        rset.getInt("LOTE"),
                        rset.getInt("ID_FUNCIONARIO")

                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return fechamentosTurnos;
    }

    // READ - Listar FECHAMENTOS de TURNO
    public List<FechamentoTurno> listar() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<FechamentoTurno> fechamentosTurnos = null;

        try {
            String sql = "SELECT * FROM FECHAMENTO_TURNO";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                fechamentosTurnos.add(new FechamentoTurno(
                        rset.getInt("ID"),
                        rset.getInt("ID_LEITURA"),
                        rset.getTimestamp("DATA").toLocalDateTime().toLocalDate(),
                        rset.getInt("LOTE"),
                        rset.getInt("ID_FUNCIONARIO")

                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return fechamentosTurnos;
    }

    // UPDATE - atualizar o FECHAMENTO de TURNO
    public int atualizar(FechamentoTurno fechamentoTurno) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "UPDATE FECHAMENTO_TURNO SET ID_FUNCIONARIO = ?, LOTE = ? , DATA = ? , ID_LEITURA = ? WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, fechamentoTurno.getIdFuncionario());
            pst.setInt(2, fechamentoTurno.getLote());
            pst.setTimestamp(3, Timestamp.valueOf(fechamentoTurno.getData().atStartOfDay()));
            pst.setInt(4, fechamentoTurno.getIdLeitura());
            pst.setInt(5, fechamentoTurno.getId());

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

    // DELETE - Deletar Fechamento Turno
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int deletado = 0;

        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM FECHAMENTO_TURNO WHERE id = ?");
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
