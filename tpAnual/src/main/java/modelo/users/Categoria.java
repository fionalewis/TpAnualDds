package modelo.users;

import modelo.JsonManager;

public class Categoria {
	
	/* Fijamos un maximo muy grande en R9 asi quedaba mas simple el codigo */
	
	private String clasif;
	private double consumo;
	private int min;
	private int max;
	private double cargoFijo;
	private double cargoVariable;
	
	//Constructores
	
	public Categoria() {}
	
	public Categoria(double cons) {
		consumo = cons;
	}
	
	//Getters y Setters
	
	public String getClasif() {
		return clasif;
	}
	public double getCargoFijo() {
		return cargoFijo;
	}
	public double getCargoVariable() {
		return cargoVariable;
	}
	public void setClasif(String clasifCat) {
		this.clasif = clasifCat;
	}	
	public void setCargoFijo(double cargoFijo) {
		this.cargoFijo = cargoFijo;
	}
	public void setCargoVariable(double cargoVariable) {
		this.cargoVariable = cargoVariable;
	}
	
	//Dejo los setters por si usamos alguno para los tests
	
	public double getConsumo() {
		return consumo;
	}
	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	
	//Tarifa

	public double calculoTarifa(double consumo) {
		double tarifa = cargoVariable*consumo + cargoFijo;
		return tarifa;
	}
	
	//Seguro haya que corregir este metodo acorde a como sea exactamente la consulta
	//o dependiendo de quien tenga la responsabilidad de hacerla
	
	public static boolean perteneceACateg(String categ,double consumo) {
		Categoria c = JsonManager.categoria(consumo);
		return c.getClasif().equals(categ);
	}
	
}