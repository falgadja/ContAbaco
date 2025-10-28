<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.AdmDAO" %>
<%@ page import="model.Administrador" %>

<%
    // Suporte a exibição em modal, igual ao cadastrar
    String modalParam = request.getParameter("modal");
    boolean isModal = "1".equals(modalParam);

    // Busca do administrador
    String idStr = request.getParameter("id");
    Administrador adm = null;
    if (idStr != null && !idStr.isEmpty()) {
        try {
            int id = Integer.parseInt(idStr);
            AdmDAO dao = new AdmDAO();
            adm = dao.buscarPorId(id);
        } catch (Exception e) { /* opcional: log */ }
    }
%>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Atualizar Administrador</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

    <style>
        :root {
            --cor_falgadja_1: #1800CC;
            --cor_falgadja_2: #0C0066;
            --cinza: #7f7aa7;
            --bg-card: #fff;
        }
        * {
            box-sizing: border-box;
            font-family: 'Poppins', system-ui, -apple-system, 'Segoe UI', Roboto, Arial;
            margin: 0; padding: 0;
        }
        html, body {
            height: 100%; width: 100%;
            background: transparent;
            color: var(--cor_falgadja_1);
        }

        .container {
            width: 100%;
            min-height: 100vh;
            display: flex; align-items: center; justify-content: center;
            padding: 18px;
            background: transparent;
        }

        .form-card {
            width: 100%; max-width: 920px;
            border-radius: 18px;
            border: 2px solid rgba(24, 0, 204, 0.20);
            padding: 36px 44px;
            background: var(--bg-card);
            display: flex; flex-direction: column; align-items: center; gap: 22px;
            box-shadow: 0 10px 24px rgba(12, 0, 102, 0.06);
            position: relative;
        }
        .title {
            font-size: 32px; font-weight: 800; color: var(--cor_falgadja_1); text-align: center;
        }
        .subtitle {
            margin-top: -6px; color: var(--cinza); font-weight: 600; font-size: 14px; text-align: center;
        }

        .form-inner { width: 100%; max-width: 760px; display: flex; flex-direction: column; gap: 18px; }

        /* ✅ REGRA ADICIONADA AQUI ✅ */
        /* Isso adiciona espaço entre os .field-group e o .actions */
        .form-inner form {
            display: flex;
            flex-direction: column;
            gap: 18px;
        }

        label { display: block; font-weight: 600; color: var(--cor_falgadja_1); margin-bottom: 8px; font-size: 14px; }
        input[type="text"], input[type="email"], input[type="password"] {
            width: 100%; padding: 14px 18px; border-radius: 12px;
            border: 2px solid rgba(24, 0, 204, 0.18);
            outline: none; font-size: 15px; color: #222; background: #fff;
            transition: border .12s, box-shadow .12s;
        }
        input::placeholder { color: rgba(24, 0, 204, 0.22); }
        input:focus { border-color: var(--cor_falgadja_1); box-shadow: 0 8px 18px rgba(24, 0, 204, 0.06); }
        input:disabled { background: #f5f6ff; color: #666; }
        .field-group { display: block; width: 100%; }
        .actions {
            display: flex;
            justify-content: center;
            gap: 12px; /* <-- Isso separa os botões */
            margin-top: 6px;
            flex-wrap: wrap;
        }
        .btn {
            padding: 12px 42px; border-radius: 12px;
            background: linear-gradient(180deg, var(--cor_falgadja_1), var(--cor_falgadja_2));
            color: #fff; border: 0; font-weight: 800; cursor: pointer; font-size: 16px;
            box-shadow: 0 10px 18px rgba(12, 0, 102, 0.12);
        }
        .btn-sec {
            padding: 12px 18px; border-radius: 12px; background: #fff;
            color: var(--cor_falgadja_1); border: 2px solid rgba(24,0,204,0.18); font-weight: 800; cursor: pointer;
        }

        .alert {
            background:#f0f4ff; border:1px solid #c8d3ff; color:#243; padding:10px 14px; border-radius:10px; font-weight:600;
            width: 100%;
        }
        @media (max-width:900px) {
            .form-card { padding: 22px; max-width: 92%; }
            .form-inner { max-width: 100%; }
            .title { font-size: 24px; }
        }
    </style>
</head>
<body<%= isModal ? " style='background:transparent;'" : "" %>>

    <% if (isModal) { %>
<div class="container" style="height:100%; padding:0;">
    <% if (adm == null) { %>
    <div class="form-inner" role="alert">
        <div class="title">Administrador não encontrado</div>
        <div class="actions">
            <a class="btn-sec" href="<%= request.getContextPath() %>/view/Adm/crudAdm.jsp" target="_top">Voltar</a>
        </div>
    </div>
    <% } else { %>
    <div class="form-inner" role="dialog" aria-modal="true">
        <div class="title">Atualizar</div>
        <div class="subtitle">Administrador</div>

        <%
            String mensagem = (String) request.getAttribute("mensagemAtualizar");
            if (mensagem != null) {
        %>
        <div class="alert"><%= mensagem %></div>
        <% } %>

        <form action="<%= request.getContextPath() %>/AtualizarAdmServlet" method="post" target="_top">
            <div class="field-group">
                <label for="id_visual">ID:</label>
                <input type="text" id="id_visual" value="<%= adm.getId() %>" disabled>
                <input type="hidden" id="id" name="id" value="<%= adm.getId() %>">
            </div>

            <div class="field-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<%= adm.getEmail() %>" required>
            </div>

            <div class="field-group">
                <label for="senha">Senha (deixe vazio para não alterar):</label>
                <input type="password" id="senha" name="senha" placeholder="">
            </div>

            <div class="actions">
                <button type="submit" class="btn">Salvar alterações</button>
                <button type="button" class="btn-sec"
                        onclick="try{ parent.document.getElementById('modalClose').click(); }catch(e){ window.close(); }">
                    Cancelar
                </button>
            </div>
        </form>
    </div>
    <% } %>
</div>

    <% } else { %>
<div class="container">
    <% if (adm == null) { %>
    <div class="form-card" role="alert">
        <div class="title">Administrador não encontrado</div>
        <div class="actions">
            <a class="btn-sec" href="<%= request.getContextPath() %>/view/Adm/crudAdm.jsp">Voltar</a>
        </div>
    </div>
    <% } else { %>
    <div class="form-card" role="dialog" aria-modal="true">
        <div class="title">Atualizar</div>
        <div class="subtitle">Administrador</div>

        <%
            String mensagem = (String) request.getAttribute("mensagemAtualizar");
            if (mensagem != null) {
        %>
        <div class="alert"><%= mensagem %></div>
        <% } %>

        <div class="form-inner">
            <form action="<%= request.getContextPath() %>/AtualizarAdmServlet" method="post">
                <div class="field-group">
                    <label for="id_visual">ID:</label>
                    <input type="text" id="id_visual" value="<%= adm.getId() %>" disabled>
                    <input type="hidden" id="id" name="id" value="<%= adm.getId() %>">
                </div>

                <div class="field-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="<%= adm.getEmail() %>" required>
                </div>

                <div class="field-group">
                    <label for="senha">Senha (deixe vazio para não alterar):</label>
                    <input type="password" id="senha" name="senha" placeholder="">
                </div>

                <div class="actions">
                    <button type="submit" class="btn">Salvar alterações</button>
                    <a class="btn-sec" href="<%= request.getContextPath() %>/view/Adm/crudAdm.jsp">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
    <% } %>
</div>
<% } %>