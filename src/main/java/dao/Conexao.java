package dao;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class Conexao {
    Connection conn;
    ResultSet rs;
    PreparedStatement pstmt;

    // Variaveis de ambiente para conectar com o BD
    Dotenv dotenv = Dotenv.load();
    String DB_URL = dotenv.get("DB_URL");
    String DB_USUARIO = dotenv.get("DB_USUARIO");
    String DB_SENHA = dotenv.get("DB_SENHA");


    // metodo para conectar o BD
    public Connection conectar(){
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USUARIO, DB_SENHA);
        } catch (ClassNotFoundException | SQLException sqleCnfe){
            sqleCnfe.printStackTrace();
        }
        return conn;
    }


    // metodo para desconectar o BD
    public boolean desconectar(Connection conn){
        try {
            if (conn != null && conn.isClosed()) {
                conn.close();
                return true;
            }
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }
}
