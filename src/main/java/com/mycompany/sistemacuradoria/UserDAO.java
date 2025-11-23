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
    
    // --- MÉTODO LOGIN EXISTENTE ---
  public User login(String email, String senha){
    String sql = "select id_user, email, senha, nome, tipo from tb_user where email = ? AND senha = ?";
    User user = null;

    ConnectionFactory factory = new ConnectionFactory();

    try {
        Connection c = factory.obtemConexao();

        if (c == null) {
            throw new SQLException("Conexão retornou NULL! Verifique o ConnectionFactory.");
        }

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId_user(rs.getInt("id_user"));
                    user.setNome(rs.getString("nome"));
                    user.setEmail(rs.getString("email"));
                    user.setTipo(rs.getString("tipo"));
                }
            }
        }

    } catch (SQLException e){
        System.err.println("Erro durante login: " + e.getMessage());
    }

    return user;
}

    
    // ------------------------------------------------------------------------
    // --- NOVO MÉTODO CADASTRAR (PARA SER USADO NO MainUI.java) ---
    // Retorna o ID gerado pelo banco de dados ou -1 em caso de falha.
    public int cadastrar(User u){
        String sql = "INSERT INTO tb_user (nome, idade, tipo, senha, email) VALUES (?, ?, ?, ?, ?)";
        int idGerado = -1;
        
        ConnectionFactory factory = new ConnectionFactory();
        
        try(Connection c = factory.obtemConexao()){
            // Adicionamos RETURN_GENERATED_KEYS para poder pegar o ID
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); 
            
            // Usamos os getters do objeto User para preencher o statement
            ps.setString(1, u.getNome());
            ps.setInt(2, u.getIdade());
            ps.setString(3, u.getTipo());
            ps.setString(4, u.getSenha());
            ps.setString(5, u.getEmail());
            ps.execute();
            
            // Tentamos recuperar a chave gerada (ID)
            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    idGerado = rs.getInt(1); // O primeiro campo gerado é o ID
                }
            }
        }
        catch(Exception e){
           e.printStackTrace();
           // Em caso de erro (ex: e-mail duplicado), o idGerado permanece -1
        }
        return idGerado;
    }
    // ------------------------------------------------------------------------
    
    // --- MÉTODO LISTAUSERS EXISTENTE ---
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