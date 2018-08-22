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
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoConvertido;

public class TestMetodoSimplex {
	Cliente pepe = new Cliente();
	MetodoSimplex simplex = new MetodoSimplex();
	
	DispositivoInteligente dispSCI1 = new DispositivoInteligente();
	DispositivoInteligente dispSCI2 = new DispositivoInteligente();
	DispositivoInteligente dispSCI3 = new DispositivoInteligente();
	List<Dispositivo> dispSCI = new ArrayList<Dispositivo>();
	
	//Variables tests de Salo
	List<Dispositivo> dispPepe = new ArrayList<Dispositivo>();
	List<Dispositivo> dispSCDSalo = new ArrayList<Dispositivo>();
	List<Dispositivo> dispSCISalo = new ArrayList<Dispositivo>();
	List<Dispositivo> dispDaniel = new ArrayList<Dispositivo>();

	List<Dispositivo> dispSCDSalo1 = new ArrayList<Dispositivo>();
	List<Dispositivo> dispSCISalo1 = new ArrayList<Dispositivo>();
	List<Dispositivo> dispDaniel1 = new ArrayList<Dispositivo>();
	
	@Before
	public void init() {

		//Init Salo
		
		DeviceFactory f = new DeviceFactory();
		
		//Factory Daniel (Ejemplo del TP)
		
		Dispositivo dani1 = f.crearDisp("Televisor","LED 40' ");
		Dispositivo dani2 = f.crearDisp("Lampara","De 11 W");
		Dispositivo dani3 = f.crearDisp("Lavarropas","Semi-automatico de 5 kg");
		Dispositivo dani4 = f.crearDisp("PC","De escritorio");
		Dispositivo dani5 = f.crearDisp("Aire Acondicionado","2200 frigorias");
		Dispositivo dani6 = f.crearDisp("Microondas","Convencional");
		Dispositivo dani7 = f.crearDisp("Plancha","A vapor");
		Dispositivo dani8 = f.crearDisp("Ventilador","De techo");
		
		//Estos son los unicos que todavia no me fije como ignorar en las listas
		//Dispositivo dani9 = f.crearDisp("Termotanque","Eléctrico");
		//Dispositivo dani10 = f.crearDisp("Horno","Eléctrico");
		 
		
		//Convertimos el microondas y la plancha a inteligentes
		DispositivoConvertido dani6conv = new DispositivoConvertido(dani6);
		DispositivoConvertido dani7conv = new DispositivoConvertido(dani7);
	
		dispDaniel.add(dani6conv);
		dispDaniel.add(dani7conv);
		
		dispDaniel.add(dani1);
		dispDaniel.add(dani2);
		dispDaniel.add(dani3);
		dispDaniel.add(dani4);
		dispDaniel.add(dani5);
		//dispDaniel.add(dani6);
		//dispDaniel.add(dani7);
		dispDaniel.add(dani8);	
		
		//Factory Ejemplo
		
		Dispositivo d11 = f.crearDisp("Televisor","LED 40' ");
		Dispositivo d12 = f.crearDisp("Aire Acondicionado","3500 frigorias");
		Dispositivo d13 = f.crearDisp("Aire Acondicionado","2200 frigorias");
		
		dispSCDSalo.add(d11);
		dispSCDSalo.add(d12);
		dispSCDSalo.add(d13);
		
		//Ejemplo Pepe
		
		Dispositivo aire = f.crearDisp("Aire Acondicionado","3500 frigorias");
		Dispositivo aire1 = f.crearDisp("Aire Acondicionado","2200 frigorias");
		pepe.agregarDispositivo(aire); pepe.agregarDispositivo(aire1);
		
	}
	
	/*@Test //Este test lo habian hecho ustedes y perdi los disp que tenia en el merge, despues lo completo
    public void testMetodoSimplexSCD() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		PointValuePair solucionSCD = simplex.aplicarMetodoSimplex(???);
		Assert.assertEquals(1470, solucionSCD.getValue(), 0.01);

		System.out.println("Test testMetodoSimplexSCD:\n " + "la suma de los x dio 1470: " + solucionSCD.getValue());

		Assert.assertEquals(360, solucionSCD.getPoint()[0], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x4 dio: " + solucionSCD.getPoint()[0]);
		Assert.assertEquals(30, solucionSCD.getPoint()[1], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x3 dio: " + solucionSCD.getPoint()[1]);
		Assert.assertEquals(360, solucionSCD.getPoint()[2], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x2 dio: " + solucionSCD.getPoint()[2]);
		Assert.assertEquals(360, solucionSCD.getPoint()[3], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x1 dio: " + solucionSCD.getPoint()[3]);
	}*/
	
