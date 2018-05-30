package modelo.Reglas;

import modelo.devices.Sensor;

public class CondicionSensorYValor extends Condicion{

	private Sensor sensor;
	private Double valorFijo;
	
	public CondicionSensorYValor(Regla reg, Sensor sen,  double valor, String comp){
		//this.regla = reg;
		this.sensor = sen;
		this.valorFijo = valor;
		this.comparacion = comp;
		this.sensor.subscribir(this);
	}
	
	//llamado por sensor
	@Override
	public void update(){
		this.estado = evaluar(sensor.getMagnitud(), valorFijo); //evalua la condicion y le avisa regla
		System.out.println("Se evalu�� si: ");
		System.out.println("\nEl sensor, " + sensor.getNombreMagnitud() + " = " + sensor.getMagnitud());
		System.out.println(" es: " + comparacion + " al ");
		System.out.println(" valor fijo = " + valorFijo);
		System.out.println("\nCon resultado: " + estado);
	}
	
	public String getExpresion(){
		return sensor.getNombreMagnitud().concat(comparacion).concat(valorFijo.toString());
	}
}