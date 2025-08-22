/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen1;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;

public class Game extends RentItem implements MenuActions {
    Calendar fechaPub;
    ArrayList<String> specs;

    public Game(String code, String name, Double price, String img) {
        super(code, name, price, img);
        fechaPub = Calendar.getInstance();
        specs = new ArrayList<>();
    }

    private String fechaComoTexto() {
        int y = fechaPub.get(Calendar.YEAR);
        int m = fechaPub.get(Calendar.MONTH) + 1;
        int d = fechaPub.get(Calendar.DAY_OF_MONTH);
        String mm = (m < 10 ? "0" + m : "" + m);
        String dd = (d < 10 ? "0" + d : "" + d);
        return y + "-" + mm + "-" + dd;
    }

    public void setFechaPublicacion(int year, int mes, int dia) {
        fechaPub.set(year, mes - 1, dia);
    }

    public String getFechaPublicacionTexto() {
        return fechaComoTexto();
    }

    public void listEspecificaciones() {
        String texto = construirSpecs(0, new StringBuilder()).toString();
        if (texto.isEmpty()) texto = "(sin especificaciones)";
        JOptionPane.showMessageDialog(null, texto);
    }

    private StringBuilder construirSpecs(int i, StringBuilder sb) {
        if (i < specs.size()) {
            sb.append("- ").append(specs.get(i)).append("\n");
            construirSpecs(i + 1, sb);
        }
        return sb;
    }

    @Override
    public double pagoRenta(int dias) {
        return 20.0 * dias;
    }

    @Override
    public String toString() {
        return super.toString() + ", Fecha pub: " + fechaComoTexto() + " – PS3 Game";
    }

    public void submenu() {
        String menu = "SUBMENU\n1. Actualizar fecha de publicacion\n2. Agregar especificacion\n3. Ver especificaciones\n4. Salir";
        int opcion;
        do {
            try {
                opcion = Integer.parseInt(JOptionPane.showInputDialog(null, menu, "Submenu", JOptionPane.QUESTION_MESSAGE));
                ejecutarOpcion(opcion);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número valido.");
                opcion = 0;
            }
        } while (opcion != 4);
    }

    @Override
    public void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> {
                int anio = Integer.parseInt(JOptionPane.showInputDialog("Año:"));
                int mes = Integer.parseInt(JOptionPane.showInputDialog("Mes (1-12):"));
                int dia = Integer.parseInt(JOptionPane.showInputDialog("Día:"));
                setFechaPublicacion(anio, mes, dia);
                JOptionPane.showMessageDialog(null, "Fecha actualizada: " + fechaComoTexto());
            }
            case 2 -> {
                String espec = JOptionPane.showInputDialog("Especificacion:");
                if (espec != null && !espec.trim().isEmpty()) {
                    specs.add(espec.trim());
                    JOptionPane.showMessageDialog(null, "Especificacion agregada.");
                }
            }
            case 3 -> listEspecificaciones();
            case 4 -> JOptionPane.showMessageDialog(null, "Saliendo del submenu...");
            default -> JOptionPane.showMessageDialog(null, "Opcion invalida.");
        }
    }
}
