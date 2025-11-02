<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Empresas - Área Restrita</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/crud.css">
</head>
<body>

<div class="esquerda">
    <!-- Sidebar da área restrita -->
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

        <!-- Navegação principal -->
        <div class="nav">
            <a href="${pageContext.request.contextPath}/adm" class="botao"><i class="fa-solid fa-crown"></i> Adm</a>
            <a href="${pageContext.request.contextPath}/empresas" class="botao selecionado"><i class="fa-solid fa-building"></i> Empresas</a>
            <a href="${pageContext.request.contextPath}/funcionarios" class="botao"><i class="fa-solid fa-user-tie"></i> Funcionários</a>
            <a href="${pageContext.request.contextPath}/planos" class="botao"><i class="fa-solid fa-clipboard-list"></i> Planos</a>
            <a href="${pageContext.request.contextPath}/pagamento" class="botao"><i class="fa-solid fa-credit-card"></i> Pagamento</a>
            <a href="${pageContext.request.contextPath}/endereco" class="botao"><i class="fa-solid fa-location-dot"></i> Endereços</a>
        </div>

        <!-- Botão de sair -->
        <div class="sair">
            <button class="botao-sair" onclick="location.href='${pageContext.request.contextPath}/logout'">
                <i class="fa-solid fa-right-from-bracket"></i> Sair
            </button>
        </div>
    </div>

    <div class="main">
        <!-- Cabeçalho da seção -->
        <div class="titulos">
            <div>
                <div id="AR">Área Restrita</div>
                <div id="CRUD">CRUD</div>
            </div>
        </div>

        <!-- Botão para adicionar nova empresa -->
        <div class="adicionador">
            <button id="abrirModalCriar" class="botao-add" type="button">
                <i class="fa-solid fa-plus"></i> Adicionar Novo
            </button>
        </div>

        <!-- Filtros de busca -->
        <form action="${pageContext.request.contextPath}/empresas" method="get" class="filtros">
            <label for="filtroNome">Buscar por Nome:</label>
            <input type="text" name="filtroNome" id="filtroNome" placeholder="Digite o nome da empresa" value="${param.filtroNome}">

            <label for="filtroMinFuncionarios">Funcionários (min):</label>
            <input type="number" name="filtroMinFuncionarios" id="filtroMinFuncionarios" value="${param.filtroMinFuncionarios}" style="width: 80px;">

            <label for="filtroMaxFuncionarios">(max):</label>
            <input type="number" name="filtroMaxFuncionarios" id="filtroMaxFuncionarios" value="${param.filtroMaxFuncionarios}" style="width: 80px;">

            <label for="ordenacao">Ordenar por:</label>
            <select name="ordenacao" id="ordenacao">
                <option value="">-- Nenhum --</option>
                <option value="idCrescente" ${param.ordenacao == 'idCrescente' ? 'selected' : ''}>ID Crescente</option>
                <option value="idDecrescente" ${param.ordenacao == 'idDecrescente' ? 'selected' : ''}>ID Decrescente</option>
                <option value="nomeAZ" ${param.ordenacao == 'nomeAZ' ? 'selected' : ''}>Nome (A-Z)</option>
                <option value="nomeZA" ${param.ordenacao == 'nomeZA' ? 'selected' : ''}>Nome (Z-A)</option>
                <option value="funcionariosCrescente" ${param.ordenacao == 'funcionariosCrescente' ? 'selected' : ''}>Qtd. Func. Crescente</option>
                <option value="funcionariosDecrescente" ${param.ordenacao == 'funcionariosDecrescente' ? 'selected' : ''}>Qtd. Func. Decrescente</option>
            </select>

            <button type="submit">Filtrar</button>
        </form>

        <!-- Mensagem de feedback (sucesso, erro, etc.) -->
        <c:if test="${not empty mensagem}">
            <p class="mensagem">${mensagem}</p>
        </c:if>

        <!-- Tabela de empresas -->
        <div class="tabela empresa-style">
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
                                <th>Qtd Funcionarios</th>
                                <th class="acoes-col">Ações</th>
                            </tr>
                            </thead>
                            <tbody>
                            <!-- Loop de exibição de empresas -->
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
                                        <!-- Botão de Detalhes -->
                                        <button class="btn" title="Detalhes" onclick="abrirModalDetalhes(${empresa.id})">
                                            <i class="fa-solid fa-info"></i>
                                        </button>

                                        <!-- Botão de Editar -->
                                        <button class="btn" title="Editar" onclick="abrirModalEditar(${empresa.id})">
                                            <i class="fa-solid fa-pen"></i>
                                        </button>

                                        <!-- Botão de Excluir -->
                                        <form action="${pageContext.request.contextPath}/empresa-delete" method="post" style="display:inline;"
                                              onsubmit="return confirm('Deseja realmente excluir esta empresa?');">
                                            <input type="hidden" name="id" value="${empresa.id}">
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
                        <p style="padding:10px 6px; color:#666;">Nenhuma empresa cadastrada (ou encontrada no filtro).</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Modal reutilizável -->
            <div class="in-table-modal" id="modalTabela">
                <button class="modal-close" id="botaoFecharModal" title="Fechar">
                    <i class="fa-solid fa-xmark"></i>
                </button>
                <iframe id="iframeModal" src="" width="100%" height="100%"></iframe>
            </div>
        </div>
    </div>
</div>

<script>
    // Script para controle dos modais da tabela
    (function () {
        const modal = document.getElementById('modalTabela');
        const frame = document.getElementById('iframeModal');
        const botaoAbrir = document.getElementById('abrirModalCriar');
        const botaoFechar = document.getElementById('botaoFecharModal');

        // Caso algum elemento não exista, mostra erro no console
        if (!modal || !frame || !botaoAbrir || !botaoFechar) {
            console.error('Algum elemento do modal não foi encontrado.');
            return;
        }

        // Abre o modal para cadastrar nova empresa
        botaoAbrir.addEventListener('click', function () {
            frame.src = '${pageContext.request.contextPath}/empresa-create';
            modal.classList.add('open');
            modal.setAttribute('aria-hidden', 'false');
        });

        // Função que fecha o modal e atualiza a página
        function fecharModal() {
            modal.classList.remove('open');
            modal.setAttribute('aria-hidden', 'true');
            frame.src = 'about:blank';
            window.location.reload(); // Atualiza a tabela automaticamente
        }

        // Fecha o modal no clique do botão “X”
        botaoFechar.addEventListener('click', fecharModal);

        // Permite fechar com a tecla “ESC”
        document.addEventListener('keydown', function (e) {
            if (e.key === 'Escape' && modal.classList.contains('open')) {
                fecharModal();
            }
        });

        // Abre o modal de detalhes da empresa
        window.abrirModalDetalhes = function (id) {
            if (!id) return;
            frame.src = '${pageContext.request.contextPath}/empresa-detalhes?id=' + id;
            modal.classList.add('open');
            modal.setAttribute('aria-hidden', 'false');
        };

        // Abre o modal de edição da empresa
        window.abrirModalEditar = function (id) {
            if (!id) return;
            frame.src = '${pageContext.request.contextPath}/empresa-update?id=' + id;
            modal.classList.add('open');
            modal.setAttribute('aria-hidden', 'false');
        };
    })();
</script>

</body>
</html>
