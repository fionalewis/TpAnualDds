package tpAnual.model;

import java.io.FileNotFoundException;
import java.util.List;

public class RepoCliente {
	
	private String ruta = ".jsonCliente.json";
	
	public List<Cliente> getClientes() {
		try {
			return tpAnual.DAOJson.deserializarLista(Cliente.class, ruta);
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
