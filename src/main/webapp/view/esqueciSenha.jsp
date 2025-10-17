<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 16/10/2025
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página de recuperar senha</title>
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
            padding: 20px;
        }

        /* --- Container Principal do Login --- */
        .login-container {
            width: 100%;
            max-width: 700px; /* Largura ajustada para um formulário único */
            padding: 50px 40px;
            border-radius: 40px;
            background-color: #ffffff;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        }

        .content {
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
            width: 100%;
        }

        .icon-main {
            width: 60px;
            margin-bottom: 20px;
        }

        h1 {
            font-size: clamp(1.8rem, 5vw, 2.2rem); /* Fonte responsiva */
            font-weight: 700;
            color: #304FFE;
            margin-bottom: 30px;
        }

        /* --- Formulário --- */
        form {
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
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

        /* --- Botão Principal --- */
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

        .btn-solid {
            background-color: #304FFE;
            color: #ffffff;
            margin-top: 10px; /* Espaço acima do botão */
        }

        .btn-solid:hover {
            background-color: #0019CB;
        }

        /* --- Links --- */
        .link-sm {
            font-size: 0.85rem;
            text-decoration: none;
            color: #304FFE;
            transition: all 0.3s ease;
        }

        .link-sm:hover {
            text-decoration: underline;
        }

        .link-forgot {
            width: 100%;
            text-align: right; /* Alinha o link à direita */
            margin-bottom: 20px;
        }

        .link-register {
            margin-top: 25px; /* Espaço acima do link de cadastro */
        }

        p{
            color: #304FFE;
        }


    </style>
</head>
<body>
<div class="login-container">
    <div class="content">
        <img src="../img/logo azul bonito sem fundo 2 (1).png" alt="Ícone Principal" class="icon-main">

        <h1>Recupere sua senha!</h1>
        <p>Informe o email cadastrado que nós enviaremos um link para a alteração da senha.</p>

        <form>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Digite seu e-mail" required>
            </div>
            <button type="submit" class="btn btn-solid">Recuperar</button>
        </form>

        <a href="Login/login.jsp" class="link-sm link-register">Cancelar</a>
    </div>
</div>
</body>
</html>
