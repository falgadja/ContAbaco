<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.SetorDAO" %>
<%@ page import="dao.EmpresaDAO" %>
<%@ page import="model.Setor" %>
<%@ page import="model.Empresa" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Funcionário</title>
    <link rel="stylesheet" href="../css/pessoa.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="main-container">
    <div class="panel right-panel">
        <div class="content">
            <h1>Faça seu cadastro!</h1>

            <form action="${pageContext.request.contextPath}/InserirFuncionario" method="post">

                <div class="form-group">
                    <label for="nome">Nome</label>
                    <input type="text" id="nome" name="nome" placeholder="Seu nome" maxlength="50" required>
                </div>

                <div class="form-group">
                    <label for="sobrenome">Sobrenome</label>
                    <input type="text" id="sobrenome" name="sobrenome" placeholder="Seu sobrenome" maxlength="50" required>
                </div>

                <div class="form-group">
                    <label for="dataNascimento">Data de Nascimento</label>
                    <input type="date" id="dataNascimento" name="dataNascimento" required>
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="seuemail@exemplo.com" maxlength="80" required>
                </div>

                <div class="form-group">
                    <label for="senha">Senha</label>
                    <input type="password" id="senha" name="senha" placeholder="Crie uma senha forte" minlength="8" maxlength="30" required>
                </div>

                <div class="form-group">
                    <label for="confirmarSenha">Confirme sua senha</label>
                    <input type="password" id="confirmarSenha" name="confirmarSenha" placeholder="Repita a senha" minlength="8" maxlength="30" required>
                </div>

                <div class="form-group">
                    <label for="idSetor">Setor</label>
                    <select id="idSetor" name="idSetor" required>
                        <option value="">Selecione o setor</option>
                        <%
                            SetorDAO setorDAO = new SetorDAO();
                            List<Setor> setores = setorDAO.listarTodos();
                            for (Setor setor : setores) {
                        %>
                        <option value="<%= setor.getId() %>"><%= setor.getNome() %></option>
                        <% } %>
                    </select>
                </div>

                <div class="form-group">
                    <label for="idEmpresa">Empresa</label>
                    <select id="idEmpresa" name="idEmpresa" required>
                        <option value="">Selecione a empresa</option>
                        <%
                            EmpresaDAO empresaDAO = new EmpresaDAO();
                            List<Empresa> empresas = empresaDAO.listar();
                            for (Empresa empresa : empresas) {
                        %>
                        <option value="<%= empresa.getId() %>"><%= empresa.getNome() %></option>
                        <% } %>
                    </select>
                </div>

                <button type="submit" class="btn btn-solid">Cadastrar</button>
            </form>

            <%-- Mensagem de erro ou sucesso --%>
            <%
                String mensagem = (String) request.getAttribute("mensagem");
                if (mensagem != null) {
            %>
            <p style="color: red; margin-top: 15px;"><%= mensagem %></p>
            <% } %>

        </div>
    </div>
</div>
</body>
</html>
