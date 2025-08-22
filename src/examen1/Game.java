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

    public Game(String code, String name, Double price, String img, Calendar fecha) {
        super(code, name, price, img);
        fechaPub = fecha;
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
                JOptionPane.showMessageDialog(null, "Ingrese un numero valido.");
                opcion = 0;
            }
        } while (opcion != 4);
    }

    @Override
    public void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> {
                try {
                    com.toedter.calendar.JDateChooser dateChooser = new com.toedter.calendar.JDateChooser();
                    dateChooser.setDateFormatString("dd/MM/yyyy");

                    int result = JOptionPane.showConfirmDialog(null, dateChooser, 
                            "Seleccione fecha de publicacion", JOptionPane.OK_CANCEL_OPTION, 
                            JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        java.util.Date fecha = dateChooser.getDate();
                        if (fecha != null) {
                            java.util.Calendar cal = java.util.Calendar.getInstance();
                            cal.setTime(fecha);

                            setFechaPublicacion(cal.get(Calendar.YEAR),
                                                cal.get(Calendar.MONTH) + 1,
                                                cal.get(Calendar.DAY_OF_MONTH));

                            JOptionPane.showMessageDialog(null, "Fecha actualizada: " + fechaComoTexto());
                        } else {
                            JOptionPane.showMessageDialog(null, "No se selecciono ninguna fecha.");
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al abrir el selector de fecha.");
                }
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
