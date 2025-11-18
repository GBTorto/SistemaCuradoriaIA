package com.mycompany.sistemacuradoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Pichau
 */
public class User {
    private int id_user;
    private String nome;
    private String email;
    private int idade;
    private String tipo;
    private String senha;
    
    public User(){};
    
    public User(int id_user, String nome, String email, int idade, String tipo, String senha){
        this.id_user = id_user;
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.tipo = tipo;
        this.senha = senha;
    }
    
    public int getId_user(){
        return id_user;
    }
    
    public String getNome(){
        return nome;
    }
    
    public String getEmail(){
        return email;
    }
    
    public int getIdade(){
        return idade;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public String getSenha(){
        return senha;
    }
    
    public void setId_user(int id_user){
        this.id_user = id_user;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setIdade(int idade){
        this.idade = idade;
    }
    
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    
    public void setSenha(String senha){
        this.senha = senha;
    }
    
    public int cadastrar(){
        String sql = "INSERT INTO tb_user (nome, idade, tipo, senha, email) VALUES (?, ?, ?, ?, ?)";
        int idGerado = -1;
        
        ConnectionFactory factory = new ConnectionFactory();
        
        try(Connection c = factory.obtemConexao()){
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, this.nome);
            ps.setInt(2, this.idade);
            ps.setString(3, this.tipo);
            ps.setString(4, this.senha);
            ps.setString(5, this.email);
            ps.execute();
            
            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    // recuperar o id gerado pelo banco de dados
                    
                    idGerado = rs.getInt(1);
                    this.id_user = idGerado;
                }
            } 
        }
        catch(Exception e){
           e.printStackTrace();
        }
        return idGerado;
    }
}
