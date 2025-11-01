<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página de Login</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

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
            max-width: 700px;
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
            font-size: clamp(1.8rem, 5vw, 2.2rem);
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

        .form-group input:focus {
            border-color: #0019CB;
        }


        .password-wrapper {
            position: relative;
            width: 100%;
        }

        .password-wrapper input {
            padding-right: 45px;
        }

        .password-wrapper i {
            position: absolute;
            top: 50%;
            right: 15px;
            transform: translateY(-50%);
            cursor: pointer;
            color: #304FFE;
            font-size: 1.2rem;
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
            margin-top: 10px;
        }

        .btn-solid:hover {
            background-color: #0019CB;
        }

        /* --- ESTADO DE CARREGAMENTO DO BOTÃO (NOVO) --- */
        .btn-solid.loading-state {
            display: flex;        /
            align-items: center;
            justify-content: center;
            gap: 10px;
            cursor: not-allowed;
            opacity: 0.8;
        }


        /* --- Links --- */
        .link-sm {
            font-size: 0.85rem;
            text-decoration: underline;
            color: #304FFE;
            transition: all 0.3s ease;
        }

        .link-sm:hover {
            color: #0019CB;
        }

        .link-forgot {
            width: 100%;
            text-align: right;
            margin-bottom: 20px;
        }

        .link-register {
            margin-top: 25px;
        }

        p {
            color: #304FFE;
        }

        /* --- Estilo da Mensagem de Erro --- */
        .erro {
            color: red;
            text-align: center;
            margin: 10px 0;
            font-size: 0.9em;
            min-height: 1.2em;
        }

    </style>
</head>
<body>
<div class="login-container">
    <div class="content">

        <img src="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png" alt="Ícone Principal" class="icon-main">

        <h1>Faça seu login!</h1>

        <p class="erro">${mensagem}</p>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Digite seu e-mail" required>
            </div>


            <div class="form-group">
                <label for="senha">Senha</label>
                <div class="password-wrapper">
                    <input type="password" id="senha" name="senha" placeholder="Digite sua senha" required>
                    <i class='bx bx-show' id="togglePassword"></i>
                </div>
            </div>

            <button type="submit" class="btn btn-solid">Entrar</button>
        </form>
    </div>
</div>


<script>
    document.addEventListener('DOMContentLoaded', function () {

        // --- CÓDIGO DE MOSTRAR/OCULTAR SENHA (EXISTENTE) ---
        const togglePassword = document.querySelector('#togglePassword');
        const passwordInput = document.querySelector('#senha');

        if (togglePassword && passwordInput) {

            togglePassword.addEventListener('click', function (e) {

                const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
                passwordInput.setAttribute('type', type);

                if (type === 'password') {
                    this.classList.remove('bx-hide');
                    this.classList.add('bx-show');
                } else {
                    this.classList.remove('bx-show');
                    this.classList.add('bx-hide');
                }
            });
        }

        const loginForm = document.querySelector('form');
        const submitButton = document.querySelector('.btn-solid');

        if (loginForm && submitButton) {
            loginForm.addEventListener('submit', function() {

                submitButton.disabled = true;

                submitButton.classList.add('loading-state');

                submitButton.innerHTML = '<i class="bx bx-loader-alt bx-spin"></i> Carregando...';
            });
        }

    });
</script>

</body>
</html>