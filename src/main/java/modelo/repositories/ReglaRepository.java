package modelo.repositories;

import java.util.List;

import db.EntityManagerHelper;
import modelo.Reglas.Regla;
import modelo.users.Cliente;

public class ReglaRepository {
	public ReglaRepository(){}
	
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
	
	public static List<Regla> getTodasLasReglas() {
		EntityManagerHelper.beginTransaction();
		List<Regla> reg = EntityManagerHelper.getEntityManager().createNativeQuery("SELECT * FROM Regla", Regla.class).getResultList();
		EntityManagerHelper.closeEntityManager();
		return reg;
	}
}