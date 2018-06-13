package modelo.devices;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.bytecode.Descriptor.Iterator;
import modelo.deviceState.Apagado;
import modelo.deviceState.Encendido;
import modelo.deviceState.EstadoDispositivo;
import modelo.devices.IntervaloDispositivo.modo;

public class DispositivoInteligente extends Dispositivo {
	
	public double kWhAhorro;
	private EstadoDispositivo estadoDisp;
	Map<String, Sensor> sensores = new HashMap<String, Sensor>();
	private IntervaloDispositivo unIntervalo;
	
	private List<IntervaloDispositivo> intervalos = new ArrayList<>();
	
	//Constructor default
	public DispositivoInteligente(String nombDisp,double kWh) {
		setNombreDisp(nombDisp);
		setkWh(kWh);
		setEstadoDisp(new Encendido());
		setFechaRegistro(LocalDateTime.now());
		setkWhAhorro(kWh);
		setIntervalo(new IntervaloDispositivo(getFechaRegistro(),modo.NORMAL));
		
	}
	
	//Constructor para los tests
	public DispositivoInteligente(String nombDisp,double kWh,int year,int month,int day,int hour,int min,int sec) {
		setNombreDisp(nombDisp);
		setkWh(kWh);
		setEstadoDisp(new Encendido());
		setFechaRegistro(LocalDateTime.of(year,month,day,hour,min,sec));
		setkWhAhorro(kWh);
		setIntervalo(new IntervaloDispositivo(getFechaRegistro(),modo.NORMAL));

	}
	
	//Constructor para la conversion
	public DispositivoInteligente() {
		setEstadoDisp(new Encendido());
		setFechaRegistro(LocalDateTime.now());
		setIntervalo(new IntervaloDispositivo(getFechaRegistro(),modo.NORMAL));
	}

	//Getters y Setters
	
	public double getkWhAhorro() {
		return kWhAhorro;
	}
	public void setkWhAhorro(double kWh) {
		this.kWhAhorro = kWh*0.8;
	}
	
	@Override
	public EstadoDispositivo getEstadoDisp() {
		return estadoDisp;
	}
	public void setEstadoDisp(EstadoDispositivo estadoDisp) {
		this.estadoDisp = estadoDisp;
	}
	
	//Intervalos
	
	public IntervaloDispositivo getIntervalo() {
		return unIntervalo;
	}
	public void setIntervalo(IntervaloDispositivo intervalo) {
		this.unIntervalo = intervalo;
	}
	public List<IntervaloDispositivo> getIntervalos() {
		return intervalos;
	}
	public void setIntervalos(List<IntervaloDispositivo> intervalos) {
		this.intervalos = intervalos;
	}

	//Sensores	

	public Map<String, Sensor> getSensores() {
		return sensores;
	}
	public void setSensores(Map<String, Sensor> sensores) {
		this.sensores = sensores;
	}
	public void agregarSensor(Sensor unSensor){
		sensores.put(unSensor.getNombreMagnitud(),unSensor);
	}
	public void quitarSensor(Sensor unSensor){
		sensores.remove(unSensor.getNombreMagnitud(),unSensor);
	}
	public void aumentarIntensidadSensor(String nombreSensor, double valor){
		Sensor sen = getSensoresConNombre(nombreSensor);
		sen.aumentarMagnitud(valor);
	}
	public void disminuirIntensidadSensor(String nombreSensor, double valor){
		Sensor sen = getSensoresConNombre(nombreSensor);
		sen.disminuirMagnitud(valor);
	}
	public void setMagnitudSensor(String nombreSensor, double valor){
		Sensor sen = getSensoresConNombre(nombreSensor);
		sen.setMagnitud(valor);
	}
	public double getMagnitudSensor(String nombreSensor){
		Sensor sen = getSensoresConNombre(nombreSensor);
		return sen.getMagnitud();
	}
	public Sensor getSensoresConNombre(String nombreSensor){
		return this.sensores.get(nombreSensor);
	}
	
	public String stringEstado() {
		return estadoDisp.darEstado();	
	}
	
	//Funcionalidades
	
	// Funcionalidades Entrega1
	
