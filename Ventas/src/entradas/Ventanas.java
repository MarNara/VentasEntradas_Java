package entradas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ventanas {
    private final SistemaEntradas sistema;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel panelPrincipal = new JPanel(cardLayout);

    // Paneles
    private final JPanel panelInicio = new JPanel();
    private final JPanel panelMenuUsuario = new JPanel();
    private final JPanel panelRegistrar = new JPanel();
    private final JPanel panelEventos = new JPanel();
    private final JPanel panelComprar = new JPanel();
    private final JPanel panelMenuOperador = new JPanel();
    private final JPanel panelUsuarios = new JPanel();
    private final JPanel panelVentas = new JPanel();

    // Botones menú usuario
    private final JButton btnRegistrar = new JButton("Registrar Usuario");
    private final JButton btnEventos = new JButton("Mostrar Eventos");
    private final JButton btnComprar = new JButton("Comprar Entrada");

    // Botones menú operador
    private final JButton btnRegistrarEvento = new JButton("Registrar Evento");
    private final JButton btnMostrarUsuarios = new JButton("Mostrar Usuarios");
    private final JButton btnMostrarVentas = new JButton("Mostrar Ventas");

    // Listas GUI
    private final DefaultListModel<String> modeloUsuarios = new DefaultListModel<>();
    private final JList<String> listaUsuarios = new JList<>(modeloUsuarios);

    private final DefaultListModel<String> modeloEventos = new DefaultListModel<>();
    private final JList<String> listaEventos = new JList<>(modeloEventos);

    private final DefaultListModel<String> modeloVentas = new DefaultListModel<>();
    private final JList<String> listaVentas = new JList<>(modeloVentas);

    public Ventanas(SistemaEntradas sistema) {
        this.sistema = sistema;

        JFrame frame = new JFrame("Sistema de Ventas de Entradas");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                sistema.guardarDatosAlSalir();  // <-- Aquí llamas al método
                frame.dispose();
                System.exit(0);
            }
        });
        frame.setLocationRelativeTo(null);

        initPanelInicio();
        initPanelMenuUsuario();
        initPanelRegistrar();
        initPanelEventos();
        initPanelComprar();
        initPanelMenuOperador();
        initPanelUsuarios();
        initPanelVentas();

        // Agregar paneles al CardLayout
        panelPrincipal.add(panelInicio, "inicio");
        panelPrincipal.add(panelMenuUsuario, "menuUsuario");
        panelPrincipal.add(panelRegistrar, "registrar");
        panelPrincipal.add(panelEventos, "eventos");
        panelPrincipal.add(panelComprar, "comprar");
        panelPrincipal.add(panelMenuOperador, "menuOperador");
        panelPrincipal.add(panelUsuarios, "mostrarUsuarios");
        panelPrincipal.add(panelVentas, "mostrarVentas");

        frame.add(panelPrincipal);
        cardLayout.show(panelPrincipal, "inicio");

        frame.setVisible(true);
    }

    // =========================
    // PANEL INICIO
    // =========================
    private void initPanelInicio() {
        JLabel titulo = new JLabel("Bienvenido al Sistema de Ventas");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnUsuario = new JButton("Menú Usuario");
        JButton btnOperador = new JButton("Menú Operador");
        JButton btnSalir = new JButton("Salir");

        Dimension botonGrande = new Dimension(240, 50);
        Font fuenteGrande = new Font("Arial", Font.BOLD, 18);

        for (JButton boton : new JButton[]{btnUsuario, btnOperador, btnSalir}) {
            boton.setMaximumSize(botonGrande);
            boton.setFont(fuenteGrande);
            boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        panelInicio.setLayout(new BoxLayout(panelInicio, BoxLayout.Y_AXIS));
        panelInicio.add(Box.createVerticalGlue());
        panelInicio.add(titulo);
        panelInicio.add(Box.createVerticalStrut(20));
        panelInicio.add(btnUsuario);
        panelInicio.add(Box.createVerticalStrut(10));
        panelInicio.add(btnOperador);
        panelInicio.add(Box.createVerticalStrut(10));
        panelInicio.add(btnSalir);
        panelInicio.add(Box.createVerticalGlue());

        btnUsuario.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
        btnOperador.addActionListener(e -> cardLayout.show(panelPrincipal, "menuOperador"));
        btnSalir.addActionListener(e -> {
            sistema.guardarDatosAlSalir();
            System.exit(0);
        });
    }

    // =========================
    // MENÚ USUARIO
    // =========================
    private void initPanelMenuUsuario() {
        panelMenuUsuario.setLayout(new BoxLayout(panelMenuUsuario, BoxLayout.Y_AXIS));

        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEventos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnComprar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnVolverUsuario = new JButton("Volver");
        btnVolverUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension botonGrande = new Dimension(240, 50);
        Font fuenteGrande = new Font("Arial", Font.BOLD, 18);
        for (JButton boton : new JButton[]{btnRegistrar, btnEventos, btnComprar}) {
            boton.setMaximumSize(botonGrande);
            boton.setFont(fuenteGrande);
        }

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
        btnComprar.addActionListener(e -> cardLayout.show(panelPrincipal, "comprar"));
        btnVolverUsuario.addActionListener(e -> cardLayout.show(panelPrincipal, "inicio"));
    }

    // =========================
    // REGISTRAR USUARIO
    // =========================
    private void initPanelRegistrar() {
        panelRegistrar.setLayout(new BoxLayout(panelRegistrar, BoxLayout.Y_AXIS));

        JTextField txtNombre = new JTextField(15);
        JTextField txtEdad = new JTextField(5);

        JCheckBox chkTecnologia = new JCheckBox("Tecnología");
        JCheckBox chkDeportes = new JCheckBox("Deportes");
        JCheckBox chkMusica = new JCheckBox("Música");
        JCheckBox chkCiencia = new JCheckBox("Ciencia");

        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver = new JButton("Volver");

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
        btnGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelRegistrar.add(btnGuardar);
        panelRegistrar.add(Box.createVerticalStrut(20));
        panelRegistrar.add(btnVolver);
        panelRegistrar.add(Box.createVerticalGlue());

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
                JOptionPane.showMessageDialog(null, "Usuario registrado con éxito!");
                txtNombre.setText("");
                txtEdad.setText("");
                chkTecnologia.setSelected(false);
                chkDeportes.setSelected(false);
                chkMusica.setSelected(false);
                chkCiencia.setSelected(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Edad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
    }

    // =========================
    // MOSTRAR EVENTOS
    // =========================
    private void initPanelEventos() {
        panelEventos.setLayout(new BorderLayout());
        JButton btnVolver = new JButton("Volver");
        panelEventos.add(new JScrollPane(listaEventos), BorderLayout.CENTER);
        panelEventos.add(btnVolver, BorderLayout.SOUTH);
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
    }

    // =========================
    // COMPRAR ENTRADA (MODIFICADO)
    // =========================
    private void initPanelComprar() {
        panelComprar.setLayout(new BorderLayout());
        JButton btnComprarAhora = new JButton("Confirmar Compra");
        JButton btnVolver = new JButton("Volver");

        JList<String> listaEventosCompra = new JList<>(modeloEventos);
        listaEventosCompra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panelComprar.add(new JScrollPane(listaEventosCompra), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnComprarAhora);
        panelBotones.add(btnVolver);
        panelComprar.add(panelBotones, BorderLayout.SOUTH);

        btnComprarAhora.addActionListener(e -> {
            String usuarioNombre = JOptionPane.showInputDialog(null, "Ingrese nombre de usuario:");
            int idx = listaEventosCompra.getSelectedIndex();
            if (idx >= 0) {
                Eventos ev = sistema.getEventos().get(idx);
                try {
                    String resultado = sistema.realizarVenta(usuarioNombre, ev.getNombre());
                    if (resultado.startsWith("Compra realizada")) {
                        // Crear ticket visual
                        mostrarVentanaCompra(usuarioNombre, ev, resultado);
                    } else {
                        JOptionPane.showMessageDialog(null, resultado);
                    }
                } catch (UsuarioNoRegistradoException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El usuario no está registrado.");
                } catch (EventoNoEncontradoException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El evento no fue encontrado.");
                } catch (EntradaNoDisponibleException ex) {
                    JOptionPane.showMessageDialog(null, "Error: No hay entradas disponibles para este evento.");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un evento de la lista.");
            }
        });

        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
    }

    // =========================
    // VENTANA DE CONFIRMACIÓN DE COMPRA
    // =========================
    private void mostrarVentanaCompra(String usuario, Eventos evento, String detalle) {
        JDialog dialogo = new JDialog((Frame) null, "Comprobante de Compra", true);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(null);
        dialogo.setLayout(new BorderLayout());

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelInfo.add(new JLabel("¡Compra realizada con éxito!"));
        panelInfo.add(Box.createVerticalStrut(10));
        panelInfo.add(new JLabel("Usuario: " + usuario));
        panelInfo.add(new JLabel("Evento: " + evento.getNombre()));
        panelInfo.add(new JLabel("Lugar: " + evento.getLugar()));
        panelInfo.add(new JLabel("Fecha: " + evento.getFecha()));
        panelInfo.add(new JLabel("Detalle: " + detalle));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(ev -> dialogo.dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);

        dialogo.add(panelInfo, BorderLayout.CENTER);
        dialogo.add(panelBoton, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    // =========================
    // MENÚ OPERADOR
    // =========================
    private void initPanelMenuOperador() {
        panelMenuOperador.setLayout(new BoxLayout(panelMenuOperador, BoxLayout.Y_AXIS));

        btnRegistrarEvento.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMostrarUsuarios.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMostrarVentas.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btnVolver = new JButton("Volver");
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension botonGrande = new Dimension(240, 50);
        Font fuenteGrande = new Font("Arial", Font.BOLD, 18);
        for (JButton boton : new JButton[]{btnRegistrarEvento, btnMostrarUsuarios, btnMostrarVentas, btnVolver}) {
            boton.setMaximumSize(botonGrande);
            boton.setFont(fuenteGrande);
        }

        panelMenuOperador.add(Box.createVerticalGlue());
        panelMenuOperador.add(btnRegistrarEvento);
        panelMenuOperador.add(Box.createVerticalStrut(10));
        panelMenuOperador.add(btnMostrarUsuarios);
        panelMenuOperador.add(Box.createVerticalStrut(10));
        panelMenuOperador.add(btnMostrarVentas);
        panelMenuOperador.add(Box.createVerticalStrut(20));
        panelMenuOperador.add(btnVolver);
        panelMenuOperador.add(Box.createVerticalGlue());

        // --- Registrar evento con formulario ---
        btnRegistrarEvento.addActionListener(e -> {
            JTextField txtNombre = new JTextField();
            JTextField txtLugar = new JTextField();
            JTextField txtFecha = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Nombre:"));
            panel.add(txtNombre);
            panel.add(new JLabel("Lugar:"));
            panel.add(txtLugar);
            panel.add(new JLabel("Fecha (dd/MM/yyyy):"));
            panel.add(txtFecha);

            int result = JOptionPane.showConfirmDialog(null, panel, "Registrar Evento",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String nombre = txtNombre.getText();
                    String lugar = txtLugar.getText();
                    String fecha = txtFecha.getText();

                    sistema.registrarEvento(nombre, lugar, fecha);
                    JOptionPane.showMessageDialog(null, "Evento registrado con éxito!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al registrar evento: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnMostrarUsuarios.addActionListener(e -> {
            actualizarListaUsuarios("");
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

        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "inicio"));
    }

    // =========================
    // PANEL USUARIOS (con filtro)
    // =========================
    private void initPanelUsuarios() {
        panelUsuarios.setLayout(new BorderLayout());

        // Barra superior con filtro
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtFiltro = new JTextField(15);
        JButton btnFiltrar = new JButton("Filtrar");

        panelFiltro.add(new JLabel("Interés:"));
        panelFiltro.add(txtFiltro);
        panelFiltro.add(btnFiltrar);

        JScrollPane scrollUsuarios = new JScrollPane(listaUsuarios);
        JButton btnVolver = new JButton("Volver");

        panelUsuarios.add(panelFiltro, BorderLayout.NORTH);
        panelUsuarios.add(scrollUsuarios, BorderLayout.CENTER);
        panelUsuarios.add(btnVolver, BorderLayout.SOUTH);

        btnFiltrar.addActionListener(e -> {
            String filtro = txtFiltro.getText().trim();
            actualizarListaUsuarios(filtro);
        });

        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "menuOperador"));
    }

    // Método auxiliar para actualizar lista de usuarios con filtro
    private void actualizarListaUsuarios(String filtro) {
        modeloUsuarios.clear();
        for (Usuarios u : sistema.getUsuarios().values()) {
            boolean coincide = filtro.isEmpty() ||
                    u.getIntereses().stream().anyMatch(i -> i.equalsIgnoreCase(filtro));
            if (coincide) {
                modeloUsuarios.addElement("Nombre: " + u.getNombre() +
                        " | Edad: " + u.getEdad() +
                        " | Intereses: " + String.join(", ", u.getIntereses()));
            }
        }
    }

    // =========================
    // PANEL VENTAS
    // =========================
    private void initPanelVentas() {
        panelVentas.setLayout(new BorderLayout());
        JButton btnVolver = new JButton("Volver");
        panelVentas.add(new JScrollPane(listaVentas), BorderLayout.CENTER);
        panelVentas.add(btnVolver, BorderLayout.SOUTH);
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "menuOperador"));
    }
}
