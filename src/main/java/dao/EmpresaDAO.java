package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import model.Empresa;

public class EmpresaDAO {

    // CREATE - Inserir EMPRESA
    public int inserir(Empresa empresa) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();

        try {
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO EMPRESA(cnpj, nome, email, senha, id_plano, qntd_funcionarios) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            pst.setString(1, empresa.getCnpj());
            pst.setString(2, empresa.getNome());
            pst.setString(3, empresa.getEmail());
            pst.setString(4, empresa.getSenha());
            pst.setInt(5, empresa.getIdPlano());
            pst.setInt(6, empresa.getQntdFuncionarios());
            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()){
                if (rs.next()) {
                    empresa.setId(rs.getInt("ID"));
                }
            }
            return empresa.getId();
        }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            conexao.desconectar(con);
        }

        return empresa.getId();
    }

    // READ - Buscar por ID
    public Empresa buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Empresa empresa = null;

        try {
            String sql = "SELECT * FROM EMPRESA WHERE ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                empresa = new Empresa(
                        rset.getInt("ID"),
                        rset.getString("cnpj"),
                        rset.getString("nome"),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getInt("id_plano"),
                        rset.getInt("qntd_funcionarios")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return empresa;
    }

    // READ - Buscar por cnpj
    public Empresa buscarPorCnpj(String cnpj) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Empresa empresa = null;

        try {
            String sql = "SELECT * FROM EMPRESA WHERE CNPJ = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cnpj);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                empresa = new Empresa(
                        rset.getInt("ID"),
                        rset.getString("cnpj"),
                        rset.getString("nome"),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getInt("id_plano"),
                        rset.getInt("qntd_funcionarios")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return empresa;
    }

    // READ - Buscar por NOME
    public Empresa buscarPorNome(String nome) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Empresa empresa = null;

        try {
            String sql = "SELECT * FROM EMPRESA WHERE NOME = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                empresa = new Empresa(
                        rset.getInt("ID"),
                        rset.getString("cnpj"),
                        rset.getString("nome"),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getInt("id_plano"),
                        rset.getInt("qntd_funcionarios"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return empresa;
    }

    // READ - Buscar por EMAIL
    public Empresa buscarPorEmail(String email) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Empresa empresa = null;

        try {
            String sql = "SELECT * FROM EMPRESA WHERE EMAIL = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                empresa = new Empresa(
                        rset.getInt("ID"),
                        rset.getString("cnpj"),
                        rset.getString("nome"),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getInt("id_plano"),
                        rset.getInt("qntd_funcionarios"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return empresa;
    }

    // READ - Buscar por SENHA e EMAIL
    public Empresa buscarPorSenhaEEemail(String email, String senha) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Empresa empresa = null;

        try {
            String sql = "SELECT * FROM EMPRESA WHERE EMAIL= ? and SENHA = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, senha);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                empresa = new Empresa(
                        rset.getInt("ID"),
                        rset.getString("cnpj"),
                        rset.getString("nome"),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getInt("id_plano"),
                        rset.getInt("qntd_funcionarios"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return empresa;
    }

    // READ - Listar EMPRESA
    public List<Empresa> listar() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Empresa> empresas = new ArrayList<>();

        try {
            String sql = "SELECT * FROM EMPRESA";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                empresas.add(new Empresa(
                        rset.getInt("ID"),
                        rset.getString("cnpj"),
                        rset.getString("nome"),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getInt("id_plano"),
                        rset.getInt("qntd_funcionarios")));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return empresas;
    }

    // UPDATE - atualizar a EMPRESA
    public int atualizar(Empresa empresa) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        int retorno = 0;

        try {
            String sql = "UPDATE EMPRESA SET CNPJ = ?, NOME = ?, EMAIL = ? ,SENHA = ? , ID_PLANO = ? , QNTD_FUNCIONARIOS = ?  WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, empresa.getCnpj());
            pst.setString(2, empresa.getNome());
            pst.setString(3, empresa.getEmail());
            pst.setString(4, empresa.getSenha());
            pst.setInt(5, empresa.getIdPlano());
            pst.setInt(6, empresa.getQntdFuncionarios());
            pst.setInt(7, empresa.getId());

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

    // DELETE - Deletar EMPRESA
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int deletado = 0;

        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM EMPRESA WHERE id = ?");
            pst.setInt(1, id);

            deletado = pst.executeUpdate();
            if (deletado > 0) {
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
    // READ - Buscar por SENHA e EMAIL
    public Empresa buscarPorSenhaEEemail(String email, String senha) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Empresa empresa = null;

        try {
            String sql = "SELECT * FROM EMPRESA WHERE EMAIL= ? and SENHA = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, senha);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                empresa = new Empresa(
                        rset.getInt("ID"),
                        rset.getString("cnpj"),
                        rset.getString("nome"),
                        rset.getString("email"),
                        rset.getString("senha"),
                        rset.getInt("id_plano"),
                        rset.getInt("qntd_funcionarios"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return empresa;
    }



}