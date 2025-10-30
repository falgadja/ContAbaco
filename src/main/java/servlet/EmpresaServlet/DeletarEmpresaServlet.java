package servlet.EmpresaServlet;

import dao.EmpresaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// 1. URL "limpa"
@WebServlet("/empresas-delete")
public class DeletarEmpresaServlet extends HttpServlet {

    /**
     * 2. CORREÇÃO: doGet agora redireciona para a lista (Evita delete por GET)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/empresas");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        EmpresaDAO empresaDAO = new EmpresaDAO();

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                request.getSession().setAttribute("mensagem", "ID da empresa não foi encontrado.");
            } else {
                int id = Integer.parseInt(idParametro);

                if (empresaDAO.deletar(id) > 0) { // (Seu DAO precisa do método 'deletar')
                    request.getSession().setAttribute("mensagem", "Empresa deletada com sucesso!");
                } else {
                    request.getSession().setAttribute("mensagem", "Não foi possível deletar a empresa.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "ID inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao tentar deletar.");
        }

        // 3. CORREÇÃO: REDIRECIONA para o servlet de listagem
        response.sendRedirect(request.getContextPath() + "/empresas");
    }
}