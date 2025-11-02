<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Mantive a taglib fmt por precaução, mas ela não é usada abaixo. --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Funcionários - Área Restrita</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/crud.css">

</head>
<body>
<div class="esquerda">
    <div class="sidebar">
        <div class="aplicativo">
            <div class="logo">a
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
            <a href="${pageContext.request.contextPath}/pagamento" class="botao"><i class="fa-solid fa-credit-card"></i> Pagamento</a>
            <a href="${pageContext.request.contextPath}/endereco" class="botao selecionado"><i class="fa-solid fa-user-tie"></i> Endereços</a>
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

        <form action="${pageContext.request.contextPath}/endereco" method="get" class="filtros">
            <label for="nome">Buscar por nome:</label>
            <input type="text" name="cep" id="cep" placeholder="Digite o CEP do endereço" value="${param.cep}">

            <label for="estado">Estado:</label>
            <select name="estado" id="estado">
                <option value="todos">-- Todos --</option>
                <option value="AC" ${param.estado == 'AC' ? 'selected' : ''}>AC</option>
                <option value="AL" ${param.estado == 'AL' ? 'selected' : ''}>AL</option>
                <option value="AP" ${param.estado == 'AP' ? 'selected' : ''}>AP</option>
                <option value="AM" ${param.estado == 'AM' ? 'selected' : ''}>AM</option>
                <option value="BA" ${param.estado == 'BA' ? 'selected' : ''}>BA</option>
                <option value="CE" ${param.estado == 'CE' ? 'selected' : ''}>CE</option>
                <option value="DF" ${param.estado == 'DF' ? 'selected' : ''}>DF</option>
                <option value="ES" ${param.estado == 'ES' ? 'selected' : ''}>ES</option>
                <option value="GO" ${param.estado == 'GO' ? 'selected' : ''}>GO</option>
                <option value="MA" ${param.estado == 'MA' ? 'selected' : ''}>MA</option>
                <option value="MT" ${param.estado == 'MT' ? 'selected' : ''}>MT</option>
                <option value="MS" ${param.estado == 'MS' ? 'selected' : ''}>MS</option>
                <option value="MG" ${param.estado == 'MG' ? 'selected' : ''}>MG</option>
                <option value="PA" ${param.estado == 'PA' ? 'selected' : ''}>PA</option>
                <option value="PB" ${param.estado == 'PB' ? 'selected' : ''}>PB</option>
                <option value="PR" ${param.estado == 'PR' ? 'selected' : ''}>PR</option>
                <option value="PE" ${param.estado == 'PE' ? 'selected' : ''}>PE</option>
                <option value="PI" ${param.estado == 'PI' ? 'selected' : ''}>PI</option>
                <option value="RJ" ${param.estado == 'RJ' ? 'selected' : ''}>RJ</option>
                <option value="RN" ${param.estado == 'RN' ? 'selected' : ''}>RN</option>
                <option value="RS" ${param.estado == 'RS' ? 'selected' : ''}>RS</option>
                <option value="RO" ${param.estado == 'RO' ? 'selected' : ''}>RO</option>
                <option value="RR" ${param.estado == 'RR' ? 'selected' : ''}>RR</option>
                <option value="SC" ${param.estado == 'SC' ? 'selected' : ''}>SC</option>
                <option value="SP" ${param.estado == 'SP' ? 'selected' : ''}>SP</option>
                <option value="SE" ${param.estado == 'SE' ? 'selected' : ''}>SE</option>
                <option value="TO" ${param.estado == 'TO' ? 'selected' : ''}>TO</option>
            </select>

            <label for="tipoOrdenacao">Ordenar por:</label>
            <select name="tipoOrdenacao" id="tipoOrdenacao">
                <option value="">-- Nenhum --</option>
                <option value="idCrescente" ${param.tipoOrdenacao == 'idCrescente' ? 'selected' : ''}>ID Crescente</option>
                <option value="idDecrescente" ${param.tipoOrdenacao == 'idDecrescente' ? 'selected' : ''}>ID Decrescente</option>
            </select>

            <button type="submit">Filtrar</button>
        </form>

        <c:if test="${not empty mensagem}">
            <p class="mensagem">${mensagem}</p>
        </c:if>

        <div class="tabela func-style">
            <div class="tabela-container">

                <c:choose>
                    <c:when test="${not empty enderecos}">
                        <table>
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>ID da empresa</th>
                                <th>CEP</th>
                                <th>País</th>
                                <th>Estado</th>
                                <th>Cidade</th>
                                <th>Bairro</th>
                                <th>Rua</th>
                                <th>Numero</th>
                                <th class="acoes-col">Ações</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="e" items="${enderecos}">
                                <tr>
                                    <td>${e.id}</td>
                                    <td>${e.idEmpresa}</td>
                                    <td>${e.pais}</td>
                                    <td>${e.estado}</td>
                                    <td>${e.cidade}</td>
                                    <td>${e.bairro}</td>
                                    <td>${e.rua}</td>
                                    <td>${e.numero}</td>
                                    <td class="acoes">
                                        <button class="btn" title="Editar"
                                                onclick="abrirModalEditar(${e.id})">
                                            <i class="fa-solid fa-pen"></i>
                                        </button>

                                        <form action="${pageContext.request.contextPath}/endereco-delete" method="post" style="display: inline;" onsubmit="return confirm('Deseja realmente excluir este endereço?');">
                                            <input type="hidden" name="id" value="${e.id}">
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
                        <p style="padding:10px 6px; color:#666;">Nenhum endereço cadastrado (ou encontrado no filtro).</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="in-table-modal" id="inTableModal" role="dialog" aria-hidden="true"
                 aria-label="Formulário do Endereço">
                <button class="modal-close" id="modalClose" title="Fechar"><i
                        class="fa-solid fa-xmark"></i></button>
                <iframe id="modalFrame" src="" name="modalFrame"
                        title="Formulário Cadastrar/Atualizar Endereço"></iframe>
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

        // Abrir Modal de Adicionar (aponta para /enderecos-create)
        openBtn.addEventListener('click', function () {
            frame.src = '${pageContext.request.contextPath}/endereco-create';
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

        // Abrir Modal de Editar (aponta para /enderecos-update)
        window.abrirModalEditar = function (id) {
            if (!id) return;
            frame.src = '${pageContext.request.contextPath}/endereco-update?id=' + id;
            modal.classList.add('open');
            modal.setAttribute('aria-hidden', 'false');
        };

    })();
</script>

</body>
</html>