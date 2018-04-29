package tpAnual;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class TestAdministrador {
	
	private Administrador adminDePrueba;
	
	@Before
	public void init() {
		this.adminDePrueba = new Administrador("pepe","argento","pepe123","abcdef",2015,2,14);
	}
	
	@Test
	public void cantidadDeMesesComoAdmin() {
		LocalDate fechaR = adminDePrueba.getFechaAlta();
		Assert.assertEquals(adminDePrueba.cantMesesComoAdmin(fechaR),38);
		System.out.println("Test cantidadDeMesesComoAdmin: En el intervalo seleccionado han pasado 38 meses: " + adminDePrueba.cantMesesComoAdmin(fechaR));
	}

}