package com.mycompany.sistemacuradoria;

public class Categoria {
    
    private int idCategoria;
    private String nomeCategoria;
    
    // Construtor vazio (opcional, mas útil)
    public Categoria() {}
    
    // Construtor com todos os campos (opcional, mas útil)
    public Categoria(int idCategoria, String nomeCategoria) {
        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
    }

    // --- Getters e Setters ---
    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}