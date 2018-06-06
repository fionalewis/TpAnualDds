package Exceptions;

public class CaracterInvalidoException  extends Exception { 
	
	private static final long serialVersionUID = 1L; // porque lloraba el eclipse nomas, ni se usa

	public CaracterInvalidoException(String s){  
	  super(s);  
	} 
}