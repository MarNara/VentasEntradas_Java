package entradas;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ventanas extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel panelPrincipal = new JPanel(cardLayout);

    // ====== BOTONES MENÚ USUARIO ======
    private final JButton btnRegistrar = new JButton("Registrar Usuario");
    private final JButton btnEventos = new JButton("Mostrar Eventos");
    private final JButton btnComprar = new JButton("Comprar Entrada");
    private final JButton btnDisponibilidad = new JButton("Disponibilidad Evento");

    // ====== BOTONES MENÚ OPERADOR ======
    private final JButton btnRegistrarEvento = new JButton("Registrar Evento");
    private final JButton btnMostrarUsuarios = new JButton("Mostrar Usuarios");
    private final JButton btnMostrarVentas = new JButton("Mostrar Ventas");

    // ====== PANELES ======
    private final JPanel panelInicio = new JPanel();       // menú principal de roles
    private final JPanel panelMenuUsuario = new JPanel();  // menú usuario
    private final JPanel panelRegistrar = new JPanel();
    private final JPanel panelEventos = new JPanel();
    private final JPanel panelComprar = new JPanel();
    private final JPanel panelDisponibilidad = new JPanel();
    private final JPanel panelMenuOperador = new JPanel(); // menú operador

    // Mostrar usuarios y ventas
    private final DefaultListModel<String> modeloUsuarios = new DefaultListModel<>();
    private final JList<String> listaUsuarios = new JList<>(modeloUsuarios);
    private final JTextField txtFiltroInteres = new JTextField(15);

    private final DefaultListModel<String> modeloVentas = new DefaultListModel<>();
    private final JList<String> listaVentas = new JList<>(modeloVentas);

    // Dependencia del sistema
    private final SistemaEntradas sistema;

    public Ventanas(SistemaEntradas sistema) {
        this.sistema = sistema;

        setTitle("Sistema de Ventas de Entradas");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ====== PANEL INICIO ======
        JButton btnUsuario = new JButton("Menú Usuario");
        JButton btnOperador = new JButton("Menú Operador");
        JButton btnSalir = new JButton("Salir");

        panelInicio.setLayout(new BoxLayout(panelInicio, BoxLayout.Y_AXIS));
        btnUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOperador.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelInicio.add(Box.createVerticalGlue());
        panelInicio.add(btnUsuario);
        panelInicio.add(Box.createVerticalStrut(10));
        panelInicio.add(btnOperador);
        panelInicio.add(Box.createVerticalStrut(10));
        panelInicio.add(btnSalir);
        panelInicio.add(Box.createVerticalGlue());

        // ====== MENÚ USUARIO ======
        panelMenuUsuario.setLayout(new BoxLayout(panelMenuUsuario, BoxLayout.Y_AXIS));
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEventos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnComprar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDisponibilidad.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnVolverUsuario = new JButton("Volver");
        btnVolverUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelMenuUsuario.add(Box.createVerticalGlue());
        panelMenuUsuario.add(btnRegistrar);
        panelMenuUsuario.add(Box.createVerticalStrut(10));
        panelMenuUsuario.add(btnEventos);
        panelMenuUsuario.add(Box.createVerticalStrut(10));
        panelMenuUsuario.add(btnComprar);
        panelMenuUsuario.add(Box.createVerticalStrut(10));
        panelMenuUsuario.add(btnDisponibilidad);
        panelMenuUsuario.add(Box.createVerticalStrut(20));
        panelMenuUsuario.add(btnVolverUsuario);
        panelMenuUsuario.add(Box.createVerticalGlue());

        // ====== PANEL REGISTRAR USUARIO ======
        JTextField txtNombre = new JTextField(15);
        JTextField txtEdad = new JTextField(5);
        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver1 = new JButton("Volver");

        panelRegistrar.setLayout(new BoxLayout(panelRegistrar, BoxLayout.Y_AXIS));
        panelRegistrar.add(Box.createVerticalGlue());
        panelRegistrar.add(new JLabel("Nombre:"));
        txtNombre.setMaximumSize(new Dimension(200, 25));
        panelRegistrar.add(txtNombre);
        panelRegistrar.add(Box.createVerticalStrut(10));
        panelRegistrar.add(new JLabel("Edad:"));
        txtEdad.setMaximumSize(new Dimension(100, 25));
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

        panelDisponibilidad.setLayout(new BoxLayout(panelDisponibilidad, BoxLayout.Y_AXIS));
        panelDisponibilidad.add(Box.createVerticalGlue());
        panelDisponibilidad.add(new JLabel("Nombre del evento:"));
        txtEventoDisp.setMaximumSize(new Dimension(200, 25));
        panelDisponibilidad.add(txtEventoDisp);
        panelDisponibilidad.add(Box.createVerticalStrut(10));
        panelDisponibilidad.add(btnConsultar);
        panelDisponibilidad.add(Box.createVerticalStrut(20));
        panelDisponibilidad.add(btnVolver4);
        panelDisponibilidad.add(Box.createVerticalGlue());

        // ====== MENÚ OPERADOR ======
        JButton btnVolverOperador = new JButton("Volver");
        panelMenuOperador.setLayout(new BoxLayout(panelMenuOperador, BoxLayout.Y_AXIS));
        btnRegistrarEvento.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMostrarUsuarios.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMostrarVentas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolverOperador.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelMenuOperador.add(Box.createVerticalGlue());
        panelMenuOperador.add(btnRegistrarEvento);
        panelMenuOperador.add(Box.createVerticalStrut(10));
        panelMenuOperador.add(btnMostrarUsuarios);
        panelMenuOperador.add(Box.createVerticalStrut(10));
        panelMenuOperador.add(btnMostrarVentas);
        panelMenuOperador.add(Box.createVerticalStrut(20));
        panelMenuOperador.add(btnVolverOperador);
        panelMenuOperador.add(Box.createVerticalGlue());

        // ====== PANEL MOSTRAR USUARIOS ======
        JPanel panelUsuarios = new JPanel(new BorderLayout());
        JPanel panelFiltro = new JPanel();
        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnVolverUsuarios = new JButton("Volver");
        panelFiltro.add(new JLabel("Interés:"));
        panelFiltro.add(txtFiltroInteres);
        panelFiltro.add(btnFiltrar);
        panelUsuarios.add(panelFiltro, BorderLayout.NORTH);
        panelUsuarios.add(new JScrollPane(listaUsuarios), BorderLayout.CENTER);
        panelUsuarios.add(btnVolverUsuarios, BorderLayout.SOUTH);

        // ====== PANEL MOSTRAR VENTAS ======
        JPanel panelVentas = new JPanel(new BorderLayout());
        JButton btnVolverVentas = new JButton("Volver");
        panelVentas.add(new JScrollPane(listaVentas), BorderLayout.CENTER);
        panelVentas.add(btnVolverVentas, BorderLayout.SOUTH);

        // ====== CARDLAYOUT ======
        panelPrincipal.add(panelInicio, "inicio");
        panelPrincipal.add(panelMenuUsuario, "menuUsuario");
        panelPrincipal.add(panelRegistrar, "registrar");
        panelPrincipal.add(panelEventos, "eventos");
        panelPrincipal.add(panelComprar, "comprar");
        panelPrincipal.add(panelDisponibilidad, "disponibilidad");
        panelPrincipal.add(panelMenuOperador, "menuOperador");
        panelPrincipal.add(panelUsuarios, "mostrarUsuarios");
        panelPrincipal.add(panelVentas, "mostrarVentas");

        add(panelPrincipal);

        // ====== ACCIONES ======
        btnUsuario.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
        btnOperador.addActionListener(e -> cardLayout.show(panelPrincipal, "menuOperador"));
        btnSalir.addActionListener(e -> System.exit(0));

        btnVolverUsuario.addActionListener(e -> cardLayout.show(panelPrincipal, "inicio"));
        btnVolverOperador.addActionListener(e -> cardLayout.show(panelPrincipal, "inicio"));

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

        btnVolver1.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
        btnVolver2.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
        btnVolver3.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
        btnVolver4.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));

        // Guardar usuario
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

        // Comprar entrada
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

        // Consultar disponibilidad
        btnConsultar.addActionListener(e -> {
            String evento = txtEventoDisp.getText();
            if (evento != null && !evento.trim().isEmpty()) {
                sistema.mostrarUbicacionesDisponibles(evento);
                JOptionPane.showMessageDialog(this, "Disponibilidad mostrada en consola.");
            }
        });

        // Mostrar usuarios
        btnMostrarUsuarios.addActionListener(e -> {
            modeloUsuarios.clear();
            for (Usuarios u : sistema.getUsuarios().values()) {
                modeloUsuarios.addElement("Nombre: " + u.getNombre() + " | Edad: " + u.getEdad());
            }
            cardLayout.show(panelPrincipal, "mostrarUsuarios");
        });

        // Filtrar usuarios
        btnFiltrar.addActionListener(e -> {
            String tema = txtFiltroInteres.getText();
            modeloUsuarios.clear();
            for (Usuarios u : sistema.getUsuarios().values()) {
                if (u.getIntereses().contains(tema)) {
                    modeloUsuarios.addElement("Nombre: " + u.getNombre() +
                                              " | Edad: " + u.getEdad() +
                                              " | Intereses: " + String.join(", ", u.getIntereses()));
                }
            }
        });

        btnVolverUsuarios.addActionListener(e -> cardLayout.show(panelPrincipal, "menuOperador"));

        // Mostrar ventas
        btnMostrarVentas.addActionListener(e -> {
            modeloVentas.clear();
            for (Entrada en : sistema.getEntradas()) {
                modeloVentas.addElement("Usuario: " + en.getUsuario().getNombre() +
                                        " | Evento: " + en.getEvento().getNombre() +
                                        " | Precio: " + en.getPrecio());
            }
            cardLayout.show(panelPrincipal, "mostrarVentas");
        });

        btnVolverVentas.addActionListener(e -> cardLayout.show(panelPrincipal, "menuOperador"));

        // Registrar evento
        btnRegistrarEvento.addActionListener(e -> {
            JTextField txtNombreEv = new JTextField();
            JTextField txtLugar = new JTextField();
            JTextField txtFecha = new JTextField();

            Object[] message = {
                "Nombre:", txtNombreEv,
                "Lugar:", txtLugar,
                "Fecha (dd/MM/yyyy):", txtFecha
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Registrar Evento", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(txtFecha.getText());
                    Operador op = new Operador("operador", sistema);
                    op.registrarEvento(txtNombreEv.getText(), txtLugar.getText(), fecha);
                    JOptionPane.showMessageDialog(this, "Evento registrado!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Fecha inválida. Use formato dd/MM/yyyy");
                }
            }
        });
    }

    public static void main(String[] args) {
        SistemaEntradas sistema = new SistemaEntradas();
        sistema.cargarDatosIniciales();
        SwingUtilities.invokeLater(() -> new Ventanas(sistema).setVisible(true));
    }
}
