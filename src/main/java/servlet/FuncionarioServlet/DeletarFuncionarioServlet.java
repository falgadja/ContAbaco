package servlet.FuncionarioServlet;

import dao.FuncionarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/funcionarios-delete")
public class DeletarFuncionarioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET não deleta, apenas redireciona para a lista de funcionários
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/funcionarios");
    }

    // POST realiza a exclusão do funcionário
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(); // DAO instanciado aqui
        String mensagem;

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                mensagem = "ID do funcionário não foi encontrado.";
            } else {
                int id = Integer.parseInt(idParametro);

                if (funcionarioDAO.deletar(id) > 0) {
                    mensagem = "Funcionário deletado com sucesso!";
                } else {
                    mensagem = "Não foi possível deletar o funcionário.";
                }
            }
        } catch (NumberFormatException nfe) {
            mensagem = "ID inválido.";
        } catch (Exception e) {
            e.printStackTrace();
            mensagem = "Erro inesperado ao tentar deletar o funcionário.";
        }

        // Salva a mensagem na sessão e redireciona para a lista
        request.getSession().setAttribute("mensagemDeletar", mensagem);
        response.sendRedirect(request.getContextPath() + "/funcionarios");
    }
}
