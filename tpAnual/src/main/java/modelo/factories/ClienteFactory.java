package modelo.factories;

import java.time.LocalDate;

import db.EntityManagerHelper;
import modelo.users.Cliente;
import modelo.users.Cliente.TipoDocumento;


public class ClienteFactory {
	
	public static void addCliente(Cliente cliente) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(cliente);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updateNombre(String nroDoc, String nombre) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Cliente SET nombre = " + nombre + " WHERE nroDoc = '"
				+ nroDoc).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updateApellido(String nroDoc, String apellido) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Cliente SET apellido = " + apellido + " WHERE nroDoc = '"
				+ nroDoc).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updateFechaAlta(String nroDoc, LocalDate fechaAlta) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Cliente SET fechaAlta = " + fechaAlta + " WHERE nroDoc = '"
				+ nroDoc).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updateUserName(String nroDoc, String userName) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Cliente SET userName = " + userName + " WHERE nroDoc = '"
				+ nroDoc).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updatePassword(String nroDoc, String password) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Cliente SET password = " + password + " WHERE nroDoc = '"
				+ nroDoc).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updateTipoDoc(String nroDoc, TipoDocumento tipoDoc) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Cliente SET tipoDoc= " + tipoDoc + " WHERE nroDoc = '"
				+ nroDoc).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updateTelefono(String nroDoc, String telefono) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Cliente SET telefono = " + telefono + " WHERE nroDoc = '"
				+ nroDoc).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	public static void updateDomicilio(String nroDoc, String domicilio) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Cliente SET domicilio = " + domicilio + " WHERE nroDoc = '"
				+ nroDoc).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	public static void updatePuntos(String nroDoc, int puntos) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Cliente SET puntos = " + puntos + " WHERE nroDoc = '"
				+ nroDoc).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	public static void deleteCliente(String nroDoc){
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("DELETE FROM Sensor WHERE nroDoc = '"
				+ nroDoc).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	

	
}
