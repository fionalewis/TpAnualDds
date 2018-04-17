package tpAnual;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Administrador extends Usuario {

	private int codAdmin;
	private List<Integer> codigosAdmins = new ArrayList<>(); //Temporal hasta que tengamos una BDD para los usuarios o mueva esto a la clase Programa
	
	public Administrador(String name,String surname,String username,String pass) {
		super(name,surname,username,pass);
		this.codAdmin = this.asignarCod();		
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
	
	public int cantMesesComoAdmin() {
		LocalDateTime fechaConsulta = LocalDateTime.now();
		if(fechaConsulta.getYear() == fechaAlta.getYear()){
			return fechaConsulta.getMonthValue() - fechaAlta.getMonthValue();
		} else {
			return this.calculoMeses(fechaConsulta, fechaAlta);
		}
	}
	
	private int calculoMeses(LocalDateTime fechaActual,LocalDateTime fechaInicio) {
		int x = (fechaActual.getYear() - fechaInicio.getYear())*12;
		int y = Math.abs(fechaActual.getMonthValue()-fechaInicio.getMonthValue());
		return x - y;
	}

}