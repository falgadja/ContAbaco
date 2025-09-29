package dao;

import conexao.Conexao;
import model.Empresa;

import java.sql.*;
import java.util.List;

public class EmpresaDAO {

    // CREATE - Inserir EMPRESA
    public int inserir(Empresa empresa) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;

        try {
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO EMPRESA(id, cnpj, nome, email, senha, id_plano, qntd_funcionarios) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            pst.setInt(1, empresa.getId());
            pst.setString(2, empresa.getCpnj());
            pst.setString(3, empresa.getNome());
            pst.setString(4, empresa.getEmail());
            pst.setString(5, empresa.getSenha());
            pst.setInt(6, empresa.getIdPlano());
            pst.setInt(7, empresa.getQntdFuncionarios());
            int linhas = pst.executeUpdate();
            if (linhas > 0) {
                retorno = 1;
                return retorno;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            conexao.desconectar(con);
        }

        return retorno;
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
    public Empresa buscarPorCnpj(int cnpj) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        Empresa empresa = null;

        try {
            String sql = "SELECT * FROM EMPRESA WHERE CNPJ = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cnpj);
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

    // READ - Listar EMPRESA
    public List<Empresa> listar() {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Empresa> empresas = null;

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
                        rset.getInt("qntd_funcionarios"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        }
        return empresas;
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