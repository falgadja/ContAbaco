package servlet.EnderecoServlet;

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

@WebServlet("/endereco")
public class BuscarEnderecoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem);
            session.removeAttribute("mensagem");
        }

        String cepParam = request.getParameter("cep");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");
        String estadoParam = request.getParameter("estado");

        // Tratar estado como lista para manter compatibilidade
        List<String> estados = (estadoParam != null && !estadoParam.isEmpty())
                ? List.of(estadoParam)
                : new ArrayList<>();

        EnderecoDAO enderecoDAO = new EnderecoDAO();
        EnderecoFiltro enderecoFiltro = new EnderecoFiltro();
        List<Endereco> enderecos = new ArrayList<>();

        try {
            if (cepParam != null && !cepParam.trim().isEmpty()) {
                // BUSCA POR CEP
                String cepValidado = ValidacaoRegex.verificarCep(cepParam);
                if (cepValidado != null) {
                    Endereco endereco = enderecoDAO.buscarPorCEP(cepValidado);
                    if (endereco != null) {
                        enderecos.add(endereco);
                        request.setAttribute("mensagem", "Endereço encontrado com sucesso.");
                    } else {
                        request.setAttribute("mensagem", "Nenhum endereço encontrado com esse CEP.");
                    }
                } else {
                    request.setAttribute("mensagem", "CEP inválido. Verifique e tente novamente.");
                }
            } else {
                // LISTA TODOS OS ENDEREÇOS
                enderecos = enderecoDAO.listar();

                // FILTRA POR ESTADO
                if (!estados.isEmpty()) {
                    enderecos = enderecoFiltro.filtrarPorEstado(enderecos, estados);
                }

                // ORDENACAO
                if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty()) {
                    if (tipoOrdenacao.equals("idCrescente")) {
                        enderecos = enderecoFiltro.OrdenarIdCrece(enderecos);
                    } else if (tipoOrdenacao.equals("idDecrescente")) {
                        enderecos = enderecoFiltro.OrdenarIdDecre(enderecos);
                    }
                }

                // Mensagem final
                if (enderecos.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum endereço com os filtros aplicados.");
                } else {
                    request.setAttribute("mensagem", "Foram encontrados " + enderecos.size() + " endereços.");
                }
            }

            request.setAttribute("enderecos", enderecos);
            // Manter filtros selecionados
            request.setAttribute("estadoSelecionado", estadoParam != null ? estadoParam : "");
            request.setAttribute("tipoOrdenacao", tipoOrdenacao != null ? tipoOrdenacao : "");
            request.setAttribute("cepParam", cepParam != null ? cepParam : "");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Endereco/crudEndereco.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
