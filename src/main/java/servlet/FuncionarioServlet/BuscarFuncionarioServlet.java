package servlet.FuncionarioServlet;

// IMPORTS NECESSÁRIOS: DAO, FILTRO, SERVLET, SESSION, MODEL E STREAMS
import dao.FuncionarioDAO;
import filtros.FuncionarioFiltro;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Empresa;
import model.Funcionario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SERVLET RESPONSÁVEL POR BUSCAR E LISTAR FUNCIONÁRIOS
 * PERMITE PESQUISA POR NOME, FILTRO POR EMPRESA, ORDENAR RESULTADOS
 * EXIBE MENSAGENS TEMPORÁRIAS (PADRÃO PRG)
 */
@WebServlet("/funcionarios")
public class BuscarFuncionarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // PEGANDO MENSAGENS TEMPORÁRIAS DA SESSÃO (PADRÃO PRG)
        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem); // COLOCA MENSAGEM NO REQUEST
            session.removeAttribute("mensagem"); // REMOVE DA SESSÃO PRA NÃO REPETIR
        }

        // RECEBE PARAMETROS DE PESQUISA E ORDENACAO
        String nome = request.getParameter("nome");
        String idEmpresa = request.getParameter("idEmpresa");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        // CRIA OBJETOS NECESSÁRIOS: DAO, FILTRO E LISTA
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        FuncionarioFiltro funcionarioFiltro = new FuncionarioFiltro();
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            // PESQUISA POR NOME SE INFORMADO
            if (nome != null && !nome.trim().isEmpty()) {
                funcionarios = funcionarioDAO.buscarPorNome(nome);
                if (funcionarios == null) {
                    request.setAttribute("mensagem", "Nenhum funcionário encontrado com esse nome. Tente novamente.");
                } else {
                    request.setAttribute("mensagem", "Funcionários encontrados com sucesso.");
                    request.setAttribute("funcionarios", funcionarios);
                }
            } else {
                // SE NOME NÃO INFORMADO, LISTA TODOS FUNCIONÁRIOS
                funcionarios = funcionarioDAO.listar();
                if (funcionarios == null || funcionarios.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum funcionário registrado no sistema.");
                } else {
                    // FILTRA POR ID DA EMPRESA SE INFORMADO
                    if (idEmpresa != null && !idEmpresa.isEmpty()) {
                        int idEmpresaNum = Integer.parseInt(idEmpresa);
                        funcionarios = funcionarioFiltro.filtrarPorIdEmpresa(funcionarios, idEmpresaNum);
                    }

                    if (funcionarios.isEmpty()) {
                        request.setAttribute("mensagem", "Não foi encontrado nenhum funcionário no sistema com os filtros aplicados.");
                    } else {
                        request.setAttribute("mensagem", "Foram encontrados " + funcionarios.size() + " funcionários.");
                    }

                    // ORDENACAO CASO TENHA SIDO ESCOLHIDA
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

            // DEFINE LISTA FINAL COMO ATRIBUTO DO REQUEST
            request.setAttribute("funcionarios", funcionarios);

        } catch (NumberFormatException nfe) {
            // TRATA ERRO QUANDO ID DA EMPRESA NÃO É NÚMERO
            nfe.printStackTrace();
            request.setAttribute("mensagem", "ID inválido, digite apenas números inteiros.");

        } catch (Exception e) {
            // ERROS INESPERADOS
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");

        } finally {
            // ENCAMINHA PARA O JSP RESPONSÁVEL PELO CRUD DE FUNCIONÁRIOS
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Funcionario/crudFuncionario.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // REDIRECIONA POST PARA O MESMO FLUXO DO GET
        doGet(request, response);
    }
}
