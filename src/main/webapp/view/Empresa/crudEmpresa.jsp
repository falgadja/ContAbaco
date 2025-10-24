<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.EmpresaDAO" %>
<%@ page import="model.Empresa" %>

<%
    EmpresaDAO dao = new EmpresaDAO();
    List<Empresa> empresas = dao.listar();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CRUD de Empresas</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; }
        a.button { text-decoration: none; padding: 6px 12px; border: 1px solid #333; border-radius: 4px; background-color: #eee; color: black; }
        a.button:hover { background-color: #ddd; }
    </style>
</head>
<body>
<h1>CRUD de Empresas</h1>

<!-- Link para adicionar nova empresa -->
<a href="<%= request.getContextPath() %>/view/Empresa/cadastrarEmpresa.jsp" class="button">Adicionar Nova Empresa</a>

<!-- Tabela de empresas -->
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>CNPJ</th>
        <th>Nome</th>
        <th>Email</th>
        <th>Plano</th>
        <th>Qtd Funcionários</th>
        <th>Ações</th>
    </tr>
    </thead>
    <tbody>
    <% for (Empresa e : empresas) { %>
    <tr>
        <td><%= e.getId() %></td>
        <td><%= e.getCnpj() %></td>
        <td><%= e.getNome() %></td>
        <td><%= e.getEmail() %></td>
        <td><%= e.getIdPlano() %></td>
        <td><%= e.getQntdFuncionarios() %></td>
        <td>
            <a href="<%= request.getContextPath() %>/view/Empresa/atualizarEmpresa.jsp?id=<%= e.getId() %>" class="button">Atualizar</a>
            <a href="<%= request.getContextPath() %>/view/Empresa/deletarEmpresa.jsp?id=<%= e.getId() %>" class="button" onclick="return confirm('Deseja realmente deletar?');">Deletar</a>
            <!-- Novo link para ver funcionários -->
            <a href="<%= request.getContextPath() %>/view/Funcionario/listarFuncionario.jsp?idEmpresa=<%= e.getId() %>" class="button">Ver Funcionários</a>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>
