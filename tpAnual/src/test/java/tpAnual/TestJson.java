package tpAnual;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestJson {
	
	@Test
	public void testJsonTraeClientes() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		List<Cliente> clientes = DAOJson.deserializarLista(Cliente.class, "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonClientes.json");
		Assert.assertEquals("homero", clientes.get(1).nombre);
		Assert.assertEquals(2, clientes.size());
    }
    
    @Test
	public void testJsonTraeUnCliente() throws FileNotFoundException, InstantiationException, IllegalAccessException{
    	Cliente cli = (Cliente) DAOJson.buscarIndexEnLista(Cliente.class, 1,"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonClientes.json");
    	Assert.assertEquals("homero",cli.nombre);
    	Assert.assertEquals("homero",cli.getNombre());
    }
    
    @Test
    public void testJsonTraeUnDispositivo() throws FileNotFoundException, InstantiationException, IllegalAccessException{
    	Dispositivo disp = (Dispositivo) DAOJson.buscarIndexEnLista(Dispositivo.class, 1,"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonDispositivos.json");
    	Assert.assertEquals("microondas", disp.getNombreDisp());
    }
    
    @Test
    public void testJsonTraeUnAdmin() throws FileNotFoundException, InstantiationException, IllegalAccessException {
    	Administrador admin = (Administrador) DAOJson.buscarIndexEnLista(Administrador.class, 0,"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonAdministradores.json");
    	Assert.assertEquals(20000, admin.getCodAdmin());	//C:\Users\Salome\git\TpAnualDds\tpAnual\JSONs Dejenme esto aca para poder hacer pruebas en mi cochino windows
    }
    
}
