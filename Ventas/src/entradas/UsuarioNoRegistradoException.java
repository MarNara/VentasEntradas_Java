package entradas;

public class UsuarioNoRegistradoException extends Exception {
	private static final long serialVersionUID = 1L;
	public UsuarioNoRegistradoException() {
		super("Usuario no registrado en el sistema.");
	}
}
