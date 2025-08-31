package entradas;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class SistemaEntradas {
    private List<Usuarios> usuarios;
    private List<Eventos> eventos;
    private List<Entrada> entradas;

    public SistemaEntradas() {
        this.usuarios = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.entradas = new ArrayList<>();
    }

    // Carga de datos iniciales de ejemplo
    public void cargarDatosIniciales() {
        // Usuarios iniciales
        List<String> intereses1 = new ArrayList<>();
        intereses1.add("Tecnología");
        intereses1.add("Negocios");
        Usuarios u1 = new Usuarios("Ana", 25, intereses1);
        usuarios.add(u1);

        List<String> intereses2 = new ArrayList<>();
        intereses2.add("Arte");
        intereses2.add("Ciencia");
        Usuarios u2 = new Usuarios("Luis", 30, intereses2);
        usuarios.add(u2);

        // Eventos iniciales
        Eventos ev1 = new Eventos("Charla de Java", "Auditorio 1", new Date(), 50);
        Eventos ev2 = new Eventos("Seminario de Física", "Sala 2", new Date(), 40);
        eventos.add(ev1);
        eventos.add(ev2);
    }

    // Registro de usuarios
    public void registrarUsuarios(String nombre, int edad, String interesesStr) {
        String[] interesesArr = interesesStr.split(",");
        List<String> intereses = new ArrayList<>();
        for (String i : interesesArr) {
            intereses.add(i.trim());
        }
        Usuarios u = new Usuarios(nombre, edad, intereses);
        usuarios.add(u);
    }

    // Registro de eventos
    public void registrarEvento(String nombreEvento, String lugar, String fechaStr) {
        // Por simplicidad convertimos fechaStr a Date como nueva Date()
        Eventos ev = new Eventos(nombreEvento, lugar, new Date(), 50); // Capacidad default
        eventos.add(ev);
    }

    // Mostrar todos los eventos
    public void mostrarEventos() {
        System.out.println("Eventos disponibles:");
        for (Eventos e : eventos) {
            System.out.println("Nombre: " + e.getNombre() + " | Lugar: " + e.getLugar() +
                               " | Fecha: " + e.getFecha() + " | Capacidad: " + e.getCapacidad());
        }
    }

    // Realizar venta de entrada
    public void realizarVenta(String nombreUsuario, String nombreEvento) {
        Usuarios usuario = null;
        Eventos evento = null;

        // Buscar usuario
        for (Usuarios u : usuarios) {
            if (u.getNombre().equalsIgnoreCase(nombreUsuario)) {
                usuario = u;
                break;
            }
        }

        // Buscar evento
        for (Eventos e : eventos) {
            if (e.getNombre().equalsIgnoreCase(nombreEvento)) {
                evento = e;
                break;
            }
        }

        if (usuario != null && evento != null) {
            Entrada entrada = new Entrada(evento, usuario, 100); // Precio fijo para ejemplo
            entradas.add(entrada);
            System.out.println("Entrada vendida a " + usuario.getNombre() + " para " + evento.getNombre());
        } else {
            System.out.println("Usuario o Evento no encontrado.");
        }
    }

    // Mostrar entradas disponibles
    public void mostrarUbicacionesDisponibles(String nombreEvento) {
        for (Eventos e : eventos) {
            if (e.getNombre().equalsIgnoreCase(nombreEvento)) {
                System.out.println("Evento: " + e.getNombre() + " | Capacidad restante: " + e.getCapacidad());
                return;
            }
        }
        System.out.println("Evento no encontrado.");
    }

    // Getters y setters
    public List<Usuarios> getUsuarios() { return usuarios; }
    public List<Eventos> getEventos() { return eventos; }
    public List<Entrada> getEntradas() { return entradas; }
}
