<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.EnderecoDAO" %>
<%@ page import="model.Endereco" %>

<%
    EnderecoDAO dao = new EnderecoDAO();
    List<Endereco> enderecos = dao.listar();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CRUD de Endereços</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; }
        a.button { text-decoration: none; padding: 6px 12px; border: 1px solid #333; border-radius: 4px; background-color: #eee; color: black; }
        a.button:hover { background-color: #ddd; }
    </style>
</head>
<body>
<div class="menu">
    <a href="<%= request.getContextPath() %>/view/Adm/crudAdm.jsp">Adm</a>
    <a href="<%= request.getContextPath() %>/view/Empresa/crudEmpresa.jsp">Empresas</a>
    <a href="<%= request.getContextPath() %>/view/Funcionario/crudFuncionario.jsp" class="active">Funcionários</a>
    <a href="<%= request.getContextPath() %>/view/Plano/crudPlano.jsp">Planos</a>
    <a href="<%= request.getContextPath() %>/view/Pagamento/crudPagamento.jsp">Pagamentos</a>
</div>

<!-- Link para adicionar novo endereço -->
<a href="<%= request.getContextPath() %>/endereco-create" class="button">Adicionar Novo Endereço</a>

<!-- Tabela de endereços -->
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Rua</th>
        <th>Número</th>
        <th>Bairro</th>
        <th>Cidade</th>
        <th>Estado</th>
        <th>CEP</th>
        <th>Ações</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Endereco end : enderecos) {
    %>
    <tr>
        <td><%= end.getId() %></td>
        <td><%= end.getRua() %></td>
        <td><%= end.getNumero() %></td>
        <td><%= end.getBairro() %></td>
        <td><%= end.getCidade() %></td>
        <td><%= end.getEstado() %></td>
        <td><%= end.getCep() %></td>
        <td>
            <a href="<%= request.getContextPath() %>/view/Endereco/atualizarEndereco.jsp?id=<%= end.getId() %>" class="button">Atualizar</a>
            <a href="<%= request.getContextPath() %>/view/Endereco/deletarEndereco.jsp?id=<%= end.getId() %>" class="button" onclick="return confirm('Deseja realmente deletar?');">Deletar</a>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
</body>
</html>
