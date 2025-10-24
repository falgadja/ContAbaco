<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 16/10/2025
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cadastro / Login</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
  <style>
    /* --- Reset Básico e Estilos do Corpo --- */
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
      padding: 20px; /* Adiciona espaçamento para ecrãs pequenos */
    }

    /* --- Container Principal --- */
    .main-container {
      display: flex;
      width: 100%;
      max-width: 1100px;
      height: auto; /* Altura automática para se ajustar ao conteúdo */
      min-height: 600px;
      border-radius: 50px;
      background-color: #ffffff;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
      overflow: hidden;
      position: relative;
      flex-direction: column; /* Layout de coluna para telemóveis */
    }

    /* Adaptação para ecrãs maiores */
    @media (min-width: 768px) {
      .main-container {
        flex-direction: row; /* Layout de linha para desktops */
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
      display: none; /* Escondido por defeito */
    }

    @media (min-width: 768px) {
      .main-container::after {
        display: block; /* Visível apenas em ecrãs maiores */
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
      order: 2; /* Aparece em segundo no telemóvel */
    }
    @media (min-width: 768px) {
      .left-panel {
        flex: 2;
        order: 1; /* Volta a ser o primeiro em ecrãs maiores */
      }
    }

    .logo {
      width: 80px;
      margin-bottom: 30px;
    }

    .left-panel h1 {
      font-size: 2.2rem;
      font-weight: 700;
      margin-bottom: 15px;
      color: #304FFE;
    }

    .left-panel p {
      font-size: 1rem;
      margin-bottom: 30px;
      color: #304FFE;
    }

    .left-panel button{
      width: 300px;
    }

    /* --- Painel Direito (Cadastro) --- */
    .right-panel {
      background-color: #ffffff;
      color: #333;
      padding: 40px 20px;
      position: relative;
      order: 1; /* Aparece primeiro no telemóvel */
    }

    @media (min-width: 768px) {
      .right-panel {
        flex: 3;
        padding: 40px 60px;
        order: 2; /* Volta a ser o segundo em ecrãs maiores */
      }
    }

    .icon-abaco {
      position: absolute;
      top: 260px;
      left: 50px;
      width: 55px;
      display: none; /* Escondido em telemóveis para uma interface mais limpa */
    }

    @media (min-width: 768px) {
      .icon-abaco {
        display: block;
      }
    }

    .right-panel h1 {
      font-size: 1.8rem;
      font-weight: 600;
      color: #304FFE;
      margin-bottom: 25px;
    }

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
      text-decoration: underline;
      color: #304FFE;
    }

    .left-panel .link-sm {
      color: #304FFE;
    }

    .link-sm:hover {
      color: #0019CB;
    }

    a{
      color: #304FFE;
      text-decoration: underline;
    }


  </style>
</head>
<body>
<div class="main-container">
  <div class="panel left-panel">
    <div class="content">
      <img src="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png" alt="Logo da Empresa" class="logo">
      <h1>Bem-vindo de volta!</h1>
      <p>Acesse sua conta agora mesmo.</p>
      <a href="login.html"><button class="btn btn-outline">Entrar</button></a>
    </div>
  </div>

  <div class="panel right-panel">

    <div class="content">
      <h1>Faça seu cadastro!</h1>
      <form>
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" name="email" placeholder="seuemail@exemplo.com" required>
        </div>
        <div class="form-group">
          <label for="senha">Senha</label>
          <input type="password" id="senha" name="senha" placeholder="Crie uma senha forte" required minlength="8">
        </div>
        <div class="form-group">
          <label for="confirme-senha">Confirme sua senha</label>
          <input type="password" id="confirme-senha" name="confirme-senha" placeholder="Repita a senha" required minlength="8">
        </div>
        <button type="submit" class="btn btn-solid">Cadastrar</button>
      </form>
      <a href="${pageContext.request.contextPath}/view/cadastrarEmpresa.jsp" class="link-sm">Cadastrar como Empresa</a>
    </div>
  </div>
</div>
</body>
</html>