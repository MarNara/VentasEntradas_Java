package entradas;

import java.util.*;

public class SistemaEntradas {
    private Map<String, Usuarios> usuarios;
    private List<Eventos> eventos;
    private List<Entrada> entradas;

    public SistemaEntradas() {
        usuarios = new HashMap<>();
        eventos = new ArrayList<>();
        entradas = new ArrayList<>();
    }

    // ====== Cargar datos iniciales ======
    public void cargarDatosIniciales() {
        // Usuarios
        usuarios.put("Ana", new Usuarios("Ana", 25, List.of("Tecnología")));
        usuarios.put("Luis", new Usuarios("Luis", 30, List.of("Deportes", "Música")));

        // Eventos con categorías y ubicaciones
        List<Ubicacion> ubicacionesJava = List.of(
            new Ubicacion("Frontal", 20, 0.2),
            new Ubicacion("Medio", 30, 0.0),
            new Ubicacion("Posterior", 50, -0.1)
        );
        eventos.add(new Eventos("Charla Java", "Auditorio 1", new Date(), 100, "Tecnología", ubicacionesJava));
    }

    // ====== Registrar usuario ======
    public void registrarUsuarios(String nombre, int edad, List<String> intereses) {
        Usuarios usuario = new Usuarios(nombre, edad, intereses);
        usuarios.put(nombre, usuario);
    }

    // ====== Registrar evento ======
    public void registrarEvento(String nombreEvento, String lugarEvento, Date fechaEvento,
                                int capacidad, String categoria, List<Ubicacion> ubicaciones) {
        Eventos evento = new Eventos(nombreEvento, lugarEvento, fechaEvento, capacidad, categoria, ubicaciones);
        eventos.add(evento);
    }

    // ====== Buscar evento ======
    public Eventos buscarEvento(String nombreEvento) {
        for (Eventos e : eventos)
            if (e.getNombre().equalsIgnoreCase(nombreEvento)) return e;
        return null;
    }

    // ====== Sugerir eventos según intereses ======
    public List<Eventos> sugerirEventos(Usuarios usuario) {
        List<Eventos> sugeridos = new ArrayList<>();
        for (Eventos ev : eventos) {
            if (usuario.getIntereses().contains(ev.getCategoria())) {
                sugeridos.add(ev);
            }
        }
        return sugeridos;
    }

    // ====== Sugerir ubicación automática ======
    public Ubicacion sugerirUbicacion(Usuarios usuario, Eventos evento) {
        List<Ubicacion> disponibles = new ArrayList<>();
        for (Ubicacion u : evento.getUbicaciones()) {
            if (u.hayLugaresDisponibles()) disponibles.add(u);
        }
        if (disponibles.isEmpty()) return null;

        // Lógica simple basada en edad
        if (usuario.getEdad() < 25) return disponibles.get(0);
        else if (usuario.getEdad() < 50) return disponibles.get(1);
        else return disponibles.get(disponibles.size()-1);
    }

    // ====== Realizar venta (devuelve mensaje) ======
    public String realizarVenta(String nombreUsuario, String nombreEvento) throws UsuarioNoRegistradoException, EntradaNoDisponibleException, EventoNoEncontradoException {
        Usuarios usuario = usuarios.get(nombreUsuario);
        Eventos evento = buscarEvento(nombreEvento);

        if (usuario == null) throw new UsuarioNoRegistradoException();
        if (evento == null) throw new EventoNoEncontradoException();

        Ubicacion sugerida = sugerirUbicacion(usuario, evento);
        if (sugerida == null || !sugerida.hayLugaresDisponibles()) {
        	throw new EntradaNoDisponibleException();
        }
        double precioBase = 100;
        double precioFinal = precioBase * (1 + sugerida.getPrecio());
        entradas.add(new Entrada(evento, usuario, precioFinal));
        sugerida.ocuparLugar();
        evento.setCapacidad(evento.getCapacidad() - 1);
        return "Compra realizada en " + sugerida.getNombre() + " | Precio: $" + precioFinal;
        
    }

    // ====== Método NUEVO: mostrar ubicaciones disponibles ======
    public List<Ubicacion> mostrarUbicacionesDisponibles(String nombreEvento) {
        Eventos evento = buscarEvento(nombreEvento);
        if (evento == null) return new ArrayList<>();

        List<Ubicacion> disponibles = new ArrayList<>();
        for (Ubicacion u : evento.getUbicaciones()) {
            if (u.hayLugaresDisponibles()) {
                disponibles.add(u);
            }
        }
        return disponibles;
    }

    // ====== Obtener listas para GUI ======
    public List<String> listarUsuarios() {
        List<String> lista = new ArrayList<>();
        for (Usuarios u : usuarios.values())
            lista.add(u.getNombre() + " | Edad: " + u.getEdad() + " | Intereses: " + String.join(", ", u.getIntereses()));
        return lista;
    }

    public List<String> listarEventos() {
        List<String> lista = new ArrayList<>();
        for (Eventos e : eventos)
            lista.add(e.getNombre() + " | " + e.getLugar() + " | " + e.getFecha() + " | Capacidad: " + e.getCapacidad());
        return lista;
    }

    public List<String> listarVentas() {
        List<String> lista = new ArrayList<>();
        for (Entrada en : entradas)
            lista.add("Usuario: " + en.getUsuario().getNombre() +
                      " | Evento: " + en.getEvento().getNombre() +
                      " | Precio: $" + en.getPrecio());
        return lista;
    }

    // ====== Consultar disponibilidad simple ======
    public String consultarDisponibilidad(String nombreEvento) {
        Eventos evento = buscarEvento(nombreEvento);
        if (evento == null) return "Evento no encontrado.";
        return "Capacidad disponible: " + evento.getCapacidad();
    }

    // ====== Getters ======
    public Map<String, Usuarios> getUsuarios() { return usuarios; }
    public List<Eventos> getEventos() { return eventos; }
    public List<Entrada> getEntradas() { return entradas; }
}
