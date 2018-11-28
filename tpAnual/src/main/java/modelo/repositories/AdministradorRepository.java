package modelo.repositories;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import db.EntityManagerHelper;
import modelo.users.Administrador;
import modelo.users.Cliente;


public class AdministradorRepository {
	
	public static void addAdministrador(Administrador admin) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(admin);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}	

	public static void mergeAdministrador(Administrador admin) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.merge(admin);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static Administrador getAdministrador(int unCodigo) {
		Administrador administrador = EntityManagerHelper.getEntityManager().find(Administrador.class,unCodigo);
	    return administrador;
	  }

	public static void updateNombre(int codAdmin, String nombre) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Administrador SET nombre = " + nombre + " WHERE codAdmin = '"
				+ codAdmin).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updateApellido(int codAdmin, String apellido) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Administrador SET apellido = " + apellido + " WHERE codAdmin = '"
				+ codAdmin).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updateFechaAlta(int codAdmin, LocalDate fechaAlta) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Administrador SET fechaAlta = " + fechaAlta + " WHERE codAdmin = '"
				+ codAdmin).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updateUserName(int codAdmin, String userName) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Administrador SET userName = " + userName + " WHERE codAdmin = '"
				+ codAdmin).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updatePassword(int codAdmin, String password) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Administrador SET password = " + password + " WHERE codAdmin = '"
				+ codAdmin).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void deleteCliente(int codAdmin){
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("DELETE FROM Sensor WHERE codAdmin = '"
				+ codAdmin).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static Administrador getAdminConNombre(String username) {
		EntityManagerHelper.beginTransaction();
		Administrador admin = (Administrador) EntityManagerHelper
				.getEntityManager().createNativeQuery("SELECT * FROM Administrador where userName = '" 
						+ username + "'",Administrador.class).getSingleResult();
		EntityManagerHelper.closeEntityManager();
		return admin;
	}	
	
	public static Administrador obtenerAdmin(String username){
		List<Administrador> lista = new AdministradorRepository().getTodosLosAdmin();
		Iterator<Administrador> iterator = lista.iterator();
	    while (iterator.hasNext()) {
	        Administrador adm = iterator.next();
	        if (adm.getUserName().equals(username)) {
	            return adm;
	        }
	    }
	    return null;
	}
	
	public static List<Administrador> getTodosLosAdmin() {
		EntityManagerHelper.beginTransaction();
		List<Administrador> adm = EntityManagerHelper.getEntityManager()
				.createNativeQuery("SELECT * FROM Administrador", Administrador.class).getResultList();
		EntityManagerHelper.closeEntityManager();
		return adm;
	}
}
