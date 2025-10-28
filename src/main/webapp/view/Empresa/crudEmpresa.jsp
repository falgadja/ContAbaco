<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Empresa" %>
<%@ page import="dao.EmpresaDAO" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Empresas - Área Restrita</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png">

    <!-- fontes / ícones -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>

    <style>
        /* ======== ESTILIZAÇÃO DO ENTORNO (EXATAMENTE COMO VOCÊ ENVIOU) ======== */
        :root { --cor_falgadja_1:#1800CC; --cor_falgadja_2:#0C0066; --cor_sair:#b71c1c; --branco:#ffffff; }
        *{ box-sizing:border-box; margin:0; padding:0; font-family:'Poppins',system-ui,-apple-system,'Segoe UI',Roboto,Arial; }
        html,body{ height:100%; width:100%; background:var(--branco); color:var(--cor_falgadja_1); }

        .esquerda{ display:flex; height:100vh; width:100%; overflow:hidden; }
        .sidebar{ width:255px; padding:28px 20px; display:flex; flex-direction:column; gap:18px; }
        .aplicativo{ display:flex; gap:15px; align-items:center; }
        .logo{ width:56px; height:56px; border-radius:10px; border:2px solid var(--cor_falgadja_1); display:flex; align-items:center; justify-content:center; overflow:hidden; }
        .logo img{ max-width:100%; max-height:100%; display:block; }
        .titulo_app{ font-weight:700; color:var(--cor_falgadja_1); font-size:20px; }
        .subtitulo_app{ font-size:12px; color:grey; margin-top:2px; }

        .linha{ height:6px; border-radius:30px; background:linear-gradient(90deg,var(--cor_falgadja_1),var(--cor_falgadja_2)); width:80%; margin:6px 0; }

        .nav{ display:flex; flex-direction:column; gap:12px; margin-top:10px; }
        .botao{ display:flex; align-items:center; gap:12px; padding:12px 18px; border-radius:10px; border:2px solid var(--cor_falgadja_1); background:transparent; color:var(--cor_falgadja_1); font-weight:600; width:190px; cursor:pointer; text-decoration:none; }
        .botao.selecionado{ background:linear-gradient(180deg,var(--cor_falgadja_1),var(--cor_falgadja_2)); color:#fff; border-color:transparent; box-shadow:0 6px 18px rgba(12,0,102,0.25); }

        .separator{ width:4px; background:var(--cor_falgadja_1); }

        .main{ flex:1; padding:36px 48px; overflow:auto; }

        .titulos{ display:flex; align-items:center; justify-content:space-between; gap:12px; }
        #AR{ font-size:30px; font-weight:800; color:var(--cor_falgadja_1); }
        #CRUD{ color:grey; font-weight:600; margin-top:-10px; }

        .pesquisar{ width:480px; height:44px; border-radius:10px; border:2px solid var(--cor_falgadja_1); display:flex; align-items:center; padding:8px 12px; gap:10px; }
        .pesquisar input{ border:0; outline:0; flex:1; font-size:14px; color:var(--cor_falgadja_1); }
        .pesquisar input::placeholder{ color: rgba(24,0,204,0.45); }

        .adicionador{ margin-top:28px; }
        .botao-add{ padding:12px 18px; border-radius:12px; background:linear-gradient(180deg,var(--cor_falgadja_1),var(--cor_falgadja_2)); color:#fff; font-weight:700; border:0; cursor:pointer; display:inline-flex; align-items:center; gap:10px; text-decoration:none; }

        .sair{ margin-top:auto;}
        .botao-sair{padding:12px 18px; border-radius:12px; background:var(--cor_sair); color:white; border:0; cursor:pointer; font-weight:700; width:100%; display:inline-flex; align-items:center; gap:10px;}

        /* ======== ESTILO DA TABELA (mantido) ======== */
        .tabela {
            margin-top:20px;
            border:3px solid rgba(24,0,204,0.95);
            border-radius:14px;
            padding:10px;
            width: calc(100% - 32px);
            max-width:1180px;
            margin-left:auto; margin-right:auto;
            box-shadow:0 10px 22px rgba(12,0,102,0.08);
            position:relative;
            background:#fff;
        }

        .tabela-container {
            width:100%;
            max-height:calc(100vh - 260px);
            overflow-y:auto;
            overflow-x:auto;
            padding:10px;
        }
        .tabela-container::-webkit-scrollbar{ height:10px; width:10px; }
        .tabela-container::-webkit-scrollbar-thumb{ background: linear-gradient(180deg, rgba(24,0,204,0.2), rgba(12,0,102,0.2)); border-radius:10px; }

        .empresa-style table { width:auto; table-layout:auto; border-collapse:collapse; font-size:14px; white-space:nowrap; }
        .empresa-style thead th, .empresa-style tbody td {
            padding:14px 16px; overflow:visible; text-overflow:clip; white-space:nowrap;
            font-weight:600; text-align:center;
        }
        .empresa-style tbody tr:nth-child(even){ background:#fafaff; }
        .empresa-style tbody tr:hover{ background:rgba(24,0,204,0.04); }

        .empresa-style th.acoes-col, .empresa-style td.acoes {
            position:sticky; right:0; background:#fff; box-shadow:-8px 0 12px rgba(12,0,102,0.06);
            min-width:170px; padding:0 8px; vertical-align:middle; text-align:center; overflow:visible; white-space:nowrap;
        }

        .btn{ display:inline-flex; align-items:center; justify-content:center; width:36px; height:36px; border-radius:8px; border:1.4px solid rgba(24,0,204,0.9); background:#fff; cursor:pointer; transition:.15s; }
        .btn:hover{ transform:translateY(-2px); box-shadow:0 6px 12px rgba(12,0,102,0.06); }
    </style>
</head>
<body>
<div class="esquerda">
    <!-- Sidebar -->
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
            <a href="<%= request.getContextPath() %>/view/Adm/crudAdm.jsp" class="botao"><i class="fa-solid fa-crown"></i> Adm</a>
            <a href="<%= request.getContextPath() %>/view/Empresa/crudEmpresa.jsp" class="botao selecionado"><i class="fa-solid fa-building"></i> Empresas</a>
            <a href="<%= request.getContextPath() %>/view/Funcionario/crudFuncionario.jsp" class="botao"><i class="fa-solid fa-user-tie"></i> Funcionários</a>
            <a href="<%= request.getContextPath() %>/view/Plano/crudPlano.jsp" class="botao"><i class="fa-solid fa-clipboard-list"></i> Planos</a>
            <a href="<%= request.getContextPath() %>/view/Pagamento/crudPagamento.jsp" class="botao"><i class="fa-solid fa-credit-card"></i> Pagamento</a>
        </div>

        <div class="sair">
            <button class="botao-sair" onclick="location.href='${pageContext.request.contextPath}/LogoutServlet'">
                <i class="fa-solid fa-right-from-bracket"></i> Sair
            </button>
        </div>
    </div>

    <div class="separator"></div>

    <!-- Main -->
    <div class="main">
        <div class="titulos">
            <div>
                <div id="AR">Área Restrita</div>
                <div id="CRUD">CRUD</div>
            </div>
            <div class="pesquisar">
                <i class="fa fa-search"></i>
                <input type="text" placeholder="Buscar por id, nome, email...">
            </div>
        </div>

        <div class="adicionador">
            <a class="botao-add" href="<%= request.getContextPath() %>/view/Empresa/cadastrarEmpresa.jsp">
                <i class="fa-solid fa-plus"></i> Adicionar Novo
            </a>
        </div>

        <!-- Tabela de Empresas -->
        <div class="tabela empresa-style">
            <div class="tabela-container">
                <%
                    EmpresaDAO dao = new EmpresaDAO();
                    List<Empresa> lista = dao.listar();
                    if (lista != null && !lista.isEmpty()) {
                %>
                <table>
                    <thead>
                    <tr>
                        <th>id_empresa</th>
                        <th>nome</th>
                        <th>cnpj</th>
                        <th>email</th>
                        <th>qtd_funcionários</th>
                        <th>plano</th>
                        <th class="acoes-col">Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        for (Empresa e : lista) {
                            String plano;
                            switch(e.getIdPlano()) {
                                case 1: plano = "Mensal"; break;
                                case 2: plano = "Anual"; break;
                                case 3: plano = "Premium"; break;
                                default: plano = "Teste";
                            }
                    %>
                    <tr>
                        <td><%= e.getId() %></td>
                        <td><%= e.getNome() %></td>
                        <td><%= e.getCnpj() %></td>
                        <td><%= e.getEmail() %></td>
                        <td><%= e.getQntdFuncionarios() %></td>
                        <td><%= plano %></td>
                        <td class="acoes">
                            <button class="btn" title="Visualizar"
                                    onclick="window.location.href='<%= request.getContextPath() %>/view/Empresa/detalhesEmpresa.jsp?id=<%= e.getId() %>'">
                                <i class="fa-regular fa-eye"></i>
                            </button>
                            <button class="btn" title="Editar"
                                    onclick="window.location.href='<%= request.getContextPath() %>/view/Empresa/atualizarEmpresa.jsp?id=<%= e.getId() %>'">
                                <i class="fa-solid fa-pen"></i>
                            </button>
                            <button class="btn" title="Excluir"
                                    onclick="if(confirm('Deseja excluir esta empresa?')) window.location.href='<%= request.getContextPath() %>/DeletarEmpresaServlet?id=<%= e.getId() %>'">
                                <i class="fa-solid fa-trash"></i>
                            </button>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <%
                } else {
                %>
                <p style="padding:10px 6px; color:#666;">Nenhuma empresa cadastrada no momento.</p>
                <% } %>
            </div>
        </div>
    </div>
</div>
</body>
</html>