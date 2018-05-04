package capaPresentacion;

import tpAnual.ExceptionsHandler;

import java.util.List;

import tpAnual.Categoria;
import tpAnual.Cliente;
import tpAnual.DAOJson;
import tpAnual.Cliente.TipoDocumento;

public class Programa {
	
	public static boolean isBetween(double n,double min,double max) {
		return (n>=min)&&(n<=max);
	}

	public static Categoria categoria(double consumo) {
		List<Categoria> categorias = null;
		Categoria categoria = null;
		try {
			categorias = DAOJson.deserializarLista(Categoria.class,"//C://Users//Salome//git//TpAnualDds//tpAnual//JSONs//jsonCategorias.json"); // //home//dds//git//TpAnualDds//tpAnual//JSONs//jsonCategorias.json
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
		for(Categoria c: categorias) {
			if(isBetween(consumo,c.getMin(),c.getMax())) {
				categoria = c;
			}
		}
		return categoria;
	}
	
	public static void main(String[] args) {
		Cliente cprueba = new Cliente("bart","simpson","elbarto","12345",TipoDocumento.DNI,"4444444","11111111","Avenida Siempreviva 742");
		cprueba.setCateg(); // ver def de este metodo en cliente
		System.out.println(cprueba.getCateg().getClasif());
		System.out.println(cprueba.obtenerTarifa());
	}
}