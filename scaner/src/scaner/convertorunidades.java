package scaner;

import java.util.Scanner;

public class convertorunidades {

	public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    double metros = sc.nextDouble();
    double kilometros = metros/1000;
    double centimetros = metros*100;
    double pies = metros*3.28084;
    System.out.println("kilometros:"+kilometros);
    System.out.println("centimetros:"+centimetros);
    System.out.println("pies:"+pies);
    		
	}

}
