package modelo.users;

import java.util.List;
import java.util.stream.Collectors;

import java.util.function.*;

import modelo.FilterPredicates;
import modelo.JsonManager;
import modelo.deviceState.AhorroEnergia;
import modelo.deviceState.Encendido;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoConvertido;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.geoLocation.GeoLocation;
import modelo.geoLocation.Transformador;

import java.util.ArrayList;
import java.io.IOException;
import java.time.LocalDate;

//OJO CON LA RUTA DE JSON

public class Cliente extends Usuario {

	public enum TipoDocumento { DNI, CI, LE, LI }

	private TipoDocumento tipoDoc;
	private String nroDoc;
	private String telefono;
	private String domicilio;
	private List<Dispositivo> dispositivos = new ArrayList<>();
	private Categoria categ;
	private int puntos = 0;
	
	private Transformador transformadorActual;
	
	//Esta lista es auxiliar hasta que veamos donde guardar los DE que borramos de la lista gral
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
		this.dispositivos.addAll(disp);
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
		this.dispositivos.addAll(disp);
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

	//Dispositivos
	
	public List<Dispositivo> getDispositivos() {
		return dispositivos;
	}

	public void setDispositivos(List<Dispositivo> dispositivos) {
		this.dispositivos = dispositivos;
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
			System.out.println(dispositivos.get(i).getTipoDisp().toString());
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
			System.out.println("Existe algún dispositivo encendido.");
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
	
	//Este metodo quizas deberia ir en el administrador mas adelante, pero por ahora lo consultamos directamente desde el cliente
	
	public boolean tieneHogarEficiente() {
		//si en el mes no consume mas de 612kwh
		return false;
	}
}