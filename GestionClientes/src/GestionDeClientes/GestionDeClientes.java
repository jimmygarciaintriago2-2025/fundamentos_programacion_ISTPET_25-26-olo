package GestionDeClientes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GestionDeClientes {

		private static final int MAX_CLIENTES = 100;
	    private static final String ARCHIVO_POR_DEFECTO = "clientes.txt";

	    private static String[] nombres = new String[MAX_CLIENTES];
	    private static String[] cedulas = new String[MAX_CLIENTES];
	    private static String[] emails = new String[MAX_CLIENTES];
	    private static int contadorClientes = 0;

	    private static Scanner sc = new Scanner(System.in);

	    public static void main(String[] args) {
	        inicializarSistema();
	        cargarClientesAlIniciar();
	        menuPrincipal();
	        guardarClientesAutomatico();
	    }

	    public static void inicializarSistema() {
	        contadorClientes = 0;
	        System.out.println("Sistema iniciado. Capacidad máxima: " + MAX_CLIENTES);
	    }

	  

	    public static boolean agregarCliente(String nombre, String cedula, String email) {
	        if (contadorClientes >= MAX_CLIENTES) {
	            System.out.println("Sistema lleno.");
	            return false;
	        }
	        if (nombre.isEmpty() || cedula.isEmpty() || email.isEmpty()) {
	            System.out.println("Datos incompletos.");
	            return false;
	        }
	        if (buscarPorCedula(cedula) != -1) {
	            System.out.println("Cédula duplicada.");
	            return false;
	        }
	        if (!email.contains("@")) {
	            System.out.println("Email inválido.");
	            return false;
	        }

	        nombres[contadorClientes] = nombre;
	        cedulas[contadorClientes] = cedula;
	        emails[contadorClientes] = email;
	        contadorClientes++;
	        return true;
	    }

	    public static void listarClientes() {
	        if (contadorClientes == 0) {
	            System.out.println("No hay clientes.");
	            return;
	        }
	        for (int i = 0; i < contadorClientes; i++) {
	            System.out.println((i + 1) + ". " + nombres[i] + " | " + cedulas[i] + " | " + emails[i]);
	        }
	    }

	    public static int buscarPorCedula(String cedula) {
	        for (int i = 0; i < contadorClientes; i++) {
	            if (cedulas[i].equals(cedula)) return i;
	        }
	        return -1;
	    }

	    public static void editarClienteInteractivo() {
	        System.out.print("Cédula a editar: ");
	        String ced = sc.nextLine();
	        int idx = buscarPorCedula(ced);

	        if (idx == -1) {
	            System.out.println("Cliente no encontrado.");
	            return;
	        }

	        System.out.print("Nuevo nombre: ");
	        String n = sc.nextLine();
	        if (!n.isEmpty()) nombres[idx] = n;

	        System.out.print("Nuevo email: ");
	        String e = sc.nextLine();
	        if (!e.isEmpty() && e.contains("@")) emails[idx] = e;

	        System.out.println("Cliente actualizado.");
	    }

	    public static void eliminarCliente(int idx) {
	        if (idx < 0 || idx >= contadorClientes) return;

	        for (int i = idx; i < contadorClientes - 1; i++) {
	            nombres[i] = nombres[i + 1];
	            cedulas[i] = cedulas[i + 1];
	            emails[i] = emails[i + 1];
	        }
	        contadorClientes--;
	        System.out.println("Cliente eliminado.");
	    }

	    

	    public static boolean guardarClientes(String nombreArchivo) {
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
	            bw.write("nombre,cedula,email");
	            bw.newLine();

	            for (int i = 0; i < contadorClientes; i++) {
	                bw.write(nombres[i] + "," + cedulas[i] + "," + emails[i]);
	                bw.newLine();
	            }
	            return true;
	        } catch (IOException e) {
	            System.out.println("Error al guardar archivo: " + e.getMessage());
	            return false;
	        }
	    }

	    public static void guardarClientesAutomatico() {
	        if (guardarClientes(ARCHIVO_POR_DEFECTO))
	            System.out.println("Datos guardados automáticamente.");
	        else
	            System.out.println("Error al guardar automáticamente.");
	    }

	    public static int cargarClientes(String nombreArchivo) {
	        File archivo = new File(nombreArchivo);
	        if (!archivo.exists()) return 0;

	        int cargados = 0;

	        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
	            String linea = br.readLine(); 

	            while ((linea = br.readLine()) != null) {
	                String[] partes = linea.split(",");
	                if (partes.length == 3) {
	                    if (agregarCliente(partes[0], partes[1], partes[2])) {
	                        cargados++;
	                    }
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error al cargar archivo: " + e.getMessage());
	        }
	        return cargados;
	    }

	    public static void cargarClientesAlIniciar() {
	        int total = cargarClientes(ARCHIVO_POR_DEFECTO);
	        if (total > 0)
	            System.out.println("Cargados " + total + " clientes desde archivo.");
	        else
	            System.out.println("Archivo no encontrado, sistema vacío.");
	    }

	    

	    public static void menuPrincipal() {
	        int opcion;
	        do {
	            System.out.println("\nSISTEMA DE GESTIÓN DE CLIENTES");
	            System.out.println("1. Agregar cliente");
	            System.out.println("2. Listar clientes");
	            System.out.println("3. Buscar cliente");
	            System.out.println("4. Editar cliente");
	            System.out.println("5. Eliminar cliente");
	            System.out.println("6. Guardar datos (manual)");
	            System.out.println("7. Cargar datos (manual)");
	            System.out.println("8. Estadísticas");
	            System.out.println("9. Salir");
	            System.out.print("Opción: ");

	            opcion = sc.nextInt();
	            sc.nextLine();

	            switch (opcion) {
	                case 1 -> {
	                    System.out.print("Nombre: ");
	                    String n = sc.nextLine();
	                    System.out.print("Cédula: ");
	                    String c = sc.nextLine();
	                    System.out.print("Email: ");
	                    String e = sc.nextLine();
	                    if (agregarCliente(n, c, e)) System.out.println("Cliente agregado.");
	                }
	                case 2 -> listarClientes();
	                case 3 -> {
	                    System.out.print("Cédula: ");
	                    int idx = buscarPorCedula(sc.nextLine());
	                    if (idx != -1)
	                        System.out.println(nombres[idx] + " | " + emails[idx]);
	                    else
	                        System.out.println("No encontrado.");
	                }
	                case 4 -> editarClienteInteractivo();
	                case 5 -> {
	                    System.out.print("Cédula a eliminar: ");
	                    int idx = buscarPorCedula(sc.nextLine());
	                    if (idx != -1) eliminarCliente(idx);
	                }
	                case 6 -> guardarClientesAutomatico();
	                case 7 -> cargarClientesAlIniciar();
	                case 8 -> {
	                    double uso = (double) contadorClientes / MAX_CLIENTES * 100;
	                    System.out.println("Total: " + contadorClientes + " | Uso: " + uso + "%");
	                }
	            }
	        } while (opcion != 9);
	    }
	
	




	}


