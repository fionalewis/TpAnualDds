package modelo.Actuador;

import modelo.devices.Dispositivo;

public class ActuadorAdapter {
	
	public void execute(int idFab, Dispositivo dispo, String unaOrden){
		System.out.println("Soy el adapter del actuador con id fabricante: " + idFab 
		+ "\n el actuador mando la orden: " + unaOrden 
		+ "\n Para el dispositivo:" + dispo.getTipoDisp().toString());
	}
}
