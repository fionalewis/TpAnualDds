package modelo.factories;

import db.EntityManagerHelper;
import modelo.Reglas.Regla;
import modelo.devices.Dispositivo;

public class DispositivoFactory {
	public static void addDispositivoEstandar(Dispositivo dispositivo) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(dispositivo);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	public static void addDispositivoInteligente(Dispositivo dispositivo) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(dispositivo);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	/*
	public static Regla getRegla(String unNombre) {
		Regla regla = EntityManagerHelper.getEntityManager().find(Regla.class,unNombre);
	    return regla;
	  }
	  */
}
