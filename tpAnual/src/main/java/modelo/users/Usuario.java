package modelo.users;

import java.time.LocalDate;

import javax.persistence.*;

@MappedSuperclass
public abstract class Usuario {
	
	protected String nombre;
	protected String apellido;
	@Column(nullable=true)
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
	
	public boolean loginCorrecto(String pass)
	{
		return password.equals(pass);
	}
	
	
	public abstract double calcularConsumo(); //Redefinido por cada clase segun lo que necesiten hacer
		
}