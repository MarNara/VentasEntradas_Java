package entradas;

import javax.swing.*;
import java.awt.*;
//import java.text.Normalizer;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.List;


public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
	private final SistemaEntradas sistema;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel panelPrincipal = new JPanel(cardLayout);

    public VentanaPrincipal(SistemaEntradas sistema) {
        this.sistema = sistema;
        setTitle("Sistema de Ventas de Entradas");
        setSize(700, 500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                sistema.guardarDatosAlSalir();
                dispose();
                System.exit(0);
            }
        });

        // Panel de inicio
        JPanel panelInicio = new JPanel();
        panelInicio.setLayout(new BoxLayout(panelInicio, BoxLayout.Y_AXIS));
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

        panelInicio.add(Box.createVerticalGlue());
        panelInicio.add(titulo);
        panelInicio.add(Box.createVerticalStrut(20));
        panelInicio.add(btnUsuario);
        panelInicio.add(Box.createVerticalStrut(10));
        panelInicio.add(btnOperador);
        panelInicio.add(Box.createVerticalStrut(10));
        panelInicio.add(btnSalir);
        panelInicio.add(Box.createVerticalGlue());

        // Crear paneles hijos
        MenuUsuarioPanel menuUsuario = new MenuUsuarioPanel(sistema, cardLayout, panelPrincipal);
        MenuOperadorPanel menuOperador = new MenuOperadorPanel(sistema, cardLayout, panelPrincipal);

        // Agregar paneles al CardLayout
        panelPrincipal.add(panelInicio, "inicio");
        panelPrincipal.add(menuUsuario, "menuUsuario");
        panelPrincipal.add(menuOperador, "menuOperador");

        add(panelPrincipal);
        cardLayout.show(panelPrincipal, "inicio");

        btnUsuario.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
        btnOperador.addActionListener(e -> cardLayout.show(panelPrincipal, "menuOperador"));
        btnSalir.addActionListener(e -> {
            sistema.guardarDatosAlSalir();
            System.exit(0);
        });

        setVisible(true);
    }
}

