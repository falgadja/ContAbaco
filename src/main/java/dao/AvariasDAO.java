package dao;

import model.Avarias;

import java.sql.SQLException;

public class AvariasDAO {
    Conexao conexao = new Conexao();

    public Avarias buscarPorID(int id){
        try {
            conexao.conectar();

            conexao.pstmt = conexao.conn.prepareStatement("SELECT * FROM AVARIAS WHERE ID = ?");

            conexao.pstmt.setInt(1, id);

            conexao.rs = conexao.pstmt.executeQuery();

            if (conexao.rs.next()) {
                return new Avarias(
                        conexao.rs.getString("nome"),
                        conexao.rs.getInt("ID"),
                        conexao.rs.getString("descricao")
                );
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conexao.conn);
        }
        return null;
    }

    public Avarias buscarPorNome(int nome){
        try {
            conexao.conectar();

            conexao.pstmt = conexao.conn.prepareStatement("SELECT * FROM AVARIAS WHERE NOME = ?");

            conexao.pstmt.setInt(1, nome);

            conexao.rs = conexao.pstmt.executeQuery();

            return new Avarias(
                    conexao.rs.getString("nome"),
                    conexao.rs.getInt("ID"),
                    conexao.rs.getString("descricao")
            );



        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conexao.conn);
        }
        return null;
    }
}
