package entradas;

import javax.swing.*;// importar swing para la interface
import java.awt.*;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

//ventana hija
class MenuUsuarioPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    // Modelos de listas para mostrar datos de eventos, usuarios y ventas
    private final DefaultListModel<String> modeloEventos = new DefaultListModel<>();
    private final DefaultListModel<String> modeloUsuarios = new DefaultListModel<>();
    private final DefaultListModel<String> modeloVentas = new DefaultListModel<>();
    
    // Componentes visuales JList que mostrarán la información en pantalla
    private final JList<String> listaEventos = new JList<>(modeloEventos);
    private final JList<String> listaUsuarios = new JList<>(modeloUsuarios);
    private final JList<String> listaVentas = new JList<>(modeloVentas);

    /*panel del menú de usuarios, esto contiene todo lo relacionado a los botones del menú de usuario
      y a las acciones de cada botón*/
    public MenuUsuarioPanel(SistemaEntradas sistema, CardLayout cardLayout, JPanel panelPrincipal) {
        // Usa un diseño vertical para colocar los botones uno debajo del otro
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Creación de los botones principales del menú de usuario
        JButton btnRegistrar = new JButton("Registrar Usuario");
        JButton btnEventos = new JButton("Mostrar Eventos");
        JButton btnComprar = new JButton("Comprar Entrada");
        JButton btnVolver = new JButton("Volver");

        // Configuración de tamaño y estilo de los botones
        Dimension botonGrande = new Dimension(240, 50);
        Font fuenteGrande = new Font("Arial", Font.BOLD, 18);
        for (JButton boton : new JButton[]{btnRegistrar, btnEventos, btnComprar, btnVolver}) {
            boton.setMaximumSize(botonGrande);
            boton.setFont(fuenteGrande);
            boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        // Organización de los botones dentro del panel con espacios
        add(Box.createVerticalGlue());
        add(btnRegistrar);
        add(Box.createVerticalStrut(10));
        add(btnEventos);
        add(Box.createVerticalStrut(10));
        add(btnComprar);
        add(Box.createVerticalStrut(20));
        add(btnVolver);
        add(Box.createVerticalGlue());

        // Acción del botón "Volver": regresa al panel principal usando CardLayout
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "inicio"));

        // =========================
        // REGISTRAR USUARIO
        // =========================
        btnRegistrar.addActionListener(e -> {
            // Campos de texto para capturar los datos del nuevo usuario
            JTextField txtRut = new JTextField(15);
            JTextField txtNombre = new JTextField(15);
            JTextField txtEdad = new JTextField(5);

            // Casillas de verificación para seleccionar intereses del usuario
            JCheckBox chkTecnologia = new JCheckBox("Tecnología");
            JCheckBox chkDeportes = new JCheckBox("Deportes");
            JCheckBox chkMusica = new JCheckBox("Música");
            JCheckBox chkCiencia = new JCheckBox("Ciencia");

            // Construcción del panel con todos los campos de entrada
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("RUT:")); panel.add(txtRut); panel.add(Box.createVerticalStrut(5));
            panel.add(new JLabel("Nombre:")); panel.add(txtNombre); panel.add(Box.createVerticalStrut(5));
            panel.add(new JLabel("Edad:")); panel.add(txtEdad); panel.add(Box.createVerticalStrut(5));
            panel.add(new JLabel("Intereses:")); panel.add(chkTecnologia); panel.add(chkDeportes);
            panel.add(chkMusica); panel.add(chkCiencia);

            // Muestra el cuadro de diálogo para registrar al usuario
            int result = JOptionPane.showConfirmDialog(null, panel, "Registrar Usuario",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Obtiene y valida los datos ingresados
                    String rut = txtRut.getText().trim();
                    String nombre = txtNombre.getText().trim();
                    int edad = Integer.parseInt(txtEdad.getText().trim());
                    
                    // Verificación de campos obligatorios
                    if (rut.isEmpty() || nombre.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "RUT y nombre obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Verifica si el usuario ya existe
                    if (sistema.getUsuarios().containsKey(rut)) {
                        JOptionPane.showMessageDialog(null, "Usuario ya registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Obtiene la lista de intereses seleccionados
                    List<String> intereses = new ArrayList<>();
                    if (chkTecnologia.isSelected()) intereses.add("Tecnología");
                    if (chkDeportes.isSelected()) intereses.add("Deportes");
                    if (chkMusica.isSelected()) intereses.add("Música");
                    if (chkCiencia.isSelected()) intereses.add("Ciencia");

                    // Registra al usuario en el sistema
                    sistema.registrarUsuariosConRut(rut, nombre, edad, intereses);
                    JOptionPane.showMessageDialog(null, "Usuario registrado con éxito!");
                } catch (NumberFormatException ex) {
                    // Muestra error si la edad no es un número válido
                    JOptionPane.showMessageDialog(null, "Edad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // =========================
        // MOSTRAR EVENTOS
        // =========================
        btnEventos.addActionListener(e -> {
            // Limpia la lista y la vuelve a llenar con los eventos del sistema
            modeloEventos.clear();
            for (Eventos ev : sistema.getEventos()) {
                modeloEventos.addElement(ev.getNombre() + " | " + ev.getLugar() + " | " + ev.getFecha());
            }
            // Muestra la lista de eventos en un cuadro de diálogo desplazable
            JOptionPane.showMessageDialog(null, new JScrollPane(listaEventos), "Lista de Eventos",
                    JOptionPane.PLAIN_MESSAGE);
        });

        // =========================
        // COMPRAR ENTRADA
        // =========================
        btnComprar.addActionListener(e -> {
            // Permite seleccionar un solo evento para comprar entrada
            listaEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            modeloEventos.clear();
            // Carga todos los eventos disponibles
            for (Eventos ev : sistema.getEventos()) {
                modeloEventos.addElement(ev.getNombre() + " | " + ev.getLugar() + " | " + ev.getFecha());
            }

            // Cuadro de diálogo para seleccionar el evento
            int opcion = JOptionPane.showConfirmDialog(null, new JScrollPane(listaEventos),
                    "Seleccione evento para comprar", JOptionPane.OK_CANCEL_OPTION);

            // Si se confirma la compra y hay evento seleccionado
            if (opcion == JOptionPane.OK_OPTION && !listaEventos.isSelectionEmpty()) {
                int idx = listaEventos.getSelectedIndex();
                Eventos ev = sistema.getEventos().get(idx);

                // Solicita el RUT del usuario para procesar la compra
                String rutUsuario = JOptionPane.showInputDialog(null, "Ingrese RUT de usuario:");
                try {
                    // Realiza la venta a través del sistema
                    String resultado = sistema.realizarVenta(rutUsuario, ev.getNombre());
                    JOptionPane.showMessageDialog(null, resultado, "Compra", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    // Muestra error si ocurre algún problema en la compra
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
