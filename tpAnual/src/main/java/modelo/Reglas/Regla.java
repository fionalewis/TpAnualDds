package modelo.Reglas;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.EnumUtils;

import Exceptions.CaracterInvalidoException;
import modelo.Actuador.Actuador;
import modelo.devices.DispositivoInteligente;
import modelo.devices.Sensor;

public class Regla {
	
	private List<Condicion> condiciones = new ArrayList<>(); // condiciones q c/u es una comparacion binaria
	private List<Actuador> actuadores = new ArrayList<>(); //acciones
	private int contador;
	private DispositivoInteligente disp;
	private String criterioCondiciones; // AND , OR
	private enum criterios{AND,OR}
	private boolean state; //para test NO AGREGAR AL DIAGRAMA DE CLASES
	
	public Regla(DispositivoInteligente unDispo,String critCond){
		this.disp = unDispo;
		this.criterioCondiciones = critCond;
	}
	
	//getters y setters
	public void setCondiciones(List<Condicion> comparaciones){
		this.condiciones = comparaciones;
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
	public Condicion getCondicionConIndice(int indice){
		return condiciones.get(indice);
	}
	public Condicion getCondicion(Condicion con){
		return getCondicionConIndice(condiciones.indexOf(con));
	}
	public boolean getState(){
		return this.state;
	}
	// ============================
	public void aplicarRegla(){
			
		for(Condicion con:this.condiciones){
			if(con.getEstado()){
				contador++;
			}
		}
		
		if(criterioCondiciones.equals("AND")){
			evaluarCondicionesAND(contador);
		}else evaluarCondicionesOR(contador);
		
		/*switch (criterioCondiciones){
			case "AND":
				evaluarCondicionesAND(contador);
			case "OR":
				evaluarCondicionesOR(contador);
			default: return;
		}*/
	}
	
	public void evaluarCondicionesAND(int cont){
		if(contador == condiciones.size()){
			System.out.println("La regla cumplio todas las condiciones");
			for(Actuador act:actuadores){
				act.execute(disp);
			}
			this.state = true; //para test 
		}
	}
	
	public void evaluarCondicionesOR(int cont){
		if(contador>0){
			System.out.println("La regla cumplio al menos una condicion");
			for(Actuador act:actuadores){
				act.execute(disp);
			}
			this.state = true; //para test
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

