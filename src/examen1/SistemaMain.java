/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class SistemaMain {
    private JFrame ventana;
    private ArrayList<RentItem> items;

    public SistemaMain() {
        items = new ArrayList<>();
        ventana = new JFrame("Rentas de Items");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(500, 380);
        ventana.setLocationRelativeTo(null);

        JButton btnAgregar = new JButton("Agregar Item");
        JButton btnRentar = new JButton("Rentar");
        JButton btnSubmenu = new JButton("Ejecutar Submenu");
        JButton btnImprimir = new JButton("Imprimir Todo");

        btnAgregar.addActionListener(e -> agregarItem());
        btnRentar.addActionListener(e -> rentarItem());
        btnSubmenu.addActionListener(e -> ejecutarSubmenu());
        btnImprimir.addActionListener(e -> imprimirTodo());

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(btnAgregar);
        panel.add(btnRentar);
        panel.add(btnSubmenu);
        panel.add(btnImprimir);

        ventana.setContentPane(panel);
        ventana.setVisible(true);
    }

    private void agregarItem() {
        String[] opciones = {"Movie", "Game"};
        String tipo = (String) JOptionPane.showInputDialog(ventana, "¿Qué desea agregar?", "Agregar",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (tipo == null) return;

        String codigo = JOptionPane.showInputDialog(ventana, "Codigo:");
        if (codigo == null || codigo.trim().isEmpty()) return;
        if (buscarPorCodigo(codigo) != null) {
            JOptionPane.showMessageDialog(ventana, "Codigo repetido. Intente otro.");
            return;
        }

        String nombre = JOptionPane.showInputDialog(ventana, "Nombre:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        double precio = 0.0;
        if (tipo.equals("Movie")) {
            String p = JOptionPane.showInputDialog(ventana, "Precio base por dia:");
            if (p == null || p.trim().isEmpty()) return;
            try { precio = Double.parseDouble(p); } catch (Exception ex) { return; }
        } else {
            precio = 20.0;
        }

        String rutaImagen = seleccionarImagen();
        if (rutaImagen == null) return;

        if (tipo.equals("Movie")) {
            Movie m = new Movie(codigo, nombre, precio, rutaImagen);
            m.setImagen(rutaImagen);
            items.add(m);
            JOptionPane.showMessageDialog(ventana, "Movie guardada.");
        } else {
            Game g = new Game(codigo, nombre, precio, rutaImagen);
            g.setImagen(rutaImagen);
            items.add(g);
            JOptionPane.showMessageDialog(ventana, "Game guardado.");
        }
    }

    private void rentarItem() {
        String codigo = JOptionPane.showInputDialog(ventana, "Codigo del item:");
        if (codigo == null || codigo.trim().isEmpty()) return;
        RentItem it = buscarPorCodigo(codigo);
        if (it == null) {
            JOptionPane.showMessageDialog(ventana, "Item No Existe");
            return;
        }
        String d = JOptionPane.showInputDialog(ventana, "Dias de renta:");
        if (d == null || d.trim().isEmpty()) return;
        int dias;
        try { dias = Integer.parseInt(d); } catch (Exception ex) { return; }
        double total = it.pagoRenta(dias);

        ImageIcon icon = cargarIcono(it.getImagen(), 160, 220);
        String txt = it.toString() + "\nTotal renta por " + dias + " día(s): " + total;
        JOptionPane.showMessageDialog(ventana, txt, "Renta",
                JOptionPane.INFORMATION_MESSAGE, icon);
    }

    private void ejecutarSubmenu() {
        String codigo = JOptionPane.showInputDialog(ventana, "Codigo del ítem:");
        if (codigo == null || codigo.trim().isEmpty()) return;
        RentItem it = buscarPorCodigo(codigo);
        if (it == null) {
            JOptionPane.showMessageDialog(ventana, "Item No Existe");
            return;
        }
        if (it instanceof MenuActions) {
            try {
                it.getClass().getMethod("submenu").invoke(it);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(ventana, "No se pudo abrir el submenu.");
            }
        } else {
            JOptionPane.showMessageDialog(ventana, "Este ítem no tiene submenu.");
        }
    }

    private void imprimirTodo() {
        JDialog dialogo = new JDialog(ventana, "Items Registrados", true);
        dialogo.setSize(760, 560);
        dialogo.setLocationRelativeTo(ventana);

        JPanel cont = new JPanel();
        cont.setLayout(new GridLayout(0, 3, 10, 10));
        cont.setBorder(new EmptyBorder(10, 10, 10, 10));

        for (RentItem it : items) {
            JPanel card = new JPanel();
            card.setLayout(new BorderLayout(5, 5));
            card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

            ImageIcon icon = cargarIcono(it.getImagen(), 160, 220);
            JLabel lblImg = new JLabel(icon);
            lblImg.setHorizontalAlignment(SwingConstants.CENTER);

            String nombre = it.getNombre();
            String precio = "Precio por dia: " + it.getPrecioBase();
            String l3 = "";
            String l4 = "";

            if (it instanceof Movie) {
                Movie m = (Movie) it;
                l3 = "Estado: " + m.getEstado();
                l4 = "Movie";
            } else if (it instanceof Game) {
                Game g = (Game) it;
                l3 = "Fecha pub: " + g.getFechaPublicacionTexto();
                l4 = "PS3 Game";
            }

            JPanel texto = new JPanel();
            texto.setLayout(new BoxLayout(texto, BoxLayout.Y_AXIS));
            JLabel a = new JLabel(nombre);
            JLabel b = new JLabel(precio);
            JLabel c = new JLabel(l3);
            JLabel d = new JLabel(l4);
            a.setFont(a.getFont().deriveFont(Font.BOLD));
            texto.add(a);
            texto.add(b);
            if (!l3.isEmpty()) texto.add(c);
            if (!l4.isEmpty()) texto.add(d);

            card.add(lblImg, BorderLayout.CENTER);
            card.add(texto, BorderLayout.SOUTH);
            cont.add(card);
        }

        JScrollPane scroll = new JScrollPane(cont);
        dialogo.setContentPane(scroll);
        dialogo.setVisible(true);
    }

    private RentItem buscarPorCodigo(String codigo) {
        for (RentItem x : items) {
            if (x.getCodigo().equalsIgnoreCase(codigo)) return x;
        }
        return null;
    }

    private String seleccionarImagen() {
        JFileChooser ch = new JFileChooser();
        int r = ch.showOpenDialog(ventana);
        if (r == JFileChooser.APPROVE_OPTION) {
            File f = ch.getSelectedFile();
            if (f != null) return f.getAbsolutePath();
        }
        return null;
    }

    private ImageIcon cargarIcono(String ruta, int w, int h) {
        if (ruta == null || ruta.trim().isEmpty()) return new ImageIcon();
        ImageIcon icon = new ImageIcon(ruta);
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaMain());
    }
}

