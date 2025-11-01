package servlet.EmpresaServlet;

import dao.EmpresaDAO;
import dao.EnderecoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Endereco;

import java.io.IOException;

@WebServlet("/empresa-delete")
public class DeletarEmpresaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET não deleta, apenas redireciona para a listagem
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/empresas");
    }

    // POST realiza a exclusão da empresa
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        // DAOs instanciadas
        EmpresaDAO empresaDAO = new EmpresaDAO();
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        Endereco endereco = new Endereco();


        String mensagem;

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                mensagem = "ID da empresa não foi encontrado.";
            } else {
                int id = Integer.parseInt(idParametro);
                endereco = enderecoDAO.buscarPorEmpresa(id);

                if (enderecoDAO.deletar(endereco.getId())>0 && empresaDAO.deletar(id)>0) {
                    mensagem = "Empresa deletada com sucesso!";
                } else {
                    mensagem = "Não foi possível deletar a empresa.";
                }
            }
        } catch (NumberFormatException nfe) {
            mensagem = "ID inválido.";
        } catch (Exception e) {
            e.printStackTrace();
            mensagem = "Erro inesperado ao tentar deletar.";
        }

        // Salva a mensagem e redireciona para a lista
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/empresas");
    }
}
