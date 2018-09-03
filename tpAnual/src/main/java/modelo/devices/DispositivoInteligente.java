package modelo.devices;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.deviceState.Apagado;
import modelo.deviceState.Encendido;
import modelo.deviceState.EstadoDispositivo;
import modelo.devices.IntervaloDispositivo.modo;

public class DispositivoInteligente extends Dispositivo {
	
	public double kWhAhorro;
	private EstadoDispositivo estadoDisp;
	Map<String, Sensor> sensores = new HashMap<String, Sensor>();
	private IntervaloDispositivo unIntervalo;

	boolean esInteligente = true; //Ya no se si hace falta este atributo, pero lo vemos despues

	private List<IntervaloDispositivo> intervalos = new ArrayList<>();
	
	//Constructor para los dispositivos que no cuentan para evaluar ya que no estan en la lista de precargados
	public DispositivoInteligente(String nombDisp,String descrip) {
		setNombreDisp(nombDisp);
		setEquipoConcreto(descrip);
		setkWh(0);setHorasUsoMin(-1);setHorasUsoMax(-1);
	}
		
	public DispositivoInteligente(String nombDisp,double kWh,int year,int month,int day,int hour,int min,int sec,boolean esBajoC) {
		setNombreDisp(nombDisp);
		setkWh(kWh);
		setEstadoDisp(new Encendido());
		setFechaRegistro(LocalDateTime.of(year,month,day,hour,min,sec));
		setkWhAhorro(kWh);
		setEsBajoConsumo(esBajoC);
		setIntervalo(new IntervaloDispositivo(getFechaRegistro(),modo.NORMAL));

	}
	
	//Constructor para la conversion actualizado (falta revisar)
	public DispositivoInteligente() {
		setEstadoDisp(new Encendido());
		setFechaRegistro(LocalDateTime.now());
		setIntervalo(new IntervaloDispositivo(getFechaRegistro(),modo.NORMAL));
	}
	
