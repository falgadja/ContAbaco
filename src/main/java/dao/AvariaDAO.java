package dao;

import conexao.Conexao;
import model.Avarias;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvariaDAO {
    // CONEXÃO COM O BANCO
    private Connection connection;

    public AvariaDAO(Connection connection) {
        this.connection = connection;
    }


    // CREATE - INSERIR UMA NOVA AVARIA
    public int inserir(Avarias avaria) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO avarias (nome, descricao) VALUES (?, ?)"
            );
            // DEFININDO VALORES DOS CAMPOS
            pst.setString(1, avaria.getNome());
            pst.setString(2, avaria.getDescricao());

            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1; // RETORNA 1 SE INSERIU COM SUCESSO
                return retorno;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return retorno; // RETORNA 0 SE DER ERRO
        } finally {
            conexao.desconectar(con); // FECHA CONEXÃO
        }
        return retorno;
    }


    // READ - BUSCAR AVARIA PELO ID
    public Avarias buscarPorId(int id) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Avarias avaria = null;

        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM avarias WHERE id = ?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                avaria = new Avarias(
                        rs.getString("nome"),
                        rs.getInt("id"),
                        rs.getString("descricao")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con); // FECHA CONEXÃO
        }

        return avaria; // RETORNA A AVARIA ENCONTRADA OU NULL
    }


    // READ - BUSCAR AVARIAS PELO NOME
    public List<Avarias> buscarPorNome(String nome) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Avarias> lista = new ArrayList<>();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM avarias WHERE nome ILIKE ?");
            pst.setString(1, "%" + nome + "%");
            ResultSet rs = pst.executeQuery();

            // ADICIONA TODAS AS AVARIAS ENCONTRADAS NA LISTA
            while (rs.next()) {
                lista.add(new Avarias(
                        rs.getString("nome"),
                        rs.getInt("id"),
                        rs.getString("descricao")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con); // FECHA CONEXÃO
        }

        return lista; // RETORNA LISTA DE AVARIAS
    }


    // READ - LISTAR TODAS AS AVARIAS
    public List<Avarias> listarTodas() throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Avarias> lista = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM avarias");

            // PERCORRE TODOS OS REGISTROS DA TABELA
            while (rs.next()) {
                lista.add(new Avarias(
                        rs.getString("nome"),
                        rs.getInt("id"),
                        rs.getString("descricao")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con); // FECHA CONEXÃO
        }

        return lista; // RETORNA LISTA COM TODAS AS AVARIAS
    }


    // UPDATE - ATUALIZAR A DESCRIÇÃO DE UMA AVARIA PELO ID
    public int atualizarDescricao(int id, String novaDescricao) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement("UPDATE avarias SET descricao=? WHERE id=?");
            pst.setString(1, novaDescricao);
            pst.setInt(2, id);

            retorno = pst.executeUpdate();
            if (retorno > 0) {
                retorno = 1; // RETORNA 1 SE ATUALIZOU
                return retorno;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return retorno; // RETORNA 0 SE DER ERRO
        } finally {
            conexao.desconectar(con); // FECHA CONEXÃO
        }

        return retorno;
    }


    // DELETE - REMOVER AVARIA PELO ID
    public int deletar(int id) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int deletado = 0;

        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM avarias WHERE id=?");
            pst.setInt(1, id);

            deletado = pst.executeUpdate();
            if (deletado > 0) {
                deletado = 1; // RETORNA 1 SE DELETOU
                return deletado;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return deletado; // RETORNA 0 SE DER ERRO
        } finally {
            conexao.desconectar(con); // FECHA CONEXÃO
        }

        return deletado;
    }
}
