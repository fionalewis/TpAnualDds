package modelo.repositories;

import java.util.List;

import db.EntityManagerHelper;
import modelo.Actuador.Actuador;
import modelo.devices.Dispositivo;

public class ActuadorRepository {
	public static void addActuador(Actuador actuador) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(actuador);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static String getActuadorOrden(Long idDisp){
		EntityManagerHelper.beginTransaction();
		String orden = (String) EntityManagerHelper.getEntityManager().createNativeQuery("SELECT orden FROM actuador WHERE idFabricante ="+ idDisp , String.class).getResultList().get(0);
		EntityManagerHelper.closeEntityManager();
		return orden;
	}
	
	public static Actuador getActuador(Long idDisp){
		EntityManagerHelper.beginTransaction();
		List<Actuador> aux =  EntityManagerHelper.getEntityManager().createNativeQuery("SELECT * FROM actuador WHERE idFabricante ="+ idDisp , Actuador.class).getResultList();
		EntityManagerHelper.closeEntityManager();
		if(aux.size()<1){
			return null;
		}else {
			Actuador actuador = aux.get(0);
			return actuador;
		}
		
	}
	
	public static Long getActuadorReglaId(Long idDisp){
		EntityManagerHelper.beginTransaction();
		Long reglaId = (Long) EntityManagerHelper.getEntityManager().createNativeQuery("SELECT Regla FROM actuador WHERE idFabricante ="+ idDisp , Long.class).getResultList().get(0);
		EntityManagerHelper.closeEntityManager();
		return reglaId;
	}
}
