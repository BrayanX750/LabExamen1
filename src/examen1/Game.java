/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen1;

import java.util.ArrayList;
import java.util.Calendar;

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
        
    }
    
    @Override
    public void ejecutarOpcion(int opcion){
        
    }

}
