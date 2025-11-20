package Calculodepromedio;
import java.util.ArrayList;
public class Calculodepromedio {

	public static void main(String[] args) {
		ArrayList<Integer> numeros= new ArrayList<Integer>();
		numeros.add(5);
		numeros.add(8);
		numeros.add(7);
		double suma= 0;
		for (int numero:numeros) {
			suma+= numero;
		}
		double promedio=suma/numeros.size();
		System.out.printf("el promedio del estudiante es:"+promedio);
				

	}

}
