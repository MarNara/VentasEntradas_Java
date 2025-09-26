package entradas;

import java.util.Date;
import java.util.List;

public class Eventos {
    private String nombre;
    private String lugar;
    private Date fecha;
    private int capacidad;
    private String categoria; // Nueva propiedad
    private List<Ubicacion> ubicaciones; // Para manejar stock y sectores

    public Eventos(String nombre, String lugar, Date fecha, int capacidad, String categoria, List<Ubicacion> ubicaciones) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.capacidad = capacidad;
        this.categoria = categoria;
        this.ubicaciones = ubicaciones;
    }

    // ===== Getters y Setters =====
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public List<Ubicacion> getUbicaciones() { return ubicaciones; }
    public void setUbicaciones(List<Ubicacion> ubicaciones) { this.ubicaciones = ubicaciones; }
}
