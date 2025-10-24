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
    if (idStr != null) {
        int id = Integer.parseInt(idStr);
        EnderecoDAO dao = new EnderecoDAO();
        dao.deletar(id);
    }
    response.sendRedirect(request.getContextPath() + "/view/Endereco/crudEndereco.jsp");
%>
