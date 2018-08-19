package modelo.geoLocation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

import exceptions.ExceptionsHandler;
import modelo.users.Cliente;

//Por ahora esta clase se maneja con un json de clientes como su "BDD" para poder modificar los atributos del cliente,
//como registrar cambios de domicilio y reasignarle el transformador, o para obtener los clientes que tienen un X transformador
//asignado y necesitamos conocer en el metodo suministroActual, pero esto a futuro no va a ser con un json creo

public class Transformador {

	private int idTransformador;
	private String zona; //dada por el ENRE
	private GeoLocation ubicacion;
	
	public Transformador(String zona,double lat,double lng) {
		this.zona = zona;
		this.ubicacion = new GeoLocation(lat,lng);
	}
	
	public Transformador(double lat,double lng) {
		this.ubicacion = new GeoLocation(lat,lng);
	}
	
	public int getIdTransformador() {
		return idTransformador;
	}
	public GeoLocation getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(GeoLocation ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	
	//Funcionalidades

	public boolean perteneceA(String nombreZona) {
		return getZona().equals(nombreZona);
	}

	public double suministroActual() { //no funciona x ahora
		
		List<Cliente> clientesConEsteTransf = new ArrayList<>();		
		Gson gson = new Gson();		
		BufferedReader buffReader = null;
		try {
			buffReader = new BufferedReader(new FileReader("C:\\Users\\Salome\\git\\TpAnualDdS\\tpAnual\\JSONs\\JsonsParaPruebas\\clientesPrueba.json"));
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
	    Cliente[] arrayCli = gson.fromJson(buffReader, Cliente[].class);
	
		for(int i = 0;i<arrayCli.length;i++) {
			Cliente c = new Cliente();
			c.setDomicilio(arrayCli[i].getDomicilio()); //muy basico por ahora, solo queremos obtener el consumo de quienes tengan este transf asignado
			c.setNombre(arrayCli[i].getNombre());
			c.setTelefono(arrayCli[i].getTelefono());
			c.setDispositivos(arrayCli[i].getDispositivos());
			c.setApellido(arrayCli[i].getApellido());
			c.setUserName(arrayCli[i].getUserName());
			c.setPassword(arrayCli[i].getPassword());
			c.setDispositivos(arrayCli[i].getDispositivos());
			int idAEvaluar = c.getTransformadorActual().getIdTransformador();
			if(idAEvaluar == idTransformador) {
					clientesConEsteTransf.add(c);
				}
			}
		
		return clientesConEsteTransf.stream().mapToDouble(unCliente-> unCliente.calcularConsumo()).sum();
	}

}