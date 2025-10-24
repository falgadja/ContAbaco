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
    <title>Deletar Plano</title>
</head>
<body>
<h1>Deletar Plano</h1>

<p>Deseja realmente deletar o plano: <strong><%= plano.getNome() %></strong>?</p>

<form action="<%= request.getContextPath() %>/DeletarPlanoServlet" method="post">
    <input type="hidden" name="id" value="<%= plano.getId() %>">
    <button type="submit">Sim, deletar</button>
</form>

<a href="<%= request.getContextPath() %>/view/Plano/crudPlano.jsp">Cancelar</a>
</body>
</html>
