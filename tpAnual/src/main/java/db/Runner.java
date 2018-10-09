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
import modelo.factories.ClienteFactory;
import modelo.factories.ReglaFactory;
import modelo.factories.SensorFactory;
import modelo.users.Administrador;
import modelo.users.Cliente;


public class Runner implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	
	Sensor sensor = new Sensor("Temperatura");
	CondicionSensorYValor condicion = new CondicionSensorYValor(sensor,25,"MAYOR");
	Actuador actuador = new Actuador(152,"APAGAR");
	Cliente cliente = new Cliente("Lucas","Ramirez","lramirez","1234",TipoDocumento.DNI,"39769099","45424625","Villa Urquiza"); 
	Administrador admin = new Administrador("Lucia","Gomez", "lgomez","1111");
	
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
		regla.agregarCondicion(condicion);
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

}