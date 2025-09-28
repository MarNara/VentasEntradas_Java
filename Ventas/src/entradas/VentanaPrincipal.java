package entradas;

import javax.swing.*;
import java.awt.*;

/*
 Clase VentanaPrincipal
 Esta clase representa la ventana principal del sistema de ventas de entradas. Gestiona la interfaz gráfica inicial del programa
 y permite navegar entre el menú de usuario, el menú de operador o salir de la aplicación. Además, se encarga de guardar los datos al cerrar.
 */
public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L; // Identificador de versión para la serialización
	private final SistemaEntradas sistema; // Referencia al sistema principal que maneja la lógica de negocio
    private final CardLayout cardLayout = new CardLayout(); // Gestor de diseño que permite cambiar entre diferentes paneles
    private final JPanel panelPrincipal = new JPanel(cardLayout); // Panel principal que contiene los distintos menús

    /*
     Constructor de VentanaPrincipal
   	 Inicializa la interfaz gráfica principal, configura los paneles y gestiona los eventos.
     */
    public VentanaPrincipal(SistemaEntradas sistema) {
        this.sistema = sistema;
        
        // Configuración básica de la ventana principal
        setTitle("Sistema de Ventas de Entradas");  // Título de la ventana
        setSize(700, 500);                          // Tamaño de la ventana
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Evita el cierre directo (se controla manualmente)
        setLocationRelativeTo(null);                // Centra la ventana en la pantalla

        // Listener que detecta cuando se intenta cerrar la ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                sistema.guardarDatosAlSalir(); // Guarda los datos antes de salir
                dispose();                      // Libera recursos de la ventana
                System.exit(0);                 // Finaliza la aplicación
            }
        });

        // --------------------------- Panel de inicio ---------------------------
        JPanel panelInicio = new JPanel(); // Panel inicial mostrado al abrir el programa
        panelInicio.setLayout(new BoxLayout(panelInicio, BoxLayout.Y_AXIS)); // Organiza componentes en columna

        // Título del sistema
        JLabel titulo = new JLabel("Bienvenido al Sistema de Ventas");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));  // Configura el estilo del título
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centra el título horizontalmente

        // Botones principales de navegación
        JButton btnUsuario = new JButton("Menú Usuario");
        JButton btnOperador = new JButton("Menú Operador");
        JButton btnSalir = new JButton("Salir");

        // Configuración estética común de los botones
        Dimension botonGrande = new Dimension(240, 50);       // Tamaño uniforme
        Font fuenteGrande = new Font("Arial", Font.BOLD, 18); // Fuente más grande
        for (JButton boton : new JButton[]{btnUsuario, btnOperador, btnSalir}) {
            boton.setMaximumSize(botonGrande);
            boton.setFont(fuenteGrande);
            boton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centra cada botón
        }

        // Añade componentes al panel de inicio con espacios verticales
        panelInicio.add(Box.createVerticalGlue());     // Espaciado flexible superior
        panelInicio.add(titulo);                      // Título del sistema
        panelInicio.add(Box.createVerticalStrut(20)); // Espacio entre título y botones
        panelInicio.add(btnUsuario);                  // Botón de acceso al menú de usuario
        panelInicio.add(Box.createVerticalStrut(10)); // Espacio entre botones
        panelInicio.add(btnOperador);                 // Botón de acceso al menú de operador
        panelInicio.add(Box.createVerticalStrut(10)); // Espacio entre botones
        panelInicio.add(btnSalir);                    // Botón para salir del sistema
        panelInicio.add(Box.createVerticalGlue());    // Espaciado flexible inferior

        // --------------------------- Creación de paneles hijos ---------------------------
        // Panel de menú para usuarios
        MenuUsuarioPanel menuUsuario = new MenuUsuarioPanel(sistema, cardLayout, panelPrincipal);
        // Panel de menú para operadores
        MenuOperadorPanel menuOperador = new MenuOperadorPanel(sistema, cardLayout, panelPrincipal);

        // --------------------------- Agregar paneles al CardLayout ---------------------------
        panelPrincipal.add(panelInicio, "inicio");          // Panel inicial
        panelPrincipal.add(menuUsuario, "menuUsuario");     // Panel del menú de usuario
        panelPrincipal.add(menuOperador, "menuOperador");   // Panel del menú de operador

        // Añade el panel principal a la ventana y muestra el panel de inicio por defecto
        add(panelPrincipal);
        cardLayout.show(panelPrincipal, "inicio");

        // --------------------------- Eventos de los botones ---------------------------
        // Cambia al panel del menú de usuario
        btnUsuario.addActionListener(e -> cardLayout.show(panelPrincipal, "menuUsuario"));
        // Cambia al panel del menú de operador
        btnOperador.addActionListener(e -> cardLayout.show(panelPrincipal, "menuOperador"));
        // Guarda los datos y cierra el sistema al presionar "Salir"
        btnSalir.addActionListener(e -> {
            sistema.guardarDatosAlSalir();
            System.exit(0);
        });

        // Muestra la ventana
        setVisible(true);
    }
}
