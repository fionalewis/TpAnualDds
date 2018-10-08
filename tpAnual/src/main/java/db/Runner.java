package db;

import java.util.ArrayList;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import modelo.Reglas.CondicionSensorYValor;
import modelo.devices.Sensor;
import modelo.repositorios.SensorRepository;


public class Runner implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	
	
	public static void main(String[] args) {
		//new EmpresasService().run(); //se usa para probar la actulizacion
		new Runner().init();
	}

	public void init() {
		withTransaction(() -> {
			setSensor();
		});
	}
	
	public void setSensor() {
		Sensor sensor = new Sensor("Temperatura");
		sensor.setMagnitud(23.6);
		sensor.setMagnitud(22.4);
		sensor.setMagnitud(24);
		sensor.setMagnitud(22.9);
		sensor.setMagnitud(20.8);
		CondicionSensorYValor condicion = new CondicionSensorYValor(sensor,25,"MENOR");
		
		SensorRepository.addSensor(sensor);
	}

}