package modelo.Actuador;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import modelo.devices.Dispositivo;
import modelo.devices.DispositivoInteligente;
@Entity
public class Actuador {
	@Id @GeneratedValue
	private Long id;
	private int idFabricante; 
	private String orden;//orden: PRENDER,APAGAR,AHORRO
	@Transient
	private ActuadorAdapter adapter = new ActuadorAdapter();
	
	public Actuador(int unIDFab, String unaOrden){ 
		idFabricante = unIDFab;
		orden = unaOrden;
		ActuadorAdapter adapter = new ActuadorAdapter();
		this.adapter = adapter;
		
	}
	
	public Actuador(){}
	
	public void execute(Dispositivo dispo){
		adapter.execute(idFabricante,(DispositivoInteligente) dispo, orden);
	}
	
	// getters y setters
	public void setOrden(String unaOrden){
		this.orden = unaOrden;
	}
	public void setAdapter(ActuadorAdapter unAdapter){
		this.adapter = unAdapter;
	}
	public void setIdFabbricante(int unID){
		this.idFabricante = unID;
	}
	public String getOrden(){
		return this.orden;
	}
	public ActuadorAdapter getAdapter(){
		return this.adapter;
	}
	public int getIdFabbricante(){
		return this.idFabricante;
	}
	
	public Long getId() {
		return this.id;
	}
	
	
}
