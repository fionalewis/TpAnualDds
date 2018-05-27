package Commands;

import Exceptions.CaracterInvalidoException;
import tpAnual.Regla;
import tpAnual.devices.DispositivoInteligente;

public class ReglaAND extends Regla{

	private int contador;
	
	public ReglaAND(DispositivoInteligente unDispo, Actuador unaAccion) {
		super();
		// TODO Auto-generated constructor stub
	}

	// Deben cumplir todas las condiciones para que dispare las acciones
	public void evaluarCondiciones() throws CaracterInvalidoException{
		
		for(Condicion cond:this.condiciones){
			if(cond.evaluar()){
				contador++;
			}
		if(contador == condiciones.size()){
			//disparar actuador
		} else{
			System.out.println("no se cumplieron todas las condiciones");
			}	
		}
	}
}
