package tpAnual;

import java.time.LocalDateTime;
//import static java.util.concurrent.TimeUnit.NANOSECONDS;
import java.util.concurrent.TimeUnit;

public class Dispositivo {
	
	private String nombreDisp;
	private double kWh;
	private boolean estadoDisp = false;
	private LocalDateTime fechaRegistro;
	private long startTime;
	private double horasDeUso = 0;
	
	public Dispositivo(String nombDisp,double kWh) {
		this.nombreDisp = nombDisp;
		this.kWh = kWh;
		this.estadoDisp = true;
		
		//lo mismo q la fecha de cliente
		this.fechaRegistro = LocalDateTime.now();
		this.startTime = System.nanoTime();		
	}
	
	//Getters y Setters
	
	public String getNombreDisp() {
		return nombreDisp;
	}
	public void setNombreDisp(String nombreDisp) {
		this.nombreDisp = nombreDisp;
	}
	public double getkWh() {
		return kWh;
	}
	public void setkWh(float kWh) {
		this.kWh = kWh;
	}
	public boolean isEstadoDisp() {
		return estadoDisp;
	}
	public void setEstadoDisp(boolean estadoDisp) {
		this.estadoDisp = estadoDisp;
	}
	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}
	public long startTime() {
		return startTime;
	}
	
	//Funcionalidades

	/* A futuro seguro va a hacer falta redefinir esto con un do while según el estado del dispositivo 
	 y/o rediseñar toda la clase con del state pattern o algo */
	public long calculoHoras() {
		long currentTime = System.nanoTime();
		long elapsedTime = currentTime - startTime;
		long currentHours = TimeUnit.NANOSECONDS.toHours(elapsedTime);
		return currentHours;
	}
	
	public double consumoActual() {
		this.horasDeUso = this.calculoHoras();
		double consumoActual = horasDeUso*kWh;
		return consumoActual;	
	}

}