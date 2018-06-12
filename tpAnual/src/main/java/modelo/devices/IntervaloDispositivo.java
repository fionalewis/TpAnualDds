package modelo.devices;

import java.time.Duration;
import java.time.LocalDateTime;

public class IntervaloDispositivo {
	
	public enum modo {AHORRO, NORMAL};
	
	private LocalDateTime inicio;
	private LocalDateTime fin;
	private modo modo;
	
	public IntervaloDispositivo() {}
	
	public IntervaloDispositivo(LocalDateTime inicioInt,modo mod) {
		setInicio(inicioInt);
		setModo(mod);
	}
	public IntervaloDispositivo(int y,int m,int d,int h,int min,int sec,modo mod) {
		setInicio(LocalDateTime.of(y,m,d,h,min,sec));
		setModo(mod);
	}

	public LocalDateTime getInicio() {
		return inicio;
	}
	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}
	public LocalDateTime getFin() {
		return fin;
	}
	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}
	public modo getModo() {
		return modo;
	}
	public void setModo(modo modo) {
		this.modo = modo;
	}
	
	public double calculoDeHoras() {
		Duration period = Duration.between(inicio,fin);
        double periodSeconds = period.getSeconds();
        double horasDeUso = periodSeconds/3600;
        return horasDeUso;
	}
	
	public double calculoDeHorasAhora() { // hasta la fecha
		LocalDateTime currentDate = LocalDateTime.now();
	    Duration period = Duration.between(inicio,currentDate);
	    double periodSeconds = period.getSeconds();
	    double horasDeUso = periodSeconds/3600;
	    return horasDeUso;
	}
	
	public double calculoDeHorasDesde(LocalDateTime fechaFin) { // desde un inicio
	    Duration period = Duration.between(inicio,fechaFin);
	    double periodSeconds = period.getSeconds();
	    double horasDeUso = periodSeconds/3600;
	    return horasDeUso;
	}
	
}