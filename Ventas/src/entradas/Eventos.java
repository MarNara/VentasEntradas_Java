package entradas;
import java.util.Date;

public class Eventos {
    private String nombre;
    private String lugar;
    private Date fecha;
    private int capacidad;

    public Eventos(String nombre, String lugar, Date fecha, int capacidad) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.capacidad = capacidad;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
}
