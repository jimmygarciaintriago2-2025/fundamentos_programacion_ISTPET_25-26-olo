package GestionClientes;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Sistema de Gestión de Clientes con persistencia categorizada.
 * Se implementan principios de modularización y manejo de excepciones.
 */
public class GestionClientes
{
    
    // Constantes de Configuración
    private static final int MAX_CLIENTES = 100;
    private static final String FILE_PREFIX = "clientes_";
    private static final String FILE_EXT = ".txt";
    
    // Categorías
    private static final String CAT_PREMIUM = "Premium";
    private static final String CAT_REGULAR = "Regular";
    private static final String CAT_NUEVO = "Nuevo";
    private static final String[] CATEGORIAS_VALIDAS = {CAT_PREMIUM, CAT_REGULAR, CAT_NUEVO};

    // Estructuras de Datos
    private static String[] nombres = new String[MAX_CLIENTES];
    private static String[] cedulas = new String[MAX_CLIENTES];
    private static String[] emails = new String[MAX_CLIENTES];
    private static String[] categorias = new String[MAX_CLIENTES];
    private static int totalClientes = 0;

    public static void main(String[] args) {
        cargarDatosDesdeDisco();
        ejecutarMenuPrincipal();
    }

    // --- LÓGICA DE NEGOCIO ---

    public static boolean registrarCliente(String nombre, String cedula, String email, String categoria) {
        if (totalClientes >= MAX_CLIENTES) {
            System.out.println("Error: Capacidad máxima alcanzada.");
            return false;
        }
        if (buscarPorCedula(cedula) != -1) {
            System.out.println("Error: La cédula ya está registrada.");
            return false;
        }

        nombres[totalClientes] = nombre;
        cedulas[totalClientes] = cedula;
        emails[totalClientes] = email;
        categorias[totalClientes] = (categoria == null || categoria.isBlank()) 
                                    ? autodefinirCategoria(nombre, email) 
                                    : categoria;
        
        totalClientes++;
        return true;
    }

    private static String autodefinirCategoria(String nombre, String email) {
        if (nombre.toUpperCase().contains("S.A.") || email.toLowerCase().endsWith("@empresa.com")) {
            return CAT_PREMIUM;
        }
        return CAT_NUEVO;
    }

    public static int buscarPorCedula(String cedula) {
        for (int i = 0; i < totalClientes; i++) {
            if (cedulas[i].equals(cedula)) return i;
        }
        return -1;
    }

    public static boolean eliminarCliente(String cedula) {
        int index = buscarPorCedula(cedula);
        if (index == -1) return false;

        for (int i = index; i < totalClientes - 1; i++) {
            nombres[i] = nombres[i + 1];
            cedulas[i] = cedulas[i + 1];
            emails[i] = emails[i + 1];
            categorias[i] = categorias[i + 1];
        }
        totalClientes--;
        return true;
    }

    // --- PERSISTENCIA ---

    public static void guardarDatos() {
        for (String cat : CATEGORIAS_VALIDAS) {
            Path path = Paths.get(FILE_PREFIX + cat.toLowerCase() + FILE_EXT);
            try (BufferedWriter bw = Files.newBufferedWriter(path)) {
                for (int i = 0; i < totalClientes; i++) {
                    if (categorias[i].equalsIgnoreCase(cat)) {
                        bw.write(String.join(",", nombres[i], cedulas[i], emails[i], categorias[i]));
                        bw.newLine();
                    }
                }
            } catch (IOException e) {
                System.err.println("Error guardando categoría " + cat + ": " + e.getMessage());
            }
        }
    }

    public static void cargarDatosDesdeDisco() {
        totalClientes = 0;
        for (String cat : CATEGORIAS_VALIDAS) {
            File archivo = new File(FILE_PREFIX + cat.toLowerCase() + FILE_EXT);
            if (!archivo.exists()) continue;

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] d = linea.split(",");
                    if (d.length == 4) registrarCliente(d[0], d[1], d[2], d[3]);
                }
            } catch (IOException e) {
                System.out.println("Error al leer: " + archivo.getName());
            }
        }
    }

    // --- INTERFAZ ---

    private static void ejecutarMenuPrincipal() {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 7) {
            System.out.println("\n--- SISTEMA GESTIÓN CLIENTES ---");
            System.out.println("1. Registrar Cliente | 2. Listar por Categoría | 3. Buscar por Cédula");
            System.out.println("4. Eliminar Cliente  | 5. Guardar Cambios       | 6. Crear Backup");
            System.out.println("7. Salir");
            System.out.print("Selección: ");
            
            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Nombre: "); String n = sc.nextLine();
                        System.out.print("Cédula: "); String c = sc.nextLine();
                        System.out.print("Email: "); String e = sc.nextLine();
                        if(registrarCliente(n, c, e, "")) System.out.println("¡Éxito!");
                    }
                    case 2 -> {
                        System.out.print("Categoría (Premium/Regular/Nuevo): ");
                        String catBusqueda = sc.nextLine();
                        listarPorCategoria(catBusqueda);
                    }
                    case 4 -> {
                        System.out.print("Cédula a eliminar: ");
                        if(eliminarCliente(sc.nextLine())) System.out.println("Eliminado.");
                        else System.out.println("No encontrado.");
                    }
                    case 5 -> guardarDatos();
                    case 6 -> generarBackup();
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida.");
            }
        }
    }

    private static void listarPorCategoria(String cat) {
        System.out.println("Listado " + cat + ":");
        for (int i = 0; i < totalClientes; i++) {
            if (categorias[i].equalsIgnoreCase(cat)) {
                System.out.printf("- %s [%s]%n", nombres[i], cedulas[i]);
            }
        }
    }

    public static void generarBackup() {
        String dirName = "backup_" + System.currentTimeMillis();
        new File(dirName).mkdir();
        System.out.println("Backup creado en: " + dirName);
    }
}