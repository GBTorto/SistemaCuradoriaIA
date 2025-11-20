package com.mycompany.sistemacuradoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class PostDAO {
    public List<Post> listarPost(int idUser){
        List<Post> posts = new ArrayList<>();
        
        String sql = 
            "SELECT tb_post.titulo, tb_post.autor, tb_post.conteudo, " +
            "tb_post.id_categoria, tb_post.id_user, " +
            "tb_user.nome AS nome_user, " +
            "tb_categoria.categoria AS nome_categoria " +
            "FROM tb_post " +
            "INNER JOIN tb_user ON tb_post.id_user = tb_user.id_user " +
            "INNER JOIN tb_categoria ON tb_post.id_categoria = tb_categoria.id_categoria " +
            "INNER JOIN tb_user_interesse on tb_categoria.id_categoria = tb_user_interesse.id_categoria " +
            "WHERE tb_user_interesse.id_user = ?";
        
        ConnectionFactory factory = new ConnectionFactory();

        try(Connection c = factory.obtemConexao();
            PreparedStatement ps = c.prepareStatement(sql)){
            
            ps.setInt(1, idUser);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Post p = new Post();
                
                p.setTitulo(rs.getString("titulo"));
                p.setAutor(rs.getString("autor"));
                p.setConteudo(rs.getString("conteudo"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setIdUser(rs.getInt("id_user"));
                p.setNomeUser(rs.getString("nome_user"));
                p.setNomeCategoria(rs.getString("nome_categoria"));
                
                posts.add(p);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return posts;
    }
    
    public List<Post> listarTodosPosts(){
        List<Post> posts = new ArrayList<>();
        
        String sql = 
            "SELECT tb_post.titulo, tb_post.autor, tb_post.conteudo, " +
            "tb_post.id_categoria, tb_post.id_user, " +
            "tb_user.nome AS nome_user, " +
            "tb_categoria.categoria AS nome_categoria " +
            "FROM tb_post " +
            "INNER JOIN tb_user ON tb_post.id_user = tb_user.id_user " +
            "INNER JOIN tb_categoria ON tb_post.id_categoria = tb_categoria.id_categoria";

        
        ConnectionFactory factory = new ConnectionFactory();

        try(Connection c = factory.obtemConexao();
            PreparedStatement ps = c.prepareStatement(sql)){
            
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Post p = new Post();
                
                p.setTitulo(rs.getString("titulo"));
                p.setAutor(rs.getString("autor"));
                p.setConteudo(rs.getString("conteudo"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setIdUser(rs.getInt("id_user"));
                p.setNomeUser(rs.getString("nome_user"));
                p.setNomeCategoria(rs.getString("nome_categoria"));
                
                posts.add(p);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return posts;
    }
}
