package entradas;

import javax.swing.*;
import java.awt.*;

public class Ventanas extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel panelPrincipal = new JPanel(cardLayout);

    // Botones menú principal
    private final JButton btnRegistrar = new JButton("Registrar Usuario");
    private final JButton btnEventos = new JButton("Mostrar Eventos");
    private final JButton btnComprar = new JButton("Comprar Entrada");
    private final JButton btnDisponibilidad = new JButton("Disponibilidad Evento");

    // Paneles secundarios
    private final JPanel panelMenu = new JPanel();
    private final JPanel panelRegistrar = new JPanel();
    private final JPanel panelEventos = new JPanel();
    private final JPanel panelComprar = new JPanel();
    private final JPanel panelDisponibilidad = new JPanel();

    // Dependencia del sistema
    private final SistemaEntradas sistema;

    public Ventanas(SistemaEntradas sistema) {
        this.sistema = sistema;

        setTitle("Sistema de Ventas de Entradas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ====== PANEL MENÚ PRINCIPAL ======
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));

        // Centramos cada botón horizontalmente
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEventos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnComprar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDisponibilidad.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Añadimos los botones con espacio arriba y abajo para centrar verticalmente
        panelMenu.add(Box.createVerticalGlue());
        panelMenu.add(btnRegistrar);
        panelMenu.add(Box.createVerticalStrut(10));
        panelMenu.add(btnEventos);
        panelMenu.add(Box.createVerticalStrut(10));
        panelMenu.add(btnComprar);
        panelMenu.add(Box.createVerticalStrut(10));
        panelMenu.add(btnDisponibilidad);
        panelMenu.add(Box.createVerticalGlue());

        // ====== PANEL REGISTRAR ======
        JTextField txtNombre = new JTextField(15);
        JTextField txtEdad = new JTextField(5);
        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver1 = new JButton("Volver");

        panelRegistrar.setLayout(new BoxLayout(panelRegistrar, BoxLayout.Y_AXIS));

        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblEdad = new JLabel("Edad:");

        // Centramos elementos del panel registrar
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtNombre.setMaximumSize(new Dimension(200, 25)); // más pequeño
        txtNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblEdad.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtEdad.setMaximumSize(new Dimension(100, 25));   // más pequeño
        txtEdad.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver1.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelRegistrar.add(Box.createVerticalGlue());
        panelRegistrar.add(lblNombre);
        panelRegistrar.add(txtNombre);
        panelRegistrar.add(Box.createVerticalStrut(10));
        panelRegistrar.add(lblEdad);
        panelRegistrar.add(txtEdad);
        panelRegistrar.add(Box.createVerticalStrut(20));
        panelRegistrar.add(btnGuardar);
        panelRegistrar.add(Box.createVerticalStrut(20));
        panelRegistrar.add(btnVolver1);
        panelRegistrar.add(Box.createVerticalGlue());

        // ====== PANEL EVENTOS ======
        DefaultListModel<String> modeloEventos = new DefaultListModel<>();
        JList<String> listaEventos = new JList<>(modeloEventos);
        JButton btnVolver2 = new JButton("Volver");
        panelEventos.setLayout(new BorderLayout());
        panelEventos.add(new JScrollPane(listaEventos), BorderLayout.CENTER);
        panelEventos.add(btnVolver2, BorderLayout.SOUTH);

        // ====== PANEL COMPRAR ======
        JButton btnComprarAhora = new JButton("Confirmar Compra");
        JButton btnVolver3 = new JButton("Volver");
        panelComprar.setLayout(new BoxLayout(panelComprar, BoxLayout.Y_AXIS));
        JLabel lblComprar = new JLabel("Seleccione un evento en 'Mostrar Eventos' primero");
        lblComprar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnComprarAhora.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver3.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelComprar.add(Box.createVerticalGlue());
        panelComprar.add(lblComprar);
        panelComprar.add(Box.createVerticalStrut(10));
        panelComprar.add(btnComprarAhora);
        panelComprar.add(Box.createVerticalStrut(20));
        panelComprar.add(btnVolver3);
        panelComprar.add(Box.createVerticalGlue());

        // ====== PANEL DISPONIBILIDAD ======
        JTextField txtEventoDisp = new JTextField(15);
        JButton btnConsultar = new JButton("Consultar");
        JButton btnVolver4 = new JButton("Volver");

        JLabel lblEvento = new JLabel("Nombre del evento:");
        lblEvento.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtEventoDisp.setMaximumSize(new Dimension(200, 25));
        txtEventoDisp.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConsultar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver4.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelDisponibilidad.setLayout(new BoxLayout(panelDisponibilidad, BoxLayout.Y_AXIS));
        panelDisponibilidad.add(Box.createVerticalGlue());
        panelDisponibilidad.add(lblEvento);
        panelDisponibilidad.add(txtEventoDisp);
        panelDisponibilidad.add(Box.createVerticalStrut(10));
        panelDisponibilidad.add(btnConsultar);
        panelDisponibilidad.add(Box.createVerticalStrut(20));
        panelDisponibilidad.add(btnVolver4);
        panelDisponibilidad.add(Box.createVerticalGlue());

        // Agregar pantallas al CardLayout
        panelPrincipal.add(panelMenu, "menu");
        panelPrincipal.add(panelRegistrar, "registrar");
        panelPrincipal.add(panelEventos, "eventos");
        panelPrincipal.add(panelComprar, "comprar");
        panelPrincipal.add(panelDisponibilidad, "disponibilidad");

        add(panelPrincipal);

        // ====== ACCIONES ======

        // Navegación
        btnRegistrar.addActionListener(e -> cardLayout.show(panelPrincipal, "registrar"));
        btnEventos.addActionListener(e -> {
            modeloEventos.clear();
            for (Eventos ev : sistema.getEventos()) {
                modeloEventos.addElement(ev.getNombre() + " | " + ev.getLugar() + " | " + ev.getFecha());
            }
            cardLayout.show(panelPrincipal, "eventos");
        });
        btnComprar.addActionListener(e -> cardLayout.show(panelPrincipal, "comprar"));
        btnDisponibilidad.addActionListener(e -> cardLayout.show(panelPrincipal, "disponibilidad"));

        btnVolver1.addActionListener(e -> cardLayout.show(panelPrincipal, "menu"));
        btnVolver2.addActionListener(e -> cardLayout.show(panelPrincipal, "menu"));
        btnVolver3.addActionListener(e -> cardLayout.show(panelPrincipal, "menu"));
        btnVolver4.addActionListener(e -> cardLayout.show(panelPrincipal, "menu"));

        // Funcionalidades
        btnGuardar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                int edad = Integer.parseInt(txtEdad.getText());
                sistema.registrarUsuarios(nombre, edad, "Tecnología,Deportes");
                JOptionPane.showMessageDialog(this, "Usuario registrado!");
                txtNombre.setText("");
                txtEdad.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Edad inválida.");
            }
        });

        btnComprarAhora.addActionListener(e -> {
            String usuario = JOptionPane.showInputDialog(this, "Ingrese nombre de usuario:");
            int idx = listaEventos.getSelectedIndex();
            if (idx >= 0) {
                Eventos ev = sistema.getEventos().get(idx);
                sistema.realizarVenta(usuario, ev.getNombre());
                JOptionPane.showMessageDialog(this, "Entrada comprada para: " + ev.getNombre());
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un evento en la lista de 'Mostrar Eventos' primero.");
            }
        });

        btnConsultar.addActionListener(e -> {
            String evento = txtEventoDisp.getText();
            if (evento != null && !evento.trim().isEmpty()) {
                sistema.mostrarUbicacionesDisponibles(evento);
                JOptionPane.showMessageDialog(this, "Disponibilidad mostrada en consola.");
            }
        });
    }

    public static void main(String[] args) {
        SistemaEntradas sistema = new SistemaEntradas();
        sistema.cargarDatosIniciales();
        SwingUtilities.invokeLater(() -> new Ventanas(sistema).setVisible(true));
    }
}
