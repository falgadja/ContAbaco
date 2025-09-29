package dao;

import conexao.Conexao;
import model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {

    // INSERIR ENDEREÇO
    public int inserirEndereco(Endereco endereco) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;
        String sql = "INSERT INTO endereco (pais,estado,cidade,bairro,rua,numero,cep,id_empresa) VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, endereco.getPais());
            pst.setString(2, endereco.getEstado());
            pst.setString(3, endereco.getCidade());
            pst.setString(4, endereco.getBairro());
            pst.setString(5, endereco.getRua());
            pst.setInt(6, endereco.getNumero());
            pst.setString(7, endereco.getCep());
            pst.setInt(8, endereco.getIdEmpresa());

            int linhas = pst.executeUpdate();
            if (linhas > 0) retorno = 1;

        } catch (SQLException e) {
            e.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }
        return retorno;
    }

    // BUSCAR POR ID
    public Endereco buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Endereco endereco = null;
        String sql = "SELECT * FROM endereco WHERE id = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                endereco = new Endereco(
                        rs.getInt("id"),
                        rs.getString("pais"),
                        rs.getString("estado"),
                        rs.getString("cidade"),
                        rs.getString("bairro"),
                        rs.getString("rua"),
                        rs.getInt("numero"),
                        rs.getString("cep"),
                        rs.getInt("id_empresa")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }
        return endereco;
    }
    // BUSCAR POR PAÍS
    public List<Endereco> buscarPorPais(String pais) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Endereco> lista = new ArrayList<>();
        String sql = "SELECT * FROM endereco WHERE pais = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, pais);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                lista.add(new Endereco(
                        rs.getInt("id"),
                        rs.getString("pais"),
                        rs.getString("estado"),
                        rs.getString("cidade"),
                        rs.getString("bairro"),
                        rs.getString("rua"),
                        rs.getInt("numero"),
                        rs.getString("cep"),
                        rs.getInt("id_empresa")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return lista;
    }

    // BUSCAR POR ESTADO
    public List<Endereco> buscarPorEstado(String estado) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Endereco> lista = new ArrayList<>();
        String sql = "SELECT * FROM endereco WHERE estado = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, estado);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                lista.add(new Endereco(
                        rs.getInt("id"),
                        rs.getString("pais"),
                        rs.getString("estado"),
                        rs.getString("cidade"),
                        rs.getString("bairro"),
                        rs.getString("rua"),
                        rs.getInt("numero"),
                        rs.getString("cep"),
                        rs.getInt("id_empresa")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return lista;
    }

    // BUSCAR POR CEP
    public List<Endereco> buscarPorCep(String cep) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Endereco> lista = new ArrayList<>();
        String sql = "SELECT * FROM endereco WHERE cep = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, cep);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                lista.add(new Endereco(
                        rs.getInt("id"),
                        rs.getString("pais"),
                        rs.getString("estado"),
                        rs.getString("cidade"),
                        rs.getString("bairro"),
                        rs.getString("rua"),
                        rs.getInt("numero"),
                        rs.getString("cep"),
                        rs.getInt("id_empresa")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return lista;
    }


    // LISTAR TODOS ENDEREÇOS
    public List<Endereco> listarTodos() {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Endereco> lista = new ArrayList<>();
        String sql = "SELECT * FROM endereco";

        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Endereco(
                        rs.getInt("id"),
                        rs.getString("pais"),
                        rs.getString("estado"),
                        rs.getString("cidade"),
                        rs.getString("bairro"),
                        rs.getString("rua"),
                        rs.getInt("numero"),
                        rs.getString("cep"),
                        rs.getInt("id_empresa")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }
        return lista;
    }

    // ATUALIZAR ENDEREÇO
    public int atualizarEndereco(Endereco endereco) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;
        String sql = "UPDATE endereco SET pais=?, estado=?, cidade=?, bairro=?, rua=?, numero=?, cep=?, id_empresa=? WHERE id=?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, endereco.getPais());
            pst.setString(2, endereco.getEstado());
            pst.setString(3, endereco.getCidade());
            pst.setString(4, endereco.getBairro());
            pst.setString(5, endereco.getRua());
            pst.setInt(6, endereco.getNumero());
            pst.setString(7, endereco.getCep());
            pst.setInt(8, endereco.getIdEmpresa());
            pst.setInt(9, endereco.getId());

            int linhas = pst.executeUpdate();
            if (linhas > 0) retorno = 1;

        } catch (SQLException e) {
            e.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }
        return retorno;
    }

    // DELETAR ENDEREÇO
    public int deletarEndereco(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;
        String sql = "DELETE FROM endereco WHERE id=?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            int linhas = pst.executeUpdate();
            if (linhas > 0) retorno = 1;
        } catch (SQLException e) {
            e.printStackTrace();
            retorno = -1;
        } finally {
            conexao.desconectar(con);
        }
        return retorno;
    }

}
