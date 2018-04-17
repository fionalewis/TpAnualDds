package tpAnual;

public class Categoria {
	
	private String clasif;
	private double cargoFijo;
	private double cargoVariable;
	
	public Categoria() {}
	
	public Categoria(String clasif,double cF,double cV) {
		this.clasif = clasif;
		this.cargoFijo = cF;
		this.cargoVariable = cV;
	}

	public Categoria(String codClasif) {
		this.clasif = codClasif;
	}

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
	
	public void setValores(double cF,double cV) {
		this.cargoFijo = cF;
		this.cargoVariable = cV;
	}

	public Categoria actualizarCategoria(double consumo,Categoria myCateg) {
	  if(consumo <= 150){myCateg.setClasif("R1");myCateg.setValores(18.76, 0.644);}
	  	else if(consumo <= 325 ){myCateg.setClasif("R2");myCateg.setValores(35.32, 0.644);}
	  	else if(consumo <= 400 ){myCateg.setClasif("R3");myCateg.setValores(60.71, 0.681);}
	  	else if(consumo <= 450 ){myCateg.setClasif("R4");myCateg.setValores(71.74, 0.738);}
	  	else if(consumo <= 500 ){myCateg.setClasif("R5");myCateg.setValores(110.38, 0.794);}
	  	else if(consumo <= 600 ){myCateg.setClasif("R6");myCateg.setValores(220.75, 0.832);}
	  	else if(consumo <= 700 ){myCateg.setClasif("R7");myCateg.setValores(443.59, 0.851);}
	  	else if(consumo <= 1400 ){myCateg.setClasif("R8");myCateg.setValores(545.96, 0.851);}
	  	else {myCateg.setClasif("R9");myCateg.setValores(887.19, 0.851);}
	  	return myCateg;
	}
	
	public double calculoTarifa(Categoria categ,float consumo) {
		categ.actualizarCategoria(consumo,categ);
		double tarifa = categ.getCargoVariable()*consumo + categ.getCargoFijo();
		return tarifa;
	}
}