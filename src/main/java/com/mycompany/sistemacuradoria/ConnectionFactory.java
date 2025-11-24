package com.mycompany.sistemacuradoria;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private final String url =
    "jdbc:mysql://localhost:3306/db_curadoria_ia?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String user = "root";
    private final String password = "anima123";

    public Connection obtemConexao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 🔥 garante que o driver é carregado
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("ERRO AO CONECTAR: " + e.getMessage());
            return null;
        }
    }
}
