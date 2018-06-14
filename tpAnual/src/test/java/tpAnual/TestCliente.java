package tpAnual;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import org.junit.*;

import modelo.devices.DispositivoInteligente;
import modelo.users.Cliente;
import modelo.users.Cliente.TipoDocumento;
import modelo.devices.Dispositivo;


public class TestCliente {
	
	private Cliente clienteDePrueba;
	private List<Dispositivo> dispositivosPrueba = new ArrayList<>();
	DispositivoInteligente tele = new DispositivoInteligente("Televisor",0.14);
	DispositivoInteligente aire = new DispositivoInteligente("Aire acondicionado",1.013);
	LocalDateTime fechaFinalParaTest = LocalDateTime.of(2018,4,18,7,50,0);
	
	@Before
	public void init() {
		dispositivosPrueba.add(tele);
		this.clienteDePrueba = new Cliente("bart","simpson","elbarto","12345",2018,3,1,TipoDocumento.DNI,
								"4444444","11111111","Avenida Siempreviva 742",dispositivosPrueba);
		tele.setFechaRegistro(2018,2,5,22,15,0); //Instanciamos la fecha de registro de los dispositivos
		aire.setFechaRegistro(2018,4,10,0,0,0);
	}
	

	/*@Test
	public void testCalcularConsumoSoloConLaTeleEnLista() {
		Assert.assertEquals(239.9012,clienteDePrueba.calcularConsumo(fechaFinalParaTest),5);
		System.out.println("Test calcularConsumoSoloConLaTeleEnLista:\n El consumo del cliente sólo con la tele es aproximadamente de 239.9012 kWh: " + clienteDePrueba.calcularConsumo(fechaFinalParaTest));
	}*/

	@Test
	public void testAgregarDispositivo() {
		clienteDePrueba.agregarDispositivo(aire);
		Assert.assertTrue(clienteDePrueba.getDispositivos().contains(aire));
	}

	@Test
	public void testQuitarDispositivo() {
		clienteDePrueba.quitarDispositivo(tele);
		Assert.assertFalse(clienteDePrueba.getDispositivos().contains(tele));
	}

	@Test
	public void testAlgunoEncendido() {
		Assert.assertTrue(clienteDePrueba.algunoEncendido());
	}

	@Test
	public void testCantDispositivosApagados() {
		Assert.assertEquals(clienteDePrueba.cantDisp(false),0);
		System.out.println("Test cantDispositivosApagados:\n Al tener únicamente a la tele en la lista, la cantidad de dispositivos apagados es cero: " + clienteDePrueba.cantDisp(false));
	}
	
	@Test
	public void testCantDispositivosEncendidos() {
		Assert.assertEquals(clienteDePrueba.cantDisp(true),1);
		System.out.println("Test cantDispositivosApagados:\n Al tener únicamente a la tele en la lista, la cantidad de dispositivos encendidos es uno: " + clienteDePrueba.cantDisp(true));
	}
}