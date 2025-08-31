package entradas;

import java.util.Date; //Es como la primera tarea de Estructura con esto de las fechas.
import java.util.ArrayList;//para agregar las ubicaciones del evento por vcategoria, ver si lo dividimos por edad o area vip, platea, etc..
import java.util.List;

public class Eventos {
    private String nombre;
    private String lugar;
    private Date fecha;
    private int capacidad;

    private List<Ubicacion> lugares;//coleccion anidada

    public Eventos(String nombre, String lugar, Date fecha, int capacidad) {
    	this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.capacidad = capacidad;
        this.lugares = new ArrayList<>();
    }

    public String getNombre() {
    	return nombre;
    }
    public void setNombre(String nombre) { 
    	this.nombre = nombre;;
    }

    public String getLugar() { 
    	return lugar;
    }
    public void setLugar(String lugar) {
    	this.lugar = lugar;
    }

    public Date getFecha() {
    	return fecha;
    }
    public void setFecha(Date fecha) { 
    	this.fecha = fecha;
    }

    public int getCapacidad() {
    	return capacidad;
    }
    public void setCapacidad(int capacidad) { 
    	this.capacidad = capacidad;
    }
    
    public List<Ubicacion> getUbicaciones() {
        return lugares;
    }

    public void setUbicaciones(List<Ubicacion> lugares) {
        this.lugares = lugares;
    }

    
    
}
