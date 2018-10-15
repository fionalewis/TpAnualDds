package entregas;

import java.util.Scanner;

import modelo.repositories.*;
import modelo.users.Administrador;
import modelo.users.Cliente;
import modelo.users.Cliente.TipoDocumento;

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
			
			case 1:
				prueba1();
				break;
				
			case 3:
				prueba3();
				break;
			
			default: menuPrincipal();
			}
	}
	
	public static void prueba1(){
		System.out.println("Elija una de las siguientes opciones:"
				+ "\n1. Agregar usuario"
				+ "\n2. Consultar usuario"
				+ "\n3. Modificar usuario");
	Scanner in = new Scanner(System.in);
	switch(in.nextInt()){
		case 1:
			agregarUsuario();
		break;
		
		case 2:
//			consultarUsuario();
		break;
		
		case 3:
//			modificarUsuario();
		break;}
	}
				
	public static void agregarUsuario() {
			System.out.println("Ingrese el tipo de usuario que quiere crear:"
					+ "\n1. Administrador"
					+ "\n2. Cliente");
		Scanner in = new Scanner(System.in);
		switch(in.nextInt()){
		case 1:
			agregarAdministrador();
		break;
		
		case 2:
			agregarCliente();
		break;}
		}
	
		
	public static void agregarAdministrador() {

		String nombre, apellido, user, password;

		Scanner s = new Scanner(System.in);
		System.out.println("\nIngrese un nombre:");
		nombre = s.nextLine();

		System.out.println("\nIngrese un apellido:");
		apellido = s.nextLine();
				
		System.out.println("\nIngrese un nombre de usuario:");
		user = s.nextLine();
				
		System.out.println("\nIngrese una contrase�a:");
		password = s.nextLine();
				
		Administrador admin = new Administrador(nombre,apellido,user,password);
		AdministradorRepository.addAdministrador(admin);
	}
	
	public static void agregarCliente() {

		String nombre, apellido, user, password, domicilio, telefono, nroDoc;
		TipoDocumento tipoDoc;
		
		Scanner s = new Scanner(System.in);

		System.out.println("\nIngrese un nombre:");
		nombre = s.nextLine();

		System.out.println("\nIngrese un apellido:");
		apellido = s.nextLine();
				
		System.out.println("\nIngrese un nombre de usuario:");
		user = s.nextLine();
				
		System.out.println("\nIngrese una contrase�a:");
		password = s.nextLine();

		System.out.println("\nIngrese tipo de documento:");
		tipoDoc = TipoDocumento.valueOf(s.nextLine());
		
		System.out.println("\nIngrese numero de documento:");
		nroDoc = s.nextLine();
		
		System.out.println("\nIngrese domicilio:");
		domicilio = s.nextLine();
		
		System.out.println("\nIngrese telefono:");
		telefono = s.nextLine();

		Cliente cliente = new Cliente(nombre,apellido,user,password,tipoDoc,nroDoc,telefono,domicilio);
		ClienteRepository.addCliente(cliente);
	}
	
	public static void consultarUsuario() {
		String nombre, apellido, user, password;
		System.out.println("Ingrese el n del usuario: ");

		Scanner s = new Scanner(System.in);
			System.out.println("\nIngrese un nombre:");
			nombre = s.nextLine();

			System.out.println("\nIngrese un apellido:");
			apellido = s.nextLine();
					
			System.out.println("\nIngrese un nombre de usuario:");
			user = s.nextLine();
					
			System.out.println("\nIngrese una contrase�a:");
			password = s.nextLine();
					
			Administrador admin = new Administrador(nombre,apellido,user,password);
			AdministradorRepository.addAdministrador(admin);
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
