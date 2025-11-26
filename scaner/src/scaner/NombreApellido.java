package scaner;

import java.util.Scanner;

public class NombreApellido {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("ingrese su nombre:");
		String nombre = sc.nextLine();
		System.out.println("ingrese su apellido:");
		String apellido = sc.nextLine();
		System.out.println("ingrese su edad:");
		Integer edad = sc.nextInt();
		System.out.println("ingrese su altura:");
		float altura = sc.nextFloat();
		System.out.println("ingrese su estado civil:");
	
		String estadocivil = sc.next();
		
		sc.close();
		
		System.out.println("mi  nombre es:" +nombre+ ""+apellido);
		System.out.println("mi estado civil es:" +estadocivil);
        System.out.println("mi edad es:" +edad);
        System.out.println("mi altura es:" +altura);

	}

}
