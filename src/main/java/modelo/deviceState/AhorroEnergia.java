package modelo.deviceState;

import modelo.devices.DispositivoInteligente;

public class AhorroEnergia implements EstadoDispositivo {
	
	@Override
	public EstadoDispositivo getEstado() {
		return this;
	}
	
	@Override
	public String darEstado() {
		return "El dispositivo se encuentra en modo ahorro de energ�a.";
	}

	@Override
	public void apagar(DispositivoInteligente disp) {
		System.out.println("El dispositivo ha sido apagado.");
		disp.setEstadoDisp(new Apagado());
	}

	@Override
	public void encender(DispositivoInteligente disp) {
		System.out.println("El dispositivo ha cambiado a modo encendido.");
		disp.setEstadoDisp(new Encendido());
	}

	@Override
	public void ahorroEnergia(DispositivoInteligente disp) {
		System.out.println("El dispositivo contin�a en modo ahorro de energ�a.");
	}

	@Override
	public boolean estaEncendido() {
		System.out.println("El dispositivo se encuentra encendido en modo ahorro de energ�a.");
		return true;
	}

	@Override
	public boolean estaApagado() {
		System.out.println("El dispositivo no est� apagado.");
		return false;
	}

	@Override
	public boolean estaEnAhorro() {
		System.out.println("El dispositivo se encuentra encendido en modo ahorro de energ�a.");
		return true;
	}

}