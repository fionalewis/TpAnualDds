package tpAnual;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import exceptions.ExceptionsHandler;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.DispositivosRepository;
import modelo.devices.Sensor;

public class TestDispositivo {

	DispositivoInteligente tele = new DispositivoInteligente("Televisor",0.14);
	LocalDateTime fechaFinalParaTest = LocalDateTime.of(2018,4,18,7,50,0);
	Sensor sensorVolumen = new Sensor("volumen",tele);
	DispositivosRepository repoDispo = new DispositivosRepository();
	List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();
	DispositivoInteligente disp1 = new DispositivoInteligente();
	DispositivoEstandar disp2 = new DispositivoEstandar();
	DispositivoInteligente disp3 = new DispositivoInteligente();
	
	@Before
	public void init() {
		tele.setFechaRegistro(2018,2,5,22,15,0);
		tele.agregarSensor(sensorVolumen);
		tele.setMagnitudSensor("volumen", 20);
		
		try{
			repoDispo.importarDispoDeJson();
		} catch(Exception e){
			ExceptionsHandler.catchear(e);
		}
		disp1 = (DispositivoInteligente) repoDispo.crearDispositivoSegunTipo(0);
		disp2 = (DispositivoEstandar) repoDispo.crearDispositivoSegunTipo(4);
		disp3 = (DispositivoInteligente) repoDispo.crearDispositivoSegunTipo(0);
		dispositivos.add(disp1);
		dispositivos.add(disp2);
		dispositivos.add(disp3);
		
	}
	
	//@Test
	/*public void calculoDeHorasTele() {
		Assert.assertEquals(1713.58,tele.calculoDeHoras(fechaFinalParaTest),0.1);
		System.out.println("Test calculoDeHorasTele:\n  En el intervalo seleccionado pasaron aprox. 1713.58hs: " 
		+ tele.calculoDeHoras(fechaFinalParaTest));
	}
	*/
	//@Test
	/*public void consumoActualTele() {
		Assert.assertEquals(239.9012,tele.consumoTotal(fechaFinalParaTest),0.1);
		System.out.println("Test consumoActualTele:\n  En el intervalo seleccionado el consumo de la tele fue "
				+ "de aprox. 239.9012 kWh: " + tele.consumoTotal(fechaFinalParaTest));
	}*/
	
	@Test
	public void subir5VolumenTele() {
		tele.aumentarIntensidadSensor("volumen", 5);
		Assert.assertEquals(25, tele.getMagnitudSensor("volumen"),0.1);
		System.out.println("Test subir5VolumenTele:\n Luego de subirle 5 unidades de volumen a la tele, quedo: " 
		+ tele.getMagnitudSensor("volumen"));
	}
	
	@Test
	public void bajar5VolumenTele() {
		tele.disminuirIntensidadSensor("volumen", 5);
		Assert.assertEquals(15, tele.getMagnitudSensor("volumen"),0.1);
		System.out.println("Test bajar5VolumenTele:\n Luego de bajarle 5 unidades de volumen a la tele, quedo: " 
		+ tele.getMagnitudSensor("volumen"));
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
	
	@Test
    public void testCantDispoDeUnTipo(){
		
		Assert.assertEquals(2,repoDispo.filtrarCantSegunTipo(dispositivos,0),0.01);
		System.out.println("TestCantDispoDeUnTipo: "
				+ "la lista de dispo hay 2 dispositivos de tipo 0, \nretultado: "
				+ repoDispo.filtrarCantSegunTipo(dispositivos,0));
		
	}
	
	@Test
    public void testCantTipos(){
		Assert.assertEquals(2,repoDispo.filtrarCantTipos(dispositivos),0.1);
		System.out.println("TestCantTipos: "
				+ "la lista tiene dispositivos de 2 tipos distintos, \nretultado: "
				+ repoDispo.filtrarCantTipos(dispositivos));
	}
	
	@Test
	public void testListaRepresentantes(){
		List<Dispositivo> listaRepresentante = new ArrayList();
		listaRepresentante = repoDispo.filtrarRepresentatesDeTipos(dispositivos);
		Assert.assertEquals(repoDispo.filtrarCantTipos(dispositivos),listaRepresentante.size(),0.01);
		System.out.println("testListaRepresentantes: "
				+ "Obtuve una lista de los representantes por cada tipo => "
				+ "\nLa cantidad de tipos debe ser igual al tamanio de la lista representantes: "
				+ "\nCant tipos: " + repoDispo.filtrarCantTipos(dispositivos)
				+ " y tamanio lista representantes: " + listaRepresentante.size());
	}
	
	@Test
	public void testListaDeCantPorTipo(){
		List<Integer> listaDeCantPorTipo = new ArrayList<Integer>();
		listaDeCantPorTipo = repoDispo.generarListaDeCantDeCadaTipo(dispositivos);
		Assert.assertEquals(2,listaDeCantPorTipo.get(0),0.01);
		System.out.println("TestListaDeCantPorTipo: la lista tiene 2 dispositivos de tipo 0 y 1 de tipo 4: "
				+ "\nTipo 1: " + listaDeCantPorTipo.get(0)
				+ "\nTipo 4: " + listaDeCantPorTipo.get(1));
	}

}