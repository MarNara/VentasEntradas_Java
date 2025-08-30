package entradas;

public class Entrada {
    private Eventos evento;
    private Usuarios usuario;
    private double precio;

    public Entrada(Eventos evento, Usuarios usuario, double precio) { }

    public Eventos getEvento() { 
    	return evento;
    }
    public void setEvento(Eventos evento) { }

    public Usuarios getUsuario() {
    	return usuario;
    }
    public void setUsuario(Usuarios usuario) { }

    public double getPrecio() { 
    	return precio;
    }
    public void setPrecio(double precio) { }
}
