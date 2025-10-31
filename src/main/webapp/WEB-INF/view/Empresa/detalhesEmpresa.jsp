<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Empresa, model.Funcionario, model.Endereco" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
  Empresa empresa = (Empresa) request.getAttribute("empresa");
  Endereco endereco = (Endereco) request.getAttribute("endereco");
  List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Detalhes da Empresa</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 20px; }
    button, a { margin: 5px; padding: 8px 12px; cursor: pointer; text-decoration: none; color: white; background-color: #007BFF; border: none; border-radius: 4px; }
    button:hover, a:hover { background-color: #0056b3; }
    ul { list-style-type: none; padding: 0; }
    li { margin-bottom: 5px; }
  </style>
</head>
<body>
<c:choose>
  <c:when test="${empresa != null}">
    <h1><c:out value="${empresa.nome}"/></h1>

    <h2>Endereço</h2>
    <c:choose>
      <c:when test="${endereco != null}">
        <p>
          <c:out value="${endereco.rua}"/>, <c:out value="${endereco.numero}"/> -
          <c:out value="${endereco.cidade}"/>, <c:out value="${endereco.estado}"/>
        </p>

        <button onclick="window.location.href='${pageContext.request.contextPath}/endereco-update?id=${empresa.id}'">
          Atualizar
        </button>

        <button onclick="if(confirm('Deseja excluir este endereço?')) window.location.href='${pageContext.request.contextPath}/endereco-delete?id=${empresa.id}'">
          Deletar
        </button>
      </c:when>
      <c:otherwise>
        <a href="${pageContext.request.contextPath}/endereco-create?idEmpresa=${empresa.id}">
          Adicionar Endereço
        </a>
      </c:otherwise>
    </c:choose>

    <h2>Funcionários</h2>
    <c:choose>
      <c:when test="${funcionarios != null && !funcionarios.isEmpty()}">
        <ul>
          <c:forEach var="f" items="${funcionarios}">
            <li><c:out value="${f.nome}"/> - <c:out value="${f.email}"/></li>
          </c:forEach>
        </ul>
      </c:when>
      <c:otherwise>
        <p>Nenhum funcionário cadastrado.</p>
      </c:otherwise>
    </c:choose>

  </c:when>
  <c:otherwise>
    <p>Empresa não encontrada!</p>
  </c:otherwise>
</c:choose>

</body>
</html>
