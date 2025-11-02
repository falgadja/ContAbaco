package servlet.EnderecoServlet;

// IMPORTS DO DAO E MODEL
import dao.EmpresaDAO;
import dao.EnderecoDAO;
import model.Empresa;
import model.Endereco;

// IMPORTS PADRÕES DE SERVLET
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// UTILIDADES DE VALIDAÇÃO
import utils.ValidacaoRegex;

import java.io.IOException;
import java.util.List;

// DEFINE A ROTA DO SERVLET
@WebServlet("/endereco-create")
public class InserirEnderecoServlet extends HttpServlet {

    // MÉTODO DOGET → EXIBE FORMULÁRIO DE CADASTRO DE ENDEREÇO
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- BUSCAR TODAS AS EMPRESAS PRA PREENCHER SELECT ---
        EmpresaDAO empresaDAO = new EmpresaDAO();
        List<Empresa> empresas = empresaDAO.listar();
        request.setAttribute("empresas", empresas);

        // --- REDIRECIONA PRO JSP DE CADASTRO ---
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Endereco/cadastrarEndereco.jsp");
        dispatcher.forward(request, response);
    }

    // MÉTODO DOPOST → RECEBE DADOS DO FORMULÁRIO E INSERE NO BANCO
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // PEGANDO DADOS DO FORMULÁRIO
        String pais = request.getParameter("pais");
        String estado = request.getParameter("estado");
        String cidade = request.getParameter("cidade");
        String bairro = request.getParameter("bairro");
        String rua = request.getParameter("rua");
        String cep = request.getParameter("cep");
        String numeroStr = request.getParameter("numero");
        String idEmpresaStr = request.getParameter("idEmpresa");

        // VALIDAÇÃO DO CEP (RETORNA NULL SE INVÁLIDO)
        String cepValidado = null;
        if (cep != null && !cep.isBlank()) {
            cepValidado = ValidacaoRegex.verificarCep(cep);
        }

        try {
            // VALIDAÇÃO DE CAMPOS OBRIGATÓRIOS
            if (pais == null || estado == null || cidade == null || bairro == null ||
                    rua == null || cep == null || numeroStr == null || idEmpresaStr == null ||
                    pais.isBlank() || estado.isBlank() || cidade.isBlank() || bairro.isBlank() ||
                    rua.isBlank() || cep.isBlank() || numeroStr.isBlank() || idEmpresaStr.isBlank()) {

                request.setAttribute("mensagem", "Todos os campos são obrigatórios!");
                doGet(request, response);
                return;
            }

            // VALIDAÇÃO DO CEP
            if (cepValidado == null || cepValidado.trim().isEmpty()) {
                request.setAttribute("mensagem", "CEP inválido.");
                doGet(request, response);
                return;
            }

            // CONVERSÃO DE VALORES NUMÉRICOS
            int numero = Integer.parseInt(numeroStr);
            int idEmpresa = Integer.parseInt(idEmpresaStr);

            // CRIA OBJETO ENDEREÇO
            Endereco endereco = new Endereco(pais, estado, cidade, bairro, rua, numero, cepValidado, idEmpresa);

            // INSERE ENDEREÇO NO BANCO
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            int idGerado = enderecoDAO.inserir(endereco);

            // VERIFICA SE DEU CERTO
            if (idGerado > 0) {
                // SUCESSO → REDIRECIONA PRA LISTA DE ENDEREÇOS
                response.sendRedirect(request.getContextPath() + "/endereco");
            } else {
                request.setAttribute("mensagem", "Não foi possível cadastrar o endereço. Tente novamente!");
                doGet(request, response);
            }

        } catch (NumberFormatException e) {
            // TRATA ERRO QUANDO NÚMEROS SÃO INVÁLIDOS
            request.setAttribute("mensagem", "Erro: número inválido informado!");
            doGet(request, response);

        } catch (Exception e) {
            // QUALQUER OUTRO ERRO
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro interno: " + e.getMessage());
            doGet(request, response);
        }
    }
}
