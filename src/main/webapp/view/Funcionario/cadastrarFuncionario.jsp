<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.SetorDAO" %>
<%@ page import="dao.EmpresaDAO" %>
<%@ page import="model.Setor" %>
<%@ page import="model.Empresa" %>
<%
    String modalParam = request.getParameter("modal");
    boolean isModal = "1".equals(modalParam);
    // Este formulário agora SÓ funciona como modal
%>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Cadastrar Funcionário</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>

    <style>
        :root{ --cor_falgadja_1:#1800CC; --cor_falgadja_2:#0C0066; --cinza:#7f7aa7; }
        *{ box-sizing:border-box; font-family:'Poppins', system-ui, -apple-system, 'Segoe UI', Roboto, Arial; margin:0; padding:0; }
        html,body{ height:100%; width:100%; background:transparent; color:var(--cor_falgadja_1); }
        .container{
            width:100%;
            height:100%;
            display:flex;
            align-items: flex-start; /* Alinha ao topo */
            justify-content:center;
            background:transparent;
            overflow-y: auto; /* Permite rolagem */
            padding-top: 24px;
            padding-bottom: 24px;
        }
        .title{ font-size:32px; font-weight:800; color:var(--cor_falgadja_1); text-align:center; }
        .subtitle{ margin-top:-6px; color:var(--cinza); font-weight:600; font-size:14px; text-align:center; margin-bottom: 22px; }
        .form-inner{ width:100%; max-width:760px; display:flex; flex-direction:column; gap:18px; }
        label{ display:block; font-weight:600; color:var(--cor_falgadja_1); margin-bottom:8px; font-size:14px; }
        input[type="text"], input[type="email"], input[type="password"], input[type="date"], select {
            width:100%; padding:14px 18px; border-radius:12px; border:2px solid rgba(24,0,204,0.18); outline:none; font-size:15px; color:#222; background:#fff; transition:border .12s, box-shadow .12s;
        }
        input::placeholder{ color:rgba(24,0,204,0.22); }
        input:focus{ border-color:var(--cor_falgadja_1); box-shadow:0 8px 18px rgba(24,0,204,0.06); }
        .field-group{ display:block; width:100%; }
        .actions{ display:flex; justify-content:center; margin-top:6px; }
        .btn{ padding:12px 42px; border-radius:12px; background:linear-gradient(180deg,var(--cor_falgadja_1),var(--cor_falgadja_2)); color:#fff; border:0; font-weight:800; cursor:pointer; font-size:16px; box-shadow:0 10px 18px rgba(12,0,102,0.12); }
    </style>
</head>
<body style="background:transparent;">

<div class="container">
    <div class="form-inner">
        <div class="title">Adicionar</div>
        <div class="subtitle">Funcionário</div>

        <form action="${pageContext.request.contextPath}/InserirFuncionario" method="post" target="_top">

            <div class="field-group">
                <label for="nome">Nome</label>
                <input type="text" id="nome" name="nome" placeholder="Nome do funcionário" maxlength="50" required>
            </div>
            <div class="field-group">
                <label for="sobrenome">Sobrenome</label>
                <input type="text" id="sobrenome" name="sobrenome" placeholder="Sobrenome do funcionário" maxlength="50" required>
            </div>
            <div class="field-group">
                <label for="dataNascimento">Data de Nascimento</label>
                <input type="date" id="dataNascimento" name="dataNascimento" required>
            </div>
            <div class="field-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="email@exemplo.com" maxlength="80" required>
            </div>
            <div class="field-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" placeholder="Crie uma senha forte" minlength="8" maxlength="30" required>
            </div>
            <div class="field-group">
                <label for="confirmarSenha">Confirme sua senha</label>
                <input type="password" id="confirmarSenha" name="confirmarSenha" placeholder="Repita a senha" minlength="8" maxlength="30" required>
            </div>
            <div class="field-group">
                <label for="idSetor">Setor</label>
                <select id="idSetor" name="idSetor" required>
                    <option value="">Selecione o setor</option>
                    <%
                        SetorDAO setorDAO = new SetorDAO();
                        List<Setor> setores = setorDAO.listarTodos();
                        for (Setor setor : setores) {
                    %>
                    <option value="<%= setor.getId() %>"><%= setor.getNome() %></option>
                    <% } %>
                </select>
            </div>
            <div class="field-group">
                <label for="idEmpresa">Empresa</label>
                <select id="idEmpresa" name="idEmpresa" required>
                    <option value="">Selecione a empresa</option>
                    <%
                        EmpresaDAO empresaDAO = new EmpresaDAO();
                        List<Empresa> empresas = empresaDAO.listar();
                        for (Empresa empresa : empresas) {
                    %>
                    <option value="<%= empresa.getId() %>"><%= empresa.getNome() %></option>
                    <% } %>
                </select>
            </div>

            <div class="actions">
                <button type="submit" class="btn">Cadastrar</button>
            </div>
        </form>
        <%
            String mensagem = (String) request.getAttribute("mensagem");
            if (mensagem != null) {
        %>
        <p style="color: red; margin-top: 15px; text-align: center;"><%= mensagem %></p>
        <% } %>
    </div>
</div>
</body>
</html>