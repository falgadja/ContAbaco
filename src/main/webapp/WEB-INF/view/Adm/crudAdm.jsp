<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <!DOCTYPE html>
    <html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <title>Área Restrita - Administradores</title>

        <link rel="icon" href="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/crud.css">
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
                <a href="${pageContext.request.contextPath}/adm" class="botao selecionado"><img src="${pageContext.request.contextPath}/img/icone-adm-branco.png" alt="" style="width: 21px; height: 22px;"> Adm</a>

                <a href="${pageContext.request.contextPath}/empresas" class="botao"><img src="${pageContext.request.contextPath}/img/icone-empresa-azul.png" alt="" style="width: 20px; height: 18px;"> Empresas</a>

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
                    <div id="CRUD">CRUD</div>
                </div>
            </div>

            <div class="adicionador">
                <button id="openModal" class="botao-add" type="button"><img src="${pageContext.request.contextPath}/img/icone-adicionar.png" alt="" style="width: 20px; height: 20px;"> Adicionar Novo</button>
            </div>

            <form action="${pageContext.request.contextPath}/adm" method="get" class="filtros">
                <label for="email">Buscar por Email:</label>
                <input type="text" name="email" id="email" placeholder="Digite o email do administrador">

                <label for="tipoOrdenacao">Ordenar por:</label>
                <select name="tipoOrdenacao" id="tipoOrdenacao">
                    <option value="">-- Nenhum --</option>
                    <option value="idCrescente">ID Crescente</option>
                    <option value="idDecrescente">ID Decrescente</option>
                    <option value="Az">Email (A-Z)</option>
                    <option value="Za">Email (Z-A)</option>
                </select>

                <button type="submit">Filtrar</button>
            </form>

            <c:if test="${not empty mensagem}">
                <p class="mensagem">${mensagem}</p>
            </c:if>

            <div class="tabela tabela-adm adm-style" id="tabelaContainer">
                <div class="tabela-container">

                    <c:choose>
                        <c:when test="${not empty adms}">
                            <table>
                                <thead>
                                <tr>
                                    <th>id_adm</th>
                                    <th>email</th>
                                    <th>senha</th> <th class="acoes-col">ações</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="a" items="${adms}">
                                    <tr>
                                        <td>${a.id}</td>
                                        <td>${a.email}</td>
                                        <td>${a.senha}</td> <td class="acoes">
                                        <button class="btn" title="Editar" onclick="abrirModalEditar(${a.id})">
                                            <i class="fa-solid fa-pen"></i>
                                        </button>

                                        <form action="${pageContext.request.contextPath}/adm-delete" method="post" style="display: inline;" onsubmit="return confirm('Deseja realmente excluir este administrador?');">
                                            <input type="hidden" name="id" value="${a.id}">
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
                            <p style="padding:10px 6px; color:#666;">Nenhum administrador cadastrado (ou encontrado no filtro).</p>
                        </c:otherwise>
                    </c:choose>
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
        (function () {
            const modal = document.getElementById('inTableModal');
            const frame = document.getElementById('modalFrame');
            const openBtn = document.getElementById('openModal');
            const closeBtn = document.getElementById('modalClose');

            if (!modal || !frame || !openBtn || !closeBtn) {
                console.error('Elementos do modal não encontrados.');
                return;
            }

            // CORRIGIDO: Adicionado "?modal=1"
            openBtn.addEventListener('click', function () {
                frame.src = '${pageContext.request.contextPath}/adm-create?modal=1';
                modal.classList.add('open');
                modal.setAttribute('aria-hidden', 'false');
            });

            function closeModal() {
                modal.classList.remove('open');
                modal.setAttribute('aria-hidden', 'true');
                frame.src = 'about:blank';

                // Esta linha é o que atualiza a tabela QUANDO VOCÊ FECHA.
                // Mas agora, o formulário de "Cadastrar" vai fazer isso sozinho.
                window.location.reload();
            }

            closeBtn.addEventListener('click', closeModal);

            document.addEventListener('keydown', function (e) {
                if (e.key === 'Escape' && modal.classList.contains('open')) {
                    closeModal();
                }
            });

            // CORRIGIDO: Adicionado "&modal=1"
            window.abrirModalEditar = function (id) {
                if (!id) return;
                frame.src = '${pageContext.request.contextPath}/adm-update?id=' + id + '&modal=1';
                modal.classList.add('open');
                modal.setAttribute('aria-hidden', 'false');
            };

        })();
    </script>

    </body>
    </html>