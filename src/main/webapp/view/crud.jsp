<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Área Restrita - CRUD</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png">
    <style>
        /* ===== RESET ===== */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

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

        .logo {
            text-align: center;
            margin-bottom: 40px;
        }

        .logo h2 {
            font-size: 1.3rem;
            color: #140066;
        }

        .logo small {
            color: #777;
        }

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
            gap: 10px;
            transition: 0.2s;
        }

        .menu a.active {
            background-color: #140066;
            color: white;
        }

        .menu a:hover {
            background-color: #140066;
            color: #fff;
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

        .logout:hover {
            background-color: #a30000;
        }

        /* ===== CONTEÚDO ===== */
        .content {
            flex: 1;
            padding: 40px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .header h1 {
            color: #140066;
            font-size: 1.7rem;
        }

        .header p {
            font-size: 0.9rem;
            color: #555;
        }

        .search-box {
            display: flex;
            align-items: center;
            border: 1.5px solid #140066;
            border-radius: 8px;
            padding: 8px 12px;
            width: 280px;
        }

        .search-box input {
            border: none;
            outline: none;
            flex: 1;
            font-size: 0.9rem;
        }

        .btn-add {
            display: inline-block;
            background-color: #140066;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 10px;
            cursor: pointer;
            font-weight: 500;
            transition: 0.3s;
        }

        .btn-add:hover {
            background-color: #0a0044;
        }

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

        td {
            font-size: 0.95rem;
            color: #333;
        }

        tr:hover {
            background-color: #f0f0ff;
        }

        .acoes {
            display: flex;
            gap: 10px;
        }

        .acoes button {
            background: none;
            border: 1.5px solid #140066;
            border-radius: 6px;
            padding: 6px;
            cursor: pointer;
            transition: 0.2s;
        }

        .acoes button:hover {
            background-color: #140066;
            color: white;
        }

        .acoes i {
            font-size: 1rem;
        }

        /* ===== ÍCONES ===== */
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
        <a href="#">Adm</a>
        <a href="#" class="active">Empresas</a>
        <a href="#">Funcionários</a>
        <a href="#">Planos</a>
        <a href="#">Pagamento</a>
    </div>

    <button class="logout">Sair</button>
</div>

<div class="content">
    <div class="header">
        <div>
            <h1>Área Restrita</h1>
            <p>CRUD</p>
        </div>
        <div class="search-box">
            <i class="fa fa-search"></i>
            <input type="text" placeholder="Buscar por id, nome, email...">
        </div>
    </div>

    <button class="btn-add"><i class="fa fa-plus"></i> Adicionar Novo</button>

    <table>
        <thead>
        <tr>
            <th>id_empresa</th>
            <th>nome</th>
            <th>cnpj</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>1</td>
            <td>JBS</td>
            <td>02.916.265/0001-60</td>
            <td class="acoes">
                <button><i class="fa fa-eye"></i></button>
                <button><i class="fa fa-pen"></i></button>
                <button><i class="fa fa-trash"></i></button>
            </td>
        </tr>
        <tr>
            <td>2</td>
            <td>Faz Galinha Industrial</td>
            <td>00.000.000/0000-00</td>
            <td class="acoes">
                <button><i class="fa fa-eye"></i></button>
                <button><i class="fa fa-pen"></i></button>
                <button><i class="fa fa-trash"></i></button>
            </td>
        </tr>
        </tbody>
    </table>
</div>

</body>
</html>
