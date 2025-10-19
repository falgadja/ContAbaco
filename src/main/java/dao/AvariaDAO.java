package dao;

import conexao.Conexao;
import model.Avaria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvariaDAO {

    // CREATE - Inserir AVARIA
    public int inserir(Avaria avaria) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "INSERT INTO avaria (nome, descricao) VALUES (?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, avaria.getNome());
            pst.setString(2, avaria.getDescricao());

            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    avaria.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(conn);
        }

        return retorno;
    }

    // READ - Buscar AVARIA por ID
    public Avaria buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Avaria a = null;

        try {
            String sql = "SELECT * FROM avaria WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                a = new Avaria(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return a;
    }

    // READ - Listar todas as AVARIAS
    public List<Avaria> listarTodos() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Avaria> avarias = new ArrayList<>();

        try {
            String sql = "SELECT * FROM avaria";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Avaria a = new Avaria(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao")
                );
                avarias.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return avarias;
    }

    // UPDATE - Atualizar AVARIA
    public int atualizar(Avaria avaria) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "UPDATE avaria SET nome = ?, descricao = ? WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, avaria.getNome());
            pst.setString(2, avaria.getDescricao());
            pst.setInt(3, avaria.getId());

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

    // DELETE - Deletar AVARIA
    public int deletar(Avaria avaria) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "DELETE FROM avaria WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, avaria.getId());

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
