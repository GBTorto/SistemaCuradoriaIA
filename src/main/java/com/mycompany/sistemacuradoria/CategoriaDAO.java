package com.mycompany.sistemacuradoria; // Ajuste o pacote conforme sua estrutura



// CORREÇÃO 1: Usar a interface List do pacote java.util
import java.util.List; 
import java.util.ArrayList; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaDAO {
    public List<Categoria> buscarTodas() {
        List<Categoria> categorias = new ArrayList<>();
        
        String sql = "select id_categoria, categoria from tb_categoria";
        
        ConnectionFactory factory = new ConnectionFactory();
        
        try(Connection c = factory.obtemConexao();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ){
            while (rs.next()){
                Categoria cat = new Categoria();
                String nomeLido = rs.getString("categoria");
                System.out.println("DEBUG DAO: Lendo Categoria: " + nomeLido);
                
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setNomeCategoria(rs.getString("categoria"));
                
                categorias.add(cat);
            }
        }
        catch(SQLException e){
            System.err.println("Erro ao buscar categoria" + e.getMessage());
        }
        return categorias;
    }
    
    public boolean salvarInteresse(int idUser, List<Integer> idsCategorias){
        String sql = "insert into tb_user_interesse(id_categoria, id_user) values (?, ?)";
        
        ConnectionFactory factory = new ConnectionFactory();
        
        try(Connection c = factory.obtemConexao();
            PreparedStatement ps = c.prepareStatement(sql)){
            
            for (int idCategoria : idsCategorias){
                ps.setInt(1, idCategoria);
                ps.setInt(2, idUser);
                
                ps.addBatch();
            }
            
            ps.executeBatch();
            return true;
        }catch(Exception e){
            System.err.println("Erro ao salvar interesses: " + e.getMessage());
            return false;
        }
    }
    
    public void cancelarInteresse(int idUser){
        String sql = "delete from tb_user where id_user = ?";
        
        ConnectionFactory factory = new ConnectionFactory();
        
        try(Connection c = factory.obtemConexao();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, idUser);
            
            ps.executeUpdate();
            
        }catch(Exception e){
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}
