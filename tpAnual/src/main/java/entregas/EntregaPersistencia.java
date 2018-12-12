package entregas;
import java.time.LocalDateTime;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import modelo.repositories.*;
import exceptions.CaracterInvalidoException;
import modelo.DAOJson;
import modelo.JsonManager;
import modelo.Actuador.Actuador;
import modelo.Reglas.CondicionSensorYValor;
import modelo.Reglas.Regla;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.Sensor;
import modelo.factories.CondicionFactory;
import modelo.users.Administrador;
import modelo.users.Cliente;
import modelo.users.Cliente.TipoDocumento;

public class EntregaPersistencia {
	public static void main(String[] args) throws HeadlessException, SQLException, CaracterInvalidoException, FileNotFoundException, InstantiationException, IllegalAccessException{
		menuPrincipal();
	}
	
	@SuppressWarnings("resource")
	public static void menuPrincipal() throws CaracterInvalidoException, HeadlessException, SQLException, FileNotFoundException, InstantiationException, IllegalAccessException{

		System.out.println("\n Por favor elija una opcion:"
				+ "\n1. Prueba 1"
				+ "\n3. Prueba 3 paso 1: crear nueva regla"
				+ "\n4. Prueba 3 paso 2: modificar una condicion"
				+ "\n5. Prueba 5"
				+ "\n6. Prueba 6");
			
		Scanner in = new Scanner(System.in);
			switch(in.nextInt()){
			
			case 1:
				prueba1();
				break;
			case 3:
				p3_crearRegla();
				break;
			case 4:
				p3_modificarCondicion();
				break;
			case 5:
				prueba5();
			case 6:
				prueba6();
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
			modificarCliente();
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

		System.out.println("\nIngrese tipo de documento: (con formato: DNI, CI, LE, LI)");
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
		Administrador admin = AdministradorRepository.getAdministrador(codigo);
		String nombre = admin.getNombre();
		String apellido = admin.getApellido();
		String user = admin.getUserName();
		String password = admin.getPassword();
		
		System.out.println("Nombre: "+ nombre + "\nApellido: " + apellido + "\nUsuario: " + user + "\nContrase�a: " + password);
	}
	
	public static void consultarCliente() throws HeadlessException, SQLException {

		Scanner in = new Scanner(System.in);
		System.out.println("Ingrese el numero del documento del cliente");
		String dni = in.nextLine();
		Cliente cliente = ClienteRepository.getCliente(dni);
		String nombre = cliente.getNombre();
		String apellido = cliente.getApellido();
		String user = cliente.getUserName();
		String password = cliente.getPassword();
		String domicilio = cliente.getDomicilio();
		int puntos = cliente.getPuntos();
		String telefono = cliente.getTelefono();
		
		System.out.println("Nombre: "+ nombre + "\nApellido: " + apellido + "\nUsuario: " + user + "\nContrase�a: " + password +
				"\nDomicilio: " + domicilio + "\nPuntos: " + puntos + "\nTelefono: " +telefono);
	}
	
	public static void modificarCliente() throws HeadlessException, SQLException {

		Scanner in = new Scanner(System.in);
		System.out.println("Ingrese el numero del documento del cliente que quiere modificar");
		String dni = in.nextLine();
		Cliente cliente = ClienteRepository.getCliente(dni);
		String nombre = cliente.getNombre();
		String apellido = cliente.getApellido();
		String user = cliente.getUserName();
		String password = cliente.getPassword();
		String domicilio = cliente.getDomicilio();
		int puntos = cliente.getPuntos();
		String telefono = cliente.getTelefono();
		System.out.println("Nombre: "+ nombre + "\nApellido: " + apellido + "\nUsuario: " + user + "\nContrase�a: " + password +
				"\nDomicilio: " + domicilio + "\nPuntos: " + puntos + "\nTelefono: " +telefono);
		System.out.println("Ingrese el nuevo apellido del cliente: ");
		String nuevoApellido = in.nextLine();
		ClienteRepository.updateApellido(dni, nuevoApellido);
		Cliente clienteModificado = ClienteRepository.getCliente(dni);
		String apellidoModificado = clienteModificado.getApellido();
		System.out.println("Cliente modificado: \nNombre: "+ nombre + "\nApellido: " + apellidoModificado + "\nUsuario: " + user + "\nContrase�a: " + password +
				"\nDomicilio: " + domicilio + "\nPuntos: " + puntos + "\nTelefono: " +telefono);
		
	}
		

	public static void p3_crearRegla(){
		//**1 nueva regla
		
		String criterio=null;
		DispositivoInteligente dispositivo = new DispositivoInteligente("Televisor","LED 24'");
		
		Scanner in = new Scanner(System.in);
		System.out.println("Ingrese un nombre para la regla");
		String nombreRegla = in.nextLine();
		System.out.println("Ingrese criterio de comparacion:\n"
				+ "1 para AND\n"
				+ "2 para OR\n");
		
		switch(in.nextInt()){
		case 1: criterio = "AND";
		break;
		case 2: criterio = "OR";
		break;
		}
		
		//asociar dispositivo, agregar acciones y condiciones
		Sensor sensor = new Sensor("Humedad",30,10);
		dispositivo.agregarSensor(sensor);
		
		CondicionSensorYValor condicion = new CondicionSensorYValor(sensor,50,"MENOR");
		System.out.println("Ingrese un nombre para la condicion");
		in.nextLine();
		String nombreCond = in.nextLine();
		condicion.setNombreCondicion(nombreCond);
		Actuador actuador = new Actuador(2,"APAGAR");
		
		System.out.println("Se asociara un dispositivo sobre la cual actuar: "
				+ "Televisor LED 24 con un sensor de humedad \n"
				+ "Se agregara una condicion: 'Humedad menor a 50%'"
				+ "Se agregara un actuador con id fabricante 2, con la accion de apagar el dispositivo");
		
		//persistir la regla
		Regla regla = new Regla(nombreRegla,dispositivo,criterio);
		//regla.agregarCondicionSYV(condicion);
		regla.agregarActuador(actuador);
		ReglaRepository.addRegla(regla);
	}
	
	public static void p3_modificarCondicion() throws CaracterInvalidoException{
		
		Scanner in = new Scanner(System.in);
		System.out.println("Ingrese el nombre de la regla");
		String nombreRegla = in.nextLine();
		Regla unaRegla = ReglaRepository.getRegla(nombreRegla);
		
		//cambiar despues de persistir los dispositivos
		DispositivoInteligente dispositivo = new DispositivoInteligente("Televisor","LED 24'");
		//unaRegla.setDisp(dispositivo);
		
		System.out.println("Se ejecutara la regla creada");
		unaRegla.aplicarRegla();
		
		System.out.println("Ingrese el nombre de la condicion a modificar");
		String nombreCond = in.nextLine();
		System.out.println("Se modificara la condicion 'Humedad menor a 50%' a 'Humedad mayor a 50%'\n");
		//Esto no estoy segura 
		//unaRegla.getCondicionesSYV().get(0)).setComparacion("MENOR");
		//unaRegla.getCondicionesSYV().get(0)).setNombreCondicion("Humedad_menor_a_50");
		//ReglaFactory.updateRegla()
		CondicionFactory.updateCondicionSYV("MAYOR", nombreCond);
	}
	
	public static void prueba5(){
		Scanner in = new Scanner(System.in);
		System.out.println("Ingrese nro de documento de cliente: ");
		String nroDoc = in.nextLine();
		Cliente cliente = ClienteRepository.getCliente(nroDoc);
		String nombre = cliente.getNombre();
		String apellido = cliente.getApellido();
		String user = cliente.getUserName();
		String password = cliente.getPassword();
		String domicilio = cliente.getDomicilio();
		int puntos = cliente.getPuntos();
		String telefono = cliente.getTelefono();
		System.out.println("Nombre: "+ nombre + "\nApellido: " + apellido + "\nUsuario: " + user + "\nContrase�a: " + password +
				"\nDomicilio: " + domicilio + "\nPuntos: " + puntos + "\nTelefono: " +telefono);
		
		//Cliente cliente = new Cliente("Nico","Alvarez","nalvarez","1234",TipoDocumento.DNI,"39656545","45654565","Pueyrredon");
		DispositivoInteligente disp1 = new DispositivoInteligente("Televisor","LED 24'");
		DispositivoEstandar disp2 = new DispositivoEstandar("Ventilador",0.45,3,"Ventilador",1,4,true);
		DispositivoEstandar disp3 = new DispositivoEstandar("Heladera",0.55,2,"Heladera",1,3,true);
		cliente.agregarDispositivo(disp1);
		cliente.agregarDispositivo(disp2);
		cliente.agregarDispositivo(disp3);
		
		System.out.println("Ingrese a�o, mes y dia de inicio: ");
		int anioInicio = in.nextInt();
		int mesInicio = in.nextInt();
		int diaInicio = in.nextInt();
		LocalDateTime fechaInicio = LocalDateTime.of(anioInicio, mesInicio, diaInicio, 0, 0);
		
		System.out.println("Ingrese a�o, mes y dia de fin: ");
		int anioFin = in.nextInt();
		int mesFin = in.nextInt();
		int diaFin = in.nextInt();
		LocalDateTime fechaFin= LocalDateTime.of(anioFin, mesFin, diaFin, 0, 0);
		
		double horasDeConsumo = cliente.consumoXPeriodo(fechaInicio, fechaFin);
		System.out.println("Horas de consumo: " + horasDeConsumo);
		
//		System.out.println("Ingrese id Dispositivo: ");
//		String nroDisp = in.nextLine();
	}

	public static void prueba6() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		
		Scanner in = new Scanner(System.in);
		System.out.println("Ingrese la ruta: ");
		String ruta = in.nextLine();
		List<Dispositivo> dispositivos = DAOJson.deserializarDispositivos(Dispositivo.class, ruta);
		
		for (Dispositivo d : dispositivos) {
			DispositivoRepository.addDispositivo(d);
			}
		
		System.out.println("El dispositivo se cargó con éxito");
	/*	final String json = ruta;
		final Gson gson = new Gson();
		final DispositivoEstandar<?> dispEstandar = gson.fromJson(json, DispositivoEstandar.class);
		DispositivoRepository d = new DispositivoRepository();
		DispositivoRepository.addDispositivo(dispEstandar);
		
	*/

}
}
