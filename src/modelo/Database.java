/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pablo Herrero
 */
public class Database {
    public static Connection crearConexion(){
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "PABLOHERRERO", "123456");
            Statement s = conexion.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
}
