package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import model.Funcionario;

public class FuncionarioDAO {

    // CREATE - INSERIR FUNCIONARIO
    public int inserir(Funcionario funcionario) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int idGerado = -1;
        String sql = "INSERT INTO funcionario(data_nascimento, nome, senha, email, id_setor, id_empresa, sobrenome) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(funcionario.getDataNascimento()));
            pst.setString(2, funcionario.getNome());
            pst.setString(3, funcionario.getSenha());
            pst.setString(4, funcionario.getEmail());
            pst.setInt(5, funcionario.getIdSetor());
            pst.setInt(6, funcionario.getIdEmpresa());
            pst.setString(7, funcionario.getSobrenome());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idGerado = rs.getInt("id");
                funcionario.setId(idGerado);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return idGerado; // Retorna ID gerado ou -1 se falhar
    }

    // READ - BUSCAR FUNCIONARIO PELO ID
    public Funcionario buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Funcionario funcionario = null;
        String sql = "SELECT * FROM FUNCIONARIO WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getTimestamp("data_nascimento").toLocalDateTime().toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_empresa"),
                        rs.getInt("id_setor")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return funcionario;
    }

    // READ - BUSCAR FUNCIONARIO PELO NOME E SOBRENOME
    public Funcionario buscarPorNomeESobrenome(String nome, String sobrenome) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Funcionario funcionario = null;
        String sql = "SELECT * FROM FUNCIONARIO WHERE nome = ? AND sobrenome = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nome);
            pst.setString(2, sobrenome);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getTimestamp("data_nascimento").toLocalDateTime().toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_empresa"),
                        rs.getInt("id_setor")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return funcionario;
    }

    // READ - BUSCAR FUNCIONARIO PELO NOME E SOBRENOME
    public Funcionario buscarPorNomeESobrenome(String nome) { // Sobrescrita para pesquisar pelo primeiro nome e pelo nome completo
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Funcionario funcionario = null;
        String sql = "SELECT * FROM FUNCIONARIO WHERE nome = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nome);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getTimestamp("data_nascimento").toLocalDateTime().toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_empresa"),
                        rs.getInt("id_setor")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return funcionario;
    }

    // READ - BUSCAR FUNCIONARIOS PELO ID DA EMPRESA
    public List<Funcionario> buscarPorIdEmpresa(int idEmpresa) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT F.* FROM FUNCIONARIO F JOIN EMPRESA E ON E.ID = F.ID_EMPRESA WHERE F.ID_EMPRESA = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, idEmpresa);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                funcionarios.add(new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getTimestamp("data_nascimento").toLocalDateTime().toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_empresa"),
                        rs.getInt("id_setor")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return funcionarios;
    }

    // READ - BUSCAR FUNCIONARIO PELO EMAIL
    public Funcionario buscarPorEmail(String email) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Funcionario funcionario = null;
        String sql = "SELECT * FROM FUNCIONARIO WHERE email = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getTimestamp("data_nascimento").toLocalDateTime().toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_empresa"),
                        rs.getInt("id_setor")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return funcionario;
    }

    // READ - BUSCAR FUNCIONARIO PELO EMAIL E SENHA
    public Funcionario buscarPorEmailESenha(String email, String senha) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Funcionario funcionario = null;
        String sql = "SELECT * FROM FUNCIONARIO WHERE email = ? AND senha = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, senha);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getTimestamp("data_nascimento").toLocalDateTime().toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_empresa"),
                        rs.getInt("id_setor")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return funcionario;
    }

    // READ - LISTAR TODOS FUNCIONARIOS
    public List<Funcionario> listar() {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM FUNCIONARIO";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                funcionarios.add(new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getTimestamp("data_nascimento").toLocalDateTime().toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_empresa"),
                        rs.getInt("id_setor")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return funcionarios;
    }

    // UPDATE - ATUALIZAR FUNCIONARIO
    public int atualizar(Funcionario funcionario) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;
        String sql = "UPDATE FUNCIONARIO SET data_nascimento = ?, nome = ?, senha = ?, email = ?, id_setor = ?, id_empresa = ?, sobrenome = ? WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(funcionario.getDataNascimento()));
            pst.setString(2, funcionario.getNome());
            pst.setString(3, funcionario.getSenha());
            pst.setString(4, funcionario.getEmail());
            pst.setInt(5, funcionario.getIdSetor());
            pst.setInt(6, funcionario.getIdEmpresa());
            pst.setString(7, funcionario.getSobrenome());
            pst.setInt(8, funcionario.getId());

            retorno = pst.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }

        return retorno; // retorna número de linhas alteradas ou -1 se erro
    }

    // DELETE - DELETAR FUNCIONARIO
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;
        String sql = "DELETE FROM FUNCIONARIO WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            retorno = pst.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }

        return retorno; // retorna número de linhas deletadas ou -1 se erro
    }
}
