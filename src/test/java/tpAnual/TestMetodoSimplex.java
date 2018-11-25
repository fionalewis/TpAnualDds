/*package tpAnual;

import java.io.FileNotFoundException;

import org.apache.commons.math3.optim.PointValuePair;
import org.junit.Before;
import org.junit.Test;

import modelo.devices.DeviceFactory;
import modelo.users.Cliente;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoConvertido;

public class TestMetodoSimplex {
	
	Cliente pepe = new Cliente();
	Cliente daniel = new Cliente();

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
		//Dispositivo dani6 = f.crearDisp("Microondas","Convencional"); //Estandar
		//Dispositivo dani7 = f.crearDisp("Plancha","A vapor"); //Estandar
		Dispositivo dani8 = f.crearDisp("Ventilador","De techo");
		Dispositivo dani9 = f.crearDisp("Termotanque","El�ctrico"); //El simplex ignora a estos dos ultimos disp
		Dispositivo dani10 = f.crearDisp("Horno","El�ctrico");
		
		daniel.agregarDispositivo(dani1);daniel.agregarDispositivo(dani2);daniel.agregarDispositivo(dani3);daniel.agregarDispositivo(dani4);
		daniel.agregarDispositivo(dani5);daniel.agregarDispositivo(dani8);daniel.agregarDispositivo(dani9);daniel.agregarDispositivo(dani10);

		//Ejemplo Pepe
		
		Dispositivo aire = f.crearDisp("Aire Acondicionado","3500 frigorias");
		Dispositivo aire1 = f.crearDisp("Aire Acondicionado","2200 frigorias");
		pepe.agregarDispositivo(aire); pepe.agregarDispositivo(aire1);
		
	}	
	
	@Test
    public void testSimplexDanielSinEstandar() throws FileNotFoundException, InstantiationException, IllegalAccessException{	
		PointValuePair solucionSimplexDaniel = daniel.llamarSimplex();
		System.out.println("Test simplexDanielSinDispEstandar:\n" + "El valor de Z M�x dio: " + solucionSimplexDaniel.getValue()); 
		System.out.println("Las horas m�ximas para el dispositivo x1 (Televisor LED 40') son: " + solucionSimplexDaniel.getPoint()[0]);
		System.out.println("Las horas m�ximas para el dispositivo x2 (L�mpara de 11W) son: " + solucionSimplexDaniel.getPoint()[1]);
		System.out.println("Las horas m�ximas para el dispositivo x3 (Lavarropas semi autom�tico de 5kg) son: " + solucionSimplexDaniel.getPoint()[2]);
		System.out.println("Las horas m�ximas para el dispositivo x4 (PC de escritorio) son: " + solucionSimplexDaniel.getPoint()[3]);
		System.out.println("Las horas m�ximas para el dispositivo x5 (Aire Acondicionado de 2200 frigor�as) son: " + solucionSimplexDaniel.getPoint()[4]);
		System.out.println("Las horas m�ximas para el dispositivo x6 (Ventilador de techo) son: " + solucionSimplexDaniel.getPoint()[5]);
	}	
	
	@Test
    public void testSimplexDanielConDispEstandar() throws FileNotFoundException, InstantiationException, IllegalAccessException{	
		DeviceFactory f = new DeviceFactory();
		Dispositivo dani6 = f.crearDisp("Microondas","Convencional"); //Estandar
		Dispositivo dani7 = f.crearDisp("Plancha","A vapor"); //Estandar
		daniel.agregarDispositivo(dani6);daniel.agregarDispositivo(dani7);
		PointValuePair solucionSimplexDaniel = daniel.llamarSimplex();
		System.out.println("Test simplexDanielConDispEstandar:\n" + "El valor de Z M�x dio: " + solucionSimplexDaniel.getValue()); 
		System.out.println("Las horas m�ximas para el dispositivo x1 (Televisor LED 40') son: " + solucionSimplexDaniel.getPoint()[0]);
		System.out.println("Las horas m�ximas para el dispositivo x2 (L�mpara de 11W) son: " + solucionSimplexDaniel.getPoint()[1]);
		System.out.println("Las horas m�ximas para el dispositivo x3 (Lavarropas semi autom�tico de 5kg) son: " + solucionSimplexDaniel.getPoint()[2]);
		System.out.println("Las horas m�ximas para el dispositivo x4 (PC de escritorio) son: " + solucionSimplexDaniel.getPoint()[3]);
		System.out.println("Las horas m�ximas para el dispositivo x5 (Aire Acondicionado de 2200 frigor�as) son: " + solucionSimplexDaniel.getPoint()[4]);
		System.out.println("Las horas m�ximas para el dispositivo x6 (Ventilador de techo) son: " + solucionSimplexDaniel.getPoint()[5]);
		System.out.println("Las horas m�ximas para el dispositivo x7 (Microondas convencional) son: " + solucionSimplexDaniel.getPoint()[6]);
		System.out.println("Las horas m�ximas para el dispositivo x8 (Plancha a vapor) son: " + solucionSimplexDaniel.getPoint()[7]);	
	}
    
	@Test
    public void testSimplexDanielConDispConvertidos() throws FileNotFoundException, InstantiationException, IllegalAccessException{	
    	DeviceFactory f = new DeviceFactory();
    	Dispositivo dani6 = f.crearDisp("Microondas","Convencional"); //Estandar
		Dispositivo dani7 = f.crearDisp("Plancha","A vapor"); //Estandar
    	//Convertimos el microondas y la plancha a inteligentes
		DispositivoConvertido dani6conv = new DispositivoConvertido(dani6);
		DispositivoConvertido dani7conv = new DispositivoConvertido(dani7);	
		daniel.agregarDispositivo(dani6conv);daniel.agregarDispositivo(dani7conv);
		PointValuePair solucionSimplexDaniel = daniel.llamarSimplex();
		System.out.println("Test simplexDanielConDispConvertidos:\n" + "El valor de Z M�x dio: " + solucionSimplexDaniel.getValue()); 
		System.out.println("Las horas m�ximas para el dispositivo x1 (Televisor LED 40') son: " + solucionSimplexDaniel.getPoint()[0]);
		System.out.println("Las horas m�ximas para el dispositivo x2 (L�mpara de 11W) son: " + solucionSimplexDaniel.getPoint()[1]);
		System.out.println("Las horas m�ximas para el dispositivo x3 (Lavarropas semi autom�tico de 5kg) son: " + solucionSimplexDaniel.getPoint()[2]);
		System.out.println("Las horas m�ximas para el dispositivo x4 (PC de escritorio) son: " + solucionSimplexDaniel.getPoint()[3]);
		System.out.println("Las horas m�ximas para el dispositivo x5 (Aire Acondicionado de 2200 frigor�as) son: " + solucionSimplexDaniel.getPoint()[4]);
		System.out.println("Las horas m�ximas para el dispositivo x6 (Ventilador de techo) son: " + solucionSimplexDaniel.getPoint()[5]);
		System.out.println("Las horas m�ximas para el dispositivo x7 (Microondas convencional - CONVERTIDO) son: " + solucionSimplexDaniel.getPoint()[6]);
		System.out.println("Las horas m�ximas para el dispositivo x8 (Plancha a vapor - CONVERTIDO) son: " + solucionSimplexDaniel.getPoint()[7]);	
	}

	@Test
	public void testSimplexPepe() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		PointValuePair solucionSCD = pepe.llamarSimplex();
		System.out.println("Test testSimplexPepe:\n" + "El valor de Z M�x dio: " + solucionSCD.getValue());
		System.out.println("Las horas m�ximas para el dispositivo x1 (Aire Acondicionado de 3500 frigor�as) son: " + solucionSCD.getPoint()[0]);
		System.out.println("Las horas m�ximas para el dispositivo x2 (Aire Acondicionado de 2200 frigor�as) son: " + solucionSCD.getPoint()[1]);
	}
	
	@Test
	public void testSimplexPepeConDispEstandar() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		DeviceFactory f = new DeviceFactory();
		Dispositivo estandar = f.crearDisp("Microondas","Convencional");	
		pepe.agregarDispositivo(estandar);
		PointValuePair solucionSCD = pepe.llamarSimplex();
		System.out.println("Test testSimplexPepeConDispEstandar:\n" + "El valor de Z M�x dio: " + solucionSCD.getValue());
		System.out.println("Las horas m�ximas para el dispositivo x1 (Aire Acondicionado de 3500 frigor�as) son: " + solucionSCD.getPoint()[0]);
		System.out.println("Las horas m�ximas para el dispositivo x2 (Aire Acondicionado de 2200 frigor�as) son: " + solucionSCD.getPoint()[1]);
		System.out.println("Las horas m�ximas para el dispositivo x3 (Microondas convencional) son: " + solucionSCD.getPoint()[2]);
	}
	
	//@Test
	public void testSimplexPepeConDispConv() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		DeviceFactory f = new DeviceFactory();
		Dispositivo estandar = f.crearDisp("Microondas","Convencional");	
		DispositivoConvertido estandarConv = new DispositivoConvertido(estandar);
		daniel.agregarDispositivo(estandarConv);
		PointValuePair solucionSCD = pepe.llamarSimplex();
		System.out.println("Test testSimplexPepeConDispConv:\n" + "El valor de Z M�x dio: " + solucionSCD.getValue());
		System.out.println("Las horas m�ximas para el dispositivo x1 (Aire Acondicionado de 3500 frigor�as) son: " + solucionSCD.getPoint()[0]);
		System.out.println("Las horas m�ximas para el dispositivo x2 (Aire Acondicionado de 2200 frigor�as) son: " + solucionSCD.getPoint()[1]);
		System.out.println("Las horas m�ximas para el dispositivo x3 (Microondas convencional - CONVERTIDO) son: " + solucionSCD.getPoint()[2]);
	}
	
}*/