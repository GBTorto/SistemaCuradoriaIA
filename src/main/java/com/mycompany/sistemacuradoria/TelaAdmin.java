package com.mycompany.sistemacuradoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaAdmin extends JFrame {

    public TelaAdmin() {
        setTitle("Painel do Administrador");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Usuários", painelUsuarios());
        tabs.add("Categorias", painelCategorias());

        add(tabs);
    }

    // ============================================
    // PAINEL DE USUÁRIOS
    // ============================================
    private JPanel painelUsuarios() {
        JPanel p = new JPanel(new BorderLayout());

        String[] colunas = {"ID", "Nome", "Idade", "Email", "Tipo"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        UserDAO dao = new UserDAO();
        List<User> lista = dao.listaUsers();

        for (User u : lista) {
            model.addRow(new Object[]{
                    u.getId_user(),
                    u.getNome(),
                    u.getIdade(),
                    u.getEmail(),
                    u.getTipo()
            });
        }

        JTable tabela = new JTable(model);
        // Ativar ordenação
        tabela.setAutoCreateRowSorter(true);

        // Permitir mover colunas
        tabela.getTableHeader().setReorderingAllowed(true);

        // Permitir redimensionar colunas
        tabela.getTableHeader().setResizingAllowed(true);
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel botoes = new JPanel();

        JButton btAdd = new JButton("Adicionar Usuário");
        btAdd.addActionListener(e -> {
            JTextField nomeField = new JTextField();
            JTextField idadeField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField senhaField = new JTextField();

            Object[] form = {
                    "Nome:", nomeField,
                    "Idade:", idadeField,
                    "Email:", emailField,
                    "Senha:", senhaField
            };

            int result = JOptionPane.showConfirmDialog(
                    null, form, 
                    "Cadastrar Usuário", 
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {

                try {
                    int idade = Integer.parseInt(idadeField.getText());

                    User novo = new User();
                    novo.setNome(nomeField.getText());
                    novo.setIdade(idade);
                    novo.setEmail(emailField.getText());
                    novo.setSenha(senhaField.getText());
                    novo.setTipo("Comum");

                    int idGerado = dao.cadastrar(novo);

                    model.addRow(new Object[]{
                            idGerado,
                            novo.getNome(),
                            novo.getIdade(),
                            novo.getEmail(),
                            novo.getTipo()
                    });

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Dados inválidos!");
                }
            }
        });

        JButton btSair = new JButton("Fechar Admin");
        btSair.addActionListener(e -> dispose());

        botoes.add(btSair);
        botoes.add(btAdd);

        p.add(scroll, BorderLayout.CENTER);
        p.add(botoes, BorderLayout.SOUTH);

        return p;
    }

    // ============================================
    // PAINEL DE CATEGORIAS
    // ============================================
    private JPanel painelCategorias() {
        JPanel p = new JPanel(new BorderLayout());

        String[] colunas = {"ID", "Categoria"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        CategoriaDAO dao = new CategoriaDAO();
        List<Categoria> lista = dao.listarNomesCategorias();

        for (Categoria c : lista) {
            model.addRow(new Object[]{
                    c.getIdCategoria(),
                    c.getNomeCategoria()
            });
        }

        JTable tabela = new JTable(model);
        // Ativar ordenação
        tabela.setAutoCreateRowSorter(true);

        // Permitir mover colunas
        tabela.getTableHeader().setReorderingAllowed(true);

        // Permitir redimensionar colunas
        tabela.getTableHeader().setResizingAllowed(true);
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel botoes = new JPanel();

        JButton btAdd = new JButton("Adicionar Categoria");
        btAdd.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog("Nome da nova categoria:");

            if (nome != null && !nome.trim().isEmpty()) {
                dao.inserirCategoria(nome);
                model.addRow(new Object[]{"-", nome});
            }
        });

        JButton btVoltar = new JButton("Fechar Admin");
        btVoltar.addActionListener(e -> dispose());

        botoes.add(btVoltar);
        botoes.add(btAdd);

        p.add(scroll, BorderLayout.CENTER);
        p.add(botoes, BorderLayout.SOUTH);

        return p;
    }
}
