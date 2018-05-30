package modelo.deviceState;

import java.time.Duration;
import java.time.LocalDateTime;

import modelo.devices.DispositivoInteligente;

public class AhorroEnergia implements EstadoDispositivo {

	@Override
	public void apagar(DispositivoInteligente disp) {
		disp.setEstadoDisp(new Apagado());
	}

	@Override
	public void encender(DispositivoInteligente disp) {
		disp.setEstadoDisp(new Encendido());
	}

	@Override
	public void ahorroEnergia(DispositivoInteligente disp) {}

	@Override
	public boolean estaEncendido() {
		return true;
	}

	@Override
	public boolean estaApagado() {
		return !estaEncendido();
	}

	@Override
	public boolean estaEnAhorro() {
		return true;
	}
	
	@Override
	public double consumoEnUltimasHoras(int horas,DispositivoInteligente disp) {
		return disp.getkWhAhorro()*horas;
	}

	@Override
	public double consumoTotal(LocalDateTime fechaInicio, LocalDateTime fechaFin,DispositivoInteligente disp) {
		double horasDeUso = calculoDeHoras(fechaInicio,fechaFin);
		return horasDeUso*(disp.getkWhAhorro());	
	}
	
	public double calculoDeHoras(LocalDateTime fechaInicio,LocalDateTime fechaFin) {
		Duration period = Duration.between(fechaInicio,fechaFin);
        double periodSeconds = period.getSeconds();
        double horasDeUso = periodSeconds/3600;
        return horasDeUso;
	}

}
