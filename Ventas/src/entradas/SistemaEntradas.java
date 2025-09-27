package entradas;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class SistemaEntradas {
    private Map<String, Usuarios> usuarios;
    private List<Eventos> eventos;
    private List<Entrada> entradas;

    private final String CARPETA_DATA = "data";

    public SistemaEntradas() {
        usuarios = new HashMap<>();
        eventos = new ArrayList<>();
        entradas = new ArrayList<>();
    }

    // =========================
    // MÉTODOS BATCH
    // =========================
    public void cargarDatosIniciales() {
        cargarUsuariosCSV();
        cargarEventosCSV();
        cargarEntradasCSV();
    }

    public void guardarDatosAlSalir() {
        guardarUsuariosCSV();
        guardarEventosCSV();
        guardarEntradasCSV();
    }

    // =========================
    // CARGA DESDE CSV
    // =========================
    public void cargarUsuariosCSV() {
        File archivo = new File(CARPETA_DATA + "/usuarios.csv");
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine(); // encabezado
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length < 3) continue;
                String nombre = campos[0].trim();
                int edad = Integer.parseInt(campos[1].trim());
                List<String> intereses = Arrays.asList(campos[2].split(";"));
                usuarios.put(nombre, new Usuarios(nombre, edad, intereses));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void cargarEventosCSV() {
        File archivo = new File(CARPETA_DATA + "/eventos.csv");
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine(); // encabezado
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

                // 🔹 Aquí creamos ubicaciones por defecto (para que nunca queden vacías)
                List<Ubicacion> ubicaciones = new ArrayList<>();
                ubicaciones.add(new Ubicacion("General", 50, 0.0));
                ubicaciones.add(new Ubicacion("VIP", 30, 0.5));
                ubicaciones.add(new Ubicacion("Platea", 20, 0.2));

                registrarEvento(nombre, lugar, fecha, capacidad, categoria, ubicaciones);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void cargarEntradasCSV() {
        File archivo = new File(CARPETA_DATA + "/entradas_vendidas.csv");
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine(); // encabezado
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length < 3) continue;
                String nombreUsuario = campos[0].trim();
                String nombreEvento = campos[1].trim();
                double precio = Double.parseDouble(campos[2].trim());

                Usuarios usuario = usuarios.get(nombreUsuario);
                Eventos evento = buscarEvento(nombreEvento);

                if (usuario != null && evento != null)
                    entradas.add(new Entrada(evento, usuario, precio));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // =========================
    // GUARDAR EN CSV
    // =========================
    public void guardarUsuariosCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CARPETA_DATA + "/usuarios.csv"))) {
            pw.println("Nombre,Edad,Intereses");
            for (Usuarios u : usuarios.values()) {
                pw.println(u.getNombre() + "," + u.getEdad() + "," + String.join(";", u.getIntereses()));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

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

    public void guardarEntradasCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CARPETA_DATA + "/entradas_vendidas.csv"))) {
            pw.println("Usuario,Evento,Precio");
            for (Entrada en : entradas) {
                pw.println(en.getUsuario().getNombre() + "," + en.getEvento().getNombre() + "," + en.getPrecio());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ====== Funciones de registro, venta, listado etc. ======
    public void registrarUsuarios(String nombre, int edad, List<String> intereses) {
        usuarios.put(nombre, new Usuarios(nombre, edad, intereses));
    }

    public void registrarEvento(String nombreEvento, String lugarEvento, Date fechaEvento,
                                int capacidad, String categoria, List<Ubicacion> ubicaciones) {
        eventos.add(new Eventos(nombreEvento, lugarEvento, fechaEvento, capacidad, categoria, ubicaciones));
    }

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

    public Eventos buscarEvento(String nombreEvento) {
        for (Eventos e : eventos) if (e.getNombre().equalsIgnoreCase(nombreEvento)) return e;
        return null;
    }

    public List<Eventos> getEventos() { return eventos; }
    public Map<String, Usuarios> getUsuarios() { return usuarios; }
    public List<Entrada> getEntradas() { return entradas; }

    public String realizarVenta(String nombreUsuario, String nombreEvento) {
        Usuarios usuario = usuarios.get(nombreUsuario);
        Eventos evento = buscarEvento(nombreEvento);
        if (usuario == null) return "Usuario no encontrado.";
        if (evento == null) return "Evento no encontrado.";

        Ubicacion sugerida = sugerirUbicacion(usuario, evento);
        if (sugerida != null && sugerida.hayLugaresDisponibles()) {
            double precioBase = 100;
            double precioFinal = precioBase * (1 + sugerida.getPrecio());
            entradas.add(new Entrada(evento, usuario, precioFinal));
            sugerida.ocuparLugar();
            evento.setCapacidad(evento.getCapacidad() - 1);
            return "Compra realizada en " + sugerida.getNombre() + " | Precio: $" + precioFinal;
        } else return "No hay ubicaciones disponibles para este evento.";
    }

    public Ubicacion sugerirUbicacion(Usuarios usuario, Eventos evento) {
        List<Ubicacion> disponibles = new ArrayList<>();
        for (Ubicacion u : evento.getUbicaciones()) if (u.hayLugaresDisponibles()) disponibles.add(u);
        if (disponibles.isEmpty()) return null;
        if (usuario.getEdad() < 25) return disponibles.get(0);
        else if (usuario.getEdad() < 50) return disponibles.get(1);
        else return disponibles.get(disponibles.size() - 1);
    }
}
