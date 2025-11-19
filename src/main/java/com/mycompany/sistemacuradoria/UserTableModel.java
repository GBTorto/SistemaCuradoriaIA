package com.mycompany.sistemacuradoria;

import java.util.List;
import javax.swing.table.AbstractTableModel;


public class UserTableModel extends AbstractTableModel {
    private final List<User> users;
    private final String[] colunas = {"ID", "Nome", "Idade", "Email", "Tipo"};
    
    public UserTableModel(List<User> user){
        this.users = user;
    }
    
    // Define a quantidade de colunas
    @Override
    public int getColumnCount(){
        return colunas.length;
    }
    
    // Retorna o nome da coluna
    @Override
    public String getColumnName(int columnIndex){
        return colunas[columnIndex];
    }
    
    // Define a quantidade de linhas da tabela
    @Override
    public int getRowCount(){
        return users.size();
    }
    
    // Define o valor a ser exibido em cada bloco da tabela
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        User user = users.get(rowIndex);
        
        switch(columnIndex){
            case 0: return user.getId_user();
            case 1: return user.getNome();
            case 2: return user.getIdade();
            case 3: return user.getEmail();
            case 4: return user.getTipo();
            default: return null;
        }
    }
    
    public User getUser(int rowIndex){
        return users.get(rowIndex);
    }
}
