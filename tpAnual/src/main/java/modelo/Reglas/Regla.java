package modelo.Reglas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.lang3.EnumUtils;

import exceptions.CaracterInvalidoException;
import modelo.Actuador.Actuador;
import modelo.devices.DispositivoInteligente;
import modelo.devices.Sensor;
@Entity
public class Regla {
	
	@Id @GeneratedValue
	private Long id;
	private String nombreRegla; //solo para mostrar en el main
	@OneToMany
	private List<Condicion> condiciones = new ArrayList<>(); // condiciones q c/u es una comparacion binaria
	@OneToMany
	private List<Actuador> actuadores = new ArrayList<>(); //acciones
	@Transient
	private int contador;
	@Transient
	private DispositivoInteligente disp;
	private String criterioCondiciones; // AND , OR
	
	private enum criterios{AND,OR}
	//private boolean state; //para test NO AGREGAR AL DIAGRAMA DE CLASES
	
	public Regla(String unNombreRegla,DispositivoInteligente unDispo,String critCond){
		this.nombreRegla = unNombreRegla;
		this.disp = unDispo;
		this.criterioCondiciones = critCond;
	}
	
	//getters y setters
	public void setCondiciones(List<Condicion> comparaciones){
		this.condiciones = comparaciones;
	}
	public List<Condicion> getCondiciones(){
		return this.condiciones;
	}
	public void agregarCondicion(Condicion unaCondicion){
		condiciones.add(unaCondicion);
	}
	public void quitarCondicion(Condicion unaCondicion){
		condiciones.remove(unaCondicion);
	}
	public void setActuadores(List<Actuador> acts){
		this.actuadores = acts;
	}
	public List<Actuador> getActuadores(){
		return this.actuadores;
	}
	public void agregarActuador(Actuador act){
		actuadores.add(act);
	}
	public void quitarActuador(Actuador act){
		actuadores.remove(act);
	}
	public void setComparacionCondiciones(String comparacion) throws CaracterInvalidoException{
		this.criterioCondiciones = comparacion;
		EnumUtils.isValidEnum(criterios.class, comparacion);	
	}
	public String getComparacionCondiciones(){
		return this.criterioCondiciones;
	}
	public Condicion getCondicionConIndice(int indice){
		return condiciones.get(indice);
	}
	public Condicion getCondicion(Condicion con){
		return getCondicionConIndice(condiciones.indexOf(con));
	}
	/*public boolean getState(){
		return this.state;
	}
	public void setNombreRegla(String unNombreRegla){
		this.nombreRegla = unNombreRegla;
	}
	public String getNombreRegla(){
		return this.nombreRegla;
	}*/
	// ============================
	public void aplicarRegla(){
			
		for(Condicion con:this.condiciones){
			if(con.getEstado()){
				contador++;
			}
		}
		
		switch (criterioCondiciones){
			case "AND":
				evaluarCondicionesAND(contador);
				break;
			case "OR":
				evaluarCondicionesOR(contador);
				break;
			default: return;
		}
	}
	
	public void evaluarCondicionesAND(int cont){
		if(contador == condiciones.size()){
			System.out.println("La regla cumplio todas las condiciones");
			for(Actuador act:actuadores){
				act.execute(disp);
			}
			//his.state = true; //para test 
		}else{
			System.out.println("No se cumplieron todas las condiciones para la regla de criterio AND");
		}
	}
	
	public void evaluarCondicionesOR(int cont){
		if(contador>0){
			System.out.println("La regla cumplio al menos una condicion");
			for(Actuador act:actuadores){
				act.execute(disp);
			}
			//this.state = true; //para test
		}else{
			System.out.println("No se cumplio ninguna condicion");
		}
	}
	
	//sensor --> variables
	//valor --> fijo
	//1 variable con 1 fijo o 2 variables
	//signo comparacion

	public void crearCondicionDosSensores(Sensor sen1, Sensor sen2, String comparacion){ 
		Condicion comp = new CondicionDosSensores(sen1,sen2,comparacion); //sen1 comparacion sen2
		condiciones.add(comp);
	}
	public void crearCondicionSensoresYValor(Sensor sen1, double valorFijo, String comparacion){
		Condicion comp = new CondicionSensorYValor(sen1,valorFijo,comparacion); //sen1 comparacion valor
		condiciones.add(comp);
	}
	
}

