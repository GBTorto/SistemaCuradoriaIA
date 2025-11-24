package com.mycompany.sistemacuradoria;

// CORREÇÃO 1: Usar a interface List do pacote java.util
import java.util.List; 
import java.util.ArrayList; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaDAO {
    
    // ------------------------------
    // LISTAR NOMES + ID
    // ------------------------------
    public List<Categoria> listarNomesCategorias(){
        List<Categoria> categorias = new ArrayList<>();
        
        String sql = "select id_categoria, categoria from tb_categoria";
        
        ConnectionFactory factory = new ConnectionFactory();
        
        try(Connection c = factory.obtemConexao();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            
            while (rs.next()) {
                int idCategoria = rs.getInt("id_categoria");
                String categoria = rs.getString("categoria");
                
                categorias.add(new Categoria(idCategoria, categoria));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categorias;
    }
    
    // ------------------------------
    // BUSCAR TODAS AS CATEGORIAS
    // ------------------------------
    public List<Categoria> buscarTodas() {
        List<Categoria> categorias = new ArrayList<>();
        
        String sql = "select id_categoria, categoria from tb_categoria";
        
        ConnectionFactory factory = new ConnectionFactory();
        
        try(Connection c = factory.obtemConexao();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            
            while (rs.next()){
                Categoria cat = new Categoria();
                
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setNomeCategoria(rs.getString("categoria"));
                
                categorias.add(cat);
            }
        }
        catch(SQLException e){
            System.err.println("Erro ao buscar categoria: " + e.getMessage());
        }
        return categorias;
    }

    // ------------------------------
    // *** MÉTODO QUE ESTAVA FALTANDO ***
    // INSERIR CATEGORIA
    // ------------------------------
    public boolean inserirCategoria(String nomeCategoria) {
        String sql = "INSERT INTO tb_categoria (categoria) VALUES (?)";

        ConnectionFactory factory = new ConnectionFactory();

        try (Connection c = factory.obtemConexao();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, nomeCategoria);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir categoria: " + e.getMessage());
            return false;
        }
    }
    
    
    public void excluirCategoria(int idCategoria) {
    try {
        ConnectionFactory factory = new ConnectionFactory();
        Connection c = factory.obtemConexao();


        if (c == null) {
            System.err.println("ERRO: Conexão é null em excluirCategoria()");
            return;
        }

        PreparedStatement ps = c.prepareStatement(
            "DELETE FROM categorias WHERE id_categoria = ?"
        );

        ps.setInt(1, idCategoria);
        ps.executeUpdate();

        ps.close();
        c.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    // ------------------------------
    // SALVAR INTERESSE DO USUÁRIO
    // ------------------------------
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

    // ------------------------------
    // REMOVER USUÁRIO (CUIDADO)
    // ------------------------------
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
