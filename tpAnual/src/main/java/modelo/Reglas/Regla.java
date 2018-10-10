package modelo.Reglas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.commons.lang3.EnumUtils;

import exceptions.CaracterInvalidoException;
import modelo.Actuador.Actuador;
import modelo.devices.DispositivoInteligente;
import modelo.devices.Sensor;
@Entity
public class Regla {
	
	@Id
	private String nombreRegla; //solo para mostrar en el main
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "Regla")
	private List<CondicionSensorYValor> condicionesSYV = new ArrayList<>(); // condiciones q c/u es una comparacion binaria
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "Regla")
	private List<CondicionDosSensores> condicionesDS = new ArrayList<>();
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "Regla")
	private List<Actuador> actuadores = new ArrayList<>(); //acciones
	
	private String criterioCondiciones; // AND , OR
	
	//@OneToOne
	@Transient
	private DispositivoInteligente disp;
	@Transient
	private int contador;
	
	private enum criterios{AND,OR}
	//private boolean state; //para test NO AGREGAR AL DIAGRAMA DE CLASES
	
	public Regla(String unNombreRegla,DispositivoInteligente unDispo,String critCond){
		this.nombreRegla = unNombreRegla;
		this.disp = unDispo;
		this.criterioCondiciones = critCond;
	}
	
	public Regla(){}
	
	//getters y setters
	public String getNombreRegla(){
		return nombreRegla;
	}
	public void setNombreRegla(String nombre){
		nombreRegla = nombre;
	}
	
	public void setCondicionesSYV(List<CondicionSensorYValor> comparaciones){
		this.condicionesSYV = comparaciones;
	}
	public List<CondicionSensorYValor> getCondicionesSYV(){
		return this.condicionesSYV;
	}
	public void setCondiciones(List<CondicionDosSensores> comparaciones){
		this.condicionesDS = comparaciones;
	}
	public List<CondicionDosSensores> getCondiciones(){
		return this.condicionesDS;
	}
	public void agregarCondicionSYV(CondicionSensorYValor unaCondicion){
		condicionesSYV.add(unaCondicion);
	}
	public void quitarCondicionSYV(CondicionSensorYValor unaCondicion){
		condicionesSYV.remove(unaCondicion);
	}
	public void agregarCondicionDS(CondicionDosSensores unaCondicion){
		condicionesDS.add(unaCondicion);
	}
	public void quitarCondicionDS(CondicionDosSensores unaCondicion){
		condicionesDS.remove(unaCondicion);
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
	public CondicionSensorYValor getCondicionConIndiceSYV(int indice){
		return condicionesSYV.get(indice);
	}
	public CondicionDosSensores getCondicionConIndiceDS(int indice){
		return condicionesDS.get(indice);
	}
	public CondicionSensorYValor getCondicionSYV(CondicionSensorYValor con){
		return getCondicionConIndiceSYV(condicionesSYV.indexOf(con));
	}
	public CondicionDosSensores getCondicionDS(Condicion con){
		return getCondicionConIndiceDS(condicionesDS.indexOf(con));
	}
	public void setDisp(DispositivoInteligente dispositivo){
		this.disp = dispositivo;
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
		if(condicionesSYV!=null){
			for(CondicionSensorYValor con:this.condicionesSYV){
				con.update();
				if(con.getEstado()){
					contador++;
				}
			}
		}
		
		if(condicionesDS!=null){
			for(CondicionDosSensores con:this.condicionesDS){
				con.update();
				if(con.getEstado()){
					contador++;
				}
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
		if(contador == condicionesSYV.size()+condicionesDS.size()){
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

	public void crearCondicionDosSensores(Sensor sen1, Sensor sen2, String comparacion){ 
		CondicionDosSensores comp = new CondicionDosSensores(sen1,sen2,comparacion); //sen1 comparacion sen2
		condicionesDS.add(comp);
	}
	public void crearCondicionSensoresYValor(Sensor sen1, double valorFijo, String comparacion){
		CondicionSensorYValor comp = new CondicionSensorYValor(sen1,valorFijo,comparacion); //sen1 comparacion valor
		condicionesSYV.add(comp);
	}
	
}

