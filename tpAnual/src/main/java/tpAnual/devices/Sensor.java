package tpAnual.devices;

import java.util.ArrayList;
import java.util.List;
import Reglas.Condicion;

public class Sensor {
	
	private String nombreMagnitud;
	private double magnitud = 0;
	private int intervalo = 10; //segundos
	private List<Condicion> subscribers = new ArrayList<>(); 
	
	public Sensor(String nomMag, DispositivoInteligente dispo){
		this.nombreMagnitud = nomMag;
	}
	
	//funcionalidades
	public void medir(){
		notificar();
	}
	public void notificar(){
		for(Condicion subs:subscribers){
			subs.update();
		}
	}
	public void subscribir(Condicion cond){
		subscribers.add(cond);
	}
	
	//magnitud
	public void setMagnitud(double unaMagnitud){
		this.magnitud = unaMagnitud;
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
	
	//intervalo
	public void setIntervalo(int unIntervalo){ //en segundos
		this.intervalo = unIntervalo;
	}
	public double getIntervalo(){
		return this.intervalo;
	}
}
