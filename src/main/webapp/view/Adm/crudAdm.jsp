<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Administrador" %>
<%@ page import="dao.AdmDAO" %>
<%@ page import="java.lang.reflect.Field" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <title>Área Restrita - Administradores</title>
    <link rel="icon"
          href="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

    <style>
        :root {
            --cor_falgadja_1: #1800CC;
            --cor_falgadja_2: #0C0066;
            --cor_sair: #b71c1c;
            --branco: #ffffff;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Poppins', system-ui, -apple-system, 'Segoe UI', Roboto, Arial;
        }

        html,
        body {
            height: 100%;
            width: 100%;
            background: var(--branco);
            color: var(--cor_falgadja_1);
        }

        .esquerda {
            display: flex;
            height: 100vh;
            width: 100%;
            overflow: hidden;
        }

        .sidebar {
            width: 255px;
            padding: 28px 20px;
            display: flex;
            flex-direction: column;
            gap: 18px;
        }

        .aplicativo {
            display: flex;
            gap: 15px;
            align-items: center;
        }

        .logo {
            width: 56px;
            height: 56px;
            border-radius: 10px;
            border: 2px solid var(--cor_falgadja_1);
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }

        .logo img {
            max-width: 100%;
            max-height: 100%;
            display: block;
        }

        .titulo_app {
            font-weight: 700;
            color: var(--cor_falgadja_1);
            font-size: 20px;
        }

        .subtitulo_app {
            font-size: 12px;
            color: grey;
            margin-top: 2px;
        }

        .linha {
            height: 6px;
            border-radius: 30px;
            background: linear-gradient(90deg, var(--cor_falgadja_1), var(--cor_falgadja_2));
            width: 80%;
            margin: 6px 0;
        }

        .nav {
            display: flex;
            flex-direction: column;
            gap: 12px;
            margin-top: 10px;
        }

        .botao {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 18px;
            border-radius: 10px;
            border: 2px solid var(--cor_falgadja_1);
            background: transparent;
            color: var(--cor_falgadja_1);
            font-weight: 600;
            width: 190px;
            cursor: pointer;
            text-decoration: none;
        }

        .botao.selecionado {
            background: linear-gradient(180deg, var(--cor_falgadja_1), var(--cor_falgadja_2));
            color: #fff;
            border-color: transparent;
            box-shadow: 0 6px 18px rgba(12, 0, 102, 0.25);
        }

        .separator {
            width: 4px;
            background: var(--cor_falgadja_1);
        }

        .main {
            flex: 1;
            padding: 36px 48px;
            overflow: auto;
        }

        .titulos {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 12px;
        }

        #AR {
            font-size: 30px;
            font-weight: 800;
            color: var(--cor_falgadja_1);
        }

        #CRUD {
            color: grey;
            font-weight: 600;
            margin-top: -10px;
        }

        .pesquisar {
            width: 480px;
            height: 44px;
            border-radius: 10px;
            border: 2px solid var(--cor_falgadja_1);
            display: flex;
            align-items: center;
            padding: 8px 12px;
            gap: 10px;
        }

        .pesquisar input {
            border: 0;
            outline: 0;
            flex: 1;
            font-size: 14px;
            color: var(--cor_falgadja_1);
        }

        .pesquisar input::placeholder {
            color: rgba(24, 0, 204, 0.45);
        }

        .adicionador {
            margin-top: 28px;
        }

        .botao-add {
            padding: 12px 18px;
            border-radius: 12px;
            background: linear-gradient(180deg, var(--cor_falgadja_1), var(--cor_falgadja_2));
            color: #fff;
            font-weight: 700;
            border: 0;
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            gap: 10px;
        }

        .sair {
            margin-top: auto;
        }

        .botao-sair {
            padding: 12px 18px;
            border-radius: 12px;
            background: var(--cor_sair);
            color: white;
            border: 0;
            cursor: pointer;
            font-weight: 700;
            width: 100%;
            display: inline-flex;
            align-items: center;
            gap: 10px;
        }

        .tabela,
        .tabela-adm {
            margin-top: 20px;
            border: 3px solid rgba(24, 0, 204, 0.95);
            border-radius: 14px;
            padding: 10px;
            width: calc(100% - 32px);
            max-width: 1180px;
            margin-left: auto;
            margin-right: auto;
            box-shadow: 0 10px 22px rgba(12, 0, 102, 0.08);
            position: relative;
            background: #fff;
            overflow: visible;
        }

        .tabela-container {
            width: 100%;
            max-height: calc(100vh - 260px);
            overflow-y: auto;
            overflow-x: auto;
            padding: 10px;
        }

        .tabela-container::-webkit-scrollbar {
            height: 10px;
            width: 10px;
        }

        .tabela-container::-webkit-scrollbar-thumb {
            background: linear-gradient(180deg, rgba(24, 0, 204, 0.2), rgba(12, 0, 102, 0.2));
            border-radius: 10px;
        }

        .adm-style table {
            width: 100%;
            table-layout: fixed;
            border-collapse: collapse;
            font-size: 14px;
        }

        .adm-style thead th,
        .adm-style tbody td {
            padding: 14px 16px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            font-weight: 600;
            text-align: center;
        }

        .adm-style tbody tr:nth-child(even) {
            background: #fafaff;
        }

        .adm-style tbody tr:hover {
            background: rgba(24, 0, 204, 0.04);
        }

        .adm-style th.acoes-col,
        .adm-style td.acoes {
            position: sticky;
            right: 0;
            background: #fff;
            box-shadow: -8px 0 12px rgba(12, 0, 102, 0.06);
            width: 170px;
            min-width: 170px;
            max-width: 260px;
            vertical-align: middle;
            padding: 0 8px;
            white-space: nowrap;
        }

        .adm-style td.acoes .btn {
            display: inline-flex;
            margin: 0 4px;
            align-items: center;
            justify-content: center;
            width: 32px;
            height: 32px;
            border-radius: 6px;
            border: 1.5px solid var(--cor_falgadja_1);
            background: transparent;
            color: var(--cor_falgadja_1);
            cursor: pointer;
            transition: 0.2s;
        }

        .adm-style td.acoes .btn:hover {
            background: var(--cor_falgadja_1);
            color: #fff;
        }

        .in-table-modal {
            position: fixed;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            width: min(80%, 900px);
            min-height: 460px;
            max-height: calc(100% - 60px);
            border-radius: 18px;
            border: 3px solid rgba(24, 0, 204, 0.95);
            background: #fff;
            box-shadow: 0 18px 40px rgba(12, 0, 102, 0.12);
            z-index: 1000;
            display: none;
            overflow: hidden;
            padding: 24px 36px;
        }


        .in-table-modal.open {
            display: block;
        }

        .in-table-modal .modal-close {
            position: absolute;
            top: 10px;
            right: 10px;
            width: 44px;
            height: 44px;
            border-radius: 50%;
            background: #fff;
            border: 2px solid rgba(24, 0, 204, 0.15);
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            z-index: 60;
            box-shadow: 0 6px 14px rgba(24, 0, 204, 0.08);
        }

        .in-table-modal .modal-close i {
            color: var(--cor_falgadja_1);
        }

        .in-table-modal iframe {
            width: 100%;
            height: 100%;
            border: 0;
            display: block;
            background: transparent;
            min-height: 460px;
        }

        @media (max-width:920px) {
            .in-table-modal {
                width: calc(100% - 32px);
                left: 50%;
                transform: translate(-50%, -45%);
            }
        }
    </style>
