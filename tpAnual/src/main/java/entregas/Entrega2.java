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
			categorias = DAOJson.deserializarLista(Categoria.class,"C:\\Users\\Giuli\\eclipse-workspace\\TpAnualDds\\tpAnual\\JSONs");
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


}

}