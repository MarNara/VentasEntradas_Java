package entradas;

public class EventoNoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;
    public EventoNoEncontradoException() {
        super("Evento no encontrado en el sistema.");
    }
}
