/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen1;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SistemaMain {
    private JFrame ventana;
    private ArrayList<RentItem> items;

    public SistemaMain() {
        items = new ArrayList<>();
        ventana = new JFrame("Videoclub");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(600, 450);
        ventana.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Videoclub", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.BLACK);
        titulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(4, 1, 15, 15));
        botones.setBackground(Color.WHITE);
        botones.setBorder(new EmptyBorder(20, 50, 20, 50));

        JButton btnAgregar = new JButton("Agregar Item");
        JButton btnRentar = new JButton("Rentar");
        JButton btnSubmenu = new JButton("Ejecutar Submenu");
        JButton btnImprimir = new JButton("Imprimir Todo");
        JButton btnSalir = new JButton("Salir");

        JButton[] botonesArray = {btnAgregar, btnRentar, btnSubmenu, btnImprimir};
        for (JButton b : botonesArray) {
            b.setBackground(Color.BLACK);
            b.setForeground(Color.WHITE);
            b.setFont(new Font("Arial", Font.BOLD, 16));
            b.setFocusPainted(false);
            botones.add(b);
        }

        JPanel salirPanel = new JPanel();
        salirPanel.setBackground(Color.WHITE);
        btnSalir.setBackground(Color.BLACK);
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 16));
        btnSalir.setFocusPainted(false);
        salirPanel.add(btnSalir);

        btnAgregar.addActionListener(e -> agregarItem());
        btnRentar.addActionListener(e -> rentarItem());
        btnSubmenu.addActionListener(e -> ejecutarSubmenu());
        btnImprimir.addActionListener(e -> imprimirTodo());
        btnSalir.addActionListener(e -> System.exit(0));

        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setBackground(Color.WHITE);
        panelBotones.add(botones, BorderLayout.CENTER);
        panelBotones.add(salirPanel, BorderLayout.SOUTH);

        panel.add(panelBotones, BorderLayout.CENTER);

        ventana.setContentPane(panel);
        ventana.setVisible(true);
    }

    private void agregarItem() {
        String[] opciones = {"Movie", "Game"};
        String tipo = (String) JOptionPane.showInputDialog(
                ventana, "¿Que desea agregar?", "Agregar",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (tipo == null) return;

        String codigo = null;
        while (true) {
            codigo = JOptionPane.showInputDialog(ventana, "Codigo:");
            if (codigo == null) return;
            codigo = codigo.trim();
            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "Codigo vacio. Intente otro.");
                continue;
            }
            if (buscarPorCodigo(codigo) != null) {
                JOptionPane.showMessageDialog(ventana, "Codigo repetido. Intente otro.");
                continue;
            }
            break;
        }

        String nombre = JOptionPane.showInputDialog(ventana, "Nombre:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        double precio = 0.0;
        if (tipo.equals("Movie")) {
            while (true) {
                String p = JOptionPane.showInputDialog(ventana, "Precio base por dia:");
                if (p == null) return;
                p = p.trim();
                if (p.isEmpty()) {
                    JOptionPane.showMessageDialog(ventana, "Precio vacio. Intente de nuevo.");
                    continue;
                }
                try {
                    precio = Double.parseDouble(p);
                    break;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ventana, "Precio invalido. Ingrese un numero.");
                }
            }
        } else {
            precio = 20.0;
        }

        String rutaImagen = seleccionarImagen();
        if (rutaImagen == null) return;

        if (tipo.equals("Movie")) {
            JDateChooser dateChooser = new JDateChooser();
            dateChooser.setDateFormatString("dd/MM/yyyy");
            int opcion = JOptionPane.showConfirmDialog(
                    ventana, dateChooser, "Seleccione fecha de estreno",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (opcion != JOptionPane.OK_OPTION) return;

            Calendar fechaEstreno = Calendar.getInstance();
            fechaEstreno.setTime(dateChooser.getDate());

            Movie m = new Movie(codigo, nombre, precio, rutaImagen, fechaEstreno);
            m.setImagen(rutaImagen);
            items.add(m);
            JOptionPane.showMessageDialog(ventana, "Movie guardada.");
        } else {
            JDateChooser dateChooser = new JDateChooser();
            dateChooser.setDateFormatString("dd/MM/yyyy");
            int opcion = JOptionPane.showConfirmDialog(
                    ventana, dateChooser, "Seleccione fecha de publicacion",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (opcion != JOptionPane.OK_OPTION) return;

            Calendar fechaEstreno = Calendar.getInstance();
            fechaEstreno.setTime(dateChooser.getDate());
            
            
            Game g = new Game(codigo, nombre, precio, rutaImagen, fechaEstreno);
            g.setImagen(rutaImagen);
            items.add(g);
            JOptionPane.showMessageDialog(ventana, "Game guardado.");
        }
    }

    private void rentarItem() {
        String codigo = JOptionPane.showInputDialog(ventana, "Codigo del item:");
        if (codigo == null || codigo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Codigo invalido.");
            return;
        }

        RentItem it = buscarPorCodigo(codigo);
        if (it == null) {
            JOptionPane.showMessageDialog(ventana, "Item no existe");
            return;
        }


        JPanel panel = new JPanel(new BorderLayout(10, 10));

        ImageIcon icon = cargarIcono(it.getImagen(), 120, 160);
        JLabel lblImagen = new JLabel(icon);
        panel.add(lblImagen, BorderLayout.WEST);

        JTextArea info = new JTextArea(it.toString());
        info.setEditable(false);
        info.setOpaque(false);
        panel.add(info, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Dias de renta:"));
        JTextField txtDias = new JTextField(5);
        inputPanel.add(txtDias);
        panel.add(inputPanel, BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(
                ventana, panel, "Rentar Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String d = txtDias.getText().trim();
            if (d.isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "Debe ingresar los dias de renta.");
                return;
            }

            int dias;
            try {
                dias = Integer.parseInt(d);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "Ingrese un numero valido.");
                return;
            }

            double total = it.pagoRenta(dias);

            JOptionPane.showMessageDialog(ventana,
                    "Total renta por " + dias + " dia(s): " + total,
                    "Renta confirmada",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void ejecutarSubmenu() {
        String codigo = JOptionPane.showInputDialog(ventana, "Codigo del item:");
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
            JOptionPane.showMessageDialog(ventana, "Este item no tiene submenu.");
        }
    }

    private void imprimirTodo() {
        JDialog dialogo = new JDialog(ventana, "Items Registrados", true);
        dialogo.setSize(760, 560);
        dialogo.setLocationRelativeTo(ventana);

        JPanel cont = new JPanel();
        cont.setLayout(new GridLayout(0, 3, 10, 10));
        cont.setBorder(new EmptyBorder(10, 10, 10, 10));

        if (items.isEmpty()) {
            JPanel card = new JPanel();
            card.setLayout(new BorderLayout(5,5));
            card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

            JLabel mensaje = new JLabel("No hay items registrados", SwingConstants.CENTER);
            mensaje.setFont(new Font("Arial", Font.BOLD, 18));
            card.add(mensaje, BorderLayout.CENTER);

            cont.add(card);
        } else {
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

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Imagenes (JPG, PNG)", "jpg", "jpeg", "png");
        ch.setFileFilter(filter);

        int r = ch.showOpenDialog(ventana);
        if (r == JFileChooser.APPROVE_OPTION) {
            File f = ch.getSelectedFile();
            if (f != null) {
                String nombre = f.getName().toLowerCase();
                if (nombre.endsWith(".jpg") || nombre.endsWith(".jpeg") || nombre.endsWith(".png")) {
                    return f.getAbsolutePath();
                } else {
                    JOptionPane.showMessageDialog(ventana,
                            "Tipo de archivo invalido, seleccione un JPG o PNG.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
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

