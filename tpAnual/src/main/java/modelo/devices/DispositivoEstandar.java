package modelo.devices;

import java.time.Period;
import java.time.LocalDate;
import java.time.LocalDateTime;
import modelo.deviceState.EstadoDispositivo;

public class DispositivoEstandar extends Dispositivo {
	
	public int horasUsoDiarias = 0;
	
	//Constructor default
		public DispositivoEstandar(String nombDisp,double kWhAprox,int horasUso,String descripcion, double horasUsoMax, double horasUsoMin) {
			setNombreDisp(nombDisp);
			setkWh(kWhAprox);
			setFechaRegistro(LocalDateTime.now());
			setHorasUsoDiarias(horasUso);
			setEquipoConcreto(descripcion);
			setHorasUsoMax(horasUsoMax);
			setHorasUsoMin(horasUsoMin);
			
		}
		
	//Constructor para los tests
		public DispositivoEstandar(String nombDisp,double kWhAprox,int year,int month,int day,int hour,int min,int sec,int horasUso) {
			setNombreDisp(nombDisp);
			setkWh(kWhAprox);
			setFechaRegistro(LocalDateTime.of(year,month,day,hour,min,sec));
			setHorasUsoDiarias(horasUso);
		}
		
	//Metodos basicos

		public int getHorasUsoDiarias() {
			return horasUsoDiarias;
		}

		public void setHorasUsoDiarias(int horasUsoDiarias) {
			this.horasUsoDiarias = horasUsoDiarias;
		}
		
		@Override
		public double consumoTotal() {
			horasDeUso = horasDeUsoTotales();
			return horasDeUso*kWh;
		}
		
		public double horasDeUsoTotales() {
			LocalDate currentDate = LocalDate.now();
	        Period period = Period.between(getFechaRegistro().toLocalDate(),currentDate);
	        int periodDays = period.getDays();
	        double horasDeUsoAprox = periodDays*horasUsoDiarias;
	        return horasDeUsoAprox;
		}

		//Todos estos repetidos son para testear
		
		public double consumoTotal(LocalDateTime fechaFin) {
			horasDeUso = calculoDeHoras(fechaFin);
			return horasDeUso*kWh;	
		}
		
		public double calculoDeHoras(LocalDateTime fechaFin) {
	        Period period = Period.between(getFechaRegistro().toLocalDate(),fechaFin.toLocalDate());
	        int periodDays = period.getDays();
	        double horasDeUsoAprox = periodDays*horasUsoDiarias;
	        return horasDeUsoAprox;
		}
		
		//Tengo que ver como hacer con esto sorry
		
		public EstadoDispositivo getEstadoDisp() {
			return null;
		}
	
}