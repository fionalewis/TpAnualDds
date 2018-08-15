package tpAnual;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import exceptions.ExceptionsHandler;
import modelo.DAOJson;
//import modelo.DispositivosRepository; no se por que mi eclipse no me lo actualiza aunque lo tenga
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
			clientes = DAOJson.deserializarLista(Cliente.class, "\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\jsonClientes.json");
			clientes = DAOJson.deserializarLista(Cliente.class, 
					//"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonClientes.json"
					ruta.concat("\\jsonClientes.json"));
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
    	Dispositivo disp = null;
		try {
			disp = (Dispositivo) DAOJson.buscarIndexEnLista(Dispositivo.class, 1,"\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\jsonDispositivos.json");
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
    	System.out.println("Test JsonTraeUnAdmin: El código del admin en la posición 0 es 20000: " + admin.getCodAdmin());
    }
    
    @Test
	public void testJsonTraeCategoriasYDatos() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		List<Categoria> categorias = null;
		
		
		try {
			categorias = DAOJson.deserializarLista(Categoria.class, "\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\jsonCategorias.json");
			categorias = DAOJson.deserializarLista(Categoria.class, 
					//"//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonCategorias.json"
					ruta.concat("\\jsonCategorias.json"));
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
		
		
		
		/*try {
			categorias = DAOJson.deserializarLista(Categoria.class, "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonCategorias.json");
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}*/
		Assert.assertEquals(9, categorias.size());
		System.out.println("Test JsonTraeCategorias:\n El tamaño de la lista de categorias del json es nueve: " + categorias.size());
		Assert.assertEquals("R3", categorias.get(2).getClasif());
		System.out.println("Test JsonTraeCategorias:\n La categoría en la posición dos es R3: " + categorias.get(2).getClasif());
		Assert.assertEquals(110.38, categorias.get(4).getCargoFijo(),0.1);
		System.out.println("Test JsonTraeCategorias:\n El valor de cargoFijo de R5 es 110.38: " + categorias.get(4).getCargoFijo());
		Assert.assertEquals(0.851, categorias.get(8).getCargoVariable(),0.1);
		System.out.println("Test JsonTraeCategorias:\n El valor de cargoVariable de R9 es 0.851: " + categorias.get(8).getCargoVariable());
    }
    
    
	//@Test
    /*
    public void testJsonTablaDispositivos() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		List<Dispositivo> disp = null;
		DispositivosRepository repoDispo = new DispositivosRepository();
		
		try{
			repoDispo.importarDispoDeJson();
			disp = repoDispo.getDispositivosExistentes();
		} catch(Exception e){
			ExceptionsHandler.catchear(e);
		}
		
		Assert.assertEquals(24, disp.size());
		System.out.println("Test JsonJsonTablaDispositivos:\n "
				+ "El tamaño de la lista de dispositivos del json es: " + disp.size());
		
		Assert.assertEquals(90.0, disp.get(0).getHorasUsoMin(),0.1);
		System.out.println("Test JsonJsonTablaDispositivos:\n "
				+ "El uso mensual minimo del equipo en la posicion cero es 90: " 
				+ disp.get(0).getHorasUsoMin());
		
		Assert.assertEquals("Color de tubo fluorescente de 21' ", disp.get(2).getEquipoConcreto());
		System.out.println("Test JsonJsonTablaDispositivos:\n "
				+ "El equipo en la posicion dos es Color de tubo fluorescente de 21': \n " 
				+ disp.get(2).getEquipoConcreto());
		
		Assert.assertEquals(360.0, disp.get(3).getHorasUsoMax(),0.1);
		System.out.println("Test JsonJsonTablaDispositivos:\n "
				+ "El uso mensual maximo del equipo en la posicion tres es 360: " 
				+ disp.get(3).getHorasUsoMin());
		
		Assert.assertTrue(((DispositivoInteligente) disp.get(5)).getEsBajoConsumo());
		System.out.println("Test JsonJsonTablaDispositivos:\n "
				+ "El dispositivo en posicion cinco es de bajo consumo: " 
				+ ((DispositivoInteligente) disp.get(5)).getEsBajoConsumo());
		
		Assert.assertEquals(0.09, disp.get(8).getkWh(),0.001);
		System.out.println("Test JsonJsonTablaDispositivos:\n "
				+ "El valor de kWh del dispositivo en la posicion ocho es 0.09: " 
				+ disp.get(8).getkWh());

    }*/
}