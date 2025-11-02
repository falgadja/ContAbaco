<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/crud.css">

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
            <a href="${pageContext.request.contextPath}/endereco" class="botao"><i class="fa-solid fa-user-tie"></i> Endereços</a>
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
                <option value="todos">-- Todos --</option>
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