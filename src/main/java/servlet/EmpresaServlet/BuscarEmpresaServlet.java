package servlet.EmpresaServlet;

import dao.EmpresaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;

import java.io.IOException;
import java.util.List;

@WebServlet("/BuscarEmpresaServlet")
public class BuscarEmpresaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        EmpresaDAO empresaDAO = new EmpresaDAO();

        try {
            //verifica se aconteceu uma pesquisa por nome
            if (nome != null && !nome.trim().isEmpty()) {
                // Busca a empresa pelo nome
                Empresa empresa = empresaDAO.buscarPorNome(nome);

                // Verifica se existe uma empresa com esse nome
                if (empresa == null) {
                    request.setAttribute("mensagemBusca", "Não foi encontrado nenhuma empresa com esse nome, digite novamente.");
                } else {
                    request.setAttribute("mensagemBusca", "Empresa encontrada.");
                    request.setAttribute("empresa", empresa);
                }

            } else {

                // Lista as empresas
                List<Empresa> empresas = empresaDAO.listar();

                // Verifica se existem empresas registradas
                if (empresas == null || empresas.isEmpty()) {
                    request.setAttribute("mensagemLista", "Não foi encontrado nenhuma empresa");
                } else {
                    request.setAttribute("empresas", empresas);
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