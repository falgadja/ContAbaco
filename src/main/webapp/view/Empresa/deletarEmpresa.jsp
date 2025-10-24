<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 24/10/2025
  Time: 00:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.EmpresaDAO" %>
<%
    String idStr = request.getParameter("id");
    if (idStr != null) {
        int id = Integer.parseInt(idStr);
        EmpresaDAO dao = new EmpresaDAO();
        dao.deletar(id);
    }
    response.sendRedirect("crudEmpresa.jsp");
%>