	//Conversion 2.0
	public DispositivoInteligente(int y,int m,int d,int h,int min,int s) {
		setEstadoDisp(new Encendido());
		setFechaRegistro(LocalDateTime.of(y,m,d,h,min,s));
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
	
	public boolean getEsInteligente(){
		return esInteligente;
	}
	public void setEsInteligente(boolean ansSmart){
		this.esInteligente = ansSmart;
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
	
	// Funcionalidades Entrega1
	
	public double horasDeUsoTotales() {
		horasDeUso = intervalos.stream().mapToDouble(unInt -> unInt.calculoDeHoras()).sum();
		return horasDeUso;
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
		
	@Override
	public double consumoTotal() {
		return intervalos.stream().mapToDouble(unInt -> consumoParcial(unInt)).sum();
	}
	
	// Duplicadas para evaluar en una lista especifica que nosotros creemos
	
	public double horasDeUsoTotales(List<IntervaloDispositivo> listaAEvaluar) {
		horasDeUso = listaAEvaluar.stream().mapToDouble(unInt -> unInt.calculoDeHoras()).sum();
		return horasDeUso;
	}
	
	public double consumoTotal(List<IntervaloDispositivo> listaAEvaluar) { // Metodo que usa el calculo de consumo entre periodos
		return listaAEvaluar.stream().mapToDouble(unInt -> consumoParcial(unInt)).sum();
	}
	
	// Ordenes basicas al dispositivo (habria que ver como combinar esto con los execute del actuador)
	
	public void guardarAuxiliar() { //Para que se guarden los intervalos en la lista (esto es un metodo delegado nunca lo van a tocar en el main)
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
	
	// Consultas de consumo en periodos especificos y sus metodos delegados
	
	public double consumoTotalEntre(LocalDateTime fechaInicio,LocalDateTime fechaFin){
		int posIntInicial = posicionInicial(fechaInicio);
		int posIntFin = posicionFinal(fechaFin);
		if(condicionDeError(posIntInicial,posIntFin)){
			return 0;
		}
		IntervaloDispositivo intInic = setearIntervAux(fechaInicio,posIntInicial,true);
		IntervaloDispositivo intFin = setearIntervAux(fechaFin,posIntFin,false);
		List<IntervaloDispositivo> intervalosAEvaluar = listaAEvaluar(intInic,intFin,posIntInicial,posIntFin);
		return consumoTotal(intervalosAEvaluar);	
	}
	
	public double consumoEnUltimasHoras(int horas) { // En tiempo real
		LocalDateTime momentoActual = LocalDateTime.now();
		LocalDateTime momentoInicial = momentoActual.minusHours(horas);		
		boolean condicion = false;		
		int posIntervI = posicionInicial(momentoInicial);
		IntervaloDispositivo evaluado = intervalos.get(posIntervI);
		if(momentoInicial.isAfter(evaluado.getFin())) {
			posIntervI+=1;
			condicion = true;
		}		
		int posIntervF = intervalos.size()-1;		
		if(condicion) {
			momentoInicial = intervalos.get(posIntervI).getInicio();
		}		
		IntervaloDispositivo inicial = setearIntervAux(momentoInicial,posIntervI,true);
		IntervaloDispositivo fin = setearIntervAux(momentoActual,posIntervF,false);		
		List<IntervaloDispositivo> intervalosAEvaluar = listaAEvaluar(inicial,fin,posIntervI,posIntervF);
		return consumoTotal(intervalosAEvaluar);
	}
	
	public double consumoEnUltimasHoras(int horas,LocalDateTime fechaLim) { // Para testear
		
		LocalDateTime momentoActual = fechaLim;
		LocalDateTime momentoInicial = momentoActual.minusHours(horas);
		
		boolean condicion = false;
		
		int posIntervI = posicionInicial(momentoInicial);
		IntervaloDispositivo evaluado = intervalos.get(posIntervI);
		if(momentoInicial.isAfter(evaluado.getFin())) {
			posIntervI+=1;
			condicion = true;
		}
		
		int posIntervF = intervalos.size()-1;
		
		if(condicion) {
			momentoInicial = intervalos.get(posIntervI).getInicio();
		}
		
		IntervaloDispositivo inicial = setearIntervAux(momentoInicial,posIntervI,true);
		IntervaloDispositivo fin = setearIntervAux(momentoActual,posIntervF,false);
		
		List<IntervaloDispositivo> intervalosAEvaluar = listaAEvaluar(inicial,fin,posIntervI,posIntervF);
		return consumoTotal(intervalosAEvaluar);
	}

	// Metodos para calculo de consumo entre (fechainic,fechafin) y en n horas

	public boolean condicionDeError(int i,int f) {
		if(i==-1||f==-1) {
			if(i==-1) {
				System.out.println("Fecha inicial no válida.");
				return true;
			}
			System.out.println("Fecha final no válida.");
			return true;
		}
		return false;
	}
	
	public IntervaloDispositivo setearIntervAux(LocalDateTime fechaIoF,int posIoF,boolean opcion) { //true:inicial false:fin
		IntervaloDispositivo interv = new IntervaloDispositivo();
		if(opcion) {
			interv.setInicio(fechaIoF);
		} else {interv.setInicio(intervalos.get(posIoF).getInicio());}
		if(opcion) {
			interv.setFin(intervalos.get(posIoF).getFin());
		} else {interv.setFin(fechaIoF);}
		interv.setModo(intervalos.get(posIoF).getModo());
		return interv;
	}
	
	public List<IntervaloDispositivo> listaAEvaluar(IntervaloDispositivo i,IntervaloDispositivo f,int pI,int pF){
		List<IntervaloDispositivo> listaAEvaluar = new ArrayList<>();
		int z = 0;
		int x = pI + 1;
		int y = pF - 1;
		listaAEvaluar.add(i);
		for(z=x;z<=y;z++) {
			listaAEvaluar.add(intervalos.get(x));
		}
		listaAEvaluar.add(f);
		return listaAEvaluar;
	}

	public int posicionInicial(LocalDateTime unaFechaI) {
		
		List<LocalDateTime> fInicios = new ArrayList<>();
		intervalos.stream().forEach(unInt-> fInicios.add(unInt.getInicio())); // Una lista con todas las fechas iniciales
 		int i = 0, tam = intervalos.size();
		LocalDateTime[] fechas = fInicios.toArray(new LocalDateTime[tam]); // Creo un array para usar los indices que es mas facil que el get (para mi)
		
		LocalDateTime primerFI = intervalos.get(0).getInicio();
		LocalDateTime ultFI = intervalos.get(tam-1).getInicio();
		LocalDateTime ultFF = intervalos.get(tam-1).getFin();
		
		//Casos extremos: fechaI es antes que todas las fechasI o fechaI despues de todas
		
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
		
		while(i<tam && !(unaFechaI.isBefore(fechas[i])||unaFechaI.isEqual(fechas[i]))) {
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
		
		LocalDateTime primerFI = intervalos.get(0).getInicio();
		LocalDateTime primerFF = intervalos.get(0).getFin();
		LocalDateTime ultFF = intervalos.get(tam-1).getFin();
		
		//Casos extremos: fechaF ocurre antes que cualquier fecha inicial
		
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
	
}