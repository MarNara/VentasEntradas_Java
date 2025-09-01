package entradas;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class MainVentas {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SistemaEntradas sistema = new SistemaEntradas();
        sistema.cargarDatosIniciales();

        // Lista de temas disponibles
        List<String> temasInteres = new ArrayList<>();
        temasInteres.add("Tecnología");
        temasInteres.add("Deportes");
        temasInteres.add("Música");
        temasInteres.add("Arte");
        temasInteres.add("Ciencia");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        boolean salirSistema = false;
        while(!salirSistema) {
            System.out.println("===== BIENVENIDO =====");
            System.out.println("1. Usuario");
            System.out.println("2. Operador");
            System.out.println("3. Salir");
            System.out.print("Seleccione opción: ");
            int rol = Integer.parseInt(br.readLine());

            switch(rol) {
                case 1: // Menú Usuario
                    boolean salirUsuario = false;
                    while(!salirUsuario) {
                        System.out.println("\n===== MENÚ USUARIO =====");
                        System.out.println("1. Registrar usuario");
                        System.out.println("2. Comprar entrada");
                        System.out.println("3. Mostrar eventos");
                        System.out.println("4. Mostrar disponibilidad de eventos");
                        System.out.println("5. Volver al menú principal");
                        System.out.print("Seleccione opción: ");
                        int op = Integer.parseInt(br.readLine());

                        switch(op) {
                            case 1: // Registrar usuario
                                System.out.print("Nombre: "); 
                                String n = br.readLine();
                                System.out.print("Edad: "); 
                                int e = Integer.parseInt(br.readLine());

                                System.out.println("Seleccione temas de interés (separados por coma):");
                                for(int i=0;i<temasInteres.size();i++)
                                    System.out.println((i+1) + ". " + temasInteres.get(i));
                                System.out.print("Ingrese números de los temas: ");
                                String seleccion = br.readLine();
                                String[] indices = seleccion.split(",");
                                List<String> intereses = new ArrayList<>();
                                for(String idx : indices) {
                                    int i = Integer.parseInt(idx.trim()) - 1;
                                    if(i >=0 && i < temasInteres.size()) intereses.add(temasInteres.get(i));
                                }

                                sistema.registrarUsuarios(n,e,String.join(",", intereses));
                                break;

                            case 2: // Comprar entrada
                                System.out.print("Nombre usuario: "); 
                                String u = br.readLine();

                                List<Eventos> eventos = sistema.getEventos();
                                if(eventos.isEmpty()) {
                                    System.out.println("No hay eventos disponibles.");
                                    break;
                                }
                                System.out.println("Seleccione evento:");
                                for(int i=0;i<eventos.size();i++)
                                    System.out.println((i+1) + ". " + eventos.get(i).getNombre() + 
                                                       " | " + eventos.get(i).getLugar() + 
                                                       " | " + eventos.get(i).getFecha());
                                System.out.print("Ingrese número del evento: ");
                                int evIdx = Integer.parseInt(br.readLine()) - 1;
                                if(evIdx >=0 && evIdx < eventos.size()) {
                                    Eventos evSeleccionado = eventos.get(evIdx);
                                    sistema.realizarVenta(u, evSeleccionado.getNombre());
                                } else {
                                    System.out.println("Selección inválida.");
                                }
                                break;

                            case 3: // Mostrar eventos
                                sistema.mostrarEventos();
                                break;

                            case 4: // Mostrar disponibilidad
                                System.out.print("Nombre evento: "); 
                                String evd = br.readLine();
                                sistema.mostrarUbicacionesDisponibles(evd);
                                break;

                            case 5: // Volver
                                salirUsuario = true;
                                break;
                        }
                    }
                    break;

                case 2: // Menú Operador
                    System.out.print("Nombre operador: ");
                    String nombreOp = br.readLine();
                    Operador operador = new Operador(nombreOp, sistema);

                    boolean salirOperador = false;
                    while(!salirOperador) {
                        System.out.println("\n===== MENÚ OPERADOR =====");
                        System.out.println("1. Registrar evento");
                        System.out.println("2. Mostrar usuarios");
                        System.out.println("3. Mostrar ventas");
                        System.out.println("4. Volver al menú principal");
                        System.out.print("Seleccione opción: ");
                        int op = Integer.parseInt(br.readLine());

                        switch(op) {
                            case 1: // Registrar evento
                                System.out.print("Nombre evento: "); 
                                String ne = br.readLine();
                                System.out.print("Lugar: "); 
                                String lu = br.readLine();
                                System.out.print("Fecha (dd/MM/yyyy): "); 
                                String fe = br.readLine();
                                try {
                                    Date fecha = sdf.parse(fe);
                                    operador.registrarEvento(ne, lu, fecha);
                                } catch (java.text.ParseException ex) {
                                    System.out.println("Formato de fecha inválido. Use dd/MM/yyyy.");
                                }
                                break;

                            case 2: // Mostrar usuarios
                                operador.mostrarUsuarios();
                                break;

                            case 3: // Mostrar ventas
                                operador.mostrarVentas();
                                break;

                            case 4: // Volver
                                salirOperador = true;
                                break;
                        }
                    }
                    break;

                case 3:
                    salirSistema = true;
                    break;
            }
        }
    }
}
