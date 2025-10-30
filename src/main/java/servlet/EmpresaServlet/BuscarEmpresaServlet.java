package servlet.EmpresaServlet;

import dao.EmpresaDAO;
import jakarta.servlet.RequestDispatcher; // Importe
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Importe
import model.Empresa;
import filtros.EmpresaFiltro;
// import model.Funcionario; // (Não usado neste servlet)
// import model.Plano; // (Não usado neste servlet)

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 1. URL "limpa" para o menu
@WebServlet("/empresas")
public class BuscarEmpresaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 2. Lógica para ler mensagens de sucesso/erro (Padrão PRG)
        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem);
            session.removeAttribute("mensagem"); // Limpa a mensagem da sessão
        }
        // (Você pode adicionar outros tipos de mensagem se precisar, ex: "mensagemDeletar")

        // --- Início da sua lógica de filtro (que já estava boa) ---
        String nome = request.getParameter("nome");
        String min = request.getParameter("min");
        String max = request.getParameter("max");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");
        EmpresaDAO empresaDAO = new EmpresaDAO();
        EmpresaFiltro empresaFiltro = new EmpresaFiltro();
        List<Empresa> empresas = new ArrayList<>();

        try {
            if (nome != null && !nome.trim().isEmpty()) {
                Empresa empresa = empresaDAO.buscarPorNome(nome);
                if (empresa == null) {
                    request.setAttribute("mensagemBusca", "Não foi encontrado nenhuma empresa com esse nome, digite novamente.");
                } else {
                    List<Empresa> lista = new ArrayList<>();
                    lista.add(empresa);
                    empresas = lista;
                    request.setAttribute("mensagem", "Empresa encontrada.");
                    request.setAttribute("empresas", empresas);
                }
            } else {
                empresas = empresaDAO.listar();
                if (empresas == null || empresas.isEmpty()) {
                    request.setAttribute("mensagemLista", "Não foi encontrado nenhuma empresa");
                } else {
                    request.setAttribute("empresas", empresas);
                }
                if (min!= null && !min.isEmpty() && max!= null && !max.isEmpty()) {
                    int minNum = Integer.parseInt(min);
                    int maxNum = Integer.parseInt(max);
                    empresas = empresaFiltro.filtrarPorQtdFuncionario(empresas, minNum, maxNum);
                    if (empresas.isEmpty()) {
                        request.setAttribute("mensagem", "Não foram encontradas empresas entre essa faixa de funcionários");
                    } else {
                        request.setAttribute("mensagem", "funcionários encontrados.");
                    }
                }
                if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && empresas != null && !empresas.isEmpty()) {
                    // ... (sua lógica de ordenação) ...
                }
                request.setAttribute("empresas", empresas);
            }
        } catch(NumberFormatException nfe){
            request.setAttribute("mensagem", "Número minímo ou maxímo inválido, digite apenas números inteiros.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }
        // --- Fim da sua lógica de filtro ---

        // 3. Encaminha para o JSP (já estava correto)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Empresa/crudEmpresa.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // O formulário de filtro usa POST, então ele chama o doGet
        doGet(request, response);
    }
}