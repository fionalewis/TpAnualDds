package tpAnual;

import java.util.ArrayList;
import java.util.List;

import Reglas.Regla;
import tpAnual.devices.Sensor;

public abstract class Actuador {
	
	private Sensor sensor;
	private List<Regla> reglas = new ArrayList<>();
	
	public void recibirMagnitud(Sensor sensor){
		this.sensor = sensor;
	}
	
}

//El actuador mandara un identificador de fabrica