package modelo.factories;

import db.EntityManagerHelper;
import modelo.devices.Sensor;
import modelo.users.Categoria;


public class CategoriaFactory {
	public static void addCategoria(Categoria categoria) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(categoria);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	public static void deleteCategoria(String clasif){
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("DELETE FROM Categoria WHERE clasif = '"
				+ clasif).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	

	
}
