package com.mycompany.sistemacuradoria;

// Imports necessários do seu projeto
import com.mycompany.sistemacuradoria.User;
import com.mycompany.sistemacuradoria.UserDAO;
import com.mycompany.sistemacuradoria.Post;
import com.mycompany.sistemacuradoria.PostDAO;
import javax.swing.table.DefaultTableModel;


import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


// -------------------------------------------------------------------


public class MainUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContent; 
    
    // Armazenar o usuário logado para usar em outras telas (ex: criar post)
    private User currentUser = null; 
    
    // Lista local para visualização (deve ser alimentada pelo PostDAO)
    private List<Post> posts = new ArrayList<>(); 
    
    // Elementos da tela de Perfil
    private JLabel lblPerfilNome;
    private JLabel lblPerfilEmail;
    private JLabel lblPerfilTipo;
    
    private JPanel postsPanel; // Para a lista de posts
    
    // Variáveis de classe para os botões
    private JButton btLogin;
    private JButton btCadastro;
    private JButton btCriarPost; 
    private JButton btPosts;
    private JButton btAdmin;
    private JButton btLogout;


    public MainUI() {
        // --- Construtor ---
         aplicarFonteGlobal();
        setTitle("Plataforma Curadoria — Sistema Completo");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        setExtendedState(JFrame.MAXIMIZED_BOTH); 

        add(buildTopBar(), BorderLayout.NORTH);
        add(buildSidebar(), BorderLayout.WEST);
        add(buildMainContent(), BorderLayout.CENTER); // MainContent agora é o GradientPanel

        // Inicializa o Perfil (para que os Labels existam antes de serem preenchidos

        setVisible(true);
    }
    
    private void aplicarFonteGlobal() {
    Font novaFonte = new Font("Montserrat", Font.PLAIN, 16);  // Troque a fonte se quiser

    UIManager.put("Label.font", novaFonte);
    UIManager.put("Button.font", novaFonte);
    UIManager.put("TextField.font", novaFonte);
    UIManager.put("PasswordField.font", novaFonte);
    UIManager.put("TextArea.font", novaFonte);
    UIManager.put("ComboBox.font", novaFonte);
    UIManager.put("List.font", novaFonte);
    UIManager.put("Menu.font", novaFonte);
    UIManager.put("MenuItem.font", novaFonte);
    UIManager.put("CheckBox.font", novaFonte);
    UIManager.put("RadioButton.font", novaFonte);
    UIManager.put("Table.font", novaFonte);
}
    // =========================================================================
    // === TOP BAR (Mantido) ===
    private JPanel buildTopBar() {
        JPanel top = new GradientPanel(new Color(20, 20, 20), new Color(40, 40, 40)); 
        top.setLayout(new BorderLayout());
        top.setPreferredSize(new Dimension(1100, 70));

        JLabel title = new JLabel("  ✨ Plataforma Curadoria");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26)); 
        title.setForeground(new Color(255, 255, 255)); 
        
        top.add(title, BorderLayout.WEST);
        
        JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlRight.setOpaque(false);
        top.add(pnlRight, BorderLayout.EAST);

        return top;
    }

    private void styleTopButton(JButton b) {
        b.setFocusPainted(false);
        b.setBackground(new Color(0, 150, 255)); 
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
       
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(new Color(0, 120, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(new Color(0, 150, 255));
            }
        });
    }

    // =========================================================================
    // === SIDEBAR (CORRIGIDO: Usa BoxLayout sem "glue" para manter o tamanho) ===
    private JPanel buildSidebar() {
        // Usando BoxLayout para que os componentes se movam quando escondidos.
        JPanel side = new JPanel(); 
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS)); // ESSENCIAL PARA SUMIR O ESPAÇO
        
        side.setPreferredSize(new Dimension(220, 0)); 
        side.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        side.setBackground(new Color(15, 15, 15)); 

        JButton btHome = styledButton("🏠 Home");
        
        // Inicializando variáveis de classe
        btLogin = styledButton("🔑 Login");
        btCadastro = styledButton("📝 Cadastro");
        
        btCriarPost = styledButton("✍️ Criar Post");
        btPosts = styledButton("📰 Ver Posts");
        JButton btConfig = styledButton("⚙️ Configurações");
        
        JButton btPerfil = styledButton("👤 Meu Perfil");
        
        btAdmin = styledButton("🛠 Painel Admin");
        btAdmin.setVisible(false);
        btAdmin.addActionListener(e -> cardLayout.show(mainContent, "admin"));


    btPerfil.addActionListener(e -> {
    updatePerfilInfo();   // <- Esse método tem que existir
    cardLayout.show(mainContent, "perfil");
    });

        
        btHome.addActionListener(e -> cardLayout.show(mainContent, "home"));
        btLogin.addActionListener(e -> cardLayout.show(mainContent, "login"));
        btCadastro.addActionListener(e -> cardLayout.show(mainContent, "cadastro"));
        
        btCriarPost.addActionListener(e -> {
            if (currentUser != null) {
                cardLayout.show(mainContent, "criarpost");
            } else {
                JOptionPane.showMessageDialog(this, "Você precisa fazer login para criar um post!", "Acesso Negado", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btPosts.addActionListener(e -> {
            updatePostsList();
            cardLayout.show(mainContent, "verposts");
        });
        
        btConfig.addActionListener(e -> cardLayout.show(mainContent, "config"));
        
        
        
        
        // Adicionando os componentes 
        side.add(btHome);
        side.add(btLogin);
        side.add(btCadastro);
        side.add(btCriarPost);
        side.add(btPosts);
        side.add(btPerfil);
        side.add(btConfig);
        side.add(btAdmin);
        
        // ADICIONAR UM ESPAÇO FLEXÍVEL ABAIXO PARA EMPURRAR OS BOTÕES PARA O TOPO (mantendo-os grandes)
        //side.add(Box.createVerticalStrut(80));
        
        // Define o estado inicial da sidebar
        updateSidebarVisibility();
        
        return side;
    }

    private JButton styledButton(String t) {
        JButton b = new JButton(t);
        b.setFocusPainted(false);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setBackground(new Color(30, 30, 30)); 
        b.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10)); 
        b.setHorizontalAlignment(SwingConstants.LEFT); 
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // CORREÇÃO: Define o alinhamento X e o tamanho máximo para que o BoxLayout estique o componente na largura.
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200)); // Altura fixa para todos os botões

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(new Color(0, 150, 255)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(new Color(30, 30, 30));
            }
        });

        return b;
    }


    // =========================================================================
    // ======== ÁREA PRINCIPAL COM TODAS AS TELAS ========
    private JPanel buildMainContent() {

   
    mainContent = new BackGroundPanel();
    mainContent.setOpaque(false);     // ESSENCIAL!
    
    cardLayout = new CardLayout();
    mainContent.setLayout(cardLayout);

    mainContent.add(buildHome(), "home");
    mainContent.add(buildLogin(), "login");
    cardLayout.show(mainContent, "home");
    mainContent.add(buildCadastro(), "cadastro");
    mainContent.add(buildCriarPost(), "criarpost");
    mainContent.add(buildVerPosts(), "verposts");
    mainContent.add(buildPerfil(), "perfil");
    mainContent.add(buildConfig(), "config");
    mainContent.add(buildAdminPanel(), "admin");


    return mainContent;
}

    
    private JPanel buildHome() {
    JPanel h = new JPanel(new GridBagLayout()); 
    h.setOpaque(false); 

    JLabel welcome = new JLabel(
        "👋 Bem-vindo(a) à Plataforma Curadoria! Selecione uma opção no menu.",
        SwingConstants.CENTER
    );
    welcome.setFont(new Font("Segoe UI", Font.PLAIN, 24));
    welcome.setForeground(new Color(50, 50, 50));

    h.add(welcome);
    return h;
}


    // === LOGIN (Lógica de Visibilidade Integrada) ===
  // === LOGIN (Lógica de Visibilidade Integrada) ===
