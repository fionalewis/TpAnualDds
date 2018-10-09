package entregas;

import java.util.Scanner;

public class EntregaPersistencia {
	public static void main(String[] args){
		menuPrincipal();
	}
	
	@SuppressWarnings("resource")
	public static void menuPrincipal(){
		System.out.println("\n Por favor elija una opcion:"
				+ "\n1. Prueba 1"
				+ "\n2. Prueba 2"
				+ "\n3. Prueba 3"
				+ "\n4. Prueba 4"
				+ "\n5. Prueba 5");
			
		Scanner in = new Scanner(System.in);
			switch(in.nextInt()){
			
			case 3:
				prueba3();
				break;
			
			default: menuPrincipal();
			}
	}
	
	public static void prueba3(){
		System.out.println("Ingrese un nombre para la regla");
		//crear una regla
		//asociar dispositivo
		//agregar acciones y condiciones
		//persistir la regla
		//recuperar y ejecutar??????
		//modificar alguna condicion
		//recuperar la condicion evaluada y ver si se guardaron los cambios
	}
	
	public static void init(){
		
	}
}
