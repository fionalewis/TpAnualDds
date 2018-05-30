package modelo.users;

import java.util.List;

import capaPresentacion.Programa;
import modelo.deviceState.AhorroEnergia;
import modelo.deviceState.Encendido;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoConvertido;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Cliente extends Usuario {

	public enum TipoDocumento { DNI, CI, LE, LI }

	private TipoDocumento tipoDoc;
	private String nroDoc;
	private String telefono;
	private String domicilio;
	private List<DispositivoInteligente> dispInteligentes = new ArrayList<>();
	private List<DispositivoEstandar> dispEstandares = new ArrayList<>();
	private Categoria categ;
	private int puntos = 0;
	
	public Cliente() {
		super();
	}
		
	//Constructor default p/cuando un usuario cree una cuenta
	public Cliente(String name,String surname,String username,String pass,
					TipoDocumento tDoc,String nDoc,String tel,String dom) {
		setNombre(name);
		setApellido(surname);
		setUserName(userName);
		setPassword(pass);
		this.fechaAlta = LocalDate.now();
		this.tipoDoc = tDoc;
		this.nroDoc = nDoc;
		this.telefono = tel;
		this.domicilio = dom;
		setCateg();
	}
	
	//Constructor para el json
	public Cliente(String name,String surname,String username,String pass,int y,int m,int d,
					TipoDocumento tDoc,String nDoc,String tel,String dom,List<DispositivoInteligente> disp) {
		setNombre(name);
		setApellido(surname);
		setUserName(userName);
		setPassword(pass);
		this.fechaAlta = LocalDate.now();
		this.tipoDoc = tDoc;
		this.nroDoc = nDoc;
		this.telefono = tel;
		this.domicilio = dom;
		this.dispInteligentes.addAll(disp);
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
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public Categoria getCateg() {
		return categ;
	}
	public void setCateg(Categoria categoria) {
		this.categ = categoria;
	}
	public void setCateg() {
		this.categ = Programa.categoria(this.calcularConsumo());
	}
	public int getPuntos() { //
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
	//Dispositivos

	public List<DispositivoEstandar> getDispEstandar() {
		return dispEstandares;
	}
	public void setDispEstandar(List<DispositivoEstandar> dispEstandar) {
		this.dispEstandares = dispEstandar;
	}
	public void agregarADispEstandar(DispositivoEstandar dispositivo) {
		dispEstandares.add(dispositivo);
	}
	public void quitarDispEstandar(DispositivoEstandar dispositivo) {
		dispEstandares.remove(dispositivo);
	}
	
	//Para la lista de dispositivos inteligentes

	public void setDispInteligente(List<DispositivoInteligente> dispositivos) {
		this.dispInteligentes = dispositivos;
	}
	
	public List<DispositivoInteligente> getDispInteligente() {
		return dispInteligentes;
	}

	public void agregarDispInteligente(Dispositivo dispositivo) {
		if(dispositivo instanceof DispositivoInteligente){
			dispInteligentes.add((DispositivoInteligente) dispositivo);
			puntos+=15;
		} else if(dispositivo instanceof DispositivoConvertido) {
			dispInteligentes.add((DispositivoConvertido) dispositivo);
			} else {
			agregarADispEstandar((DispositivoEstandar) dispositivo);
			}		
	}
	public void quitarDispInteligente(DispositivoInteligente dispositivo) {
		dispInteligentes.remove(dispositivo);
	}
	
	//Conversion de dispositivos
	
	public void agregarModuloAdaptador(DispositivoEstandar disp,double kwhA) {
		DispositivoConvertido dispConvertido = new DispositivoConvertido(disp,kwhA);
		agregarDispInteligente(dispConvertido);
		quitarDispEstandar(disp);
		puntos+=10;
	}
	
	
	//Consumo

	@Override public double calcularConsumo() {
		double consumo = dispInteligentes.stream().mapToDouble(unDisp -> unDisp.consumoTotal()).sum();
		return consumo;
	}
	
	//Duplicado para poder pasarle una fechaFin en los tests
	
	public double calcularConsumo(LocalDateTime fechaFin) {
		double consumo = dispInteligentes.stream().mapToDouble(unDisp -> unDisp.consumoTotal(fechaFin)).sum();
		return consumo;
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
	
	public boolean algunoEncendido(List<DispositivoInteligente> dispositivos) {
		return this.dispInteligentes.stream().anyMatch(unDisp -> unDisp.getEstadoDisp() instanceof Encendido);
	}
	
	public int cantDispositivos() {
		return dispInteligentes.size();
	}
	
	public int cantDisp(boolean opcion) { //Prendidos = true	Apagados = false
		int apagados = 0, prendidos = 0;
		for (DispositivoInteligente unDisp: dispInteligentes) {
			if(unDisp.getEstadoDisp() instanceof Encendido || unDisp.getEstadoDisp() instanceof AhorroEnergia) {
				prendidos++;				
			}
		}
		apagados = this.cantDispositivos() - prendidos;
		if(opcion) {
			return prendidos;
		} else {return apagados;}
	}
	
}