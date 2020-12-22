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
public class AluguelDAO {
    private Connection conecta;
    
    public AluguelDAO(){
        this.conecta = new DAO().conecta();
    }
    
    public int salvar(Aluguel aluguel){
        
        String sql = "INSERT INTO aluguel(id_filme, id_pes, data_aluguel, data_devolucao, ativo) " + 
                     "VALUES(?,?,?,?,?);";
        
        try{
            PreparedStatement stmt = conecta.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, aluguel.getFilme().getId_filme());
            stmt.setInt(2, aluguel.getPessoa().getId_pes());            
            stmt.setString(3, aluguel.getData_aluguel());
            stmt.setString(4, aluguel.getData_devolucao());
            stmt.setBoolean(5, aluguel.isAtivo());
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int idGerado = rs.getInt(1);
            aluguel.setId_aluguel(idGerado);
            
            stmt.close();
            return idGerado;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public Boolean verificaAluguelC(int idCliente){
        
        String sql = "SELECT * "
                   + "FROM aluguel a "
                   + "WHERE a.id_pes = ?";
        ResultSet resultadoBD;
        Aluguel aluguel = new Aluguel();

        try{
            PreparedStatement stmt = conecta.prepareStatement(sql);
            stmt.setInt(1, idCliente );
            resultadoBD = stmt.executeQuery();
            resultadoBD.next();
            aluguel.setId_aluguel(resultadoBD.getInt("id_pes"));
            return true;

        }catch(SQLException e){
            System.out.println(e);
            return false;
        }
        
    }
    
    public void deletar(Aluguel aluguel){
        String sql = "DELETE FROM aluguel "
                   + "WHERE id_aluguel = ?";
        
        try{
            PreparedStatement stmt = conecta.prepareStatement(sql);
            stmt.setInt(1, aluguel.getId_aluguel());
            stmt.execute();
            stmt.close();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public void inativar(Aluguel aluguel){
        String sql = "UPDATE aluguel "
                   + "SET ativo = false "
                   + "WHERE id_aluguel = ?";
        
        try{
            PreparedStatement stmt = conecta.prepareStatement(sql);
            stmt.setInt(1, aluguel.getId_aluguel());
            stmt.execute();
            stmt.close();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public Boolean verificaAluguel(int idFilme){
        
        String sql = "SELECT * "
                   + "FROM aluguel a "
                   + "WHERE a.id_filme = ?";
        ResultSet resultadoBD;
        Aluguel aluguel = new Aluguel();

        try{
            PreparedStatement stmt = conecta.prepareStatement(sql);
            stmt.setInt(1, idFilme );
            resultadoBD = stmt.executeQuery();
            resultadoBD.next();
            aluguel.setId_aluguel(resultadoBD.getInt("id_aluguel"));
            return true;

        }catch(SQLException e){
            System.out.println(e);
            return false;
        }
        
    }
    
    public List<Aluguel> listarAluguel(String nomeFilme,String cpf){
            
        String where = "";
        
        if (nomeFilme != ""){
            where = "WHERE f.nome_filme LIKE('%" + nomeFilme + "%') "; 
        }
        
        if(cpf != ""){
            if(where == ""){
                where = "WHERE p.cpf LIKE('%" + cpf + "%') ";
            }
            else {
                where += "AND p.cpf LIKE('%" + cpf + "%') ";
            }
        }
        
        String sql = "SELECT * "
                   + "FROM aluguel a "
                   + "INNER JOIN pessoa p ON (p.id_pes = a.id_pes) "
                   + "INNER JOIN filme f ON (f.id_filme = a.id_filme) "
                   + where 
                   + "ORDER BY a.id_aluguel";
        ResultSet resultadoBD;
        List<Aluguel> alugueis = new ArrayList<Aluguel>();
        
        try{
            PreparedStatement stmt = conecta.prepareStatement(sql);
            resultadoBD = stmt.executeQuery();
            while (resultadoBD.next()) {
                
                Aluguel aluguel = new Aluguel();
                aluguel.setId_aluguel(resultadoBD.getInt("id_aluguel"));
                aluguel.setData_aluguel(resultadoBD.getString("data_aluguel"));
                aluguel.setData_devolucao(resultadoBD.getString("data_devolucao"));
                aluguel.setAtivo(resultadoBD.getBoolean("ativo"));
                
                Filme filme = new Filme();
                filme.setId_filme(resultadoBD.getInt("id_filme"));
                filme.setNome_filme(resultadoBD.getString("nome_filme"));
                aluguel.setFilme(filme);
                
                Pessoa pessoa = new Pessoa();
                pessoa.setId_pes(resultadoBD.getInt("id_pes"));
                pessoa.setNome_pes(resultadoBD.getString("nome_pes"));
                aluguel.setPessoa(pessoa);
                
                alugueis.add(aluguel);               
            }
            resultadoBD.close();
            stmt.close();           
            
            
        }catch(SQLException e){
            System.out.println(e);
        }
        
        return alugueis;
    }
}