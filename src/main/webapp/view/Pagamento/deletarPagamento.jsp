<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 24/10/2025
  Time: 00:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.PagamentoDAO" %>
<%@ page import="model.Pagamento" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    PagamentoDAO dao = new PagamentoDAO();
    Pagamento p = dao.buscarPorId(id);

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        dao.deletar(id);
        response.sendRedirect(request.getContextPath() + "/view/Pagamento/crudPagamento.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Deletar Pagamento</title>
</head>
<body>
<h1>Deletar Pagamento</h1>
<p>Tem certeza que deseja deletar o pagamento de ID <%= p.getId() %>?</p>

<form method="post">
    <input type="submit" value="Sim, deletar">
</form>

<a href="<%= request.getContextPath() %>/view/Pagamento/crudPagamento.jsp">Cancelar</a>
</body>
</html>
