package modelo.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

	public static List<Transformador> getListaTranformadores() {
		EntityManagerHelper.beginTransaction();
		List<Transformador> transf = EntityManagerHelper.getEntityManager().createNativeQuery("SELECT * FROM Transformador", Transformador.class).getResultList();
		EntityManagerHelper.closeEntityManager();
		return transf;
	}
	
}
