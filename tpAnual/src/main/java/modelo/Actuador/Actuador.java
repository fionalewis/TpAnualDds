package modelo.Actuador;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import modelo.devices.Dispositivo;
@Entity
public class Actuador {
	@Id
	private int idFabricante; 
	private String orden;
	@Transient
	private ActuadorAdapter adapter = new ActuadorAdapter();
	
	public Actuador(int unIDFab, String unaOrden){
		idFabricante = unIDFab;
		orden = unaOrden;
		
	}
	
	public void execute(Dispositivo dispo){
		adapter.execute(idFabricante,dispo, orden);
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
	
	
}
