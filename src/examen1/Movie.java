/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen1;


import java.util.Calendar;

public class Movie extends RentItem {
    private Calendar fechaEstreno;

    public Movie(String codigo, String nombre, double precioBase, String imagen, Calendar fecha) {
        super(codigo, nombre, precioBase, imagen);
        fechaEstreno = fecha;
    }

    public Calendar getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(Calendar fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public String getEstado() {
    Calendar hoy = Calendar.getInstance();

    int anioHoy = hoy.get(Calendar.YEAR);
    int mesHoy = hoy.get(Calendar.MONTH);
    int diaHoy = hoy.get(Calendar.DAY_OF_MONTH);

    int anioEstreno = fechaEstreno.get(Calendar.YEAR);
    int mesEstreno = fechaEstreno.get(Calendar.MONTH);
    int diaEstreno = fechaEstreno.get(Calendar.DAY_OF_MONTH);

    int diferenciaMeses = (anioHoy - anioEstreno) * 12 + (mesHoy - mesEstreno);

    if (diferenciaMeses < 3 || (diferenciaMeses == 3 && diaHoy - diaEstreno <= 0)) {
        return "ESTRENO";
    } else {
        return "NORMAL";
    }
}

    @Override
    public String toString() {
        return super.toString() + ", Estado: " + getEstado() + " – Movie";
    }

    @Override
    public double pagoRenta(int dias) { 
        double total = getPrecioBase() * dias;
        String estado = getEstado();
        if (estado.equals("ESTRENO") && dias > 2) {
            int extras = dias - 2;
            total += 50 * extras;
        } else if (estado.equals("NORMAL") && dias > 5) {
            int extras = dias - 5;
            total += 30 * extras;
        }
        return total;
    }
}
