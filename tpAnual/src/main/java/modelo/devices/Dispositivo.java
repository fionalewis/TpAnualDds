package modelo.devices;

import java.time.LocalDateTime;
import modelo.deviceState.EstadoDispositivo;;

public abstract class Dispositivo {

	protected String nombreDisp;
	protected double kWh;
	protected LocalDateTime fechaRegistro;
	protected double horasDeUso = 0;
	protected double horasUsoMax = 0;
	protected double horasUsoMin = 0;
	public String equipoConcreto = null;
	
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
	public double getHorasUsoMax() {
		return horasUsoMax;
	}
	public void setHorasUsoMax(double horasDeUso) {
		this.horasUsoMax = horasDeUso;
	}
	public double getHorasUsoMin() {
		return horasUsoMin;
	}
	public void setHorasUsoMin(double horasDeUso) {
		this.horasUsoMin = horasDeUso;
	}
	public String getEquipoConcreto(){
		return equipoConcreto;
	}
	public void setEquipoConcreto(String descripcion){
		this.equipoConcreto = descripcion;
	}

	//Funcionalidades
	
	public abstract double consumoTotal();
	
	/* Los DI conocen la duracion exacta del tiempo que tienen funcionando, los DE solo
	 * conocen un aproximado de horas de uso diarias y la fecha de registro, por lo que
	 * calculan cuantos dias vienen funcionando aproximadamente y en base a eso sus horas */
	
	public abstract double horasDeUsoTotales();
	
	public abstract EstadoDispositivo getEstadoDisp(); //Solo para que no llore el cantDisp de cliente
	
	//Para los predicates (puede llegar a ser util mas adelante tambien)
	
	public static boolean esInteligente(Dispositivo disp) {
		return disp instanceof DispositivoInteligente;
	}
	
	public static boolean esEstandar(Dispositivo undisp) {
		return undisp instanceof DispositivoEstandar;
	}
	
	public static boolean esConvertido(Dispositivo undisp) {
		return undisp instanceof DispositivoConvertido;
	}
	
	public static boolean esAmbos(Dispositivo undisp) {
		return (esInteligente(undisp)||esConvertido(undisp));
	}
	
}