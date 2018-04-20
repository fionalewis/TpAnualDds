package tpAnual;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import org.junit.*;
import tpAnual.Cliente.TipoDocumento;


public class TestCliente {
	
	//private static final TipoDocumento DNI = null;
	private Cliente clienteDePrueba;
	private List<Dispositivo> dispositivosPrueba = new ArrayList<>();
	Dispositivo tele = new Dispositivo("Televisor",0.14);
	Dispositivo aire = new Dispositivo("Aire acondicionado",1.013); //Por si llega a ser necesario agregar m�s tests
	LocalDateTime fechaFinalParaTest = LocalDateTime.of(2018,4,18,7,50,0);
	
	@Before
	public void init() {
		dispositivosPrueba.add(tele);
		this.clienteDePrueba = new Cliente("bart","simpson","elbarto","12345",2018,3,1,TipoDocumento.DNI,
								"4444444","11111111","Avenida Siempreviva 742",dispositivosPrueba);
		tele.setFechaRegistro(2018,2,5,22,15,0); //Instanciamos la fecha de registro de los dispositivos
		aire.setFechaRegistro(2018,4,10,0,0,0);
	}
	

	@Test
	public void testCalcularConsumoSoloConLaTeleEnLista() {
		Assert.assertEquals(239.9012,clienteDePrueba.calcularConsumo(fechaFinalParaTest),5);
	}

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
	public void testGetCategDefault() {
		Assert.assertEquals(clienteDePrueba.getCateg().getClasif(),"R1");
	}
	
	@Test
	public void testActualizarCategoriaR2PorTenerConsumoDeLaTele() {
		double cons = clienteDePrueba.calcularConsumo();
		Categoria categ = clienteDePrueba.getCateg();
		Assert.assertEquals(clienteDePrueba.categoria(cons,categ),"R2");
	}

	@Test
	public void testAlgunoEncendido() {
		Assert.assertTrue(clienteDePrueba.algunoEncendido(clienteDePrueba.getDispositivos()));
	}

	@Test
	public void testCantDispositivosApagados() {
		Assert.assertEquals(clienteDePrueba.cantDisp(false),0);
	}
	
	@Test
	public void testCantDispositivosEncendidos() {
		Assert.assertEquals(clienteDePrueba.cantDisp(true),1);
	}
}
