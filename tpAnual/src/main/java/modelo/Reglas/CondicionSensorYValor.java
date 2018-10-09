package modelo.Reglas;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import modelo.devices.Sensor;
@Entity
public class CondicionSensorYValor extends Condicion{
	
	@Transient
	private Sensor sensor;
	private Double valorFijo;
	
	public CondicionSensorYValor(Sensor sen,  double valor, String comp){
		this.sensor = sen;
		this.valorFijo = valor;
		this.comparacion = comp;
		this.sensor.subscribir(this);
	}
	
	@Override
	public void update(){
		this.estado = evaluar(sensor.getMagnitud(), valorFijo); //evalua la condicion y le avisa regla
		System.out.println("Se evalu¨® si: ");
		System.out.println("\nEl sensor, " + sensor.getNombreMagnitud() + " = " + sensor.getMagnitud());
		System.out.println(" es: " + comparacion + " al ");
		System.out.println(" valor fijo = " + valorFijo);
		System.out.println("\nCon resultado: " + estado);
	}
	
	public String getExpresion(){
		return sensor.getNombreMagnitud().concat(comparacion).concat(valorFijo.toString());
	}
	
	//CUANDO ALGUIEN DESDE AFUERA DESEA AVALUAR LAS CONDICIONES, 
	//Y NO SER DISPARADO AUTOMATICAMENTE POR SENSORES
	//ESTO ESTA PARA CONSULTARLE AL SENSOR EL VALOR ACTUAL DE LA MAGNITUD QUE NECESITO
	@Override
	public boolean getEstado(){
		sensor.medir();
		return this.estado;
	}
	
	public void setValorFijo(double unValor){
		valorFijo = unValor;
	}
}
