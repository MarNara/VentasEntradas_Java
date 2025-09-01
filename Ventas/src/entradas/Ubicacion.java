package entradas;

public class Ubicacion {
    private String nombre;
    private int capacidad;
    private double precio;

    public Ubicacion(String nombre, int capacidad, double precio) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public boolean hayLugaresDisponibles() { return capacidad > 0; }
    public void ocuparLugar() { if(capacidad>0) capacidad--; }
}
