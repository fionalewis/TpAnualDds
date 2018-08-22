package modelo.devices;

import java.util.ArrayList;
import java.util.List;

import modelo.JsonManager;
import modelo.geoLocation.Transformador;
import modelo.users.Cliente;
import modelo.users.Cliente.TipoDocumento;

public class DeviceFactory {
	
	public static List<DispositivoInteligente> tablaDI = new ArrayList<>();
	public static List<DispositivoEstandar> tablaDE = new ArrayList<>();
	public static List<Dispositivo> DGral = new ArrayList<>(); 
	
	public DeviceFactory() {
		JsonManager.inicializarFactory();
		DGral.addAll(tablaDI);DGral.addAll(tablaDE);
	}
	
	public Dispositivo crearDisp(String tipo) { //Por si llega a hacer falta
		return JsonManager.buscarPrimerMatch(tipo);
	}
	
	public Dispositivo crearDisp(String tipo,String descrip){
		if (cumpleCondInteligente(tipo,descrip))
			return crearDispInteligente(tipo,descrip);
		else{
			return crearDispEstandar(tipo,descrip);
		}
    }
	
	public DispositivoInteligente crearDispInteligente(String t,String d) {
		int pos = encontrarPosEnTablaDI(t,d);
		if(pos==-1) {
			System.out.println("No se encontró el dispositivo buscado.");
		}
		DispositivoInteligente dispIntel = tablaDI.get(pos);
		return dispIntel;
	}
	
	public int encontrarPosEnTablaDI(String t,String d) {
		int pos = -1;
		for(int i = 0;i<tablaDI.size();i++) {
			Dispositivo act = tablaDI.get(i);
			if(act.getNombreDisp().equals(t)&&act.getEquipoConcreto().equals(d)) {
				pos = i;
			}
		}
		return pos;
	}
	
	public DispositivoEstandar crearDispEstandar(String t,String d) {
		int pos = encontrarPosEnTablaDE(t,d);
		if(pos==-1) {
			System.out.println("No se encontró el dispositivo buscado.");
		}
		DispositivoEstandar dispEstandar = tablaDE.get(pos);
		return dispEstandar;
	}
	
	public int encontrarPosEnTablaDE(String t,String d) {
		int pos = -1;
		for(int i = 0;i<tablaDE.size();i++) {
			Dispositivo act = tablaDE.get(i);
			if(act.getNombreDisp().equals(t)&&act.getEquipoConcreto().equals(d)) {
				pos = i;
			}
		}
		return pos;
	}
	
	public static boolean cumpleCondInteligente(String tipo,String descrip) {
		boolean result = false;
		switch(tipo) {
		case("Aire Acondicionado"):
			result = true;
			break;
		case("Televisor"):
			result = (descrip.contains("LED")) ? true : false;
			break;
		case("Heladera"):
			result = true;
			break;
		case("Lavarropas"):
			result = (descrip.equals("Automatico de 5 kg")) ? true : false;
			break;
		case("Ventilador"):
			result = (descrip.equals("De techo")) ? true : false;
			break;
		case("Lampara"):
			result = true;
			break;
		case("PC"):
			result = true;
			break;
		case("Microondas"):
			result = false;
			break;
		case("Plancha"):
			result = false;
			break;
		default:
			result = false;
			break;
		}
		return result;
	}

	public static void main(String[] args) {
		
		//Les dejo el main para que vean mas o menos como funciona por si esta confuso
		
		DeviceFactory factory = new DeviceFactory();
		int i;
		
		//De nuestra tabla de dispositivos precargados:
		System.out.println("Cant de disp estandar: "+ tablaDE.size());
		for(i = 0;i<tablaDE.size();i++) {
			System.out.println(tablaDE.get(i).getNombreDisp() + " " + tablaDE.get(i).getEquipoConcreto());
		}
		System.out.println("\nCant de disp inteligentes: " + tablaDI.size());
		for(i = 0;i<tablaDI.size();i++) {
			System.out.println(tablaDI.get(i).getNombreDisp() + " " + tablaDI.get(i).getEquipoConcreto());
		}
		
		DispositivoInteligente aire = (DispositivoInteligente) factory.crearDisp("Aire Acondicionado","3500 frigorias");
		DispositivoEstandar plancha = (DispositivoEstandar) factory.crearDisp("Plancha","A vapor");
		System.out.println("\n" + aire.getNombreDisp() + " " + aire.getEquipoConcreto() + ". ¿Es de bajo consumo?: " + aire.getEsBajoConsumo());
		System.out.println(plancha.getNombreDisp() + " " + plancha.getEquipoConcreto() + ". ¿Es de bajo consumo?: " + plancha.getEsBajoConsumo());
		
		List<Dispositivo> list = new ArrayList<>();
		list.add(aire);list.add(plancha);
		
		Cliente pepe = new Cliente("Pepe","Argento","pepe123","wtf",2018,8,20,TipoDocumento.DNI,"40111222","4789456","Avenida Medrano 953",list);
		System.out.println("\n" +pepe.getNombre() + " " + pepe.getApellido());
		Transformador t = pepe.getTransformadorActual();
		System.out.println("Zona del transformador asignado a Pepe: " + t.getZona());
		
		System.out.println("Sus dispositivos:");
		for(int e=0;e<2;e++) {
			System.out.println(pepe.getDispositivos().get(e).getNombreDisp());
		}
	}
}