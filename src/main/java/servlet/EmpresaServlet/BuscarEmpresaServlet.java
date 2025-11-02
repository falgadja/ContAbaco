package servlet.EmpresaServlet;

// Imports da classe
import dao.EmpresaDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Empresa;
import filtros.EmpresaFiltro;

/**
 * Servlet responsável por buscar e listar pagamentos.
 * Permite pesquisa por nome, filtro de quantidade de funcionários e ordenação da lista de resultados,
 * Lida também com mensagens temporárias armazenadas na sessão (Padrão PRG).
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/empresas")
public class BuscarEmpresaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leitura de mensagens temporárias da sessão (Padrão PRG)
        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem);
            session.removeAttribute("mensagem"); // Limpa a mensagem da sessão
        }

        // Recebe parâmetros de pesquisa e ordenação do JSP
        String nome = request.getParameter("filtroNome");
        String min = request.getParameter("filtroMinFuncionarios");
        String max = request.getParameter("filtroMaxFuncionarios");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        // Instancia DAO, filtro e a lista de resultados

        EmpresaDAO empresaDAO = new EmpresaDAO();
        EmpresaFiltro empresaFiltro = new EmpresaFiltro();
        List<Empresa> empresas = new ArrayList<>();

        try {
            // Pesquisa por nome se informado
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
                // Lista todas as empresas se não houver pesquisa por nome
                 empresas = empresaDAO.listar();
                if (empresas == null || empresas.isEmpty()) {
                    request.setAttribute("mensagemLista", "Não foi encontrado nenhuma empresa");
                } else {
                    request.setAttribute("empresas", empresas);
                }
                // Filtra por quantidade de funcionários casp seja informado um min. e max.
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
                // Ordena a lista de pagamentos caso tipoOrdenacao seja informado
                if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && empresas != null && !empresas.isEmpty()) {
                    if (tipoOrdenacao.equals("idCrescente")) {
                        empresas = empresaFiltro.OrdenarIdCrece(empresas);
                    } else if (tipoOrdenacao.equals("idDecrescente")) {
                        empresas = empresaFiltro.OrdenarIdDecre(empresas);
                    } else if (tipoOrdenacao.equals("Az")) {
                        empresas = empresaFiltro.OrdenarNomeAz(empresas);
                    } else if (tipoOrdenacao.equals("Za")) {
                        empresas = empresaFiltro.OrdenarNomeZa(empresas);
                    }  else if (tipoOrdenacao.equals("qtndFuncionarioCrescente")) {
                        empresas = empresaFiltro.OrdenarQntdFuncionarioCrece(empresas);
                    } else if (tipoOrdenacao.equals("qtndFuncionarioDecrescente")) {
                        empresas = empresaFiltro.OrdenarQntdFuncionarioDecre(empresas);
                    }
                }

                // Define a lista de empresas como atributo do request
                request.setAttribute("listaEmpresas", empresas);

            }
        } catch(NumberFormatException nfe){
            // Quantidade de funcionários inválida
            request.setAttribute("mensagem", "Número minímo ou maxímo inválido, digite apenas números inteiros.");
        } catch (Exception e) {
            // Trata erros inesperados
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }


        // Encaminha para o JSP correspondente
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