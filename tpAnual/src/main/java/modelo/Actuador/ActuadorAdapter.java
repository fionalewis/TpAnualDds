package modelo.Actuador;

import modelo.devices.Dispositivo;
import modelo.devices.DispositivoInteligente;

public class ActuadorAdapter {
	
	public void execute(int idFab, DispositivoInteligente dispo, String unaOrden){
		
		switch(unaOrden){
			case "APAGAR": dispo.apagar();
				break;
			case "ENCENDER": dispo.encender();
				break;
			case "AHORRO": dispo.ahorroEnergia();
				break;
		}
				
		System.out.println("Soy el adapter del actuador con id fabricante: " + idFab 
		+ "\n el actuador mando la orden: " + unaOrden 
		+ "\n Para el dispositivo:" + dispo.getNombreDisp());
	}
	
	
}
