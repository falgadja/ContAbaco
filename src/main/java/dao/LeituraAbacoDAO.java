package dao;

import conexao.Conexao;
import model.LeituraAbaco;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LeituraAbacoDAO {
    //CONEXÃO COM O BANCO
    private Connection conexao;

    //INSERIR LEITURA DO ABACO
    public int inserirLeituraAbaco(LeituraAbaco leituraAbaco) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int idGerado = 0;
        String sql = "INSERT INTO leitura_abaco(imagem, data_hora) VALUES(?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setBytes(1, leituraAbaco.getImagem());
            pst.setTimestamp(2, java.sql.Timestamp.valueOf(leituraAbaco.getDataHora())); //Converte o localDateTime para o tipo aceito pelo JDBC


            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idGerado = rs.getInt("id");
                leituraAbaco.setId(idGerado); // Define o ID no objeto
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(con);
        }

        return idGerado; // Retorna o ID gerado ou -1 se falhar
    }
    //ATUALIZAR
    public int atualizar(int id, LeituraAbaco leituraAbaco) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        String sql = "UPDATE leitura_abaco SET data_hora = ?, imagem=? WHERE id = ?";
        int retorno = 0;

        try{
            PreparedStatement pst= con.prepareStatement(sql);{
                pst.setTimestamp(1,java.sql.Timestamp.valueOf(leituraAbaco.getDataHora()));
                pst.setBytes(2,leituraAbaco.getImagem() );
                pst.setInt(2,id);

                int linhas= pst.executeUpdate();
                if (linhas > 0) {
                    retorno = 1;
                }
            }

        }catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno=-1;
        }
        finally {
            conexao.desconectar(con);
        }
        return retorno;

    }

    // DELETAR REGISTRO

    public int deletarLeituraAbaco(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        String sql = "DELETE FROM leitura_abaco WHERE id = ?";
        int retorno = 0;

        try {
            PreparedStatement pst= con.prepareStatement(sql);{
                pst.setInt(1, id);

                int linhas= pst.executeUpdate();
                if (linhas > 0) {
                    retorno = 1;
                }
            }
        }catch (SQLException sqle) {
            sqle.printStackTrace();
            retorno=-1;
        }
        finally {
            conexao.desconectar(con);
        }
        return retorno;
    }

    //LISTAR REGISTROS DA LEITURA DO ABACO

    public List<LeituraAbaco> listarLeituraAbaco() {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        List<LeituraAbaco> lista = new ArrayList<LeituraAbaco>();
        String sql = "SELECT * FROM leitura_abaco";

        try {
            Statement stm= con.createStatement();
            ResultSet rs= stm.executeQuery(sql);

            while(rs.next()){
                lista.add(new LeituraAbaco(
                        rs.getInt("id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        rs.getBytes("imagem")


                ));

            }


        }catch (SQLException sqle){
            sqle.printStackTrace();

        }finally {
            conexao.desconectar(con);
        }
        return lista;
    }

    // BUSCAR POR ID
    public LeituraAbaco buscarPorId(int idBusca) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        LeituraAbaco leitura = null;
        String sql = "SELECT * FROM leitura_abaco WHERE id = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, idBusca);

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

        return leitura; // se não achar, volta null
    }


    // BUSCAR POR DATA E HORA

    public LeituraAbaco buscarPorDataHora(LocalDateTime dataHoraBusca) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        LeituraAbaco leitura = null;
        String sql = "SELECT * FROM leitura_abaco WHERE data_hora = ?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setTimestamp(1, java.sql.Timestamp.valueOf(dataHoraBusca));

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

        return leitura; // retorna null se não encontrar
    }






}
