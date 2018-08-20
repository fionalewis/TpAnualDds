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
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.users.Cliente;

public class TestMetodoSimplex {
	Cliente cliente = new Cliente();
	MetodoSimplex simplex = new MetodoSimplex();
	DispositivoInteligente dispSCD1 = new DispositivoInteligente("dispSCD1",  0.18);
	DispositivoInteligente dispSCD2 = new DispositivoInteligente("dispSCD2",  0.875);
	DispositivoInteligente dispSCD3 = new DispositivoInteligente("dispSCD3",  0.06);
	DispositivoEstandar dispEstandar = new DispositivoEstandar("dispEstandar",  0.6, 2017, 8, 21, 8, 12, 24, 50);
	List<DispositivoInteligente> dispSCD = new ArrayList<DispositivoInteligente>();
	
	DispositivoInteligente dispSCI1 = new DispositivoInteligente("dispSCI1",  0.18);
	DispositivoInteligente dispSCI2 = new DispositivoInteligente("dispSCI2",  0.875);
	DispositivoInteligente dispSCI3 = new DispositivoInteligente("dispSCI3",  180);
	List<DispositivoInteligente> dispSCI = new ArrayList<DispositivoInteligente>();
	
	DispositivoInteligente dispSI1 = new DispositivoInteligente("dispSI1",  2);
	DispositivoInteligente dispSI2 = new DispositivoInteligente("dispSI2",  5);
	List<DispositivoInteligente> dispSI = new ArrayList<DispositivoInteligente>();
	
	
	@Before
	public void init() {
		dispSCD1.setHorasUsoMax(370);
		dispSCD1.setHorasUsoMin(90);
		dispSCD2.setHorasUsoMax(30);
		dispSCD2.setHorasUsoMin(6);
		dispSCD3.setHorasUsoMax(360);
		dispSCD3.setHorasUsoMin(120);
		dispEstandar.setHorasUsoMin(100);
		dispEstandar.setHorasUsoMax(200);
		
		dispSCD.add(dispSCD1);
		dispSCD.add(dispSCD2);
		dispSCD.add(dispSCD3);
	
		cliente.setSimplex(simplex);
		cliente.agregarDispositivo(dispSCD1);
		cliente.agregarDispositivo(dispSCD2);
		cliente.agregarDispositivo(dispSCD3);
		cliente.agregarDispositivo(dispEstandar);
				
		dispSI1.setHorasUsoMax(370);
		dispSI1.setHorasUsoMin(90);
		dispSI2.setHorasUsoMax(30);
		dispSI2.setHorasUsoMin(6);
		dispSI.add(dispSI1);
		dispSI.add(dispSI2);
	}
	
	@Test
    public void testMetodoSimplexSCD(){
		PointValuePair solucionSCD = cliente.llamarSimplex();
		
		Assert.assertEquals(760, solucionSCD.getValue(), 0.01);
		System.out.println("Test testMetodoSimplexSCD:\n "
				+ "la suma de los x dio 760: " + solucionSCD.getValue());
		Assert.assertEquals(360, solucionSCD.getPoint()[0], 0.01); // <--- X2
		System.out.println("Test testMetodoSimplexSCD:\n "
				+ "La cantidad de horas para el dispositivo x2 dio 360: " + solucionSCD.getPoint()[0]);
		Assert.assertEquals(30, solucionSCD.getPoint()[1], 0.01); // <--- X1
		System.out.println("Test testMetodoSimplexSCD:\n "
				+ "La cantidad de horas para el dispositivo x1 dio 30: " + solucionSCD.getPoint()[1]);
		Assert.assertEquals(370, solucionSCD.getPoint()[2], 0.01); // <--- X0 
		System.out.println("Test testMetodoSimplexSCD:\n "
				+ "La cantidad de horas para el dispositivo x0 dio 370: " + solucionSCD.getPoint()[2]);

	}
	
	@Test
    public void testMetodoSimplexSCI(){
		try{
			PointValuePair solucionSCI = simplex.aplicarMetodoSimplex(dispSCI);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}