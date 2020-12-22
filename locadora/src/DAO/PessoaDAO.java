/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import MODEL.Endereco;
import java.sql.*;
import MODEL.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ana
 */
public class PessoaDAO {
    private Connection conecta;
    
    public PessoaDAO(){
        this.conecta = new DAO().conecta();
    }
    
    public int salvar(Pessoa pessoa){
        
        String sql = "INSERT INTO pessoa(nome_pes, id_end, cpf, email, telefone_1, telefone_2, sexo) " + 
                     "VALUES(?,?,?,?,?,?,'" + pessoa.getSexo() + "');";
        
        try{
            PreparedStatement stmt = conecta.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, pessoa.getNome_pes());
            stmt.setInt(2, pessoa.getEndereco().getId_end());            
            stmt.setString(3, pessoa.getCpf());
            stmt.setString(4, pessoa.getEmail());
            stmt.setInt(5, pessoa.getTelefone1());
            stmt.setInt(6, pessoa.getTelefone2());
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int idGerado = rs.getInt(1);
            pessoa.setId_pes(idGerado);
            
            stmt.close();
            
            return idGerado;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public void alterar(Pessoa pessoa){
        
        String sql = "UPDATE pessoa p "
                   + "SET nome_pes = ?, "
                   + "    id_end = ?, "
                   + "    cpf = ?, "
                   + "    email = ?, "
                   + "    telefone_1 = ?, "
                   + "    telefone_2 = ?, "
                   + "    sexo = '" + pessoa.getSexo() + "' "
                   + "WHERE p.id_pes = ?";
        
        try
        {
            PreparedStatement stmt = conecta.prepareStatement(sql);
           
            stmt.setString(1, pessoa.getNome_pes());
            stmt.setInt(2, pessoa.getEndereco().getId_end());
            stmt.setString(3, pessoa.getCpf());
            stmt.setString(4, pessoa.getEmail());
            stmt.setInt(5, pessoa.getTelefone1());
            stmt.setInt(6, pessoa.getTelefone2());
            stmt.setInt(7, pessoa.getId_pes());
            
            stmt.execute();
            stmt.close();
        }
        catch(SQLException e){
            System.out.println(e);
        }
        
    }
    
    public void salvarAlterar(Pessoa pessoa){
        int id = this.buscaPessoa(pessoa.getCpf()).getId_pes();
        
        if(id != 0 && pessoa.getCpf() != null && pessoa.getCpf() != ""){
            pessoa.setId_pes(id);
            this.alterar(pessoa);
        }
        else {
            this.salvar(pessoa);
        }
    }
    
    public void deletar(Pessoa pessoa){
        String sql = "DELETE FROM pessoa "
                   + "WHERE id_pes = ?";
        
        try{
            PreparedStatement stmt = conecta.prepareStatement(sql);
            stmt.setInt(1, pessoa.getId_pes());
            stmt.execute();
            stmt.close();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public Pessoa buscaPessoa(String cpf){
        
        String sql = "SELECT * "
                   + "FROM pessoa p "
                   + "WHERE p.cpf = ?";
        ResultSet resultadoBD;
        
        Pessoa pessoa = new Pessoa();
        Endereco endereco = new Endereco();
        pessoa.setEndereco(endereco);
        
        try{
            PreparedStatement stmt = conecta.prepareStatement(sql);
            stmt.setString(1, cpf);
            resultadoBD = stmt.executeQuery();
            resultadoBD.next();

            pessoa.setId_pes(resultadoBD.getInt("id_pes"));            
            pessoa.setNome_pes(resultadoBD.getString("nome_pes"));
            pessoa.setCpf(resultadoBD.getString("cpf"));
            pessoa.setEmail(resultadoBD.getString("email"));
            pessoa.setTelefone1(resultadoBD.getInt("telefone_1"));
            pessoa.setTelefone2(resultadoBD.getInt("telefone_2"));
            pessoa.setSexo(resultadoBD.getString("sexo").charAt(0));

            endereco.setId_end(resultadoBD.getInt("id_end"));
            pessoa.setEndereco(endereco);

        }catch(SQLException e){
            System.out.println(e);
            return pessoa;
        }
        return pessoa;
    }
    
    public boolean verificaEnd(Pessoa pessoa){
        pessoa = this.buscaPessoa(pessoa.getCpf());
        if(pessoa.getEndereco().getId_end() != 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public List<Pessoa> listarPessoa(String cpf){
        
        String where = "";
        
        if(cpf != ""){
            where = "WHERE p.cpf = '" + cpf + "' ";
        }
        
        String sql = "SELECT * "
                   + "FROM pessoa p "
                   + where
                   + "ORDER BY p.nome_pes";
        ResultSet resultadoBD;
        List<Pessoa> pessoas = new ArrayList<Pessoa>();

        try{
            PreparedStatement stmt = conecta.prepareStatement(sql);
            resultadoBD = stmt.executeQuery();
            while (resultadoBD.next()) {
                
                Pessoa pessoa = new Pessoa();
                pessoa.setId_pes(resultadoBD.getInt("id_pes"));
                pessoa.setNome_pes(resultadoBD.getString("nome_pes"));
                pessoa.setCpf(resultadoBD.getString("cpf"));
                pessoa.setEmail(resultadoBD.getString("email"));
                pessoa.setTelefone1(resultadoBD.getInt("telefone_1"));
                pessoa.setTelefone2(resultadoBD.getInt("telefone_2"));
                pessoa.setSexo(resultadoBD.getString("sexo").charAt(0));
                
                Endereco endereco = new Endereco();
                endereco.setId_end(resultadoBD.getInt("id_end"));
                pessoa.setEndereco(endereco);
                
                pessoas.add(pessoa);               
            }
            resultadoBD.close();
            stmt.close();           
            
            
        }catch(SQLException e){
            System.out.println(e);
        }
        
        return pessoas;
    }
}