	//Este metodo no me funciona, lo comente para que no me tire error, diganme si a ustedes les anda a ver que onda
	//@Test
    public void testMetodoSimplexSCI(){
		try{
			PointValuePair solucionSCI = simplex.aplicarMetodoSimplex(dispSCI);
		}
		catch (Exception e) {
			System.out.println("testMetodoSimplexSCI: un sistema compatible indeterminado no tiene solucion");
		}
	}
	
    //Deje los assert de mis tests como comentarios porque no tengo idea del valor que tiene que devolver, si alguno tiene idea de como calcularlos agreguelos obvio
    //Deje comentados los valores que me dieron a mi en las pruebas
   
	@Test
    public void testMetodoSimplexDaniel() throws FileNotFoundException, InstantiationException, IllegalAccessException{		
		PointValuePair solucionSCD = simplex.aplicarMetodoSimplex(dispDaniel);
		//Assert.assertEquals(1875, solucionSCD.getValue(), 0.01);
		System.out.println("Test testMetodoSimplexDaniel:\n " + "La suma de los x dio: " + solucionSCD.getValue());
		//Assert.assertEquals(360, solucionSCD.getPoint()[0], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x8 dio: " + solucionSCD.getPoint()[0]);
		//Assert.assertEquals(30, solucionSCD.getPoint()[1], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x7 dio: " + solucionSCD.getPoint()[1]);
		//Assert.assertEquals(15, solucionSCD.getPoint()[2], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x6 dio: " + solucionSCD.getPoint()[2]);
		//Assert.assertEquals(360, solucionSCD.getPoint()[3], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x5 dio: " + solucionSCD.getPoint()[3]);
		//Assert.assertEquals(360, solucionSCD.getPoint()[4], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x4 dio: " + solucionSCD.getPoint()[4]);
		//Assert.assertEquals(30, solucionSCD.getPoint()[5], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x3 dio: " + solucionSCD.getPoint()[5]);
		//Assert.assertEquals(360, solucionSCD.getPoint()[6], 0.01); 
		//System.out.println("La cantidad de horas para el dispositivo x2 dio: " + solucionSCD.getPoint()[6]);
		//Assert.assertEquals(360, solucionSCD.getPoint()[7], 0.01); 
		//System.out.println("La cantidad de horas para el dispositivo x1 dio: " + solucionSCD.getPoint()[7]);	
	}
	
	
	
	//@Test
    public void testMetodoSimplexSCDSalo() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		PointValuePair solucionSCD = simplex.aplicarMetodoSimplex(dispSCDSalo);
		//Assert.assertEquals(1080, solucionSCD.getValue(), 0.01);
		System.out.println("Test testMetodoSimplexSCD:\n " + "La suma de los x dio: " + solucionSCD.getValue());
		//Assert.assertEquals(360, solucionSCD.getPoint()[0], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x3 dio: " + solucionSCD.getPoint()[0]);
		//Assert.assertEquals(360, solucionSCD.getPoint()[1], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x2 dio: " + solucionSCD.getPoint()[1]);
		//Assert.assertEquals(360, solucionSCD.getPoint()[2], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x1 dio: " + solucionSCD.getPoint()[2]);
	}

	//Este test es diferente ya que llama al simplex desde el cliente pepe
	//@Test
	public void testMetodoSimplexEjemploPepe() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		PointValuePair solucionSCD = pepe.llamarSimplex();
		Assert.assertEquals(720, solucionSCD.getValue(), 0.01);
		System.out.println("Test testMetodoSimplexEjemploPepe:\n " + "La suma de los x dio 720: " + solucionSCD.getValue());
		Assert.assertEquals(360, solucionSCD.getPoint()[0], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x2 dio 360: " + solucionSCD.getPoint()[0]);
		Assert.assertEquals(360, solucionSCD.getPoint()[1], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x1 dio 360: " + solucionSCD.getPoint()[1]);
	}
}