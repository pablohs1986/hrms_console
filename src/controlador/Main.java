/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.Scanner;
import modelo.*;

/**
 *
 * @author Pablo Herrero
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Opcion opcion = new Opcion();
        Scanner sc = new Scanner(System.in);
        int seleccionUsuario = 0;
        int seleccionUsuario2;

        System.out.println("*****************************************");
        System.out.println("******************HRMS*******************");
        System.out.println("*****************************************");
        System.out.println("");

        do {
            System.out.println("Select an option:");
            System.out.println("1. Employees");
            System.out.println("2. Departments");
            System.out.println("0. Exit");

            seleccionUsuario = sc.nextInt();

            switch (seleccionUsuario) {
                case 1:
                    do {
                        System.out.println("\nSelect an option:");
                        System.out.println("1. See all employees");
                        System.out.println("2. Search employee");
                        System.out.println("3. Filter by department");
                        System.out.println("4. Filter by job");
                        System.out.println("5. Add employee");
                        System.out.println("6. Update employee");
                        System.out.println("7. Delete employee");
                        System.out.println("0. Go back");

                        seleccionUsuario2 = sc.nextInt();

                        switch (seleccionUsuario2) {
                            case 1:
                                opcion.mostrarEmple();
                                opcion.enterParaVolver();
                                break;
                            case 2:
                                opcion.buscarEmple();
                                opcion.enterParaVolver();
                                break;
                            case 3:
                                opcion.filtrarEmpleDep();
                                opcion.enterParaVolver();
                                break;
                            case 4:
                                opcion.filtrarEmpleOficio();
                                opcion.enterParaVolver();
                                break;
                            case 5:
                                opcion.añadirEmple();
                                opcion.enterParaVolver();
                                break;
                            case 6:
                                opcion.actualizarEmple();
                                opcion.enterParaVolver();
                                break;
                            case 7:
                                opcion.eliminarEmple();
                                opcion.enterParaVolver();
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Opción no valida");
                        }
                    } while (seleccionUsuario2 != 0);
                    break;
                case 2:
                    do {
                        System.out.println("\nSelect an option:");
                        System.out.println("1. See all departments");
                        System.out.println("2. Filter by department location");
                        System.out.println("0. Go back");

                        seleccionUsuario2 = sc.nextInt();

                        switch (seleccionUsuario2) {
                            case 1:
                                opcion.mostrarDepart();
                                opcion.enterParaVolver();
                                break;
                            case 2:
                                opcion.filtrarDepartLoc();
                                opcion.enterParaVolver();
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Opción no valida");
                                break;
                        }

                    } while (seleccionUsuario2 != 0);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no valida");
            }

        } while (seleccionUsuario != 0);

    }

}
