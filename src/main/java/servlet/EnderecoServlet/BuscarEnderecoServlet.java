package servlet.EnderecoServlet;

// IMPORTS NECESSÁRIOS: DAO, FILTRO, SERVLET, SESSION, MODEL E VALIDAÇÃO DE CEP
import dao.EnderecoDAO;
import filtros.EnderecoFiltro;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Endereco;
import utils.ValidacaoRegex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SERVLET RESPONSÁVEL POR BUSCAR E LISTAR ENDEREÇOS
 * PERMITE PESQUISA POR CEP, FILTROS POR ESTADO, ORDENAR RESULTADOS
 * TRATA MENSAGENS TEMPORÁRIAS (PADRÃO PRG)
 */
@WebServlet("/endereco")
public class BuscarEnderecoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // PEGANDO MENSAGENS TEMPORÁRIAS DA SESSÃO (PADRÃO PRG)
        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem); // JOGA MENSAGEM NO REQUEST
            session.removeAttribute("mensagem"); // REMOVE DA SESSÃO PRA NÃO REPETIR
        }

        // RECEBE PARAMETROS DO FORMULÁRIO
        String cepParam = request.getParameter("cep");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");
        String[] estadosParam = request.getParameterValues("estados");

        // TRANSFORMA ARRAY EM LIST PARA FACILITAR FILTRO
        List<String> estados = (estadosParam != null) ? List.of(estadosParam) : new ArrayList<>();

        // CRIA OBJETOS NECESSÁRIOS: DAO, FILTRO E LISTA
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        EnderecoFiltro enderecoFiltro = new EnderecoFiltro();
        List<Endereco> enderecos = new ArrayList<>();

        try {
            if (cepParam != null && !cepParam.trim().isEmpty()) {
                // VALIDA E BUSCA POR CEP
                String cepValidado = ValidacaoRegex.verificarCep(cepParam);
                if (cepValidado != null) {
                    Endereco endereco = enderecoDAO.buscarPorCEP(Integer.parseInt(cepValidado));
                    if (endereco != null) {
                        enderecos.add(endereco);
                        request.setAttribute("mensagem", "Endereço encontrado com sucesso.");
                    } else {
                        request.setAttribute("mensagem", "Nenhum endereço encontrado com esse CEP. Tente novamente.");
                    }
                } else{
                    request.setAttribute("mensagem", "CEP inválido. Verifique e tente novamente.");
                }
            } else {
                // LISTA TODOS OS ENDEREÇOS SE CEP NÃO FOR INFORMADO
                enderecos = enderecoDAO.listar();

                // FILTRA POR ESTADO SE ALGUM FOI SELECIONADO
                if (!estados.isEmpty()) {
                    enderecos = enderecoFiltro.filtrarPorEstado(enderecos, estados);
                }

                // DEFINE MENSAGEM DE ACORDO COM O RESULTADO
                if (enderecos.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum endereço no sistema com os filtros aplicados.");
                } else {
                    request.setAttribute("mensagem", "Foram encontrados " + enderecos.size() + " endereços.");
                }

                // ORDENACAO CASO TENHA SIDO ESCOLHIDA
                if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && !enderecos.isEmpty()) {
                    if (tipoOrdenacao.equals("idCrescente")) {
                        enderecos = enderecoFiltro.OrdenarIdCrece(enderecos);
                    } else if (tipoOrdenacao.equals("idDecrescente")) {
                        enderecos = enderecoFiltro.OrdenarIdDecre(enderecos);
                    }
                }
            }

            // DEFINE LISTA FINAL COMO ATRIBUTO DO REQUEST
            request.setAttribute("enderecos", enderecos);
        } catch (Exception e) {
            // TRATA ERROS INESPERADOS
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }

        // ENCAMINHA PARA O JSP RESPONSÁVEL PELO CRUD DE ENDEREÇOS
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Endereco/crudEndereco.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // FORMULÁRIO DE FILTRO USA POST, ENTÃO CHAMA DOGET PRA NÃO REPETIR CÓDIGO
        doGet(request, response);
    }
}
