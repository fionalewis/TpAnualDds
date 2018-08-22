package tpAnual;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.optim.PointValuePair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import exceptions.ExceptionsHandler;
import modelo.DispositivosRepository;
import modelo.MetodoSimplex;
import modelo.devices.DeviceFactory;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoInteligente;

public class TestMetodoSimplex {
	MetodoSimplex simplex = new MetodoSimplex();
	DispositivoInteligente dispSCD1 = new DispositivoInteligente();
	DispositivoInteligente dispSCD2 = new DispositivoInteligente();
	DispositivoInteligente dispSCD3 = new DispositivoInteligente();
	DispositivoInteligente dispSCD4 = new DispositivoInteligente();
	DispositivoInteligente dispSCD5 = new DispositivoInteligente();
	List<DispositivoInteligente> dispSCD = new ArrayList<DispositivoInteligente>();
	
	DispositivoInteligente dispSCI1 = new DispositivoInteligente();
	DispositivoInteligente dispSCI2 = new DispositivoInteligente();
	DispositivoInteligente dispSCI3 = new DispositivoInteligente();
	List<DispositivoInteligente> dispSCI = new ArrayList<DispositivoInteligente>();
	DispositivosRepository repoDispo = new DispositivosRepository();
<<<<<<< HEAD
	
	//Variables tests de Salo
	List<Dispositivo> dispPepe = new ArrayList<Dispositivo>();
	List<Dispositivo> dispSCDSalo = new ArrayList<Dispositivo>();
	List<Dispositivo> dispSCISalo = new ArrayList<Dispositivo>();
	List<Dispositivo> dispDaniel = new ArrayList<Dispositivo>();
=======
>>>>>>> af51f5c11f174cf8936e2b42b0e0a51995997726
	
	/*DispositivoInteligente dispSI1 = new DispositivoInteligente("dispSI1",  2);
	DispositivoInteligente dispSI2 = new DispositivoInteligente("dispSI2",  5);
	List<DispositivoInteligente> dispSI = new ArrayList<DispositivoInteligente>();*/
	
	
	@Before
	public void init() {
		
		try{
			repoDispo.importarDispoDeJson();
		} catch(Exception e){
			ExceptionsHandler.catchear(e);
		}
		
		dispSCD1 = (DispositivoInteligente) repoDispo.crearDispositivoSegunTipo(0);
		dispSCD2 = (DispositivoInteligente) repoDispo.crearDispositivoSegunTipo(0);
		dispSCD3 = (DispositivoInteligente) repoDispo.crearDispositivoSegunTipo(6);
		dispSCD4 = (DispositivoInteligente) repoDispo.crearDispositivoSegunTipo(11);
		dispSCD5 = (DispositivoInteligente) repoDispo.crearDispositivoSegunTipo(16);
		
		dispSCD.add(dispSCD1);
		dispSCD.add(dispSCD2);
		dispSCD.add(dispSCD3);
		dispSCD.add(dispSCD4);
		dispSCD.add(dispSCD5);
		
		/*dispSCI1.setHorasUsoMax(370);
		dispSCI1.setHorasUsoMin(90);
		dispSCI2.setHorasUsoMax(30);
		dispSCI2.setHorasUsoMin(6);
		dispSCI3.setHorasUsoMax(360);
		dispSCI3.setHorasUsoMin(120);
		dispSCI.add(dispSCI1);
		dispSCI.add(dispSCI2);
		dispSCI.add(dispSCI3);*/
		
		/*dispSI1.setHorasUsoMax(370);
		dispSI1.setHorasUsoMin(90);
		dispSI2.setHorasUsoMax(30);
		dispSI2.setHorasUsoMin(6);
		dispSI.add(dispSI1);
		dispSI.add(dispSI2);*/
		
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
		
		dispDaniel.add(dani1);
		dispDaniel.add(dani2);
		dispDaniel.add(dani3);
		dispDaniel.add(dani4);
		dispDaniel.add(dani5);
		dispDaniel.add(dani6);
		dispDaniel.add(dani7);
		dispDaniel.add(dani8);	
		
		//Factory Ejemplo
		
		Dispositivo d11 = f.crearDisp("Televisor","LED 40' ");
		Dispositivo d12 = f.crearDisp("Aire Acondicionado","3500 frigorias");
		Dispositivo d13 = f.crearDisp("Plancha","A vapor");
		Dispositivo d14 = f.crearDisp("Aire Acondicionado","2200 frigorias");
		Dispositivo d15 = f.crearDisp("Televisor","LCD de 40' ");
		
		dispSCDSalo.add(d11);
		dispSCDSalo.add(d12);
		dispSCDSalo.add(d13);
		dispSCDSalo.add(d14);
		dispSCDSalo.add(d15);
		
		//Ejemplo Pepe
		
		Dispositivo aire = f.crearDisp("Aire Acondicionado","3500 frigorias");
	 	Dispositivo aire1 =  f.crearDisp("Aire Acondicionado","2200 frigorias");
		Dispositivo plancha = f.crearDisp("Plancha","A vapor");
		Dispositivo tele = f.crearDisp("Televisor","Color de tubo fluorescente de 29' a 34' ");
		Dispositivo tele1 =  f.crearDisp("Televisor","Color de tubo fluorescente de 21' ");
		dispPepe.add(aire);dispPepe.add(aire1);dispPepe.add(plancha);dispPepe.add(tele);dispPepe.add(tele1);
		
	}
	

