package modelo.geoLocation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import exceptions.ExceptionsHandler;
import modelo.DAOJson;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoInteligente;
import modelo.repositories.ClienteRepository;
import modelo.repositories.DispositivoRepository;
import modelo.repositories.TransformadorRepository;
import modelo.users.Cliente;
import modelo.users.Reporte;

//Por ahora esta clase se maneja con un json de clientes como su "BDD" para poder modificar los atributos del cliente,
//como registrar cambios de domicilio y reasignarle el transformador, o para obtener los clientes que tienen un X transformador
//asignado y necesitamos conocer en el metodo suministroActual, pero esto a futuro no va a ser con un json creo
@Entity
public class Transformador {
	@Id
	@Column(name="transformador_id")
	private int idTransformador;
	private String zona; //dada por el ENRE

	@OneToMany(mappedBy="transformadorActual",cascade=CascadeType.ALL)
	private List<Cliente> cli;

	@OneToMany(mappedBy="transf", cascade=CascadeType.ALL)
	private List<Reporte> reportes = new ArrayList<>();

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="longitude", column=@Column(name="longitud")),
		@AttributeOverride(name="latitude", column=@Column(name="latitud"))
	})
	private GeoLocation ubicacion;
	@Transient
	double suministroActual;
	public Transformador(){}
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
		List<Cliente> listaClientes = new ArrayList<>();
		List<Double> consumo = new ArrayList<>();
		
		listaClientes = ClienteRepository.getTodosLosClientes();
		// try {
		// 	listaClientes = DAOJson.deserializarListaTransf(Cliente.class, "\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\JsonsParaPruebas\\clientesPruebaParaTransformador.json");
			
		// } catch (Exception e) {
		// 	ExceptionsHandler.catchear(e);
		// }
		
		for (Cliente c : listaClientes){
			if (c.getTransformadorActual() != null && c.getTransformadorActual().getIdTransformador() == idTransformador) {	
				for (Dispositivo d : DispositivoRepository.getDispositivosDeUnCliente(c.getNroDoc())){				
					if (d.getEsInteligente()) {
						consumo.add( ((DispositivoInteligente) d).consumoTotalEntre(d.getFechaRegistro(), LocalDateTime.now()) );
					} else {
						consumo.add( d.consumoTotal() );
					}
					//ver que hacer con los convertidos
				}
			}			
		}
		this.suministroActual = consumo.stream().mapToDouble( d -> d ).sum();
		return suministroActual;
	}

}