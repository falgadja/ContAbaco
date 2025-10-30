package servlet.FuncionarioServlet;

import dao.EmpresaDAO; // Importe
import dao.FuncionarioDAO;
import dao.SetorDAO; // Importe
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa; // Importe
import model.Funcionario;
import model.Setor; // Importe
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List; // Importe

@WebServlet("/funcionarios-update")
public class AtualizarFuncionarioServlet extends HttpServlet {

    /**
     * CORRIGIDO: Agora também busca as listas para os dropdowns.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        if (idParametro == null || idParametro.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID do funcionário não encontrado para edição.");
            response.sendRedirect(request.getContextPath() + "/funcionarios");
            return;
        }

        try {
            int id = Integer.parseInt(idParametro);

            // 1. Buscar o funcionário
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            Funcionario funcionario = funcionarioDAO.buscarPorId(id);

            // 2. Buscar as listas
            SetorDAO setorDAO = new SetorDAO();
            EmpresaDAO empresaDAO = new EmpresaDAO();
            List<Setor> setores = setorDAO.listarTodos(); // (Assumindo que seu SetorDAO tem 'listarTodos')
            List<Empresa> empresas = empresaDAO.listar();

            if (funcionario == null) {
                request.getSession().setAttribute("mensagem", "Funcionário não encontrado (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            // 3. Enviar TUDO para a JSP
            request.setAttribute("funcionarioParaEditar", funcionario);
            request.setAttribute("setores", setores);
            request.setAttribute("empresas", empresas);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Funcionario/atualizarFuncionario.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar dados para edição: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/funcionarios");
        }
    }

    /**
     * Seu método doPost (que você já tinha e estava correto) continua aqui...
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // (Todo o seu código doPost... ele já está correto e redireciona para /funcionarios)
        // ...
    }
}