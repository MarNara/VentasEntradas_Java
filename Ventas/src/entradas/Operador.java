package entradas;

public class Operador {
    private String nombre;

    public Operador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public void crearEvento(SistemaEntradas sistema, String nombreEvento, String lugar, String fecha, double costoBase) {
        sistema.registrarEvento(nombreEvento, lugar, fecha, costoBase);
    }
}

