package modelo.repositories;

import static org.junit.Assume.assumeNoException;

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

import db.EntityManagerHelper;
import modelo.deviceState.EstadoDispositivo;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.IntervaloDispositivo;
import modelo.users.Categoria;
import modelo.users.Cliente;

public class DispositivoRepository {
	
	//para los que no estan asociados a ningun cliente
	public static void addDispositivo(Dispositivo dispositivo) { 
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(dispositivo);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	public static void addDispositivoConCliente(String nro, Dispositivo d) {
		addDispositivo(d);
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Dispositivo set cliente_id='"+nro+"' where id = "+d.id).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	public static void deleteDispositivo(String nroDoc, int dispId) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createNativeQuery("SET SQL_SAFE_UPDATES = 0; ").executeUpdate();
		EntityManagerHelper.getEntityManager().createNativeQuery("delete FROM tp_anual_dds.intervalodispositivo where dispositivo_id = "+dispId+";").executeUpdate();
		EntityManagerHelper.getEntityManager().createNativeQuery("delete FROM tp_anual_dds.dispositivo where cliente_id = '"+nroDoc+"' and id = "+dispId+";").executeUpdate();
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

	public static void dropTablas() {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.entityManager().createNativeQuery("drop table if exists tp_anual_dds.administrador;").executeUpdate();
		EntityManagerHelper.entityManager().createNativeQuery("drop table if exists tp_anual_dds.actuador;").executeUpdate();
		EntityManagerHelper.entityManager().createNativeQuery("drop table if exists tp_anual_dds.administrador;").executeUpdate();
		EntityManagerHelper.entityManager().createNativeQuery("drop table if exists tp_anual_dds.intervalodispositivo;").executeUpdate();
		EntityManagerHelper.entityManager().createNativeQuery("drop table if exists tp_anual_dds.dispositivo;").executeUpdate();
		EntityManagerHelper.entityManager().createNativeQuery("drop table if exists tp_anual_dds.cliente;").executeUpdate();
		EntityManagerHelper.entityManager().createNativeQuery("drop table if exists tp_anual_dds.transformador;").executeUpdate();
		EntityManagerHelper.entityManager().createNativeQuery("drop table if exists tp_anual_dds.condicion;").executeUpdate();
		EntityManagerHelper.entityManager().createNativeQuery("drop table if exists tp_anual_dds.registromediciones;").executeUpdate();
		EntityManagerHelper.entityManager().createNativeQuery("drop table if exists tp_anual_dds.regla;").executeUpdate();
		EntityManagerHelper.entityManager().createNativeQuery("drop table if exists tp_anual_dds.sensor;").executeUpdate();
		EntityManagerHelper.entityManager().createNativeQuery("drop table if exists tp_anual_dds.categoria;").executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}

	//public static void addDispConCliente(Long id, String nroDoc) {
	//	EntityManagerHelper.beginTransaction();
	//	//EntityManagerHelper.createNativeQuery("INSERT INTO Dispositivo VALUES (?,?)").setParameter(param, value);
	//	EntityManagerHelper.commit();
	//	EntityManagerHelper.closeEntityManager();
	//}

}
