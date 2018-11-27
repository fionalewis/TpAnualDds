package modelo.repositories;

import db.EntityManagerHelper;
import modelo.Reglas.Regla;

public class ReglaRepository {
	public static void addRegla(Regla regla) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(regla);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static Regla getRegla(String unNombre) {
		Regla regla = EntityManagerHelper.getEntityManager().find(Regla.class,unNombre);
	    return regla;
	  }
}
