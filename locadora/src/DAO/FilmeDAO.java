/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.*;
import MODEL.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ana
 */
public class FilmeDAO {
    private Connection conecta;
    
    public FilmeDAO(){
        this.conecta = new DAO().conecta();
    }
    
    public int salvar(Filme filme){
        
        String sql = "INSERT INTO filme(id_gen, nome_filme, sinopse, class_indicativa, quant_estoque) " + 
                     "VALUES(?,?,?,?,?);";
        
        try{
            PreparedStatement stmt = conecta.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, filme.getGenero().getId_gen());
            stmt.setString(2, filme.getNome_filme());            
            stmt.setString(3, filme.getSinopse());
            stmt.setString(4, filme.getClass_indicativa());
            stmt.setInt(5, filme.getQuant_estoque());
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int idGerado = rs.getInt(1);
            filme.setId_filme(idGerado);
            
            stmt.close();
            
            return idGerado;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public void alterar(Filme filme){
        
        String sql = "UPDATE filme "
                   + "SET id_gen = ?, "
                   + "    nome_filme = ?, "
                   + "    sinopse = ?, "
                   + "    class_indicativa = ?, "
                   + "    quant_estoque = ? "
                   + "WHERE filme.id_filme = ? ";
        
        try
        {
            PreparedStatement stmt = conecta.prepareStatement(sql);
           
            stmt.setInt(1, filme.getGenero().getId_gen());
            stmt.setString(2, filme.getNome_filme());
            stmt.setString(3, filme.getSinopse());
            stmt.setString(4, filme.getClass_indicativa());
            stmt.setInt(5, filme.getQuant_estoque());
            stmt.setInt(6, filme.getId_filme());
           
            stmt.execute();
            stmt.close();
        }
        catch(SQLException e){
            System.out.println(e);
        }
        
    }
    
    
    public void deletar(Filme filme){
        String sql = "DELETE FROM filme "
                   + "WHERE id_filme = ?";
        
        try{
            PreparedStatement stmt = conecta.prepareStatement(sql);
            stmt.setInt(1, filme.getId_filme());
            stmt.execute();
            stmt.close();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public Filme buscaFilme(String nomeFilme){
        
        String sql = "SELECT * "
                   + "FROM filme f "
                   + "WHERE f.nome_filme = ?";
        ResultSet resultadoBD;
        Filme filme = new Filme();

        try{
            PreparedStatement stmt = conecta.prepareStatement(sql);
            stmt.setString(1, nomeFilme );
            resultadoBD = stmt.executeQuery();
            resultadoBD.next();
            Genero genero = new Genero();
            genero.setId_gen(resultadoBD.getInt("id_gen"));
            
            filme.setId_filme(resultadoBD.getInt("id_filme"));            
            filme.setNome_filme(resultadoBD.getString("nome_filme"));
            filme.setSinopse(resultadoBD.getString("sinopse"));
            filme.setGenero(genero);
            filme.setClass_indicativa(resultadoBD.getString("class_indicativa"));
            filme.setQuant_estoque(resultadoBD.getInt("quant_estoque"));

        }catch(SQLException e){
            System.out.println(e);
        }
        return filme;
    }
    
    public List<Filme> listarFilmes(String nomeFilme){
        String where = "";
        if(nomeFilme != ""){
            where = "WHERE f.nome_filme LIKE('%" + nomeFilme + "%') ";
        }
        String sql = "SELECT * "
                   + "FROM filme f "
                   + where
                   + "ORDER BY f.nome_filme ";
        ResultSet resultadoBD;
        List<Filme> filmes = new ArrayList<Filme>();
        
        try{
            PreparedStatement stmt = conecta.prepareStatement(sql);
            resultadoBD = stmt.executeQuery();
            while (resultadoBD.next()) {
                
                Filme filme = new Filme();
                filme.setId_filme(resultadoBD.getInt("id_filme"));
                filme.setNome_filme(resultadoBD.getString("nome_filme"));
                filme.setSinopse(resultadoBD.getString("sinopse"));
                filme.setClass_indicativa(resultadoBD.getString("class_indicativa"));
                filme.setQuant_estoque(resultadoBD.getInt("quant_estoque"));
                
                Genero genero = new Genero();
                genero.setId_gen(resultadoBD.getInt("id_gen"));
                filme.setGenero(genero);
                
                filmes.add(filme);               
            }
            resultadoBD.close();
            stmt.close();           
            
            
        }catch(SQLException e){
            System.out.println(e);
        }
        
        return filmes;
    }
}