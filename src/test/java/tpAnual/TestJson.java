package tpAnual;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import exceptions.ExceptionsHandler;
import modelo.DAOJson;
import modelo.JsonManager;
import modelo.users.Administrador;
import modelo.users.Categoria;
import modelo.users.Cliente;

 //En la clase JsonManager agregue un monton de rutas para que las puedan usar mas facil, agreguen las suyas y las usan donde sea 

public class TestJson {

	String ruta = "\\C:\\Users\\Marina\\workspace\\TpAnualDds\\tpAnual\\JSONs";

	//Por alguna razon el deserializarlista y buscarindex no me esta funcionando para algunas clases pero para otras si, para mi no consigue instanciar los 
	//objetos pero no se por que, me trajo problemas con los dispositivos y ahora con la clase cliente
	
	//@Test
	public void testJsonTraeClientes() throws FileNotFoundException, InstantiationException, IllegalAccessException {
		List<Cliente> clientes = null;
		try {
			clientes = DAOJson.deserializarLista(Cliente.class,JsonManager.rutaJsonClientes);
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
		Assert.assertEquals(2, clientes.size());
		System.out.println("Test JsonTraeClientes: El tama�o de la lista de clientes del json es dos: " + clientes.size());
    }
    
   //@Test
	public void testJsonTraeUnCliente() throws FileNotFoundException, InstantiationException, IllegalAccessException{
    	Cliente cli = null;
		try {
			cli = (Cliente) DAOJson.buscarIndexEnLista(Cliente.class, 1,JsonManager.rutaJsonClientes);
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
    	Assert.assertEquals("homero",cli.getNombre());
		System.out.println("Test JsonTraeUnCliente: El nombre del cliente del json es: " + cli.getNombre());
    	Assert.assertEquals("homero",cli.getNombre());
    }
    
   @Test
    public void testJsonTraeUnAdmin() throws FileNotFoundException, InstantiationException, IllegalAccessException {
    	Administrador admin = null;
    	try {
    		admin = (Administrador) DAOJson.buscarIndexEnLista(Administrador.class,0,JsonManager.rutaJsonAdmin);
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
    	Assert.assertEquals(20000,admin.getCodAdmin());
    	System.out.println("Test JsonTraeUnAdmin: El c�digo del admin en la posici�n 0 es 20000: " + admin.getCodAdmin());
    }
    
    @Test
	public void testJsonTraeCategoriasYDatos() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		List<Categoria> categorias = null;		
		try {
			categorias = DAOJson.deserializarLista(Categoria.class,JsonManager.rutaJsonCateg);
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

}