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
	
	//Separando estos dos (no dar mucha bola a estos cambios xq falta lo del state pattern)
	
	public void setCargoFijo(double cargoFijo) {
		this.cargoFijo = cargoFijo;
	}

	public void setCargoVariable(double cargoVariable) {
		this.cargoVariable = cargoVariable;
	}

	//
	
	public Categoria actualizarCategoria(double consumo,Categoria myCateg) {
	  if(consumo <= 150){myCateg.setClasif("R1");myCateg.setCargoFijo(18.76);myCateg.setCargoVariable(0.644);}
	  	else if(consumo <= 325 ){myCateg.setClasif("R2");myCateg.setCargoFijo(35.32);myCateg.setCargoVariable(0.644);}
	  	else if(consumo <= 400 ){myCateg.setClasif("R3");myCateg.setCargoFijo(60.71);myCateg.setCargoVariable(0.738);}
	  	else if(consumo <= 500 ){myCateg.setClasif("R5");myCateg.setCargoFijo(110.38);myCateg.setCargoVariable(0.794);}
	  	else if(consumo <= 600 ){myCateg.setClasif("R6");myCateg.setCargoFijo(220.75);myCateg.setCargoVariable(0.832);}
	  	else if(consumo <= 700 ){myCateg.setClasif("R7");myCateg.setCargoFijo(443.59);myCateg.setCargoVariable(0.851);}
	  	else if(consumo <= 1400 ){myCateg.setClasif("R8");myCateg.setCargoFijo(545.96);myCateg.setCargoVariable(0.851);}
	  	else {myCateg.setClasif("R9");myCateg.setCargoFijo(887.19);myCateg.setCargoVariable(0.851);}
	  	return myCateg;
	}
	
	public double calculoTarifa(Categoria categAActualizar,double consumo) {
		categAActualizar.actualizarCategoria(consumo,categAActualizar);
		double tarifa = categAActualizar.getCargoVariable()*consumo + categAActualizar.getCargoFijo();
		return tarifa;
	}
}