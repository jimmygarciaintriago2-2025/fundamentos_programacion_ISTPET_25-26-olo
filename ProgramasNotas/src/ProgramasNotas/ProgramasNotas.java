package ProgramasNotas;

import java.util.Scanner;

public class ProgramasNotas {


		    // Constante para definir la nota mínima de aprobación
		    private static final double NOTA_MINIMA_APROBACION = 7.0;

		    public static void main(String[] args) {
		        Scanner scanner = new Scanner(System.in);

		        // 1. Entrada de datos
		        System.out.println("Ingrese nombre:");
		        String nombreEstudiante = scanner.nextLine();

		        double nota1 = solicitarNota(scanner, 1);
		        double nota2 = solicitarNota(scanner, 2);
		        double nota3 = solicitarNota(scanner, 3);

		        // 2. Procesamiento
		        double promedio = calcularPromedio(nota1, nota2, nota3);

		        // 3. Salida de resultados
		        mostrarResultadoFinal(nombreEstudiante, promedio);
		        verificarNotasNegativas(nota1, nota2, nota3);

		        scanner.close();
		    }

		    /**
		     * Solicita una nota al usuario por consola.
		     */
		    private static double solicitarNota(Scanner sc, int numeroNota) {
		        System.out.println("Ingrese nota " + numeroNota + ":");
		        return sc.nextDouble();
		    }

		    /**
		     * Calcula el promedio aritmético de tres notas.
		     */
		    private static double calcularPromedio(double n1, double n2, double n3) {
		        return (n1 + n2 + n3) / 3;
		    }

		    /**
		     * Determina si el estudiante aprobó o reprobó e imprime el mensaje descriptivo.
		     */
		    private static void mostrarResultadoFinal(String nombre, double promedio) {
		        String estado = (promedio >= NOTA_MINIMA_APROBACION) ? "aprobado" : "reprobado";
		        System.out.printf("Estudiante %s %s con promedio %.2f%n", nombre, estado, promedio);
		    }

		    /**
		     * Verifica si existe alguna nota inválida (negativa).
		     */
		    private static void verificarNotasNegativas(double... notas) {
		        for (double nota : notas) {
		            if (nota < 0) {
		                System.out.println("Alerta: Hay notas negativas registradas.");
		                break; // Solo imprimimos el mensaje una vez
		            }
		        }
		    }
		}


