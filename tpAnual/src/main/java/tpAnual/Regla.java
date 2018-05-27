package tpAnual;

import java.util.ArrayList;
import java.util.List;

import Commands.Actuador;
import Commands.Condicion;
import tpAnual.devices.DispositivoInteligente;
import tpAnual.devices.Sensor;

public abstract class Regla {
	
	protected List<Sensor> sensores = new ArrayList<>();
	protected List<Actuador> actuador = new ArrayList<>();
	protected DispositivoInteligente disp;
	protected List<Condicion> condiciones = new ArrayList<>();
	
	public void setCondiciones(List<Condicion> condiciones){
		this.condiciones = condiciones;
	}
	
	public void setDispositivo(DispositivoInteligente disp){
		this.disp = disp;
	}
}

//AND u OR