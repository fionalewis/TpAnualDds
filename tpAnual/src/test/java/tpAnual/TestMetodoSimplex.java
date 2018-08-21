package tpAnual;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.optim.PointValuePair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import modelo.MetodoSimplex;
import modelo.devices.DeviceFactory;
import modelo.devices.DispositivoInteligente;
import modelo.users.Cliente;

public class TestMetodoSimplex {
	Cliente cliente = new Cliente();
	MetodoSimplex simplex = new MetodoSimplex();

	/*DispositivoInteligente dispSCD1 = new DispositivoInteligente();
	DispositivoInteligente dispSCD2 = new DispositivoInteligente();
	DispositivoInteligente dispSCD3 = new DispositivoInteligente();
	List<DispositivoInteligente> dispSCD = new ArrayList<DispositivoInteligente>();
	
	DispositivoInteligente dispSCI1 = new DispositivoInteligente();
	DispositivoInteligente dispSCI2 = new DispositivoInteligente();
	DispositivoInteligente dispSCI3 = new DispositivoInteligente();
	List<DispositivoInteligente> dispSCI = new ArrayList<DispositivoInteligente>();*/
	
	List<DispositivoInteligente> dispPepe = new ArrayList<DispositivoInteligente>();
	List<DispositivoInteligente> dispSCDSalo = new ArrayList<DispositivoInteligente>();
	List<DispositivoInteligente> dispSCISalo = new ArrayList<DispositivoInteligente>();
	List<DispositivoInteligente> dispDaniel = new ArrayList<DispositivoInteligente>();
	
	//DispositivoInteligente dispSI1 = new DispositivoInteligente("dispSI1",  2);
	//DispositivoInteligente dispSI2 = new DispositivoInteligente("dispSI2",  5);
	//List<DispositivoInteligente> dispSI = new ArrayList<DispositivoInteligente>();
	
	
	@Before
	public void init() {

		//Init Salo
		
		DeviceFactory f = new DeviceFactory();
		
		//Factory Daniel (Ejemplo del TP)
		
		DispositivoInteligente dani1 = (DispositivoInteligente) f.crearDisp("Televisor","LED 40' ");
		DispositivoInteligente dani2 = (DispositivoInteligente) f.crearDisp("Lampara","De 11 W");
		DispositivoInteligente dani3 = (DispositivoInteligente) f.crearDisp("PC","De escritorio");
		DispositivoInteligente dani4 = (DispositivoInteligente) f.crearDisp("Aire Acondicionado","2200 frigorias");
		DispositivoInteligente dani5 = (DispositivoInteligente) f.crearDisp("Ventilador","De techo");
		
		//Estos son los unicos que todavia no me fije como ignorar en las listas
		//Dispositivo dani9 = f.crearDisp("Termotanque","Eléctrico");
		//Dispositivo dani10 = f.crearDisp("Horno","Eléctrico");
		
		dispDaniel.add(dani1);
		dispDaniel.add(dani2);
		dispDaniel.add(dani3);
		dispDaniel.add(dani4);
		dispDaniel.add(dani5);	
		
		//Factory Ejemplo
		
		DispositivoInteligente d11 = (DispositivoInteligente) f.crearDisp("Televisor","LED 40' ");
		DispositivoInteligente d12 = (DispositivoInteligente) f.crearDisp("Aire Acondicionado","3500 frigorias");
		DispositivoInteligente d13 = (DispositivoInteligente) f.crearDisp("Aire Acondicionado","2200 frigorias");
		
		dispSCDSalo.add(d11);
		dispSCDSalo.add(d12);
		dispSCDSalo.add(d13);
		
		//Ejemplo Pepe
		
		DispositivoInteligente aire = (DispositivoInteligente) f.crearDisp("Aire Acondicionado","3500 frigorias");
		DispositivoInteligente aire1 =  (DispositivoInteligente) f.crearDisp("Aire Acondicionado","2200 frigorias");
		dispPepe.add(aire);dispPepe.add(aire1);
		
	}
	

   
	@Test
    public void testMetodoSimplexDaniel() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		PointValuePair solucionSCD = simplex.aplicarMetodoSimplex(dispDaniel);
		Assert.assertEquals(1800, solucionSCD.getValue(), 0.01);
		System.out.println("Test testMetodoSimplexDaniel:\n " + "la suma de los x dio: " + solucionSCD.getValue());
		Assert.assertEquals(360, solucionSCD.getPoint()[0], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x5 dio: " + solucionSCD.getPoint()[0]);
		Assert.assertEquals(360, solucionSCD.getPoint()[1], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x4 dio: " + solucionSCD.getPoint()[1]);
		Assert.assertEquals(360, solucionSCD.getPoint()[2], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x3 dio: " + solucionSCD.getPoint()[2]);
		Assert.assertEquals(360, solucionSCD.getPoint()[3], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x2 dio: " + solucionSCD.getPoint()[3]);
		Assert.assertEquals(360, solucionSCD.getPoint()[4], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x1 dio: " + solucionSCD.getPoint()[4]);
	}
	
	@Test
	public void testMetodoSimplexEjemploPepe() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		PointValuePair solucionSCD = simplex.aplicarMetodoSimplex(dispPepe);
		Assert.assertEquals(720, solucionSCD.getValue(), 0.01);
		System.out.println("Test testMetodoSimplexEjemploPepe:\n " + "La suma de los x dio: " + solucionSCD.getValue());
		Assert.assertEquals(360, solucionSCD.getPoint()[0], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x5 dio: " + solucionSCD.getPoint()[0]);
		Assert.assertEquals(360, solucionSCD.getPoint()[1], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x4 dio: " + solucionSCD.getPoint()[1]);
	}
	
	
	@Test
    public void testMetodoSimplexSCDSalo() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		PointValuePair solucionSCD = simplex.aplicarMetodoSimplex(dispSCDSalo);
		Assert.assertEquals(1080, solucionSCD.getValue(), 0.01);
		System.out.println("Test testMetodoSimplexSCD:\n " + "la suma de los x dio: " + solucionSCD.getValue());
		Assert.assertEquals(360, solucionSCD.getPoint()[0], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x5 dio: " + solucionSCD.getPoint()[0]);
		Assert.assertEquals(360, solucionSCD.getPoint()[1], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x4 dio: " + solucionSCD.getPoint()[1]);
		Assert.assertEquals(360, solucionSCD.getPoint()[2], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x3 dio: " + solucionSCD.getPoint()[2]);
	}

}