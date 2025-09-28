package entradas;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


//clase hija de VentanaPrincipal
class MenuOperadorPanel extends JPanel {
    private static final long serialVersionUID = 1L;
	private final DefaultListModel<String> modeloUsuarios = new DefaultListModel<>();
    private final DefaultListModel<String> modeloVentas = new DefaultListModel<>();
    private final JList<String> listaVentas = new JList<>(modeloVentas);

    public MenuOperadorPanel(SistemaEntradas sistema, CardLayout cardLayout, JPanel panelPrincipal) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Botones principales
        JButton btnRegistrarEvento = new JButton("Registrar Evento");
        JButton btnMostrarUsuarios = new JButton("Mostrar Usuarios");
        JButton btnMostrarVentas = new JButton("Mostrar Ventas");
        JButton btnEliminarEvento = new JButton("Eliminar Evento");
        JButton btnModificarEvento = new JButton("Modificar Evento");
        JButton btnModificarUbicacion = new JButton("Modificar Categoría");
        JButton btnEliminarUbicacion = new JButton("Eliminar Categoría");
        JButton btnVolver = new JButton("Volver");

        Dimension botonGrande = new Dimension(240, 50);
        Font fuenteGrande = new Font("Arial", Font.BOLD, 18);
        for (JButton boton : new JButton[]{btnRegistrarEvento, btnMostrarUsuarios, btnMostrarVentas,
                btnEliminarEvento, btnModificarEvento, btnModificarUbicacion, btnEliminarUbicacion, btnVolver}) {
            boton.setMaximumSize(botonGrande);
            boton.setFont(fuenteGrande);
            boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        add(Box.createVerticalGlue());
        add(btnRegistrarEvento); add(Box.createVerticalStrut(10));
        add(btnMostrarUsuarios); add(Box.createVerticalStrut(10));
        add(btnMostrarVentas); add(Box.createVerticalStrut(20));
        add(btnEliminarEvento); add(Box.createVerticalStrut(10));
        add(btnModificarEvento); add(Box.createVerticalStrut(10));
        add(btnModificarUbicacion); add(Box.createVerticalStrut(10));
        add(btnEliminarUbicacion); add(Box.createVerticalStrut(20));
        add(btnVolver);
        add(Box.createVerticalGlue());

        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "inicio"));

        // =========================
        // REGISTRAR EVENTO
        // =========================
        btnRegistrarEvento.addActionListener(e -> registrarEvento(sistema));

        // =========================
        // MOSTRAR USUARIOS CON FILTRO POR INTERESES
        // =========================
        btnMostrarUsuarios.addActionListener(e -> mostrarUsuariosConFiltro(sistema));

        // =========================
        // MOSTRAR VENTAS
        // =========================
        btnMostrarVentas.addActionListener(e -> {
            modeloVentas.clear();
            for (Entrada en : sistema.getEntradas()) {
                modeloVentas.addElement("Usuario: " + en.getUsuario().getNombre() +
                        " | Evento: " + en.getEvento().getNombre() +
                        " | Precio: $" + en.getPrecio());
            }
            JDialog dialog = new JDialog((Frame) null, "Lista de Ventas", true);
            dialog.setContentPane(new JScrollPane(listaVentas));
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        // =========================
        // ELIMINAR EVENTO
        // =========================
        btnEliminarEvento.addActionListener(e -> {
            if (sistema.getEventos().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay eventos registrados.");
                return;
            }
            String[] eventosArray = sistema.getEventos().stream().map(Eventos::getNombre).toArray(String[]::new);
            JComboBox<String> comboEventos = new JComboBox<>(eventosArray);
            comboEventos.setSelectedIndex(-1);
            int opcion = JOptionPane.showConfirmDialog(null, comboEventos,
                    "Seleccione evento a eliminar", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION && comboEventos.getSelectedIndex() != -1) {
                try {
                    sistema.eliminarEvento((String) comboEventos.getSelectedItem());
                    JOptionPane.showMessageDialog(null, "Evento eliminado con éxito.");
                } catch (EventoNoEncontradoException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // =========================
        // MODIFICAR EVENTO
        // =========================
        btnModificarEvento.addActionListener(e -> {
            if (sistema.getEventos().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay eventos registrados.");
                return;
            }
            String[] eventosArray = sistema.getEventos().stream().map(Eventos::getNombre).toArray(String[]::new);
            JComboBox<String> comboEventos = new JComboBox<>(eventosArray);
            comboEventos.setSelectedIndex(-1);
            int opcion = JOptionPane.showConfirmDialog(null, comboEventos,
                    "Seleccione evento a modificar", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION && comboEventos.getSelectedIndex() != -1) {
                Eventos evento = sistema.getEventos().get(comboEventos.getSelectedIndex());
                modificarEvento(evento);
            }
        });

        // =========================
        // MODIFICAR UBICACIÓN
        // =========================
        btnModificarUbicacion.addActionListener(e -> modificarUbicacion(sistema));/*en este caso esta relacionado a la zona en donde estará 
        la persona y estas zonas se dividen segun la categoría, es decir: General, VIP o Platea */

        // =========================
        // ELIMINAR UBICACIÓN
        // =========================
        btnEliminarUbicacion.addActionListener(e -> eliminarUbicacion(sistema));
        /*en este caso eliminar ubicación relacionado a la zona en donde estará 
        la persona y estas zonas se dividen segun la categoría, es decir: General, VIP o Platea */
        
        //TODO LO RELACIONADO A GENERAR REPORTE
        JButton btnReporte = new JButton("Generar Reporte de Eventos");
        btnReporte.setMaximumSize(new Dimension(300, 50));
        btnReporte.setFont(new Font("Arial", Font.BOLD, 18));
        btnReporte.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(0));
        add(btnReporte);
        
        
        btnReporte.addActionListener(ev -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte como...");
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
                if (!rutaArchivo.toLowerCase().endsWith(".txt")) {
                    rutaArchivo += ".txt";
                }
                try {
                    boolean ok = sistema.generarReporteEventosTXT(rutaArchivo);
                    if (ok) {
                        JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + rutaArchivo);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al generar reporte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    // =========================
    // MÉTODOS AUXILIARES
    // =========================
    
    private void modificarUbicacion(SistemaEntradas sistema) {
        if (sistema.getEventos().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay eventos registrados.");
            return;
        }
        String[] eventosArray = sistema.getEventos().stream().map(Eventos::getNombre).toArray(String[]::new);
        JComboBox<String> comboEventos = new JComboBox<>(eventosArray);
        comboEventos.setSelectedIndex(-1);
        int opcionEvento = JOptionPane.showConfirmDialog(null, comboEventos, "Seleccione evento", JOptionPane.OK_CANCEL_OPTION);
        if (opcionEvento != JOptionPane.OK_OPTION || comboEventos.getSelectedIndex() == -1) return;

        Eventos evento = sistema.getEventos().get(comboEventos.getSelectedIndex());
        if (evento.getUbicaciones().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Este evento no tiene categorias.");
            return;
        }

        String[] ubicacionesArray = evento.getUbicaciones().stream().map(Ubicacion::getNombre).toArray(String[]::new);
        JComboBox<String> comboUbicaciones = new JComboBox<>(ubicacionesArray);
        comboUbicaciones.setSelectedIndex(-1);
        int opcionUbic = JOptionPane.showConfirmDialog(null, comboUbicaciones, "Seleccione Categoría", JOptionPane.OK_CANCEL_OPTION);
        if (opcionUbic != JOptionPane.OK_OPTION || comboUbicaciones.getSelectedIndex() == -1) return;

        Ubicacion ubicacion = evento.getUbicaciones().get(comboUbicaciones.getSelectedIndex());

        JTextField txtNombre = new JTextField(ubicacion.getNombre());
        JTextField txtCapacidad = new JTextField(String.valueOf(ubicacion.getCapacidad()));
        JTextField txtPrecio = new JTextField(String.valueOf(ubicacion.getPrecio()));

        JPanel panel = new JPanel(new GridLayout(0,1,5,5));
        panel.add(new JLabel("Nombre:")); panel.add(txtNombre);
        panel.add(new JLabel("Capacidad:")); panel.add(txtCapacidad);
        panel.add(new JLabel("Precio:")); panel.add(txtPrecio);

        int resultado = JOptionPane.showConfirmDialog(null, panel, "Modificar Categoría", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String nuevoNombre = txtNombre.getText().trim();
                int nuevaCapacidad = Integer.parseInt(txtCapacidad.getText().trim());
                double nuevoPrecio = Double.parseDouble(txtPrecio.getText().trim());

                if(nuevoNombre.isEmpty() || nuevaCapacidad < 0 || nuevoPrecio < 0) {
                    JOptionPane.showMessageDialog(null, "Valores inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                sistema.modificarUbicacion(evento, ubicacion, nuevoNombre, nuevaCapacidad, nuevoPrecio);
                JOptionPane.showMessageDialog(null, "Categoría modificada con éxito.");
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Capacidad o precio inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //este metodo elimina la categoria de un evento, es decir, general, platea o VIP.
    private void eliminarUbicacion(SistemaEntradas sistema) {
        if (sistema.getEventos().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay eventos registrados.");
            return;
        }
        String[] eventosArray = sistema.getEventos().stream().map(Eventos::getNombre).toArray(String[]::new);
        JComboBox<String> comboEventos = new JComboBox<>(eventosArray);
        comboEventos.setSelectedIndex(-1);
        int opcionEvento = JOptionPane.showConfirmDialog(null, comboEventos, "Seleccione evento", JOptionPane.OK_CANCEL_OPTION);
        if (opcionEvento != JOptionPane.OK_OPTION || comboEventos.getSelectedIndex() == -1) return;

        Eventos evento = sistema.getEventos().get(comboEventos.getSelectedIndex());
        if (evento.getUbicaciones().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Este evento no tiene Categoría.");
            return;
        }

        String[] ubicacionesArray = evento.getUbicaciones().stream().map(Ubicacion::getNombre).toArray(String[]::new);
        JComboBox<String> comboUbicaciones = new JComboBox<>(ubicacionesArray);
        comboUbicaciones.setSelectedIndex(-1);
        int opcionUbic = JOptionPane.showConfirmDialog(null, comboUbicaciones, "Seleccione Categoria a eliminar", JOptionPane.OK_CANCEL_OPTION);
        if(opcionUbic != JOptionPane.OK_OPTION || comboUbicaciones.getSelectedIndex() == -1) return;

        Ubicacion ubicacion = evento.getUbicaciones().get(comboUbicaciones.getSelectedIndex());
        int confirmar = JOptionPane.showConfirmDialog(null, "Eliminar Categoría " + ubicacion.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if(confirmar == JOptionPane.YES_OPTION) {
            sistema.eliminarUbicacion(evento, ubicacion);
            JOptionPane.showMessageDialog(null, "Categoría eliminada con éxito.");
        }
    }


  //Este metodo se encarga de registrar un nuevo evento.
    private void registrarEvento(SistemaEntradas sistema) {
        JTextField txtNombre = new JTextField();
        JTextField txtLugar = new JTextField();
        JTextField txtFecha = new JTextField();
        JTextField txtCapacidad = new JTextField();
        JTextField txtCategoria = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(new JLabel("Nombre Evento:")); panel.add(txtNombre); panel.add(Box.createVerticalStrut(5));
        panel.add(new JLabel("Lugar:")); panel.add(txtLugar); panel.add(Box.createVerticalStrut(5));
        panel.add(new JLabel("Fecha (dd/MM/yyyy):")); panel.add(txtFecha); panel.add(Box.createVerticalStrut(5));
        panel.add(new JLabel("Capacidad:")); panel.add(txtCapacidad); panel.add(Box.createVerticalStrut(5));
        panel.add(new JLabel("Categoría:")); panel.add(txtCategoria); panel.add(Box.createVerticalStrut(10));

        JButton btnOK = new JButton("OK");
        JButton btnCancelar = new JButton("Cancelar");
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnOK); panelBotones.add(btnCancelar);
        panel.add(panelBotones);

        JDialog dialogo = new JDialog((Frame) null, "Registrar Evento", true);
        dialogo.setContentPane(panel);

        btnOK.addActionListener(ev -> {
            try {
                String nombre = txtNombre.getText().trim();
                String lugar = txtLugar.getText().trim();
                String fechaStr = txtFecha.getText().trim();
                String capacidadStr = txtCapacidad.getText().trim();
                String categoria = txtCategoria.getText().trim();

                if (nombre.isEmpty() || lugar.isEmpty() || fechaStr.isEmpty() || capacidadStr.isEmpty() || categoria.isEmpty()) {
                    JOptionPane.showMessageDialog(dialogo, "Todos los campos son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                Date fecha = sdf.parse(fechaStr);

                int capacidad = Integer.parseInt(capacidadStr);
                if (capacidad <= 0) {
                    JOptionPane.showMessageDialog(dialogo, "La capacidad debe ser mayor que 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                sistema.registrarEvento(nombre, lugar, fecha, capacidad, categoria, new ArrayList<>());
                JOptionPane.showMessageDialog(dialogo, "Evento registrado con éxito!");
                dialogo.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error en los datos ingresados: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(ev -> dialogo.dispose());

        dialogo.pack();
        dialogo.setLocationRelativeTo(null);
        dialogo.setVisible(true);
    }
    
    //Este metodo se encarga de modificar todos los campos de un evento
    private void modificarEvento(Eventos evento) {
        JTextField txtNombre = new JTextField(evento.getNombre());
        JTextField txtLugar = new JTextField(evento.getLugar());
        JTextField txtFecha = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(evento.getFecha()));
        JTextField txtCapacidad = new JTextField(String.valueOf(evento.getCapacidad()));
        JTextField txtCategoria = new JTextField(evento.getCategoria());

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Nombre:")); panel.add(txtNombre);
        panel.add(new JLabel("Lugar:")); panel.add(txtLugar);
        panel.add(new JLabel("Fecha (dd/MM/yyyy):")); panel.add(txtFecha);
        panel.add(new JLabel("Capacidad:")); panel.add(txtCapacidad);
        panel.add(new JLabel("Categoría:")); panel.add(txtCategoria);

        int resultado = JOptionPane.showConfirmDialog(null, panel, "Modificar Evento",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String nombreNuevo = txtNombre.getText().trim();
                String lugarNuevo = txtLugar.getText().trim();
                Date fechaNueva = new SimpleDateFormat("dd/MM/yyyy").parse(txtFecha.getText().trim());
                int capacidadNueva = Integer.parseInt(txtCapacidad.getText().trim());
                String categoriaNueva = txtCategoria.getText().trim();

                if (nombreNuevo.isEmpty() || lugarNuevo.isEmpty() || categoriaNueva.isEmpty() || capacidadNueva <= 0) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios y la capacidad debe ser mayor que 0.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Modificar directamente en el objeto existente
                evento.setNombre(nombreNuevo);
                evento.setLugar(lugarNuevo);
                evento.setFecha(fechaNueva);
                evento.setCapacidad(capacidadNueva);
                evento.setCategoria(categoriaNueva);

                JOptionPane.showMessageDialog(null, "Evento modificado con éxito.");
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Capacidad inválida. Debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar fecha: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha modificado ningún evento.");
        }
    }

    //Muestra los usuarios que hay
    private void mostrarUsuariosConFiltro(SistemaEntradas sistema) {
        JPanel panelUsuarios = new JPanel(new BorderLayout());
        JTextField txtFiltro = new JTextField(15);
        JButton btnFiltrar = new JButton("Filtrar");
        JList<String> listaUsuarios = new JList<>(modeloUsuarios);

        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.add(new JLabel("Interés:")); panelFiltro.add(txtFiltro); panelFiltro.add(btnFiltrar);

        JScrollPane scrollUsuarios = new JScrollPane(listaUsuarios);
        JButton btnVolver = new JButton("Volver");

        panelUsuarios.add(panelFiltro, BorderLayout.NORTH);
        panelUsuarios.add(scrollUsuarios, BorderLayout.CENTER);
        panelUsuarios.add(btnVolver, BorderLayout.SOUTH);

        // Filtrar
        btnFiltrar.addActionListener(e -> actualizarListaUsuariosPorInteres(sistema, modeloUsuarios, txtFiltro.getText()));
        btnVolver.addActionListener(e -> ((JDialog) SwingUtilities.getWindowAncestor(panelUsuarios)).dispose());

        actualizarListaUsuariosPorInteres(sistema, modeloUsuarios, "");

        JDialog dialog = new JDialog((Frame) null, "Lista de Usuarios", true);
        dialog.setContentPane(panelUsuarios);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    //filtrar usuarios segun el interes
    private void actualizarListaUsuariosPorInteres(SistemaEntradas sistema, DefaultListModel<String> modelo, String filtro) {
        modelo.clear();
        if (sistema.getUsuarios() == null) return;
        String filtroNorm = normalizarTexto(filtro);
        for (Usuarios u : sistema.getUsuarios().values()) {
            boolean coincide = filtroNorm.isEmpty() ||
                    u.getIntereses().stream()
                     .map(this::normalizarTexto)
                     .anyMatch(i -> i.equals(filtroNorm));

            if (coincide) {
                modelo.addElement("Nombre: " + u.getNombre() +
                        " | Edad: " + u.getEdad() +
                        " | Intereses: " + String.join(", ", u.getIntereses()));
            }
        }
    }

    private String normalizarTexto(String texto) {
        if (texto == null) return "";
        String temp = java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD);
        temp = temp.replaceAll("\\p{M}", "");
        return temp.toLowerCase();
    }
    
}
