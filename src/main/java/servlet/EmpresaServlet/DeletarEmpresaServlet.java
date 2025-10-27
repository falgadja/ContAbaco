package servlet.EmpresaServlet;

import dao.EmpresaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/DeletarEmpresaServlet")
public class DeletarEmpresaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        EmpresaDAO empresaDAO = new EmpresaDAO();

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                request.setAttribute("mensagemDeletar", "ID da empresa não foi encontrado.");
            } else {
                int id = Integer.parseInt(idParametro);

                if (empresaDAO.deletar(id) > 0) {
                    request.setAttribute("mensagemDeletar", "Empresa deletada com sucesso!");
                } else {
                    request.setAttribute("mensagemDeletar", "Não foi possível deletar.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.setAttribute("mensagemDeletar", "ID inválido.");
        } catch (Exception e) {
            request.setAttribute("mensagemDeletar", "Erro inesperado ao tentar deletar.");
        }

        // Caminho absoluto para o JSP de CRUD da empresa
        response.sendRedirect(request.getContextPath() + "/view/Empresa/crudEmpresa.jsp");
    }
}
