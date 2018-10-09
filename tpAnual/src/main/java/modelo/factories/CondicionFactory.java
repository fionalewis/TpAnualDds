package modelo.factories;

import javax.persistence.Entity;

import db.EntityManagerHelper;
import modelo.Reglas.Condicion;

public class CondicionFactory {
	public static void updateCondicion(String comparacion, String nombreCondicion) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Condicion SET comparacion = " + comparacion + " WHERE nombreCondicion = '"
				+ nombreCondicion).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static Condicion getCondicion(String unNombre) {
	    Condicion condicion = EntityManagerHelper.getEntityManager().find(Condicion.class,unNombre);
	    return condicion;
	  }
}
