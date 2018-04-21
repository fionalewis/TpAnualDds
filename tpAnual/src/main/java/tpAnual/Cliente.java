package tpAnual;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Cliente extends Usuario {

	public enum TipoDocumento { DNI, CI, LE, LI }

	private TipoDocumento tipoDoc;
	private String nroDoc;
	private String telefono;
	private String domicilio;
	private Categoria categ = new Categoria("R1");
	private List<Dispositivo> dispositivos = new ArrayList<>();
	
	public Cliente() {
		super();
	}
		
	//Este seria un constructor default para cuando un usuario cree una cuenta
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
	}
	
	//Constructor para el json
	public Cliente(String name,String surname,String username,String pass,int y,int m,int d,
					TipoDocumento tDoc,String nDoc,String tel,String dom,List<Dispositivo> disp) {
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
	
	//Dispositivos
	
	public List<Dispositivo> getDispositivos() {
		return dispositivos;
	}
	
	public void agregarDispositivo(Dispositivo dispositivo) {
		dispositivos.add(dispositivo);
	}
	
	public void quitarDispositivo(Dispositivo dispositivo) {
		dispositivos.remove(dispositivo);
	}
	
	//Categoria
	
	public Categoria getCateg() {
		return categ;
	}

	public String categoria(double consumo,Categoria categ) {
		consumo = calcularConsumo();
		categ.actualizarCategoria(consumo,categ);
		return categ.getClasif();
	}
	
	//Consumo

	@Override public double calcularConsumo() { //Le podriamos cambiar el nombre a consumo() pero bue no hace la dif ahora
		double consumo = this.dispositivos.stream().mapToDouble(unDisp -> unDisp.consumoActual()).sum();
		return consumo;
	}
	
	//Metodo duplicado para poder pasar una fecha final con la que evaluar los test
	
	public double calcularConsumo(LocalDateTime fechaFin) {
		double consumo = this.dispositivos.stream().mapToDouble(unDisp -> unDisp.consumoActual(unDisp.getFechaRegistro(),fechaFin)).sum();
		return consumo;
	}
	
	//Funcionalidades
	
	public boolean algunoEncendido(List<Dispositivo> dispositivos) {
		return dispositivos.stream().anyMatch(unDisp -> unDisp.isEstadoDisp() == true);
	}
	
	public int cantDispositivos() {
		return dispositivos.size();
	}
	
	public int cantDisp(boolean opcion) { //Prendido = true, Apagado = false
		int apagados = 0, prendidos =0;
		for (Dispositivo unDisp: dispositivos) {
			if(unDisp.isEstadoDisp()) {
				prendidos++;
				apagados = dispositivos.size() - prendidos;
			}
		}
		if(opcion) {
			return prendidos;
		} else {return apagados;}
	}
	
}