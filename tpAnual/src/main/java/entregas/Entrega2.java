package entregas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import exceptions.ExceptionsHandler;
import modelo.DAOJson;
import modelo.MetodoSimplex;
import modelo.devices.Dispositivo;
//import modelo.devices.DispositivoConvertido;
//import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.IntervaloDispositivo;
//import modelo.devices.IntervaloDispositivo.modo;
import modelo.users.Categoria;
import modelo.users.Cliente;
import modelo.users.Cliente.TipoDocumento;

public class Entrega2 {
	
	public static boolean isBetween(double n,double min,double max) {
		return (n>=min)&&(n<=max);
	}
	
	public static Categoria categoria(double consumo) {
		List<Categoria> categorias = null;
		Categoria categoria = null;
		try {
			categorias = DAOJson.deserializarLista(Categoria.class,"\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\jsonCategorias.json");
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
	
		DispositivoInteligente dispSCD1 = new DispositivoInteligente("dispSCD1",  0.18);
		DispositivoInteligente dispSCD2 = new DispositivoInteligente("dispSCD2",  0.875);
		DispositivoInteligente dispSCD3 = new DispositivoInteligente("dispSCD3",  0.06);
		List<DispositivoInteligente> dispSCD = new ArrayList<DispositivoInteligente>();
		
		DispositivoInteligente dispSCI1 = new DispositivoInteligente("dispSCI1",  0.18);
		DispositivoInteligente dispSCI2 = new DispositivoInteligente("dispSCI2",  0.875);
		DispositivoInteligente dispSCI3 = new DispositivoInteligente("dispSCI3",  180);
		List<DispositivoInteligente> dispSCI = new ArrayList<DispositivoInteligente>();
		
		
}

}
