package model;

public class Dispositivo {
	
	private String nombre;
	private Integer consumoXHora;
	private boolean encendido;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getConsumoXHora() {
		return consumoXHora;
	}
	public void setConsumoXHora(Integer consumoXHora) {
		this.consumoXHora = consumoXHora;
	}
	public boolean isEncendido() {
		return encendido;
	}
	public void setEncendido(boolean encendido) {
		this.encendido = encendido;
	}
}
