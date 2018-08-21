package modelo.devices;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import exceptions.ExceptionsHandler;
import modelo.DAOJson;
import modelo.JsonManager;

public class DispositivosRepository {
	
	/*	El metodo "importarDispoDeJson" llenara la lista "dispositivosExistentes"
	 * y este sera una lista de 'tipos' de dispositivos inteligentes
	 * cuando se quiere crear o instanciar un nuevo dispositivo de un tipo determinado
	 * se puede lograr llamando al metodo 'instanciarMetodo(unTipo)' pasandole el indice del tipo
	 * 
	 * Lista de tipos de dispositivos:
	 * 
	 * 0- Aire acondicionado, de 3500 frigor¨ªas, DI (Dispositivo Inteligente)
	 * 1- Aire acondicionado, de 2200 frigor¨ªas, DI
	 * 2- Televisor, Color de tubo fluorescente de 21¡±, DE
	 * 3- Televisor, Color de tubo fluorescente de 29¡± a 34", DE
	 * 4- Televisor, LCD de 40¡±, DE
	 * 5- Televisor, LED 24¡±, DI
	 * 6- Televisor, LED 32¡±, DI
	 * 7- Televisor, LED 40¡±, DI
	 * 8- Heladera, con freezer, DI //NO TIENE CONSUMO MAX Y MIN PORQUE NUNCA SE PUEDE APAGAR
	 * 9- Heladera, sin freezer, DI //NO TIENE CONSUMO MAX Y MIN PORQUE NUNCA SE PUEDE APAGAR
	 * 10- Lavarropas, automatico de 5kg con calentamiento de agua, DE
	 * 11- Lavarropas, automatico de 5kg, DI
	 * 12- Lavarropas, semi-automatico de 5kg, DE
	 * 13- Ventilador, de pie, DE
	 * 14- Ventilador, de techo, DI
	 * 15- Lampara, halogenas de 40W, DI
	 * 16- Lampara, halogenas de 60W, DI
	 * 17- Lampara, halogenas de 100W, DI
	 * 18- Lampara, de 11W, DI
	 * 19- Lampara de 15W, DI
	 * 20- Lampara de 20W, DI
	 * 21- PC, de escritorio, DI
	 * 22- Microondas, convencional, DE
	 * 23- Plancha, a vapor, DE
	 * */
	
	String ruta = "\\C:\\Users\\Marina\\workspace\\TpAnualDds\\tpAnual\\JSONs";
	//String ruta = "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonDispositivos.json"
	
	List<Dispositivo> dispositivosExistentes = new ArrayList<Dispositivo>();
	
	public List<Dispositivo> getDispositivosExistentes(){
		return this.dispositivosExistentes;
	}
	
	public void importarDispoDeJson() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		List<DispositivoInteligente> disp = null;
		
		try {
			disp = DAOJson.deserializarLista(DispositivoInteligente.class,
					JsonManager.rutaJsonDispSalo);
		} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
		disp.stream().forEach(d-> dispositivosExistentes.add(filtrarEInstanciarDispo(d)));
	}
	
	public Dispositivo filtrarEInstanciarDispo(DispositivoInteligente disp){
		if(!disp.getEsInteligente()){ 
			DispositivoEstandar dispo = new DispositivoEstandar(disp.getNombreDisp(),disp.getkWh(),0,
					disp.getEquipoConcreto(),disp.getHorasUsoMax(),disp.getHorasUsoMin());
			return dispo;
		} return disp;
	}
	
	//crear nuevos dispositivos del mismo tipo
	public Dispositivo crearDispositivoSegunTipo(int unTipo){
		Dispositivo unDisp = dispositivosExistentes.get(unTipo);
		return (Dispositivo) unDisp.crearDispositivo();
	}
	
	public int filtrarCantSegunTipo(List<Dispositivo> dispositivos,int unTipo){
		
		int counter = 0;
		String tipo = dispositivosExistentes.get(unTipo).getEquipoConcreto();
		
		for(int i=0; i<dispositivos.size();i++){
			if(dispositivos.get(i).getEquipoConcreto().equals(tipo)) counter++;
		}
		
		return counter;
	}
	
	
	public int filtrarCantTipos(List<Dispositivo> dispositivos){
		int counter = 1;
		int i = 1;
		String tipo;
		List<String> tipos = new ArrayList<>();
		tipos.add(dispositivos.get(0).getEquipoConcreto());
		
		while(i<dispositivos.size()){
			tipo = dispositivos.get(i).getEquipoConcreto();
			if(!tipos.contains(tipo)){ 
				counter++;
				tipos.add(tipo);
				i++;
			}else i++;
		}
		
		return counter;
		
	}
	
	public List<Dispositivo> filtrarRepresentatesDeTipos(List<Dispositivo> dispositivos){
		int i = 1;
		String tipo = null;
		List<String> tipos = new ArrayList<>();
		List<Dispositivo> listaRepresentantes = new ArrayList<Dispositivo>();
		
		tipos.add(dispositivos.get(0).getEquipoConcreto());
		listaRepresentantes.add(dispositivos.get(0));
		
		while(i<dispositivos.size()){
			tipo = dispositivos.get(i).getEquipoConcreto();
			if(!tipos.contains(tipo)){ 
				tipos.add(tipo);
				listaRepresentantes.add(dispositivos.get(i));
				i++;
			}else i++;
		}
		
		return listaRepresentantes;
	}
	
	public int getIndexPorDescripcion(String unaDescripcion){
		int i = 0;
		String descr = null;
		while(i<dispositivosExistentes.size()){
			descr = dispositivosExistentes.get(i).getEquipoConcreto();
			if(descr.equals(unaDescripcion)){
				return i;
			}
			else i++;
		}
		return -1;
	}
	
	public List<Integer> generarListaDeCantDeCadaTipo(List<Dispositivo> dispositivos){
		List<Integer> listaCantPorTipo = new ArrayList<Integer>();
		int i = 1; 
		int cant = 0;
		String tipo = null;
		List<String> tipos = new ArrayList<String>();
		tipo = dispositivos.get(0).getEquipoConcreto();
		tipos.add(dispositivos.get(0).getEquipoConcreto());
		cant = filtrarCantSegunTipo(dispositivos,getIndexPorDescripcion(tipo));
		listaCantPorTipo.add(cant);
		
		while(i<dispositivos.size()){
			tipo = dispositivos.get(i).getEquipoConcreto();
			if(!tipos.contains(tipo)){
				cant = filtrarCantSegunTipo(dispositivos,getIndexPorDescripcion(tipo));
				listaCantPorTipo.add(cant);
				tipos.add(dispositivos.get(0).getEquipoConcreto());
				i++;
			}else i++;
		}
		
		return listaCantPorTipo;
	}

}