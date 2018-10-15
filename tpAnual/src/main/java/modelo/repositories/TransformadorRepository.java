package modelo.repositories;

import java.time.LocalDate;

import db.EntityManagerHelper;
import modelo.geoLocation.Transformador;


public class TransformadorRepository {
	
	public static void addTransformador(Transformador transformador) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(transformador);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void dropTransformador() {
		EntityManagerHelper.beginTransaction();		
		EntityManagerHelper.getEntityManager().createNativeQuery("SET SQL_SAFE_UPDATES = 0; ").executeUpdate();
		EntityManagerHelper.getEntityManager().createNativeQuery("UPDATE `cliente` SET `idTransformador` = NULL; ").executeUpdate();
		EntityManagerHelper.getEntityManager().createNativeQuery("DELETE FROM transformador where idTransformador > 0; ").executeUpdate();
		EntityManagerHelper.getEntityManager().createNativeQuery("SET SQL_SAFE_UPDATES = 1;").executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
}
