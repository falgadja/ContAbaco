<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.FuncionarioDAO" %>
<%@ page import="model.Funcionario" %>
<%@ page import="java.util.List" %>

<%
    String idEmpresaStr = request.getParameter("idEmpresa");
    if (idEmpresaStr == null) {
        response.sendRedirect(request.getContextPath() + "/view/Empresa/crudEmpresa.jsp");
        return;
    }

    int idEmpresa = Integer.parseInt(idEmpresaStr);
    FuncionarioDAO dao = new FuncionarioDAO();
    List<Funcionario> funcionarios = dao.buscarPorIdEmpresa(idEmpresa);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Funcionários da Empresa</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; }
        a.button { text-decoration: none; padding: 6px 12px; border: 1px solid #333; border-radius: 4px; background-color: #eee; color: black; }
        a.button:hover { background-color: #ddd; }
    </style>
</head>
<body>
<h1>Funcionários da Empresa ID: <%= idEmpresa %></h1>

<a href="<%= request.getContextPath() %>/view/Empresa/crudEmpresa.jsp" class="button">Voltar</a>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Sobrenome</th>
        <th>Data Nascimento</th>
        <th>Email</th>
        <th>ID Setor</th>
        <th>Ações</th>
    </tr>
    </thead>
    <tbody>
    <% for (Funcionario f : funcionarios) { %>
    <tr>
        <td><%= f.getId() %></td>
        <td><%= f.getNome() %></td>
        <td><%= f.getSobrenome() %></td>
        <td><%= f.getDataNascimento() %></td>
        <td><%= f.getEmail() %></td>
        <td><%= f.getIdSetor() %></td>
        <td>
            <a href="<%= request.getContextPath() %>/view/Funcionario/atualizarFuncionario.jsp?id=<%= f.getId() %>" class="button">Atualizar</a>
            <a href="<%= request.getContextPath() %>/view/Funcionario/deletarFuncionario.jsp?id=<%= f.getId() %>" class="button" onclick="return confirm('Deseja realmente deletar?');">Deletar</a>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>
