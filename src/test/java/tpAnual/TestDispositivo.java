package tpAnual;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import exceptions.ExceptionsHandler;
import modelo.devices.DeviceFactory;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.Sensor;

public class TestDispositivo {

	//DispositivoInteligente tele = new DispositivoInteligente("Televisor",0.14);
	LocalDateTime fechaFinalParaTest = LocalDateTime.of(2018,4,18,7,50,0);
	//Sensor sensorVolumen = new Sensor("volumen",tele);
	List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();
	DispositivoInteligente disp1 = new DispositivoInteligente();
	DispositivoEstandar disp2 = new DispositivoEstandar();
	DispositivoInteligente disp3 = new DispositivoInteligente();
	DeviceFactory f = new DeviceFactory();
	
	@Before
	public void init() {
		//tele.setFechaRegistro(2018,2,5,22,15,0);
		//tele.agregarSensor(sensorVolumen);
		//tele.setMagnitudSensor("volumen", 20);
		
		disp1 = (DispositivoInteligente) f.crearDisp("Televisor","LED 40' ");
		disp2 = (DispositivoEstandar) f.crearDisp("Plancha","A vapor");
		disp3 = (DispositivoInteligente) f.crearDisp("Televisor","LED 40' ");
		
	}
	
	//@Test
	public void subir5VolumenTele() {
		//tele.aumentarIntensidadSensor("volumen", 5);
		//Assert.assertEquals(25, tele.getMagnitudSensor("volumen"),0.1);
		//System.out.println("Test subir5VolumenTele:\n Luego de subirle 5 unidades de volumen a la tele, quedo: " 
		//+ tele.getMagnitudSensor("volumen"));
	}
	
	//@Test
	public void bajar5VolumenTele() {
		//tele.disminuirIntensidadSensor("volumen", 5);
		//Assert.assertEquals(15, tele.getMagnitudSensor("volumen"),0.1);
		//System.out.println("Test bajar5VolumenTele:\n Luego de bajarle 5 unidades de volumen a la tele, quedo: " 
		//+ tele.getMagnitudSensor("volumen"));
	}
	

	@Test
    public void testInstanciarDosDisposIguales() 
    		throws FileNotFoundException, InstantiationException, IllegalAccessException{
		
		Assert.assertTrue(disp1.getEquipoConcreto() == disp3.getEquipoConcreto());
		Boolean result = disp1.getEquipoConcreto() == disp3.getEquipoConcreto();
		System.out.println("TestInstanciarDosDisposIguales: \nEl dispositivo 1 y 3 "
				+ "deberian ser del mismo tipo,"
				+ "\ntipo 1: " + disp1.equipoConcreto
				+ "\ntipo 3: " + disp3.equipoConcreto
				+ "\n resulatado: "
				+ result);
	}
	
	@Test
    public void testInstanciarDosDisposDistintos() 
    		throws FileNotFoundException, InstantiationException, IllegalAccessException{
		
		Assert.assertFalse(disp1.getEquipoConcreto() == disp2.getEquipoConcreto());
		Boolean result = disp1.getEquipoConcreto() != disp2.getEquipoConcreto();
		System.out.println("TestInstanciarDosDisposIguales: \nEl dispositivo 1 y 2 "
				+ "deberian ser tipos distintos,"
				+ "\ntipo 1: " + disp1.equipoConcreto
				+ "\ntipo 2: " + disp2.equipoConcreto
				+ "\n resulatado: "
				+ result);
	}

}