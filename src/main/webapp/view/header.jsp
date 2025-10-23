<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <link rel="stylesheet" href="" />
    <div class="tela">
        <aside class="sidebar">
            <div class="aplicativo">
                <div class="logo"><img src="${pageContext.request.contextPath}/imagens/contAbaco cantinho.png"
                        alt="logo" style="width:42px;height:42px;"></div>
                <div>
                    <div class="titulo_app">ContÁbaco</div>
                    <div class="subtitulo_app">adm</div>
                </div>
            </div>
            <div class="linha"></div>

            <nav class="nav">
                <a class="botao" href="${pageContext.request.contextPath}/adm/adm.jsp">Adm</a>
                <a class="botao" href="${pageContext.request.contextPath}/empresa/empresa.jsp">Empresas</a>
                <a class="botao" href="${pageContext.request.contextPath}/plano/plano.jsp">Planos</a>
                <a class="botao" href="${pageContext.request.contextPath}/pagamento/pagamento.jsp">Pagamento</a>
                <a class="botao" href="${pageContext.request.contextPath}/funcionario/funcionario.jsp">Funcionários</a>
                <a class="botao" href="${pageContext.request.contextPath}/empresa/endereco.jsp">Endereço</a>
                <a class="botao" href="${pageContext.request.contextPath}/empresa/setor.jsp">Setor</a>
            </nav>

            <div style="margin-top:auto;">
                <button class="botao-sair"
                    style="padding:12px 18px; border-radius:12px; background:var(--cor_sair); color:white; border:0; cursor:pointer; font-weight:700; width: 100%; display:inline-flex; align-items:center; gap:10px;">Sair</button>
            </div>
        </aside>

        <div class="separator"></div>

        <main class="main">
            <div class="titulos">
                <div>
                    <h1 id="AR">Área Restrita</h1>
                    <div id="CRUD">
                        <p>CRUD</p>
                    </div>
                </div>
                <div class="pesquisar">
                    <img src="${pageContext.request.contextPath}/imagens/lupa.png" alt=""
                        style="width:18px;height:18px;">
                    <form action="" method="get" style="display:flex; width:100%;">
                        <input id="main-search" name="q" type="text" placeholder="Buscar por id, nome, email..." />
                    </form>
                </div>
            </div>