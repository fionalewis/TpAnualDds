package tpAnual;

public class Categoria {
	
	private String clasif;
	private double cargoFijo;
	private double cargoVariable;
	
	//Constructores
	
	public Categoria() {}

	public Categoria(String codClasif) {
		this.clasif = codClasif;
	}
	
	public Categoria(String clasif,double cF,double cV) {
		this.clasif = clasif;
		this.cargoFijo = cF;
		this.cargoVariable = cV;
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
	
	//Para settear todo de una y que no quede tanto codigo repetido, si hace falta separarlo despues lo hacemos
	//Lo dejo explicado en la tabla de decisiones

	public void setterCateg(String cat,double cF,double cV) {
		clasif = cat; cargoFijo = cF; cargoVariable = cV;
	}
	
	public Categoria actualizarCategoria(double consumo,Categoria myCateg) {
	  if(consumo <= 150){myCateg.setterCateg("R1",18.76,0.644);}
	  	else if(consumo <= 325){myCateg.setterCateg("R2",35.32,0.644);}
	  	else if(consumo <= 400){myCateg.setterCateg("R3",60.71,0.681);}
	  	else if(consumo <= 450){myCateg.setterCateg("R4",71.74,0.738);}
	  	else if(consumo <= 500){myCateg.setterCateg("R5",110.38,0.794);}
	  	else if(consumo <= 600){myCateg.setterCateg("R6",220.75,0.832);}
	  	else if(consumo <= 700){myCateg.setterCateg("R7",443.59,0.851);}
	  	else if(consumo <= 1400){myCateg.setterCateg("R8",545.96,0.851);}
	  	else {myCateg.setterCateg("R9",887.19,0.851);}
	  	return myCateg;
	}
	
	public double calculoTarifa(Categoria categ,double consumo) {
		categ.actualizarCategoria(consumo,categ);
		double tarifa = categ.getCargoVariable()*consumo + categ.getCargoFijo();
		return tarifa;
	}
}