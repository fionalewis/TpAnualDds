package tpAnual;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/* Acuerdense de revisar si tienen los JSONs en la misma ubicacion antes de correr los tests !!! */

public class TestJson {

	@Test
	public void testJsonTraeClientes() throws FileNotFoundException, InstantiationException, IllegalAccessException {
		List<Cliente> clientes = null;
		try {
			clientes = DAOJson.deserializarLista(Cliente.class, "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonClientes.json");
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
		Assert.assertEquals(2, clientes.size());
		System.out.println("Test JsonTraeClientes: El tamaño de la lista de clientes del json es dos: " + clientes.size());
    }
    
   @Test
	public void testJsonTraeUnCliente() throws FileNotFoundException, InstantiationException, IllegalAccessException{
    	Cliente cli = null;
		try {
			cli = (Cliente) DAOJson.buscarIndexEnLista(Cliente.class, 1,"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonClientes.json");
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
    	Assert.assertEquals("homero",cli.nombre);
		System.out.println("Test JsonTraeUnCliente: El nombre del cliente del json es: " + cli.nombre);
    	Assert.assertEquals("homero",cli.getNombre());
    }
    
    @Test
    public void testJsonTraeUnDispositivo() throws FileNotFoundException, InstantiationException, IllegalAccessException{
    	Dispositivo disp = null;
		try {
			disp = (Dispositivo) DAOJson.buscarIndexEnLista(Dispositivo.class, 1,"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonDispositivos.json");
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
    	Assert.assertEquals("microondas", disp.getNombreDisp());
    	System.out.println("Test JsonTraeUnDispositivo: El nombre del dispositivo del json es: " + disp.getNombreDisp());
    }
    
    @Test
    public void testJsonTraeUnAdmin() throws FileNotFoundException, InstantiationException, IllegalAccessException {
    	Administrador admin = null;
    	try {
    		admin = (Administrador) DAOJson.buscarIndexEnLista(Administrador.class, 0,"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonAdministradores.json");
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
    	Assert.assertEquals(20000, admin.getCodAdmin());	//C:\Users\Salome\git\TpAnualDds\tpAnual\JSONs Dejenme esto aca para poder hacer pruebas en windows
    	System.out.println("Test JsonTraeUnAdmin: El código del admin en la posición 0 es 20000: " + admin.getCodAdmin());
    }
    
    @Test
	public void testJsonTraeCategoriasYDatos() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		List<Categoria> categorias = null;
		try {
			categorias = DAOJson.deserializarLista(Categoria.class, "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonCategorias.json");
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
		Assert.assertEquals(9, categorias.size());
		System.out.println("Test JsonTraeCategorias:\n El tamaño de la lista de categorias del json es nueve: " + categorias.size());
		Assert.assertEquals("R3", categorias.get(2).getClasif());
		System.out.println("Test JsonTraeCategorias:\n La categoría en la posición dos es R3: " + categorias.get(2).getClasif());
		Assert.assertEquals(110.38, categorias.get(4).getCargoFijo(),0.1);
		System.out.println("Test JsonTraeCategorias:\n El valor de cargoFijo de R5 es 110.38: " + categorias.get(4).getCargoFijo());
		Assert.assertEquals(0.851, categorias.get(8).getCargoVariable(),0.1);
		System.out.println("Test JsonTraeCategorias:\n El valor de cargoVariable de R9 es 0.851: " + categorias.get(8).getCargoVariable());
    }
    
}