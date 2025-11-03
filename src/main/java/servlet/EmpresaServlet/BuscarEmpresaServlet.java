package servlet.EmpresaServlet;

// IMPORTS NECESSÁRIOS: DAO, FILTRO, SERVLET, SESSION, JSP E MODEL
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SERVLET RESPONSÁVEL POR BUSCAR E LISTAR EMPRESAS
 * PERMITE PESQUISA POR NOME, FILTRO POR QUANTIDADE DE FUNCIONÁRIOS E ORDENAR RESULTADOS
 * TRATA MENSAGENS TEMPORÁRIAS (PADRÃO PRG)
 */
@WebServlet("/empresas")
public class BuscarEmpresaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // PEGANDO MENSAGENS DA SESSÃO (PRG) PARA MOSTRAR NO JSP
        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem); // COLOCA MENSAGEM NO REQUEST
            session.removeAttribute("mensagem"); // LIMPA SESSÃO PRA NÃO REPETIR
        }

        // RECEBE PARAMETROS DO FORMULÁRIO
        String nome = request.getParameter("filtroNome");
        String min = request.getParameter("filtroMinFuncionarios");
        String max = request.getParameter("filtroMaxFuncionarios");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        // CRIA OBJETOS NECESSÁRIOS: DAO, FILTRO E LISTA
        EmpresaDAO empresaDAO = new EmpresaDAO();
        EmpresaFiltro empresaFiltro = new EmpresaFiltro();
        List<Empresa> empresas = new ArrayList<>();

        try {
            // PESQUISA POR NOME SE INFORMADO
            if (nome != null && !nome.trim().isEmpty()) {
                Empresa empresa = empresaDAO.buscarPorNome(nome);
                if (empresa == null) {
                    request.setAttribute("mensagem", "Nenhuma empresa encontrada com esse e-mail. Tente novamente.");
                } else {
                    List<Empresa> lista = new ArrayList<>();
                    lista.add(empresa);
                    empresas = lista;
                    request.setAttribute("mensagem", "Empresa encontrada com sucesso.");
                    request.setAttribute("empresas", empresas);
                }
            } else {
                // SE NAO INFORMAR NOME, LISTA TODAS AS EMPRESAS
                empresas = empresaDAO.listar();
                if (empresas == null || empresas.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhuma empresa registrada no sistema.");
                } else {
                    request.setAttribute("empresas", empresas);


                    // FILTRO POR QUANTIDADE DE FUNCIONÁRIOS SE MIN E MAX FOREM INFORMADOS
                    if (min != null && !min.isEmpty() && max != null && !max.isEmpty()) {
                        int minNum = Integer.parseInt(min);
                        int maxNum = Integer.parseInt(max);
                        empresas = empresaFiltro.filtrarPorQtdFuncionario(empresas, minNum, maxNum);
                    }

                    if (empresas.isEmpty()) {
                        request.setAttribute("mensagem", "Não foi encontrado nenhuma empresa no sistema com os filtros aplicados.");
                    } else {
                        request.setAttribute("mensagem", "Foram encontradas " + empresas.size() + " empresas.");
                    }

                    // ORDENACAO CASO TENHA SIDO ESCOLHIDA
                    if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && empresas != null && !empresas.isEmpty()) {
                        if (tipoOrdenacao.equals("idCrescente")) {
                            empresas = empresaFiltro.OrdenarIdCrece(empresas);
                        } else if (tipoOrdenacao.equals("idDecrescente")) {
                            empresas = empresaFiltro.OrdenarIdDecre(empresas);
                        } else if (tipoOrdenacao.equals("nomeAz")) {
                            empresas = empresaFiltro.OrdenarNomeAz(empresas);
                        } else if (tipoOrdenacao.equals("nomeZa")) {
                            empresas = empresaFiltro.OrdenarNomeZa(empresas);
                        } else if (tipoOrdenacao.equals("funcionarioCrescente")) {
                            empresas = empresaFiltro.OrdenarQntdFuncionarioCrece(empresas);
                        } else if (tipoOrdenacao.equals("funcionarioDecrescente")) {
                            empresas = empresaFiltro.OrdenarQntdFuncionarioDecre(empresas);
                        }
                    }
                }

                // DEFINE LISTA FINAL COMO ATRIBUTO DO REQUEST
                request.setAttribute("empresas", empresas);
            }

        } catch(NumberFormatException nfe){
            // TRATA ERRO QUANDO MIN OU MAX NAO SÃO NÚMEROS
            request.setAttribute("mensagem", "Número minímo ou maxímo inválido, digite apenas números inteiros.");
        } catch (Exception e) {
            // TRATA ERROS INESPERADOS
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }

        // ENCAMINHA PARA O JSP RESPONSÁVEL PELO CRUD DE EMPRESAS
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Empresa/crudEmpresa.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // FORMULÁRIO DE FILTRO USA POST, ENTÃO CHAMA O DOGET PRA NÃO REPETIR CÓDIGO
        doGet(request, response);
    }
}
