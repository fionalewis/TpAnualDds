package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Admin extends Usuario {
	
	public int mesesTrabajando(){
		LocalDate ld = LocalDate.now();
		if(ld.getYear() == fechaDeAlta.getYear()){
			return ld.getMonth().getValue() - fechaDeAlta.getMonth();
		} else {
			return (ld.getYear() - fechaDeAlta.getYear())*12 + fechaDeAlta.getMonth();
		}
	}
}
