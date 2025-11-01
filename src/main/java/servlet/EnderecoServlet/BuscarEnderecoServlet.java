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

        // Recupera mensagem temporária da sessão (PRG)
        String mensagem = (String) session.getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem);
            session.removeAttribute("mensagem");
        }

        String cepParam = request.getParameter("cep");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");
        String[] estadosParam = request.getParameterValues("estados");

        List<String> estados = (estadosParam != null) ? List.of(estadosParam) : new ArrayList<>();

        EnderecoDAO enderecoDAO = new EnderecoDAO();
        EnderecoFiltro enderecoFiltro = new EnderecoFiltro();
        List<Endereco> enderecos = new ArrayList<>();

        try {
            if (cepParam != null && !cepParam.trim().isEmpty()) {
                // Valida e busca por CEP
                String cepValidado = ValidacaoRegex.verificarCep(cepParam);
                if (cepValidado != null) {
                    Endereco endereco = enderecoDAO.buscarPorCEP(Integer.parseInt(cepValidado));
                    if (endereco != null) {
                        enderecos.add(endereco);
                        mensagem = "Endereço encontrado com sucesso.";
                    } else {
                        mensagem = "Nenhum endereço encontrado com esse CEP.";
                    }
                } else {
                    mensagem = "CEP inválido. Verifique e tente novamente.";
                }
            } else {
                // Lista todos os endereços
                enderecos = enderecoDAO.listar();

                // Filtra por estado
                if (!estados.isEmpty()) {
                    enderecos = enderecoFiltro.filtrarPorEstado(enderecos, estados);
                }

                // Define mensagem de acordo com o resultado
                if (enderecos.isEmpty()) {
                    mensagem = "Nenhum endereço encontrado para os filtros aplicados.";
                } else {
                    mensagem = "Endereços encontrados com sucesso.";
                }
            }

            // Ordena se solicitado
            if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && !enderecos.isEmpty()) {
                switch (tipoOrdenacao) {
                    case "idCrescente" -> enderecos = enderecoFiltro.OrdenarIdCrece(enderecos);
                    case "idDecrescente" -> enderecos = enderecoFiltro.OrdenarIdDecre(enderecos);
                }
            }

            request.setAttribute("enderecos", enderecos);
            request.setAttribute("mensagem", mensagem);

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
