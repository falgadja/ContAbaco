package dao;

// IMPORTS DA CLASSE
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexao.Conexao;
import model.Endereco;

public class EnderecoDAO {

    // CREATE - INSERIR Endereco
    public int inserir(Endereco endereco) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        String sql = "INSERT INTO endereco (pais, estado, cidade, bairro, rua, numero, cep, id_empresa) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        int idGerado = -1;

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, endereco.getPais());
            pst.setString(2, endereco.getEstado());
            pst.setString(3, endereco.getCidade());
            pst.setString(4, endereco.getBairro());
            pst.setString(5, endereco.getRua());
            pst.setInt(6, endereco.getNumero());
            pst.setString(7, endereco.getCep());
            pst.setInt(8, endereco.getIdEmpresa());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idGerado = rs.getInt("id");
                endereco.setId(idGerado);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return idGerado; // Retorna o ID gerado se der certo, se falhar retorna -1
    }

    // READ - BUSCAR Endereco PELO ID
    public Endereco buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Endereco endereco = null;
        String sql = "SELECT * FROM endereco WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
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

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return endereco; // se não encontrar retorna null, se encontrar retorna o objeto
    }

    // READ - BUSCAR Endereco PELO ID DA EMPRESA
    public Endereco buscarPorEmpresa(int idEmpresa) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Endereco endereco = null;
        String sql = "SELECT * FROM endereco WHERE id_empresa = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, idEmpresa);

            ResultSet rs = pst.executeQuery();

            // Se encontrar algum endereço para a empresa, retorna o primeiro
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

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return endereco; // retorna null se não encontrar
    }

    // READ - BUSCAR Endereco PELO CEP
    public Endereco buscarPorCEP(String cep) { // <-- trocar int por String
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Endereco endereco = null;
        String sql = "SELECT * FROM endereco WHERE cep = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, cep); // <-- usar setString

            ResultSet rs = pst.executeQuery();

            // Se encontrar algum endereço, retorna o primeiro
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

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return endereco; // retorna null se não encontrar
    }


    // READ - LISTAR TODOS OS Enderecos
    public List<Endereco> listar() {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM endereco";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                enderecos.add(new Endereco(
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

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return enderecos; // retorna a lista, vazia se não encontrar nada
    }

    // UPDATE - ATUALIZAR Endereco
    public int atualizar(Endereco endereco) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno;
        String sql = "UPDATE endereco SET pais=?, estado=?, cidade=?, bairro=?, rua=?, numero=?, cep=?, id_empresa=? WHERE id=?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, endereco.getPais());
            pst.setString(2, endereco.getEstado());
            pst.setString(3, endereco.getCidade());
            pst.setString(4, endereco.getBairro());
            pst.setString(5, endereco.getRua());
            pst.setInt(6, endereco.getNumero());
            pst.setString(7, endereco.getCep());
            pst.setInt(8, endereco.getIdEmpresa());
            pst.setInt(9, endereco.getId());

            retorno = pst.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1; // Se ocorreu alguma exceção retorna -1
        } finally {
            conexao.desconectar(con);
        }

        return retorno; // Se tiver atualizado o Endereco, retorna o numero de linhas alteradas, se não retorna 0
    }

    // DELETE - DELETAR Endereco
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno;
        String sql = "DELETE FROM endereco WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);

            retorno = pst.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno = -1; // Se ocorreu alguma exceção retorna -1
        } finally {
            conexao.desconectar(con);
        }

        return retorno; // Se tiver deletado o Endereco, retorna o numero de linhas alteradas, se não retorna 0
    }
}
