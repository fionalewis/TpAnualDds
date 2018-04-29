package tpAnual;

import java.time.LocalDateTime;
import org.junit.*;

public class TestDispositivo {
	
	Dispositivo tele = new Dispositivo("Televisor",0.14);
	LocalDateTime fechaFinalParaTest = LocalDateTime.of(2018,4,18,7,50,0);

	@Before
	public void init() {
		tele.setFechaRegistro(2018,2,5,22,15,0);
	}
	
	@Test
	public void calculoDeHorasTele() {
		Assert.assertEquals(1713.58,tele.calculoDeHoras(fechaFinalParaTest),0.1);
		System.out.println("Test calculoDeHorasTele:\n  En el intervalo seleccionado pasaron aprox. 1713.58hs: " + tele.calculoDeHoras(fechaFinalParaTest));
	}
	
	@Test
	public void consumoActualTele() {
		Assert.assertEquals(239.9012,tele.consumoActual(fechaFinalParaTest),0.1);
		System.out.println("Test consumoActualTele:\n  En el intervalo seleccionado el consumo de la tele fue de aprox. 239.9012 kWh: " + tele.consumoActual(fechaFinalParaTest));
	}

}