<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 24/10/2025
  Time: 00:56
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 26/10/2025
  Time: 13:45
  Página de atualização de Administrador
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.AdmDAO" %>
<%@ page import="model.Administrador" %>

<%
    String idStr = request.getParameter("id");
    Administrador adm = null;

    if (idStr != null) {
        int id = Integer.parseInt(idStr);
        AdmDAO dao = new AdmDAO();
        adm = dao.buscarPorId(id);
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Atualizar Administrador</title>
</head>
<body>
<h1>Atualizar Administrador</h1>

<%
    if (adm != null) {
%>
<%
    String mensagem = (String) request.getAttribute("mensagemAtualizar");
    if (mensagem != null) {
%>
<p><%= mensagem %></p>
<%
    }
%>

<form action="<%= request.getContextPath() %>/AtualizarAdmServlet" method="post">
    <input type="hidden" name="id" value="<%= adm.getId() %>">

    Email: <input type="email" name="email" value="<%= adm.getEmail() %>" required><br><br>
    Senha: <input type="password" name="senha"><br><br>

    <input type="submit" value="Atualizar">
    <a href="crudAdm.jsp">Cancelar</a>
</form>
<%
} else {
%>
<p>Administrador não encontrado!</p>
<a href="crudAdm.jsp">Voltar</a>
<%
    }
%>
</body>
</html>

