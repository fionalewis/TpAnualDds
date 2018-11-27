package modelo.factories;

import javax.persistence.Entity;

import db.EntityManagerHelper;
import modelo.Reglas.Condicion;

public class CondicionFactory {
	public static void updateCondicionSYV(String comparacion, String nombreCondicion) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager()
				.createQuery("UPDATE CondicionSensorYValor as c SET c.comparacion = :comp WHERE c.nombreCondicion = '" 
						+ nombreCondicion + "'")
				.setParameter("comp",comparacion)
				.executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updateCondicionDS(String comparacion, String nombreCondicion) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager()
				.createQuery("UPDATE CondicionDosSensores as c SET c.comparacion = :comp WHERE c.nombreCondicion = '" 
						+ nombreCondicion + "'")
				.setParameter("comp",comparacion)
				.executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static Condicion getCondicion(String unNombre) {
	    Condicion condicion = EntityManagerHelper.getEntityManager().find(Condicion.class,unNombre);
	    return condicion;
	  }
}
