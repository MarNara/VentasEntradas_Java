package entradas;
import java.util.List;


public class SistemaEntradas {
    private List<Usuarios> usuarios;
    private List<Eventos> eventos;
    private List<Entrada> entradas;

    public SistemaEntradas() { }

    public void agregarUsuario(Usuarios usuario) { }
    public void agregarEvento(Eventos evento) { }
    public void venderEntrada(Usuario usuario, Eventos evento, double precio) { }

    public List<Usuarios> getUsuarios() { }
    public List<Eventos> getEventos() { }
    public List<Entrada> getEntradas() { }
}
