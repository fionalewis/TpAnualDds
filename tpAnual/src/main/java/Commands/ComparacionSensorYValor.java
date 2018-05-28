package Commands;

import tpAnual.devices.Sensor;

public class ComparacionSensorYValor extends Comparacion{

	private Sensor sensor;
	private Double valorFijo;
	
	public ComparacionSensorYValor(Regla reg, Sensor sen,  double valor, String comp){
		this.regla = reg;
		this.sensor = sen;
		this.valorFijo = valor;
		this.comparacion = comp;
	}
	
	@Override
	public void update(){
		this.estado = evaluar(sensor.getMagnitud(), valorFijo);
	}
	
}
