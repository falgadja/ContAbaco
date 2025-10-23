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
            text-decoration: underline; /* MODIFICADO: O sublinhado agora é padrão */
            color: #304FFE;
            transition: all 0.3s ease;
        }

        .link-sm:hover {
            color: #0019CB; /* O sublinhado já é padrão, então mudamos a cor no hover */
        }

        .link-forgot {
            width: 100%;
            text-align: right; /* Alinha o link à direita */
            margin-bottom: 20px;
        }

        .link-register {
            margin-top: 25px; /* Espaço acima do link de cadastro */
        }

        p {
            color: #304FFE;
        }

        /* Ícone */
        .icon img {
            margin-bottom: 10px;
        }

        /* Título */
        h2 {
            color: #0000cc;
            margin-bottom: 20px;
        }

        label {
            display: block;
            text-align: left;
            font-weight: 600;
            color: #333;
            margin-bottom: 5px;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1.5px solid #0000cc;
            border-radius: 10px;
            outline: none;
            transition: 0.2s;
        }

        input:focus {
            border-color: #0000ff;
        }

        /* Botão */
        button {
            width: 100%;
            padding: 10px;
            background-color: #0000cc;
            border: none;
            color: white;
            border-radius: 10px;
            cursor: pointer;
            font-weight: 600;
            transition: 0.2s;
        }

        button:hover {
            background-color: #000099;
        }

        /* Links */
        a {
            color: #0000cc;
            text-decoration: none;
            font-size: 0.9em;
        }

        a.esqueci {
            float: right;
            margin-bottom: 20px;
        }

        a.cadastro {
            display: block;
            margin-top: 15px;
        }

        .erro {
            color: red;
            text-align: center;
            margin: 10px 0;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
<div class="login-container">
    <div class="content">
        <!-- Ícones de exemplo -->
        <img src="../../img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png" alt="Ícone Principal" class="icon-main">

        <h1>Faça seu login!</h1>
        <p>${mensagem}</p>

        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
        <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Digite seu e-mail" required>
            </div>
            <div class="form-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" placeholder="Digite sua senha" required>
            </div>
            <a href="../view/erro.jsp" class="link-sm link-forgot">Esqueci minha senha</a>
            <button type="submit" class="btn btn-solid">Entrar</button>
        </form>

        <a href="../view/erro.jsp" class="link-sm link-register">Ainda não tenho cadastro</a>
    </div>
</div>

<script>
    // Função para obter parâmetro da URL usando javascript
    function getParametroURL(nome) {
        const params = new URLSearchParams(window.location.search);
        return params.get(nome);
    }

    // Verificar se existe erro na URL
    const erro = getParametroURL('erro');
    const mensagemErro = document.getElementById('mensagemErro');

    if (erro === '1') {
        mensagemErro.textContent = "Email ou senha inválidos!";
    } else if (erro === '2') {
        mensagemErro.textContent = "Ocorreu um erro no sistema. Tente novamente mais tarde.";
    }
</script>
</body>
</html>
