<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 24/10/2025
  Time: 00:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.PlanoDAO" %>
<%@ page import="model.Plano" %>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    PlanoDAO dao = new PlanoDAO();
    Plano plano = dao.buscarPorId(id);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Atualizar Plano</title>
</head>
<body>
<h1>Atualizar Plano</h1>

<form action="<%= request.getContextPath() %>/AtualizarPlanoServlet" method="post">
    <input type="hidden" name="id" value="<%= plano.getId() %>">

    <label>Nome do Plano:</label>
    <input type="text" name="nome" value="<%= plano.getNome() %>" required><br><br>

    <label>Preço:</label>
    <input type="number" step="0.01" name="preco" value="<%= plano.getPreco() %>" required><br><br>

    <button type="submit">Atualizar</button>
</form>

<a href="<%= request.getContextPath() %>/view/Plano/crudPlano.jsp">Voltar</a>
</body>
</html>
