package modelo.devices;

import java.time.LocalDateTime;
import modelo.deviceState.EstadoDispositivo;;

public abstract class Dispositivo {

	public enum tipoDispositivo {AireAcondicionado,Televisor,Heladera,
									Lavarropas,Ventilador,Lámpara,PC,Microondas,
									Plancha,TermotanqueEléctrico,HornoEléctrico}
	protected tipoDispositivo tipoDisp;
	public String descripcion = "";
	protected double kWh;
	protected LocalDateTime fechaRegistro;
	protected double horasDeUso = 0;
	protected double horasUsoMax = 0;
	protected double horasUsoMin = 0;
	boolean esInteligente;
	boolean esBajoConsumo;
	
	//Getters y Setters
	
	public tipoDispositivo getTipoDisp() {
		return tipoDisp;
	}
	public void setTipoDisp(tipoDispositivo tipoDisp) {
		this.tipoDisp = tipoDisp;
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
	public void setHorasUsoMax(double horasDeUsoMax) {
		if(getTipoDisp().equals(tipoDispositivo.Heladera)) {
			this.horasUsoMax = -1; // estos disp no cuentan a la hora de analizar el simplex, porque no se pueden apagar
		} else { this.horasUsoMax = horasDeUsoMax; }
	}
	public double getHorasUsoMin() {
		return horasUsoMin;
	}
	public void setHorasUsoMin(double horasDeUsoMin) {
		if(getTipoDisp().equals(tipoDispositivo.Heladera)) {
			this.horasUsoMin = -1; // estos disp no cuentan a la hora de analizar el simplex, porque no se pueden apagar
		} else { this.horasUsoMin = horasDeUsoMin; }
	}
	public String getDescrip(){
		return descripcion;
	}
	public void setDescrip(String descripcion){
		this.descripcion = descripcion;
	}

	public boolean getEsInteligente(){
		return esInteligente;
	}
	
	public void setEsInteligente(boolean ansSmart){
		this.esInteligente = ansSmart;
	}
	
	public boolean getEsBajoConsumo(){
		return esBajoConsumo;
	}
	
	public void setEsBajoConsumo(boolean ansBajoConsumo){
		this.esBajoConsumo = ansBajoConsumo;
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