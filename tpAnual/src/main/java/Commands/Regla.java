package Commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;

import Commands.Comparacion.SignosComparaciones;
import Exceptions.CaracterInvalidoException;
import tpAnual.devices.DispositivoInteligente;
import tpAnual.devices.Sensor;

public class Regla {
	
	//private List<Sensor> sensores = new ArrayList<>(); // valores
	private DispositivoInteligente disp;
	
	private List<Comparacion> comparaciones = new ArrayList<>(); // condiciones q c/u es una comparacion binaria
	private List<Actuador> actuadores = new ArrayList<>(); //acciones
	private int contador;
	private String criterioCondiciones; // AND , OR
	private enum criterios{AND,OR}
	
	public Regla(DispositivoInteligente dispo, String critCond){
		this.disp = dispo;
		this.criterioCondiciones = critCond;
	}
	
	public void setCondiciones(List<Comparacion> comparaciones){
		this.comparaciones = comparaciones;
	}
	
	public void setDispositivo(DispositivoInteligente disp){
		this.disp = disp;
	}
	
	public void setComparacionCondiciones(String comparacion) throws CaracterInvalidoException{
		this.criterioCondiciones = comparacion;
		EnumUtils.isValidEnum(criterios.class, comparacion);	
	}
	
	// ============================
	public void evaluarCondiciones(){
			
		for(Comparacion conp:this.comparaciones){
			if(conp.getEstado()){
				contador++;
			}
		}
		switch (criterioCondiciones){
			case "AND":
				evaluarCondicionesAND(contador);
			case "OR":
				evaluarCondicionesOR(contador);
			default: return;
		}
	}
	
	public void evaluarCondicionesAND(int cont){
		if(contador == comparaciones.size()){
			for(Actuador act:actuadores){
				//execute actuadores
			}
		}
	}
	
	public void evaluarCondicionesOR(int cont){
		if(contador>0){
			for(Actuador act:actuadores){
				//execute actuadores
			}
		}
	}
	
	//sensor --> variables
	//valor --> fijo
	//1 variable con 1 fijo o 2 variables
	//signo comparacion
	
	public void crearCondicionConDosSensores(Sensor sen1, Sensor sen2, String comparacion){
		Comparacion comp = new ComparacionSensores(this,sen1,sen2,comparacion); //sen1 comparacion sen2
		comparaciones.add(comp);
	}
	public void crearCondicionSensoresYValor(Sensor sen1, double valorFijo, String comparacion){
		Comparacion comp = new ComparacionSensorYValor(this,sen1,valorFijo,comparacion); //sen1 comparacion valor
		comparaciones.add(comp);
	}
}

