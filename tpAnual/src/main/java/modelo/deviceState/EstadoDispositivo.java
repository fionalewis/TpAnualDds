package modelo.deviceState;

import java.time.LocalDateTime;

import modelo.devices.DispositivoInteligente;

public interface EstadoDispositivo {
	
	public void apagar(DispositivoInteligente disp);
	public void encender(DispositivoInteligente disp);
	public void ahorroEnergia(DispositivoInteligente disp);
	public boolean estaEncendido();
	public boolean estaApagado();
	public boolean estaEnAhorro();
	public double consumoEnUltimasHoras(int horas,DispositivoInteligente disp);
	public double consumoTotal(LocalDateTime fechaInicio,LocalDateTime fechaFin,DispositivoInteligente disp);
	public String darEstado();
}