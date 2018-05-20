package tpAnual.devices;

import java.time.LocalDateTime;

public abstract class Dispositivo {

	protected String nombreDisp;
	protected double kWh;
	protected LocalDateTime fechaRegistro;
	protected double horasDeUso = 0;
	
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
	public void setkWh(double kWh) {
		this.kWh = kWh;
	}
	public double getHorasDeUso() {
		return horasDeUso;
	}
	public void setHorasDeUso(double horasDeUso) {
		this.horasDeUso = horasDeUso;
	}
	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(int y,int m,int d,int hour,int min,int sec) {
		this.fechaRegistro = LocalDateTime.of(y,m,d,hour,min,sec);
	}
	public void setFechaRegistro(LocalDateTime fecha) {
		this.fechaRegistro = fecha;
	}

	//Funcionalidades
	
	public abstract double consumoTotal();
	
	//Duplicado para los tests
	
	public abstract double consumoTotal(LocalDateTime fechaFin);
	
}