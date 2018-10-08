package modelo.factories;

import db.EntityManagerHelper;
import modelo.Actuador.Actuador;

public class ActuadorFactory {
	public static void addActuador(Actuador actuador) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(actuador);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
}