	public double horasDeUsoTotales() {
		horasDeUso = intervalos.stream().mapToDouble(unInt -> unInt.calculoDeHoras()).sum();
		return horasDeUso;
	}
	public double horasDeUsoTotales(List<IntervaloDispositivo> l) {
		horasDeUso = l.stream().mapToDouble(unInt -> unInt.calculoDeHoras()).sum();
		return horasDeUso;
	}
	
	@Override
	public double consumoTotal() {
		return intervalos.stream().mapToDouble(unInt -> consumoParcial(unInt)).sum();
	}
	
	public double consumoTotalD(List<IntervaloDispositivo> l) {
		return l.stream().mapToDouble(unInt -> consumoParcial(unInt)).sum();
	}
	
	public double consumoParcial(IntervaloDispositivo unInt) {
		double consumoParcial = 0;
		if(unInt.getModo() == modo.NORMAL) {
			consumoParcial = unInt.calculoDeHoras()*kWh;
		} else if(unInt.getModo()== modo.AHORRO) {
			consumoParcial = unInt.calculoDeHoras()*getkWhAhorro();
		}
		return consumoParcial;
	}
	
	// Ordenes basicas al dispositivo (habria que ver como combinar esto con los execute del actuador¿¿¿)
	
	public void guardarAuxiliar() { //Para que se guarden los intervalos en la lista
		IntervaloDispositivo aux = new IntervaloDispositivo();
		aux.setInicio(unIntervalo.getInicio());
		aux.setFin(unIntervalo.getFin());
		aux.setModo(unIntervalo.getModo());
		intervalos.add(aux);		
	}
	
	public void apagar() {
		unIntervalo.setFin(LocalDateTime.now());
		guardarAuxiliar();
		estadoDisp.apagar(this);
	}
	
	public void encender(){
		if(estadoDisp instanceof Apagado) {
			unIntervalo.setInicio(LocalDateTime.now());
			unIntervalo.setModo(modo.NORMAL);
		} else {
			unIntervalo.setFin(LocalDateTime.now());
			guardarAuxiliar();
			unIntervalo.setInicio(LocalDateTime.now().plusSeconds(1));
			unIntervalo.setModo(modo.NORMAL);
		}
		estadoDisp.encender(this);		
	}
	
	public void ahorroEnergia(){
		if(estadoDisp instanceof Apagado) {
			unIntervalo.setInicio(LocalDateTime.now());
			unIntervalo.setModo(modo.AHORRO);
		} else {
			unIntervalo.setFin(LocalDateTime.now());
			guardarAuxiliar();
			unIntervalo.setInicio(LocalDateTime.now().plusSeconds(1));
			unIntervalo.setModo(modo.AHORRO);
		}
		estadoDisp.ahorroEnergia(this);
	}
	
	// Duplicados para poder hacer los tests con fechas especificas/mas faciles de calcular
	
	public void apagar(LocalDateTime unafecha) {
		unIntervalo.setFin(unafecha);
		guardarAuxiliar();
		estadoDisp.apagar(this);
	}
	
	public void encender(LocalDateTime unafecha){
		if(estadoDisp instanceof Apagado) {
			unIntervalo.setInicio(unafecha);
			unIntervalo.setModo(modo.NORMAL);
		} else {
			unIntervalo.setFin(unafecha);
			guardarAuxiliar();
			unIntervalo.setInicio(unafecha.plusSeconds(1));
			unIntervalo.setModo(modo.NORMAL);
		}
		estadoDisp.encender(this);		
	}
	
	public void ahorroEnergia(LocalDateTime unafecha){
		if(estadoDisp instanceof Apagado) {
			unIntervalo.setInicio(unafecha);
			unIntervalo.setModo(modo.AHORRO);
		}
		else {
			unIntervalo.setFin(unafecha);
			guardarAuxiliar();
			unIntervalo.setInicio(unafecha.plusSeconds(1));
			unIntervalo.setModo(modo.AHORRO);
		}
		estadoDisp.ahorroEnergia(this);
	}
	
	// Metodos de consultas
	
	public boolean estaEncendido(){
		return estadoDisp.estaEncendido();
	}
	
	public boolean estaApagado(){
		return estadoDisp.estaApagado();
	}
	
	public boolean estaEnAhorro(){
		return estadoDisp.estaEncendido() && estadoDisp.estaEnAhorro();
	}
	
