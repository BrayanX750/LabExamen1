/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen1;


import java.util.Calendar;
import java.util.Date;

public class Movie extends RentItem {
    private Date fechaEstreno;

    public Movie(String codigo, String nombre, double precioBase, String imagen) {
        super(codigo, nombre, precioBase, imagen);
        this.fechaEstreno = Calendar.getInstance().getTime();
    }

    public Date getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(Date fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public String getEstado() {
        Date hoy = Calendar.getInstance().getTime();
        long diferencia = hoy.getTime() - fechaEstreno.getTime();
        long dias = diferencia / (1000 * 60 * 60 * 24);
        long meses = dias / 30;
        if (meses <= 3) {
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
