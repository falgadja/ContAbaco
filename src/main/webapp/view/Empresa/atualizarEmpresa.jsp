<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 24/10/2025
  Time: 00:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.EmpresaDAO" %>
<%@ page import="model.Empresa" %>

<%
    String idStr = request.getParameter("id");
    Empresa empresa = null;

    if (idStr != null) {
        int id = Integer.parseInt(idStr);
        EmpresaDAO dao = new EmpresaDAO();
        empresa = dao.buscarPorId(id);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Atualizar Empresa</title>
</head>
<body>
<h1>Atualizar Empresa</h1>

<%
    if (empresa != null) {
%>
<form action="<%= request.getContextPath() %>/AtualizarEmpresaServlet" method="post">
    <input type="hidden" name="id" value="<%= empresa.getId() %>">

    <label for="nome">Nome:</label>
    <input type="text" id="nome" name="nome" value="<%= empresa.getNome() %>" required>
    <br><br>

    <label for="cnpj">CNPJ:</label>
    <input type="text" id="cnpj" name="cnpj" value="<%= empresa.getCnpj() %>" required>
    <br><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" value="<%= empresa.getEmail() %>" required>
    <br><br>

    <label for="senha">Senha:</label>
    <input type="password" id="senha" name="senha" placeholder="Deixe vazio para não alterar">
    <br><br>

    <label for="idPlano">Plano (ID):</label>
    <input type="number" id="idPlano" name="idPlano" value="<%= empresa.getIdPlano() %>" required>
    <br><br>

    <label for="qntdFuncionarios">Qtd Funcionários:</label>
    <input type="number" id="qntdFuncionarios" name="qntdFuncionarios" value="<%= empresa.getQntdFuncionarios() %>" required>
    <br><br>

    <input type="submit" value="Atualizar">
</form>

<%
} else {
%>
<p>Empresa não encontrada!</p>
<a href="crudEmpresa.jsp">Voltar</a>
<%
    }
%>
</body>
</html>

