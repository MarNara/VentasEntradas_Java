package entradas;

public class Entrada {
    private Eventos evento;
    private Usuarios usuario;
    private double precio;

    public Entrada(Eventos evento, Usuarios usuario, double precio) {
        this.evento = evento;
        this.usuario = usuario;
        this.precio = precio;
    }

    public Eventos getEvento() { return evento; }
    public void setEvento(Eventos evento) { this.evento = evento; }

    public Usuarios getUsuario() { return usuario; }
    public void setUsuario(Usuarios usuario) { this.usuario = usuario; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}