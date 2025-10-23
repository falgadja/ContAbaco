package dao;

import conexao.Conexao;
import model.Plano;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanoDAO {
    // CREATE - INSERIR PLANO
    public int inserir(Plano plano) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int idGerado = -1;

        try {
            String sql = "INSERT INTO PLANO (NOME, PRECO) VALUES (?, ?) RETURNING ID";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, plano.getNome());
            pst.setDouble(2, plano.getPreco());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idGerado = rs.getInt("ID");
                plano.setId(idGerado);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return idGerado;
    }

    // READ - BUSCAR PLANO PELO ID
    public Plano buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Plano plano = null;

        try {
            String sql = "SELECT * FROM PLANO WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                plano = new Plano(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("PRECO")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return plano;
    }

    // READ - BUSCAR PLANO PELO NOME
    public Plano buscarPorNome(String nome) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Plano plano = null;

        try {
            String sql = "SELECT * FROM PLANO WHERE NOME = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nome);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                plano = new Plano(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("PRECO")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return plano;
    }

    // READ - BUSCAR PLANO PELO PREÇO
    public Plano buscarPorPreco(double preco) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Plano plano = null;

        try {
            String sql = "SELECT * FROM PLANO WHERE PRECO = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDouble(1, preco);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                plano = new Plano(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("PRECO")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return plano;
    }

    // READ - LISTAR TODOS OS PLANOS
    public List<Plano> listar() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Plano> planos = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PLANO";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                planos.add(new Plano(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("PRECO")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }

        return planos;
    }

    // UPDATE - ATUALIZAR PLANO
    public int atualizar(Plano plano) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno;

        try {
            String sql = "UPDATE PLANO SET NOME = ?, PRECO = ? WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, plano.getNome());
            pst.setDouble(2, plano.getPreco());
            pst.setInt(3, plano.getId());

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

    // DELETE - APAGAR PLANO
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno;

        try {
            String sql = "DELETE FROM PLANO WHERE ID = ?";
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
