package tpAnual.devices;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import tpAnual.deviceState.Encendido;
import tpAnual.deviceState.EstadoDispositivo;

public class DispositivoInteligente extends Dispositivo {
	
	public double kWhAhorro;
	public String fabricante; //seguro con enum
	private List<Sensor> sensores = new ArrayList<>();
	
	private EstadoDispositivo estadoDisp;
	private double temperatura;
	private double movimiento; //0 o 1
	private double luz; // en unidad de intensidad luminica
	private double humedad; // en %
	
	
	
	//Constructor default
	public DispositivoInteligente(String nombDisp,double kWh,double kWhAhorro,String fabricante) {
		setNombreDisp(nombDisp);
		setkWh(kWh);
		setEstadoDisp(new Encendido());
		setFechaRegistro(LocalDateTime.now());
		setkWhAhorro(kWhAhorro);
		setFabricante(fabricante);
	}
	
	//Constructor para los tests
	public DispositivoInteligente(String nombDisp,double kWh,int year,int month,int day,int hour,int min,int sec,
									double kWhAhorro,String fabricante) {
		setNombreDisp(nombDisp);
		setkWh(kWh);
		setEstadoDisp(new Encendido());
		setFechaRegistro(LocalDateTime.of(year,month,day,hour,min,sec));
		setkWhAhorro(kWhAhorro);
		setFabricante(fabricante);
	}
	
	//Constructor para la conversion
	public DispositivoInteligente() {
		setEstadoDisp(new Encendido());
		setFechaRegistro(LocalDateTime.now());
	}

	//Getters y Setters
	
	public double getkWhAhorro() {
		return kWhAhorro;
	}
	public void setkWhAhorro(double kWhAhorro) {
		this.kWhAhorro = kWhAhorro;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public EstadoDispositivo getEstadoDisp() {
		return estadoDisp;
	}
	public void setEstadoDisp(EstadoDispositivo estadoDisp) {
		this.estadoDisp = estadoDisp;
	}
	public void setTemperatura(double unaTemp) {
		this.temperatura = unaTemp;
	}
	public double getTemperatura() {
		return temperatura;
	}
	public void setMovimiento(double hayMovimiento) {
		this.movimiento = hayMovimiento;
	}
	public double getMovimiento() {
		return movimiento;
	}
	public void setLuz(double luz) {
		this.luz = luz;
	}
	public double getLuz() {
		return luz;
	}
	public void setHumedad(double humedad) {
		this.humedad = humedad;
	}
	public double getHumedad() {
		return humedad;
	}
	
	//Sensores	

	public List<Sensor> getSensores() {
		return sensores;
	}
	public void setSensores(List<Sensor> sensores) {
		this.sensores = sensores;
	}
	
	//Funcionalidades
		
	@Override public double consumoTotal() {
		horasDeUso = calculoDeHoras();
		return horasDeUso*kWh;
	}
	
	public double calculoDeHoras() {
		LocalDateTime currentDate = LocalDateTime.now();
	    Duration period = Duration.between(fechaRegistro,currentDate);
	    double periodSeconds = period.getSeconds();
	    horasDeUso = periodSeconds/3600;
	    return horasDeUso;
	}
		
	//Duplicado para los tests
		
	@Override public double consumoTotal(LocalDateTime fechaFin) {
		horasDeUso = calculoDeHoras(fechaFin);
		return horasDeUso*kWh;
	}
	
	public double calculoDeHoras(LocalDateTime fechaFin) {
	    Duration period = Duration.between(fechaRegistro,fechaFin);
	    double periodSeconds = period.getSeconds();
	    horasDeUso = periodSeconds/3600;
	    return horasDeUso;
	}
	
	//Funcionalidades Entrega1
	
	public void apagar() {
		estadoDisp.apagar(this);
	}
	
	public void encender(){
		estadoDisp.encender(this);
	}
	
	public void ahorroEnergia(){
		estadoDisp.ahorroEnergia(this);
	}
	
	public boolean estaEncendido(){
		return estadoDisp.estaEncendido();
	}
	
	public boolean estaApagado(){
		return estadoDisp.estaApagado();
	}
	
	public double consumoEnUltimasHoras(int horas){
		return estadoDisp.consumoEnUltimasHoras(horas, this);
	}
	
	public double consumoTotal(LocalDateTime fechaInicio,LocalDateTime fechaFin){ //Consumo entre dos fechas
		return estadoDisp.consumoTotal(fechaInicio,fechaFin,this);		
	}

}