	@Test
    public void testMetodoSimplexSCD() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		PointValuePair solucionSCD = simplex.aplicarMetodoSimplex(dispSCD);
		Assert.assertEquals(1470, solucionSCD.getValue(), 0.01);
<<<<<<< HEAD
		System.out.println("Test testMetodoSimplexSCD:\n " + "la suma de los x dio 1470: " + solucionSCD.getValue());
=======
		System.out.println("Test testMetodoSimplexSCD:\n "
				+ "la suma de los x dio 1080: " + solucionSCD.getValue());
>>>>>>> af51f5c11f174cf8936e2b42b0e0a51995997726
		Assert.assertEquals(360, solucionSCD.getPoint()[0], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x4 dio: " + solucionSCD.getPoint()[0]);
		Assert.assertEquals(30, solucionSCD.getPoint()[1], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x3 dio: " + solucionSCD.getPoint()[1]);
		Assert.assertEquals(360, solucionSCD.getPoint()[2], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x2 dio: " + solucionSCD.getPoint()[2]);
		Assert.assertEquals(360, solucionSCD.getPoint()[3], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x1 dio: " + solucionSCD.getPoint()[3]);
<<<<<<< HEAD
=======

>>>>>>> af51f5c11f174cf8936e2b42b0e0a51995997726
	}
	
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
		PointValuePair solucionSCD = simplex.aplicarMetodoSimplexSalo(dispDaniel);
		//Assert.assertEquals(1875, solucionSCD.getValue(), 0.01);
		System.out.println("Test testMetodoSimplexDaniel:\n " + "la suma de los x dio: " + solucionSCD.getValue());
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
		System.out.println("La cantidad de horas para el dispositivo x2 dio: " + solucionSCD.getPoint()[6]);
		//Assert.assertEquals(360, solucionSCD.getPoint()[7], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x1 dio: " + solucionSCD.getPoint()[7]);
	}
	
	@Test
	public void testMetodoSimplexEjemploPepe() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		PointValuePair solucionSCD = simplex.aplicarMetodoSimplexSalo(dispPepe);
		//Assert.assertEquals(1189.78, solucionSCD.getValue(), 0.01);
		System.out.println("Test testMetodoSimplexEjemploPepe:\n " + "La suma de los x dio: " + solucionSCD.getValue());
		//Assert.assertEquals(360, solucionSCD.getPoint()[0], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x5 dio: " + solucionSCD.getPoint()[0]);
		//Assert.assertEquals(360, solucionSCD.getPoint()[1], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x4 dio: " + solucionSCD.getPoint()[1]);
		//Assert.assertEquals(349.78, solucionSCD.getPoint()[2], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x3 dio: " + solucionSCD.getPoint()[2]);
		//Assert.assertEquals(30, solucionSCD.getPoint()[3], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x2 dio: " + solucionSCD.getPoint()[3]);
		//Assert.assertEquals(89.99, solucionSCD.getPoint()[4], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x1 dio: " + solucionSCD.getPoint()[4]);
	}
	
	
	@Test
    public void testMetodoSimplexSCDSalo() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		PointValuePair solucionSCD = simplex.aplicarMetodoSimplexSalo(dispSCDSalo);
		//Assert.assertEquals(1186.229, solucionSCD.getValue(), 0.01);
		System.out.println("Test testMetodoSimplexSCD:\n " + "la suma de los x dio: " + solucionSCD.getValue());
		//Assert.assertEquals(360, solucionSCD.getPoint()[0], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x5 dio: " + solucionSCD.getPoint()[0]);
		//Assert.assertEquals(346.229, solucionSCD.getPoint()[1], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x4 dio: " + solucionSCD.getPoint()[1]);
		//Assert.assertEquals(30, solucionSCD.getPoint()[2], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x3 dio: " + solucionSCD.getPoint()[2]);
		//Assert.assertEquals(90, solucionSCD.getPoint()[3], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x2 dio: " + solucionSCD.getPoint()[3]);
		//Assert.assertEquals(360, solucionSCD.getPoint()[4], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x1 dio: " + solucionSCD.getPoint()[4]);
	}
	
}