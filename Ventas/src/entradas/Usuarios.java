package entradas;

import java.util.ArrayList; //falta ver diferencias y porque usar uno o el otro
import java.util.List;

public class Usuarios {
	//QUIZAS SI HAGA FALTA UN ID POR LA REPETICION DE NOMBRES, LO CUAL PODRIA SER EL RUT
    private String nombre;
    private int edad;
    private List<String> intereses;

    public Usuarios(String nombre, int edad, List<String> intereses) {
        this.nombre = nombre;
        this.edad = edad;
        this.intereses = intereses;
    }

    // Getters y setters
    public String getNombre()
    { 
        return nombre;
    }
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public int getEdad()
    {
        return edad;
    }
    public void setEdad(int edad)
    {
        this.edad = edad;
    }

    public List<String> getIntereses()
    {
        return intereses;
    }
    public void setIntereses(List<String> intereses)
    {
        this.intereses = intereses;
    }

    // Agregar un interés
    public void agregarInteres(String interes) {
        if (intereses == null) {
            intereses = new ArrayList<>();
        }
        intereses.add(interes);
    }
}
