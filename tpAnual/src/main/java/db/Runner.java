package db;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import modelo.Actuador.Actuador;
import modelo.Reglas.CondicionSensorYValor;
import modelo.Reglas.Regla;
import modelo.devices.DispositivoInteligente;
import modelo.devices.Sensor;
import modelo.factories.ActuadorFactory;
import modelo.factories.ReglaFactory;
import modelo.factories.SensorFactory;


public class Runner implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	
	Sensor sensor = new Sensor("Temperatura");
	CondicionSensorYValor condicion = new CondicionSensorYValor(sensor,25,"MAYOR");
	
	public static void main(String[] args) {
		//new EmpresasService().run(); //se usa para probar la actulizacion
		new Runner().init();
	}

	public void init() {
		condicion.setNombreCondicion("TemperaturaMayorA25");
		withTransaction(() -> {
			setSensor();
			setRegla();
			setActuador();
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
		Regla regla = new Regla("Super Regla", dispositivo,"OR");
		regla.agregarCondicion(condicion);
		ReglaFactory.addRegla(regla);
	}
	
	public void setActuador(){
		Actuador actuador = new Actuador(1,"APAGAR");
		ActuadorFactory.addActuador(actuador);
	}

}