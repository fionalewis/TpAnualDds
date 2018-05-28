package tpAnual.devices;

import java.util.ArrayList;
import java.util.List;

import Commands.Actuador;
import Commands.Comparacion;

public class Sensor {
	
	private String nombreMagnitud;
	private double magnitud = 0;
	private int intervalo; //segundos
	private List<Comparacion> subscribers = new ArrayList<>(); 
	
	public void notificar(){
		for(Comparacion subs:subscribers){
			subs.update();
		}
	}
	
	public void setMetrica(double unaMagnitud){
		this.magnitud = unaMagnitud;
	}
	
	public String getNombreMagnitud(){
		return this.nombreMagnitud;
	}
	
	public double getMagnitud(){
		return this.magnitud;
	}
	
}
