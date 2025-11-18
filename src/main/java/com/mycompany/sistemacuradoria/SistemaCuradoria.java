package com.mycompany.sistemacuradoria;

import javax.swing.SwingUtilities;

public class SistemaCuradoria {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                Cadastro telaCadastro = new Cadastro();
                telaCadastro.setVisible(true);
                telaCadastro.setLocationRelativeTo(null);
                
            }
        });
    }
}
