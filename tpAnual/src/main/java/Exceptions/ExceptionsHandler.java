package Exceptions;

import java.io.FileNotFoundException;

/* Hay mejores formas de hacer esto pero solo queria poner menos codigo rapido, si prueban algo mas prolijo
 * pruebenlo y avisen tranqui  */

public class ExceptionsHandler {

	public static void catchear(Exception e) {
		if (e instanceof FileNotFoundException) {
			System.out.println("No se ha encontrado el archivo.");
		} else if (e instanceof InstantiationException) {
			System.out.println("Error en la inicialización.");
		} else if (e instanceof IllegalAccessException) {
			System.out.println("Error de acceso.");
		} else if (e instanceof CaracterInvalidoException) {
			System.out.println("Caracter Invalido.");
		}
	}
	
}