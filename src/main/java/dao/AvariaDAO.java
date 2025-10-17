package dao;

import conexao.Conexao;
import model.Avaria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvariaDAO {

    // CREATE - INSERIR UMA NOVA AVARIA COM ID GERADO AUTOMATICAMENTE
    public int inserir(Avaria avaria) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int idGerado = -1;

        String sql = "INSERT INTO avaria (nome, descricao) VALUES (?, ?) RETURNING id";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, avaria.getNome());
            pst.setString(2, avaria.getDescricao());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idGerado = rs.getInt("id");
                avaria.setId(idGerado); // Define o ID no objeto
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return idGerado; // Retorna o ID gerado ou -1 se falhar
    }

    // READ - BUSCAR AVARIA PELO ID
    public Avaria buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Avaria avaria = null;

        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM avaria WHERE id = ?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                avaria = new Avaria(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return avaria; // NULL se não encontrar
    }

    // READ - BUSCAR AVARIAS PELO NOME
    public List<Avaria> buscarPorNome(String nome) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Avaria> lista = new ArrayList<>();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM avaria WHERE nome ILIKE ?");
            pst.setString(1, "%" + nome + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lista.add(new Avaria(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return lista;
    }

    // READ - LISTAR TODAS AS AVARIAS
    public List<Avaria> listarTodas() {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Avaria> lista = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM avaria");
            while (rs.next()) {
                lista.add(new Avaria(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return lista;
    }

    // UPDATE - ATUALIZAR A DESCRIÇÃO DE UMA AVARIA PELO ID
    public int atualizarDescricao(int id, String novaDescricao) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement("UPDATE avaria SET descricao=? WHERE id=?");
            pst.setString(1, novaDescricao);
            pst.setInt(2, id);
            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1; // ATUALIZOU COM SUCESSO
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = -1; // ERRO DE EXCEÇÃO
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // UPDATE - ATUALIZAR NOME DE UMA AVARIA
    public int atualizarNome(int id, String nomeNovo) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;
        String sql = "UPDATE avaria SET nome=? WHERE id=?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nomeNovo);
            pst.setInt(2, id);
            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }

    // DELETE - REMOVER AVARIA PELO ID
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM avaria WHERE id=?");
            pst.setInt(1, id);
            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1; // DELETOU COM SUCESSO
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = -1; // ERRO DE EXCEÇÃO
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
    }
}
