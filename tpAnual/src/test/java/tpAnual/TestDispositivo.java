package tpAnual;

import java.time.LocalDateTime;
import org.junit.*;

import modelo.devices.DispositivoInteligente;
import modelo.devices.Sensor;

public class TestDispositivo {
	
	DispositivoInteligente tele = new DispositivoInteligente("Televisor",0.14,0.1);
	LocalDateTime fechaFinalParaTest = LocalDateTime.of(2018,4,18,7,50,0);
	Sensor sensorVolumen = new Sensor("volumen",tele);

	@Before
	public void init() {
		tele.setFechaRegistro(2018,2,5,22,15,0);
		tele.agregarSensor(sensorVolumen);
		tele.setMagnitudSensor("volumen", 20);
	}
	
	@Test
	public void calculoDeHorasTele() {
		Assert.assertEquals(1713.58,tele.calculoDeHoras(fechaFinalParaTest),0.1);
		System.out.println("Test calculoDeHorasTele:\n  En el intervalo seleccionado pasaron aprox. 1713.58hs: " 
		+ tele.calculoDeHoras(fechaFinalParaTest));
	}
	
	@Test
	public void consumoActualTele() {
		Assert.assertEquals(239.9012,tele.consumoTotal(fechaFinalParaTest),0.1);
		System.out.println("Test consumoActualTele:\n  En el intervalo seleccionado el consumo de la tele fue "
				+ "de aprox. 239.9012 kWh: " + tele.consumoTotal(fechaFinalParaTest));
	}
	
	@Test
	public void subir5VolumenTele() {
		tele.aumentarIntensidadSensor("volumen", 5);
		Assert.assertEquals(25, tele.getMagnitudSensor("volumen"),0.1);
		System.out.println("Test subir5VolumenTele:\n Luego de subirle 5 unidades de volumen a la tele, quedo: " 
		+ tele.getMagnitudSensor("volumen"));
	}
	
	@Test
	public void bajar5VolumenTele() {
		tele.disminuirIntensidadSensor("volumen", 5);
		Assert.assertEquals(15, tele.getMagnitudSensor("volumen"),0.1);
		System.out.println("Test bajar5VolumenTele:\n Luego de bajarle 5 unidades de volumen a la tele, quedo: " 
		+ tele.getMagnitudSensor("volumen"));
	}

}