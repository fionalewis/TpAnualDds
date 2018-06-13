package modelo.deviceState;

//import java.time.LocalDateTime;

import modelo.devices.DispositivoInteligente;

public class AhorroEnergia implements EstadoDispositivo {
	
	@Override
	public EstadoDispositivo getEstado() {
		return this;
	}
	
	@Override
	public String darEstado() {
		return "El dispositivo se encuentra en modo ahorro de energía.";
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
		System.out.println("El dispositivo continúa en modo ahorro de energía.");
	}

	@Override
	public boolean estaEncendido() {
		System.out.println("El dispositivo se encuentra encendido en modo ahorro de energía.");
		return true;
	}

	@Override
	public boolean estaApagado() {
		System.out.println("El dispositivo no está apagado.");
		return false;
	}

	@Override
	public boolean estaEnAhorro() {
		System.out.println("El dispositivo se encuentra encendido en modo ahorro de energía.");
		return true;
	}
	
	/*@Override
	public double consumoEnUltimasHoras(int horas,DispositivoInteligente disp) {
		return disp.getkWhAhorro()*horas;
	}
*/
}