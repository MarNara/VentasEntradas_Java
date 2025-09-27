package entradas;

public class EntradaNoDisponibleException extends Exception {
	private static final long serialVersionUID = 1L;
	public EntradaNoDisponibleException() {
		super("No hay entradas disponibles para el evento.");
	}
}