</head>

<body>
<div class="esquerda">
    <div class="sidebar">
        <div class="aplicativo">
            <div class="logo"><img src="${pageContext.request.contextPath}/img/CelularFotoContabaco.png"
                                   alt="logo"></div>
            <div>
                <div class="titulo_app">Contábaco</div>
                <div class="subtitulo_app">adm</div>
            </div>
        </div>

        <div class="linha"></div>

        <div class="nav">
            <a href="<%= request.getContextPath() %>/view/Adm/crudAdm.jsp" class="botao selecionado"><i
                    class="fa-solid fa-crown"></i> Adm</a>
            <a href="<%= request.getContextPath() %>/view/Empresa/crudEmpresa.jsp" class="botao"><i
                    class="fa-solid fa-building"></i> Empresa</a>
            <a href="<%= request.getContextPath() %>/view/Funcionario/crudFuncionario.jsp" class="botao"><i
                    class="fa-solid fa-user-tie"></i> Funcionário</a>
            <a href="<%= request.getContextPath() %>/view/Plano/crudPlano.jsp" class="botao"><i
                    class="fa-solid fa-clipboard-list"></i> Plano</a>
            <a href="<%= request.getContextPath() %>/view/Pagamento/crudPagamento.jsp" class="botao"><i
                    class="fa-solid fa-credit-card"></i> Pagamento</a>
        </div>

        <div class="sair">
            <button class="botao-sair" onclick="location.href='${pageContext.request.contextPath}/LogoutServlet'">
                <i class="fa-solid fa-right-from-bracket"></i> Sair
            </button>
        </div>
    </div>

    <div class="separator"></div>

    <div class="main">
        <div class="titulos">
            <div>
                <div id="AR">Área Restrita</div>
                <div id="CRUD">CRUD</div>
            </div>
            <div class="pesquisar">
                <i class="fa fa-search"></i>
                <input type="text" placeholder="Buscar por id, nome, email...">
            </div>
        </div>

        <div class="adicionador">
            <button id="openModal" class="botao-add" type="button">
                <i class="fa-solid fa-plus"></i> Adicionar Novo
            </button>
        </div>

        <div class="tabela tabela-adm adm-style" id="tabelaContainer">
            <div class="tabela-container">
                <table>
                    <thead>
                    <tr>
                        <th>id_adm</th>
                        <th>email</th>
                        <th>senha</th>
                        <th class="acoes-col">ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% AdmDAO dao=new AdmDAO(); List<Administrador> lista = dao.listar();
                        for (Administrador a : lista) {
                    %>
                    <tr>
                        <td>
                            <%= a.getId() %>
                        </td>
                        <td>
                            <%= a.getEmail() %>
                        </td>
                        <td>
                            <%= a.getSenha() %>
                        </td>
                        <td class="acoes">
                            <button class="btn" title="Visualizar"
                                    onclick="window.location.href='<%= request.getContextPath() %>/view/Adm/visualizarAdm.jsp?id=<%= a.getId() %>'"><i
                                    class="fa-regular fa-eye"></i></button>
                            <button class="btn" title="Editar" onclick="abrirModalEditar(<%= a.getId() %>)">
                                <i class="fa-solid fa-pen"></i>
                            </button>
                            <button class="btn" title="Excluir"
                                    onclick="if(confirm('Deseja excluir este administrador?')) window.location.href='<%= request.getContextPath() %>/DeletarAdmServlet?id=<%= a.getId() %>';"><i
                                    class="fa-solid fa-trash"></i></button>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
            <div class="in-table-modal" id="inTableModal" role="dialog" aria-hidden="true"
                 aria-label="Cadastrar Administrador">
                <button class="modal-close" id="modalClose" title="Fechar"><i
                        class="fa-solid fa-xmark"></i></button>
                <iframe id="modalFrame" src="" name="modalFrame"
                        title="Formulário Cadastrar Administrador"></iframe>
            </div>
        </div>
    </div>
</div>

<script>
    function abrirModalEditar(id) {
        const modal = document.getElementById('inTableModal');
        const frame = document.getElementById('modalFrame');
        const ctx = '<%= request.getContextPath() %>';
        frame.src = ctx + '/view/Adm/atualizarAdm.jsp?modal=1&id=' + encodeURIComponent(id);
        modal.classList.add('open');
        modal.setAttribute('aria-hidden', 'false');
    }

    (function () {
        const openBtn = document.getElementById('openModal');
        const modal = document.getElementById('inTableModal');
        const closeBtn = document.getElementById('modalClose');
        const frame = document.getElementById('modalFrame');

        openBtn.addEventListener('click', function () {
            frame.src = '<%= request.getContextPath() %>/view/Adm/cadastrarAdm.jsp?modal=1';
            modal.classList.add('open');
            modal.setAttribute('aria-hidden', 'false');
        });

        function closeModal() {
            modal.classList.remove('open');
            modal.setAttribute('aria-hidden', 'true');
            frame.src = 'about:blank';
        }

        closeBtn.addEventListener('click', closeModal);
        document.addEventListener('keydown', function (e) {
            if (e.key === 'Escape' && modal.classList.contains('open')) closeModal();
        });
    })();
</script>
</body>

</html>