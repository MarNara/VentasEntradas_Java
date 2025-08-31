package entradas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;

public class MainVentas {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SistemaEntradas sistema = new SistemaEntradas();
        sistema.cargarDatosIniciales();

        boolean salir = false;

        while (!salir) {
            try {
                limpiarPantalla();
                System.out.println("=== SISTEMA DE VENTA DE ENTRADAS ===");
                System.out.println("1. Menu Usuario");
                System.out.println("2. Menu Operador");
                System.out.println("3. Salir");
                System.out.print("Seleccione opción: ");
                int opcion = Integer.parseInt(br.readLine());

                switch (opcion) {
                    case 1: menuUsuario(sistema, br);
                    case 2: menuOperador(sistema, br);
                    case 3: salir = true;
                    default: System.out.println("Opción inválida.");
                }

            } catch (IOException e) {
                System.out.println("Error de entrada/salida.");
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }

    // ===================== Menú Usuario =====================
    private static void menuUsuario(SistemaEntradas sistema, BufferedReader br) throws IOException {
        limpiarPantalla();
        System.out.println("=== MENU USUARIO ===");
        System.out.println("1. Registrar Usuario");
        System.out.println("2. Ver Temas y agregar intereses");
        System.out.println("3. Comprar entrada");
        System.out.println("4. Ver eventos");
        System.out.println("5. Volver");
        System.out.print("Seleccione opción: ");
        int op = Integer.parseInt(br.readLine());

        switch (op) {
            case 1: {
                System.out.print("Nombre: ");
                String nombre = br.readLine();
                System.out.print("Edad: ");
                int edad = Integer.parseInt(br.readLine());
                sistema.registrarUsuarios(nombre, edad);
                System.out.println("Usuario registrado.");
                br.readLine();
            }
            case 2: {
                System.out.print("Nombre del usuario: ");
                String nombre = br.readLine();
                Usuarios u = sistema.getUsuarios().stream().filter(us -> us.getNombre().equals(nombre)).findFirst().orElse(null);
                if (u != null) {
                    List<String> temas = sistema.getTemasInteres();
                    System.out.println("=== Temas disponibles ===");
                    for (int i = 0; i < temas.size(); i++) {
                        System.out.println((i + 1) + ". " + temas.get(i));
                    }
                    System.out.print("Seleccione el número del tema a agregar: ");
                    int sel = Integer.parseInt(br.readLine());
                    if (sel >= 1 && sel <= temas.size()) {
                        u.agregarInteres(temas.get(sel - 1));
                        System.out.println("Interés agregado.");
                    }
                    br.readLine();
                }
            }
            case 3: {
                System.out.print("Nombre del usuario: ");
                String usuarioCompra = br.readLine();
                System.out.print("Nombre del evento: ");
                String eventoCompra = br.readLine();
                sistema.realizarVenta(usuarioCompra, eventoCompra);
                br.readLine();
            }
            case 4: {
                sistema.mostrarEventos();
                br.readLine();
            }
            default: System.out.println("Volviendo...");
        }
    }

    // ===================== Menú Operador =====================
    private static void menuOperador(SistemaEntradas sistema, BufferedReader br) throws IOException {
        limpiarPantalla();
        System.out.println("=== MENU OPERADOR ===");
        System.out.println("1. Crear Evento");
        System.out.println("2. Ver eventos");
        System.out.println("3. Volver");
        System.out.print("Seleccione opción: ");
        int op = Integer.parseInt(br.readLine());

        switch (op) {
            case 1: {
                System.out.print("Nombre evento: ");
                String ne = br.readLine();
                System.out.print("Lugar evento: ");
                String le = br.readLine();
                System.out.print("Fecha evento (dd/mm/yyyy HH:mm): ");
                String fe = br.readLine();
                System.out.print("Costo base: ");
                double costo = Double.parseDouble(br.readLine());
                sistema.registrarEvento(ne, le, fe, costo);
                System.out.println("Evento creado.");
                br.readLine();
            }
            case 2: {
                sistema.mostrarEventos();
                br.readLine();
            }
            default: System.out.println("Volviendo...");
        }
    }

    // ===================== Limpieza de pantalla =====================
    public static void limpiarPantalla() {
        for (int i = 0; i < 50; i++) System.out.println();
    }
}
