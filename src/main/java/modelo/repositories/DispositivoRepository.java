package modelo.repositories;

import static org.junit.Assume.assumeNoException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.hql.internal.ast.tree.IntoClause;
import org.uqbarproject.jpa.java8.extras.convert.LocalDateTimeConverter;

import java.time.LocalDateTime;

import db.EntityManagerHelper;
import modelo.deviceState.EstadoDispositivo;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.IntervaloDispositivo;
import modelo.geoLocation.Zona;
import modelo.users.Categoria;
import modelo.users.Cliente;
import modelo.users.Reporte;

public class DispositivoRepository {
	
	//para los que no estan asociados a ningun cliente
	public static void addDispositivo(Dispositivo dispositivo) { 
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(dispositivo);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	public static Long addDispMerge(Dispositivo dispositivo) { 
		EntityManagerHelper.beginTransaction();
		Dispositivo managedEntity = (Dispositivo) EntityManagerHelper.merge(dispositivo);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
		return managedEntity.id;		
	}

	public static void addDispositivoConCliente(String nro, Dispositivo d) {
		Long idPreMerge = addDispMerge(d); //es necesario porque hibernate es un estúpido
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Dispositivo set cliente_id='"+nro+"' where id = "+idPreMerge).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	public static void addDispositivoEIntervaloConCliente(String nro, Dispositivo d) {
		Long idPreMerge = addDispMerge(d); //es necesario porque hibernate es un estúpido
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Dispositivo set cliente_id='"+nro+"' where id = "+idPreMerge).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();		
		addIntervaloDispositivo(idPreMerge, ((DispositivoInteligente) d).getIntervalos());
	}
	public static Long addIntDispMerge(IntervaloDispositivo dispositivo) { 
		EntityManagerHelper.beginTransaction();
		IntervaloDispositivo managedEntity = (IntervaloDispositivo) EntityManagerHelper.merge(dispositivo);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
		return managedEntity.id;
		
	}
	public static void addIntervaloDispositivo(Long nro, List<IntervaloDispositivo> intd) {
		for(IntervaloDispositivo d : intd){
			Long idPreMerge = addIntDispMerge(d); //es necesario porque hibernate es un estúpido
			EntityManagerHelper.beginTransaction();
			EntityManagerHelper.getEntityManager().createQuery("UPDATE IntervaloDispositivo set dispositivo_id="+nro+" where id="+idPreMerge).executeUpdate();//fin='"+d.getFin()+"' and inicio='"+d.getInicio()+"' and modo='"+d.getModo()+"'").executeUpdate();
			EntityManagerHelper.commit();
			EntityManagerHelper.closeEntityManager();
			EntityManagerHelper.beginTransaction();
			EntityManagerHelper.getEntityManager().createNativeQuery("SET SQL_SAFE_UPDATES = 0; ").executeUpdate();
			EntityManagerHelper.getEntityManager().createNativeQuery("delete FROM tp_anual_dds.intervalodispositivo where dispositivo_id is null").executeUpdate();
			EntityManagerHelper.getEntityManager().createNativeQuery("SET SQL_SAFE_UPDATES = 1;").executeUpdate();
			EntityManagerHelper.commit();
			EntityManagerHelper.closeEntityManager();
		}
	}

	public static void deleteDispositivo(String nroDoc, long dispId) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createNativeQuery("SET SQL_SAFE_UPDATES = 0; ").executeUpdate();
		EntityManagerHelper.getEntityManager().createNativeQuery("delete FROM tp_anual_dds.intervalodispositivo where dispositivo_id = "+dispId+";").executeUpdate();
		EntityManagerHelper.getEntityManager().createNativeQuery("delete FROM tp_anual_dds.dispositivo where cliente_id = '"+nroDoc+"' and dispositivo_id = "+dispId+";").executeUpdate();
		EntityManagerHelper.getEntityManager().createNativeQuery("SET SQL_SAFE_UPDATES = 1;").executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	//meto el de categ acá despues lo muevo 
	
	public static void addCategorias(Categoria cat) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.merge(cat);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	public static void addZonas(Zona zona) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.merge(zona);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static List<Zona> getListaZonas() {
		EntityManagerHelper.beginTransaction();
		List<Zona> transf = EntityManagerHelper.getEntityManager().createNativeQuery("SELECT * FROM Zona", Zona.class).getResultList();
		EntityManagerHelper.closeEntityManager();
		return transf;
	}

	public static List<Dispositivo> getListaDispositivos(String tipo) {
		if(tipo=="estandar"){
			EntityManagerHelper.beginTransaction();
			List<Dispositivo> disp = EntityManagerHelper.getEntityManager().createNativeQuery("SELECT * FROM Dispositivo where TIPO = 'E'", DispositivoEstandar.class).getResultList();
			EntityManagerHelper.closeEntityManager();
			return disp;
		}else{
			EntityManagerHelper.beginTransaction();
			List<Dispositivo> disp = EntityManagerHelper.getEntityManager().createNativeQuery("SELECT * FROM Dispositivo where (TIPO = 'C' or TIPO = 'I')", DispositivoInteligente.class).getResultList();
			EntityManagerHelper.closeEntityManager();
			return disp;
		}		
	}
	
	public static List<Dispositivo> getDispositivosLegales() {
			EntityManagerHelper.beginTransaction();
			List<Dispositivo> disp = EntityManagerHelper.getEntityManager().createNativeQuery("SELECT * FROM Dispositivo where cliente_id is null", Dispositivo.class).getResultList();
			EntityManagerHelper.closeEntityManager();
			return disp;
	}
	
	public static Dispositivo getDispositivoById(String id) {
		EntityManagerHelper.beginTransaction();
		Dispositivo disp = (Dispositivo) EntityManagerHelper.getEntityManager().createNativeQuery("SELECT * FROM Dispositivo where dispositivo_id ="+id, Dispositivo.class).getSingleResult();
		EntityManagerHelper.closeEntityManager();
		return disp;
	}
	
	public static List<IntervaloDispositivo> getIntervalosDispositivo(Long idDisp){
		EntityManagerHelper.beginTransaction();
		List<IntervaloDispositivo> intervalos = EntityManagerHelper.getEntityManager().createNativeQuery("SELECT * from IntervaloDispositivo where dispositivo_id = "+idDisp,IntervaloDispositivo.class).getResultList();
		EntityManagerHelper.closeEntityManager();
		return intervalos;
	}
	
	public static void actualizarIntervaloDispositivo(Long idIntervalo, LocalDateTime fecha){
		EntityManagerHelper.beginTransaction();
		Timestamp time = new LocalDateTimeConverter().convertToDatabaseColumn(fecha);

		EntityManagerHelper.getEntityManager().createQuery("UPDATE IntervaloDispositivo SET fin = '"+ time +"' where id = "+idIntervalo).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createNativeQuery("SET SQL_SAFE_UPDATES = 0; ").executeUpdate();
		EntityManagerHelper.getEntityManager().createNativeQuery("delete FROM tp_anual_dds.intervalodispositivo where dispositivo_id is null").executeUpdate();
		EntityManagerHelper.getEntityManager().createNativeQuery("SET SQL_SAFE_UPDATES = 1;").executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	public static List<Dispositivo> getDispositivosDeUnCliente(String id) {
		EntityManagerHelper.beginTransaction();
		List<Dispositivo> disp = EntityManagerHelper.getEntityManager().createNativeQuery("SELECT * FROM Dispositivo where cliente_id = '"+id+"'", Dispositivo.class).getResultList();
		EntityManagerHelper.closeEntityManager();
		return disp;
	}
	
	public static List<DispositivoInteligente> getDispositivosDeUnClienteI(String id) {
		EntityManagerHelper.beginTransaction();
		List<DispositivoInteligente> disp = EntityManagerHelper.getEntityManager().createNativeQuery("SELECT * FROM Dispositivo where cliente_id = '"+id+"'", Dispositivo.class).getResultList();
		EntityManagerHelper.closeEntityManager();
		return disp;
	}

	//public static void addDispConCliente(Long id, String nroDoc) {
	//	EntityManagerHelper.beginTransaction();
	//	//EntityManagerHelper.createNativeQuery("INSERT INTO Dispositivo VALUES (?,?)").setParameter(param, value);
	//	EntityManagerHelper.commit();
	//	EntityManagerHelper.closeEntityManager();
	//}login-admin

}
