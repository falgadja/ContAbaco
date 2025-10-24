<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 24/10/2025
  Time: 00:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.PagamentoDAO" %>
<%@ page import="model.Pagamento" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    PagamentoDAO dao = new PagamentoDAO();
    Pagamento p = dao.buscarPorId(id); // Certifique-se de criar esse método no DAO
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Atualizar Pagamento</title>
</head>
<body>
<h1>Atualizar Pagamento</h1>

<form action="<%= request.getContextPath() %>/AtualizarPagamentoServlet" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="<%= p.getId() %>">
    Tipo de Pagamento: <input type="text" name="tipoPagto" value="<%= p.getTipoPagto() %>" required><br><br>
    Total: <input type="number" step="0.01" name="total" value="<%= p.getTotal() %>" required><br><br>
    Data: <input type="date" name="data" value="<%= p.getData() %>" required><br><br>
    Comprovante: <input type="file" name="comprovante"><br><br>
    ID Empresa: <input type="number" name="idEmpresa" value="<%= p.getIdEmpresa() %>" required><br><br>
    <input type="submit" value="Atualizar">
</form>

<a href="<%= request.getContextPath() %>/view/Pagamento/crudPagamento.jsp">Voltar</a>
</body>
</html>