private JPanel buildLogin() {
    LoginScreen login = new LoginScreen(user -> {

        // guarda o user logado para uso em outras telas
        this.currentUser = user;

        // segurança: normaliza valores nulos
        String tipo = (user.getTipo() == null) ? "" : user.getTipo().toLowerCase();
        String email = (user.getEmail() == null) ? "" : user.getEmail().toLowerCase();
        String senha = (user.getSenha() == null) ? "" : user.getSenha();

        // condição admin: aceita "admin", "Administrador", "Admin" — flexível
        boolean isAdmin = tipo.contains("admin") || email.equals("admin") || "admin".equals(senha);

        if (isAdmin) {
            // mostra o botão admin na sidebar (se existir)
            if (btAdmin != null) {
                btAdmin.setVisible(true);
            }

            // abre a janela independente do CardLayout (TelaAdmin é um JFrame)
           cardLayout.show(mainContent, "admin");

            // atualiza perfil / sidebar (opcional)
            updatePerfilInfo();
            updateSidebarVisibility();

            // mostra uma confirmação
            JOptionPane.showMessageDialog(this, "Bem-vindo, Administrador!");

            // não entra na navegação para "home" — interrompe aqui
            return;
        }

        // para usuários comuns, segue fluxo normal
        updatePerfilInfo();
        updateSidebarVisibility();
        cardLayout.show(mainContent, "home");
        JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
    });

    return login;
}


    // === CADASTRO (Lógica de Visibilidade Integrada) ===
    private JPanel buildCadastro() {
    JPanel p = new JPanel(new GridBagLayout());
    p.setOpaque(false); 
    
    JPanel formPanel = new GradientPanel(new Color(255, 255, 255), new Color(240, 240, 240));
    
    formPanel.setPreferredSize(new Dimension(450, 500));
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    formPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
        BorderFactory.createEmptyBorder(30, 50, 30, 50)
    ));

    JLabel t = titleLabel("📝 Cadastro");
    t.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel l1 = smallLabel("Email:"); l1.setAlignmentX(Component.CENTER_ALIGNMENT);
    JTextField email = field(); email.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
    
    JLabel l_user = smallLabel("Usuário/Nome:"); l_user.setAlignmentX(Component.CENTER_ALIGNMENT);
    JTextField user = field(); user.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

    JLabel l2 = smallLabel("Senha:"); l2.setAlignmentX(Component.CENTER_ALIGNMENT);
    JPasswordField pass = passField(); pass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

    JLabel l3 = smallLabel("Confirmar Senha:"); l3.setAlignmentX(Component.CENTER_ALIGNMENT);
    JPasswordField pass2 = passField(); pass2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
    
    JLabel l4 = smallLabel("Idade:"); l4.setAlignmentX(Component.CENTER_ALIGNMENT);
    JTextField idadeField = field(); idadeField.setMaximumSize(new Dimension(100, 35));
    
    JButton cadastrar = bigButton("Cadastrar"); 
    cadastrar.setAlignmentX(Component.CENTER_ALIGNMENT);
    cadastrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45)); 

    // LÓGICA DE CADASTRO
    cadastrar.addActionListener(e -> {
        String userStr = user.getText().trim();
        String emailStr = email.getText().trim();
        String senhaStr = new String(pass.getPassword());
        String senha2Str = new String(pass2.getPassword());
        String idadeStr = idadeField.getText().trim();

        if (userStr.isEmpty() || emailStr.isEmpty() || senhaStr.isEmpty() || senha2Str.isEmpty() || idadeStr.isEmpty()) {
            JOptionPane.showMessageDialog(p, "Preencha todos os campos!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!senhaStr.equals(senha2Str)) {
            JOptionPane.showMessageDialog(p, "As senhas não coincidem!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int idade = 0;
        try {
            idade = Integer.parseInt(idadeStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(p, "Idade inválida!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            UserDAO dao = new UserDAO();
            
            User newUser = new User();
            newUser.setNome(userStr); 
            newUser.setEmail(emailStr);
            newUser.setSenha(senhaStr);
            newUser.setIdade(idade);

            // 🔥 CORREÇÃO AQUI 🔥
            newUser.setTipo("Comum");
            
            int idGerado = dao.cadastrar(newUser);
            
            if (idGerado > 0) {
                JOptionPane.showMessageDialog(p, "Usuário cadastrado com sucesso! ID: " + idGerado + ". Faça login.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainContent, "login"); 
                
                user.setText(""); email.setText(""); idadeField.setText("");
                pass.setText(""); pass2.setText("");
                
                updateSidebarVisibility(); 
                
            } else {
                JOptionPane.showMessageDialog(p, "Erro ao cadastrar. O e-mail pode já estar em uso.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(p, "Erro de comunicação: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    });
    
    formPanel.add(t);
    formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    formPanel.add(l1); formPanel.add(email);
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(l_user); formPanel.add(user);
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(l2); formPanel.add(pass);
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(l3); formPanel.add(pass2);
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(l4); formPanel.add(idadeField);
    formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    formPanel.add(cadastrar);

    p.add(formPanel);

    return p;
}


    // === CRIAR POST (Centralizado) ===
    private JPanel buildCriarPost() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false); 

        JPanel formPanel = new GradientPanel(new Color(255, 255, 255), new Color(240, 240, 240));
        formPanel.setPreferredSize(new Dimension(700, 600)); 
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));

        JLabel t = titleLabel("✍️ Criar Novo Post");
        t.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel l1 = smallLabel("Título:"); l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField titulo = field(); titulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel l_cat = smallLabel("Categoria:"); l_cat.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComboBox<Categoria> comboCategoria = new JComboBox<>();
        comboCategoria.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        
        try {
            // Supondo que CategoriaDAO, Categoria, PostDAO existem no seu projeto
            // CategoriaDAO catDao = new CategoriaDAO();
            // List<Categoria> categorias = catDao.buscarTodas(); 
            // for (Categoria cat : categorias) {
            //     comboCategoria.addItem(cat);
            // }
            // Simulando categorias
            comboCategoria.addItem(new Categoria(1, "IA Responsável"));
            comboCategoria.addItem(new Categoria(2, "Cibersegurança"));
            comboCategoria.addItem(new Categoria(3, "Privacidade & Ética Digital"));
            
            comboCategoria.addItem(new Categoria(0, "Selecione uma categoria"));
            comboCategoria.setSelectedIndex(comboCategoria.getItemCount() - 1);
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar categorias: " + e.getMessage());
            comboCategoria.addItem(new Categoria(0, "Erro ao carregar"));
        }
        
        
        JLabel l2 = smallLabel("Conteúdo:"); l2.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextArea texto = new JTextArea();
        texto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        texto.setWrapStyleWord(true);
        texto.setLineWrap(true);
        JScrollPane sp = new JScrollPane(texto);
        sp.setPreferredSize(new Dimension(600, 250)); 

        JButton postar = bigButton("Publicar"); 
        postar.setAlignmentX(Component.CENTER_ALIGNMENT);
        postar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45)); 

        // LÓGICA DE POSTAGEM 
        postar.addActionListener(e -> {
            String tituloStr = titulo.getText().trim();
            String textoStr = texto.getText().trim();
            Object selectedItem = comboCategoria.getSelectedItem();
            Categoria categoriaSelecionada = (selectedItem instanceof Categoria) ? (Categoria) selectedItem : null;
            
            if (currentUser == null) {
                JOptionPane.showMessageDialog(p, "Você precisa estar logado para publicar!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tituloStr.isEmpty() || textoStr.isEmpty() || categoriaSelecionada == null || categoriaSelecionada.getIdCategoria() == 0) {
                 JOptionPane.showMessageDialog(p, "Preencha o título, conteúdo e selecione uma categoria válida!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Post novoPost = new Post();
            novoPost.setTitulo(tituloStr);
            novoPost.setConteudo(textoStr);
            novoPost.setAutor(currentUser.getNome());
            novoPost.setIdCategoria(categoriaSelecionada.getIdCategoria());
            novoPost.setIdUser(currentUser.getId_user());
            // Simulação do nome da categoria (assumindo que Post tem setNomeCategoria)
            novoPost.setNomeCategoria(categoriaSelecionada.toString()); 
            
            // PostDAO postDao = new PostDAO();
            // boolean sucesso = postDao.salvarPost(novoPost);
            
            posts.add(novoPost); 
            
            JOptionPane.showMessageDialog(p, "Post publicado (Simulado).", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            titulo.setText(""); texto.setText("");
            comboCategoria.setSelectedIndex(comboCategoria.getItemCount() - 1);
            
        });

        formPanel.add(t);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(l1); formPanel.add(titulo);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(l_cat); formPanel.add(comboCategoria);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(l2); formPanel.add(sp);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(postar);

        p.add(formPanel);

        return p;
    }

// === VER POSTS (Melhorado) ===

private JScrollPane buildVerPosts() { 
    postsPanel = new JPanel();
    postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
    postsPanel.setBackground(new Color(245, 245, 245)); 

    postsPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200)); 

    JScrollPane scroll = new JScrollPane(postsPanel);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scroll.setBorder(BorderFactory.createEmptyBorder());

    return scroll;
}

// ... (Restante do updatePostsList)

    private void updatePostsList() {
        PostDAO postDao = new PostDAO();
        // A linha abaixo usa listagem simulada ou real, dependendo da sua implementação.
        if (currentUser != null) {
              // posts = postDao.listarPost(currentUser.getId_user()); // Descomentar se tiver a função
        } else {
              // posts = postDao.listarTodosPosts(); // Descomentar se tiver a função
        }
        
        postsPanel.removeAll();
        postsPanel.add(titleLabel("📰 Posts Recentes"));
        postsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        if (posts.isEmpty()) {
              postsPanel.add(new JLabel("Nenhum post encontrado."));
        } else {
              for (Post p : posts) { 
                  JPanel card = new JPanel();
                  card.setLayout(new BorderLayout());
                  card.setBackground(Color.WHITE); 
                  card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                  ));
                  card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
    
                  // Assumindo que Post tem getNomeCategoria()
                  JLabel title = new JLabel("✨ " + p.getTitulo() + " (Categoria: " + p.getNomeCategoria() + ")"); 
                  title.setFont(new Font("Segoe UI", Font.BOLD, 18));
                  title.setForeground(new Color(0, 150, 255));
    
                  JTextArea conteudo = new JTextArea(p.getConteudo()); 
                  conteudo.setWrapStyleWord(true);
                  conteudo.setLineWrap(true);
                  conteudo.setEditable(false);
                  conteudo.setOpaque(false);
                  conteudo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
    
                  JLabel autor = new JLabel("Autor: " + p.getAutor(), SwingConstants.RIGHT); 
                  autor.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                  autor.setForeground(new Color(100, 100, 100));

                  card.add(title, BorderLayout.NORTH);
                  card.add(conteudo, BorderLayout.CENTER);
                  card.add(autor, BorderLayout.SOUTH);
    
                  postsPanel.add(card);
                  postsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
               }
        }
        
        postsPanel.revalidate();
        postsPanel.repaint();
    }
    
    // === MÉTODOS DE ESTILIZAÇÃO (Mantidos) ===
    private JLabel titleLabel(String t) {
        JLabel l = new JLabel(t, SwingConstants.CENTER);
        l.setFont(new Font("Segoe UI", Font.BOLD, 32));
        l.setForeground(new Color(50, 50, 50));
        return l;
    }

    private JLabel smallLabel(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 15)); 
        l.setForeground(new Color(70, 70, 70));
        return l;
    }

    private JTextField field() {
        JTextField f = new JTextField();
        f.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10) 
        ));
        return f;
    }

    private JPasswordField passField() {
        JPasswordField f = new JPasswordField();
        f.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return f;
    }

    private JButton bigButton(String t) {
        JButton b = new JButton(t);
        b.setFocusPainted(false);
        b.setBackground(new Color(0, 150, 255));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(new Color(0, 120, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(new Color(0, 150, 255));
            }
        });

        return b;
    }
    
    // === PERFIL (Adicionado Logout com controle de visibilidade) ===
   private JPanel buildPerfil() {
    JPanel p = new JPanel(new GridBagLayout());
    p.setOpaque(false);

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)
    ));
    infoPanel.setPreferredSize(new Dimension(500, 350));

    // TÍTULO
    JLabel t = new JLabel("👤 Perfil do Usuário");
    t.setFont(new Font("Segoe UI", Font.BOLD, 20));
    t.setAlignmentX(Component.CENTER_ALIGNMENT);

    lblPerfilNome = new JLabel("Nome: ");
    lblPerfilEmail = new JLabel("Email: ");
    lblPerfilTipo = new JLabel("Tipo de Usuário: ");

    lblPerfilNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    lblPerfilEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    lblPerfilTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

    infoPanel.add(t);
    infoPanel.add(Box.createRigidArea(new Dimension(0, 25)));
    infoPanel.add(lblPerfilNome);
    infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
    infoPanel.add(lblPerfilEmail);
    infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
    infoPanel.add(lblPerfilTipo);
    infoPanel.add(Box.createRigidArea(new Dimension(0, 25)));

    // BOTÃO LOGOUT
    JButton btLogout = new JButton("⬅ Fazer Logout");
    btLogout.setBackground(new Color(0, 150, 255));
    btLogout.setForeground(Color.WHITE);
    btLogout.setFocusPainted(false);
    btLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btLogout.setAlignmentX(Component.CENTER_ALIGNMENT);

    btLogout.addActionListener(e -> {
        currentUser = null;
        updatePerfilInfo();
        updateSidebarVisibility();
        cardLayout.show(mainContent, "home");
        JOptionPane.showMessageDialog(this, "Logout realizado!", "Sessão Encerrada", JOptionPane.INFORMATION_MESSAGE);
    });

    infoPanel.add(btLogout);

    p.add(infoPanel);

    return p;
    
    
}
   
  
   private JPanel painelUsuariosAdmin() {
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
JScrollPane scroll = new JScrollPane(tabela);

    // --- BOTÃO DE ADICIONAR ---
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

    // --- BOTÃO DE EXCLUIR ---
    JButton btExcluir = new JButton("Excluir Usuário");
    btExcluir.addActionListener(ev -> {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um usuário para excluir.");
            return;
        }

        int id = (int) model.getValueAt(linha, 0);
        System.out.println("ID enviado ao DAO: " + id);

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Tem certeza que deseja excluir o usuário ID " + id + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            dao.deletar(id);
            model.removeRow(linha);
        }
    });

    // --- ADICIONA OS BOTÕES AO PAINEL ---
    JPanel botoes = new JPanel();
    botoes.add(btAdd);
    botoes.add(btExcluir);

    p.add(scroll, BorderLayout.CENTER);
    p.add(botoes, BorderLayout.SOUTH);

    return p;}

   
   
  private JPanel painelCategoriasAdmin() {
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
    JScrollPane scroll = new JScrollPane(tabela);

    // Botão Adicionar
    JButton btAdd = new JButton("Adicionar Categoria");
    btAdd.addActionListener(e -> {
        String nome = JOptionPane.showInputDialog("Nome da nova categoria:");

        if (nome != null && !nome.trim().isEmpty()) {
            dao.inserirCategoria(nome);
            model.addRow(new Object[]{"-", nome});
        }
    });

    // Botão Excluir
    JButton btExcluir = new JButton("Excluir Categoria");
    btExcluir.addActionListener(ev -> {

        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma categoria para excluir");
            return;
        }

        int id = (int) tabela.getValueAt(linha, 0);

        int resp = JOptionPane.showConfirmDialog(
                null,
                "Excluir a categoria selecionada?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (resp == JOptionPane.YES_OPTION) {
            dao.excluirCategoria(id);
            model.removeRow(linha);
        }
    });

    JPanel botoes = new JPanel();
    botoes.add(btAdd);
    botoes.add(btExcluir);

    p.add(scroll, BorderLayout.CENTER);
    p.add(botoes, BorderLayout.SOUTH);

    return p;
}



   private JPanel buildAdminPanel() {
     JPanel container = new JPanel(new BorderLayout());

    // Criar abas (as mesmas da TelaAdmin)
    JTabbedPane abas = new JTabbedPane();

    abas.add("Usuários", painelUsuariosAdmin());
    abas.add("Categorias", painelCategoriasAdmin());

    container.add(abas, BorderLayout.CENTER);

    return container;
}
    
    // Método para atualizar as informações do perfil
    private void updatePerfilInfo() {
        if (currentUser != null) {
            lblPerfilNome.setText("Nome: " + currentUser.getNome());
            lblPerfilEmail.setText("Email: " + currentUser.getEmail());
            lblPerfilTipo.setText("Tipo de Usuário: " + currentUser.getTipo());
             if (btLogout != null) btLogout.setEnabled(true);
        } else {
            lblPerfilNome.setText("Nome: Não Logado");
            lblPerfilEmail.setText("Email: N/A");
            lblPerfilTipo.setText("Tipo de Usuário: Visitante");
             if (btLogout != null) btLogout.setEnabled(false);
        }
    }
    
     private void updateSidebarVisibility() {
        boolean logged = (currentUser != null);

        btLogin.setVisible(!logged);
        btCadastro.setVisible(!logged);

        btCriarPost.setVisible(logged);
        //btMeu Perfil.setVisible(logged);
        //btConfigurações.setVisible(logged);
        
         // Chama revalidate/repaint no painel pai, o que força o BoxLayout a recalcular o espaço.
        SwingUtilities.invokeLater(() -> {
            if (btLogin.getParent() != null) {
                btLogin.getParent().revalidate();
                btLogin.getParent().repaint();
            }

        if (currentUser == null) {
            btAdmin.setVisible(false);
        }
        });
    }

    
    
    // === CONFIGURAÇÕES (Mantido) ===
       private JPanel buildConfig() {
        JPanel p = new JPanel(new GridBagLayout()); 
        p.setOpaque(false); 

        JLabel l = titleLabel("⚙️ Configurações do Sistema"); 

        p.add(l); 
        
        return p;
    }
    
    // OBS: Classes auxiliares Categoria, GradientPanel e CategoriaDAO são necessárias
    // para compilar, mas não foram incluídas aqui, pois não fazem parte da MainUI.
}