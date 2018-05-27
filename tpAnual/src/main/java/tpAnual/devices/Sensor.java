package tpAnual.devices;

import java.util.ArrayList;
import java.util.List;

import Commands.Actuador;

public class Sensor {
	private String nombreMagnitud;
	private double magnitud = 0;
	private List<Actuador> actuadores = new ArrayList<>();
	
	public void notificar(){
		for(Actuador act:actuadores){
			act.recibirMagnitud(this);
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
