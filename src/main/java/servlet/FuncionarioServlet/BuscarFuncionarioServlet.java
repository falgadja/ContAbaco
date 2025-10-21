package servlet.FuncionarioServlet;

import dao.FuncionarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Funcionario;

import java.io.IOException;
import java.util.List;

@WebServlet("/BuscarFuncionarioServlet")
public class BuscarFuncionarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        try {
            //verifica se aconteceu uma pesquisa por nome
            if (nome != null && !nome.trim().isEmpty()) {
                // Busca o funcionário pelo nome
                int ultimoEspaco = nome.trim().lastIndexOf(" ");
                Funcionario funcionario = funcionarioDAO.buscarPorNomeESobrenome(nome.substring(0, ultimoEspaco), nome.substring(ultimoEspaco + 1));

                // Verifica se existe um funcionário com esse nome
                if (funcionario == null) {
                    request.setAttribute("mensagemBusca", "Não foi encontrado nenhum funcionário com esse nome, digite novamente.");
                } else {
                    request.setAttribute("mensagemBusca", "Funcionário encontrado.");
                    request.setAttribute("funcionário", funcionario);
                }

            } else {

                // Lista os funcionários
                List<Funcionario> funcionarios = funcionarioDAO.listar();

                // Verifica se existem funcionários registrados
                if (funcionarios == null || funcionarios.isEmpty()) {
                    request.setAttribute("mensagemLista", "Não foi encontrado nenhum funcionário");
                } else {
                    request.setAttribute("funcionarios", funcionarios);
                }
            }
        } catch (Exception e) {
            // Qualquer outro erro inesperado
            e.printStackTrace();
            request.setAttribute("mensagemBusca", "Erro inesperado ao acessar o banco de dados.");
        }

        // Encaminha para o JSP
        request.getRequestDispatcher("../CadastrarEmpresa.jsp").forward(request, response);
    }
}