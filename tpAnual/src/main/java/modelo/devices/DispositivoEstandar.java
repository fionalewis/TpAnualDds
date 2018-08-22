package modelo.devices;

import java.time.Period;
import java.time.LocalDate;
import java.time.LocalDateTime;
import modelo.deviceState.EstadoDispositivo;

public class DispositivoEstandar extends Dispositivo {
	
	public double horasUsoDiarias = 0;
	
	//Constructor default
		public DispositivoEstandar(String nombDisp,double kWhAprox,double horasDiarias,String descrip,double horasMin,double horasMax,boolean bajoConsumo) {
			setNombreDisp(nombDisp);
			setEquipoConcreto(descrip);
			setkWh(kWhAprox);
			setFechaRegistro(LocalDateTime.now());
			setHorasUsoDiarias(horasDiarias);
			setHorasUsoMin(horasMin);
			setHorasUsoMax(horasMax);
			setEsInteligente(false);
			setEsBajoConsumo(bajoConsumo);	
		}
		
	//Constructor para los tests (con fecha y hora especifica)
		public DispositivoEstandar(	String nomb,String descrip,double kWhAprox,int y,int m,int d,int h,int min,int s,int horasUsoDiarias,
									int horasMin,int horasMax,boolean bajoConsumo) {
			setNombreDisp(nomb);
			setEquipoConcreto(descrip);
			setkWh(kWhAprox);
			setFechaRegistro(LocalDateTime.of(y,m,d,h,min,s));
			setHorasUsoDiarias(horasUsoDiarias);
			setHorasUsoMin(horasMin);
			setHorasUsoMax(horasMax);
			setEsInteligente(false);
			setEsBajoConsumo(bajoConsumo);	
		}
		
		//Constructor del repository de Mari
		public DispositivoEstandar(String nombreDisp, double getkWh, int i, String equipoConcreto, double horasUsoMax,double horasUsoMin) {
			setNombreDisp(nombreDisp);
			setkWh(getkWh);
			setEquipoConcreto(equipoConcreto);
			setHorasUsoMax(horasUsoMax);
			setHorasUsoMin(horasUsoMin);
			setEsInteligente(false);
			setHorasUsoDiarias(0); //no olvidarse de setearlo despues si se usa, el bajo consumo tmb
		}
		
		//Otro
		
		public DispositivoEstandar(String tipo,String descrip,double kWhAprox,boolean esBajoCons) {
			setNombreDisp(tipo);
			setEquipoConcreto(descrip);
			setkWh(kWhAprox);
			setEsBajoConsumo(esBajoCons);
			setFechaRegistro(LocalDateTime.now());
			setHorasUsoDiarias(0); //hay que setearlo aparte despues
			setEsInteligente(false);
		}
		
		public DispositivoEstandar(){}
		
	//Metodos basicos

		public double getHorasUsoDiarias() {
			return horasUsoDiarias;
		}

		public void setHorasUsoDiarias(double horasUsoDiarias) {
			this.horasUsoDiarias = horasUsoDiarias;
		}
		
		@Override
		public double consumoTotal() {
			this.horasDeUso = horasDeUsoTotales();
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