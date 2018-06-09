package tpAnual;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Exceptions.CaracterInvalidoException;
import modelo.Actuador.Actuador;
import modelo.Reglas.Condicion;
import modelo.Reglas.Regla;
import modelo.devices.DispositivoInteligente;
import modelo.devices.Sensor;

public class TestRegla {
	DispositivoInteligente aircon = new DispositivoInteligente("Aire Acondicionado",0.14);
	Sensor sensorTemperatura = new Sensor("temperatura",aircon);
	Sensor sensorHumedad = new Sensor("humedad",aircon);
	Sensor sensorMovimiento = new Sensor("movimiento",aircon);
	
	Condicion tempIgual30;
	Condicion humedadMenor50;
	Condicion hayGenteCasa;
	
	Actuador prenderAire = new Actuador(1,"Prender el aire");
	Actuador prenderLuz = new Actuador(2,"Prender la luz");
	
	Regla reglaCopada = new Regla("reglaCopada",aircon,"AND");
	
	@Before
	public void init() {
		aircon.agregarSensor(sensorTemperatura);
		aircon.agregarSensor(sensorHumedad);
		aircon.agregarSensor(sensorMovimiento);
		aircon.setMagnitudSensor("temperatura",30);
		aircon.setMagnitudSensor("humedad",45);
		aircon.setMagnitudSensor("movimiento",1); 
		reglaCopada.crearCondicionSensoresYValor(sensorTemperatura, 30, "IGUAL");
		reglaCopada.crearCondicionSensoresYValor(sensorHumedad,50,"MENOR");
		reglaCopada.crearCondicionSensoresYValor(sensorMovimiento,1,"IGUAL");
		reglaCopada.agregarActuador(prenderAire);
		//reglaCopada.agregarActuador(prenderLuz);
		tempIgual30 = reglaCopada.getCondicionConIndice(0);
		humedadMenor50 = reglaCopada.getCondicionConIndice(1);
		hayGenteCasa = reglaCopada.getCondicionConIndice(2);
		
	}
	
	@Test
	public void setearCondicionMayor() throws CaracterInvalidoException {
		tempIgual30.setComparacion("MAYOR");
		Assert.assertEquals("MAYOR",tempIgual30.getComparacion());
		System.out.println("La condicion apagarCuandotemp30 paso a ser: apagar cuando sea mayor a 30 grados."); 
	}
	
	@Test
	public void setearCondicionConCriterioInvalido() throws CaracterInvalidoException {
		try{
			tempIgual30.setComparacion("AAA");
		} catch(Exception CaracterInvalidoException){
			System.out.println("Test setearCondicionConCriterioInvalido \n Se pudo tirar una "
			+ "excepsion cuando se intento settear una condicion de comparacion con un caracter invalida."); 
		}
	}
	
	@Test
	public void cumpleCondicionPrenderCuandoTempIgualA30(){
		tempIgual30.update();
		Assert.assertTrue(tempIgual30.getEstado());
		System.out.println("El test cumpleCondicionPrenderCuandoTempIgualA30: \n"
				+ "el estado de la condicion es: " + tempIgual30.getEstado()); 
	}
	
	@Test
	public void cumpleCondicionPrenderCuandoHumedadMenor50(){
		humedadMenor50.update();
		Assert.assertTrue(humedadMenor50.getEstado());
		System.out.println("El test cumpleCondicionPrenderCuandoHumedadMenor50: \n"
				+ "el estado de la condicion es: " + humedadMenor50.getEstado()); 
	}
	
	@Test
	public void cumpleCondicionPrenderCuandoHaygenteCasa(){
		hayGenteCasa.update();
		Assert.assertTrue(hayGenteCasa.getEstado());
		System.out.println("El test cumpleCondicionPrenderCuandoHaygenteCasa: \n"
				+ "el estado de la condicion es: " + hayGenteCasa.getEstado()); 
	}
	
	@Test
	public void cumplieronLasTresCondiciones(){
		sensorTemperatura.notificar();
		sensorHumedad.notificar();
		sensorMovimiento.notificar();
		reglaCopada.aplicarRegla();
		Assert.assertTrue(reglaCopada.getState());
		System.out.println("El test cumplieronLasTresCondiciones: \n"
				+ "La regla devolvio: " + reglaCopada.getState()); 
	}
	
}
