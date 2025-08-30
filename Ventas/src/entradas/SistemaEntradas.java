package entradas;
import java.util.List;

public class SistemaEntradas {
    private List<Usuarios> usuarios;
    private List<Eventos> eventos;
    private List<Entrada> entradas;

    public SistemaEntradas() { }

    // Métodos para Main
    public void cargarDatosIniciales() { }
    public void registrarUsuarios(String nombre, int edad, String intereses) { }
    public void registrarEvento(String nombreEvento, String categoria, String fecha) { }
    public void mostrarEventos() { }
    public void realizarVenta(String nombreUsuario, String nombreEvento) { }
    public void mostrarUbicacionesDisponibles(String nombreEvento) { }

    // Métodos auxiliares opcionales
    public void agregarUsuario(Usuarios usuario) { }
    public void agregarEvento(Eventos evento) { }
    public void venderEntrada(Usuarios usuario, Eventos evento, double precio) { }

    public List<Usuarios> getUsuarios() { return usuarios; }
    public List<Eventos> getEventos() { return eventos; }
    public List<Entrada> getEntradas() { return entradas; }
}

