package servlet.EnderecoServlet;

import dao.EnderecoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/endereco-delete")
public class DeletarEnderecoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET não deleta, apenas redireciona para a lista de endereços
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/endereco");
    }

    // POST realiza a exclusão do endereço
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        EnderecoDAO enderecoDAO = new EnderecoDAO(); // DAO instanciado aqui
        String mensagem;

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                mensagem = "ID do endereço não foi encontrado.";
            } else {
                int id = Integer.parseInt(idParametro);

                if (enderecoDAO.deletar(id) > 0) {
                    mensagem = "Endereço deletado com sucesso!";
                } else {
                    mensagem = "Não foi possível deletar o endereço.";
                }
            }
        } catch (NumberFormatException nfe) {
            mensagem = "ID inválido.";
        } catch (Exception e) {
            e.printStackTrace();
            mensagem = "Erro inesperado ao tentar deletar o endereço.";
        }

        // Salva a mensagem na sessão e redireciona para a listagem
        request.getSession().setAttribute("mensagemDeletar", mensagem);
        response.sendRedirect(request.getContextPath() + "/empresa");
    }
}
