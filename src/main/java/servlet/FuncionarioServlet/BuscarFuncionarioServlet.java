package servlet.FuncionarioServlet; 

import dao.FuncionarioDAO; 
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Funcionario;
import filtros.FuncionarioFiltro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Mapeado para a URL de listagem
@WebServlet("/funcionarios")
public class BuscarFuncionarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lógica de Mensagens (PRG Pattern)
        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem);
            session.removeAttribute("mensagem");
        }

        // --- Variáveis de Filtro e Ordenação ---
        String nomeFiltro = request.getParameter("nome");
        // Parâmetro de filtro por ID da Empresa
        String idEmpresaFiltro = request.getParameter("idEmpresa");
        // Parâmetro de ordenação
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        FuncionarioFiltro funcionarioFiltro = new FuncionarioFiltro();
        List<Funcionario> funcionarios = new ArrayList<>();
        String mensagemExibicao = null;

        try {
            // 1. Obter a lista completa de funcionários
            List<Funcionario> listaCompleta = funcionarioDAO.listar();
            funcionarios = listaCompleta;

            // 2. Aplicar FILTRO por nome (Busca flexível: contém)
            if (nomeFiltro != null && !nomeFiltro.trim().isEmpty()) {
                final String nomeLower = nomeFiltro.trim().toLowerCase();

                funcionarios = funcionarios.stream()
                        .filter(f -> f.getNome() != null && f.getNome().toLowerCase().contains(nomeLower))
                        .collect(Collectors.toList());

                mensagemExibicao = "Filtrando por nome: foram encontrados " + funcionarios.size() + " funcionários.";
            }

            // 3. Aplicar FILTRO por ID da Empresa (se o parâmetro for válido)
            if (idEmpresaFiltro != null && !idEmpresaFiltro.trim().isEmpty()) {
                try {
                    int idEmpresa = Integer.parseInt(idEmpresaFiltro.trim());
                    // Usa o método da sua classe FuncionarioFiltro
                    funcionarios = funcionarioFiltro.filtrarPorIdEmpresa(funcionarios, idEmpresa);
                    mensagemExibicao = "Filtrando por ID da Empresa (" + idEmpresa + "): " + funcionarios.size() + " encontrados.";
                } catch (NumberFormatException e) {
                    mensagemExibicao = "O ID da Empresa fornecido não é um número válido.";
                }
            }


            // 4. Aplicar ORDENAÇÃO (se o parâmetro 'tipoOrdenacao' foi enviado e a lista não estiver vazia)
            if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && !funcionarios.isEmpty()) {
                switch (tipoOrdenacao) {
                    case "idCrescente":
                        funcionarios = funcionarioFiltro.OrdenarIdCrece(funcionarios);
                        break;
                    case "idDecrescente":
                        funcionarios = funcionarioFiltro.OrdenarIdDecre(funcionarios);
                        break;
                    case "Az":
                        funcionarios = funcionarioFiltro.OrdenarNomeAz(funcionarios);
                        break;
                    case "Za":
                        funcionarios = funcionarioFiltro.OrdenarNomeZa(funcionarios);
                        break;
                    default:
                        // Nenhuma ordenação específica ou tipo inválido
                        break;
                }
            }

            // 5. Lidar com lista vazia no final
            if (funcionarios.isEmpty() && mensagemExibicao == null) {
                mensagemExibicao = "Nenhum funcionário cadastrado (ou nenhum correspondente aos filtros).";
            }

            // 6. Configurar atributos para a JSP
            request.setAttribute("funcionarios", funcionarios);
            // Prioriza a mensagem do fluxo PRG, senão usa a mensagem de filtro/lista
            if (request.getAttribute("mensagem") == null && mensagemExibicao != null) {
                request.setAttribute("mensagem", mensagemExibicao);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao buscar funcionários no banco de dados.");
        }

        // 7. Encaminha para o JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Funcionario/crudFuncionario.jsp"); // Ajuste o caminho
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}