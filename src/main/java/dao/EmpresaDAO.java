package dao;

import model.Empresa;
import model.Funcionario;
import model.Login;

import java.sql.SQLException;

public class EmpresaDAO {

    Conexao conexao = new Conexao();

    // Busca uma Empresa pelo ID
    public Empresa buscarPorID(int id) {
        try {
            conexao.conectar();

            conexao.pstmt = conexao.conn.prepareStatement("SELECT * FROM EMPRESA WHERE ID = ?");
            conexao.pstmt.setInt(1, id);

            conexao.rs = conexao.pstmt.executeQuery();

            if (conexao.rs.next()) {

                int loginId = conexao.rs.getInt("id_login");
                LoginDAO loginDAO = new LoginDAO();
                Login login = loginDAO.lerUsuario(loginId);

                int funcionarioID = conexao.rs.getInt("id_login");
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO(funcionarioID);
                Funcionario funcionario = funcionarioDAO.buscarPorId(funcionarioID);

                return new Empresa(
                        conexao.rs.getLong("cnpj"),
                        conexao.rs.getString("nomeEmpresa")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar(conexao.conn);
        }

        return null;
    }
}