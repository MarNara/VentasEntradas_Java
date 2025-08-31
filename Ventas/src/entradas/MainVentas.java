package entradas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class MainVentas {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        SistemaEntradas sistema = new SistemaEntradas();
        sistema.cargarDatosIniciales(); // precarga de eventos, ubicaciones y usuarios

        boolean salir = false;

        while (!salir) {
            try {
            	limpiarPantalla();//esto hace que la primera parte tenga el tremeno espacio para que no este pegado
                // 2. Menú principal  NO SE QUE TAN NECESARIA SEA LA PARTE DE AGREGAR EVENTO
                System.out.println("===== SISTEMA DE VENTA DE ENTRADAS =====");
                System.out.println("1. Registrar nuevo usuario");
                System.out.println("2. Registrar nuevo evento");
                System.out.println("3. Mostrar eventos disponibles");
                System.out.println("4. Comprar entrada");
                System.out.println("5. Mostrar disponibilidad de ubicaciones");
                System.out.println("6. Salir");
                System.out.print("Seleccione una opción: ");
               

                int opcion = Integer.parseInt(br.readLine());

                switch (opcion) {
                    case 1:
                        // Registrar nuevo usuario
                        System.out.print("Nombre del usuario: ");
                        String nombre = br.readLine();
                        System.out.print("Edad: ");
                        int edad = Integer.parseInt(br.readLine());
                        System.out.println("Ingrese intereses separados por coma: ");
                        String intereses = br.readLine();
                        sistema.registrarUsuarios(nombre, edad, intereses);
                        System.out.println("Usuario registrado exitosamente.");
                        System.out.println("Presione ENTER para volver al menú...");
                        br.readLine(); // pausa hasta que el usuario presione Enter
                        limpiarPantalla();
                        break;

                    case 2:
                        // Registrar nuevo evento
                        System.out.print("Nombre del evento: ");
                        String nombreEvento = br.readLine();
                        System.out.print("Categoría del evento: ");
                        String categoria = br.readLine();
                        System.out.print("Fecha del evento: ");
                        String fecha = br.readLine();
                        sistema.registrarEvento(nombreEvento, categoria, fecha);
                        System.out.println("Evento registrado exitosamente.");
                        System.out.println("Presione ENTER para volver al menú...");
                        br.readLine(); // pausa hasta que el usuario presione Enter
                        limpiarPantalla();
                        break;

                    case 3:
                        // Mostrar eventos disponibles
                        sistema.mostrarEventos();
                        break;

                    case 4:
                        // Comprar entrada con sugerencia automática
                        System.out.print("Nombre del usuario: ");
                        String usuarioCompra = br.readLine();
                        System.out.print("Nombre del evento: ");
                        String eventoCompra = br.readLine();
                        sistema.realizarVenta(usuarioCompra, eventoCompra);
                        System.out.println("Entrada comprada exitosamente.");
                        System.out.println("Presione ENTER para volver al menú...");
                        br.readLine(); // pausa hasta que el usuario presione Enter
                        limpiarPantalla();
                        break;

                    case 5:
                        // Mostrar disponibilidad de ubicaciones de un evento
                        System.out.print("Nombre del evento: ");
                        String eventoDisponibilidad = br.readLine();
                        sistema.mostrarUbicacionesDisponibles(eventoDisponibilidad);
                        br.readLine(); // pausa hasta que el usuario presione Enter
                        limpiarPantalla();
                        break;

                    case 6:
                        System.out.println("Saliendo del sistema...");
                        salir = true;
                        break;

                    default:
                        System.out.println("Opción inválida, intente nuevamente.");
                }

                System.out.println();

            } catch (IOException e) {
                System.out.println("Error de entrada/salida. Intente nuevamente.");
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }
    public static void limpiarPantalla() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

}