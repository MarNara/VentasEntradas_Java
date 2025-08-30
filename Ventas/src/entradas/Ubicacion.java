package entradas;

public class Ubicacion {
    private String nombre;     // Ej: Nombre del sector
    private int capacidad;
    private double precio;     // Precio de la ubicación

    // Constructor
    public Ubicacion(String nombre, int capacidad, double precio) { 
    	this.nombre = nombre;
    	this.capacidad = capacidad;
    	this.precio = precio;
    }

    // Getters y Setters
    public String getNombre() {
    	return nombre;
    }
    public void setNombre(String nombre) {
    	this.nombre = nombre;
    }

    public int getCapacidad() { 
    	return capacidad;
    }
    public void setCapacidad(int capacidad) { 
    	this.capacidad = capacidad;
    }

    public double getPrecio() { 
    	return precio;
    }
    public void setPrecio(double precio) { 
    	this.precio = precio;
    }

    // Métodos
    public boolean hayLugaresDisponibles() { 
    	return capacidad > 0;
    }
    
    //sugerencia para ver el tema de los lugares, igual ver si nos conviene otra forma de plantearlo.
    public void ocuparLugar() { 
    	if (capacidad > 0) {
            capacidad--;
        } else {
            System.out.println("No hay lugares disponibles en " + nombre);
        }
    }
 
}