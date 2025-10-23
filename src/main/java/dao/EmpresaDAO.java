package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import model.Empresa;

public class EmpresaDAO {

    // CREATE - INSERIR EMPRESA
    public int inserir(Empresa empresa) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int idGerado = -1;
        String sql = "INSERT INTO EMPRESA(cnpj, nome, email, senha, id_plano, qntd_funcionarios) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, empresa.getCnpj());
            pst.setString(2, empresa.getNome());
            pst.setString(3, empresa.getEmail());
            pst.setString(4, empresa.getSenha());
            pst.setInt(5, empresa.getIdPlano());
            pst.setInt(6, empresa.getQntdFuncionarios());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idGerado = rs.getInt("id");
                empresa.setId(idGerado);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return idGerado; // Retorna o ID gerado, -1 se falhar
    }

    // READ - BUSCAR EMPRESA PELO ID
    public Empresa buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Empresa empresa = null;
        String sql = "SELECT * FROM EMPRESA WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                empresa = new Empresa(
                        rs.getInt("id"),
                        rs.getString("cnpj"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_plano"),
                        rs.getInt("qntd_funcionarios")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return empresa; // retorna null se não encontrar
    }

    // READ - BUSCAR EMPRESA PELO CNPJ
    public Empresa buscarPorCnpj(String cnpj) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Empresa empresa = null;
        String sql = "SELECT * FROM EMPRESA WHERE cnpj = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, cnpj);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                empresa = new Empresa(
                        rs.getInt("id"),
                        rs.getString("cnpj"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_plano"),
                        rs.getInt("qntd_funcionarios")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return empresa;
    }

    // READ - BUSCAR EMPRESA PELO NOME
    public Empresa buscarPorNome(String nome) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Empresa empresa = null;
        String sql = "SELECT * FROM EMPRESA WHERE nome = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nome);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                empresa = new Empresa(
                        rs.getInt("id"),
                        rs.getString("cnpj"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_plano"),
                        rs.getInt("qntd_funcionarios")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return empresa;
    }

    // READ - BUSCAR EMPRESA PELO EMAIL
    public Empresa buscarPorEmail(String email) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Empresa empresa = null;
        String sql = "SELECT * FROM EMPRESA WHERE email = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                empresa = new Empresa(
                        rs.getInt("id"),
                        rs.getString("cnpj"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_plano"),
                        rs.getInt("qntd_funcionarios")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return empresa;
    }

    // READ - BUSCAR EMPRESA PELO EMAIL E SENHA
    public Empresa buscarPorEmailESenha(String email, String senha) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Empresa empresa = null;
        String sql = "SELECT * FROM EMPRESA WHERE email = ? AND senha = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, senha);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                empresa = new Empresa(
                        rs.getInt("id"),
                        rs.getString("cnpj"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_plano"),
                        rs.getInt("qntd_funcionarios")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return empresa;
    }

    // READ - LISTAR TODAS AS EMPRESAS
    public List<Empresa> listar() {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Empresa> empresas = new ArrayList<>();
        String sql = "SELECT * FROM EMPRESA";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                empresas.add(new Empresa(
                        rs.getInt("id"),
                        rs.getString("cnpj"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_plano"),
                        rs.getInt("qntd_funcionarios")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return empresas;
    }

    // UPDATE - ATUALIZAR EMPRESA
    public int atualizar(Empresa empresa) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno;
        String sql = "UPDATE EMPRESA SET cnpj = ?, nome = ?, email = ?, senha = ?, id_plano = ?, qntd_funcionarios = ? WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, empresa.getCnpj());
            pst.setString(2, empresa.getNome());
            pst.setString(3, empresa.getEmail());
            pst.setString(4, empresa.getSenha());
            pst.setInt(5, empresa.getIdPlano());
            pst.setInt(6, empresa.getQntdFuncionarios());
            pst.setInt(7, empresa.getId());

            retorno = pst.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }

        return retorno; // retorna número de linhas alteradas ou -1 em caso de erro
    }

    // DELETE - DELETAR EMPRESA
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno;
        String sql = "DELETE FROM EMPRESA WHERE id = ?";

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

        return retorno; // retorna número de linhas deletadas ou -1 em caso de erro
    }
}
