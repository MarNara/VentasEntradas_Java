package entradas;

import javax.swing.SwingUtilities;

public class MainVentas {
    public static void main(String[] args) {
        SistemaEntradas sistema = new SistemaEntradas();

        // Cargar datos desde CSV
        sistema.cargarUsuariosCSV();
        sistema.cargarEventosCSV();
        sistema.cargarEntradasCSV();

        SwingUtilities.invokeLater(() -> {
            new Ventanas(sistema);
        });
    }
}
