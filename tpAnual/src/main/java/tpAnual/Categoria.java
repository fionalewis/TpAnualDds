package tpAnual;

public class Categoria {
	
	private CategoriaContext context = new CategoriaContext();
	private double consumo;
	private Categoria1 stateR1 = new Categoria1();
	private Categoria2 stateR2 = new Categoria2();
	private Categoria3 stateR3 = new Categoria3();
	private Categoria4 stateR4 = new Categoria4();
	private Categoria5 stateR5 = new Categoria5();
	private Categoria6 stateR6 = new Categoria6();
	private Categoria7 stateR7 = new Categoria7();
	private Categoria8 stateR8 = new Categoria8();
	private Categoria9 stateR9 = new Categoria9();
	
	public Categoria() {}
	public Categoria(double _consumo) {
		consumo = _consumo;
		actualizarCategoria(consumo);
		context.calcularTarifa(consumo);
	}
	
	public void actualizarCategoria(double consumo) {                                                
		if(consumo <= 100 ) { stateR1.actualizarCategoria(context); return; }                                                      
		if(consumo <= 325 ) { stateR2.actualizarCategoria(context); return; }                                   
	  	if(consumo <= 400 ) { stateR3.actualizarCategoria(context); return; }                                   
	  	if(consumo <= 450 ) { stateR4.actualizarCategoria(context); return; }                                   
	  	if(consumo <= 500 ) { stateR5.actualizarCategoria(context); return; }                                   
	  	if(consumo <= 600 ) { stateR6.actualizarCategoria(context); return; }                                   
	  	if(consumo <= 700 ) { stateR7.actualizarCategoria(context); return; }                                   
	  	if(consumo <= 1400) { stateR8.actualizarCategoria(context); return; }                                   
	  	if(consumo > 1400 ) { stateR9.actualizarCategoria(context); return; }                                   
	}
	
	public String categoriaActual() { return context.getState().getCategoriaActual(); }
	public double tarifaActual() { return context.calcularTarifa(consumo); } 
}


