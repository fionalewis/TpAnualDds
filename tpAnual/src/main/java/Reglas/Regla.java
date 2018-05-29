package Reglas;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.EnumUtils;
import Actuador.Actuador;
import Exceptions.CaracterInvalidoException;
import tpAnual.devices.DispositivoInteligente;
import tpAnual.devices.Sensor;

public class Regla {
	
	private DispositivoInteligente disp;
	private List<Condicion> condiciones = new ArrayList<>(); // condiciones q c/u es una comparacion binaria
	private List<Actuador> actuadores = new ArrayList<>(); //acciones
	private int contador;
	private String criterioCondiciones; // AND , OR
	private enum criterios{AND,OR}
	
	public Regla(DispositivoInteligente dispo, String critCond){
		this.disp = dispo;
		this.criterioCondiciones = critCond;
	}
	
	//getters y setters
	public void setCondiciones(List<Condicion> comparaciones){
		this.condiciones = comparaciones;
	}
	public void setDispositivo(DispositivoInteligente disp){
		this.disp = disp;
	}
	public void setActuadores(List<Actuador> acts){
		this.actuadores = acts;
	}
	public void agregarActuador(Actuador act){
		actuadores.add(act);
	}
	public void setComparacionCondiciones(String comparacion) throws CaracterInvalidoException{
		this.criterioCondiciones = comparacion;
		EnumUtils.isValidEnum(criterios.class, comparacion);	
	}
	public Condicion getUnaCondicion(int indice){
		return condiciones.get(indice);
	}
	public Condicion getCondicion(Condicion con){
		int i = condiciones.indexOf(con);
		return condiciones.get(i);
	}
	
	// ============================
	public void evaluarCondiciones(){
			
		for(Condicion conp:this.condiciones){
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
		if(contador == condiciones.size()){
			System.out.println("La regla cumplio todas las condiciones");
			for(Actuador act:actuadores){
				act.execute(disp);
			}
		}
	}
	
	public void evaluarCondicionesOR(int cont){
		if(contador>0){
			System.out.println("La regla cumplioal menos una condicion");
			for(Actuador act:actuadores){
				act.execute(disp);
			}
		}
	}
	
	//sensor --> variables
	//valor --> fijo
	//1 variable con 1 fijo o 2 variables
	//signo comparacion

	public void crearCondicionConDosSensores(Sensor sen1, Sensor sen2, String comparacion){ 
		Condicion comp = new CondicionDosSensores(this,sen1,sen2,comparacion); //sen1 comparacion sen2
		condiciones.add(comp);
	}
	public void crearCondicionSensoresYValor(Sensor sen1, double valorFijo, String comparacion){
		Condicion comp = new CondicionSensorYValor(this,sen1,valorFijo,comparacion); //sen1 comparacion valor
		condiciones.add(comp);
	}
	
}

