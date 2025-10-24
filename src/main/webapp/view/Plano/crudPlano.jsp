<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 24/10/2025
  Time: 00:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.PlanoDAO" %>
<%@ page import="model.Plano" %>

<%
    PlanoDAO dao = new PlanoDAO();
    List<Plano> planos = dao.listar();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CRUD de Planos</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; }
        a.button { text-decoration: none; padding: 6px 12px; border: 1px solid #333; border-radius: 4px; background-color: #eee; color: black; }
        a.button:hover { background-color: #ddd; }
    </style>
</head>
<body>
<h1>CRUD de Planos</h1>

<a href="<%= request.getContextPath() %>/view/Plano/cadastrarPlano.jsp" class="button">Adicionar Novo Plano</a>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Nome do Plano</th>
        <th>Preço</th>
        <th>Duração (meses)</th>
        <th>Ações</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Plano p : planos) {
    %>
    <tr>
        <td><%= p.getId() %></td>
        <td><%= p.getNome() %></td>
        <td><%= p.getPreco() %></td>
=        <td>
            <a href="<%= request.getContextPath() %>/view/Plano/atualizarPlano.jsp?id=<%= p.getId() %>" class="button">Atualizar</a>
            <a href="<%= request.getContextPath() %>/view/Plano/deletarPlano.jsp?id=<%= p.getId() %>" class="button" onclick="return confirm('Deseja realmente deletar este plano?');">Deletar</a>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
</body>
</html>
