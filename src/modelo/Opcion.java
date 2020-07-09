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
    public static void mostrarEmple() {
        Database db = new Database();
        Connection conexion = db.crearConexion();
        Statement s;

        try {
            s = conexion.createStatement();
            ResultSet listado = s.executeQuery("SELECT * FROM EMPLE");
            String format = ("\t%-12s | %-10s | %-12s | %-9s | %-20s | %-9s | %-10s | %-10s \n");
            System.out.format(format, "EMPLOYEE NUM", "NAME", "JOB", "MANAGER", "START DATE", "SALARY", "COMMISSION", "DEPARTMENT NUM");
            System.out.println("\t----------------------------------------------------------------------------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("EMPNO"), listado.getString("NOMBRE"), listado.getString("OFICIO"), listado.getString("MGR"), listado.getString("FECALTA"), listado.getString("SAL"), listado.getString("COMM"), listado.getString("DEPT_NO"));
            }
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void buscarEmple() {
        Database db = new Database();
        Connection conexion = db.crearConexion();

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Introducir número de empleado:");
            int entrada = sc.nextInt();
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM EMPLE WHERE EMPNO = ?");
            stmt.setInt(1, entrada);
            ResultSet listado = stmt.executeQuery();
            String format = ("\t%-12s | %-10s | %-12s | %-9s | %-20s | %-9s | %-10s | %-10s \n");
            System.out.format(format, "EMPLOYEE NUM", "NAME", "JOB", "MANAGER", "START DATE", "SALARY", "COMMISSION", "DEPARTMENT NUM");
            System.out.println("\t----------------------------------------------------------------------------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("EMPNO"), listado.getString("NOMBRE"), listado.getString("OFICIO"), listado.getString("MGR"), listado.getString("FECALTA"), listado.getString("SAL"), listado.getString("COMM"), listado.getString("DEPT_NO"));
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void filtrarEmpleDep() {
        Database db = new Database();
        Connection conexion = db.crearConexion();

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter department:");
            int entrada = sc.nextInt();
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM EMPLE WHERE DEPT_NO = ?");
            stmt.setInt(1, entrada);
            ResultSet listado = stmt.executeQuery();
            String format = ("\t%-12s | %-10s | %-12s | %-9s | %-20s | %-9s | %-10s | %-10s \n");
            System.out.format(format, "EMPLOYEE NUM", "NAME", "JOB", "MANAGER", "START DATE", "SALARY", "COMMISSION", "DEPARTMENT NUM");
            System.out.println("\t----------------------------------------------------------------------------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("EMPNO"), listado.getString("NOMBRE"), listado.getString("OFICIO"), listado.getString("MGR"), listado.getString("FECALTA"), listado.getString("SAL"), listado.getString("COMM"), listado.getString("DEPT_NO"));
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void filtrarEmpleOficio() {
        Database db = new Database();
        Connection conexion = db.crearConexion();

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter job:");
            String entrada = sc.nextLine();
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM EMPLE WHERE UPPER(OFICIO) LIKE UPPER('%' || ? || '%') ORDER BY EMPNO");
            stmt.setString(1, entrada);
            ResultSet listado = stmt.executeQuery();

            String format = ("\t%-12s | %-10s | %-12s | %-9s | %-20s | %-9s | %-10s | %-10s \n");
            System.out.format(format, "EMPLOYEE NUM", "NAME", "JOB", "MANAGER", "START DATE", "SALARY", "COMMISSION", "DEPARTMENT NUM");
            System.out.println("\t----------------------------------------------------------------------------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("EMPNO"), listado.getString("NOMBRE"), listado.getString("OFICIO"), listado.getString("MGR"), listado.getString("FECALTA"), listado.getString("SAL"), listado.getString("COMM"), listado.getString("DEPT_NO"));
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void añadirEmple() {
        Database db = new Database();
        Connection conexion = db.crearConexion();

        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("\nEnter the details of the new employee.");
            System.out.println("\tName:");
            String nombre = sc.nextLine();
            System.out.println("\tJob:");
            String oficio = sc.nextLine();
            System.out.println("\tManager:");
            int mgr = sc.nextInt();
            System.out.println("\tSalary:");
            int salario = sc.nextInt();
            System.out.println("\tCommission:");
            int comision = sc.nextInt();
            System.out.println("\tDepartment number:");
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
            stmt.close();

            System.out.println("");
            System.out.println("Employee successfully created, displaying data:");
            PreparedStatement stmt2 = conexion.prepareStatement("SELECT * FROM EMPLE WHERE NOMBRE = ?");
            stmt2.setString(1, nombre);
            ResultSet listado = stmt2.executeQuery();
            String format = ("\t%-12s | %-10s | %-12s | %-9s | %-20s | %-9s | %-10s | %-10s \n");
            System.out.format(format, "EMPLOYEE NUM", "NAME", "JOB", "MANAGER", "START DATE", "SALARY", "COMMISSION", "DEPARTMENT NUM");
            System.out.println("\t----------------------------------------------------------------------------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("EMPNO"), listado.getString("NOMBRE"), listado.getString("OFICIO"), listado.getString("MGR"), listado.getString("FECALTA"), listado.getString("SAL"), listado.getString("COMM"), listado.getString("DEPT_NO"));
            }
            stmt2.close();
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void actualizarEmple() {
        Database db = new Database();
        Connection conexion = db.crearConexion();
        int seleccionUsuario;
        int numEmple;

        try {
            Scanner sc = new Scanner(System.in);
            do{
                System.out.println("\nEnter the number of the employee you want to update:");
                numEmple = sc.nextInt();

                System.out.println("\nSelect the field you want to update:");
                System.out.println("1. Name");
                System.out.println("2. Job");
                System.out.println("3. Manager");
                System.out.println("4. Salary");
                System.out.println("5. Commission");
                System.out.println("6. Department number");
                System.out.println("0. Go back");
                seleccionUsuario = sc.nextInt();
                sc.nextLine();

                switch (seleccionUsuario) {
                    case 1:
                        System.out.println("Enter the new name:");
                        String nuevoNombre = sc.nextLine();
                        PreparedStatement stmt1 = conexion.prepareStatement("UPDATE EMPLE SET NOMBRE=? WHERE EMPNO = ?");
                        stmt1.setString(1, nuevoNombre);
                        stmt1.setInt(2, numEmple);
                        stmt1.execute();
                        stmt1.close();
                        break;
                    case 2:
                        System.out.println("Enter the new job:");
                        String nuevoOficio = sc.nextLine();
                        PreparedStatement stmt2 = conexion.prepareStatement("UPDATE EMPLE SET OFICIO=? WHERE EMPNO = ?");
                        stmt2.setString(1, nuevoOficio);
                        stmt2.setInt(2, numEmple);
                        stmt2.execute();
                        stmt2.close();
                        break;
                    case 3:
                        System.out.println("Enter the new manager:");
                        int nuevoManager = sc.nextInt();
                        PreparedStatement stmt3 = conexion.prepareStatement("UPDATE EMPLE SET MGR=? WHERE EMPNO = ?");
                        stmt3.setInt(1, nuevoManager);
                        stmt3.setInt(2, numEmple);
                        stmt3.execute();
                        stmt3.close();
                        break;
                    case 4:
                        System.out.println("Enter the new salary:");
                        int nuevoSalario = sc.nextInt();
                        PreparedStatement stmt4 = conexion.prepareStatement("UPDATE EMPLE SET SAL=? WHERE EMPNO = ?");
                        stmt4.setInt(1, nuevoSalario);
                        stmt4.setInt(2, numEmple);
                        stmt4.execute();
                        stmt4.close();
                        break;
                    case 5:
                        System.out.println("Enter the new comission:");
                        int nuevoComision = sc.nextInt();
                        PreparedStatement stmt5 = conexion.prepareStatement("UPDATE EMPLE SET COMM=? WHERE EMPNO = ?");
                        stmt5.setInt(1, nuevoComision);
                        stmt5.setInt(2, numEmple);
                        stmt5.execute();
                        stmt5.close();
                        break;
                    case 6:
                        System.out.println("Enter the new department:");
                        int nuevoDep = sc.nextInt();
                        PreparedStatement stmt6 = conexion.prepareStatement("UPDATE EMPLE SET DEPT_NO=? WHERE EMPNO = ?");
                        stmt6.setInt(1, nuevoDep);
                        stmt6.setInt(2, numEmple);
                        stmt6.execute();
                        stmt6.close();
                        break;
                }
                System.out.println("");
                System.out.println("Employee successfully updated, displaying data:");
                PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM EMPLE WHERE EMPNO = ?");
                stmt.setInt(1, numEmple);
                ResultSet listado = stmt.executeQuery();
                String format = ("\t%-12s | %-10s | %-12s | %-9s | %-20s | %-9s | %-10s | %-10s \n");
                System.out.format(format, "EMPLOYEE NUM", "NAME", "JOB", "MANAGER", "START DATE", "SALARY", "COMMISSION", "DEPARTMENT NUM");
                System.out.println("\t----------------------------------------------------------------------------------------------------------------------");
                while (listado.next()) {
                    System.out.format(format, listado.getString("EMPNO"), listado.getString("NOMBRE"), listado.getString("OFICIO"), listado.getString("MGR"), listado.getString("FECALTA"), listado.getString("SAL"), listado.getString("COMM"), listado.getString("DEPT_NO"));
                }
                stmt.close();
                
                System.out.println("Do you want to update another employee? (Y/N)");
                String preguntaSalida = sc.nextLine();
                if (preguntaSalida.equalsIgnoreCase("N")){
                    seleccionUsuario = 0;
                }
            } while (seleccionUsuario != 0);
            
            

        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void eliminarEmple() {
        Database db = new Database();
        Connection conexion = db.crearConexion();

        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("\nEnter the number of the employee you want to delete:");
            int empleAeliminar = sc.nextInt();

            PreparedStatement stmt = conexion.prepareStatement("DELETE FROM EMPLE WHERE EMPNO = ?");
            stmt.setInt(1, empleAeliminar);
            stmt.execute();
            stmt.close();

            System.out.println("");
            System.out.println("Employee successfully deleted.");
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
            System.out.format(format, "DEPARTMENT NUM", "NAME", "LOCATION");
            System.out.println("\t-----------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("DEPT_NO"), listado.getString("DNOMBRE"), listado.getString("LOC"));
            }
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void buscarDepart() {
        Database db = new Database();
        Connection conexion = db.crearConexion();

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter department number:");
            int entrada = sc.nextInt();
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM DEPART WHERE DEPT_NO = ?");
            stmt.setInt(1, entrada);
            ResultSet listado = stmt.executeQuery();
            String format = ("\t%-18s | %-15s | %-18s \n");
            System.out.format(format, "DEPARTMENT NUM", "NAME", "LOCATION");
            System.out.println("\t-----------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("DEPT_NO"), listado.getString("DNOMBRE"), listado.getString("LOC"));
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void buscarDepartNombre() {
        Database db = new Database();
        Connection conexion = db.crearConexion();

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter department name:");
            String entrada = sc.nextLine();
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM DEPART WHERE UPPER(DNOMBRE) LIKE UPPER('%' || ? || '%')");
            stmt.setString(1, entrada);
            ResultSet listado = stmt.executeQuery();
            String format = ("\t%-18s | %-15s | %-18s \n");
            System.out.format(format, "DEPARTMENT NUM", "NAME", "LOCATION");
            System.out.println("\t-----------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("DEPT_NO"), listado.getString("DNOMBRE"), listado.getString("LOC"));
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void filtrarDepartLoc() {
        Database db = new Database();
        Connection conexion = db.crearConexion();

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter location of the department:");
            String entrada = sc.nextLine();
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM DEPART WHERE UPPER(LOC) LIKE UPPER('%' || ? || '%')");
            stmt.setString(1, entrada);
            ResultSet listado = stmt.executeQuery();
            String format = ("\t%-18s | %-15s | %-18s \n");
            System.out.format(format, "DEPARTMENT NUM", "NAME", "LOCATION");
            System.out.println("\t-----------------------------------------------------");
            while (listado.next()) {
                System.out.format(format, listado.getString("DEPT_NO"), listado.getString("DNOMBRE"), listado.getString("LOC"));
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Volver
    public static void enterParaVolver() {
        System.out.println("\nPress enter to return...");
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(Opcion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
