package entradas;

import java.util.Date; //Es como la primera tarea de Estructura con esto de las fechas.

public class Eventos {
    private String nombre;
    private String lugar;
    private Date fecha;
    private int capacidad;

    public Eventos(String nombre, String lugar, Date fecha, int capacidad) { }

    public String getNombre() {
    	return nombre;
    }
    public void setNombre(String nombre) { }

    public String getLugar() { 
    	return lugar;
    }
    public void setLugar(String lugar) { }

    public Date getFecha() {
    	return fecha;
    }
    public void setFecha(Date fecha) { 
    }

    public int getCapacidad() {
    	return capacidad;
    }
    public void setCapacidad(int capacidad) { }
}
