package modelo.devices;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import modelo.Reglas.Condicion;

@Entity
@Embeddable
public class Sensor {
	
	@Id @GeneratedValue
	private Long id;
	private String nombreMagnitud;
	
	@ElementCollection
	@CollectionTable(name = "registro_mediciones")
	private List<Double> mediciones = new ArrayList<Double>();
	
	private double magnitud = 0;
	private int intervalo = 10; //segundos
	@Transient
	private List<Condicion> subscribers = new ArrayList<>();
	
	public Sensor(String nomMag, double valor, int unIntervalo){
		this.nombreMagnitud = nomMag;
		this.magnitud = valor;
		this.intervalo = unIntervalo;
	}
	
	//funcionalidades
	public void notificar(){
		for(Condicion subs:subscribers){
			subs.update();
		}
	}
	public void subscribir(Condicion cond){
		subscribers.add(cond);
	}
	
	//para disparar evaluar las condiciones
	public void medir(){
		notificar();
	}
	
	//magnitud
	public void setMagnitud(double unaMagnitud){
		this.magnitud = unaMagnitud;
		mediciones.add(magnitud);
	}
	public double getMagnitud(){
		return this.magnitud;
	}
	public void setNombreMagnitud(String unNombre){
		this.nombreMagnitud = unNombre;
	}
	public String getNombreMagnitud(){
		return this.nombreMagnitud;
	}
	public void aumentarMagnitud(double valor){
		this.magnitud += valor;
	}
	public void disminuirMagnitud(double valor){
		this.magnitud -= valor;
	}
	public List<Condicion> getSubscribers(){
		return null;//subscribers;
	}
	//intervalo
	public void setIntervalo(int unIntervalo){ //en segundos
		this.intervalo = unIntervalo;
	}
	public double getIntervalo(){
		return this.intervalo;
	}
}
