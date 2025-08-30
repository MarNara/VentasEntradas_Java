package entradas;

public class Ubicacion {
    private String nombre;     // Ej: Nombre del sector
    private int capacidad;
    private double precio;     // Precio de la ubicación

    // Constructor
    public Ubicacion(String nombre, int capacidad, double precio) { }

    // Getters y Setters
    public String getNombre() { }
    public void setNombre(String nombre) { }

    public int getCapacidad() { }
    public void setCapacidad(int capacidad) { }

    public double getPrecio() { }
    public void setPrecio(double precio) { }

    // Métodos
    public boolean hayLugaresDisponibles() { }
    public void ocuparLugar() { }
}