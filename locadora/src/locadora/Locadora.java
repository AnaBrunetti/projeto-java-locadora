/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locadora;
import DAO.*;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author ana
 */
public class Locadora {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException{
        // TODO code application logic here
        Connection conecta = new DAO().conecta();
        System.out.println("Conexão Aberta");
        conecta.close();
    }
    
}
