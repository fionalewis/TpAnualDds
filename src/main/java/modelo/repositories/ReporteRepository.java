package modelo.repositories;
import java.time.LocalDate;

import db.EntityManagerHelper;
import modelo.users.Administrador;
import modelo.users.Cliente;
import modelo.users.Reporte;


public class ReporteRepository {

	public static void addReporte(Reporte reporte,Cliente cli,double consumo) { 
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(reporte);			
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();

		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Reporte set cliente_id='"+cli.getNroDoc()+"',consumo = " + consumo + " where id = "+reporte.id).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	public static void addReporte(Reporte reporte, int id, double consumo) { 
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(reporte);			
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();

		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Reporte set transformador_id='"+id+"',consumo = " + consumo + " where id = "+reporte.id).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	public static void addReporte(Reporte reporte, String tipo, long consumo) { 
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(reporte);			
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();

		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Reporte set tipo_dispositivo='"+tipo+"',consumo = " + consumo + " where id = "+reporte.id).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	public static void updateConsumoHogar(String nroDoc, double consumo) { 
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Reporte SET consumo = " + consumo + " WHERE cliente_id = '"+nroDoc+"'").executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	// public static String getConsumoTransf(int id) {
	// 	EntityManagerHelper.beginTransaction();
	// 	String transf = EntityManagerHelper.getEntityManager().createNativeQuery("SELECT consumo FROM Zona where id_transformador="+id).toString();
	// 	EntityManagerHelper.closeEntityManager();
	// 	return transf;
	// }
	
	// public static void getReportePorPeriodo() {
	// 	EntityManagerHelper.beginTransaction();
	// 	EntityManagerHelper.persist();
	// 	EntityManagerHelper.commit();
	// 	EntityManagerHelper.closeEntityManager();
	// }

	// public static void getReportePromedioDispPeriodo() {
	// 	EntityManagerHelper.beginTransaction();
	// 	EntityManagerHelper.persist();
	// 	EntityManagerHelper.commit();
	// 	EntityManagerHelper.closeEntityManager();
	// }

	// public static void getReportePorTransformador() {
	// 	EntityManagerHelper.beginTransaction();
	// 	EntityManagerHelper.persist();
	// 	EntityManagerHelper.commit();
	// 	EntityManagerHelper.closeEntityManager();
	// }
}
