package modelo;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import exceptions.ExceptionsHandler;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;

public class DispositivosRepository {
	
	String ruta = "C:\\Users\\Giuli\\eclipse-workspace\\TpAnualDds\\tpAnual\\JSONs";
	//String ruta = "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonDispositivos.json"
	List<Dispositivo> dispositivosExistentes = new ArrayList<Dispositivo>();
	
	public List<Dispositivo> getDispositivosExistentes(){
		return this.dispositivosExistentes;
	}
	
	public void importarDispoDeJson() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		List<DispositivoInteligente> disp = null;
		
		try {
			disp = DAOJson.deserializarLista(DispositivoInteligente.class,
					ruta.concat("\\jsonDispositivos.json"));
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
}
