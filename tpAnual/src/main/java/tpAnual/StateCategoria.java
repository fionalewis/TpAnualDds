package tpAnual;

public interface StateCategoria {
	public void actualizarCategoria(CategoriaContext context);
	String getCategoriaActual();
    double getValorFijo();
    double getValorVariable();
    void setValorFijo(double valFijo);
    void setValorVariable(double valVariable);
}

class CategoriaContext {
	private StateCategoria catState;                                                                 
    
	//Default                                                                                        
	CategoriaContext() {                                                                             
	    setState(  new Categoria1() );                                                                
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

class Categoria1 implements StateCategoria {                                                                 
	String categoriaR = "R1";                                                                                
	double valorFijo = 18.76;                                                                                
	double valorVariable = 0.644;
	public String getCategoriaActual() { return categoriaR; }                                                
    public double getValorFijo() { return valorFijo; }                                                       
    public double getValorVariable() { return valorFijo; }                                                   
    public void setValorFijo(double valFijo) { this.valorFijo = valFijo; }                                   
    public void setValorVariable(double valVariable) {this.valorVariable = valVariable; }                    
    public void actualizarCategoria(CategoriaContext context) {context.setState(this);}
}

class Categoria2 implements StateCategoria {                                                                 
	String categoriaR = "R2";                                                                                
	double valorFijo = 35.32;                                                                                
	double valorVariable = 0.644;                                                                            
	public String getCategoriaActual() { return categoriaR; }                                                
    public double getValorFijo() { return valorFijo; }                                                       
    public double getValorVariable() { return valorFijo; }                                                   
    public void setValorFijo(double valFijo) { this.valorFijo = valFijo; }                                   
    public void setValorVariable(double valVariable) {this.valorVariable = valVariable; }                    
    public void actualizarCategoria(CategoriaContext context) {context.setState(this);}
}

class Categoria3 implements StateCategoria {                                                                 
	String categoriaR = "R3";                                                                                
	double valorFijo = 60.71;                                                                                
	double valorVariable = 0.681;                                                                            
	public String getCategoriaActual() { return categoriaR; }                                                
    public double getValorFijo() { return valorFijo; }                                                       
    public double getValorVariable() { return valorFijo; }                                                   
    public void setValorFijo(double valFijo) { this.valorFijo = valFijo; }                                   
    public void setValorVariable(double valVariable) {this.valorVariable = valVariable; }                    
    public void actualizarCategoria(CategoriaContext context) {context.setState(this);}
}                                                                                                            
                                                                                                             
class Categoria4 implements StateCategoria {                                                                 
	String categoriaR = "R4";                                                                                
	double valorFijo = 71.74;                                                                                
	double valorVariable = 0.738;                                                                            
	public String getCategoriaActual() { return categoriaR; }                                                
    public double getValorFijo() { return valorFijo; }                                                       
    public double getValorVariable() { return valorFijo; }                                                   
    public void setValorFijo(double valFijo) { this.valorFijo = valFijo; }                                   
    public void setValorVariable(double valVariable) {this.valorVariable = valVariable; }                    
    public void actualizarCategoria(CategoriaContext context) {context.setState(this);}
}                                                                                                            
                                                                                                             
class Categoria5 implements StateCategoria {                                                                 
	String categoriaR = "R5";                                                                                
	double valorFijo = 110.38;                                                                               
	double valorVariable = 0.794;                                                                            
	public String getCategoriaActual() { return categoriaR; }                                                
    public double getValorFijo() { return valorFijo; }                                                       
    public double getValorVariable() { return valorFijo; }                                                   
    public void setValorFijo(double valFijo) { this.valorFijo = valFijo; }                                   
    public void setValorVariable(double valVariable) {this.valorVariable = valVariable; }                    
    public void actualizarCategoria(CategoriaContext context) {context.setState(this);}
}                                                                                                            
                                                                                                             
class Categoria6 implements StateCategoria {                                                                 
	String categoriaR = "R6";                                                                                
	double valorFijo = 220.75;                                                                               
	double valorVariable = 0.832;                                                                            
	public String getCategoriaActual() { return categoriaR; }                                                
    public double getValorFijo() { return valorFijo; }                                                       
    public double getValorVariable() { return valorFijo; }                                                   
    public void setValorFijo(double valFijo) { this.valorFijo = valFijo; }                                   
    public void setValorVariable(double valVariable) {this.valorVariable = valVariable; }                    
    public void actualizarCategoria(CategoriaContext context) {context.setState(this);}
}                                                                                                            
                                                                                                             
class Categoria7 implements StateCategoria {                                                                 
	String categoriaR = "R7";                                                                                
	double valorFijo = 443.59;                                                                               
	double valorVariable = 0.851;                                                                            
	public String getCategoriaActual() { return categoriaR; }                                                
    public double getValorFijo() { return valorFijo; }                                                       
    public double getValorVariable() { return valorFijo; }                                                   
    public void setValorFijo(double valFijo) { this.valorFijo = valFijo; }                                   
    public void setValorVariable(double valVariable) {this.valorVariable = valVariable; }                    
    public void actualizarCategoria(CategoriaContext context) {context.setState(this);}
}                                                                                                            
                                                                                                             
class Categoria8 implements StateCategoria {                                                                 
	String categoriaR = "R8";                                                                                
	double valorFijo = 545.96;                                                                               
	double valorVariable = 0.851;                                                                            
	public String getCategoriaActual() { return categoriaR; }                                                
    public double getValorFijo() { return valorFijo; }                                                       
    public double getValorVariable() { return valorFijo; }                                                   
    public void setValorFijo(double valFijo) { this.valorFijo = valFijo; }                                   
    public void setValorVariable(double valVariable) {this.valorVariable = valVariable; }                    
    public void actualizarCategoria(CategoriaContext context) {context.setState(this);}
}                                                                                      
                                                                                                             
class Categoria9 implements StateCategoria {                                                                 
	String categoriaR = "R9";                                                                                
	double valorFijo = 887.19;                                                                               
	double valorVariable = 0.851;                                                                            
	public String getCategoriaActual() { return categoriaR; }                                                
    public double getValorFijo() { return valorFijo; }                                                       
    public double getValorVariable() { return valorFijo; }                                                   
    public void setValorFijo(double valFijo) { this.valorFijo = valFijo; }                                   
    public void setValorVariable(double valVariable) {this.valorVariable = valVariable; }                    
    public void actualizarCategoria(CategoriaContext context) {context.setState(this);}
}
