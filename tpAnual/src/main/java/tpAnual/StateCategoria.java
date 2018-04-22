package tpAnual;

public abstract class StateCategoria {
	
    public String categoriaR;
    public double valorFijo;
    public double valorVariable;
	
	public StateCategoria(String categoria,double cargoFijo,double cargoVariable) {
		this.categoriaR = categoria;
		setValorFijo(cargoFijo);
		setValorVariable(cargoVariable);
	}
    
    public String getCategoriaActual(){return categoriaR;}                                                
    public double getValorFijo(){return valorFijo;}                                                       
    public double getValorVariable(){return valorFijo;}                                                   
    public void setValorFijo(double valFijo){this.valorFijo = valFijo;}                                   
    public void setValorVariable(double valVariable){this.valorVariable = valVariable;}                    
    public void actualizarCategoria(CategoriaContext context){context.setState(this);}
    
}

class CategoriaContext {
	
	private StateCategoria catState;                                                                 
    
	//Default
	CategoriaContext() {                                                                             
	    setState(new Categoria1());
	}                                                                                                
	                                                                                                 
	//este setter solo se deberia usar aca en cualquier otro momento usar actualizarCategoria
	void setState(StateCategoria newState) {                                                   
	    catState = newState;                                                                         
	}                                                                                                
	                                                                                                 
	StateCategoria getState() {                                                                      
	    return catState;                                                                             
	}                                                                                                                                                                                              
	                                                                                                 
	public double calcularTarifa(double consumo){                                                    
		return catState.getValorFijo() + catState.getValorVariable() * consumo;                      
	}
	
}

class Categoria1 extends StateCategoria {
	Categoria1(){
		super("R1",18.76,0.644);
	}
}

class Categoria2 extends StateCategoria {
	Categoria2(){
		super("R2",35.32,0.644);
	}
}

class Categoria3 extends StateCategoria {
	Categoria3(){
		super("R3",60.71,0.681);
	}
}                                                                                                            
                                                                                                             
class Categoria4 extends StateCategoria {
	Categoria4(){
		super("R4",71.74,0.738);
	}
}                                                                                                            
                                                                                                             
class Categoria5 extends StateCategoria {
	Categoria5(){
		super("R5",110.38,0.794);
	}
}                                                                                                            
                                                                                                             
class Categoria6 extends StateCategoria {
	Categoria6(){
		super("R6",220.75,0.832);
	}
}                                                                                                            
                                                                                                             
class Categoria7 extends StateCategoria {
	Categoria7(){
		super("R7",443.59,0.851);
	}
}                                                                                                            
                                                                                                             
class Categoria8 extends StateCategoria {
	Categoria8(){
		super("R8",545.96,0.851);
	}
}                                                                                      
                                                                                                             
class Categoria9 extends StateCategoria {
	Categoria9(){
		super("R9",887.19,0.851);
	}
}