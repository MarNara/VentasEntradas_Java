package entradas;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class SistemaEntradas {
    private Map<String, Usuarios> usuarios; 
    private List<Eventos> eventos;
    private List<Entrada> entradas;

    public SistemaEntradas() {
        usuarios = new HashMap<>();
        eventos = new ArrayList<>();
        entradas = new ArrayList<>();
    }

    public void cargarDatosIniciales() {
        List<String> intereses = new ArrayList<>();
        intereses.add("Tecnología");
        usuarios.put("Ana", new Usuarios("Ana", 25, intereses));
        List<String> interesesLuis = new ArrayList<>();
        interesesLuis.add("Deportes");
        interesesLuis.add("Música");
        usuarios.put("Luis", new Usuarios("Luis", 30, interesesLuis));
        eventos.add(new Eventos("Charla Java", "Auditorio 1", new Date(), 50));
        
    }

    public void registrarUsuarios(String nombre, int edad, String interesesStr) {
        String[] lista = interesesStr.split(",");
        List<String> intereses = new ArrayList<>();
        for(String i : lista) intereses.add(i.trim());
        usuarios.put(nombre, new Usuarios(nombre, edad, intereses));
    }


    // Sobrecarga 1: registrar evento sin fecha, usa fecha actual
    public void registrarEvento(String nombreEvento, String lugarEvento) {
        registrarEvento(nombreEvento, lugarEvento, new Date());
    }

    // Sobrecarga 2: registrar evento con fecha y capacidad personalizada
    public void registrarEvento(String nombreEvento, String lugarEvento, Date fechaEvento, int capacidad) {
        eventos.add(new Eventos(nombreEvento, lugarEvento, fechaEvento, capacidad));
    }
    
    public void registrarEvento(String nombreEvento, String lugarEvento, Date fechaEvento) {
        eventos.add(new Eventos(nombreEvento, lugarEvento, fechaEvento, 50));
    }

    public void mostrarEventos() {
        for(Eventos e : eventos) {
            System.out.println("Evento: " + e.getNombre() + " | Lugar: " + e.getLugar() +
                               " | Fecha: " + e.getFecha() + " | Capacidad: " + e.getCapacidad());
        }
    }

    public void realizarVenta(String nombreUsuario, String nombreEvento) {
        Usuarios usuario = usuarios.get(nombreUsuario); 
        Eventos evento = null;

        for(Eventos e : eventos)
            if(e.getNombre().equalsIgnoreCase(nombreEvento)) evento = e;

        if(usuario != null && evento != null && evento.getCapacidad() > 0) {
            entradas.add(new Entrada(evento, usuario, 100));
            evento.setCapacidad(evento.getCapacidad()-1);
            System.out.println("Compra realizada con éxito.");
        } else {
            System.out.println("No se pudo realizar la compra.");
        }
    }

    public void mostrarUbicacionesDisponibles(String nombreEvento) {
        for(Eventos e : eventos)
            if(e.getNombre().equalsIgnoreCase(nombreEvento))
                System.out.println("Capacidad disponible: " + e.getCapacidad());
    }

    // Getters
    public Map<String, Usuarios> getUsuarios() { return usuarios; } // Map
    public List<Eventos> getEventos() { return eventos; }
    public List<Entrada> getEntradas() { return entradas; }
}
