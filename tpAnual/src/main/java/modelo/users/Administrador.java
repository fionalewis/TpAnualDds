package modelo.users;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.Period;

public class Administrador extends Usuario {

	private int codAdmin;
	private List<Integer> codigosAdmins = new ArrayList<>(); //Temporal hasta que tengamos una BDD para los usuarios o mueva esto a la clase Programa

	public Administrador() {
		super();		
	}	
	
	//Constructor default
	public Administrador(String nombre,String apellido,String username,String pass) {
		setNombre(nombre);
		setApellido(apellido);
		setUserName(userName);
		setPassword(pass);
		this.fechaAlta = LocalDate.now();
		this.codAdmin = this.asignarCod();		
	}
	
	//Constructor del json
	public Administrador(String nombre,String apellido,String username,String pass,int y,int m,int d) {
		setNombre(nombre);
		setApellido(apellido);
		setUserName(userName);
		setPassword(pass);
		this.codAdmin = this.asignarCod();
		this.fechaAlta = LocalDate.of(y,m,d);
	}

	public int getCodAdmin() {
		return codAdmin;
	}
	
	public int asignarCod() {
		int cuantosHay = codigosAdmins.size();
		this.codAdmin = cuantosHay + 1;
		codigosAdmins.add(codAdmin);
		return codAdmin;
	}
	
	public int cantMesesComoAdmin(LocalDate fechaReg) {
		LocalDate fechaConsulta = LocalDate.now();
	    Period p = Period.between(fechaAlta,fechaConsulta);
	    int elapsedYears = p.getYears();
	    int elapsedMonths =  elapsedYears*12 + p.getMonths();
	    return elapsedMonths;
	}
	
	public double calcularConsumo() {return 0;} //Por ahora hasta que se nos informe lo que hace el admin

}