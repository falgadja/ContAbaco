package dao;

import conexao.Conexao;
import model.Administrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmDAO {
    //Inserir
    public int inserirAdm(Administrador adm) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        String sql = "INSERT INTO ADM(EMAIL,SENHA) VALUES (?, ?)RETURNING id";
        int idGerado = -1;

        try{
            PreparedStatement pst= con.prepareStatement(sql);
            pst.setString(1, adm.getEmail());
            pst.setString(2,adm.getSenha());

            ResultSet rs= pst.executeQuery();
            if(rs.next()){
            idGerado = rs.getInt("id");
            adm.setId(idGerado); // Define o ID no objeto
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        conexao.desconectar(con);
    }

        return idGerado; // Retorna o ID gerado ou -1 se falhar
}
    //Atualizar adm
    public int atualizarAdm(Administrador adm) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno=0;
        String sql="UPDATE ADM SET EMAIL=?,SENHA=? WHERE ID=?";

        try{
            PreparedStatement pst= con.prepareStatement(sql);
            pst.setString(1, adm.getEmail());
            pst.setString(2, adm.getSenha());
            pst.setInt(3, adm.getId());
            retorno= pst.executeUpdate();
            if(retorno>0){
                retorno=1;
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
            retorno=-1;
        }finally {
            conexao.desconectar(con);
        }
        return retorno;
    }
    //Deletar adm
    public int deletarAdm(int id) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno=0;
        String sql="DELETE FROM ADM WHERE ID=?";

        try {
            PreparedStatement pst= con.prepareStatement(sql);
            pst.setInt(1,id);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }finally {
            conexao.desconectar(con);
        }
        return retorno;

    }

    //Buscar por email e senha
    public Administrador buscarPorEmailSenha(String email, String senha) {
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        Administrador adm = null;
        String sql="SELECT * FROM ADM WHERE EMAIL=? AND SENHA=?";

        try{
            PreparedStatement pst= con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2,senha);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                adm= new Administrador(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("senha")

                );
        }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }finally {
            conexao.desconectar(con);
        }
        return adm;
}











































































}


