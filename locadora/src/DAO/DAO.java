/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ana
 */
public class DAO {
    public Connection conecta(){
        try{
            
            String url = "jdbc:postgresql://localhost:5434/locadora";
            String usuario = "postgres";
            String senha = "2324";
            
            return DriverManager.getConnection(url, usuario, senha);
            
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
