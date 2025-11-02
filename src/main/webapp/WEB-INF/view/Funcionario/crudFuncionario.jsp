<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

            <a href="${pageContext.request.contextPath}/funcionarios" class="botao selecionado"><img src="${pageContext.request.contextPath}/img/icone-funcionario-branco.png" alt="" style="width: 19px; height: 20px;"> Funcionários</a>

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
                <div id="CRUD">Funcionários</div>
            </div>
        </div>

        <div class="adicionador">
            <button id="openModal" class="botao-add" type="button"><img src="${pageContext.request.contextPath}/img/icone-adicionar.png" alt="" style="width: 20px; height: 20px;"> Adicionar Novo</button>
        </div>

        <form action="${pageContext.request.contextPath}/funcionarios" method="get" class="filtros">
            <label for="nome">Nome:</label>
            <input type="text" name="nome" id="nome" placeholder="Digite o nome" value="${param.nome}">

            <label for="idEmpresa">ID da empresa:</label>
            <input type="number" name="idEmpresa" id="idEmpresa" placeholder="Digite o ID" value="${param.idEmpresa}">

            <label for="tipoOrdenacao">Ordenar:</label>
            <select name="tipoOrdenacao" id="tipoOrdenacao">
                <option value="">-- Nenhum --</option>
                <option value="idCrescente" ${param.tipoOrdenacao == 'idCrescente' ? 'selected' : ''}>ID Crescente</option>
                <option value="idDecrescente" ${param.tipoOrdenacao == 'idDecrescente' ? 'selected' : ''}>ID Decrescente</option>
                <option value="Az" ${param.tipoOrdenacao == 'Az' ? 'selected' : ''}>Nome (A-Z)</option>
                <option value="Za" ${param.tipoOrdenacao == 'Za' ? 'selected' : ''}>Nome (Z-A)</option>
            </select>

            <button type="submit">Filtrar</button>
        </form>

        <c:if test="${not empty mensagem}">
            <p class="mensagem">${mensagem}</p>
        </c:if>

        <div class="tabela func-style">
            <div class="tabela-container">
                <c:choose>
                    <c:when test="${not empty funcionarios}">
                        <table>
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Sobrenome</th>
                                <th>Data Nasc.</th>
                                <th>Email</th>
                                <th>Senha</th>
                                <th>ID Setor</th>
                                <th>ID Empresa</th>
                                <th>Ações</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="f" items="${funcionarios}">
                                <tr>
                                    <td>${f.id}</td>
                                    <td>${f.nome}</td>
                                    <td>${f.sobrenome}</td>
                                    <td>${f.dataNascimentoFormatada}</td>
                                    <td>${f.email}</td>
                                    <td>${f.senha}</td>
                                    <td>${f.idSetor}</td>
                                    <td>${f.idEmpresa}</td>
                                    <td class="acoes">
                                        <button class="btn" title="Editar" onclick="abrirModalEditar(${f.id})">
                                            <i class="fa-solid fa-pen"></i>
                                        </button>
                                        <form action="${pageContext.request.contextPath}/funcionarios-delete" method="post" style="display:inline;" onsubmit="return confirm('Deseja realmente excluir este funcionário?');">
                                            <input type="hidden" name="id" value="${f.id}">
                                            <button class="btn" type="submit" title="Excluir"><i class="fa-solid fa-trash"></i></button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p style="padding:10px 6px; color:#666;">Nenhum funcionário encontrado.</p>
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
    // Pegando os elementos do modal e do iframe
    const modal = document.getElementById('inTableModal');
    const frame = document.getElementById('modalFrame');
    const openBtn = document.getElementById('openModal');
    const closeBtn = document.getElementById('modalClose');

    // Quando clica no botão "Adicionar Novo"
    openBtn.addEventListener('click', () => {
        // Define a URL do iframe para o formulário de cadastro
        frame.src = '${pageContext.request.contextPath}/funcionarios-create';
        // Abre o modal
        modal.classList.add('open');
        modal.setAttribute('aria-hidden', 'false');
    });

    // Quando clica no "X" do modal
    closeBtn.addEventListener('click', () => {
        // Fecha o modal
        modal.classList.remove('open');
        modal.setAttribute('aria-hidden', 'true');
        // Limpa o iframe
        frame.src = 'about:blank';
        // Recarrega a página pra atualizar a tabela
        window.location.reload();
    });

    // Fecha o modal quando aperta ESC
    document.addEventListener('keydown', e => {
        if(e.key === 'Escape' && modal.classList.contains('open')) {
            modal.classList.remove('open');
            modal.setAttribute('aria-hidden', 'true');
            frame.src = 'about:blank';
            window.location.reload();
        }
    });

    // Função chamada quando clica no botão "Editar" de um funcionário
    window.abrirModalEditar = id => {
        // Define o iframe pra abrir o formulário de edição com o ID correto
        frame.src = '${pageContext.request.contextPath}/funcionarios-update?id=' + id;
        modal.classList.add('open');
        modal.setAttribute('aria-hidden', 'false');
    };
</script>