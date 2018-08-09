package tpAnual;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import exceptions.ExceptionsHandler;
import modelo.DAOJson;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoInteligente;
import modelo.users.Administrador;
import modelo.users.Categoria;
import modelo.users.Cliente;

/* Acuerdense de revisar si tienen los JSONs en la misma ubicacion antes de correr los tests !!! */

public class TestJson {

		String ruta = "\\C:\\Users\\Marina\\workspace\\TpAnualDds\\tpAnual\\JSONs";
	
	@Test
	public void testJsonTraeClientes() throws FileNotFoundException, InstantiationException, IllegalAccessException {
		List<Cliente> clientes = null;
		try {
			clientes = DAOJson.deserializarLista(Cliente.class, 
					//"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonClientes.json"
					ruta.concat("\\jsonClientes.json"));
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
		Assert.assertEquals(2, clientes.size());
		System.out.println("Test JsonTraeClientes: El tama�o de la lista de clientes del json es dos: " + clientes.size());
    }
    
   @Test
	public void testJsonTraeUnCliente() throws FileNotFoundException, InstantiationException, IllegalAccessException{
    	Cliente cli = null;
		try {
			cli = (Cliente) DAOJson.buscarIndexEnLista(Cliente.class, 1,
					//"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonClientes.json"
					ruta.concat("\\jsonClientes.json"));
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
    	Assert.assertEquals("homero",cli.getNombre());
		System.out.println("Test JsonTraeUnCliente: El nombre del cliente del json es: " + cli.getNombre());
    	Assert.assertEquals("homero",cli.getNombre());
    }
    
    @Test
    public void testJsonTraeUnDispositivo() throws FileNotFoundException, InstantiationException, IllegalAccessException{
    	DispositivoInteligente disp = null;
		try {
			disp = (DispositivoInteligente) DAOJson.buscarIndexEnLista(DispositivoInteligente.class, 1,
					//"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonDispositivos.json"
					ruta.concat("\\jsonDispositivos.json"));
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
    		admin = (Administrador) DAOJson.buscarIndexEnLista(Administrador.class, 0,
    				//"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonAdministradores.json"
    				ruta.concat("\\jsonAdministradores.json"));
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
    	Assert.assertEquals(20000, admin.getCodAdmin());	//C:\Users\Salome\git\TpAnualDds\tpAnual\JSONs Dejenme esto aca para poder hacer pruebas en windows
    	System.out.println("Test JsonTraeUnAdmin: El c�digo del admin en la posici�n 0 es 20000: " + admin.getCodAdmin());
    }
    
    @Test
	public void testJsonTraeCategoriasYDatos() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		List<Categoria> categorias = null;
		try {
			categorias = DAOJson.deserializarLista(Categoria.class, 
					//"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonCategorias.json"
					ruta.concat("\\jsonCategorias.json"));
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
		Assert.assertEquals(9, categorias.size());
		System.out.println("Test JsonTraeCategorias:\n El tama�o de la lista de categorias del json es nueve: " + categorias.size());
		Assert.assertEquals("R3", categorias.get(2).getClasif());
		System.out.println("Test JsonTraeCategorias:\n La categor�a en la posici�n dos es R3: " + categorias.get(2).getClasif());
		Assert.assertEquals(110.38, categorias.get(4).getCargoFijo(),0.1);
		System.out.println("Test JsonTraeCategorias:\n El valor de cargoFijo de R5 es 110.38: " + categorias.get(4).getCargoFijo());
		Assert.assertEquals(0.851, categorias.get(8).getCargoVariable(),0.1);
		System.out.println("Test JsonTraeCategorias:\n El valor de cargoVariable de R9 es 0.851: " + categorias.get(8).getCargoVariable());
    }
    
	@Test
	public void testJsonUsoDispoMensual() throws FileNotFoundException, InstantiationException, IllegalAccessException{
    	DispositivoInteligente disp = null;
    	try {
			disp = (DispositivoInteligente) DAOJson.buscarIndexEnLista(DispositivoInteligente.class, 0,
					//"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonDispositivos.json"
					ruta.concat("\\jsonHorasUsoMensualDispo.json"));
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
    	Assert.assertEquals("Aire Acondicionado", disp.getNombreDisp());
    	System.out.println("Test JsonJsonUsoDispoMensual: El nombre del dispositivo del json es: " + disp.getNombreDisp());
    	Assert.assertEquals(90.0, disp.getHorasUsoMin(),0.1);
    	System.out.println("Test JsonJsonUsoDispoMensual: Las horas de uso mensual minimo del dispositivo del json es: " 
    			+ disp.getHorasUsoMin());
    	Assert.assertEquals(360.0, disp.getHorasUsoMax(),0.1);
    	System.out.println("Test JsonJsonUsoDispoMensual: Las horas de uso mensual maximo del dispositivo del json es: " 
    			+ disp.getHorasUsoMax());
    }
    
}