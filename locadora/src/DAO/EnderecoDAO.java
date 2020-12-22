/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.*;
import MODEL.Endereco;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ana
 */
public class EnderecoDAO {
    private Connection conecta;
    
    public EnderecoDAO(){
        this.conecta = new DAO().conecta();
    }
    
    public int salvar(Endereco endereco){
        
        String sql = "INSERT INTO endereco(estado, cidade, bairro, rua, complemento, num_residencia) " + 
                     "VALUES(?,?,?,?,?,?);";
        
        try{
            PreparedStatement stmt = conecta.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, endereco.getEstado());
            stmt.setString(2, endereco.getCidade());            
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getRua());
            stmt.setString(5, endereco.getComplemento());
            stmt.setInt(6, endereco.getNum_residencia());
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int idGerado = rs.getInt(1);
            endereco.setId_end(idGerado);
            
            stmt.close();
                
            return idGerado;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public void alterar(Endereco endereco){
        
        String sql = "UPDATE endereco e "
                   + "SET estado = ?, "
                   + "    cidade = ?, "
                   + "    bairro = ?, "
                   + "    rua = ?, "
                   + "    complemento = ?, "
                   + "    num_residencia = ? "
                   + "WHERE e.id_end = ?";
        
        try
        {
            PreparedStatement stmt = conecta.prepareStatement(sql);
           
            stmt.setString(1, endereco.getEstado());
            stmt.setString(2, endereco.getCidade());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getRua());
            stmt.setString(5, endereco.getComplemento());
            stmt.setInt(6, endereco.getNum_residencia());
            stmt.setInt(7, endereco.getId_end());
           
           
            stmt.execute();
            stmt.close();
        }
        catch(SQLException e){
            System.out.println(e);
        }
        
    }
    
    public void deletar(Endereco endereco){
        String sql = "DELETE FROM endereco "
                   + "WHERE id_end = ?";
        
        try{
            PreparedStatement stmt = conecta.prepareStatement(sql);
            stmt.setInt(1, endereco.getId_end());
            stmt.execute();
            stmt.close();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public int salvarAlterar(Endereco endereco){
        if(endereco.getId_end() != 0){
            this.alterar(endereco);
            return 0;
        }
        else {
            return this.salvar(endereco);
        }
    }
    
    public Endereco buscaEndereco(int idEndereco){
        
        String sql = "SELECT * "
                   + "FROM endereco e "
                   + "WHERE e.id_end = ?";
        ResultSet resultadoBD;
        Endereco endereco = new Endereco();

        try{
            PreparedStatement stmt = conecta.prepareStatement(sql);
            stmt.setInt(1, idEndereco);
            resultadoBD = stmt.executeQuery();
            resultadoBD.next();
            
            endereco.setId_end(resultadoBD.getInt("id_end"));            
            endereco.setEstado(resultadoBD.getString("estado"));
            endereco.setCidade(resultadoBD.getString("cidade"));
            endereco.setBairro(resultadoBD.getString("bairro"));
            endereco.setRua(resultadoBD.getString("rua"));
            endereco.setComplemento(resultadoBD.getString("complemento"));
            endereco.setNum_residencia(resultadoBD.getInt("num_residencia"));

        }catch(SQLException e){
            System.out.println(e);
        }
        return endereco;
    }
}
