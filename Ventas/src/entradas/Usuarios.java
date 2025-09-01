package entradas;

import java.util.ArrayList;
import java.util.List;

public class Usuarios {
    private String nombre;
    private int edad;
    private List<String> intereses;

    public Usuarios(String nombre, int edad, List<String> intereses) {
        this.nombre = nombre;
        this.edad = edad;
        if (intereses == null) {
            this.intereses = new ArrayList<>();
        } else {
            this.intereses = intereses;
        }
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public List<String> getIntereses() { return intereses; }
    public void setIntereses(List<String> intereses) { this.intereses = intereses; }

    public void agregarInteres(String interes) {
        intereses.add(interes);
    }
}
