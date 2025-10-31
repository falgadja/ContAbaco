<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Imports de DAO removidos --%>
<%
    String modalParam = request.getParameter("modal");
    boolean isModal = "1".equals(modalParam);
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Pagamentos - Área Restrita</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>

    <style>
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
        .filtros {
            margin-top: 24px;
            margin-bottom: -10px;
            padding: 16px;
            background: #fafaff;
            border-radius: 12px;
            border: 2px solid rgba(24, 0, 204, 0.1);
            display: flex;
            flex-wrap: nowrap;
            align-items: center;
            gap: 16px;
            width: calc(100% - 32px);
            max-width: 1180px;
            margin-left: auto;
            margin-right: auto;
            overflow-x: auto;
            padding-bottom: 20px;
        }
        .filtros label {
            font-weight: 600;
            color: var(--cor_falgadja_1);
            font-size: 14px;
            margin-right: -8px;
            flex-shrink: 0;
            white-space: nowrap;
        }
        .filtros input[type="text"],
        .filtros input[type="date"],
        .filtros select {
            padding: 10px 14px;
            border-radius: 10px;
            border: 2px solid rgba(24, 0, 204, 0.18);
            outline: none;
            font-size: 14px;
            color: #222;
            flex-shrink: 0;
        }
        .filtros select[multiple] {
            height: 100px;
            padding: 10px;
        }
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
            flex-shrink: 0;
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
        .tabela {
            margin-top:20px;
            border:3px solid rgba(24,0,204,0.95);
            border-radius:14px;
            padding:10px;
            width: calc(100% - 32px);
            max-width: 1180px;
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
        .pag-style table { width:100%; table-layout:auto; border-collapse:collapse; font-size:14px; white-space:nowrap; }
        .pag-style thead th, .pag-style tbody td {
            padding:14px 16px; overflow:visible; text-overflow:clip; white-space:nowrap;
            font-weight:600; text-align:center;
        }
        .pag-style tbody tr:nth-child(even){ background:#fafaff; }
        .pag-style tbody tr:hover{ background:rgba(24,0,204,0.04); }
        .pag-style th.acoes-col, .pag-style td.acoes {
            position:sticky; right:0; background:#fff; box-shadow:-8px 0 12px rgba(12,0,102,0.06);
            min-width:170px; padding:0 8px; vertical-align:middle; text-align:center; overflow:visible; white-space:nowrap;
        }
        .pag-style td.acoes .btn {
            display:inline-flex; align-items:center; justify-content:center; width:32px; height:32px;
            border-radius:6px; border:1.5px solid var(--cor_falgadja_1); background:transparent;
            color: var(--cor_falgadja_1); cursor:pointer; transition:.2s; margin: 0 4px;
        }
        .pag-style td.acoes .btn:hover { background: var(--cor_falgadja_1); color: #fff; }
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
            <a href="${pageContext.request.contextPath}/adm" class="botao"><i class="fa-solid fa-crown"></i> Adm</a>
            <a href="${pageContext.request.contextPath}/empresas" class="botao"><i class="fa-solid fa-building"></i> Empresas</a>
            <a href="${pageContext.request.contextPath}/funcionarios" class="botao"><i class="fa-solid fa-user-tie"></i> Funcionários</a>
            <a href="${pageContext.request.contextPath}/planos" class="botao"><i class="fa-solid fa-clipboard-list"></i> Planos</a>
            <a href="${pageContext.request.contextPath}/pagamento" class="botao selecionado"><i class="fa-solid fa-credit-card"></i> Pagamento</a>
        </div>

        <div class="sair">
            <button class="botao-sair" onclick="location.href='${pageContext.request.contextPath}/logout'">
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

        <form action="${pageContext.request.contextPath}/pagamento" method="get" class="filtros">

            <label for="id">Buscar por ID:</label>
            <input type="text" name="id" id="id" placeholder="Digite o ID" style="width: 120px;" value="${param.id}">

            <label for="tipoOrdenacao">Ordenar por:</label>
            <select name="tipoOrdenacao" id="tipoOrdenacao">
                <option value="">-- Nenhum --</option>
                <option value="idCrescente" ${param.tipoOrdenacao == 'idCrescente' ? 'selected' : ''}>ID Crescente</option>
                <option value="idDecrescente" ${param.tipoOrdenacao == 'idDecrescente' ? 'selected' : ''}>ID Decrescente</option>
                <option value="totalCrescente" ${param.tipoOrdenacao == 'totalCrescente' ? 'selected' : ''}>Total Crescente</option>
                <option value="totalDecrescente" ${param.tipoOrdenacao == 'totalDecrescente' ? 'selected' : ''}>Total Decrescente</option>
                <option value="dataCrescente" ${param.tipoOrdenacao == 'dataCrescente' ? 'selected' : ''}>Data Crescente</option>
                <option value="dataDecrescente" ${param.tipoOrdenacao == 'dataDecrescente' ? 'selected' : ''}>Data Decrescente</option>
            </select>

            <label for="tipos">Tipo de pagamento:</label>
            <select name="tipos" id="tipos">
                <option value="">-- Todos --</option>
                <option value="PIX" ${param.tipos == 'PIX' ? 'selected' : ''}>PIX</option>
                <option value="Boleto" ${param.tipos == 'Boleto' ? 'selected' : ''}>Boleto</option>
                <option value="Cartão" ${param.tipos == 'Cartão' ? 'selected' : ''}>Cartão</option>
                <option value="Transferência" ${param.tipos == 'Transferência' ? 'selected' : ''}>Transferência</option>
            </select>

            <label for="inicio">Data início:</label>
            <input type="date" name="inicio" id="inicio" value="${param.inicio}">

            <label for="fim">Data fim:</label>
            <input type="date" name="fim" id="fim" value="${param.fim}">

            <button type="submit">Filtrar</button>
        </form>

        <c:if test="${not empty mensagem}">
            <p class="mensagem">${mensagem}</p>
        </c:if>

        <div class="tabela pag-style">
            <div class="tabela-container">

                <c:choose>
                    <c:when test="${not empty pagamentos}">
                        <table>
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tipo de Pagamento</th>
                                <th>Total (R$)</th>
                                <th>Data</th>
                                <th>ID Empresa</th>
                                <th class="acoes-col">Ações</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="p" items="${pagamentos}">
                                <tr>
                                    <td>${p.id}</td>
                                    <td>${p.tipoPagto}</td>
                                    <td>${p.total}</td>
                                    <td>${p.data}</td>
                                    <td>${p.idEmpresa}</td>
                                    <td class="acoes">
                                        <button class="btn" title="Editar"
                                                onclick="abrirModalEditar(${p.id})">
                                            <i class="fa-solid fa-pen"></i>
                                        </button>

                                        <form action="${pageContext.request.contextPath}/pagamento-delete" method="post" style="display: inline;" onsubmit="return confirm('Deseja realmente excluir este pagamento?');">
                                            <input type="hidden" name="id" value="${p.id}">
                                            <button class="btn" title="Excluir" type="submit">
                                                <i class="fa-solid fa-trash"></i>
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p style="padding:10px 6px; color:#666;">Nenhum pagamento cadastrado (ou encontrado no filtro).</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="in-table-modal" id="inTableModal" role="dialog" aria-hidden="true"
                 aria-label="Formulário do Pagamento">
                <button class="modal-close" id="modalClose" title="Fechar"><i
                        class="fa-solid fa-xmark"></i></button>
                <iframe id="modalFrame" src="" name="modalFrame"
                        title="Formulário Cadastrar/Atualizar Pagamento"></iframe>
            </div>
        </div>
    </div>
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

        // Abrir Modal de Adicionar (aponta para /pagamento-create)
        openBtn.addEventListener('click', function () {
            frame.src = '${pageContext.request.contextPath}/pagamento-create';
            modal.classList.add('open');
            modal.setAttribute('aria-hidden', 'false');
        });

        // Fechar Modal
        function closeModal() {
            modal.classList.remove('open');
            modal.setAttribute('aria-hidden', 'true');
            frame.src = 'about:blank';

            // Recarrega a página para mostrar dados atualizados
            window.location.reload();
        }

        closeBtn.addEventListener('click', closeModal);
        document.addEventListener('keydown', function (e) {
            if (e.key === 'Escape' && modal.classList.contains('open')) {
                closeModal();
            }
        });

        // Abrir Modal de Editar (aponta para /pagamento-update)
        window.abrirModalEditar = function (id) {
            if (!id) return;
            frame.src = '${pageContext.request.contextPath}/pagamento-update?id=' + id;
            modal.classList.add('open');
            modal.setAttribute('aria-hidden', 'false');
        };

    })();
</script>

</body>
</html>