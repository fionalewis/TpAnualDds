package tpAnual;

import org.junit.Assert;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

public class TestDispositivo {
	
	Dispositivo tele = new Dispositivo("Televisor",0.14);
	LocalDateTime fechaFinalParaTest = LocalDateTime.of(2018,4,18,7,50,0);

	@Before
	public void init() {
		tele.setFechaRegistro(2018,2,5,22,15,0);
	}
	
	@Test
	public void calculoDeHorasTele() {
		LocalDateTime fechaInicioTest = tele.getFechaRegistro();
		Assert.assertEquals(1713.58,tele.calculoDeHoras(fechaInicioTest,fechaFinalParaTest),5);
	}
	
	@Test
	public void consumoActualTele() {
		Assert.assertEquals(239.9012,tele.consumoActual(),5);
	}

}
