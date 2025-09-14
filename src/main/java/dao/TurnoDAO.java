package dao;

import model.Turno;
import java.sql.SQLException;

public class TurnoDAO {

    Conexao conexao = new Conexao();

    // Busca um turno pelo ID
    public Turno buscarPorID(int id) {
        try {
            conexao.conectar();

            conexao.pstmt = conexao.conn.prepareStatement("SELECT * FROM TURNO WHERE ID = ?");

            conexao.pstmt.setInt(1, id);

            conexao.rs = conexao.pstmt.executeQuery();

            if (conexao.rs.next()) {
                return new Turno(
                        conexao.rs.getInt("qtd_funcionarios"),
                        conexao.rs.getObject("tempo_duracao", java.time.LocalDateTime.class),
                        conexao.rs.getInt("id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conexao.conn);
        }

        return null;
    }

    // Insere um novo turno
    public boolean inserirTurno(Turno turno) {
        try {
            conexao.conectar();

            conexao.pstmt = conexao.conn.prepareStatement("INSERT INTO TURNO (id, qtd_funcionarios, tempo_duracao) VALUES (?, ?, ?)");

            conexao.pstmt.setInt(1, turno.getId());

            conexao.pstmt.setInt(2, turno.getQtdFuncionarios());

            conexao.pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(turno.getTempoDuracao()));

            return conexao.pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conexao.conn);
        }

        return false;
    }
}
