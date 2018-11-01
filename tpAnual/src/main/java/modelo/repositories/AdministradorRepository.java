package modelo.repositories;
import java.time.LocalDate;

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
	
	

	
}
