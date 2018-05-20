package tpAnual.deviceState;

import java.time.LocalDateTime;

import tpAnual.devices.DispositivoInteligente;

public class Apagado implements EstadoDispositivo{

	@Override
	public void apagar(DispositivoInteligente disp) {}

	@Override
	public void encender(DispositivoInteligente disp) {
		disp.setEstadoDisp(new Encendido());
	}

	@Override
	public void ahorroEnergia(DispositivoInteligente disp) {
		disp.setEstadoDisp(new AhorroEnergia());
	}

	@Override
	public boolean estaEncendido() {
		return !estaApagado();
	}

	@Override
	public boolean estaApagado() {
		return true;
	}

	@Override
	public double consumoEnUltimasHoras(int horas,DispositivoInteligente disp) {
		System.out.println("El dispositivo está apagado. No es posible realizar la consulta.");
		return 0;
	}

	@Override
	public double consumoTotal(LocalDateTime fechaInicio, LocalDateTime fechaFin,DispositivoInteligente disp) {
		System.out.println("El dispositivo está apagado. No es posible realizar la consulta.");
		return 0;
	}
	
}