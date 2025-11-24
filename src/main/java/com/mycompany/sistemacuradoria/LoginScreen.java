package com.mycompany.sistemacuradoria;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.function.Consumer;
import javax.swing.*;

public class LoginScreen extends JPanel {

    private Image bg;

    // Callback enviado pelo MainUI
    private Consumer<User> onLoginSuccess;

    public LoginScreen(Consumer<User> onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;

        bg = new ImageIcon("src/assets/bg_mountains.png").getImage();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(buildCard(), gbc);
    }

    /** Painel Central */
    private JPanel buildCard() {
        JPanel card = new RoundedPanel(30);
        card.setPreferredSize(new Dimension(450, 500));
        card.setBackground(new Color(255, 255, 255, 230));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Login");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(40, 40, 60));

        JTextField userField = styledField("Email");
        JPasswordField passField = styledPassword("Senha");

        JButton btnLogin = styledButton("Entrar");

        btnLogin.addActionListener(e -> {
            String email = userField.getText().trim();
            String senha = new String(passField.getPassword());
            
              // ============================================
    // LOGIN DO ADMIN (fixo: admin / admin)
    // ============================================
            if (email.equals("admin") && senha.equals("admin")) {
            User admin = new User();
            admin.setId_user(0);
            admin.setNome("Administrador");
            admin.setEmail("admin");
            admin.setTipo("Admin");

            JOptionPane.showMessageDialog(this,
            "Login de Administrador efetuado!");

            onLoginSuccess.accept(admin);
            return; // impede continuar para o login normal
            }

            try {
                UserDAO dao = new UserDAO();
                User user = dao.login(email, senha);  // 🔥 autenticação real

                if (user != null) {
                    JOptionPane.showMessageDialog(this,
                        "Login efetuado com sucesso!");

                    onLoginSuccess.accept(user);  // 🔥 envia para o MainUI
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Email ou senha inválidos.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Erro ao tentar fazer login: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        card.add(Box.createVerticalStrut(30));
        card.add(title);
        card.add(Box.createVerticalStrut(35));
        card.add(userField);
        card.add(Box.createVerticalStrut(20));
        card.add(passField);
        card.add(Box.createVerticalStrut(25));
        card.add(btnLogin);

        return card;
    }

    /** Campo de texto estilizado */
    private JTextField styledField(String placeholder) {
        JTextField txt = new JTextField();
        txt.setMaximumSize(new Dimension(300, 45));
        txt.setPreferredSize(new Dimension(280, 45));
        txt.setFont(new Font("SansSerif", Font.PLAIN, 16));

        txt.setBackground(Color.WHITE);
        txt.setForeground(new Color(30, 30, 30));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        txt.putClientProperty("JTextField.placeholderText", placeholder);
        return txt;
    }

    /** Campo de senha estilizado */
    private JPasswordField styledPassword(String placeholder) {
        JPasswordField txt = new JPasswordField();
        txt.setMaximumSize(new Dimension(300, 45));
        txt.setPreferredSize(new Dimension(280, 45));
        txt.setFont(new Font("SansSerif", Font.PLAIN, 16));

        txt.setBackground(Color.WHITE);
        txt.setForeground(new Color(30, 30, 30));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        txt.putClientProperty("JTextField.placeholderText", placeholder);
        return txt;
    }

    /** Botão */
    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(110, 70, 170));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(220, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    /** Painel arredondado */
    class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(
                    0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
            super.paintComponent(g);
        }
    }

    /** Fundo */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();

        g2d.drawImage(bg, 0, 0, w, h, this);

        GradientPaint gp = new GradientPaint(
                0, 0, new Color(43, 29, 74, 160),
                w, h, new Color(250, 208, 229, 100)
        );

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
