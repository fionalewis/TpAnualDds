package modelo.deviceState;

import modelo.devices.DispositivoInteligente;

public class Encendido implements EstadoDispositivo {
	
	@Override
	public EstadoDispositivo getEstado() {
		return this;
	}
	
	@Override
	public String darEstado() {
		return "Encendido";
	}

	@Override
	public void apagar(DispositivoInteligente disp) {
		System.out.println("El dispositivo ha sido apagado.");
		disp.setEstadoDisp(new Apagado());
	}

	@Override
	public void encender(DispositivoInteligente disp) {
		System.out.println("El dispositivo contin�a en modo encendido.");
	}

	@Override
	public void ahorroEnergia(DispositivoInteligente disp) {
		System.out.println("El dispositivo ha cambiado a modo ahorro de energ�a.");
		disp.setEstadoDisp(new AhorroEnergia());
	}

	@Override
	public boolean estaEncendido() {
		System.out.println("El dispositivo se encuentra encendido en modo est�ndar.");
		return true;
	}

	@Override
	public boolean estaApagado() {
		System.out.println("El dispositivo no est� apagado.");
		return false;
	}

	@Override
	public boolean estaEnAhorro() {
		System.out.println("El dispositivo no se encuentra en modo ahorro de energ�a.");
		return false;
	}

}