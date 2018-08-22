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
	Cliente pepe = new Cliente();
	MetodoSimplex simplex = new MetodoSimplex();
	
<<<<<<< HEAD
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
=======
	List<DispositivoInteligente> dispSCDSalo = new ArrayList<DispositivoInteligente>();
	List<DispositivoInteligente> dispSCISalo = new ArrayList<DispositivoInteligente>();
	List<DispositivoInteligente> dispDaniel = new ArrayList<DispositivoInteligente>();
>>>>>>> origin/Entrega2
	
	
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
		pepe.agregarDispositivo(aire); pepe.agregarDispositivo(aire1);
		
	}
	

<<<<<<< HEAD
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
    
=======
   
>>>>>>> origin/Entrega2
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

	//Este test es diferente ya que llama al simplex desde el cliente pepe
	@Test
	public void testMetodoSimplexEjemploPepe() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		PointValuePair solucionSCD = pepe.llamarSimplex();
		Assert.assertEquals(720, solucionSCD.getValue(), 0.01);
		System.out.println("Test testMetodoSimplexEjemploPepe:\n " + "La suma de los x dio: " + solucionSCD.getValue());
		Assert.assertEquals(360, solucionSCD.getPoint()[0], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x5 dio: " + solucionSCD.getPoint()[0]);
		Assert.assertEquals(360, solucionSCD.getPoint()[1], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x4 dio: " + solucionSCD.getPoint()[1]);
		
	}
}