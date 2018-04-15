package model;

public class Categoria {
	
	private String categoria;
	private double cargoFijo;
	private double cargoVariable;
	
	public Categoria(String categoria, double cargoFijo, double cargoVariable){
		this.categoria = categoria;
		this.cargoFijo = cargoFijo;
		this.cargoVariable = cargoVariable;
	}

	public Categoria() {
	}

}
