package modelo.factories;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.persistence.Query;
import javax.swing.JOptionPane;


import db.EntityManagerHelper;
import modelo.users.Administrador;
import modelo.users.Cliente;


public class AdministradorFactory {
	
	public static void addAdministrador(Administrador admin) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(admin);
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
	
	

	
}
