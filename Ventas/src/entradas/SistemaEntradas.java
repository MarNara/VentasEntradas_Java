package entradas;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class SistemaEntradas {
    // Mapa que asocia el RUT de cada usuario con su objeto Usuarios
    private Map<String, Usuarios> usuarios;
    // Lista que almacena todos los eventos registrados
    private List<Eventos> eventos;
    // Lista que contiene todas las entradas vendidas
    private List<Entrada> entradas;

    // Carpeta donde se guardarán los archivos CSV
    private final String CARPETA_DATA = "data";

    // Busca un usuario en el mapa a partir de su RUT
    public Usuarios buscarUsuario(String rut) {
        return usuarios.get(rut);
    }
    
    // Registra un nuevo usuario directamente con su RUT (si no existe previamente)
    public void registrarUsuariosConRut(String rut, String nombre, int edad, List<String> intereses) {
        usuarios.put(rut, new Usuarios(rut, nombre, edad, intereses));
    }

    // Constructor: inicializa las estructuras de datos
    public SistemaEntradas() {
        usuarios = new HashMap<>();
        eventos = new ArrayList<>();
        entradas = new ArrayList<>();
    }

    // ====== MÉTODOS DE CARGA Y GUARDADO DE DATOS ======

    // Cargar todos los datos iniciales desde archivos CSV (usuarios, eventos, entradas)
    public void cargarDatosIniciales() {
        cargarUsuariosCSV();
        cargarEventosCSV();
        cargarEntradasCSV();
    }

    // Guardar todos los datos al salir del programa
    public void guardarDatosAlSalir() {
        guardarUsuariosCSV();
        guardarEventosCSV();
        guardarEntradasCSV();
    }

    // ====== CARGA DE DATOS DESDE ARCHIVOS CSV ======

    // Cargar usuarios desde el archivo usuarios.csv
    public void cargarUsuariosCSV() {
        File archivo = new File(CARPETA_DATA + "/usuarios.csv");
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine(); // Ignora el encabezado
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length < 4) continue;
                String rut = campos[0].trim();
                String nombre = campos[1].trim();
                int edad = Integer.parseInt(campos[2].trim());
                List<String> intereses = Arrays.asList(campos[3].split(";"));
                usuarios.put(rut, new Usuarios(rut, nombre, edad, intereses));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Cargar eventos desde el archivo eventos.csv
    public void cargarEventosCSV() {
        File archivo = new File(CARPETA_DATA + "/eventos.csv");
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine(); // Ignora el encabezado
            String linea;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length < 3) continue;
                String nombre = campos[0].trim();
                String lugar = campos[1].trim();
                Date fecha = sdf.parse(campos[2].trim());
                int capacidad = campos.length > 3 ? Integer.parseInt(campos[3].trim()) : 100;
                String categoria = campos.length > 4 ? campos[4].trim() : "General";

                // Se crean ubicaciones por defecto si no existen en el CSV
                List<Ubicacion> ubicaciones = new ArrayList<>();
                ubicaciones.add(new Ubicacion("General", 50, 0.0));
                ubicaciones.add(new Ubicacion("VIP", 30, 0.5));
                ubicaciones.add(new Ubicacion("Platea", 20, 0.2));

                registrarEvento(nombre, lugar, fecha, capacidad, categoria, ubicaciones);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Cargar entradas vendidas desde el archivo entradas_vendidas.csv
    public void cargarEntradasCSV() {
        File archivo = new File(CARPETA_DATA + "/entradas_vendidas.csv");
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine(); // Ignora el encabezado
            String linea;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length < 3) continue;

                String nombreUsuario = campos[0].trim();
                String nombreEvento = campos[1].trim();
                double precio = Double.parseDouble(campos[2].trim());

                Usuarios usuario = usuarios.get(nombreUsuario);
                Eventos evento = buscarEvento(nombreEvento);

                // Si no hay fecha en el CSV, se asigna la fecha actual
                Date fechaCompra = (campos.length > 3) ? sdf.parse(campos[3].trim()) : new Date();

                if (usuario != null && evento != null)
                    entradas.add(new Entrada(evento, usuario, precio, fechaCompra));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ====== GUARDADO DE DATOS EN ARCHIVOS CSV ======

    // Guardar todos los usuarios registrados en usuarios.csv
    public void guardarUsuariosCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CARPETA_DATA + "/usuarios.csv"))) {
            pw.println("RUT,Nombre,Edad,Intereses");
            for (Map.Entry<String, Usuarios> entry : usuarios.entrySet()) {
                String rut = entry.getKey();
                Usuarios u = entry.getValue();
                pw.println(rut + "," + u.getNombre() + "," + u.getEdad() + "," + String.join(";", u.getIntereses()));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Guardar todos los eventos en eventos.csv
    public void guardarEventosCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CARPETA_DATA + "/eventos.csv"))) {
            pw.println("Nombre,Lugar,Fecha,Capacidad,Categoria");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Eventos e : eventos) {
                pw.println(e.getNombre() + "," + e.getLugar() + "," + sdf.format(e.getFecha()) + "," +
                           e.getCapacidad() + "," + e.getCategoria());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Guardar todas las entradas vendidas en entradas_vendidas.csv
    public void guardarEntradasCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CARPETA_DATA + "/entradas_vendidas.csv"))) {
            pw.println("Usuario,Evento,Precio,FechaCompra");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            for (Entrada en : entradas) {
                pw.println(en.getUsuario().getNombre() + "," +
                           en.getEvento().getNombre() + "," +
                           en.getPrecio() + "," +
                           sdf.format(en.getFechaCompra()));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ====== FUNCIONALIDADES DEL SISTEMA ======

    // Registrar un nuevo usuario con validación de duplicados
    public void registrarUsuarios(String rut, String nombre, int edad, List<String> intereses) 
            throws IllegalArgumentException {
        if (usuarios.containsKey(rut)) {
            throw new IllegalArgumentException("Ya existe un usuario con este RUT.");
        }
        usuarios.put(rut, new Usuarios(rut, nombre, edad, intereses));
    }

    // Registrar un evento con todos sus datos
    public void registrarEvento(String nombreEvento, String lugarEvento, Date fechaEvento,
                                int capacidad, String categoria, List<Ubicacion> ubicaciones) {
        eventos.add(new Eventos(nombreEvento, lugarEvento, fechaEvento, capacidad, categoria, ubicaciones));
    }

    // Registrar un evento usando la fecha como String
    public void registrarEvento(String nombreEvento, String lugarEvento, String fechaStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = sdf.parse(fechaStr);
            int capacidad = 100; 
            String categoria = "General"; 
            List<Ubicacion> ubicaciones = new ArrayList<>();
            ubicaciones.add(new Ubicacion("General", 50, 0.0));
            ubicaciones.add(new Ubicacion("VIP", 30, 0.5));
            ubicaciones.add(new Ubicacion("Platea", 20, 0.2));
            eventos.add(new Eventos(nombreEvento, lugarEvento, fecha, capacidad, categoria, ubicaciones));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Buscar un evento por su nombre
    public Eventos buscarEvento(String nombreEvento) {
        for (Eventos e : eventos) if (e.getNombre().equalsIgnoreCase(nombreEvento)) return e;
        return null;
    }

    // Getters de las listas y mapa principales
    public List<Eventos> getEventos() { return eventos; }
    public Map<String, Usuarios> getUsuarios() { return usuarios; }
    public List<Entrada> getEntradas() { return entradas; }

    // Realiza la venta de una entrada a un usuario registrado
    public String realizarVenta(String rutUsuario, String nombreEvento) 
            throws UsuarioNoRegistradoException, 
                   EventoNoEncontradoException, 
                   EntradaNoDisponibleException {

        Usuarios usuario = buscarUsuario(rutUsuario);
        Eventos evento = buscarEvento(nombreEvento);
        if (usuario == null) throw new UsuarioNoRegistradoException();
        if (evento == null) throw new EventoNoEncontradoException();

        Ubicacion sugerida = sugerirUbicacion(usuario, evento);
        if (sugerida == null || !sugerida.hayLugaresDisponibles()) {
            throw new EntradaNoDisponibleException();
        }

        double precioBase = 100;
        double precioFinal = precioBase * (1 + sugerida.getPrecio());
        entradas.add(new Entrada(evento, usuario, precioFinal, new Date())); 
        sugerida.ocuparLugar();
        evento.setCapacidad(evento.getCapacidad() - 1);
        return "Compra realizada en " + sugerida.getNombre() + " | Precio: $" + precioFinal;
    }

    // Sugerir ubicación según la edad del usuario y disponibilidad
    public Ubicacion sugerirUbicacion(Usuarios usuario, Eventos evento) {
        List<Ubicacion> disponibles = new ArrayList<>();
        for (Ubicacion u : evento.getUbicaciones()) if (u.hayLugaresDisponibles()) disponibles.add(u);
        if (disponibles.isEmpty()) return null;
        if (usuario.getEdad() < 25) return disponibles.get(0);
        else if (usuario.getEdad() < 50) return disponibles.get(1);
        else return disponibles.get(disponibles.size() - 1);
    }
    
    // Eliminar un evento por su nombre y todos los datos asociados
    public void eliminarEvento(String nombreEvento) throws EventoNoEncontradoException {
        boolean eliminado = eventos.removeIf(ev -> ev.getNombre().equalsIgnoreCase(nombreEvento));

        if (!eliminado) {
            throw new EventoNoEncontradoException();
        }
    }

    // Modificar los datos de una ubicación existente dentro de un evento
    public void modificarUbicacion(Eventos evento, Ubicacion ubicacion, String nuevoNombre, int nuevaCapacidad, double nuevoPrecio) {
        ubicacion.setNombre(nuevoNombre);
        ubicacion.setCapacidad(nuevaCapacidad);
        ubicacion.setPrecio(nuevoPrecio);
    }

    // Eliminar una ubicación de un evento
    public void eliminarUbicacion(Eventos evento, Ubicacion ubicacion) {
        evento.getUbicaciones().remove(ubicacion);
    }
    
    // Generar un reporte en formato TXT con todos los eventos y ventas realizadas
    public boolean generarReporteEventosTXT(String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            int anchoLinea = 80; 
            int totalEntradasVendidas = 0;
            double totalDinero = 0.0;

            for (Eventos ev : eventos) {
                bw.write("Evento: " + ev.getNombre());
                bw.newLine();
                bw.write("Lugar: " + ev.getLugar());
                bw.newLine();
                bw.write("Fecha: " + sdf.format(ev.getFecha()));
                bw.newLine();
                bw.write("Capacidad: " + ev.getCapacidad());
                bw.newLine();
                bw.write("Categoría: " + ev.getCategoria());
                bw.newLine();

                // Calcular el precio promedio de las entradas vendidas
                double precio = 0.0;
                long cantidadVendida = entradas.stream().filter(en -> en.getEvento().equals(ev)).count();
                totalEntradasVendidas += cantidadVendida;

                if (cantidadVendida > 0) {
                    precio = entradas.stream()
                            .filter(en -> en.getEvento().equals(ev))
                            .findFirst()
                            .get()
                            .getPrecio();
                } else if (!ev.getUbicaciones().isEmpty()) {
                    precio = ev.getUbicaciones().get(0).getPrecio();
                }

                bw.write("Precio: $" + precio);
                bw.newLine();
                bw.write("Ventas realizadas para este evento: " + cantidadVendida);
                bw.newLine();

                // Calcular total de ventas del evento
                double totalVentas = entradas.stream()
                        .filter(en -> en.getEvento().equals(ev))
                        .mapToDouble(Entrada::getPrecio)
                        .sum();
                totalDinero += totalVentas;

                bw.write("Total ventas del evento: $" + totalVentas);
                bw.newLine();
                bw.write("---------------------------------------------------");
                bw.newLine();
            }

            // Resumen general al final del reporte
            String resumen = String.format("Total ventas: %d | Total recaudado: $%.2f", totalEntradasVendidas, totalDinero);
            int espacios = Math.max(0, anchoLinea - resumen.length());
            bw.write(" ".repeat(espacios) + resumen);
            bw.newLine();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
