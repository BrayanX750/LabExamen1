/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen1;


public abstract class RentItem {
    protected String codigo;
    protected String nombre;
    protected double precioBase;
    protected int copias;
    protected String imagen;

    public RentItem(String codigo, String nombre, double precioBase, String imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.copias = 0;
        this.imagen = "";
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public int getCopias() {
        return copias;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + ", Nombre: " + nombre + ", Precio base: " + precioBase;
    }

    public abstract double pagoRenta(int dias);
}














