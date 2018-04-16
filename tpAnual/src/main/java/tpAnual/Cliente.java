package tpAnual;

import java.util.List;
import java.util.ArrayList;

public class Cliente extends Usuario {

	public enum TipoDocumento { DNI, CI, LE, LI }

	private TipoDocumento tipoDoc;
	private int nroDoc;
	private int telefono;
	private String domicilio;
	private double consumo;
	private Categoria categ = new Categoria("R1");
	private List<Dispositivo> dispositivos;
	
	public Cliente(String name,String surname,String username,String pass,
					TipoDocumento tDoc,int nDoc,int tel,String dom) {
		super(name,surname,username,pass);
		this.tipoDoc = tDoc;
		this.nroDoc = nDoc;
		this.telefono = tel;
		this.domicilio = dom;
		this.dispositivos = new ArrayList<>();
	}

	//Getters y Setters
	
	public TipoDocumento getTipoDoc() {
		return tipoDoc;
	}
	public void setTipoDoc(TipoDocumento tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	public int getNroDoc() {
		return nroDoc;
	}
	public void setNroDoc(int nroDoc) {
		this.nroDoc = nroDoc;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
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

	public String categoria(int consumo,Categoria categ) {
		categ.actualizarCategoria(consumo,categ);
		return categ.getClasif();
	}
	
	//Consumo

	@Override public double calcularConsumo() {
		this.consumo = this.dispositivos.stream().mapToDouble(unDisp -> unDisp.consumoActual()).sum();
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