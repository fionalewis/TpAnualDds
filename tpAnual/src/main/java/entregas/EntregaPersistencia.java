package entregas;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.persistence.Query;

import modelo.factories.ActuadorFactory;
import modelo.factories.AdministradorFactory;
import modelo.factories.ClienteFactory;
import modelo.users.Administrador;
import modelo.users.Cliente;
import modelo.users.Cliente.TipoDocumento;

public class EntregaPersistencia {
	public static void main(String[] args) throws HeadlessException, SQLException{
		menuPrincipal();
	}
	
	@SuppressWarnings("resource")
	public static void menuPrincipal() throws HeadlessException, SQLException{
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
	
	public static void prueba1() throws HeadlessException, SQLException{
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
			consultarUsuario();
		break;
		//falta la parte de geolocalizacion
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
				
		System.out.println("\nIngrese una contraseña:");
		password = s.nextLine();
				
		Administrador admin = new Administrador(nombre,apellido,user,password);
		AdministradorFactory.addAdministrador(admin);
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
				
		System.out.println("\nIngrese una contraseña:");
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
		ClienteFactory.addCliente(cliente);
	}
	
	public static void consultarUsuario() throws HeadlessException, SQLException {
		System.out.println("Ingrese el tipo de usuario que quiere consultar:"
				+ "\n1. Administrador"
				+ "\n2. Cliente");
	Scanner in = new Scanner(System.in);
	switch(in.nextInt()){
	case 1:
		consultarAdministrador();
	break;
	
	case 2:
		consultarCliente();
	break;}
}

	public static void consultarAdministrador() throws HeadlessException, SQLException {

		Scanner in = new Scanner(System.in);
		System.out.println("Ingrese el codigo del administrador");
		int codigo = in.nextInt();
		Administrador admin = AdministradorFactory.getAdministrador(codigo);
		String nombre = admin.getNombre();
		String apellido = admin.getApellido();
		String user = admin.getUserName();
		String password = admin.getPassword();
		
		System.out.println("Nombre: "+ nombre + "\nApellido: " + apellido + "\nUsuario: " + user + "\nContraseña: " + password);
	}
	
	public static void consultarCliente() throws HeadlessException, SQLException {

		Scanner in = new Scanner(System.in);
		System.out.println("Ingrese el numero del documento del cliente");
		String dni = in.nextLine();
		Cliente cliente = ClienteFactory.getCliente(dni);
		String nombre = cliente.getNombre();
		String apellido = cliente.getApellido();
		String user = cliente.getUserName();
		String password = cliente.getPassword();
		String domicilio = cliente.getDomicilio();
		int puntos = cliente.getPuntos();
		String telefono = cliente.getTelefono();
		
		System.out.println("Nombre: "+ nombre + "\nApellido: " + apellido + "\nUsuario: " + user + "\nContraseña: " + password +
				"\nDomicilio: " + domicilio + "\nPuntos: " + puntos + "\nTelefono: " +telefono);
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
