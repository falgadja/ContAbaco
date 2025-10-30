package servlet.FuncionarioServlet;

import dao.FuncionarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// 1. URL "limpa"
@WebServlet("/funcionarios-delete")
public class DeletarFuncionarioServlet extends HttpServlet {

    /**
     * 2. CORREÇÃO: doGet agora redireciona (Evita delete por GET)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/funcionarios");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                request.getSession().setAttribute("mensagemDeletar", "ID do funcionário não foi encontrado.");
            } else {
                int id = Integer.parseInt(idParametro);

                if (funcionarioDAO.deletar(id) > 0) { // (Seu DAO precisa ter 'deletar')
                    request.getSession().setAttribute("mensagemDeletar", "Funcionário deletado com sucesso!");
                } else {
                    request.getSession().setAttribute("mensagemDeletar", "Não foi possível deletar.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagemDeletar", "ID inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagemDeletar", "Erro inesperado ao tentar deletar.");
        }

        // 3. CORREÇÃO: REDIRECIONA para o servlet de listagem
        response.sendRedirect(request.getContextPath() + "/funcionarios");
    }
}