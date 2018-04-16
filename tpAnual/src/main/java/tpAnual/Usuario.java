package tpAnual;

import java.time.LocalDateTime;

public class Usuario {
	
	protected String nombre;
	protected String apellido;
	protected LocalDateTime fechaAlta;
	protected String userName;
	protected String password;
	
	public Usuario(String name,String surname,String username,String pass) {
		this.nombre = name;
		this.apellido = surname;
		this.fechaAlta = LocalDateTime.now();
		this.userName = username;
		this.password = pass;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public double calcularConsumo() {return 0;}//Redefinido por cada clase
		
}