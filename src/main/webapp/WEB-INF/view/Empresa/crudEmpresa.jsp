<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Área Restrita - Empresas</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/crud.css">
</head>
<body>

<div class="esquerda">
    <div class="sidebar">
        <div class="aplicativo">
            <div class="logo"><img src="${pageContext.request.contextPath}/img/CelularFotoContabaco.png" alt="logo"></div>
            <div>
                <div class="titulo_app">Contábaco</div>
                <div class="subtitulo_app">adm</div>
            </div>
        </div>

        <div class="linha"></div>

        <div class="nav">
            <a href="${pageContext.request.contextPath}/adm" class="botao"><img src="${pageContext.request.contextPath}/img/icone-adm-azul.png" alt="" style="width: 21px; height: 22px;"> Adm</a>

            <a href="${pageContext.request.contextPath}/empresas" class="botao selecionado"><img src="${pageContext.request.contextPath}/img/icone-empresa-branco.png" alt="" style="width: 20px; height: 18px;"> Empresas</a>

            <a href="${pageContext.request.contextPath}/funcionarios" class="botao"><img src="${pageContext.request.contextPath}/img/icone-funcionario-azul.png" alt="" style="width: 19px; height: 20px;"> Funcionários</a>

            <a href="${pageContext.request.contextPath}/planos" class="botao"><img src="${pageContext.request.contextPath}/img/icone-plano-azul.png" alt="" style="width: 18px; height: 20px;;"> Plano</a>

            <a href="${pageContext.request.contextPath}/pagamento" class="botao"><img src="${pageContext.request.contextPath}/img/icone-pagamento-azul.png" alt="" style="width: 18px; height: 18px;"> Pagamento</a>

            <a href="${pageContext.request.contextPath}/endereco" class="botao"><img src="${pageContext.request.contextPath}/img/icone-endereco-azul.png" alt="" style="width: 15px; height: 20px"> Endereços</a>
        </div>

        <div class="sair">
            <button class="botao-sair" onclick="location.href='${pageContext.request.contextPath}/logout'"><img src="${pageContext.request.contextPath}/img/icone-sair.png" alt="" style="width: 17px; height: 20px;"> Sair</button>
        </div>
    </div>

    <div class="main">
        <div class="titulos">
            <div>
                <div id="AR">Área Restrita</div>
                <div id="CRUD">Empresas</div>
            </div>
        </div>

        <div class="adicionador">
            <button id="openModal" class="botao-add" type="button"><img src="${pageContext.request.contextPath}/img/icone-adicionar.png" alt="" style="width: 20px; height: 20px;"> Adicionar Novo</button>
        </div>

        <form action="${pageContext.request.contextPath}/empresas" method="get" class="filtros">
            <label for="filtroNome">Nome:</label>
            <input type="text" name="filtroNome" id="filtroNome" placeholder="Digite o nome" value="${param.filtroNome != null ? param.filtroNome : ''}">

            <label for="filtroMinFuncionarios">Funcionários (min):</label>
            <input type="number" name="filtroMinFuncionarios" id="filtroMinFuncionarios" value="${param.filtroMinFuncionarios != null ? param.filtroMinFuncionarios : ''}" style="width:80px;">

            <label for="filtroMaxFuncionarios">(max):</label>
            <input type="number" name="filtroMaxFuncionarios" id="filtroMaxFuncionarios" value="${param.filtroMaxFuncionarios != null ? param.filtroMaxFuncionarios : ''}" style="width:80px;">

            <label for="ordenacao">Ordenar:</label>
            <select name="ordenacao" id="ordenacao">
                <option value="">-- Nenhum --</option>
                <option value="idCrescente" ${param.ordenacao == 'idCrescente' ? 'selected' : ''}>ID Crescente</option>
                <option value="idDecrescente" ${param.ordenacao == 'idDecrescente' ? 'selected' : ''}>ID Decrescente</option>
                <option value="nomeAZ" ${param.ordenacao == 'nomeAZ' ? 'selected' : ''}>Nome (A-Z)</option>
                <option value="nomeZA" ${param.ordenacao == 'nomeZA' ? 'selected' : ''}>Nome (Z-A)</option>
                <option value="funcionariosCrescente" ${param.ordenacao == 'funcionariosCrescente' ? 'selected' : ''}>Qtd. Funcionários ↑</option>
                <option value="funcionariosDecrescente" ${param.ordenacao == 'funcionariosDecrescente' ? 'selected' : ''}>Qtd. Funcionários ↓</option>
            </select>

            <button type="submit" class="botao-filtrar"><i class="fa-solid fa-filter"></i> Filtrar</button>
        </form>

        <c:if test="${not empty mensagem}">
            <p class="mensagem">${mensagem}</p>
        </c:if>

        <div class="tabela tabela-adm empresa-style">
            <div class="tabela-container">
                <c:choose>
                    <c:when test="${not empty listaEmpresas}">
                        <table>
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>CNPJ</th>
                                <th>Nome</th>
                                <th>Email</th>
                                <th>Senha</th>
                                <th>ID Plano</th>
                                <th>Qtd Funcionários</th>
                                <th>Ações</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="empresa" items="${listaEmpresas}">
                                <tr>
                                    <td>${empresa.id}</td>
                                    <td>${empresa.cnpj}</td>
                                    <td>${empresa.nome}</td>
                                    <td>${empresa.email}</td>
                                    <td>${empresa.senha}</td>
                                    <td>${empresa.idPlano}</td>
                                    <td>${empresa.qntdFuncionarios}</td>
                                    <td class="acoes">
                                        <button class="btn" onclick="abrirModalEditar(${empresa.id})" title="Editar">
                                            <i class="fa-solid fa-pen"></i>
                                        </button>

                                        <form action="${pageContext.request.contextPath}/empresa-delete" method="post" style="display:inline;"
                                              onsubmit="return confirm('Deseja realmente excluir esta empresa?');">
                                            <input type="hidden" name="id" value="${empresa.id}">
                                            <button class="btn" type="submit" title="Excluir"><i class="fa-solid fa-trash"></i></button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p style="padding:10px 6px; color:#666;">Nenhuma empresa encontrada.</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="in-table-modal" id="inTableModal" role="dialog" aria-hidden="true">
                <button class="modal-close" id="modalClose" title="Fechar"><i class="fa-solid fa-xmark"></i></button>
                <iframe id="modalFrame" src="" name="modalFrame"></iframe>
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
        frame.src = '${pageContext.request.contextPath}/empresa-create?modal=1';
        modal.classList.add('open');
        modal.setAttribute('aria-hidden', 'false');
    });

    function fecharModal() {
        modal.classList.remove('open');
        modal.setAttribute('aria-hidden', 'true');
        frame.src = 'about:blank';
        window.location.reload();
    }

    closeBtn.addEventListener('click', fecharModal);

    document.addEventListener('keydown', e => {
        if (e.key === 'Escape' && modal.classList.contains('open')) fecharModal();
    });

    window.abrirModalEditar = id => {
        frame.src = '${pageContext.request.contextPath}/empresa-update?id=' + id + '&modal=1';
        modal.classList.add('open');
        modal.setAttribute('aria-hidden', 'false');
    };
</script>

</body>
</html>