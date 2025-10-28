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

    <!-- fontes / ícones -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>

    <style>
        /* ======== ESTILIZAÇÃO DO ENTORNO (EXATAMENTE COMO VOCÊ ENVIOU) ======== */
        :root { --cor_falgadja_1:#1800CC; --cor_falgadja_2:#0C0066; --cor_sair:#b71c1c; --branco:#ffffff; }
        *{ box-sizing:border-box; margin:0; padding:0; font-family:'Poppins',system-ui,-apple-system,'Segoe UI',Roboto,Arial; }
        html,body{ height:100%; width:100%; background:var(--branco); color:var(--cor_falgadja_1); }

        .esquerda{ display:flex; height:100vh; width:100%; overflow:hidden; }
        .sidebar{ width:255px; padding:28px 20px; display:flex; flex-direction:column; gap:18px; }
        .aplicativo{ display:flex; gap:15px; align-items:center; }
        .logo{ width:56px; height:56px; border-radius:10px; border:2px solid var(--cor_falgadja_1); display:flex; align-items:center; justify-content:center; overflow:hidden; }
        .logo img{ max-width:100%; max-height:100%; display:block; }
        .titulo_app{ font-weight:700; color:var(--cor_falgadja_1); font-size:20px; }
        .subtitulo_app{ font-size:12px; color:grey; margin-top:2px; }

        .linha{ height:6px; border-radius:30px; background:linear-gradient(90deg,var(--cor_falgadja_1),var(--cor_falgadja_2)); width:80%; margin:6px 0; }

        .nav{ display:flex; flex-direction:column; gap:12px; margin-top:10px; }
        .botao{ display:flex; align-items:center; gap:12px; padding:12px 18px; border-radius:10px; border:2px solid var(--cor_falgadja_1); background:transparent; color:var(--cor_falgadja_1); font-weight:600; width:190px; cursor:pointer; text-decoration:none; }
        .botao.selecionado{ background:linear-gradient(180deg,var(--cor_falgadja_1),var(--cor_falgadja_2)); color:#fff; border-color:transparent; box-shadow:0 6px 18px rgba(12,0,102,0.25); }

        .separator{ width:4px; background:var(--cor_falgadja_1); }

        .main{ flex:1; padding:36px 48px; overflow:auto; }

        .titulos{ display:flex; align-items:center; justify-content:space-between; gap:12px; }
        #AR{ font-size:30px; font-weight:800; color:var(--cor_falgadja_1); }
        #CRUD{ color:grey; font-weight:600; margin-top:-10px; }

        .pesquisar{ width:480px; height:44px; border-radius:10px; border:2px solid var(--cor_falgadja_1); display:flex; align-items:center; padding:8px 12px; gap:10px; }
        .pesquisar input{ border:0; outline:0; flex:1; font-size:14px; color:var(--cor_falgadja_1); }
        .pesquisar input::placeholder{ color: rgba(24,0,204,0.45); }

        .adicionador{ margin-top:28px; }
        .botao-add{ padding:12px 18px; border-radius:12px; background:linear-gradient(180deg,var(--cor_falgadja_1),var(--cor_falgadja_2)); color:#fff; font-weight:700; border:0; cursor:pointer; display:inline-flex; align-items:center; gap:10px; text-decoration:none; }

        .sair{ margin-top:auto;}
        .botao-sair{padding:12px 18px; border-radius:12px; background:var(--cor_sair); color:white; border:0; cursor:pointer; font-weight:700; width:100%; display:inline-flex; align-items:center; gap:10px;}

        /* ======== ESTILO DA TABELA (mantido) ======== */
        .tabela {
            margin-top:20px;
            border:3px solid rgba(24,0,204,0.95);
            border-radius:14px;
            padding:10px;
            width: calc(100% - 32px);
            max-width:1180px;
            margin-left:auto; margin-right:auto;
            box-shadow:0 10px 22px rgba(12,0,102,0.08);
            position:relative;
            background:#fff;
        }

        .tabela-container {
            width:100%;
            max-height:calc(100vh - 260px);
            overflow-y:auto;
            overflow-x:auto;
            padding:10px;
        }
        .tabela-container::-webkit-scrollbar{ height:10px; width:10px; }
        .tabela-container::-webkit-scrollbar-thumb{ background: linear-gradient(180deg, rgba(24,0,204,0.2), rgba(12,0,102,0.2)); border-radius:10px; }

        .empresa-style table { width:auto; table-layout:auto; border-collapse:collapse; font-size:14px; white-space:nowrap; }
        .empresa-style thead th, .empresa-style tbody td {
            padding:14px 16px; overflow:visible; text-overflow:clip; white-space:nowrap;
            font-weight:600; text-align:center;
        }
        .empresa-style tbody tr:nth-child(even){ background:#fafaff; }
        .empresa-style tbody tr:hover{ background:rgba(24,0,204,0.04); }

        .empresa-style th.acoes-col, .empresa-style td.acoes {
            position:sticky; right:0; background:#fff; box-shadow:-8px 0 12px rgba(12,0,102,0.06);
            min-width:170px; padding:0 8px; vertical-align:middle; text-align:center; overflow:visible; white-space:nowrap;
        }

        .btn{ display:inline-flex; align-items:center; justify-content:center; width:36px; height:36px; border-radius:8px; border:1.4px solid rgba(24,0,204,0.9); background:#fff; cursor:pointer; transition:.15s; }
        .btn:hover{ transform:translateY(-2px); box-shadow:0 6px 12px rgba(12,0,102,0.06); }
    </style>
</head>
<body>

<div class="sidebar">
    <div class="logo">
        <h2>ContÁbaco</h2>
        <small>adm</small>
    </div>
    <div class="menu"><div class="menu">
        <a href="<%= request.getContextPath() %>/BuscarAdmServlet" class="active-link">Adm</a>
        <a href="<%= request.getContextPath() %>/BuscarEmpresaServlet">Empresas</a>
        <a href="<%= request.getContextPath() %>/BuscarFuncionarioServlet">Funcionários</a>
        <a href="<%= request.getContextPath() %>/BuscarPlanoServlet">Planos</a>
        <a href="<%= request.getContextPath() %>/BuscarPagamentoServlet" class="active">Pagamentos</a>
    </div>

    </div>
    <button class="logout">Sair</button>
</div>

<div class="content">
    <div class="header">
        <div>
            <h1>Empresas Cadastrados</h1>
            <p>Visualize, edite ou exclua empresas registrados.</p>
        </div>
    </div>
    <form action="${pageContext.request.contextPath}/BuscarEmpresaServlet" method="get">
        <label for="nome">Buscar por ID:</label>
        <input type="text" name="nome" id="nome" placeholder="Digite o nome da empresa:">

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