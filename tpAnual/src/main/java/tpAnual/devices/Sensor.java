package tpAnual.devices;

import java.util.ArrayList;
import java.util.List;

import Reglas.Comparacion;
import tpAnual.Actuador;

public class Sensor {
	
	private String nombreMagnitud;
	private double magnitud = 0;
	private int intervalo; //segundos
	private DispositivoInteligente disp;
	private List<Comparacion> subscribers = new ArrayList<>(); 
	
	public Sensor(String nomMag, DispositivoInteligente dispo){
		this.nombreMagnitud = nomMag;
		this.disp = dispo;
	}
	
	public void notificar(){
		for(Comparacion subs:subscribers){
			subs.update();
		}
	}
	
	public void medir(){
		notificar();
	}
	
	public void setMagnitud(double unaMagnitud){
		this.magnitud = unaMagnitud;
	}
	
	public String getNombreMagnitud(){
		return this.nombreMagnitud;
	}
	
	public double getMagnitud(){
		return this.magnitud;
	}
	
}
