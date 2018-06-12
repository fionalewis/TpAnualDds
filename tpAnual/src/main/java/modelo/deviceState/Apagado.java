package modelo.deviceState;

import java.time.LocalDateTime;

import modelo.devices.DispositivoInteligente;

public class Apagado implements EstadoDispositivo{
	
	@Override
	public EstadoDispositivo getEstado() {
		return this;
	}
	
	@Override
	public String darEstado() {
		return "El dispositivo se encuentra apagado.";
	}

	@Override
	public void apagar(DispositivoInteligente disp) {
		System.out.println("El dispositivo continúa apagado.");
	}

	@Override
	public void encender(DispositivoInteligente disp) {
		System.out.println("El dispositivo ha cambiado a modo encendido.");
		disp.setEstadoDisp(new Encendido());
	}

	@Override
	public void ahorroEnergia(DispositivoInteligente disp) {
		System.out.println("El dispositivo ha cambiado a modo ahorro de energia.");
		disp.setEstadoDisp(new AhorroEnergia());
	}

	@Override
	public boolean estaEncendido() {
		System.out.println("El dispositivo no se encuentra encendido.");
		return false;
	}

	@Override
	public boolean estaApagado() {
		System.out.println("El dispositivo está apagado.");
		return true;
	}
	
	@Override
	public boolean estaEnAhorro() {
		System.out.println("El dispositivo no se encuentra en modo ahorro de energía.");
		return false;
	}

	@Override
	public double consumoEnUltimasHoras(int horas,DispositivoInteligente disp) {
		System.out.println("El dispositivo está apagado. No es posible realizar la consulta.");
		return 0;
	}

	/*@Override
	public double consumoTotal(LocalDateTime fechaInicio, LocalDateTime fechaFin,DispositivoInteligente disp) {
		System.out.println("El dispositivo está apagado. No es posible realizar la consulta.");
		return 0;
	}*/
	
}