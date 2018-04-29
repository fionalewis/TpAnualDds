package tpAnual;

import java.io.FileNotFoundException;
import java.util.List;

public class Categoria {
	
	/* Fijamos un maximo muy grande en R9 asi quedaba mas simple el codigo y no habia necesidad de usar un minimo */
	
	private String clasif;
	private double consumo;
	private int max;
	private double cargoFijo;
	private double cargoVariable;
	
	//Constructores
	
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
	
	//Metodos para obtener la tarifa
	
	public double calculoTarifa(double consumo) {
		double tarifa = cargoVariable*consumo + cargoFijo;
		return tarifa;
	}

	public static double obtenerTarifa(double consumo) {
	List<Categoria> categorias = null;
	double tarifa = 0;
	try {
		categorias = DAOJson.deserializarLista(Categoria.class, "//home//dds//git//TpAnualDds//tpAnual//JSONs//jsonCategorias.json");
	} catch (FileNotFoundException e) {
		System.out.println("No se ha encontrado el archivo.");
	} catch (InstantiationException e) {
		System.out.println("Error en la inicialización.");
	} catch (IllegalAccessException e) {
		System.out.println("Error de acceso.");
	}
	for(int i=0;i<=9;i++) {
		int cont = 0;
		Categoria c = categorias.get(cont);
		if(consumo<=(c.getMax())) {
			tarifa = c.calculoTarifa(consumo);break;
		}
		cont++;
	}
	return tarifa;
	}
	
}