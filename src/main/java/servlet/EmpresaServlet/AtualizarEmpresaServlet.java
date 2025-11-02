package servlet.EmpresaServlet;

// IMPORTS DO DAO E MODEL
import dao.EmpresaDAO;
import model.Empresa;

// IMPORTS PADRÕES DE SERVLET
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// UTILITÁRIOS
import utils.ValidacaoRegex;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/empresa-update")
public class AtualizarEmpresaServlet extends HttpServlet {

    // DOGET = CARREGA DADOS PARA ATUALIZAR //
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");

        // VALIDAÇÃO DE ID
        if (idParametro == null || idParametro.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID da empresa não informado.");
            response.sendRedirect(request.getContextPath() + "/empresas");
            return;
        }

        try {
            int id = Integer.parseInt(idParametro);
            EmpresaDAO empresaDAO = new EmpresaDAO();

            // BUSCA EMPRESA PELO ID
            Empresa empresa = empresaDAO.buscarPorId(id);
            if (empresa == null) {
                request.getSession().setAttribute("mensagem", "Empresa não encontrada (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/empresas");
                return;
            }

            // ENVIA OBJETO PARA O FORMULÁRIO DE EDIÇÃO
            request.setAttribute("empresaParaEditar", empresa);

            // ENCERRA O GET ENCAMINHANDO PARA JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Empresa/atualizarEmpresa.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "ID inválido.");
            response.sendRedirect(request.getContextPath() + "/empresas");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar empresa: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/empresas");
        }
    }

    // DOPOST = PROCESSA ATUALIZAÇÃO //
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // RECEBE OS PARÂMETROS DO FORMULÁRIO
        String idParametro = request.getParameter("id");
        String cnpj = request.getParameter("cnpj");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha"); // NOVA SENHA (OPCIONAL)
        String idPlanoParametro = request.getParameter("idPlano");
        String qntdFuncionariosParametro = request.getParameter("qntdFuncionarios");

        EmpresaDAO empresaDAO = new EmpresaDAO();

        try {
            // VALIDAÇÃO BÁSICA DOS CAMPOS OBRIGATÓRIOS
            if (idParametro == null || idParametro.isEmpty() ||
                    cnpj == null || cnpj.trim().isEmpty() ||
                    nome == null || nome.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    idPlanoParametro == null || idPlanoParametro.isEmpty() ||
                    qntdFuncionariosParametro == null || qntdFuncionariosParametro.isEmpty()) {

                request.getSession().setAttribute("mensagem", "Preencha todos os campos obrigatórios.");

            } else {
                int id = Integer.parseInt(idParametro);
                int idPlano = Integer.parseInt(idPlanoParametro);
                int qntdFuncionarios = Integer.parseInt(qntdFuncionariosParametro);

                // BUSCA EMPRESA EXISTENTE
                Empresa empresa = empresaDAO.buscarPorId(id);
                if (empresa == null) {
                    request.getSession().setAttribute("mensagem", "Empresa não encontrada para atualização.");
                    response.sendRedirect(request.getContextPath() + "/empresas");
                    return;
                }

                // VALIDAÇÃO DE EMAIL
                if (!ValidacaoRegex.verificarEmail(email)) {
                    request.getSession().setAttribute("mensagem", "Email inválido!");
                    response.sendRedirect(request.getContextPath() + "/empresas");
                    return;
                }

                // VALIDAÇÃO DE SENHA (SE INFORMADA)
                if (senha != null && !senha.isBlank() && !ValidacaoRegex.verificarSenha(senha)) {
                    request.getSession().setAttribute("mensagem",
                            "Nova senha inválida! Use ao menos 8 caracteres, letras, números e símbolos como @, #, $, sem espaços.");
                    response.sendRedirect(request.getContextPath() + "/empresas");
                    return;
                }

                // ATUALIZA CAMPOS DA EMPRESA
                empresa.setCnpj(cnpj.trim());
                empresa.setNome(nome.trim());
                empresa.setEmail(email.trim());
                empresa.setIdPlano(idPlano);
                empresa.setQntdFuncionarios(qntdFuncionarios);

                // ATUALIZA SENHA SE INFORMADA
                if (senha != null && !senha.trim().isEmpty()) {
                    empresa.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
                }

                // EXECUTA ATUALIZAÇÃO NO BANCO
                if (empresaDAO.atualizar(empresa) > 0) {
                    request.getSession().setAttribute("mensagem", "Empresa atualizada com sucesso!");
                } else {
                    request.getSession().setAttribute("mensagem", "Não foi possível atualizar a empresa.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "Valores numéricos inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao atualizar empresa: " + e.getMessage());
        }

        // REDIRECIONA PARA LISTAGEM DE EMPRESAS
        response.sendRedirect(request.getContextPath() + "/empresas");
    }
}
