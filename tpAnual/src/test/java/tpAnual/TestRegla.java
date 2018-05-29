package tpAnual;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Exceptions.CaracterInvalidoException;
import Reglas.Condicion;
import Reglas.Regla;
import tpAnual.devices.DispositivoInteligente;
import tpAnual.devices.Sensor;

public class TestRegla {
	DispositivoInteligente aircon = new DispositivoInteligente("Aire Acondicionado",0.14,0.1,"Phillip");
	Sensor sensorTemperatura = new Sensor("temperatura",aircon);
	Regla reglaCopada = new Regla(aircon,"AND");
	Condicion apagarCuandotemp30;
	
	@Before
	public void init() {
		aircon.agregarSensor(sensorTemperatura);
		aircon.setMagnitudSensor("temperatura", 24);
		reglaCopada.crearCondicionSensoresYValor(sensorTemperatura, 30, "IGUAL");
		apagarCuandotemp30 = reglaCopada.getUnaCondicion(0);
		
	}
	
	@Test
	public void setearCondicionMayor() throws CaracterInvalidoException {
		apagarCuandotemp30.setComparacion("MAYOR");
		Assert.assertEquals("MAYOR",apagarCuandotemp30.getComparacion());
		System.out.println("La condicion apagarCuandotemp30 paso a ser: apagar cuando sea mayor a 30 grados."); 
	}
	
	@Test
	public void setearCondicionConCriterioInvalido() throws CaracterInvalidoException {
		try{
			apagarCuandotemp30.setComparacion("AAA");
		} catch(Exception CaracterInvalidoException){
			System.out.println("Test setearCondicionConCriterioInvalido \n Se pudo tirar una "
			+ "excepsion cuando se intento settear una condicion de comparacion con un caracter invalida."); 
		}
	}
	
}
