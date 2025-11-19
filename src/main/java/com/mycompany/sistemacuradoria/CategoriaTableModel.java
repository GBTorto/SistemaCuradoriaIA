package com.mycompany.sistemacuradoria;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CategoriaTableModel extends AbstractTableModel{
    private final List<Categoria> categorias;
    private final String[] colunas = {"ID", "Categoria"};
    
    public CategoriaTableModel(List<Categoria> categoria){
        this.categorias = categoria;
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
        return categorias.size();
    }
    
    // Define o valor a ser exibido em cada bloco da tabela
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        Categoria categoria = categorias.get(rowIndex);
        
        switch(columnIndex){
            case 0: return categoria.getIdCategoria();
            case 1: return categoria.getNomeCategoria();
            default: return null;
        }
    }
    
    public Categoria getCategoria(int rowIndex){
        return categorias.get(rowIndex);
    }
}
