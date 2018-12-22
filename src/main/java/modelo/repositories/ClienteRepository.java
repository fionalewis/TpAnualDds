package modelo.repositories;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;

import db.EntityManagerHelper;
import modelo.devices.Dispositivo;
import modelo.users.Cliente;
import modelo.users.Cliente.TipoDocumento;


public class ClienteRepository {
	
	public static void addCliente(Cliente cliente) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(cliente);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
		System.out.println(cliente.getCateg());
	}

	public static void addClienteConDispositivos(Cliente cliente) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(cliente);
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
		for (Dispositivo d : cliente.getDispositivos()){ 
			DispositivoRepository.addDispositivoConCliente(cliente.getNroDoc(), d);
		}
		System.out.println(cliente.getCateg());
	}

	public static void addClienteConDispositivosEIntervalos(Cliente cliente) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.persist(cliente);
		EntityManagerHelper.commit();		
		EntityManagerHelper.closeEntityManager();
		for (Dispositivo d : cliente.getDispositivos()){ 
			DispositivoRepository.addDispositivoEIntervaloConCliente(cliente.getNroDoc(),d);
		}
	}

	public static List<Cliente> getTodosLosClientes() {
		EntityManagerHelper.beginTransaction();
		List<Cliente> cli = EntityManagerHelper.getEntityManager().createNativeQuery("SELECT * FROM Cliente", Cliente.class).getResultList();
		EntityManagerHelper.closeEntityManager();
		return cli;
	}
			
	public static String getnombrecli(String nrodoc) {
		EntityManagerHelper.beginTransaction();
		String cli = (String) EntityManagerHelper.getEntityManager().createNativeQuery("select nombre FROM tp_anual_dds.cliente where cliente_id = '"+nrodoc+"'").getSingleResult();
		EntityManagerHelper.closeEntityManager();
		return cli;
	}
	
	public static Cliente getCliente(String dni) {
		Cliente cliente = EntityManagerHelper.getEntityManager().find(Cliente.class,dni);
	    return cliente;
	}	
	
	public static Cliente obtenerCliente(String username) throws NoResultException{
		List<Cliente> lista = new ClienteRepository().getTodosLosClientes();
		Iterator<Cliente> iterator = lista.iterator();
	    while (iterator.hasNext()) {
	        Cliente cliente = iterator.next();
	        if (cliente.getUserName().equals(username)) {
	            return cliente;
	        }}
	        	return null;
	        }
	
	public static void updateNombre(String nroDoc, String nombre) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Cliente as c SET c.nombre = :comp WHERE c.nroDoc = '" 
				+ nroDoc + "'");
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	public static void updateApellido(String nroDoc, String apellido) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Cliente as c SET c.apellido = :a WHERE c.nroDoc = '" 
				+ nroDoc + "'")
			.setParameter("a",apellido)
			.executeUpdate();
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
	
	public static void updateConsumo(String nombre, double consumo) {
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().createQuery("UPDATE Cliente as c SET c.consumo = :consumo WHERE c.nombre = '" 
				+ nombre + "'").setParameter("consumo", consumo);
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
		EntityManagerHelper.getEntityManager().createQuery("DELETE FROM Cliente WHERE nroDoc = '"
				+ nroDoc).executeUpdate();
		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
	}
	
	
	

	
}
