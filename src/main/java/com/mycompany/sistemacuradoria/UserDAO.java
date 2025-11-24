package com.mycompany.sistemacuradoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // ============================================================
    // =============== MÉTODO LOGIN (JÁ EXISTENTE) =================
    // ============================================================
    public User login(String email, String senha) {
        String sql = "SELECT id_user, email, senha, nome, tipo FROM tb_user WHERE email = ? AND senha = ?";
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
                        user.setSenha(rs.getString("senha"));
                        user.setTipo(rs.getString("tipo"));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro durante login: " + e.getMessage());
        }

        return user;
    }

    // ============================================================
    // ============ MÉTODO CADASTRAR (PARA O ADMIN) ===============
    // ============================================================
    // Recebe um objeto User e devolve o ID gerado pelo banco.
    public int cadastrar(User u) {

        String sql = "INSERT INTO tb_user (nome, idade, tipo, senha, email) VALUES (?, ?, ?, ?, ?)";
        int idGerado = -1;

        ConnectionFactory factory = new ConnectionFactory();

        try (Connection c = factory.obtemConexao()) {

            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, u.getNome());
            ps.setInt(2, u.getIdade());
            ps.setString(3, u.getTipo());
            ps.setString(4, u.getSenha());
            ps.setString(5, u.getEmail());
            ps.execute();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGerado = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idGerado;
    }
    
    
    
    
    
    public boolean deletar(int id) {
    String sql = "DELETE FROM usuarios WHERE id_user = ?";

    try (Connection c = new ConnectionFactory().obtemConexao();
         PreparedStatement stmt = c.prepareStatement(sql)) {

        stmt.setInt(1, id);
        stmt.executeUpdate();
        return true;

    } catch (Exception e) {
        System.out.println("Erro ao deletar usuário: " + e.getMessage());
        return false;
    }
    }


    // ============================================================
    // =========== LISTAR TODOS OS USUÁRIOS (EXISTENTE) ===========
    // ============================================================
    public List<User> listaUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT id_user, nome, idade, tipo, email FROM tb_user";

        ConnectionFactory factory = new ConnectionFactory();

        try (Connection c = factory.obtemConexao();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                User u = new User();
                u.setId_user(rs.getInt("id_user"));
                u.setNome(rs.getString("nome"));
                u.setIdade(rs.getInt("idade"));
                u.setTipo(rs.getString("tipo"));
                u.setEmail(rs.getString("email"));

                users.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
