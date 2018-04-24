package tpAnual;

import java.time.LocalDateTime;
import java.time.Duration;

public class Dispositivo {
	
	private String nombreDisp;
	private double kWh;
	private boolean estadoDisp = false;
	private LocalDateTime fechaRegistro;
	private double horasDeUso = 0;
	
	public Dispositivo() {}
	
	//Constructor por default
	public Dispositivo(String nombDisp,double kWhora) {
		nombreDisp = nombDisp;
		kWh = kWhora;
		estadoDisp = true;
		fechaRegistro = LocalDateTime.now();	
	}
	
	//Constructor para los test (por si hace falta)
	public Dispositivo(String nombDisp,double kWh,int year,int month,int day,int hour,int min,int sec) {
		setNombreDisp(nombDisp);
		setkWh(kWh);
		setEstadoDisp(true);
		this.fechaRegistro = LocalDateTime.of(year,month,day,hour,min,sec); //Asi se puede elegir la fecha p/los tests
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
	public void setkWh(double kWh) {
		this.kWh = kWh;
	}
	public double getHorasDeUso() {
		return horasDeUso;
	}
	public void setHorasDeUso(double horasDeUso) {
		this.horasDeUso = horasDeUso;
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
	public void setFechaRegistro(int y,int m,int d,int hour,int min,int sec) {
		this.fechaRegistro = LocalDateTime.of(y,m,d,hour,min,sec);
	}
	
	//Funcionalidades

	/* A futuro seguro va a hacer falta redefinir esto con un do while segun el estado del dispositivo 
	 y/o rehacer con el state pattern o algo idk */
	
	public double calculoDeHoras() { //(LocalDateTime fechaInicio)
		LocalDateTime currentDate = LocalDateTime.now();
        Duration period = Duration.between(fechaRegistro,currentDate);
        double periodSeconds = period.getSeconds();
        horasDeUso = periodSeconds/3600;
        return horasDeUso;
	}
	
	public double consumoActual() {
		horasDeUso = calculoDeHoras();
		return horasDeUso*kWh;
	}
	
	public double calculoDeHoras(LocalDateTime fechaFin) {
        Duration period = Duration.between(fechaRegistro,fechaFin);
        double periodSeconds = period.getSeconds();
        horasDeUso = periodSeconds/3600;
        return horasDeUso;
	}
	
	public double consumoActual(LocalDateTime fechaFin) {
		horasDeUso = calculoDeHoras(fechaFin);
		return horasDeUso*kWh;	
	}
	
}