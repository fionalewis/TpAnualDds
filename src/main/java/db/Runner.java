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
<<<<<<< HEAD
=======
import modelo.devices.IntervaloDispositivo.modo;
>>>>>>> fionaheroku
import modelo.devices.*;
import modelo.devices.IntervaloDispositivo.modo;
import modelo.repositories.*;
import modelo.geoLocation.Transformador;
import modelo.geoLocation.Zona;
<<<<<<< HEAD
import modelo.repositories.*;
import modelo.factories.CategoriaFactory;
=======
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
>>>>>>> fionaheroku
import modelo.factories.DispositivoFactory;
import modelo.users.Administrador;
import modelo.users.Categoria;
import modelo.users.Cliente;
import modelo.users.Reporte;


public class Runner implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	
	List<Dispositivo> dispositivos = new ArrayList<>();
	List<Transformador> transformadores = new ArrayList<>();
	List<Categoria> categorias = new ArrayList<>();
	List<Cliente> clientes = new ArrayList<>();
	List<Administrador> admins = new ArrayList<>();
	List<Zona> zonas = new ArrayList<>();
	
	Sensor sensor = new Sensor("Temperatura",24.0,15);
	CondicionSensorYValor condicion = new CondicionSensorYValor(sensor,25,"MAYOR");
	Actuador actuador = new Actuador(152,"APAGAR");
	//Cliente cliente = new Cliente("Lucas","Ramirez","lramirez","1234",TipoDocumento.DNI,"40123456","45424625","Villa Urquiza"); 
	Administrador admin = new Administrador("Lucia","Gomez", "lgomez","1111");
	DispositivoInteligente dispositivo = new DispositivoInteligente("Televisor", "LED 24'");
	//Cliente c = new Cliente("pepe","argento","pepe123","12345", TipoDocumento.DNI, "40123456", "12345678", "Avenida Medrano 986");

	//Categoria cat1 = new Categoria("R1", 0, 150, 18.76, 0.644);
	DispositivoInteligente dispInteligente = new DispositivoInteligente("Televisor","LED 20'");
	DispositivoEstandar dispEstandar = new DispositivoEstandar("Ventilador",0.46,3,"Ventilador",1,4,true);
	
	public static void main(String[] args) {
		new Runner().init();
		new Reporte().jobReporte(60000);// 1 min en ms  // 2592000000 es un mes
		return;
	}
		/* con estas borras lo que haya antes en la tabla para que te quede de cero lo de los jsons
		 drop table if exists tp_anual_dds.actuador;
		 drop table if exists tp_anual_dds.administrador;

		 drop table if exists tp_anual_dds.intervalodispositivo;
		 drop table if exists tp_anual_dds.reporte;
		 drop table if exists tp_anual_dds.dispositivo;

		 drop table if exists tp_anual_dds.cliente;
		 drop table if exists tp_anual_dds.transformador;

		 drop table if exists tp_anual_dds.condicion;
		 drop table if exists tp_anual_dds.registro_mediciones;


		 drop table if exists tp_anual_dds.categoria;
		 drop table if exists tp_anual_dds.condicion_dos_sensores;
		 drop table if exists tp_anual_dds.condicion_sensor_y_valor;
		 drop table if exists tp_anual_dds.regla;

		 drop table if exists tp_anual_dds.sensor;
		drop table if exists tp_anual_dds.zona;
		*/

	public void init() {
		
		withTransaction(() -> {		
			// !!! limpiar lo que haya para cargar de cero !!!
			setUpCategorias();
			System.out.println("Categorias - OK");
			setUpTransformadores();		
			System.out.println("Transformadores - OK");		
			setUpZonas();	
			System.out.println("Zonas - OK");
			setUpDispositivos();
			System.out.println("Dispositivos - OK");
			//setUpClientes(); //sin inicializar
			//System.out.println("Clientes - OK");
			setUpAdministradores();	
			System.out.println("Admins - OK");

			setClientesCompletos();
			System.out.println("Clientes - OK");
			setUnDispositivoANico();
<<<<<<< HEAD
			setUpSuenio();
=======
			System.out.println("Nico - OK");
			setUpSuenio();
			System.out.println("Suenio - OK");
>>>>>>> fionaheroku
			//setUnDispositivoACliente();
			//setSensor();
			//setRegla();
			//setActuador();
					
		//	setCliente();
		//	setAdministrador();
		//	setDispositivoEstandar();
		//	setDispositivoInteligente();
		});
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
	
	public void setUpZonas(){
			
		try {
			zonas = DAOJson.deserializarLista(Zona.class, JsonManager.rutaJsonZonas);
			for (Zona z : zonas) {
				DispositivoRepository.addZonas(z);
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
		//regla.agregarCondicionSYV(condicion);
		regla.agregarActuador(actuador);
		ReglaRepository.addRegla(regla);
	}
	
	public void setActuador(){
		Actuador actuador1 = new Actuador(122,"AHORRO");
		ActuadorRepository.addActuador(actuador1);
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
		((DispositivoInteligente) d1).apagar(LocalDateTime.of(2018,8,21,5,0,0));
		((DispositivoInteligente) d1).ahorroEnergia(LocalDateTime.of(2018,8,21,6,0,0));
    	((DispositivoInteligente) d1).apagar(LocalDateTime.of(2018,8,21,8,30,0));
		List<Dispositivo> disp = new ArrayList<>(); disp.add(d1);
		Cliente c = new Cliente("pepe","argento","pepe123","12345",2018,8,21,TipoDocumento.DNI,"40403456","12345678","Avenida Medrano 986",disp);

		ClienteRepository.addClienteConDispositivosEIntervalos(c);
	}
	
	public void setUnDispositivoANico(){
		DeviceFactory f = new DeviceFactory();
		Dispositivo d1 = f.crearDisp("Aire Acondicionado","2200 frigorias"); 
    	//LocalDateTime fechaReg = LocalDateTime.of(2018,8,21,0,30,0);
		//d1.setFechaRegistro(fechaReg);
		
    	//((DispositivoInteligente) d1).setEstadoDisp(new Encendido());
    	((DispositivoInteligente) d1).apagar(LocalDateTime.of(2018,12,21,3,0,0));
		/*((DispositivoInteligente) d1).encender(LocalDateTime.of(2018,8,21,4,0,0));
		((DispositivoInteligente) d1).apagar(LocalDateTime.of(2018,8,21,5,0,0));
		((DispositivoInteligente) d1).ahorroEnergia(LocalDateTime.of(2018,8,21,6,0,0));
    	((DispositivoInteligente) d1).apagar(LocalDateTime.of(2018,8,21,8,30,0));*/
		List<Dispositivo> disp = new ArrayList<>(); disp.add(d1);
		Cliente c = new Cliente("nico","contreras","nico","las",2018,8,21,TipoDocumento.DNI,"40403456","12345678","Avenida Medrano 986",disp);

		ClienteRepository.addClienteConDispositivosEIntervalos(c);
	}
	
	//
	
	public void setUpSuenio(){
		
		DispositivoInteligente pepa = new DispositivoInteligente("Aire Acondicionado",1.5,2017,11,22,0,0,0,true);
<<<<<<< HEAD
		DispositivoInteligente est = new DispositivoInteligente("Aire Acondicionado",1.3,2018,11,22,0,0,0,true);
=======
		DispositivoInteligente est = new DispositivoInteligente("Pepa",1.3,2018,11,22,0,0,0,true);
>>>>>>> fionaheroku
		LocalDateTime fecha = LocalDateTime.of(2018,2,21,0,0,0);
		pepa.ahorroEnergia(fecha);
		LocalDateTime fechaap = LocalDateTime.of(2018,5,2,0,0,0);
		pepa.encender(fechaap);
		LocalDateTime fechae = LocalDateTime.of(2018,7,24,0,0,0);
		pepa.encender(fechae);
		LocalDateTime fechar = LocalDateTime.of(2018,9,11,0,0,0);
<<<<<<< HEAD
		pepa.apagar(fechar);
=======
		//pepa.apagar(fechar);
>>>>>>> fionaheroku
		/*((DispositivoInteligente) d1).encender(LocalDateTime.of(2018,8,21,4,0,0));
		((DispositivoInteligente) d1).apagar(LocalDateTime.of(2018,8,21,5,0,0));
		((DispositivoInteligente) d1).ahorroEnergia(LocalDateTime.of(2018,8,21,6,0,0));
    	((DispositivoInteligente) d1).apagar(LocalDateTime.of(2018,8,21,8,30,0));*/
		List<Dispositivo> disp = new ArrayList<>();disp.add(pepa);disp.add(est);
		Cliente c = new Cliente("pepa","pig","pepi","pik",2018,8,21,TipoDocumento.DNI,"40403568","12345678","pppp 986",disp);
<<<<<<< HEAD

		ClienteRepository.addClienteConDispositivosEIntervalos(c);
	}
	
	
	//
=======
		
		
		
		ClienteRepository.addClienteConDispositivosEIntervalos(c);
	
	}
>>>>>>> fionaheroku

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
			clientes = DAOJson.deserializarListaTransf(Cliente.class, JsonManager.rutaJsonClientesCompletos);
			for (Cliente c : clientes){
				ClienteRepository.addClienteConDispositivosEIntervalos(c);
			}
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public void setDispositivoEstandar() {
		DispositivoFactory.addDispositivoEstandar(dispEstandar);
	}
	
	public void setDispositivoInteligente() {
		DispositivoFactory.addDispositivoEstandar(dispInteligente);
	}


}