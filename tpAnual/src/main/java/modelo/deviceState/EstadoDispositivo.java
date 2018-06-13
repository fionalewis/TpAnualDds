package modelo.deviceState;

import modelo.devices.DispositivoInteligente;

public interface EstadoDispositivo {
	
	public void apagar(DispositivoInteligente disp);
	public void encender(DispositivoInteligente disp);
	public void ahorroEnergia(DispositivoInteligente disp);
	public boolean estaEncendido();
	public boolean estaApagado();
	public boolean estaEnAhorro();
	//public double consumoEnUltimasHoras(int horas,DispositivoInteligente disp);
	public EstadoDispositivo getEstado();
	public String darEstado();
}