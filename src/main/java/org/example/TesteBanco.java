package org.example;

import conexao.Conexao;

import java.sql.Connection;

public class TesteBanco {
    public static void main(String[] args) {
        //TESTAR BANCO DE DADOS
        Conexao conexao = new Conexao();
        Connection con = null;
        if(conexao.desconectar(con)==true){
            System.out.println("Banco de Dados conectado com sucesso ");
        }else{
            System.out.println("Não foi possivel conectar ao banco de dados");
        }
    }

}