	/*public double consumoEnUltimasHoras(int horas){
		return estadoDisp.consumoEnUltimasHoras(horas, this);
	}*/
	
	public double consumoTotalEntre(LocalDateTime fechaInicio,LocalDateTime fechaFin){
		int inicial = posicionInicial(fechaInicio);
		int fin = posicionFinal(fechaFin);
		if(inicial == -1 || fin == -1) {
			if(inicial == -1) {
				System.out.println("Fecha inicial no válida.");
			}
			System.out.println("Fecha final no válida.");
			return 0;
		}
		IntervaloDispositivo i = new IntervaloDispositivo();
		i.setInicio(fechaInicio);i.setFin(intervalos.get(inicial).getFin());
		i.setModo(intervalos.get(inicial).getModo());
		IntervaloDispositivo f = new IntervaloDispositivo();
		f.setFin(fechaFin);f.setInicio(intervalos.get(fin).getInicio());
		f.setModo(intervalos.get(fin).getModo());
		List<IntervaloDispositivo> ints = new ArrayList<>();
		ints.add(i);
		for(int x=inicial+1;x<=fin-1;x++) {
			ints.add(intervalos.get(x));
		}
		ints.add(f);
		return consumoTotalD(ints);	
	}
	
	public int posicionInicial(LocalDateTime unaFechaI) {
		List<LocalDateTime> fInicios = new ArrayList<>();
		intervalos.stream().forEach(unInt-> fInicios.add(unInt.getInicio()));
 		int i = 0, tam = intervalos.size();
		LocalDateTime[] fechas = fInicios.toArray(new LocalDateTime[tam]);
		//Casos extremos: fechaI es antes que todas las fechasI o fechaI despues de todas
		LocalDateTime primerFI = intervalos.get(0).getInicio();
		LocalDateTime ultFI = intervalos.get(tam-1).getInicio();
		LocalDateTime ultFF = intervalos.get(tam-1).getFin();
		if(unaFechaI.isAfter(ultFI)){
			if(unaFechaI.isAfter(ultFF)) {
				return -1;
			} else {
				return tam-1;
			}
		}
		if(unaFechaI.isBefore(primerFI)||unaFechaI.isEqual(primerFI)) {
			return 0;
		}		
		while(i<tam &&  !(unaFechaI.isBefore(fechas[i])||unaFechaI.isEqual(fechas[i]))) {
			i++;
		}
		if(unaFechaI.isBefore(fechas[i])) {
			return i-1;
		}
		if(unaFechaI.isEqual(fechas[i])) {
					return i;
		}
		return -1; //Si llego hasta aca y no evaluo es porque tambien hubo algun error
	}
	
	public int posicionFinal(LocalDateTime unaFechaF) {
		List<LocalDateTime> fInicios = new ArrayList<>();
		intervalos.stream().forEach(unInt-> fInicios.add(unInt.getInicio()));
 		int i = 0, tam = intervalos.size();
		LocalDateTime[] fechas = fInicios.toArray(new LocalDateTime[tam]);
		//Casos extremos: fechaF ocurre antes que cualquier fecha inicial
		LocalDateTime primerFI = intervalos.get(0).getInicio();
		LocalDateTime primerFF = intervalos.get(0).getFin();
		LocalDateTime ultFF = intervalos.get(tam-1).getFin();
		if(unaFechaF.isBefore(primerFF)||unaFechaF.isEqual(primerFF)){
			if(unaFechaF.isBefore(primerFI)||unaFechaF.isEqual(primerFI)) { // no habria nada que calcular en estos casos
				return -1;
			}
			return 0;
		}
		if(unaFechaF.isAfter(ultFF)) {
			return tam-1;
		}		
		while(i<tam && !(unaFechaF.isBefore(fechas[i])||unaFechaF.isEqual(fechas[i]))) {
			i++;
		}
		if(unaFechaF.isEqual(fechas[i])) {
			return i;
		}
		if(unaFechaF.isBefore(fechas[i])) {
			return i-1;
		}
		return -1; //Si llego hasta aca y no evaluo es porque tambien hubo algun error
	}
	
		/*public double consumoEnUltimasHoras(int horas) {
			LocalDateTime ahora = LocalDateTime.now();
			LocalDateTime inicio
			return calcularConsumoEntre(fechaI,fechaF);
		}*/

}