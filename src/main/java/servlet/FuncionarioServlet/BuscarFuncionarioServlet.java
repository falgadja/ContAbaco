package servlet.FuncionarioServlet;

// Imports da classe
import dao.FuncionarioDAO;
import filtros.FuncionarioFiltro;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Funcionario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servlet responsável por buscar e listar funcionários.
 * Permite pesquisa por nome, empresa e ordenação,
 * além de exibir mensagens temporárias armazenadas na sessão (Padrão PRG).
 */
@WebServlet("/funcionarios")
public class BuscarFuncionarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leitura de mensagens temporárias da sessão (Padrão PRG)
        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        String mensagemDeletar = (String) session.getAttribute("mensagemDeletar");

        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem);
            session.removeAttribute("mensagem");
        }

        // Recebe parâmetros de pesquisa e ordenação do JSP
        String nome = request.getParameter("nome");
        String idEmpresa = request.getParameter("idEmpresa");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        // Instancia DAO e filtro
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        FuncionarioFiltro funcionarioFiltro = new FuncionarioFiltro();
        List<Funcionario> funcionarios = new ArrayList<>();
        String mensagemExibicao = null;

        try {
            // Lista todos os funcionários se não houver pesquisa por nome
            funcionarios = funcionarioDAO.listar();

            if (funcionarios == null || funcionarios.isEmpty()) {
                request.setAttribute("mensagem", "Nenhum funcionário encontrado.");
            }


            // lógica diferente para pesquisa por nome de funcionário
            if (nome != null && !nome.isBlank()) {
                if (nome != null && !nome.trim().isEmpty()) {
                    final String nomeLower = nome.trim().toLowerCase();

                    funcionarios = funcionarios.stream()
                            .filter(f -> f.getNome() != null && f.getNome().toLowerCase().contains(nomeLower))
                            .collect(Collectors.toList());

                    mensagemExibicao = "Filtrando por nome: foram encontrados " + funcionarios.size() + " funcionários.";
                }
            } else {
                // Lista todos os funcionários se não houver pesquisa por nome
                funcionarios = funcionarioDAO.listar();

                if (funcionarios == null || funcionarios.isEmpty()) {
                    request.setAttribute("mensagem", "Nenhum funcionário encontrado.");
                } else {

                    // Filtra por ID da empresa se informado
                    if (idEmpresa != null && !idEmpresa.isEmpty()) {
                        int idEmpresaNum = Integer.parseInt(idEmpresa);
                        funcionarios = funcionarioFiltro.filtrarPorIdEmpresa(funcionarios, idEmpresaNum);
                        if (funcionarios.isEmpty()) {
                            request.setAttribute("mensagem", "Nenhum funcionário encontrado para essa empresa.");
                        } else {
                            request.setAttribute("mensagem", "Funcionários encontrados para a empresa informada.");
                        }
                    }

                    // Ordena a lista de funcionários caso tipoOrdenacao seja informado
                    if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && !funcionarios.isEmpty()) {
                        if (tipoOrdenacao.equals("idCrescente")) {
                            funcionarios = funcionarioFiltro.OrdenarIdCrece(funcionarios);
                        } else if (tipoOrdenacao.equals("idDecrescente")) {
                            funcionarios = funcionarioFiltro.OrdenarIdDecre(funcionarios);
                        } else if (tipoOrdenacao.equals("Az")) {
                            funcionarios = funcionarioFiltro.OrdenarNomeAz(funcionarios);
                        } else if (tipoOrdenacao.equals("Za")) {
                            funcionarios = funcionarioFiltro.OrdenarNomeZa(funcionarios);
                        }
                    }
                }
            }
            // Define a lista de funcionários como atributo do request
            request.setAttribute("funcionarios", funcionarios);

        } catch (NumberFormatException nfe) {
            // ID inválido
            nfe.printStackTrace();
            request.setAttribute("mensagem", "ID inválido, digite apenas números inteiros.");

        } catch (Exception e) {
            // Erro inesperado
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }  finally {
            // Encaminha para o JSP correspondente
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Funcionario/crudFuncionario.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona POST para o mesmo fluxo do GET
        doGet(request, response);
    }
}
