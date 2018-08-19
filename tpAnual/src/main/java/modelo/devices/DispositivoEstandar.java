package modelo.devices;

import java.time.Period;
import java.time.LocalDate;
import java.time.LocalDateTime;
import modelo.deviceState.EstadoDispositivo;

public class DispositivoEstandar extends Dispositivo {
	
	public int horasUsoDiarias = 0;
	
	//Constructor default
		public DispositivoEstandar(tipoDispositivo tipo,String descrip,double kWhAprox,int horasDiarias,double horasMin,double horasMax,boolean bajoConsumo) {
			setTipoDisp(tipo);
			setDescrip(descrip);
			setkWh(kWhAprox);
			setFechaRegistro(LocalDateTime.now());
			setHorasUsoDiarias(horasDiarias);
			setHorasUsoMin(horasMin);
			setHorasUsoMax(horasMax);
			setEsInteligente(false);
			setEsBajoConsumo(bajoConsumo);	
		}
		
	//Constructor para los tests (con fecha y hora especifica)
		public DispositivoEstandar(tipoDispositivo tipo,String descrip,double kWhAprox,int y,int m,int d,int h,int min,int s,int horasUsoDiarias,
									int horasMin,int horasMax,boolean bajoConsumo) {
			setTipoDisp(tipo);
			setDescrip(descrip);
			setkWh(kWhAprox);
			setFechaRegistro(LocalDateTime.of(y,m,d,h,min,s));
			setHorasUsoDiarias(horasUsoDiarias);
			setHorasUsoMin(horasMin);
			setHorasUsoMax(horasMax);
			setEsInteligente(false);
			setEsBajoConsumo(bajoConsumo);	
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