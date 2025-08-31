package entradas;

import java.util.ArrayList;
import java.util.List;

public class SistemaEntradas {
    private List<Usuarios> usuarios = new ArrayList<>();
    private List<Eventos> eventos = new ArrayList<>();
    private List<Entrada> entradas = new ArrayList<>();

    // Lista global de temas de interés
    private List<String> temasInteres = List.of("Tecnología", "Música", "Ciencia", "Arte", "Deportes", "Cine");

    public void cargarDatosIniciales() {
        // Ejemplo de precarga
        Usuarios u = new Usuarios("Ana", 25);
        u.agregarInteres("Ciencia");
        usuarios.add(u);

        Eventos e = new Eventos("Charla AI", "Auditorio 1", "10/09/2025 18:00", 500);
        eventos.add(e);
    }

    public void registrarUsuarios(String nombre, int edad) {
        usuarios.add(new Usuarios(nombre, edad));
    }

    public void registrarEvento(String nombreEvento, String lugar, String fecha, double costoBase) {
        eventos.add(new Eventos(nombreEvento, lugar, fecha, costoBase));
    }

    public void mostrarEventos() {
        System.out.println("=== Eventos disponibles ===");
        for (Eventos e : eventos) {
            System.out.println("Evento: " + e.getNombre() + " | Lugar: " + e.getLugar() + " | Fecha: " + e.getFecha() + " | Costo Base: $" + e.getCostoBase());
        }
    }

    public void mostrarTemas() {
        System.out.println("=== Temas disponibles ===");
        for (int i = 0; i < temasInteres.size(); i++) {
            System.out.println((i + 1) + ". " + temasInteres.get(i));
        }
    }

    public List<String> getTemasInteres() { return temasInteres; }

    public void realizarVenta(String nombreUsuario, String nombreEvento) {
        Usuarios usuario = usuarios.stream().filter(u -> u.getNombre().equals(nombreUsuario)).findFirst().orElse(null);
        Eventos evento = eventos.stream().filter(e -> e.getNombre().equals(nombreEvento)).findFirst().orElse(null);

        if (usuario != null && evento != null) {
            entradas.add(new Entrada(evento, usuario, evento.getCostoBase()));
            System.out.println("Compra realizada correctamente para " + usuario.getNombre());
        } else {
            System.out.println("Usuario o Evento no encontrado.");
        }
    }

    public List<Usuarios> getUsuarios() { return usuarios; }
    public List<Eventos> getEventos() { return eventos; }
    public List<Entrada> getEntradas() { return entradas; }
}
