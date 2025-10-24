<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 24/10/2025
  Time: 00:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.EnderecoDAO" %>
<%@ page import="model.Endereco" %>

<%
    String idStr = request.getParameter("id");
    if (idStr == null) {
        response.sendRedirect(request.getContextPath() + "/view/Endereco/crudEndereco.jsp");
        return;
    }

    int id = Integer.parseInt(idStr);
    EnderecoDAO dao = new EnderecoDAO();
    Endereco endereco = dao.buscarPorId(id);
    if (endereco == null) {
        response.sendRedirect(request.getContextPath() + "/view/Endereco/crudEndereco.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Atualizar Endereço</title>
</head>
<body>
<h1>Atualizar Endereço</h1>

<form action="<%= request.getContextPath() %>/EnderecoAtualizarServlet" method="post">
    <input type="hidden" name="id" value="<%= endereco.getId() %>"/>
    <label>Rua:</label>
    <input type="text" name="rua" value="<%= endereco.getRua() %>" required/><br/><br/>

    <label>Número:</label>
    <input type="text" name="numero" value="<%= endereco.getNumero() %>" required/><br/><br/>

    <label>Bairro:</label>
    <input type="text" name="bairro" value="<%= endereco.getBairro() %>" required/><br/><br/>

    <label>Cidade:</label>
    <input type="text" name="cidade" value="<%= endereco.getCidade() %>" required/><br/><br/>

    <label>Estado:</label>
    <input type="text" name="estado" value="<%= endereco.getEstado() %>" required/><br/><br/>

    <label>CEP:</label>
    <input type="text" name="cep" value="<%= endereco.getCep() %>" required/><br/><br/>

    <button type="submit">Atualizar</button>
    <a href="<%= request.getContextPath() %>/view/Endereco/crudEndereco.jsp">Cancelar</a>
</form>

</body>
</html>
