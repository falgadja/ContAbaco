<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 24/10/2025
  Time: 00:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.FuncionarioDAO" %>
<%@ page import="model.Funcionario" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    String idStr = request.getParameter("id");
    if (idStr == null) {
        response.sendRedirect(request.getContextPath() + "/view/Funcionario/crudFuncionario.jsp");
        return;
    }

    int id = Integer.parseInt(idStr);
    FuncionarioDAO dao = new FuncionarioDAO();
    Funcionario funcionario = dao.buscarPorId(id);
    if (funcionario == null) {
        response.sendRedirect(request.getContextPath() + "/view/Funcionario/crudFuncionario.jsp");
        return;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Atualizar Funcionário</title>
</head>
<body>
<h1>Atualizar Funcionário</h1>
<%
    String mensagem = request.getParameter("mensagem");
    if ("atualizado".equals(mensagem)) {
%>
<p>Funcionário atualizado com sucesso!</p>
<%
    }
%>

<form action="<%= request.getContextPath() %>/AtualizarFuncionarioServlet" method="post">


    <input type="hidden" name="id" value="<%= funcionario.getId() %>"/>

    <label>Nome:</label>
    <input type="text" name="nome" value="<%= funcionario.getNome() %>" required/><br/><br/>

    <label>Sobrenome:</label>
    <input type="text" name="sobrenome" value="<%= funcionario.getSobrenome() %>" required/><br/><br/>

    <label>Data de Nascimento:</label>
    <input type="date" name="dataNascimento" value="<%= funcionario.getDataNascimento().format(formatter) %>" required/><br/><br/>

    <label>Email:</label>
    <input type="email" name="email" value="<%= funcionario.getEmail() %>" required/><br/><br/>

    <label>Senha:</label>
    <input type="password" name="senha" value="<%= funcionario.getSenha() %>" required/><br/><br/>

    <label>ID Setor:</label>
    <input type="number" name="idSetor" value="<%= funcionario.getIdSetor() %>" required/><br/><br/>

    <label>ID Empresa:</label>
    <input type="number" name="idEmpresa" value="<%= funcionario.getIdEmpresa() %>" required/><br/><br/>

    <button type="submit">Atualizar</button>
    <a href="<%= request.getContextPath() %>/view/Funcionario/crudFuncionario.jsp">Cancelar</a>
</form>

</body>
</html>
