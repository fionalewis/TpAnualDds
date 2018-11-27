package modelo;

import java.util.function.Predicate;
import modelo.deviceState.Encendido;
import modelo.devices.Dispositivo;

/* Esta clase esta para separar los predicados que necesiten de los metodos y les sea mas comodo usarlos
 * si llegan a usar un filter, un any, anyMatch o lo que sea pueden crear el predicate aca*/

public class FilterPredicates {
	
	public static Predicate<Dispositivo> filterDI(){
		return unDisp -> (Dispositivo.esInteligente(unDisp)&&(!Dispositivo.esConvertido(unDisp)));
	}
	
	public static Predicate<Dispositivo> filterDE(){
		return unDisp -> Dispositivo.esEstandar(unDisp);
	}
	
	public static Predicate<Dispositivo> filterDC(){
		return unDisp -> Dispositivo.esConvertido(unDisp);
	}
	
	public static Predicate<Dispositivo> filterAmbos(){
		return unDisp -> Dispositivo.esAmbos(unDisp);
	}
	
	public static Predicate<Dispositivo> filterAnyOn(){
		return unDisp -> unDisp.getEstadoDisp() instanceof Encendido;
	}
	
	public static Predicate<Dispositivo> filterDescartados(){
		return unDisp -> unDisp.getHorasUsoMax() == -1;
	}
	
}