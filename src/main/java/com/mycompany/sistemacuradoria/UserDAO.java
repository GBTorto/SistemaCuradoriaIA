/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemacuradoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pichau
 */
public class UserDAO {
    public User login(String email, String senha){
        String sql = "select id_user, email, senha, nome, tipo from tb_user where email = ? AND senha = ?";
        User user = null;
        
        ConnectionFactory factory = new ConnectionFactory();
        
        try(Connection c = factory.obtemConexao();
            PreparedStatement ps = c.prepareStatement(sql)){
        
            ps.setString(1, email);
            ps.setString(2, senha);
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    String senhaDB = rs.getString("senha");
                    
                    if(senha.equals(senhaDB)){
                        user = new User();
                        
                        user.setId_user(rs.getInt("id_user"));
                        user.setNome(rs.getString("nome"));
                        user.setEmail(rs.getString("email"));
                        user.setTipo(rs.getString("tipo"));
                    }
                }
            }
        }
        catch(SQLException e){
            System.err.println("Erro durante login: " + e.getMessage());
        }
        return user;
    }
    
    public List<User> listaUsers(){
        List<User> users = new ArrayList<>();
        
        String sql = "select id_user, nome, idade, tipo, email from tb_user";
        
        ConnectionFactory factory = new ConnectionFactory();
        
        try(Connection c = factory.obtemConexao();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            
            while(rs.next()){
                User u = new User();
                u.setId_user(rs.getInt("id_user"));
                u.setNome(rs.getString("nome"));
                u.setIdade(rs.getInt("idade"));
                u.setTipo(rs.getString("tipo"));
                u.setEmail(rs.getString("email"));
                
                users.add(u);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return users;
    }
}
