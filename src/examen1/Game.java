/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen1;

import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;


/**
 *
 * @author ljmc2
 */
public class Game extends RentItem implements MenuActions{

    Calendar fechaPub;
    ArrayList<String> specs;

    public Game(String code, String name, Double price, String img) {
        super(code, name, price, img);
        fechaPub = Calendar.getInstance();
        specs = new ArrayList<>();
    }

    @Override
    public String toString() {
        return (super.toString() + "Fecha de publicacion: " + fechaPub + "- PS3 Game");
    }

    public void setFechaPublicacion(int year, int mes, int dia) {
        fechaPub.set(year, mes - 1, dia);
    }

    public void listEspecificaciones() {
        listEspecificacionesRec(0);
    }

    private void listEspecificacionesRec(int index) {
        if (index < specs.size()) {
            System.out.println("- " + specs.get(index));
            listEspecificacionesRec(index + 1);
        }
    }
    
    @Override
    public double pagoRenta(int dias) {
        return 20.0 * dias;
    }
    
    @Override
    public void submenu(){
        String menu = """
                SUBMENÚ
                1. Actualizar fecha de publicación
                2. Agregar especificación
                3. Ver especificaciones
                4. Salir
                """;

        int opcion;
        do {
            try {
                opcion = Integer.parseInt(JOptionPane.showInputDialog(null, menu, "Submenú", JOptionPane.QUESTION_MESSAGE));
                ejecutarOpcion(opcion);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido.");
                opcion = 0;
            }
        } while (opcion != 4);
    }
    
    @Override
    public void ejecutarOpcion(int opcion){
        switch (opcion) {
            case 1 -> {
                int anio = Integer.parseInt(JOptionPane.showInputDialog("Ingrese año:"));
                int mes = Integer.parseInt(JOptionPane.showInputDialog("Ingrese mes (1-12):"));
                int dia = Integer.parseInt(JOptionPane.showInputDialog("Ingrese día:"));
                setFechaPublicacion(anio, mes, dia);
                JOptionPane.showMessageDialog(null, "Fecha actualizada: " + fechaPub);
            }
            case 2 -> {
                String espec = JOptionPane.showInputDialog("Ingrese especificación:");
                specs.add(espec);
                JOptionPane.showMessageDialog(null, "Especificación agregada.");
            }
            case 3 -> listEspecificaciones();
            case 4 -> JOptionPane.showMessageDialog(null, "Saliendo del submenú...");
            default -> JOptionPane.showMessageDialog(null, "Opción inválida.");
        }
    }

}
