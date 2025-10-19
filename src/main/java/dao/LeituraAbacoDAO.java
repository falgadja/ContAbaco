package dao;

import conexao.Conexao;
import model.LeituraAbaco;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LeituraAbacoDAO {

    // CREATE - INSERIR LEITURA DO ABACO
    public int inserir(LeituraAbaco leituraAbaco) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int idGerado = -1;
        String sql = "INSERT INTO leitura_abaco(imagem, data_hora) VALUES(?, ?) RETURNING id";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setBytes(1, leituraAbaco.getImagem());
            pst.setTimestamp(2, Timestamp.valueOf(leituraAbaco.getDataHora()));

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idGerado = rs.getInt("id");
                leituraAbaco.setId(idGerado);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return idGerado;
    }

    // READ - BUSCAR LEITURA PELO ID
    public LeituraAbaco buscarPorId(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        LeituraAbaco leitura = null;
        String sql = "SELECT * FROM leitura_abaco WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                leitura = new LeituraAbaco(
                        rs.getInt("id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        rs.getBytes("imagem")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return leitura;
    }

    // READ - BUSCAR LEITURA PELA DATA E HORA
    public LeituraAbaco buscarPorDataHora(LocalDateTime dataHora) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        LeituraAbaco leitura = null;
        String sql = "SELECT * FROM leitura_abaco WHERE data_hora = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setTimestamp(1, Timestamp.valueOf(dataHora));
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                leitura = new LeituraAbaco(
                        rs.getInt("id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        rs.getBytes("imagem")
                );
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return leitura;
    }

    // READ - LISTAR TODAS LEITURAS
    public List<LeituraAbaco> listar() {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<LeituraAbaco> lista = new ArrayList<>();
        String sql = "SELECT * FROM leitura_abaco";

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                lista.add(new LeituraAbaco(
                        rs.getInt("id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        rs.getBytes("imagem")
                ));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return lista;
    }

    // UPDATE - ATUALIZAR LEITURA DO ABACO
    public int atualizar(LeituraAbaco leituraAbaco) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;
        String sql = "UPDATE leitura_abaco SET data_hora = ?, imagem = ? WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setTimestamp(1, Timestamp.valueOf(leituraAbaco.getDataHora()));
            pst.setBytes(2, leituraAbaco.getImagem());
            pst.setInt(3, leituraAbaco.getId());

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

    // DELETE - DELETAR LEITURA DO ABACO
    public int deletar(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno = 0;
        String sql = "DELETE FROM leitura_abaco WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);

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
}
