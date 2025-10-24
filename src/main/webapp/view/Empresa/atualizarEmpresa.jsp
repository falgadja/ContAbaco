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
<form action="AtualizarEmpresaServlet" method="post">
    <input type="hidden" name="id" value="<%= empresa.getId() %>">
    Nome: <input type="text" name="nome" value="<%= empresa.getNome() %>" required><br><br>
    CNPJ: <input type="text" name="cnpj" value="<%= empresa.getCnpj() %>" required><br><br>
    Email: <input type="email" name="email" value="<%= empresa.getEmail() %>" required><br><br>
    Senha: <input type="password" name="senha" placeholder="Deixe vazio para não alterar"><br><br>
    Plano (ID): <input type="number" name="idPlano" value="<%= empresa.getIdPlano() %>" required><br><br>
    Qtd Funcionários: <input type="number" name="qntdFuncionarios" value="<%= empresa.getQntdFuncionarios() %>" required><br><br>
    <input type="submit" value="Atualizar">
    <a href="crudEmpresa.jsp">Cancelar</a>
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

