package entradas;

import javax.swing.*;
import java.awt.*;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

//ventana hija
class MenuUsuarioPanel extends JPanel {
 private static final long serialVersionUID = 1L;
 private final DefaultListModel<String> modeloEventos = new DefaultListModel<>();
 private final DefaultListModel<String> modeloUsuarios = new DefaultListModel<>();
 private final DefaultListModel<String> modeloVentas = new DefaultListModel<>();
 private final JList<String> listaEventos = new JList<>(modeloEventos);
 private final JList<String> listaUsuarios = new JList<>(modeloUsuarios);
 private final JList<String> listaVentas = new JList<>(modeloVentas);

 public MenuUsuarioPanel(SistemaEntradas sistema, CardLayout cardLayout, JPanel panelPrincipal) {
     setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

     JButton btnRegistrar = new JButton("Registrar Usuario");
     JButton btnEventos = new JButton("Mostrar Eventos");
     JButton btnComprar = new JButton("Comprar Entrada");
     JButton btnVolver = new JButton("Volver");

     Dimension botonGrande = new Dimension(240, 50);
     Font fuenteGrande = new Font("Arial", Font.BOLD, 18);
     for (JButton boton : new JButton[]{btnRegistrar, btnEventos, btnComprar, btnVolver}) {
         boton.setMaximumSize(botonGrande);
         boton.setFont(fuenteGrande);
         boton.setAlignmentX(Component.CENTER_ALIGNMENT);
     }

     add(Box.createVerticalGlue());
     add(btnRegistrar);
     add(Box.createVerticalStrut(10));
     add(btnEventos);
     add(Box.createVerticalStrut(10));
     add(btnComprar);
     add(Box.createVerticalStrut(20));
     add(btnVolver);
     add(Box.createVerticalGlue());

     btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "inicio"));

     // =========================
     // REGISTRAR USUARIO
     // =========================
     btnRegistrar.addActionListener(e -> {
         JTextField txtRut = new JTextField(15);
         JTextField txtNombre = new JTextField(15);
         JTextField txtEdad = new JTextField(5);

         JCheckBox chkTecnologia = new JCheckBox("Tecnología");
         JCheckBox chkDeportes = new JCheckBox("Deportes");
         JCheckBox chkMusica = new JCheckBox("Música");
         JCheckBox chkCiencia = new JCheckBox("Ciencia");

         JPanel panel = new JPanel();
         panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
         panel.add(new JLabel("RUT:")); panel.add(txtRut); panel.add(Box.createVerticalStrut(5));
         panel.add(new JLabel("Nombre:")); panel.add(txtNombre); panel.add(Box.createVerticalStrut(5));
         panel.add(new JLabel("Edad:")); panel.add(txtEdad); panel.add(Box.createVerticalStrut(5));
         panel.add(new JLabel("Intereses:")); panel.add(chkTecnologia); panel.add(chkDeportes);
         panel.add(chkMusica); panel.add(chkCiencia);

         int result = JOptionPane.showConfirmDialog(null, panel, "Registrar Usuario",
                 JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
         if (result == JOptionPane.OK_OPTION) {
             try {
                 String rut = txtRut.getText().trim();
                 String nombre = txtNombre.getText().trim();
                 int edad = Integer.parseInt(txtEdad.getText().trim());
                 if (rut.isEmpty() || nombre.isEmpty()) {
                     JOptionPane.showMessageDialog(null, "RUT y nombre obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                     return;
                 }
                 if (sistema.getUsuarios().containsKey(rut)) {
                     JOptionPane.showMessageDialog(null, "Usuario ya registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                     return;
                 }
                 List<String> intereses = new ArrayList<>();
                 if (chkTecnologia.isSelected()) intereses.add("Tecnología");
                 if (chkDeportes.isSelected()) intereses.add("Deportes");
                 if (chkMusica.isSelected()) intereses.add("Música");
                 if (chkCiencia.isSelected()) intereses.add("Ciencia");
                 sistema.registrarUsuariosConRut(rut, nombre, edad, intereses);
                 JOptionPane.showMessageDialog(null, "Usuario registrado con éxito!");
             } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(null, "Edad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
             }
         }
     });

     // =========================
     // MOSTRAR EVENTOS
     // =========================
     btnEventos.addActionListener(e -> {
         modeloEventos.clear();
         for (Eventos ev : sistema.getEventos()) {
             modeloEventos.addElement(ev.getNombre() + " | " + ev.getLugar() + " | " + ev.getFecha());
         }
         JOptionPane.showMessageDialog(null, new JScrollPane(listaEventos), "Lista de Eventos",
                 JOptionPane.PLAIN_MESSAGE);
     });

     // =========================
     // COMPRAR ENTRADA
     // =========================
     btnComprar.addActionListener(e -> {
         listaEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         modeloEventos.clear();
         for (Eventos ev : sistema.getEventos()) {
             modeloEventos.addElement(ev.getNombre() + " | " + ev.getLugar() + " | " + ev.getFecha());
         }
         int opcion = JOptionPane.showConfirmDialog(null, new JScrollPane(listaEventos),
                 "Seleccione evento para comprar", JOptionPane.OK_CANCEL_OPTION);
         if (opcion == JOptionPane.OK_OPTION && !listaEventos.isSelectionEmpty()) {
             int idx = listaEventos.getSelectedIndex();
             Eventos ev = sistema.getEventos().get(idx);
             String rutUsuario = JOptionPane.showInputDialog(null, "Ingrese RUT de usuario:");
             try {
                 String resultado = sistema.realizarVenta(rutUsuario, ev.getNombre());
                 JOptionPane.showMessageDialog(null, resultado, "Compra", JOptionPane.INFORMATION_MESSAGE);
             } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
             }
         }
     });
 }
}

