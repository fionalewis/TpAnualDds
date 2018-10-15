package db;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import modelo.users.Cliente.TipoDocumento;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import modelo.DAOJson;
import modelo.JsonManager;
import modelo.Actuador.Actuador;
import modelo.Reglas.CondicionSensorYValor;
import modelo.Reglas.Regla;
import modelo.deviceState.Encendido;
import modelo.devices.DeviceFactory;
import modelo.devices.Dispositivo;
import modelo.devices.*;
import modelo.devices.Sensor;
import modelo.repositories.*;
import modelo.geoLocation.Transformador;
import modelo.users.Administrador;
import modelo.users.Categoria;
import modelo.users.Cliente;


public class Runner implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	
	List<Dispositivo> dispositivos = new ArrayList<>();
	List<Transformador> transformadores = new ArrayList<>();
	List<Categoria> categorias = new ArrayList<>();
	List<Cliente> clientes = new ArrayList<>();
	List<Administrador> admins = new ArrayList<>();
	
	Sensor sensor = new Sensor("Temperatura");
	CondicionSensorYValor condicion = new CondicionSensorYValor(sensor,25,"MAYOR");
	Actuador actuador = new Actuador(152,"APAGAR");
	Cliente cliente = new Cliente("Lucas","Ramirez","lramirez","1234",TipoDocumento.DNI,"39769099","45424625","Villa Urquiza"); 
	Administrador admin = new Administrador("Lucia","Gomez", "lgomez","1111");
	DispositivoInteligente dispositivo = new DispositivoInteligente("Televisor", "LED 24'");
	//Cliente c = new Cliente("pepe","argento","pepe123","12345", TipoDocumento.DNI, "40123456", "12345678", "Avenida Medrano 986");

	public static void main(String[] args) {
		new Runner().init();
		return;
	}

	public void init() {
		
		withTransaction(() -> {		
			//dropTablas(); //limpiar lo que haya para cargar de cero	
			setUpCategorias();
			System.out.println("Categorias - OK");
			setUpTransformadores();		
			System.out.println("Transformadores - OK");		
			setUpDispositivos();
			System.out.println("Dispositivos - OK");
			setUpClientes(); //completar json
			System.out.println("Clientes - OK");
			setUpAdministradores();	
			System.out.println("Admins - OK");

			setCliente();
			System.out.println("Un Cliente - OK");
			setUnDispositivoACliente();
			System.out.println("setUnDispositivoACliente - OK");
			setSensor();
			setRegla();
			setActuador();
			setAdministrador();	
			//setClientesCompletos(); todavia no funciona, voy a esperar a tener todas las tablas		
		});
	}

	public void dropTablas(){
		DispositivoRepository.dropTablas();
	}

	public void setUpCategorias(){
			
		try {
			categorias = DAOJson.deserializarLista(Categoria.class, JsonManager.rutaJsonCateg);
			for (Categoria d : categorias) {
				DispositivoRepository.addCategorias(d);
		 	}	
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}	
	}
	
	public void setSensor() {
		sensor.setMagnitud(23.6);
		sensor.setMagnitud(22.4);
		sensor.setMagnitud(24);
		sensor.setMagnitud(22.9);
		sensor.setMagnitud(20.8);
		SensorRepository.addSensor(sensor);
	}
	
	public void setRegla(){		
		condicion.setNombreCondicion("TemperaturaMayorA25");
		Regla regla = new Regla("Super Regla", dispositivo,"OR");
		regla.agregarCondicion(condicion);
		regla.agregarActuador(actuador);
		ReglaRepository.addRegla(regla);
	}
	
	public void setActuador(){
		Actuador actuador1 = new Actuador(122,"AHORRO");
		ActuadorRepository.addActuador(actuador1);
	}
	
	public void setCliente() {
		cliente.agregarDispositivo(dispositivo);
		ClienteRepository.addCliente(cliente);
		//ClienteRepository.addCliente(c);
	}

	public void setUpClientes(){
		try {
			clientes = DAOJson.deserializarLista(Cliente.class, JsonManager.rutaJsonClientes);
			for (Cliente c : clientes) {
				ClienteRepository.addCliente(c);
		 	}			
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
		 	e.printStackTrace();
		}
	}
	
	public void setAdministrador() {
		AdministradorRepository.addAdministrador(admin);
	}

	public void setUpAdministradores(){
		try {
			admins = DAOJson.deserializarLista(Administrador.class, JsonManager.rutaJsonAdmin);
			for (Administrador a : admins) {
				AdministradorRepository.mergeAdministrador(a);
		 	}			
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
		 	e.printStackTrace();
		}
	}

	public void setUpDispositivos(){
		try {
			dispositivos = DAOJson.deserializarDispositivos(Dispositivo.class, JsonManager.rutaJsonDisp);
			for (Dispositivo d : dispositivos) {
				DispositivoRepository.addDispositivo(d);
		 	}			
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
		 	e.printStackTrace();
		}
	}


	public void setUnDispositivoACliente(){
		DeviceFactory f = new DeviceFactory();
		Dispositivo d1 = f.crearDisp("Aire Acondicionado","2200 frigorias");
    	LocalDateTime fechaReg = LocalDateTime.of(2018,8,21,0,30,0);
		d1.setFechaRegistro(fechaReg);
		
    	((DispositivoInteligente) d1).setEstadoDisp(new Encendido());
    	((DispositivoInteligente) d1).apagar(LocalDateTime.of(2018,8,21,3,0,0));
		((DispositivoInteligente) d1).encender(LocalDateTime.of(2018,8,21,4,0,0));
		((DispositivoInteligente) d1).ahorroEnergia(LocalDateTime.of(2018,8,21,6,0,0));
    	((DispositivoInteligente) d1).apagar(LocalDateTime.of(2018,8,21,8,30,0));
		List<Dispositivo> disp = new ArrayList<>(); disp.add(d1);
		Cliente c = new Cliente("pepe","argento","pepe123","12345",2018,8,21,TipoDocumento.DNI,"40123456","12345678","Avenida Medrano 986",disp);
		//DispositivoRepository.addDispositivo(d1);
		ClienteRepository.addClienteConDispositivos(c);
		//
	}

	public void setUpTransformadores(){
		try{ 
			transformadores = DAOJson.deserializarLista(Transformador.class, JsonManager.rutaJsonTransf);
			for (Transformador t : transformadores){
				TransformadorRepository.addTransformador(t);
			}
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void transformadoresMensualENRE(String ruta){
		TransformadorRepository.dropTransformador();

		try{ 
			transformadores = DAOJson.deserializarLista(Transformador.class, ruta);
			for (Transformador t : transformadores){
				TransformadorRepository.addTransformador(t);
			}
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setClientesCompletos(){
		try{ 
			//clientes = DAOJson.deserializarListaTransf(Cliente.class, JsonManager.rutaJsonClientesCompletos);
			clientes = DAOJson.deserializarListaTransf(Cliente.class, JsonManager.rutaJsonClientesCompletos);
			for (Cliente c : clientes){
				ClienteRepository.addClienteConDispositivos(c);
			}
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}