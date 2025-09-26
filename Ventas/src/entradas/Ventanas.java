package entradas;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ventanas extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel panelPrincipal = new JPanel(cardLayout);

    // ====== BOTONES MENÚ USUARIO ======
    private final JButton btnRegistrar = new JButton("Registrar Usuario");
    private final JButton btnEventos = new JButton("Mostrar Eventos");
    private final JButton btnComprar = new JButton("Comprar Entrada");

    // ====== BOTONES MENÚ OPERADOR ======
    private final JButton btnRegistrarEvento = new JButton("Registrar Evento");
    private final JButton btnMostrarUsuarios = new JButton("Mostrar Usuarios");
    private final JButton btnMostrarVentas = new JButton("Mostrar Ventas");

    // ====== PANELES ======
    private final JPanel panelInicio = new JPanel();
    private final JPanel panelMenuUsuario = new JPanel();
    private final JPanel panelRegistrar = new JPanel();
    private final JPanel panelEventos = new JPanel();
    private final JPanel panelComprar = new JPanel();
    private final JPanel panelDisponibilidad = new JPanel();
    private final JPanel panelMenuOperador = new JPanel();
    private final JPanel panelUsuarios = new JPanel();
    private final JPanel panelVentas = new JPanel();

    // ====== LISTAS PARA GUI ======
    private final DefaultListModel<String> modeloUsuarios = new DefaultListModel<>();
    private final JList<String> listaUsuarios = new JList<>(modeloUsuarios);
    private final JTextField txtFiltroInteres = new JTextField(15); //MMMM

    private final DefaultListModel<String> modeloEventos = new DefaultListModel<>();
    private final JList<String> listaEventos = new JList<>(modeloEventos);

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

        initPanelInicio();
        initPanelMenuUsuario();
        initPanelRegistrar();
        initPanelEventos();
        initPanelComprar();
        initPanelMenuOperador();
        initPanelUsuarios();
        initPanelVentas();

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
        cardLayout.show(panelPrincipal, "inicio");
    }

    // ====== PANEL INICIO ======
    private void initPanelInicio() {
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

        btnUsuario.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
        btnOperador.addActionListener(e -> cardLayout.show(panelPrincipal, "menuOperador"));
        btnSalir.addActionListener(e -> System.exit(0));
    }

 // ====== PANEL MENÚ USUARIO ======
    private void initPanelMenuUsuario() {
        panelMenuUsuario.setLayout(new BoxLayout(panelMenuUsuario, BoxLayout.Y_AXIS));
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEventos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnComprar.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Se elimina btnDisponibilidad

        JButton btnVolverUsuario = new JButton("Volver");
        btnVolverUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelMenuUsuario.add(Box.createVerticalGlue());
        panelMenuUsuario.add(btnRegistrar);
        panelMenuUsuario.add(Box.createVerticalStrut(10));
        panelMenuUsuario.add(btnEventos);
        panelMenuUsuario.add(Box.createVerticalStrut(10));
        panelMenuUsuario.add(btnComprar);
        panelMenuUsuario.add(Box.createVerticalStrut(20));
        panelMenuUsuario.add(btnVolverUsuario);
        panelMenuUsuario.add(Box.createVerticalGlue());

        btnRegistrar.addActionListener(e -> cardLayout.show(panelPrincipal, "registrar"));
        btnEventos.addActionListener(e -> {
            modeloEventos.clear();
            for (Eventos ev : sistema.getEventos()) {
                modeloEventos.addElement(ev.getNombre() + " | " + ev.getLugar() + " | " + ev.getFecha());
            }
            cardLayout.show(panelPrincipal, "eventos");
        });
        btnComprar.addActionListener(e -> {
            modeloEventos.clear();
            for (Eventos ev : sistema.getEventos()) {
                modeloEventos.addElement(ev.getNombre() + " | " + ev.getLugar() + " | " + ev.getFecha());
            }
            cardLayout.show(panelPrincipal, "comprar");
        });
        btnVolverUsuario.addActionListener(e -> cardLayout.show(panelPrincipal, "inicio"));
    }


    // ====== PANEL REGISTRAR USUARIO ======
    private void initPanelRegistrar() {
        panelRegistrar.setLayout(new BoxLayout(panelRegistrar, BoxLayout.Y_AXIS));

        JTextField txtNombre = new JTextField(15);
        JTextField txtEdad = new JTextField(5);

        JCheckBox chkTecnologia = new JCheckBox("Tecnología");
        JCheckBox chkDeportes = new JCheckBox("Deportes");
        JCheckBox chkMusica = new JCheckBox("Música");
        JCheckBox chkCiencia = new JCheckBox("Ciencia");

        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver1 = new JButton("Volver");

        panelRegistrar.add(Box.createVerticalGlue());
        panelRegistrar.add(new JLabel("Nombre:"));
        txtNombre.setMaximumSize(new Dimension(200, 25));
        panelRegistrar.add(txtNombre);
        panelRegistrar.add(Box.createVerticalStrut(10));
        panelRegistrar.add(new JLabel("Edad:"));
        txtEdad.setMaximumSize(new Dimension(100, 25));
        panelRegistrar.add(txtEdad);
        panelRegistrar.add(Box.createVerticalStrut(10));
        panelRegistrar.add(new JLabel("Áreas de interés:"));
        panelRegistrar.add(chkTecnologia);
        panelRegistrar.add(chkDeportes);
        panelRegistrar.add(chkMusica);
        panelRegistrar.add(chkCiencia);
        panelRegistrar.add(Box.createVerticalStrut(20));
        panelRegistrar.add(btnGuardar);
        panelRegistrar.add(Box.createVerticalStrut(20));
        panelRegistrar.add(btnVolver1);
        panelRegistrar.add(Box.createVerticalGlue());

        // Acción Guardar
        btnGuardar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                int edad = Integer.parseInt(txtEdad.getText());
                List<String> intereses = new ArrayList<>();
                if (chkTecnologia.isSelected()) intereses.add("Tecnología");
                if (chkDeportes.isSelected()) intereses.add("Deportes");
                if (chkMusica.isSelected()) intereses.add("Música");
                if (chkCiencia.isSelected()) intereses.add("Ciencia");

                sistema.registrarUsuarios(nombre, edad, intereses);
                JOptionPane.showMessageDialog(this, "Usuario registrado con éxito!");
                txtNombre.setText(""); txtEdad.setText("");
                chkTecnologia.setSelected(false); chkDeportes.setSelected(false);
                chkMusica.setSelected(false); chkCiencia.setSelected(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Edad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVolver1.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
    }

    // ====== PANEL MOSTRAR EVENTOS ======
    private void initPanelEventos() {
        panelEventos.setLayout(new BorderLayout());
        JButton btnVolver2 = new JButton("Volver");
        panelEventos.add(new JScrollPane(listaEventos), BorderLayout.CENTER);
        panelEventos.add(btnVolver2, BorderLayout.SOUTH);
        btnVolver2.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
    }

 // ====== PANEL COMPRAR ======
    private void initPanelComprar() {
        panelComprar.setLayout(new BorderLayout());
        JButton btnComprarAhora = new JButton("Confirmar Compra");
        JButton btnVolver3 = new JButton("Volver");

        // Lista de eventos para seleccionar
        JList<String> listaEventosCompra = new JList<>(modeloEventos);
        listaEventosCompra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panelComprar.add(new JScrollPane(listaEventosCompra), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnComprarAhora);
        panelBotones.add(btnVolver3);
        panelComprar.add(panelBotones, BorderLayout.SOUTH);

        btnComprarAhora.addActionListener(e -> {
            String usuarioNombre = JOptionPane.showInputDialog(this, "Ingrese nombre de usuario:");
            int idx = listaEventosCompra.getSelectedIndex();
            if (idx >= 0) {
                Eventos ev = sistema.getEventos().get(idx);
                String mensaje = sistema.realizarVenta(usuarioNombre, ev.getNombre());
                JOptionPane.showMessageDialog(this, mensaje);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un evento de la lista.");
            }
        });

        btnVolver3.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
    }

   
    // ====== PANEL MENÚ OPERADOR ======
    private void initPanelMenuOperador() {
        panelMenuOperador.setLayout(new BoxLayout(panelMenuOperador, BoxLayout.Y_AXIS));
        btnRegistrarEvento.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMostrarUsuarios.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMostrarVentas.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btnVolverOperador = new JButton("Volver");
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

        btnRegistrarEvento.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad no implementada aún"));
        btnMostrarUsuarios.addActionListener(e -> {
            modeloUsuarios.clear();
            for (Usuarios u : sistema.getUsuarios().values()) {
                modeloUsuarios.addElement(u.getNombre() + " | Edad: " + u.getEdad());
            }
            cardLayout.show(panelPrincipal, "mostrarUsuarios");
        });
        btnMostrarVentas.addActionListener(e -> {
            modeloVentas.clear();
            for (Entrada en : sistema.getEntradas()) {
                modeloVentas.addElement("Usuario: " + en.getUsuario().getNombre() +
                        " | Evento: " + en.getEvento().getNombre() +
                        " | Precio: $" + en.getPrecio());
            }
            cardLayout.show(panelPrincipal, "mostrarVentas");
        });
        btnVolverOperador.addActionListener(e -> cardLayout.show(panelPrincipal, "inicio"));
    }

    // ====== PANEL USUARIOS ======
    private void initPanelUsuarios() {
        panelUsuarios.setLayout(new BorderLayout());
        JButton btnVolver5 = new JButton("Volver");
        panelUsuarios.add(new JScrollPane(listaUsuarios), BorderLayout.CENTER);
        panelUsuarios.add(btnVolver5, BorderLayout.SOUTH);
        btnVolver5.addActionListener(e -> cardLayout.show(panelPrincipal, "menuOperador"));
    }

    // ====== PANEL VENTAS ======
    private void initPanelVentas() {
        panelVentas.setLayout(new BorderLayout());
        JButton btnVolver6 = new JButton("Volver");
        panelVentas.add(new JScrollPane(listaVentas), BorderLayout.CENTER);
        panelVentas.add(btnVolver6, BorderLayout.SOUTH);
        btnVolver6.addActionListener(e -> cardLayout.show(panelPrincipal, "menuOperador"));
    }

    // ====== MAIN ======
    public static void main(String[] args) {
        SistemaEntradas sistema = new SistemaEntradas();
        sistema.cargarDatosIniciales();
        SwingUtilities.invokeLater(() -> new Ventanas(sistema).setVisible(true));
    }
}
