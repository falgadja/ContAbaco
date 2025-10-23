<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 16/10/2025
  Time: 19:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página de Cadastro e Login para empresas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        /* --- Reset Básico e Estilos do Corpo --- */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Poppins', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background: linear-gradient(to right, #304FFE, #0c1a4b);
            padding: 40px;
        }

        /* --- Container Principal --- */
        .main-container {
            display: flex;
            width: 100%;
            max-width: 1290px;
            height: auto;
            min-height: 800px;
            border-radius: 50px;
            background-color: #ffffff;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            position: relative;
            flex-direction: column;
        }

        @media (min-width: 768px) {
            .main-container {
                flex-direction: row;
                height: 600px;
            }
        }

        /* --- Linha Divisória Vertical --- */
        .main-container::after {
            content: '';
            position: absolute;
            left: 40%;
            top: 5%;
            bottom: 5%;
            width: 4px;
            background-color: #304FFE;
            transform: translateX(-50%);
            display: none;
        }

        @media (min-width: 768px) {
            .main-container::after {
                display: block;
            }
        }

        /* --- Estilos Gerais dos Painéis --- */
        .panel {
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 40px;
            width: 100%;
        }

        .panel .content {
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
            width: 100%;
            max-width: 320px;
        }

        /* --- Painel Esquerdo (Login) --- */
        .left-panel {
            background-color: #ffffff;
            order: 2;
        }
        @media (min-width: 768px) {
            .left-panel {
                flex: 2;
                order: 1;
            }
        }

        .logo {
            width: 90px;
            margin-bottom: 30px;
        }

        /* --- CORREÇÃO DE PROPORÇÃO APLICADA AQUI --- */
        .left-panel h1 {
            /* Mínimo de 1.8rem, ideal de 5vw, máximo de 2.2rem */
            font-size: clamp(1.8rem, 5vw, 2.2rem);
            font-weight: 700;
            margin-bottom: 15px;
            color: #304FFE;
        }

        .left-panel p {
            /* Mínimo de 0.9rem, ideal de 2.5vw, máximo de 1rem */
            font-size: clamp(0.9rem, 2.5vw, 1rem);
            margin-bottom: 30px;
            color: #304FFE;
        }

        .left-panel button{
            width: 300px;
        }
        /* --- FIM DA CORREÇÃO --- */


        /* --- Painel Direito (Cadastro) --- */
        .right-panel {
            background-color: #ffffff;
            color: #333;
            padding: 40px 20px;
            position: relative;
            order: 1;
        }

        @media (min-width: 768px) {
            .right-panel {
                flex: 3;
                padding: 40px 60px;
                order: 2;
            }
        }

        .icon-abaco {
            position: absolute;
            top: 360px;
            left: 80px;
            width: 55px;
            display: none;
        }

        @media (min-width: 768px) {
            .icon-abaco {
                display: block;
            }
        }

        /* --- CORREÇÃO DE PROPORÇÃO APLICADA AQUI --- */
        .right-panel h1 {
            /* Mínimo de 1.6rem, ideal de 4vw, máximo de 1.8rem */
            font-size: clamp(1.6rem, 4vw, 1.8rem);
            font-weight: 600;
            color: #304FFE;
            margin-bottom: 25px;
        }
        /* --- FIM DA CORREÇÃO --- */

        /* --- Formulário --- */
        form {
            width: 100%;
        }

        .form-group {
            width: 100%;
            margin-bottom: 20px;
            text-align: left;
        }

        .form-group label {
            display: block;
            font-size: 0.9rem;
            color: rgb(20, 44, 201);
            margin-bottom: 8px;
        }

        .form-group input {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #304FFE;
            border-radius: 12px;
            font-size: 1rem;
            font-family: 'Poppins', sans-serif;
            outline: none;
            background-color: #ffffff;
            color: #304FFE;
        }

        .form-group input::placeholder {
            color: #b0b0e0;
            opacity: 0.8;
        }

        /* --- Botões --- */
        .btn {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 12px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-align: center;
        }

        .btn-outline {
            background-color: #ffffff;
            color: #304FFE;
            border: 2px solid #304FFE;
        }

        .btn-outline:hover {
            background-color: #f0f0f0;
            border-color: #0019CB;
        }

        .btn-solid {
            background-color: #304FFE;
            color: #ffffff;
            margin-top: 10px;
        }

        .btn-solid:hover {
            background-color: #0019CB;
        }

        /* --- Links --- */
        .link-sm {
            display: block;
            margin-top: 15px;
            font-size: 0.85rem;
            text-decoration: underline; /* MODIFICADO: O sublinhado agora é padrão */
            color: #304FFE;
        }

        .left-panel .link-sm {
            color: #304FFE;
        }

        .link-sm:hover {
            /* A decoração de texto já é 'underline'. Você pode adicionar outros efeitos aqui, como uma mudança de cor */
            color: #0019CB;
        }
        .erro-msg {
            color: red;
            font-weight: 600;
            margin-bottom: 15px;
            text-align: center;
        }


    </style>
</head>
<body>
<div class="main-container">
    <div class="panel left-panel">
        <div class="content">
            <img src="../../img/FalgadjaLogo.png" alt="Logo da Empresa" class="logo">
            <h1>Bem-vindo de volta!</h1>
            <p>Acesse a sua conta agora mesmo.</p>
            <a href="../Login/login.jsp"><button class="btn btn-outline">Entrar</button></a>
        </div>
    </div>
    <div class="panel right-panel">
        <img src="../../img/abacoLogin.png" alt="Ícone Ábaco" class="icon-abaco">

        <div class="content">
            <h1>Faça o cadastro da sua empresa!</h1>
            <%
                String erro = (String) request.getAttribute("erro");
                if (erro != null) {
            %>
            <div class="erro-msg"><%= erro %></div>
            <%
                }
            %>
            <form action="${pageContext.request.contextPath}/cadastrarEmpresa" method="post">
                <div class="form-group">
                    <label for="empresa">Nome da Empresa</label>
                    <input type="text" id="empresa" name="nomeEmpresa" placeholder="Digite o nome da sua empresa" required>
                </div>
                <div class="form-group">
                    <label for="cnpj">CNPJ</label>
                    <input type="text" id="cnpj" name="cnpj" pattern="\d{2}\.\d{3}\.\d{3}/\d{4}-\d{2}|\d{14}" placeholder="00.000.000/0000-00" required>

                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="emailEmpresa" placeholder="seuemail@exemplo.com" required>
                </div>
                <div class="form-group">
                    <label for="senha">Senha</label>
                    <input type="password" id="senha" name="senha" placeholder="Crie uma senha forte" required>
                </div>
                <div class="form-group">
                    <label for="confirmarSenha">Confirme a sua senha</label>
                    <input type="password" id="confirmarSenha" name="confirmarSenha" placeholder="Repita a senha" required>
                </div>
                <button type="submit">Finalizar</button>

            </form>
            <a href="../Funcionario/cadastrarPessoa.jsp" class="link-sm">Cadastrar como pessoa</a>
        </div>
    </div>
</div>
</body>
</html>



