/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemacuradoria;

/**
 *
 * @author Pichau
 */
public class Post {
    private int idPost;
    private String titulo;
    private String autor;
    private String conteudo;
    private int idCategoria;
    private int idUser;
    private String nomeUser;
    private String nomeCategoria;
    
    public Post(){};
    
    public Post(int idPost, String titulo, String autor, String conteudo, int idCategoria, int idUser, String nomeUser, String nomeCategoria){
        this.idPost = idPost;
        this.titulo  = titulo;
        this.autor = autor;
        this.conteudo = conteudo;
        this.idCategoria = idCategoria;
        this.idUser = idUser;
        this.nomeUser = nomeUser;
        this.nomeCategoria = nomeCategoria;
    }
    
    public int getIdPost(){
        return idPost;
    }
    
    public String getTitulo(){
        return titulo;
    }
    
    public String getAutor(){
        return autor;
    }
    
    public String getConteudo(){
        return conteudo;
    }
    
    public int getIdCategoria(){
        return idCategoria;
    }
    
    public int getIdUser(){
        return idUser;
    }
    
    public String getNomeUser(){
        return nomeUser;
    }
    
    public String getNomeCategoria(){
        return nomeCategoria;
    }
    
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    
    public void setAutor(String autor){
        this.autor = autor;
    }
    
    public void setConteudo(String conteudo){
        this.conteudo = conteudo;
    }
    
    public void setIdCategoria(int idCategoria){
        this.idCategoria = idCategoria;
    }
    
    public void setIdUser(int idUser){
        this.idUser = idUser;
    }
    
    public void setNomeUser(String nomeUser){
        this.nomeUser = nomeUser;
    }
    
    public void setNomeCategoria(String nomeCategoria){
        this.nomeCategoria = nomeCategoria;
    }
}
