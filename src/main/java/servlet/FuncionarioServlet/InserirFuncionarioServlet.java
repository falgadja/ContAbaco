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
import java.time.format.DateTimeParseException;
import java.util.List; // Importe
import java.util.regex.Pattern;

@WebServlet("/funcionarios-create")
public class InserirFuncionarioServlet extends HttpServlet {

    // ... (Seus regex, etc. continuam aqui) ...

    /**
     * CORRIGIDO: Agora também busca as listas para os dropdowns.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Buscar as listas
            SetorDAO setorDAO = new SetorDAO();
            EmpresaDAO empresaDAO = new EmpresaDAO();
            List<Setor> setores = setorDAO.listarTodos(); // (Assumindo que seu SetorDAO tem 'listarTodos')
            List<Empresa> empresas = empresaDAO.listar();

            // 2. Enviar as listas para a JSP
            request.setAttribute("setores", setores);
            request.setAttribute("empresas", empresas);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao carregar listas de setores/empresas.");
        }

        // 3. Encaminhar para o formulário
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Funcionario/cadastrarFuncionario.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Seu método doPost (que você já tinha) continua aqui...
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // (Todo o seu código doPost... ele já está correto e redireciona para /funcionarios)
        // ...
    }
}