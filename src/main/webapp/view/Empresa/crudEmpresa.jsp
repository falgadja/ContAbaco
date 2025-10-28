<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Empresa" %>
<%@ page import="dao.EmpresaDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Empresas - Área Restrita</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Poppins', sans-serif; }
        body { display: flex; background-color: #ffffff; color: #140066; height: 100vh; }

        .sidebar { width: 250px; background-color: #ffffff; border-right: 2px solid #140066; display: flex; flex-direction: column; align-items: center; padding: 25px 0; }
        .logo { text-align: center; margin-bottom: 40px; }
        .logo h2 { font-size: 1.3rem; color: #140066; }
        .logo small { color: #777; }
        .menu { width: 100%; display: flex; flex-direction: column; gap: 10px; padding: 0 20px; }
        .menu a { text-decoration: none; color: #140066; border: 1.5px solid #140066; border-radius: 8px; padding: 10px; text-align: left; font-weight: 500; display: flex; align-items: center; transition: 0.2s; }
        .menu a.active, .menu a:hover { background-color: #140066; color: white; }
        .logout { margin-top: auto; background-color: #d60000; color: white; border: none; border-radius: 6px; padding: 12px 30px; cursor: pointer; font-weight: 600; transition: 0.3s; }
        .logout:hover { background-color: #a30000; }

        .content { flex: 1; padding: 40px; }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px; }
        .header h1 { color: #140066; font-size: 1.7rem; }
        .btn-add { background-color: #140066; color: white; border: none; padding: 10px 20px; border-radius: 10px; cursor: pointer; font-weight: 500; transition: 0.3s; }
        .btn-add:hover { background-color: #0a0044; }

        table { width: 100%; border-collapse: collapse; margin-top: 20px; border: 1.5px solid #140066; border-radius: 12px; overflow: hidden; }
        th, td { text-align: left; padding: 14px 16px; border-bottom: 1px solid #ddd; }
        th { background-color: #f8f8ff; color: #140066; text-transform: uppercase; font-size: 0.85rem; }
        td { font-size: 0.95rem; color: #333; }
        tr:hover { background-color: #f0f0ff; }
        .acoes { display: flex; gap: 10px; }
        .acoes button { background: none; border: 1.5px solid #140066; border-radius: 6px; padding: 6px; cursor: pointer; transition: 0.2s; }
        .acoes button:hover { background-color: #140066; color: white; }
        @import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css');
    </style>
</head>
<body>

<div class="sidebar">
    <div class="logo">
        <h2>ContÁbaco</h2>
        <small>adm</small>
    </div>
    <div class="menu">
        <a href="<%= request.getContextPath() %>/view/Adm/crudAdm.jsp">Adm</a>
        <a href="<%= request.getContextPath() %>/view/Empresa/crudEmpresa.jsp" class="active">Empresas</a>
        <a href="<%= request.getContextPath() %>/view/Funcionario/crudFuncionario.jsp">Funcionários</a>
        <a href="<%= request.getContextPath() %>/view/Plano/crudPlano.jsp">Planos</a>
        <a href="<%= request.getContextPath() %>/view/Pagamento/crudPagamento.jsp">Pagamentos</a>
    </div>
    <button class="logout">Sair</button>
</div>

<div class="content">
    <div class="header">
        <div>
            <h1>Empresas Cadastrados</h1>
            <p>Visualize, edite ou exclua empresas registrados.</p>
        </div>
        <a href="<%= request.getContextPath() %>/view/Empresa/cadastrarEmpresa.jsp" class="btn-add">+ Adicionar Empresa</a>
    </div>
    <form action="${pageContext.request.contextPath}/BuscarEmpresaServlet" method="get">
        <label for="id">Buscar por ID:</label>
        <input type="text" name="id" id="id" placeholder="Digite o ID">

        <label for="tipoOrdenacao">Ordenar por:</label>
        <select name="tipoOrdenacao" id="tipoOrdenacao">
            <option value="">-- Nenhum --</option>
            <option value="idCrescente">ID Crescente</option>
            <option value="idDecrescente">ID Decrescente</option>
            <option value="Az">Nome das empresas em ordem crescente</option>
            <option value="Za">Nome das empresas em ordem crescente</option>
            <option value="qtndFuncionarioCrescente">Quantidade de funcionarios em ordem crescente</option>
            <option value="qtndFuncionarioDecrescente">Quantidade de funcionarios em ordem decrescente</option>

        </select>



        <label for="min">Número mínimo de funcionários da empresa:</label>
        <input type="text" name="min" id="min" placeholder="Digite o mínimo de funcionários da empresa">

        <label for="max">Número maxímo de funcionários da empresa:</label>
        <input type="text" name="max" id="max" placeholder="Digite o maxímo de funcionários da empresa">

        <button type="submit">Filtrar</button>
    </form>


    <p class="mensagem">${mensagem}</p>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>CNPJ</th>
            <th>Nome</th>
            <th>Email</th>
            <th>Senha</th>
            <th>ID do plano</th>
            <th>Quantidade de funcionários</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="e" items="${empresas}">
            <tr>
                <td>${e.id}</td>
                <td>${e.cnpj}</td>
                <td>${e.nome}</td>
                <td>${e.email}</td>
                <td>${e.senha}</td>
                <td>${e.idPlano}</td>
                <td>${e.qntdFuncionarios}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


</body>
</html>
