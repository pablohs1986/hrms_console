/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pablo Herrero
 */
public class Opcion {
    // Opciones emple
    public static void mostrarEmple(){
        Database db = new Database();
        Connection conexion = db.crearConexion();
        Statement s;
        
        try {
            s = conexion.createStatement();
            ResultSet listado = s.executeQuery("SELECT * FROM EMPLE");
            String format = ("\t%-12s | %-10s | %-12s | %-9s | %-20s | %-9s | %-10s | %-10s \n");
            System.out.format(format, "Nº EMPLEADO", "NOMBRE", "OFICIO", "MANAGER", "FECHA ALTA", "SALARIO", "COMISION", "Nº DEPARTAMENTO");
            System.out.println("\t----------------------------------------------------------------------------------------------------------------------");
            while(listado.next()){
                System.out.format(format, listado.getString("EMPNO"), listado.getString("NOMBRE"), listado.getString("OFICIO"), listado.getString("MGR"), listado.getString("FECALTA"), listado.getString("SAL"), listado.getString("COMM"), listado.getString("DEPT_NO"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public static void buscarEmple() {
        Database db = new Database();
        Connection conexion = db.crearConexion();
        Statement s;


        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Introducir número de empleado:");
            int entrada = sc.nextInt();
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM EMPLE WHERE EMPNO = ?"); 
            stmt.setInt(1, entrada);
            ResultSet listado = stmt.executeQuery();
            String format = ("\t%-12s | %-10s | %-12s | %-9s | %-20s | %-9s | %-10s | %-10s \n");
            System.out.format(format, "Nº EMPLEADO", "NOMBRE", "OFICIO", "MANAGER", "FECHA ALTA", "SALARIO", "COMISION", "Nº DEPARTAMENTO");
            System.out.println("\t----------------------------------------------------------------------------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("EMPNO"), listado.getString("NOMBRE"), listado.getString("OFICIO"), listado.getString("MGR"), listado.getString("FECALTA"), listado.getString("SAL"), listado.getString("COMM"), listado.getString("DEPT_NO"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void filtrarEmpleDep() {
        Database db = new Database();
        Connection conexion = db.crearConexion();
        Statement s;

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Introducir departamento:");
            int entrada = sc.nextInt();
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM EMPLE WHERE DEPT_NO = ?");
            stmt.setInt(1, entrada);
            ResultSet listado = stmt.executeQuery();
            String format = ("\t%-12s | %-10s | %-12s | %-9s | %-20s | %-9s | %-10s | %-10s \n");
            System.out.format(format, "Nº EMPLEADO", "NOMBRE", "OFICIO", "MANAGER", "FECHA ALTA", "SALARIO", "COMISION", "Nº DEPARTAMENTO");
            System.out.println("\t----------------------------------------------------------------------------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("EMPNO"), listado.getString("NOMBRE"), listado.getString("OFICIO"), listado.getString("MGR"), listado.getString("FECALTA"), listado.getString("SAL"), listado.getString("COMM"), listado.getString("DEPT_NO"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void filtrarEmpleOficio() {
        Database db = new Database();
        Connection conexion = db.crearConexion();
        Statement s;

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Introducir oficio:");
            String entrada = sc.nextLine();
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM EMPLE WHERE UPPER(OFICIO) LIKE UPPER('%' || ? || '%') ORDER BY EMPNO");
            stmt.setString(1, entrada);
            ResultSet listado = stmt.executeQuery();
            
            String format = ("\t%-12s | %-10s | %-12s | %-9s | %-20s | %-9s | %-10s | %-10s \n");
            System.out.format(format, "Nº EMPLEADO", "NOMBRE", "OFICIO", "MANAGER", "FECHA ALTA", "SALARIO", "COMISION", "Nº DEPARTAMENTO");
            System.out.println("\t----------------------------------------------------------------------------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("EMPNO"), listado.getString("NOMBRE"), listado.getString("OFICIO"), listado.getString("MGR"), listado.getString("FECALTA"), listado.getString("SAL"), listado.getString("COMM"), listado.getString("DEPT_NO"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void añadirEmple() {
        Database db = new Database();
        Connection conexion = db.crearConexion();
        Statement s;

        try {
            Scanner sc = new Scanner(System.in);
            
            System.out.println("\nIntroducir los datos del nuevo empleado.");
            System.out.println("\tNombre:");
            String nombre = sc.nextLine();
            System.out.println("\tOficio:");
            String oficio = sc.nextLine();
            System.out.println("\tManager:");
            int mgr = sc.nextInt();
            System.out.println("\tSalario:");
            int salario = sc.nextInt();
            System.out.println("\tComisión:");
            int comision = sc.nextInt();
            System.out.println("\tNúmero de departamento:");
            int departamento = sc.nextInt();
            
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO EMPLE (EMPNO, NOMBRE, OFICIO, MGR, FECALTA, SAL, COMM, DEPT_NO) "
                    + "VALUES(((SELECT MAX(EMPNO) FROM EMPLE) + 1),?,?,?, SYSDATE,?,?,?)");
            stmt.setString(1, nombre);
            stmt.setString(2, oficio);
            stmt.setInt(3, mgr);
            stmt.setInt(4, salario);
            stmt.setInt(5, comision);
            stmt.setInt(6, departamento);
            stmt.execute();
                        
            System.out.println("");
            System.out.println("Empleado creado con éxito, mostrando datos:");
            PreparedStatement stmt2 = conexion.prepareStatement("SELECT * FROM EMPLE WHERE NOMBRE = ?");
            stmt2.setString(1, nombre);
            ResultSet listado = stmt2.executeQuery();
            String format = ("\t%-12s | %-10s | %-12s | %-9s | %-20s | %-9s | %-10s | %-10s \n");
            System.out.format(format, "Nº EMPLEADO", "NOMBRE", "OFICIO", "MANAGER", "FECHA ALTA", "SALARIO", "COMISION", "Nº DEPARTAMENTO");
            System.out.println("\t----------------------------------------------------------------------------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("EMPNO"), listado.getString("NOMBRE"), listado.getString("OFICIO"), listado.getString("MGR"), listado.getString("FECALTA"), listado.getString("SAL"), listado.getString("COMM"), listado.getString("DEPT_NO"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Opciones depart
    public static void mostrarDepart() {
        Database db = new Database();
        Connection conexion = db.crearConexion();
        Statement s;

        try {
            s = conexion.createStatement();
            ResultSet listado = s.executeQuery("SELECT * FROM DEPART");
            String format = ("\t%-18s | %-15s | %-18s \n");
            System.out.format(format, "Nº DEPARTAMENTO", "NOMBRE", "LOCALIZACIÓN");
            System.out.println("\t-----------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("DEPT_NO"), listado.getString("DNOMBRE"), listado.getString("LOC"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarDepart() {
        Database db = new Database();
        Connection conexion = db.crearConexion();
        Statement s;

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Introducir número de departamento:");
            int entrada = sc.nextInt();
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM DEPART WHERE DEPT_NO = ?");
            stmt.setInt(1, entrada);
            ResultSet listado = stmt.executeQuery();
            String format = ("\t%-18s | %-15s | %-18s \n");
            System.out.format(format, "Nº DEPARTAMENTO", "NOMBRE", "LOCALIZACIÓN");
            System.out.println("\t-----------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("DEPT_NO"), listado.getString("DNOMBRE"), listado.getString("LOC"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarDepartNombre() {
        Database db = new Database();
        Connection conexion = db.crearConexion();
        Statement s;

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Introducir nombre de departamento:");
            String entrada = sc.nextLine();
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM DEPART WHERE UPPER(DNOMBRE) LIKE UPPER('%' || ? || '%')");
            stmt.setString(1, entrada);
            ResultSet listado = stmt.executeQuery();
            String format = ("\t%-18s | %-15s | %-18s \n");
            System.out.format(format, "Nº DEPARTAMENTO", "NOMBRE", "LOCALIZACIÓN");
            System.out.println("\t-----------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("DEPT_NO"), listado.getString("DNOMBRE"), listado.getString("LOC"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void filtrarDepartLoc() {
        Database db = new Database();
        Connection conexion = db.crearConexion();
        Statement s;

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Introducir localización del departamento:");
            String entrada = sc.nextLine();
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM DEPART WHERE UPPER(LOC) LIKE UPPER('%' || ? || '%')");
            stmt.setString(1, entrada);
            ResultSet listado = stmt.executeQuery();
            String format = ("\t%-18s | %-15s | %-18s \n");
            System.out.format(format, "Nº DEPARTAMENTO", "NOMBRE", "LOCALIZACIÓN");
            System.out.println("\t-----------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("DEPT_NO"), listado.getString("DNOMBRE"), listado.getString("LOC"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Volver
    public static void enterParaVolver(){
        System.out.println("\nPress enter to return...");
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
