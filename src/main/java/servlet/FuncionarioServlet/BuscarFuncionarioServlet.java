package servlet.FuncionarioServlet;

import dao.FuncionarioDAO;
import filtros.FuncionarioFiltro;
import jakarta.servlet.RequestDispatcher; // Importe
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Importe
import model.Funcionario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 1. URL "limpa" para o menu
@WebServlet("/funcionarios")
public class BuscarFuncionarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 2. Lógica para ler mensagens da sessão (Padrão PRG)
        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        String mensagemDeletar = (String) session.getAttribute("mensagemDeletar");

        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem);
            session.removeAttribute("mensagem");
        } else if (mensagemDeletar != null) {
            request.setAttribute("mensagem", mensagemDeletar); // Unifica na mesma variável
            session.removeAttribute("mensagemDeletar");
        }
        // --- Fim da lógica de mensagens ---

        String nome = request.getParameter("nome");
        String idEmpresa  = request.getParameter("idEmpresa");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        FuncionarioFiltro funcionarioFiltro = new FuncionarioFiltro();
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            // (Toda a sua lógica de filtro e busca continua aqui... ela está ótima)
            if (nome != null && !nome.trim().isEmpty()) {
                String[] partes = nome.split(" ");
                if (partes.length == 1) {
                    funcionarios = funcionarioDAO.buscarPorNome(partes[0]);
                } else {
                    funcionarios = funcionarioDAO.buscarPorNomeESobrenome(partes[0], partes[1]);
                }
                if (funcionarios == null || funcionarios.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum Funcionário com esse nome, digite novamente.");
                }
            } else {
                funcionarios = funcionarioDAO.listar();
                if (funcionarios == null || funcionarios.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum funcionário");
                }
                if (idEmpresa!= null && !idEmpresa.isEmpty()) {
                    int idEmpresaNum = Integer.parseInt(idEmpresa);
                    funcionarios = funcionarioFiltro.filtrarPorIdEmpresa(funcionarios, idEmpresaNum);
                    if (funcionarios.isEmpty()) {
                        request.setAttribute("mensagem", "Não foram encontrados funcionarios com esse ID de empresa, digite novamente.");
                    } else {
                        request.setAttribute("mensagem", "funcionarios encontrados.");
                    }
                }
                if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && funcionarios != null && !funcionarios.isEmpty()) {
                    // ... (lógica de ordenação) ...
                }
            }
            request.setAttribute("funcionarios", funcionarios);
        } catch(NumberFormatException nfe){
            request.setAttribute("mensagem", "ID inválido, digite apenas números inteiros.");
        } catch(Exception e){
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }

        // 3. Encaminha para o JSP (já estava correto)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Funcionario/crudFuncionario.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // O formulário de filtro chama o doGet
        doGet(request, response);
    }
}