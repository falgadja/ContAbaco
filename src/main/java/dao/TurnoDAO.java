package dao;

import conexao.Conexao;
import model.Turno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAO {
    // CONEXÃO COM O BANCO
    private Connection connection;

    public TurnoDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE - Inserir Turno
    public int inserir(Turno turno) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO turno (id, qtd_funcionarios, tempo_duracao) VALUES (?, ?, ?)"
            );
            pst.setInt(1, turno.getId());
            pst.setInt(2, turno.getQtdFuncionarios());
            pst.setTimestamp(3, Timestamp.valueOf(turno.getTempoDuracao()));

            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
                return retorno;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // READ - Buscar Turno por ID
    public Turno buscarPorID(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Turno turno = null;

        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM turno WHERE id = ?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                turno = new Turno(
                        rs.getInt("id"),
                        rs.getObject("tempo_duracao", java.time.LocalDateTime.class),
                        rs.getInt("qtd_funcionarios")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return turno;
    }

    // READ - Listar todos os Turnos
    public List<Turno> listarTodos() {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Turno> turnos = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM turno");

            while (rs.next()) {
                Turno turno = new Turno(
                        rs.getInt("id"),
                        rs.getObject("tempo_duracao", java.time.LocalDateTime.class),
                        rs.getInt("qtd_funcionarios")
                );
                turnos.add(turno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return turnos;
    }

    // UPDATE - Alterar Turno
    public int atualizar(Turno turno) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "UPDATE turno SET qtd_funcionarios = ?, tempo_duracao = ? WHERE id = ?"
            );
            pst.setInt(1, turno.getQtdFuncionarios());
            pst.setTimestamp(2, Timestamp.valueOf(turno.getTempoDuracao()));
            pst.setInt(3, turno.getId());

            retorno = pst.executeUpdate();
            if (retorno > 0) {
                retorno = 1;
                return retorno;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // DELETE - Deletar Turno
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int deletado = 0;

        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM turno WHERE id = ?");
            pst.setInt(1, id);

            deletado = pst.executeUpdate();
            if (deletado > 0) {
                deletado = 1;
                return deletado;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return deletado;
    }
}
