package db;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import modelo.users.Cliente.TipoDocumento;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import modelo.Actuador.Actuador;
import modelo.Reglas.CondicionSensorYValor;
import modelo.Reglas.Regla;
import modelo.devices.DispositivoInteligente;
import modelo.devices.Sensor;
import modelo.factories.ActuadorFactory;
import modelo.factories.AdministradorFactory;
import modelo.factories.CategoriaFactory;
import modelo.factories.ClienteFactory;
import modelo.factories.ReglaFactory;
import modelo.factories.SensorFactory;
import modelo.users.Administrador;
import modelo.users.Categoria;
import modelo.users.Cliente;


public class Runner implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	
	Sensor sensor = new Sensor("Temperatura",24.0,15);
	CondicionSensorYValor condicion = new CondicionSensorYValor(sensor,25,"MAYOR");
	Actuador actuador = new Actuador(152,"APAGAR");
	Cliente cliente = new Cliente("Lucas","Ramirez","lramirez","1234",TipoDocumento.DNI,"39769099","45424625","Villa Urquiza"); 
	Administrador admin = new Administrador("Lucia","Gomez", "lgomez","1111");
	Categoria cat1 = new Categoria("R1", 0, 150, 18.76, 0.644);
	
	public static void main(String[] args) {
		new Runner().init();
	}

	public void init() {
		
		withTransaction(() -> {
			setSensor();
			setRegla();
			setActuador();
			setCliente();
			setAdministrador();
		});
	}
	
	public void setSensor() {
		sensor.setMagnitud(23.6);
		sensor.setMagnitud(22.4);
		sensor.setMagnitud(24);
		sensor.setMagnitud(22.9);
		sensor.setMagnitud(20.8);
		SensorFactory.addSensor(sensor);
	}
	
	public void setRegla(){
		DispositivoInteligente dispositivo = new DispositivoInteligente("Televisor","LED 24'");
		condicion.setNombreCondicion("TemperaturaMayorA25");
		Regla regla = new Regla("Super Regla", dispositivo,"OR");
		regla.agregarCondicionSYV(condicion);
		regla.agregarActuador(actuador);
		ReglaFactory.addRegla(regla);
	}
	
	public void setActuador(){
		Actuador actuador1 = new Actuador(122,"AHORRO");
		ActuadorFactory.addActuador(actuador1);
	}
	
	public void setCliente() {
		ClienteFactory.addCliente(cliente);
	}
	
	public void setAdministrador() {
		AdministradorFactory.addAdministrador(admin);
	}

	public void setCategoria() {
		
		Categoria cat2 = new Categoria("R2", 150, 325, 35.32, 0.644);
		Categoria cat3 = new Categoria("R3", 325, 400, 60.71, 0.681);
		Categoria cat4 = new Categoria("R4", 400, 450, 71.74, 0.738);
		Categoria cat5 = new Categoria("R5", 450, 500, 110.38, 0.794);
		Categoria cat6 = new Categoria("R6", 500, 600, 200.75, 0.832);
		Categoria cat7 = new Categoria("R7", 600, 700, 443.59, 0.851);
		Categoria cat8 = new Categoria("R8", 700, 1400, 545.96, 0.851);
		Categoria cat9 = new Categoria("R9", 1400, 1000000, 887.19, 0.851);	
		CategoriaFactory.addCategoria(cat1);
		CategoriaFactory.addCategoria(cat2);
		CategoriaFactory.addCategoria(cat3);
		CategoriaFactory.addCategoria(cat4);
		CategoriaFactory.addCategoria(cat5);
		CategoriaFactory.addCategoria(cat6);
		CategoriaFactory.addCategoria(cat7);
		CategoriaFactory.addCategoria(cat8);
		CategoriaFactory.addCategoria(cat9);
	}

}