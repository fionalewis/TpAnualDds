package modelo.users;

import modelo.FilterPredicates;
import modelo.JsonManager;
import modelo.MetodoSimplex;
import modelo.deviceState.AhorroEnergia;
import modelo.deviceState.Encendido;
import modelo.devices.DeviceFactory;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoConvertido;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.RecomendacionDTO;
import modelo.repositories.*;
import modelo.geoLocation.GeoLocation;
import modelo.geoLocation.Transformador;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.*;

import java.util.function.*;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import org.apache.commons.math3.optim.PointValuePair;

//Ojo las rutas de json!!! (ver JsonManager)

@Entity
public class Cliente extends Usuario {

	public enum TipoDocumento { DNI, CI, LE, LI }

	@Id
	@Column(name="cliente_id")
	private String nroDoc;
	
	@Enumerated(EnumType.STRING)
	private TipoDocumento tipoDoc;
	private String telefono;
	private String domicilio;

	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL)
	private List<Reporte> reportes = new ArrayList<>();

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="cliente_id", referencedColumnName="cliente_id",nullable=true,unique=false)
	private static List<Dispositivo> dispositivos = new ArrayList<>();
	
	@ManyToOne(fetch=FetchType.LAZY)
  	@JoinColumn(name="idCategoria")
	private Categoria categ;

	private int puntos = 0;

	@ManyToOne(fetch=FetchType.LAZY)
  	@JoinColumn(name="transformador_id")
	private Transformador transformadorActual;

	@Transient
	private transient MetodoSimplex simplex = new MetodoSimplex();
	
	//Esta lista es auxiliar hasta que veamos donde guardar los DE que borramos de la lista gral
	@Transient
	private List<DispositivoEstandar> aux = new ArrayList<>(); 
	
	public Cliente() {
		super();
	}
		
	//Constructor default p/cuando un usuario cree una cuenta
	public Cliente(String name,String surname,String username,String pass,
					TipoDocumento tDoc,String nDoc,String tel,String dom) {
		setNombre(name);
		setApellido(surname);
		setUserName(username);
		setPassword(pass);
		this.fechaAlta = LocalDate.now();
		this.tipoDoc = tDoc;
		this.nroDoc = nDoc;
		this.telefono = tel;
		this.domicilio = dom +  ", Buenos Aires, Argentina";		
		asignarTransformador();
		setCateg();
	}
	
	//Constructor para el json (toma la fecha de registro como la del momento)
	public Cliente(String name,String surname,String username,String pass,TipoDocumento tDoc,String nDoc,
					String tel,String dom,List<Dispositivo> disp) {
		setNombre(name);
		setApellido(surname);
		setUserName(username);
		setPassword(pass);
		this.fechaAlta = LocalDate.now();
		this.tipoDoc = tDoc;
		this.nroDoc = nDoc;
		this.telefono = tel;
		this.domicilio = dom +  ", Buenos Aires, Argentina";
		dispositivos.addAll(disp);
		asignarTransformador();
		setCateg();
	}
	
	//Constructor para los tests (le pueden asignar la fecha de registro que quieran)
	public Cliente(String name,String surname,String username,String pass,int y,int m,int d,TipoDocumento tDoc,
					String nDoc,String tel,String dom,List<Dispositivo> disp) {
		setNombre(name);
		setApellido(surname);
		setUserName(username);
		setPassword(pass);
		this.fechaAlta = LocalDate.of(y,m,d);
		this.tipoDoc = tDoc;
		this.nroDoc = nDoc;
		this.telefono = tel;
		this.domicilio = dom +  ", Buenos Aires, Argentina";
		dispositivos.addAll(disp);
		asignarTransformador();
		setCateg();
	}
	
	//Getters y Setters
	
	public TipoDocumento getTipoDoc() {
		return tipoDoc;
	}
	public void setTipoDoc(TipoDocumento tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	public String getNroDoc() {
		return nroDoc;
	}
	public void setNroDoc(String nroDoc) {
		this.nroDoc = nroDoc;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) { // Cada vez que cambie de domicilio debe reasignarse
		this.domicilio = domicilio;
		asignarTransformador(); 
	}
	public Categoria getCateg() {
		return categ;
	}
	public void setCateg(Categoria categoria) {
		this.categ = categoria;
	}
	public void setCateg() {
		this.categ = JsonManager.categoria(this.calcularConsumo());		
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
	//Getters y Setters - Entrega 2
	
	public Transformador getTransformadorActual() {
		return transformadorActual;
	}
	public void setTransformadorActual(Transformador transformadorActual) {
		this.transformadorActual = transformadorActual;
	}

	public MetodoSimplex getSimplex(){
		return simplex;
	}
	public void setSimplex(MetodoSimplex metodoSimplex)
	{
		this.simplex = metodoSimplex;
	}

	//Dispositivos
	
	public List<Dispositivo> getDispositivos() {
		return dispositivos;
	}

	public void setDispositivos(List<Dispositivo> _dispositivos) {
		dispositivos = _dispositivos;		
	}

	public void agregarDispositivo(Dispositivo disp) {
		if(disp instanceof DispositivoInteligente && !(disp instanceof DispositivoConvertido)) {
			puntos+=15;
		}
		dispositivos.add(disp);		
	}
	
	public void quitarDispositivo(Dispositivo disp) {
		dispositivos.remove(disp);
	}
	
	public List<Dispositivo> obtenerLista(String opcion){
		Predicate<Dispositivo> predicate = null;
		switch(opcion) {
		case"Inteligente":
			predicate = FilterPredicates.filterDI();
			break;
		case"Estandar":
			predicate = FilterPredicates.filterDE();
			break;
		case"Convertido":
			predicate = FilterPredicates.filterDC();
			break;
		case"IyC":
			predicate = FilterPredicates.filterAmbos();
			break;
		default: System.out.println("Opcion no valida.");
			break;
		}
		return dispositivos.stream().filter(predicate).collect(Collectors.toList());
	}	
	
	/* La funcion recibe una lista porque la vamos a usar para probar lo que hacemos,
	 * cuando no haga falta porque se muestre en web o donde sea vamos a borrar ese
	 * argumento y poner 'dispositivos' en su lugar para que tome los disp del cliente */
	
	public void mostrarLista(List<Dispositivo> dispositivos) {
		for(int i = 0;i<dispositivos.size();i++) {
			System.out.println(dispositivos.get(i).//getTipoDisp().toString());
					getNombreDisp());
		}
	}
	
	//Conversion de dispositivos
	
	public void agregarModuloAdaptador(DispositivoEstandar disp) {
		DispositivoConvertido dispConvertido = new DispositivoConvertido(disp);
		agregarDispositivo(dispConvertido);
		quitarDispositivo(disp);
		aux.add(disp); //El DE que eliminamos de la lista gral lo persistimos en otra parte
		puntos+=10;
	}
	
	//Consumo
	
	@Override public double calcularConsumo() {
		return obtenerLista("IyC").stream().mapToDouble(unDisp -> unDisp.consumoTotal()).sum();		
	}

	/* Para obtener la tarifa. El admin va a ser el unico que pueda usar este metodo
	 * para hacer alguna cosulta del estilo clienteX.obtenerTarifa(), podemos pasarlo
	 * a su clase como obtenerTarifa(clienteX) tambien, pero para esta entrega seria
	 * complicarlo para nada
	 */
	
	public double obtenerTarifa() {
		double cons = calcularConsumo();
		return getCateg().calculoTarifa(cons);
	}
	
	//Funcionalidades
	
	public boolean algunoEncendido() { //Estos cout los vamos a borrar despues, estan para que los vean mientras prueben nada mas
		boolean result = obtenerLista("IyC").stream().anyMatch(FilterPredicates.filterAnyOn());
		if(result) {
			System.out.println("Existe alg�n dispositivo encendido.");
		} else {
			System.out.print("Todos los dispositivos se encuentran apagados.");
		}
		return result;
	}
	
	public int cantDispositivosTotal() {
		return dispositivos.size();
	}
	
	public int cantDispositivos() { //Solo para los que pueden interactuar con el sistema`
		return obtenerLista("IyC").size();
		
	}
	
	public int cantDisp(boolean opcion) { //Prendidos = true	Apagados = false
		int apagados = 0, prendidos = 0;
		for (Dispositivo unDisp : obtenerLista("IyC")) {
		if(unDisp.getEstadoDisp() instanceof Encendido || unDisp.getEstadoDisp() instanceof AhorroEnergia) {
			prendidos++;				
			}
		}
		apagados = cantDispositivos() - prendidos;
		if(opcion) {
			return prendidos;
		} else {return apagados;}
	}
	
	//Entrega 2
	
	public void asignarTransformador()  {
		GeoLocation coordDom = new GeoLocation(domicilio,0,0);
		try {
			coordDom = JsonManager.conseguirCoord(domicilio);
		} catch (IOException e) {
			e.printStackTrace(); //
		}
		transformadorActual = GeoLocation.transfMasCercanoA(coordDom);
	}
	
	public double cantHorasEstandarXMes() {
		return obtenerLista("Estandar").stream().mapToDouble(unDisp ->((DispositivoEstandar) unDisp).getHorasUsoDiarias()*720).sum();
	}
	
	public double consumoXMesEstandar() {
		return obtenerLista("Estandar").stream().mapToDouble(unDisp ->((DispositivoEstandar) unDisp).getHorasUsoDiarias()*720*(unDisp.getkWh())).sum();
	}
	
	public double calculoHoras(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Period period = Period.between(fechaInicio.toLocalDate(),fechaFin.toLocalDate());
        int periodDays = period.getDays();
        double horas = periodDays*24;
        return horas;
	}
	
	public int calculoDias(LocalDateTime fechaInicio, LocalDateTime fechaFin){
        Period period = Period.between(fechaInicio.toLocalDate(),fechaFin.toLocalDate());
        return period.getDays();
	}
	
	//funcion nueva
	public double consumoXPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		double horas = calculoDias(fechaInicio, fechaFin);
		double horasEstandar = obtenerLista("Estandar").stream().mapToDouble(unDisp ->((DispositivoEstandar) unDisp).getHorasUsoDiarias()*horas*(unDisp.getkWh())).sum();
//		double horasInteligente = obtenerLista("Inteligente").stream().mapToDouble(unDisp ->((DispositivoInteligente) unDisp).consumoTotalEntre(fechaInicio,fechaFin)).sum();
//		double consumoTotal = horasEstandar + horasInteligente;
	//	return consumoTotal;
		return horasEstandar;
	}
	
	public double consumoXPeriodoNuevo(LocalDateTime fechaInicio, LocalDateTime fechaFin,DispositivoRepository disp){
		double horas = calculoHoras(fechaInicio, fechaFin);
		List <Dispositivo> de = disp.getListaDispositivos("estandar");
		List <Dispositivo> di = disp.getListaDispositivos("inteligente");
		double horasEstandar = de.stream().mapToDouble(unDisp ->((DispositivoEstandar) unDisp).getHorasUsoDiarias()*horas*(unDisp.getkWh())).sum();
		double horasInteligente = di.stream().mapToDouble(unDisp ->((DispositivoInteligente) unDisp).consumoTotalEntre(fechaInicio,fechaFin)).sum();
		double consumoTotal = horasEstandar + horasInteligente;
		return consumoTotal;
	}
	
	//Este metodo quizas deberia ir en el administrador mas adelante, pero por ahora lo consultamos directamente desde el cliente
	
	public List<Dispositivo> dispDescartadosSimplex(){
		List<Dispositivo> aNoTenerEnCuenta = new ArrayList<>();
		aNoTenerEnCuenta = obtenerLista("Inteligente").stream().filter(FilterPredicates.filterDescartados()).collect(Collectors.toList());
		return aNoTenerEnCuenta;
	}
	
	public List<Dispositivo> listaSimplex() {
		List<Dispositivo> aAnalizar = dispositivos;
		List<Dispositivo> toRemove = new ArrayList<>();
		for(Dispositivo unDisp: dispositivos){
		    if(unDisp.getHorasUsoMax()==-1){
		        toRemove.add(unDisp);
		    }
		}
		aAnalizar.removeAll(toRemove);
		return aAnalizar;
	}
	
	public boolean hogarEficiente() throws FileNotFoundException, InstantiationException, IllegalAccessException {
		PointValuePair simplex = llamarSimplex();
		return simplex.getValue() <= 612;
	}
	
	public void obtenerRecomendacion() throws FileNotFoundException, InstantiationException, IllegalAccessException {
		Map<String,Double> horasXDisp = simplex.horasMaxXDisp();
		List<Dispositivo> descartados = new ArrayList<>();
		if(descartados.size()!=0) {
			System.out.println("Se han descartado del c�lculo de horas m�ximas los siguientes dispositivos:");
			for(Dispositivo unDisp : descartados) {
				System.out.println(unDisp.getNombreDisp() + ", " + unDisp.getEquipoConcreto());
			}
		}
		for(Entry<String, Double> unValor : horasXDisp.entrySet()) {
			System.out.println("La recomendaci�n de horas m�ximas para el dispositivo '" + unValor.getKey() + "' es de " + unValor.getValue() + "hs.");
		}		
	}
	
	public Map<String,Double> horasXDisp() throws FileNotFoundException, InstantiationException, IllegalAccessException {
		return simplex.horasMaxXDisp();
	}
	
	public List<RecomendacionDTO> obtenerRecomendacionDTO() throws FileNotFoundException, InstantiationException, IllegalAccessException {
		Map<String,Double> horasXDisp = simplex.horasMaxXDisp();
		List<RecomendacionDTO> retorno = new ArrayList();
		List<Dispositivo> descartados = new ArrayList<>();
		/*if(descartados.size()!=0) {
			retorno = "Se han descartado del c�lculo de horas m�ximas los siguientes dispositivos:";
			for(Dispositivo unDisp : descartados) {
				retorno = retorno + unDisp.getNombreDisp() + ", " + unDisp.getEquipoConcreto();
			}
		}*/
		for(Entry<String, Double> unValor : horasXDisp.entrySet()) {
			RecomendacionDTO rec = new RecomendacionDTO(unValor.getKey(),unValor.getValue());
			retorno.add(rec);
		}		
		return retorno;
	}
	
	public String obtenerRecomendacionString() throws FileNotFoundException, InstantiationException, IllegalAccessException {
		Map<String,Double> horasXDisp = simplex.horasMaxXDisp();
		String retorno = "";
		List<Dispositivo> descartados = new ArrayList<>();
		if(descartados.size()!=0) {
			retorno = "Se han descartado del c�lculo de horas m�ximas los siguientes dispositivos:";
			for(Dispositivo unDisp : descartados) {
				retorno = retorno + unDisp.getNombreDisp() + ", " + unDisp.getEquipoConcreto();
			}
		}
		for(Entry<String, Double> unValor : horasXDisp.entrySet()) {
			retorno = retorno + "La recomendaci�n de horas m�ximas para el dispositivo '" + unValor.getKey() + "' es de " + unValor.getValue() + "hs.";
		}		
		return retorno;
	}
	
	public PointValuePair llamarSimplex() throws FileNotFoundException, InstantiationException, IllegalAccessException {
		//obtener lista de descartados para nombrarlos
		//enviar al simplex solo los convertidos,inteligentes y estandar analizables (no heladeras, ni descartados)
		//conseguir recomendacion hogareficiente
		List<Dispositivo> listaSimplex = listaSimplex();
		return simplex.aplicarMetodoSimplex(listaSimplex);
	}
	
	public static void main(String[] args) throws FileNotFoundException, InstantiationException, IllegalAccessException {
		Cliente pepe = new Cliente();
		DeviceFactory f = new DeviceFactory();
		Dispositivo estandar = f.crearDisp("Microondas","Convencional");	
		Dispositivo aire = f.crearDisp("Aire Acondicionado","3500 frigorias");
		Dispositivo aire1 = f.crearDisp("Aire Acondicionado","2200 frigorias");
		pepe.agregarDispositivo(estandar);pepe.agregarDispositivo(aire1);pepe.agregarDispositivo(aire);
		System.out.println("\t Main para probar que el simplex desde un cliente:\n");
		System.out.println("Creamos un disp est�ndar. Sin setear horas base se asume que todo disp estandar se usa 1h por dia:");
		System.out.println("Entonces este disp. usar�a 720hs al mes:" + pepe.cantHorasEstandarXMes());
		System.out.println("Obteniendo el consumo mensual de ese disp., que por el json tiene precargado 0.64kWh:");
		System.out.println("Entonces este disp. usar�a 720hs*0.64kWh al mes = 460.8:" + pepe.consumoXMesEstandar()+"\n");
		pepe.quitarDispositivo(estandar);((DispositivoEstandar) estandar).setHorasUsoDiarias(0.5);pepe.agregarDispositivo(estandar);
		System.out.println("Seteando 0.5hs al disp.:");
		System.out.println("Entonces este disp usaria 360hs al mes:" + pepe.cantHorasEstandarXMes());
		System.out.println("Entonces este disp ahora usa 360*0.64, 230.4kWh al mes:" + pepe.consumoXMesEstandar() + "\n");
		Dispositivo estandar2 = f.crearDisp("Plancha","A vapor");
		pepe.agregarDispositivo(estandar2);
		System.out.println("Agregando otro disp estandar que tiene 1h de uso diaria seteada y 0.75kWh");
		
		System.out.println("\n Llamando al simplex de pepe");
		System.out.println("Valor de Z M�x: " + pepe.llamarSimplex().getValue());		
		pepe.obtenerRecomendacion();
		System.out.println("\n�Tiene un hogar eficiente?: " + pepe.hogarEficiente());		
	}
	
}