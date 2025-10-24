package org;

import conexao.Conexao;

import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        Conexao conexao = new Conexao();
        Connection c = conexao.conectar();
        if (c != null) {
            System.out.println("✅ Conectou!");
            conexao.desconectar(c);
        } else {
            System.out.println("❌ Falhou a conexão!");
        }
    }
}
