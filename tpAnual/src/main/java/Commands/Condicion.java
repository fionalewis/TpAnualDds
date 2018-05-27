package Commands;

import Exceptions.CaracterInvalidoException;
import tpAnual.devices.Sensor;

public class Condicion {
	
	protected double condicion; //valor double
	protected Sensor sensor;
	protected double valorSensor;
	protected String comparacion;
	
	public boolean evaluar() throws CaracterInvalidoException{
		
		switch (this.comparacion){
		case ">":
			return this.comparacionMayor();
		case "<":
			return this.comparacionMenor();
		case "=":
			return this.comparacionIgual();
		case "!=":
			return this.comparaciondesigual();
		default: throw new CaracterInvalidoException("ingrese >, <, = o !=");
		}
		
	}
	
	public boolean comparacionMayor(){
		if (valorSensor>condicion){
			return true;
		}else 
			return false;
	}
	
	public boolean comparacionMenor(){
		if (valorSensor>condicion){
			return true;
		}else 
			return false;
	}
	
	public boolean comparacionIgual(){
		if (valorSensor==condicion){
			return true;
		}else 
			return false;
	}
	public boolean comparaciondesigual(){
		if (valorSensor!=condicion){
			return true;
		}else 
			return false;
	}
	
	// SETTERS Y GETTERS
	public void setCondicion(double condicionAComparar){
		this.condicion = condicionAComparar;
	}
	public void setSensor(Sensor sensor){
		this.sensor = sensor;
		this.valorSensor = sensor.getMagnitud();
	}
	public void setComparacion(String comparacion){
		this.comparacion = comparacion;
	}
}
