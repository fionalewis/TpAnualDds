package tpAnual.deviceState;

import java.time.LocalDateTime;

import tpAnual.devices.DispositivoInteligente;

public interface EstadoDispositivo {
	
	public void apagar(DispositivoInteligente disp);
	public void encender(DispositivoInteligente disp);
	public void ahorroEnergia(DispositivoInteligente disp);
	public boolean estaEncendido();
	public boolean estaApagado();
	public double consumoEnUltimasHoras(int horas,DispositivoInteligente disp);
	public double consumoTotal(LocalDateTime fechaInicio,LocalDateTime fechaFin,DispositivoInteligente disp);

}