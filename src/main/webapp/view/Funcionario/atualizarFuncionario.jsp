<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.FuncionarioDAO" %>
<%@ page import="model.Funcionario" %>
<%@ page import="dao.SetorDAO" %>
<%@ page import="dao.EmpresaDAO" %>
<%@ page import="model.Setor" %>
<%@ page import="model.Empresa" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    // Suporte a modal
    String modalParam = request.getParameter("modal");
    boolean isModal = "1".equals(modalParam);

    // Busca do Funcionário
    String idStr = request.getParameter("id");
    Funcionario funcionario = null;
    if (idStr == null || idStr.isEmpty()) {
        // Lógica para lidar com ID nulo, se necessário
    } else {
        int id = Integer.parseInt(idStr);
        FuncionarioDAO dao = new FuncionarioDAO();
        funcionario = dao.buscarPorId(id);
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
%>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Atualizar Funcionário</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

    <style>
        /* Estilos do formulário modal */
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
        .form-inner form {
            display: flex;
            flex-direction: column;
            gap: 18px;
        }
        label { display: block; font-weight: 600; color: var(--cor_falgadja_1); margin-bottom: 8px; font-size: 14px; }
        input[type="text"], input[type="email"], input[type="password"], input[type="date"], select {
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
            gap: 12px;
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
    </style>
</head>
<body<%= isModal ? " style='background:transparent;'" : "" %>>

<% if (isModal) { %>
<div class="container" style="height:100%; padding: 24px 0; align-items: flex-start; overflow-y: auto;">
    <% if (funcionario == null) { %>
    <div class="form-inner" role="alert">
        <div class="title">Funcionário não encontrado</div>
        <div class="actions">
            <a class="btn-sec" href="<%= request.getContextPath() %>/view/Funcionario/crudFuncionario.jsp" target="_top">Voltar</a>
        </div>
    </div>
    <% } else { %>
    <div class="form-inner" role="dialog" aria-modal="true">
        <div class="title">Atualizar</div>
        <div class="subtitle">Funcionário</div>

        <form action="<%= request.getContextPath() %>/AtualizarFuncionarioServlet" method="post" target="_top">
            <input type="hidden" name="id" value="<%= funcionario.getId() %>"/>

            <div class="field-group">
                <label>Nome:</label>
                <input type="text" name="nome" value="<%= funcionario.getNome() %>" required/>
            </div>
            <div class="field-group">
                <label>Sobrenome:</label>
                <input type="text" name="sobrenome" value="<%= funcionario.getSobrenome() %>" required/>
            </div>
            <div class="field-group">
                <label>Data de Nascimento:</label>
                <input type="date" name="dataNascimento" value="<%= funcionario.getDataNascimento().format(formatter) %>" required/>
            </div>
            <div class="field-group">
                <label>Email:</label>
                <input type="email" name="email" value="<%= funcionario.getEmail() %>" required/>
            </div>
            <div class="field-group">
                <label>Senha (deixe em branco para não alterar):</label>
                <input type="password" name="senha" placeholder="Não alterar"/>
            </div>
            <div class="field-group">
                <label for="idSetor">Setor</label>
                <select id="idSetor" name="idSetor" required>
                    <option value="">Selecione o setor</option>
                    <%
                        SetorDAO setorDAO = new SetorDAO();
                        List<Setor> setores = setorDAO.listarTodos();
                        for (Setor setor : setores) {
                            String selected = (setor.getId() == funcionario.getIdSetor()) ? "selected" : "";
                    %>
                    <option value="<%= setor.getId() %>" <%= selected %>><%= setor.getNome() %></option>
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
                            String selected = (empresa.getId() == funcionario.getIdEmpresa()) ? "selected" : "";
                    %>
                    <option value="<%= empresa.getId() %>" <%= selected %>><%= empresa.getNome() %></option>
                    <% } %>
                </select>
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
    <div class="form-card" role="dialog" aria-modal="true">
        <% if (funcionario == null) { %>
        <div class="title">Funcionário não encontrado</div>
        <div class="actions">
            <a class="btn-sec" href="crudFuncionario.jsp">Voltar</a>
        </div>
        <% } else { %>
        <div class="title">Atualizar</div>
        <div class="subtitle">Funcionário</div>
        <div class="form-inner">
            <form action="<%= request.getContextPath() %>/AtualizarFuncionarioServlet" method="post">
                <input type="hidden" name="id" value="<%= funcionario.getId() %>"/>

                <div class="field-group">
                    <label>Nome:</label>
                    <input type="text" name="nome" value="<%= funcionario.getNome() %>" required/>
                </div>
                <div class="field-group">
                    <label>Sobrenome:</label>
                    <input type="text" name="sobrenome" value="<%= funcionario.getSobrenome() %>" required/>
                </div>
                <div class="field-group">
                    <label>Data de Nascimento:</label>
                    <input type="date" name="dataNascimento" value="<%= funcionario.getDataNascimento().format(formatter) %>" required/>
                </div>
                <div class="field-group">
                    <label>Email:</label>
                    <input type="email" name="email" value="<%= funcionario.getEmail() %>" required/>
                </div>
                <div class="field-group">
                    <label>Senha (deixe em branco para não alterar):</label>
                    <input type="password" name="senha" placeholder="Não alterar"/>
                </div>
                <div class="field-group">
                    <label for="idSetor">Setor</label>
                    <select id="idSetor" name="idSetor" required>
                        <option value="">Selecione o setor</option>
                        <%
                            SetorDAO setorDAO = new SetorDAO();
                            List<Setor> setores = setorDAO.listarTodos();
                            for (Setor setor : setores) {
                                String selected = (setor.getId() == funcionario.getIdSetor()) ? "selected" : "";
                        %>
                        <option value="<%= setor.getId() %>" <%= selected %>><%= setor.getNome() %></option>
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
                                String selected = (empresa.getId() == funcionario.getIdEmpresa()) ? "selected" : "";
                        %>
                        <option value="<%= empresa.getId() %>" <%= selected %>><%= empresa.getNome() %></option>
                        <% } %>
                    </select>
                </div>

                <div class="actions">
                    <button type="submit" class="btn">Salvar alterações</button>
                    <a class="btn-sec" href="crudFuncionario.jsp">Cancelar</a>
                </div>
            </form>
        </div>
        <% } %>
    </div>
</div>
<% } %>
</body>
</html>