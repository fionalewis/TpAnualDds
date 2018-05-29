package tpAnual.users;

import java.util.List;

import capaPresentacion.Programa;
import tpAnual.deviceState.Encendido;
import tpAnual.devices.Dispositivo;
import tpAnual.devices.DispositivoEstandar;
import tpAnual.devices.DispositivoInteligente;
import tpAnual.devices.DispositivoConvertido;
import tpAnual.deviceState.AhorroEnergia;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Cliente extends Usuario {

	public enum TipoDocumento { DNI, CI, LE, LI }

	private TipoDocumento tipoDoc;
	private String nroDoc;
	private String telefono;
	private String domicilio;
	private List<DispositivoInteligente> dispositivos = new ArrayList<>();
	private List<Dispositivo> dispTotales = new ArrayList<>();
	private List<DispositivoEstandar> dispEstandar = new ArrayList<>();
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
		this.dispositivos.addAll(disp);
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

	//Para la lista de dispositivos en general
	
	public List<Dispositivo> getDispTotales() {
		return dispTotales;
	}
	public void setDispTotales(List<Dispositivo> dispTotales) {
		this.dispTotales = dispTotales;
	}
	public void agregarAListaGral(Dispositivo dispositivo) {
		dispTotales.add(dispositivo);
	}
	public void quitarDispositivo(Dispositivo dispositivo) {
		dispTotales.remove(dispositivo);
	}
	public List<DispositivoEstandar> getDispEstandar() {
		return dispEstandar;
	}
	public void setDispEstandar(List<DispositivoEstandar> dispEstandar) {
		this.dispEstandar = dispEstandar;
	}
	public void agregarADispEstandar(DispositivoEstandar dispositivo) {
		dispEstandar.add(dispositivo);
	}
	public void quitarDispEstandar(DispositivoEstandar dispositivo) {
		dispEstandar.remove(dispositivo);
	}
	
	//Para la lista de dispositivos inteligentes

	public void setDispositivos(List<DispositivoInteligente> dispositivos) {
		this.dispositivos = dispositivos;
	}
	
	public List<DispositivoInteligente> getDispositivos() {
		return dispositivos;
	}

	public void agregarDispositivo(Dispositivo dispositivo) {
		if(dispositivo instanceof DispositivoInteligente){
			dispositivos.add((DispositivoInteligente) dispositivo);
			agregarAListaGral(dispositivo);
			puntos+=15;
		} else if(dispositivo instanceof DispositivoConvertido) {
			dispositivos.add((DispositivoConvertido) dispositivo);
			agregarAListaGral(dispositivo);
			} else {
			agregarAListaGral(dispositivo);
			agregarADispEstandar((DispositivoEstandar) dispositivo);
			}		
	}
	
	public void quitarDispositivo(DispositivoInteligente dispositivo) {
		dispositivos.remove(dispositivo);
	}
	
	//Conversion de dispositivos
	
	public void agregarModuloAdaptador(DispositivoEstandar disp,double kwhA,String fabricante) {
		DispositivoConvertido dispConvertido = new DispositivoConvertido(disp,kwhA,fabricante);
		agregarDispositivo(dispConvertido);
		puntos+=10;
	}
	
	
	//Consumo

	@Override public double calcularConsumo() {
		double consumo = dispositivos.stream().mapToDouble(unDisp -> unDisp.consumoTotal()).sum();
		return consumo;
	}
	
	//Duplicado para poder pasarle una fechaFin en los tests
	
	public double calcularConsumo(LocalDateTime fechaFin) {
		double consumo = dispositivos.stream().mapToDouble(unDisp -> unDisp.consumoTotal(fechaFin)).sum();
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
		return this.dispositivos.stream().anyMatch(unDisp -> unDisp.getEstadoDisp() instanceof Encendido);
	}
	
	public int cantDispositivos() {
		return dispositivos.size();
	}
	
	public int cantDisp(boolean opcion) { //Prendidos = true	Apagados = false
		int apagados = 0, prendidos = 0;
		for (DispositivoInteligente unDisp: dispositivos) {
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