package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import model.Funcionario;

public class FuncionarioDAO {

    // CREATE - Inserir FUNCIONARIO
public int inserir(Funcionario funcionario) {
    Conexao conexao = new Conexao();
    Connection con = conexao.conectar();
    int retorno = 0;

    try {
        PreparedStatement pst = con.prepareStatement(
            "INSERT INTO funcionario(data_nascimento, nome, senha, email, id_setor, id_empresa, sobrenome) VALUES (?, ?, ?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS
        );
        pst.setDate(1, Date.valueOf(funcionario.getDataNascimento()));
        pst.setString(2, funcionario.getNome());
        pst.setString(3, funcionario.getSenha());
        pst.setString(4, funcionario.getEmail());
        pst.setInt(5, funcionario.getIdSetor());
        pst.setInt(6, funcionario.getIdEmpresa());
        pst.setString(7, funcionario.getSobrenome());
        int linhas = pst.executeUpdate();
        if (linhas > 0) {
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    funcionario.setId(rs.getInt(1));
                }
            }
            return funcionario.getId();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return funcionario.getId();
    } finally {
        conexao.desconectar(con);
    }

    return funcionario.getId();
}

    // READ - Buscar por ID
    public Funcionario buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Funcionario funcionario = null;

        try {
            String sql = "SELECT * FROM FUNCIONARIO WHERE ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                funcionario = new Funcionario(
                        rset.getInt("ID"),
                        rset.getString("nome"),
                        rset.getString("sobrenome"),
                        rset.getTimestamp("DATA_NASCIMENTO").toLocalDateTime().toLocalDate(),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getInt("id_empresa"),
                        rset.getInt("id_setor"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return funcionario;
    }

    // READ - Buscar por NOME e SOBRENOME
    public Funcionario buscarPorNome(String nome, String sobrenome) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Funcionario funcionario = null;

        try {
            String sql = "SELECT * FROM FUNCIONARIO WHERE NOME = ? AND SOBRENOME = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, sobrenome);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                funcionario = new Funcionario(
                        rset.getInt("ID"),
                        rset.getString("nome"),
                        rset.getString("sobrenome"),
                        rset.getTimestamp("DATA_NASCIMENTO").toLocalDateTime().toLocalDate(),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getInt("id_empresa"),
                        rset.getInt("id_setor"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return funcionario;
    }

    // READ - Buscar por ID da EMPRESA
    public List<Funcionario> buscarPorIdEmpresa(int idEmpresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            String sql = "SELECT F.* FROM FUNCIONARIO F JOIN EMPRESA E ON E.ID=F.ID_EMPRESA WHERE ID_EMPRESA = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idEmpresa);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                funcionarios.add(new Funcionario(
                        rset.getInt("ID"),
                        rset.getString("nome"),
                        rset.getString("sobrenome"),
                        rset.getTimestamp("DATA_NASCIMENTO").toLocalDateTime().toLocalDate(),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getInt("id_empresa"),
                        rset.getInt("id_setor")));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return funcionarios;
    }

    // READ - Buscar por EMAIL
    public Funcionario buscarPorEmail(String email) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Funcionario funcionario = null;

        try {
            String sql = "SELECT * FROM FUNCIONARIO WHERE EMAIL = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                funcionario = new Funcionario(
                        rset.getInt("ID"),
                        rset.getString("nome"),
                        rset.getString("sobrenome"),
                        rset.getTimestamp("DATA_NASCIMENTO").toLocalDateTime().toLocalDate(),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getInt("id_empresa"),
                        rset.getInt("id_setor"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return funcionario;
    }

    // READ - Buscar por EMAIL e SENHA
    public Funcionario buscarPorEmailESenha(String email, String senha) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Funcionario funcionario = null;

        try {
            String sql = "SELECT * FROM FUNCIONARIO WHERE EMAIL = ? AND senha = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, senha);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                funcionario = new Funcionario(
                        rset.getInt("ID"),
                        rset.getString("nome"),
                        rset.getString("sobrenome"),
                        rset.getTimestamp("DATA_NASCIMENTO").toLocalDateTime().toLocalDate(),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getInt("id_empresa"),
                        rset.getInt("id_setor"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return funcionario;
    }

    // READ - Listar FUNCIONARIOS
    public List<Funcionario> listar() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            String sql = "SELECT * FROM FUNCIONARIO";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                funcionarios.add(new Funcionario(
                        rset.getInt("ID"),
                        rset.getString("nome"),
                        rset.getString("sobrenome"),
                        rset.getTimestamp("DATA_NASCIMENTO").toLocalDateTime().toLocalDate(),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getInt("id_empresa"),
                        rset.getInt("id_setor")));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return funcionarios;
    }

    // UPDATE - atualizar o FUNCIONARIO
    public int atualizar(Funcionario funcionario) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "UPDATE FUNCIONARIO SET DATA_NASCIMENTO = ?, NOME = ?, SENHA = ? , EMAIL = ? , SETOR = ? , ID_EMPRESA = ? , SOBRENOME = ?  WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(funcionario.getDataNascimento()));
            pst.setString(2, funcionario.getNome());
            pst.setString(3, funcionario.getSenha());
            pst.setString(4, funcionario.getEmail());
            pst.setInt(5, funcionario.getIdSetor());
            pst.setInt(6, funcionario.getIdEmpresa());
            pst.setString(7, funcionario.getSobrenome());
            pst.setInt(8, funcionario.getId());

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

    // DELETE - Deletar Funcionario
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int deletado = 0;

        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM FUNCIONARIO WHERE id = ?");
            pst.setInt(1, id);

            deletado = pst.executeUpdate();
            if (deletado > 0) {
                deletado = 1;
                return deletado;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            conexao.desconectar(con);
        }

        return deletado;
    }
}