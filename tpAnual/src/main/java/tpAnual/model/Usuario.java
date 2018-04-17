package tpAnual;

import java.time.LocalDateTime;

public class Usuario {
	
	protected String nombreYApellido;
	protected LocalDateTime fechaAlta;
	protected String userName;
	protected String password;
	
	
	public String getNombre() {
		return nombreYApellido;
	}
	public void setNombre(String nombre) {
		this.nombreYApellido = nombre;
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