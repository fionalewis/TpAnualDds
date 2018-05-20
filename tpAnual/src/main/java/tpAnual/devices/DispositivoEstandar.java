package tpAnual.devices;

import java.time.Period;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DispositivoEstandar extends Dispositivo {
	
	public int horasUsoDiarias = 0;
	
	//Constructor default
		public DispositivoEstandar(String nombDisp,double kWhAprox,int horasUso) {
			setNombreDisp(nombDisp);
			setkWh(kWhAprox);
			setFechaRegistro(LocalDateTime.now());
			setHorasUsoDiarias(horasUso);
		}
		
	//Constructor para los tests
		public DispositivoEstandar(String nombDisp,double kWhAprox,int year,int month,int day,int hour,int min,int sec) {
			setNombreDisp(nombDisp);
			setkWh(kWhAprox);
			setFechaRegistro(LocalDateTime.of(year,month,day,hour,min,sec));
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
			horasDeUso = calculoDeHoras();
			return horasDeUso*kWh;
		}
		
		public double calculoDeHoras() {
			LocalDate currentDate = LocalDate.now();
	        Period period = Period.between(getFechaRegistro().toLocalDate(),currentDate);
	        int periodDays = period.getDays();
	        double horasDeUsoAprox = periodDays*horasUsoDiarias;
	        return horasDeUsoAprox;
		}

		@Override
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
	
}
