package tpAnual;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.optim.PointValuePair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import exceptions.ExceptionsHandler;
import modelo.MetodoSimplex;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoInteligente;
import modelo.users.Cliente;

public class TestMetodoSimplex {
	MetodoSimplex simplex = new MetodoSimplex();
	DispositivoInteligente disp1 = new DispositivoInteligente("disp1",  0.18);
	DispositivoInteligente disp2 = new DispositivoInteligente("disp2",  0.875);
	DispositivoInteligente disp3 = new DispositivoInteligente("disp3",  0.06);
	List<DispositivoInteligente> disp = new ArrayList<DispositivoInteligente>();
	
	@Before
	public void init() {
		disp1.setHorasUsoMax(370);
		disp1.setHorasUsoMin(90);
		disp2.setHorasUsoMax(30);
		disp2.setHorasUsoMin(6);
		disp3.setHorasUsoMax(360);
		disp3.setHorasUsoMin(120);
		disp.add(disp1);
		disp.add(disp2);
		disp.add(disp3);
	}
	
	@Test
    public void testMetodoSimplex(){
		PointValuePair solucion = simplex.aplicarMetodoSimplex(disp);
		Assert.assertEquals(760, solucion.getValue(), 0.01);
		System.out.println("Test testMetodoSimplex:\n "
				+ "la suma de los x dio 760: " + solucion.getValue());
		Assert.assertEquals(360, solucion.getPoint()[0], 0.01); // <--- X2
		System.out.println("Test testMetodoSimplex:\n "
				+ "La cantidad de horas para el dispositivo x2 dio 360: " + solucion.getPoint()[0]);
		Assert.assertEquals(30, solucion.getPoint()[1], 0.01); // <--- X1
		System.out.println("Test testMetodoSimplex:\n "
				+ "La cantidad de horas para el dispositivo x1 dio 30: " + solucion.getPoint()[1]);
		Assert.assertEquals(370, solucion.getPoint()[2], 0.01); // <--- X0 
		System.out.println("Test testMetodoSimplex:\n "
				+ "La cantidad de horas para el dispositivo x0 dio 370: " + solucion.getPoint()[2]);

	}
	
}
