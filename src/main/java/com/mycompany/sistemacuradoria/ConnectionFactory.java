package com.mycompany.sistemacuradoria;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionFactory {
    String url = "jdbc:mysql://localhost:3306/db_curadoria_ia?useSSL=false&serverTimezone=America/Sao_Paulo";
    String user = "root";
    String password = "y7oZf88>%F1Q";
    
    public Connection obtemConexao(){
        try{
            Connection c = DriverManager.getConnection(url, user, password);
            return c;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
