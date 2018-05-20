package tpAnual.users;

import java.time.LocalDate;

public abstract class Usuario {
	
	protected String nombre;
	protected String apellido;
	protected LocalDate fechaAlta;
	protected String userName;
	protected String password;
	
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
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(int year,int month,int day) {
		this.fechaAlta = LocalDate.of(year,month,day);
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
	
	public abstract double calcularConsumo(); //Redefinido por cada clase segun lo que necesiten hacer
		
}