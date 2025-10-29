<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Funcionario" %>
<%@ page import="dao.FuncionarioDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Funcionários - Área Restrita</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>

    <style>
        /* Estilos do layout principal (copiado do crudEmpresa) */
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
        .main{ flex:1; padding:36px 48px; overflow:auto; }
        .titulos{ display:flex; align-items:center; justify-content:space-between; gap:12px; }
        #AR{ font-size:30px; font-weight:800; color:var(--cor_falgadja_1); }
        #CRUD{ color:grey; font-weight:600; margin-top:-10px; }
        .adicionador{ margin-top:28px; }
        .botao-add{ padding:12px 18px; border-radius:12px; background:linear-gradient(180deg,var(--cor_falgadja_1),var(--cor_falgadja_2)); color:#fff; font-weight:700; border:0; cursor:pointer; display:inline-flex; align-items:center; gap:10px; text-decoration:none; }
        .sair{ margin-top:auto;}
        .botao-sair{padding:12px 18px; border-radius:12px; background:var(--cor_sair); color:white; border:0; cursor:pointer; font-weight:700; width:100%; display:inline-flex; align-items:center; gap:10px;}

        /* Estilos do Filtro */
        .filtros {
            margin-top: 24px;
            margin-bottom: -10px;
            padding: 16px;
            background: #fafaff;
            border-radius: 12px;
            border: 2px solid rgba(24, 0, 204, 0.1);
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            gap: 16px;
            width: calc(100% - 32px);
            max-width: 1180px;
            margin-left: auto;
            margin-right: auto;
        }
        .filtros label {
            font-weight: 600;
            color: var(--cor_falgadja_1);
            font-size: 14px;
            margin-right: -8px;
        }
        .filtros input[type="text"],
        .filtros input[type="number"],
        .filtros select {
            padding: 10px 14px;
            border-radius: 10px;
            border: 2px solid rgba(24, 0, 204, 0.18);
            outline: none;
            font-size: 14px;
            color: #222;
        }
        .filtros input[type="text"] { width: 250px; }
        .filtros input[type="number"] { width: 180px; }
        .filtros button {
            padding: 10px 24px;
            border-radius: 10px;
            background: linear-gradient(180deg, var(--cor_falgadja_1), var(--cor_falgadja_2));
            color: #fff;
            font-weight: 700;
            border: 0;
            cursor: pointer;
            font-size: 14px;
            margin-left: auto;
        }
        .mensagem {
            margin: 16px auto 0 auto;
            width: calc(100% - 32px);
            max-width: 1180px;
            color: var(--cor_falgadja_1);
            font-weight: 600;
            padding: 10px 16px;
            background: #f0f4ff;
            border: 1px solid #c8d3ff;
            border-radius: 10px;
            text-align: center;
        }

        /* Estilos da Tabela */
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
            overflow: visible;
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
        .func-style table { width:auto; table-layout:auto; border-collapse:collapse; font-size:14px; white-space:nowrap; }
        .func-style thead th, .func-style tbody td {
            padding:14px 16px; overflow:visible; text-overflow:clip; white-space:nowrap;
            font-weight:600; text-align:center;
        }
        .func-style tbody tr:nth-child(even){ background:#fafaff; }
        .func-style tbody tr:hover{ background:rgba(24,0,204,0.04); }
        .func-style th.acoes-col, .func-style td.acoes {
            position:sticky; right:0; background:#fff; box-shadow:-8px 0 12px rgba(12,0,102,0.06);
            min-width:170px; padding:0 8px; vertical-align:middle; text-align:center; overflow:visible; white-space:nowrap;
        }
        .func-style td.acoes .btn {
            display:inline-flex; align-items:center; justify-content:center; width:32px; height:32px;
            border-radius:6px; border:1.5px solid var(--cor_falgadja_1); background:transparent;
            color: var(--cor_falgadja_1); cursor:pointer; transition:.2s; margin: 0 4px;
        }
        .func-style td.acoes .btn:hover { background: var(--cor_falgadja_1); color: #fff; }

        /* Estilos do Modal */
        .in-table-modal {
            position: fixed; left: 50%; top: 50%; transform: translate(-50%, -50%);
            width: min(80%, 900px); min-height: 460px; max-height: calc(100% - 60px);
            border-radius: 18px; border: 3px solid rgba(24, 0, 204, 0.95);
            background: #fff; box-shadow: 0 18px 40px rgba(12, 0, 102, 0.12);
            z-index: 1000; display: none; overflow: hidden; padding: 24px 36px;
        }
        .in-table-modal.open { display: block; }
        .in-table-modal .modal-close {
            position: absolute; top: 10px; right: 10px; width: 44px; height: 44px;
            border-radius: 50%; background: #fff; border: 2px solid rgba(24, 0, 204, 0.15);
            display: flex; align-items: center; justify-content: center;
            cursor: pointer; z-index: 60; box-shadow: 0 6px 14px rgba(24, 0, 204, 0.08);
        }
        .in-table-modal .modal-close i { color: var(--cor_falgadja_1); }
        .in-table-modal iframe {
            width: 100%; height: 100%; border: 0; display: block;
            background: transparent; min-height: 460px;
        }
    </style>
</head>
<body>
<div class="esquerda">
    <div class="sidebar">
        <div class="aplicativo">
            <div class="logo">
                <img src="${pageContext.request.contextPath}/img/CelularFotoContabaco.png" alt="logo">
            </div>
            <div>
                <div class="titulo_app">Contábaco</div>
                <div class="subtitulo_app">adm</div>
            </div>
        </div>
        <div class="linha"></div>
        <div class="nav">
            <a href="<%= request.getContextPath() %>/view/Adm/crudAdm.jsp" class="botao"><i class="fa-solid fa-crown"></i> Adm</a>
            <a href="<%= request.getContextPath() %>/view/Empresa/crudEmpresa.jsp" class="botao"><i class="fa-solid fa-building"></i> Empresas</a>
            <a href="<%= request.getContextPath() %>/view/Funcionario/crudFuncionario.jsp" class="botao selecionado"><i class="fa-solid fa-user-tie"></i> Funcionários</a>
            <a href="<%= request.getContextPath() %>/view/Plano/crudPlano.jsp" class="botao"><i class="fa-solid fa-clipboard-list"></i> Planos</a>
            <a href="<%= request.getContextPath() %>/view/Pagamento/crudPagamento.jsp" class="botao"><i class="fa-solid fa-credit-card"></i> Pagamento</a>
        </div>
        <div class="sair">
            <button class="botao-sair" onclick="location.href='${pageContext.request.contextPath}/view/Login/login.jsp'">
                <i class="fa-solid fa-right-from-bracket"></i> Sair
            </button>
        </div>
    </div>

    <div class="main">
        <div class="titulos">
            <div>
                <div id="AR">Área Restrita</div>
                <div id="CRUD">CRUD</div>
            </div>
        </div>

        <div class="adicionador">
            <button id="openModal" class="botao-add" type="button">
                <i class="fa-solid fa-plus"></i> Adicionar Novo
            </button>
        </div>

        <form action="${pageContext.request.contextPath}/BuscarFuncionarioServlet" method="get" class="filtros">
            <label for="nome">Buscar por nome:</label>
            <input type="text" name="nome" id="nome" placeholder="Digite o nome do fúncionário">

            <label for="idEmpresa">ID da empresa:</label>
            <input type="number" name="idEmpresa" id="idEmpresa" placeholder="Digite o ID da empresa">

            <label for="tipoOrdenacao">Ordenar por:</label>
            <select name="tipoOrdenacao" id="tipoOrdenacao">
                <option value="">-- Nenhum --</option>
                <option value="idCrescente">ID Crescente</option>
                <option value="idDecrescente">ID Decrescente</option>
                <option value="Az">Nome (A-Z)</option>
                <option value="Za">Nome (Z-A)</option>
            </select>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Sobrenome</th>
            <th>Data de nascimento</th>
            <th>Email</th>
            <th>Senha</th>
            <th>ID do setor</th>
            <th>ID da empresa</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="f" items="${funcionarios}">
            <tr>
                <td>${f.id}</td>
                <td>${f.nome}</td>
                <td>${f.sobrenome}</td>
                <td>${f.dataNascimento}</td>
                <td>${f.email}</td>
                <td>${f.senha}</td>
                <td>${f.idSetor}</td>
                <td>${f.idEmpresa}</td>
                <td class="acoes">
                    <!-- Botão para Editar: redireciona para atualizarFuncionario.jsp -->
                    <button onclick="window.location.href='<%= request.getContextPath() %>/view/Funcionario//atualizarFuncionario.jsp?id=${f.id}'" title="Editar">
                        <i class="fa fa-pen"></i>
                    </button>

                    <!-- Botão para Excluir: chama o servlet com confirmação -->
                    <form action="<%= request.getContextPath() %>/DeletarFuncionarioServlet" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${f.id}">
                        <button title="Excluir" onclick="return confirm('Tem certeza que deseja excluir este funcionario?');">
                            <i class="fa fa-trash"></i>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    (function () {
        const modal = document.getElementById('inTableModal');
        const frame = document.getElementById('modalFrame');
        const openBtn = document.getElementById('openModal');
        const closeBtn = document.getElementById('modalClose');

        if (!modal || !frame || !openBtn || !closeBtn) {
            console.error('Elementos do modal não encontrados.');
            return;
        }

        // Abrir Modal de Adicionar
        openBtn.addEventListener('click', function () {
            frame.src = '<%= request.getContextPath() %>/view/Funcionario/cadastrarFuncionario.jsp?modal=1';
            modal.classList.add('open');
            modal.setAttribute('aria-hidden', 'false');
        });

        // Fechar Modal
        function closeModal() {
            modal.classList.remove('open');
            modal.setAttribute('aria-hidden', 'true');
            frame.src = 'about:blank';
            // Descomente a linha abaixo para recarregar a tabela ao fechar
            // window.location.reload();
        }

        closeBtn.addEventListener('click', closeModal);
        document.addEventListener('keydown', function (e) {
            if (e.key === 'Escape' && modal.classList.contains('open')) {
                closeModal();
            }
        });

        // Abrir Modal de Editar
        window.abrirModalEditar = function (id) {
            if (!id) return;
            frame.src = '<%= request.getContextPath() %>/view/Funcionario/atualizarFuncionario.jsp?id=' + id + '&modal=1';
            modal.classList.add('open');
            modal.setAttribute('aria-hidden', 'false');
        };

    })();
</script>

</body>
</html>