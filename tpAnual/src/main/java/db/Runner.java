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
	Actuador actuador = new Actuador(152,"APAGAR");
	
	public static void main(String[] args) {
		new Runner().init();
	}

	public void init() {
		
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

}