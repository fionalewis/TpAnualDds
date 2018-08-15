package modelo.geoLocation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

import modelo.users.Cliente;

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

	public double suministroActual() {
		
		List<Cliente> clientesConEsteTransf = new ArrayList<>();		
		Gson gson = new Gson();		
		BufferedReader buffReader = null;
		try {
			buffReader = new BufferedReader(new FileReader("\\JSONs\\JsonsParaPruebas\\clientesPrueba.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace(); //Falta manejar la excepcion
		}
	    Cliente[] arrayCli = gson.fromJson(buffReader, Cliente[].class);
	
		for(int i = 0;i<arrayCli.length;i++) {
			Cliente c = new Cliente();
			c.setDomicilio(arrayCli[i].getDomicilio());
			c.setNombre(arrayCli[i].getNombre());
			int idAEvaluar = c.getTransformadorActual().getIdTransformador();
			if(idAEvaluar == idTransformador) {
					clientesConEsteTransf.add(c);
				}
			}		
		return clientesConEsteTransf.stream().mapToDouble(unCliente-> unCliente.calcularConsumo()).sum();
	}

}
