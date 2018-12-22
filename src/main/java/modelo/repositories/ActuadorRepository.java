package modelo.repositories;

import db.EntityManagerHelper;
import modelo.Actuador.Actuador;

public class ActuadorRepository {
	public static void addActuador(Actuador actuador) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(actuador);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
}
