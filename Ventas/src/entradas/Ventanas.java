package entradas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private final JButton btnEliminarEvento = new JButton("Eliminar Evento");
    private final JButton btnModificarEvento = new JButton("Modificar Evento");

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
                sistema.guardarDatosAlSalir();
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
    // REGISTRAR USUARIO (con RUT)
    // =========================
    private void initPanelRegistrar() {
        panelRegistrar.setLayout(new BoxLayout(panelRegistrar, BoxLayout.Y_AXIS));

        JTextField txtRut = new JTextField(15);
        JTextField txtNombre = new JTextField(15);
        JTextField txtEdad = new JTextField(5);

        JCheckBox chkTecnologia = new JCheckBox("Tecnología");
        JCheckBox chkDeportes = new JCheckBox("Deportes");
        JCheckBox chkMusica = new JCheckBox("Música");
        JCheckBox chkCiencia = new JCheckBox("Ciencia");

        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver = new JButton("Volver");

        panelRegistrar.add(Box.createVerticalGlue());
        panelRegistrar.add(new JLabel("RUT:"));
        txtRut.setMaximumSize(new Dimension(200, 25));
        panelRegistrar.add(txtRut);
        panelRegistrar.add(Box.createVerticalStrut(10));

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
                String rut = txtRut.getText().trim();
                String nombre = txtNombre.getText().trim();
                int edad = Integer.parseInt(txtEdad.getText().trim());

                if (rut.isEmpty() || nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "RUT y nombre son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (sistema.getUsuarios().containsKey(rut)) {
                    JOptionPane.showMessageDialog(null, "Ya existe un usuario con este RUT.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<String> intereses = new ArrayList<>();
                if (chkTecnologia.isSelected()) intereses.add("Tecnología");
                if (chkDeportes.isSelected()) intereses.add("Deportes");
                if (chkMusica.isSelected()) intereses.add("Música");
                if (chkCiencia.isSelected()) intereses.add("Ciencia");

                sistema.registrarUsuariosConRut(rut, nombre, edad, intereses);

                JOptionPane.showMessageDialog(null, "Usuario registrado con éxito!");

                txtRut.setText("");
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
    // COMPRAR ENTRADA (con RUT)
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
            String rutUsuario = JOptionPane.showInputDialog(null, "Ingrese RUT de usuario:");
            int idx = listaEventosCompra.getSelectedIndex();
            if (idx >= 0) {
                Eventos ev = sistema.getEventos().get(idx);
                try {
                    String resultado = sistema.realizarVenta(rutUsuario, ev.getNombre());
                    if (resultado.startsWith("Compra realizada")) {
                        mostrarVentanaCompra(rutUsuario, ev, resultado);
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
                JOptionPane.showMessageDialog(null, "Debe seleccionar un evento.");
            }
        });

        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
    }

    private void mostrarVentanaCompra(String rut, Eventos ev, String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Compra realizada", JOptionPane.INFORMATION_MESSAGE);
    }

    // =========================
    // MENÚ OPERADOR
    // =========================
    /**
    private void initPanelMenuOperador() {
        panelMenuOperador.setLayout(new BoxLayout(panelMenuOperador, BoxLayout.Y_AXIS));
        for (JButton boton : new JButton[]{btnRegistrarEvento, btnEliminarEvento, btnModificarEvento, btnMostrarUsuarios, btnMostrarVentas}) {
            boton.setAlignmentX(Component.CENTER_ALIGNMENT);
            boton.setMaximumSize(new Dimension(240, 50));
            boton.setFont(new Font("Arial", Font.BOLD, 18));
            panelMenuOperador.add(Box.createVerticalStrut(10));
            panelMenuOperador.add(boton);
        }

        JButton btnVolver = new JButton("Volver");
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMenuOperador.add(Box.createVerticalStrut(20));
        panelMenuOperador.add(btnVolver);

        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "inicio"));

        btnRegistrarEvento.addActionListener(e -> registrarEvento());
        btnEliminarEvento.addActionListener(e -> eliminarEvento());
        btnModificarEvento.addActionListener(e -> modificarEvento());
        btnMostrarUsuarios.addActionListener(e -> {
            modeloUsuarios.clear();
            for (Usuarios u : sistema.getUsuarios().values()) {
                modeloUsuarios.addElement(u.getRut() + " | " + u.getNombre() + " | " + u.getEdad() + " | " + u.getIntereses());
            }
            cardLayout.show(panelPrincipal, "mostrarUsuarios");
        });
        btnMostrarVentas.addActionListener(e -> {
            modeloVentas.clear();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            for (Entrada v : sistema.getEntradas()) {
                String texto = v.getUsuario().getRut() + " | " + v.getUsuario().getNombre() + 
                               " | " + v.getEvento().getNombre() + 
                               " | $" + v.getPrecio() + 
                               " | " + sdf.format(v.getFechaCompra());
                modeloVentas.addElement(texto);
            }
            cardLayout.show(panelPrincipal, "mostrarVentas");
        });

    }
    **/

    // =========================
    // PANEL USUARIOS
    // =========================
    /*
    private void initPanelUsuarios() {
        panelUsuarios.setLayout(new BorderLayout());
        JButton btnVolver = new JButton("Volver");
        panelUsuarios.add(new JScrollPane(listaUsuarios), BorderLayout.CENTER);
        panelUsuarios.add(btnVolver, BorderLayout.SOUTH);
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "menuOperador"));
    }
    */
    
 // =========================
    // MENÚ OPERADOR
    // =========================
    private void initPanelMenuOperador() {
        panelMenuOperador.setLayout(new BoxLayout(panelMenuOperador, BoxLayout.Y_AXIS));

        btnRegistrarEvento.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMostrarUsuarios.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMostrarVentas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEliminarEvento.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnModificarEvento.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btnVolver = new JButton("Volver");
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension botonGrande = new Dimension(240, 50);
        Font fuenteGrande = new Font("Arial", Font.BOLD, 18);
        for (JButton boton : new JButton[]{btnRegistrarEvento, btnMostrarUsuarios, btnMostrarVentas,btnEliminarEvento,btnModificarEvento, btnVolver}) {
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
        panelMenuOperador.add(btnEliminarEvento);
        panelMenuOperador.add(Box.createVerticalStrut(10));
        panelMenuOperador.add(btnModificarEvento);
        panelMenuOperador.add(Box.createVerticalStrut(10));
        panelMenuOperador.add(btnVolver);
        panelMenuOperador.add(Box.createVerticalGlue());

        //Registrar evento con formulario
        btnRegistrarEvento.addActionListener(e -> {
            JTextField txtNombre = new JTextField();
            JTextField txtLugar = new JTextField();
            JTextField txtFecha = new JTextField();

            //Panel principal con BoxLayout vertical y márgenes
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));//espacio con los bordes

            //Nombre
            panel.add(new JLabel("Nombre Evento:"));
            panel.add(Box.createVerticalStrut(5));
            panel.add(txtNombre);
            panel.add(Box.createVerticalStrut(10));

            //Lugar
            panel.add(new JLabel("Lugar:"));
            panel.add(Box.createVerticalStrut(5));
            panel.add(txtLugar);
            panel.add(Box.createVerticalStrut(10));

            //Fecha
            panel.add(new JLabel("Fecha (dd/MM/yyyy):"));
            panel.add(Box.createVerticalStrut(5));
            panel.add(txtFecha);
            panel.add(Box.createVerticalStrut(15));

            //Botones OK y Cancelar
            JButton btnOK = new JButton("OK");
            JButton btnCancelar = new JButton("Cancelar");
            JPanel panelBotones = new JPanel();
            panelBotones.add(btnOK);
            panelBotones.add(btnCancelar);
            panel.add(panelBotones);

            //Crear JDialog, para usar la forma de formulario, como el de google :)
            JDialog dialogo = new JDialog((Frame) null, "Registrar Evento", true);
            dialogo.setContentPane(panel);

            //Acción botón OK
            btnOK.addActionListener(ev -> {
                String nombre = txtNombre.getText().trim();
                String lugar = txtLugar.getText().trim();
                String fechaStr = txtFecha.getText().trim();

                //Validaciones individuales
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(dialogo, "Ingrese el nombre del evento.", "Campo faltante", JOptionPane.WARNING_MESSAGE);
                    return; //No cerrar el dialogo, para no perder los cambios que estaba haciendo antes de que aparezca que falta llenar un campo
                }
                if (lugar.isEmpty()) {
                    JOptionPane.showMessageDialog(dialogo, "Ingrese el lugar del evento.", "Campo faltante", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (fechaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(dialogo, "Ingrese la fecha del evento.", "Campo faltante", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Validar el formato de la fecha
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(false);
                    Date fecha = sdf.parse(fechaStr);

                    sistema.registrarEvento(nombre, lugar, fechaStr);
                    JOptionPane.showMessageDialog(dialogo, "Evento registrado con éxito!");
                    dialogo.dispose(); // Solo cierra la ventana si todo es correcto
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialogo, "Formato de fecha inválido. Use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            //Accion del boton Cancelar
            btnCancelar.addActionListener(ev -> {
                JOptionPane.showMessageDialog(dialogo, "No se ha registrado ningún evento.");
                dialogo.dispose();
            });

            //Ajuste de tamaño y ubicación
            dialogo.pack();
            dialogo.setLocationRelativeTo(null); // Centrado
            dialogo.setVisible(true);
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
        //accion para eliminar un evento
        btnEliminarEvento.addActionListener(e -> {
            if (sistema.getEventos().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se ha registrado ningún evento.");
                return;
            }

            // Crear un arreglo con los nombres de los eventos
            String[] eventosArray = sistema.getEventos().stream()
                                          .map(Eventos::getNombre)
                                          .toArray(String[]::new);

            JComboBox<String> comboEventos = new JComboBox<>(eventosArray);
            comboEventos.setSelectedIndex(-1); // <- No hay selección inicial

            int opcion = JOptionPane.showConfirmDialog(null, comboEventos, 
                            "Seleccione el evento a eliminar", JOptionPane.OK_CANCEL_OPTION);

            if (opcion != JOptionPane.OK_OPTION || comboEventos.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No se ha eliminado ningún evento.");
                return;
            }

            String nombreEvento = (String) comboEventos.getSelectedItem();
            try {
                sistema.eliminarEvento(nombreEvento);
                JOptionPane.showMessageDialog(null, "Evento eliminado con éxito.");
            } catch (EventoNoEncontradoException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        //falta arreglar que sucede cuando se presiona ok sin haber precionado un evento para eliminar
        //agregar lo que hace la parte de modificar un evento
        btnModificarEvento.addActionListener(e -> {
            if (sistema.getEventos().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se ha registrado ningún evento.");
                return;
            }

            // Crear combo con nombres de eventos
            String[] eventosArray = sistema.getEventos().stream()
                                          .map(Eventos::getNombre)
                                          .toArray(String[]::new);
            JComboBox<String> comboEventos = new JComboBox<>(eventosArray);
            comboEventos.setSelectedIndex(-1); // nada seleccionado al inicio

            int opcion = JOptionPane.showConfirmDialog(null, comboEventos,
                            "Seleccione el evento a modificar", JOptionPane.OK_CANCEL_OPTION);

            if (opcion != JOptionPane.OK_OPTION || comboEventos.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "No se ha modificado ningún evento.");
                return;
            }

            Eventos evento = sistema.getEventos().get(comboEventos.getSelectedIndex());

            JTextField txtNombre = new JTextField(evento.getNombre());
            JTextField txtLugar = new JTextField(evento.getLugar());
            JTextField txtFecha = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(evento.getFecha()));

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Nombre:"));
            panel.add(txtNombre);
            panel.add(new JLabel("Lugar:"));
            panel.add(txtLugar);
            panel.add(new JLabel("Fecha (dd/MM/yyyy):"));
            panel.add(txtFecha);

            int resultado = JOptionPane.showConfirmDialog(null, panel, "Modificar Evento",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (resultado == JOptionPane.OK_OPTION) {
                try {
                    String nombreNuevo = txtNombre.getText().trim();
                    String lugarNuevo = txtLugar.getText().trim();
                    Date fechaNueva = new SimpleDateFormat("dd/MM/yyyy").parse(txtFecha.getText().trim());

                    // Modificar directamente en el objeto existente
                    evento.setNombre(nombreNuevo);
                    evento.setLugar(lugarNuevo);
                    evento.setFecha(fechaNueva);

                    JOptionPane.showMessageDialog(null, "Evento modificado con éxito.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar fecha: " + ex.getMessage(),
                                                  "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se ha modificado ningún evento.");
            }
        });



        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "inicio"));
    }

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

    // =========================
    // MÉTODOS OPERADOR
    // =========================
    private void registrarEvento() {
        JTextField txtNombre = new JTextField();
        JTextField txtLugar = new JTextField();
        JTextField txtFecha = new JTextField();

        Object[] campos = {
            "Nombre:", txtNombre,
            "Lugar:", txtLugar,
            "Fecha (dd/MM/yyyy):", txtFecha
        };

        int opcion = JOptionPane.showConfirmDialog(null, campos, "Registrar Evento", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String lugar = txtLugar.getText().trim();
            String fechaStr = txtFecha.getText().trim();

            if (nombre.isEmpty() || lugar.isEmpty() || fechaStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Pasamos el String directamente al método sobrecargado
            sistema.registrarEvento(nombre, lugar, fechaStr);

            JOptionPane.showMessageDialog(null, "Evento registrado con éxito!");
        }
    }


    private void eliminarEvento() {
        if (sistema.getEventos().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay eventos para eliminar.");
            return;
        }
        String[] nombres = sistema.getEventos().stream().map(Eventos::getNombre).toArray(String[]::new);
        JComboBox<String> combo = new JComboBox<>(nombres);
        int opcion = JOptionPane.showConfirmDialog(null, combo, "Seleccione evento a eliminar", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION && combo.getSelectedIndex() >= 0) {
            try {
                sistema.eliminarEvento((String) combo.getSelectedItem());
                JOptionPane.showMessageDialog(null, "Evento eliminado!");
            } catch (EventoNoEncontradoException ex) {
                JOptionPane.showMessageDialog(null, "Error: No se encontró el evento.");
            }
        }

    }

    private void modificarEvento() {
        if (sistema.getEventos().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay eventos para modificar.");
            return;
        }
        String[] nombres = sistema.getEventos().stream().map(Eventos::getNombre).toArray(String[]::new);
        JComboBox<String> combo = new JComboBox<>(nombres);
        int opcion = JOptionPane.showConfirmDialog(null, combo, "Seleccione evento a modificar", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION && combo.getSelectedIndex() >= 0) {
            Eventos ev = sistema.getEventos().get(combo.getSelectedIndex());

            JTextField txtNombre = new JTextField(ev.getNombre());
            JTextField txtLugar = new JTextField(ev.getLugar());
            JTextField txtFecha = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(ev.getFecha()));

            Object[] campos = {
                "Nombre:", txtNombre,
                "Lugar:", txtLugar,
                "Fecha (dd/MM/yyyy):", txtFecha
            };

            int op = JOptionPane.showConfirmDialog(null, campos, "Modificar Evento", JOptionPane.OK_CANCEL_OPTION);
            if (op == JOptionPane.OK_OPTION) {
                try {
                    String nombreNuevo = txtNombre.getText().trim();
                    String lugarNuevo = txtLugar.getText().trim();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(false);
                    Date fechaNueva = sdf.parse(txtFecha.getText().trim());

                    ev.setNombre(nombreNuevo);
                    ev.setLugar(lugarNuevo);
                    ev.setFecha(fechaNueva);

                    JOptionPane.showMessageDialog(null, "Evento modificado con éxito!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Fecha inválida.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    }
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
}
