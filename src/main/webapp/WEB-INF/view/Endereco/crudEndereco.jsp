<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Endereços - Área Restrita</title>
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
            <a href="${pageContext.request.contextPath}/adm" class="botao"><img src="${pageContext.request.contextPath}/img/icone-adm-azul.png" alt="" style="width: 21px; height: 22px;"> Adm</a>

            <a href="${pageContext.request.contextPath}/empresas" class="botao"><img src="${pageContext.request.contextPath}/img/icone-empresa-azul.png" alt="" style="width: 20px; height: 18px;"> Empresas</a>

            <a href="${pageContext.request.contextPath}/funcionarios" class="botao"><img src="${pageContext.request.contextPath}/img/icone-funcionario-azul.png" alt="" style="width: 19px; height: 20px;"> Funcionários</a>

            <a href="${pageContext.request.contextPath}/planos" class="botao"><img src="${pageContext.request.contextPath}/img/icone-plano-azul.png" alt="" style="width: 18px; height: 20px;;"> Plano</a>

            <a href="${pageContext.request.contextPath}/pagamento" class="botao"><img src="${pageContext.request.contextPath}/img/icone-pagamento-azul.png" alt="" style="width: 18px; height: 18px;"> Pagamento</a>

            <a href="${pageContext.request.contextPath}/endereco" class="botao selecionado"><img src="${pageContext.request.contextPath}/img/icone-endereco-branco.png" alt="" style="width: 15px; height: 20px"> Endereços</a>
        </div>

        <div class="sair">
            <button class="botao-sair" onclick="location.href='${pageContext.request.contextPath}/logout'"><img src="${pageContext.request.contextPath}/img/icone-sair.png" alt="" style="width: 17px; height: 20px;"> Sair</button>
        </div>
    </div>

    <div class="main">
        <div class="titulos">
            <div>
                <div id="AR">Área Restrita</div>
                <div id="CRUD">Endereços</div>
            </div>
        </div>

        <div class="adicionador">
            <button id="openModal" class="botao-add" type="button"><img src="${pageContext.request.contextPath}/img/icone-adicionar.png" alt="" style="width: 20px; height: 20px;"> Adicionar Novo</button>
        </div>

        <form action="${pageContext.request.contextPath}/endereco" method="get" class="filtros">
            <label for="cep">CEP:</label>
            <input type="text" name="cep" id="cep" placeholder="Digite o CEP" value="${param.cep}">

            <label for="estado">Estado:</label>
            <select name="estado" id="estado">
                <option value="todos">-- Todos --</option>
                <c:forEach var="uf" items="${['AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO']}">
                    <option value="${uf}" ${param.estado == uf ? 'selected' : ''}>${uf}</option>
                </c:forEach>
            </select>

            <label for="tipoOrdenacao">Ordenar:</label>
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
                                <th>ID Empresa</th>
                                <th>CEP</th>
                                <th>País</th>
                                <th>Estado</th>
                                <th>Cidade</th>
                                <th>Bairro</th>
                                <th>Rua</th>
                                <th>Número</th>
                                <th>Ações</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="e" items="${enderecos}">
                                <tr>
                                    <td>${e.id}</td>
                                    <td>${e.idEmpresa}</td>
                                    <td>${e.cep}</td>
                                    <td>${e.pais}</td>
                                    <td>${e.estado}</td>
                                    <td>${e.cidade}</td>
                                    <td>${e.bairro}</td>
                                    <td>${e.rua}</td>
                                    <td>${e.numero}</td>
                                    <td class="acoes">
                                        <button class="btn" title="Editar" onclick="abrirModalEditar(${e.id})">
                                            <i class="fa-solid fa-pen"></i>
                                        </button>
                                        <form action="${pageContext.request.contextPath}/endereco-delete" method="post" style="display:inline;" onsubmit="return confirm('Deseja realmente excluir este endereço?');">
                                            <input type="hidden" name="id" value="${e.id}">
                                            <button class="btn" type="submit" title="Excluir"><i class="fa-solid fa-trash"></i></button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p style="padding:10px 6px; color:#666;">Nenhum endereço cadastrado.</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="in-table-modal" id="inTableModal" role="dialog" aria-hidden="true">
                <button class="modal-close" id="modalClose"><i class="fa-solid fa-xmark"></i></button>
                <iframe id="modalFrame" src=""></iframe>
            </div>
        </div>
    </div>
</div>

<script>
    const modal = document.getElementById('inTableModal');
    const frame = document.getElementById('modalFrame');
    const openBtn = document.getElementById('openModal');
    const closeBtn = document.getElementById('modalClose');

    openBtn.addEventListener('click', () => {
        frame.src = '${pageContext.request.contextPath}/endereco-create';
        modal.classList.add('open');
        modal.setAttribute('aria-hidden', 'false');
    });

    closeBtn.addEventListener('click', () => {
        modal.classList.remove('open');
        modal.setAttribute('aria-hidden', 'true');
        frame.src = 'about:blank';
        window.location.reload();
    });

    document.addEventListener('keydown', e => {
        if(e.key === 'Escape' && modal.classList.contains('open')) {
            modal.classList.remove('open');
            modal.setAttribute('aria-hidden', 'true');
            frame.src = 'about:blank';
            window.location.reload();
        }
    });

    window.abrirModalEditar = id => {
        frame.src = '${pageContext.request.contextPath}/endereco-update?id=' + id;
        modal.classList.add('open');
        modal.setAttribute('aria-hidden', 'false');
    };
</script>

</body>
</html>