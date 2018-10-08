package modelo.factories;

import db.EntityManagerHelper;
import modelo.Reglas.Regla;
import modelo.devices.Sensor;

public class ReglaFactory {
	public static void addRegla(Regla regla) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(regla);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
}
