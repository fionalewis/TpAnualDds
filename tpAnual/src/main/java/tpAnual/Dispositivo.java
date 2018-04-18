package tpAnual;

import java.time.LocalDateTime;
import java.time.Duration;
//import java.text.*;

public class Dispositivo {
	
	private String nombreDisp;
	private double kWh;
	private boolean estadoDisp = false;
	private LocalDateTime fechaRegistro;
	private double horasDeUso = 0;
	
	//Constructor por default
	public Dispositivo(String nombDisp,double kWh) {
		this.nombreDisp = nombDisp;
		this.kWh = kWh;
		this.estadoDisp = true;
		this.fechaRegistro = LocalDateTime.now();	
	}
	
	//Opción de constructor para el json
	public Dispositivo(String nombDisp,double kWh,int year,int month,int day,int hour,int min,int sec) {
		setNombreDisp(nombDisp);
		setkWh(kWh);
		setEstadoDisp(true);
		this.fechaRegistro = LocalDateTime.of(year,month,day,hour,min,sec); //Así podemos elegir una fecha random por nuestra cuenta, y de paso se hace mas fácil para los tests
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

	/* A futuro seguro va a hacer falta redefinir esto con un do while según el estado del dispositivo 
	 y/o rediseñar toda la clase con del state pattern o algo */
	
    public double calculoDeHoras(LocalDateTime fechaInicio){
        LocalDateTime currentDate = LocalDateTime.now();
        Duration period = Duration.between(fechaRegistro,currentDate);
        double periodSeconds = period.getSeconds();
        this.horasDeUso = (periodSeconds/86400)*24;
        //No borren estas líneas comentadas porfa! Tengo un par de dudas de ese código que quiero poner y quiero consultarlas
        //DecimalFormat h = new DecimalFormat("#.#####");
        //double horasUso = Double.parseDouble(h.format(horasDeUso));
        int temp = (int)(horasDeUso*100.0);
        double horasUsoShort = ((double)temp)/100.0;
        return horasUsoShort;
	}
    
	public double consumoActual() {
		this.horasDeUso = this.calculoDeHoras(fechaRegistro);
		double consumoActual = horasDeUso*kWh;
		return consumoActual;	
	}
	
	//Para poder definir la fecha final en los tests
	
	public double calculoDeHoras(LocalDateTime fechaInicio,LocalDateTime fechaFinal){
	        LocalDateTime currentDate = fechaFinal;
	        Duration period = Duration.between(fechaRegistro,currentDate);
	        double periodSeconds = period.getSeconds();
	        this.horasDeUso = (periodSeconds/86400)*24;
	        int temp = (int)(horasDeUso*100.0);
	        double horasUsoShort = ((double)temp)/100.0;
	        return horasUsoShort;
		}

	public double consumoActual(LocalDateTime fechaRegistro,LocalDateTime fechaFin) {
		this.horasDeUso = this.calculoDeHoras(fechaRegistro,fechaFin);
		double consumoActual = horasDeUso*kWh;
		return consumoActual;
	}

}