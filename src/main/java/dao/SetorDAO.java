package dao;

import conexao.Conexao;
import model.Setor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SetorDAO {
    // CONEXÃO COM O BANCO
    private Connection connection;

    public SetorDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE - Inserir Setor
    public int inserir(Setor setor) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO setor (id, nome) VALUES (?, ?)"
            );
            pst.setInt(1, setor.getId());
            pst.setString(2, setor.getNome());

            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
                return retorno;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return retorno;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // READ - Buscar Setor por ID
    public Setor buscarPorID(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Setor setor = null;

        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM setor WHERE id = ?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                setor = new Setor(
                        rs.getInt("id"),
                        rs.getString("nome")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return setor;
    }

    // READ - Listar todos os Setores
    public List<Setor> listarTodos() {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Setor> setores = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM setor");

            while (rs.next()) {
                setores.add(new Setor(
                        rs.getInt("id"),
                        rs.getString("nome")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return setores;
    }

    // UPDATE - Alterar Setor
    public int atualizar(Setor setor) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "UPDATE setor SET nome = ? WHERE id = ?"
            );
            pst.setString(1, setor.getNome());
            pst.setInt(2, setor.getId());

            retorno = pst.executeUpdate();
            if (retorno > 0) {
                retorno = 1;
                return retorno;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return retorno;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // DELETE - Deletar Setor
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int deletado = 0;

        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM setor WHERE id = ?");
            pst.setInt(1, id);
            deletado = pst.executeUpdate();
            if (deletado > 0) {
                deletado = 1;
                return deletado;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return deletado;
        } finally {
            conexao.desconectar(con);
        }

        return deletado;
    }
}
