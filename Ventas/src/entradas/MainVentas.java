package entradas;
import javax.swing.SwingUtilities;

public class MainVentas {
    public static void main(String[] args) {
        SistemaEntradas sistema = new SistemaEntradas();
        sistema.cargarDatosIniciales();

        SwingUtilities.invokeLater(() -> {
            Ventanas v = new Ventanas(sistema);
            v.setVisible(true);
        });
    }
}
