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
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoInteligente;
import modelo.users.Cliente;

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
		
		dispSCI1.setHorasUsoMax(370);
		dispSCI1.setHorasUsoMin(90);
		dispSCI2.setHorasUsoMax(30);
		dispSCI2.setHorasUsoMin(6);
		dispSCI3.setHorasUsoMax(360);
		dispSCI3.setHorasUsoMin(120);
		dispSCI.add(dispSCI1);
		dispSCI.add(dispSCI2);
		dispSCI.add(dispSCI3);
		
		/*dispSI1.setHorasUsoMax(370);
		dispSI1.setHorasUsoMin(90);
		dispSI2.setHorasUsoMax(30);
		dispSI2.setHorasUsoMin(6);
		dispSI.add(dispSI1);
		dispSI.add(dispSI2);*/
	}
	
	@Test
    public void testMetodoSimplexSCD() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		PointValuePair solucionSCD = simplex.aplicarMetodoSimplex(dispSCD);
		Assert.assertEquals(1470, solucionSCD.getValue(), 0.01);
		System.out.println("Test testMetodoSimplexSCD:\n "
				+ "la suma de los x dio 1080: " + solucionSCD.getValue());
		Assert.assertEquals(360, solucionSCD.getPoint()[0], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x4 dio: " + solucionSCD.getPoint()[0]);
		Assert.assertEquals(30, solucionSCD.getPoint()[1], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x3 dio: " + solucionSCD.getPoint()[1]);
		Assert.assertEquals(360, solucionSCD.getPoint()[2], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x2 dio: " + solucionSCD.getPoint()[2]);
		Assert.assertEquals(360, solucionSCD.getPoint()[3], 0.01); 
		System.out.println("La cantidad de horas para el dispositivo x1 dio: " + solucionSCD.getPoint()[3]);

	}
	
	@Test
    public void testMetodoSimplexSCI(){
		try{
			PointValuePair solucionSCI = simplex.aplicarMetodoSimplex(dispSCI);
		}
		catch (Exception e) {
			System.out.println("testMetodoSimplexSCI: un sistema compatible indeterminado no tiene solucion");
		}
	}
	
}
