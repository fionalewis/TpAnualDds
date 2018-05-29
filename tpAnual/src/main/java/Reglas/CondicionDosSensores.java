package Reglas;

import tpAnual.devices.Sensor;

public class CondicionDosSensores extends Condicion{

	private Sensor sensor1;
	private Sensor sensor2;
	
	public CondicionDosSensores(Regla reg, Sensor s1, Sensor s2, String comp){
		this.regla = reg;
		this.sensor1 = s1;
		this.sensor2 = s2;
		this.comparacion = comp;
		s1.subscribir(this);
		s2.subscribir(this);
	}
	
	@Override
	public void update(){
		this.estado = evaluar(sensor1.getMagnitud(), sensor2.getMagnitud());
		this.regla.evaluarCondiciones();		
	}
	
}
