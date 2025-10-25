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
    <style>
        /* ===== RESET ===== */
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Poppins', sans-serif; }

        body {
            display: flex;
            background-color: #ffffff;
            color: #140066;
            height: 100vh;
        }

        /* ===== SIDEBAR ===== */
        .sidebar {
            width: 250px;
            background-color: #ffffff;
            border-right: 2px solid #140066;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 25px 0;
        }

        .logo { text-align: center; margin-bottom: 40px; }
        .logo h2 { font-size: 1.3rem; color: #140066; }
        .logo small { color: #777; }

        .menu {
            width: 100%;
            display: flex;
            flex-direction: column;
            gap: 10px;
            padding: 0 20px;
        }

        .menu a {
            text-decoration: none;
            color: #140066;
            border: 1.5px solid #140066;
            border-radius: 8px;
            padding: 10px;
            text-align: left;
            font-weight: 500;
            display: flex;
            align-items: center;
            transition: 0.2s;
        }

        .menu a.active, .menu a:hover {
            background-color: #140066;
            color: white;
        }

        .logout {
            margin-top: auto;
            background-color: #d60000;
            color: white;
            border: none;
            border-radius: 6px;
            padding: 12px 30px;
            cursor: pointer;
            font-weight: 600;
            transition: 0.3s;
        }

        .logout:hover { background-color: #a30000; }

        /* ===== CONTEÚDO ===== */
        .content { flex: 1; padding: 40px; }
        .header {
            display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px;
        }
        .header h1 { color: #140066; font-size: 1.7rem; }

        .search-box {
            display: flex; align-items: center;
            border: 1.5px solid #140066;
            border-radius: 8px;
            padding: 8px 12px;
            width: 280px;
        }
        .search-box input {
            border: none; outline: none; flex: 1; font-size: 0.9rem;
        }

        .btn-add {
            background-color: #140066;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 10px;
            cursor: pointer;
            font-weight: 500;
            transition: 0.3s;
        }
        .btn-add:hover { background-color: #0a0044; }

        /* ===== TABELA ===== */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            border: 1.5px solid #140066;
            border-radius: 12px;
            overflow: hidden;
        }

        th, td {
            text-align: left;
            padding: 14px 16px;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f8f8ff;
            color: #140066;
            text-transform: uppercase;
            font-size: 0.85rem;
        }
        td { font-size: 0.95rem; color: #333; }
        tr:hover { background-color: #f0f0ff; }

        .acoes { display: flex; gap: 10px; }
        .acoes button {
            background: none;
            border: 1.5px solid #140066;
            border-radius: 6px;
            padding: 6px;
            cursor: pointer;
            transition: 0.2s;
        }
        .acoes button:hover { background-color: #140066; color: white; }

        @import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css');
    </style>
</head>
<body>

<div class="sidebar">
    <div class="logo">
        <h2>ContÁbaco</h2>
        <small>adm</small>
    </div>

    <div class="menu">
        <a href="<%= request.getContextPath() %>/view/Adm/crudAdm.jsp">Adm</a>
        <a href="<%= request.getContextPath() %>/view/Empresa/crudEmpresa.jsp" class="active">Empresas</a>
        <a href="<%= request.getContextPath() %>/view/Funcionario/crudFuncionario.jsp">Funcionários</a>
        <a href="<%= request.getContextPath() %>/view/Plano/crudPlano.jsp">Planos</a>
        <a href="<%= request.getContextPath() %>/view/Pagamento/crudPagamento.jsp">Pagamentos</a>
    </div>

    <button class="logout">Sair</button>
</div>

<div class="content">
    <div class="header">
        <div>
            <h1>Empresas Cadastradas</h1>
            <p>Visualize, edite ou exclua empresas registradas no sistema.</p>
        </div>
        <a href="<%= request.getContextPath() %>/view/Empresa/cadastrarEmpresa.jsp" class="btn-add">+ Adicionar Empresa</a>
    </div>

    <!-- ===== TABELA DINÂMICA ===== -->
    <%
        EmpresaDAO dao = new EmpresaDAO();
        List<Empresa> lista = dao.listar();

        if (lista != null && !lista.isEmpty()) {
    %>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>CNPJ</th>
            <th>Email</th>
            <th>Telefone</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <% for (Empresa e : lista) { %>
        <tr>
            <td><%= e.getId() %></td>
            <td><%= e.getNome() %></td>
            <td><%= e.getCnpj() %></td>
            <td><%= e.getEmail() %></td>
            <td><%= e.getQntdFuncionarios() %></td>
            <!-- ===== Java para pegar o nome ===== -->
            <td><%= e.getCnpj() %></td>
            <td><%= e.getEmail() %></td>
            <td><%= e.getQntdFuncionarios() %></td>

            <%
                String plano;
                if (e.getIdPlano() == 1) {
                    plano = "Plano Mensal";
                } else if (e.getIdPlano() == 2) {
                    plano = "Plano Anual";
                } else if (e.getIdPlano() == 3) {
                    plano = "Plano Premium";
                } else {
                    plano = "Plano Teste";
                }
            %>
            <td><%= plano %></td>


            <
            <td class="acoes">
                <form action="<%= request.getContextPath() %>/editarEmpresa" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%= e.getId() %>">
                    <button title="Editar"><i class="fa fa-pen"></i></button>
                </form>
                <form action="<%= request.getContextPath() %>/excluirEmpresa" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= e.getId() %>">
                    <button title="Excluir" onclick="return confirm('Tem certeza que deseja excluir esta empresa?');"><i class="fa fa-trash"></i></button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <%
    } else {
    %>
    <p>Nenhuma empresa cadastrada no momento.</p>
    <%
        }
    %>
</div>

</body>
</html>
