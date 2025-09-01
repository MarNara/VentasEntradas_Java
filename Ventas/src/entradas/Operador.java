package entradas;
import java.util.Map;

public class Operador {
    private String nombre;
    private SistemaEntradas sistema;

    public Operador(String nombre, SistemaEntradas sistema) {
        this.nombre = nombre;
        this.sistema = sistema;
    }

    public void registrarEvento(String nombreEvento, String lugarEvento, java.util.Date fechaEvento) {
        sistema.registrarEvento(nombreEvento, lugarEvento, fechaEvento);
        System.out.println("Evento registrado correctamente por el operador.");
    }

    public void mostrarUsuarios() {
        Map<String, Usuarios> usuarios = sistema.getUsuarios(); 
        for (Usuarios u : usuarios.values()) {                  
            System.out.println("Nombre: " + u.getNombre() + " | Edad: " + u.getEdad());
        }
    }
    // SOBRECARGA: mostrar usuarios que tienen un interés específico
    public void mostrarUsuarios(String temaInteres) {
        Map<String, Usuarios> usuarios = sistema.getUsuarios();
        for (Usuarios u : usuarios.values()) {
            if (u.getIntereses().contains(temaInteres)) {
                System.out.println("Nombre: " + u.getNombre() + " | Edad: " + u.getEdad() +
                                   " | Intereses: " + String.join(", ", u.getIntereses()));
            }
        }
    }
    public void mostrarVentas() {
        for (Entrada en : sistema.getEntradas()) {
            System.out.println("Usuario: " + en.getUsuario().getNombre() +
                               " | Evento: " + en.getEvento().getNombre() +
                               " | Precio: " + en.getPrecio());
        }
    }
}