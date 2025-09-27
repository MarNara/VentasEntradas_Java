package entradas;

import java.util.ArrayList;
import java.util.List;

public class Usuarios {
	private String rut;
    private String nombre;
    private int edad;
    private List<String> intereses;

    public Usuarios(String rut, String nombre, int edad, List<String> intereses) {
        this.rut = rut;
    	this.nombre = nombre;
        this.edad = edad;
        if (intereses == null) {
            this.intereses = new ArrayList<>();
        } else {
            this.intereses = intereses;
        }
    }

    // ===== Getters y Setters =====
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public List<String> getIntereses() { return intereses; }
    public void setIntereses(List<String> intereses) { this.intereses = intereses; }

    // Agregar un interés individual
    public void agregarInteres(String interes) {
        if(!intereses.contains(interes)) {
            intereses.add(interes);
        }
    }
}
