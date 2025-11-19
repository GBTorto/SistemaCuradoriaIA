package com.mycompany.sistemacuradoria;

import javax.swing.SwingUtilities;

public class SistemaCuradoria {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                
                Login telaLogin = new Login();
                telaLogin.setVisible(true);
                telaLogin.setLocationRelativeTo(null);
                
            }
        });
    }
}
