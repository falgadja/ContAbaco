package dao;

import model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//Essa classe serve para conversar com o banco de dados e executar os comandos

public class FuncionarioDAO {
    //Conexão com o banco
    private Connection connection;

    public FuncionarioDAO(Connection connection) {
        this.connection = connection;
    }
    // INSERIR UM NOVO FUNCIONARIO
    public int inserir(Funcionario funcionario) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO funcionario (nome, data_nascimento, id_setor, id_turno) VALUES (?, ?, ?, ?)");
            pst.setString(1, funcionario.getNome());
            pst.setDate(2, java.sql.Date.valueOf(funcionario.getDataNascimento()));
            pst.setInt(3, funcionario.getIdSetor());
            pst.setInt(4, funcionario.getIdTurno());

            int linhas = pst.executeUpdate();
            if (linhas > 0) {
               retorno= 1;
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




    // READ - buscar por codigo
    public Funcionario buscarPorId(int id) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Funcionario funcionario = null;

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM funcionario WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getInt("id_setor"),
                        rs.getInt("id_turno")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return funcionario;
    }

    // READ - Buscar por Nome
    public List<Funcionario> buscarPorNome(String nome) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM funcionario WHERE nome ILIKE ?");
            stmt.setString(1, "%" + nome + "%");//COMPLEMENTO DO ILIKE
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) { //MOSTRAR EM TABELA
                funcionarios.add(new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getInt("id_setor"),
                        rs.getInt("id_turno")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return funcionarios;
    }
    // READ - listar todos
    public List<Funcionario> listarTodos() throws SQLException {
    Conexao conexao = new Conexao();
    Connection con = conexao.conectar();
    List<Funcionario> funcionarios = new ArrayList<>();

    try{
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM funcionario");

        while (rs.next()) {
            funcionarios.add(new Funcionario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDate("data_nascimento").toLocalDate(),
                    rs.getInt("id_setor"),
                    rs.getInt("id_turno")
            ));
        }
    }catch (SQLException sqe) {
        sqe.printStackTrace();
    }finally {
        conexao.desconectar(con);
    }
    return funcionarios; //RETORNA LISTA DOS FUNCIONARIOS EM FORMA DE TABELA
    }

    // UPDATE - Atualiza
    public int atualizarNome(Funcionario funcionario, String nomeNovo) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno=0;

        try{
            PreparedStatement stmt = con.prepareStatement("UPDATE  FUNCIONARIO SET nome = ? WHERE id = ? ");
            stmt.setString(1, nomeNovo);
            stmt.setInt(2, funcionario.getId());
            retorno = stmt.executeUpdate();
            if (retorno> 0) {
                retorno=1;
                return retorno;
            }
        }catch (SQLException sqe) {
            sqe.printStackTrace();
            return retorno;
        }
        finally {
            conexao.desconectar(con);
        }
        return retorno;
    }

    // DELETA UM FUNCIONARIO
    public int deletar(int id) throws SQLException {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int deletado = 0;
        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM funcionario WHERE id = ?");
            stmt.setInt(1, id);
            deletado = stmt.executeUpdate();
            if (deletado > 0) {
               deletado=1;
               return deletado;
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return deletado;
        } finally {
            conexao.desconectar(con);
        }
        return deletado;
    }
}
