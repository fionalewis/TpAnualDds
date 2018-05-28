package Commands;

import Exceptions.CaracterInvalidoException;
import tpAnual.devices.Sensor;

public class ComparacionSensores extends Comparacion{

	private Sensor sensor1;
	private Sensor sensor2;
	
	public ComparacionSensores(Regla reg, Sensor s1, Sensor s2, String comp){
		this.regla = reg;
		this.sensor1 = s1;
		this.sensor2 = s2;
		this.comparacion = comp;
	}
	
	@Override
	public void update(){
		this.estado = evaluar(sensor1.getMagnitud(), sensor2.getMagnitud());
		this.regla.evaluarCondiciones();		
	}
	
}
