package dao;

import conexao.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.FechamentoTurno;

public class FechamentoTurnoDAO {
    private Conexao conexao;


    //INSERIR
    public int inserirFechamentoTurno(FechamentoTurno fechamentoTurno){
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();
        int retorno=0;
        String sql= "INSERT INTO fechamento_turno (lote,data,id_funcionario,id_leitura) VALUES (?,?,?,?)";

        try {
            PreparedStatement pst= con.prepareStatement(sql);{
                pst.setString(1,fechamentoTurno.getLote());
                pst.setTimestamp(2,java.sql.Timestamp.valueOf(fechamentoTurno.getDataRegistro()));
                pst.setInt(3,fechamentoTurno.getIdFuncionario());
                pst.setInt(4,fechamentoTurno.getIdLeitura());

                int linhas=pst.executeUpdate();
                if (linhas > 0) {
                    retorno=1;
                }
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
            retorno=-1;
        }
        finally{
            conexao.desconectar(con);
        }
        return retorno;
    }




}
