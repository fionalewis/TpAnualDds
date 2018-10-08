package modelo.factories;

import db.EntityManagerHelper;
import modelo.devices.Sensor;


public class SensorFactory {
	public static void addSensor(Sensor sensor) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(sensor);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updateMagnitud(String nombreMagnitud, Double magnitud) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Sensor SET magnitud = " + magnitud + " WHERE nombreMagnitud = '"
				+ nombreMagnitud).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updateIntervalo(String nombreMagnitud, int intervalo) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Sensor SET intervalo = " + intervalo + " WHERE nombreMagnitud = '"
				+ nombreMagnitud).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void deleteMagnitud(String nombreMagnitud){
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("DELETE FROM Sensor WHERE nombreMagnitud = '"
				+ nombreMagnitud).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	

	
}
