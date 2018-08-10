package modelo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.exception.TooManyIterationsException;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import modelo.devices.Dispositivo;
import modelo.devices.DispositivoInteligente;

public class MetodoSimplex {
	private SimplexSolver simplex = new SimplexSolver();
	private LinearObjectiveFunction funcionEconomica;
	private Collection<LinearConstraint> restricciones = new ArrayList<LinearConstraint>();
	private GoalType objetivo = GoalType.MAXIMIZE;
	private boolean variablesPositivas = true;
	
	/*public MetodoSimplex(GoalType objetivo, boolean variablesPositivas) {
		this.variablesPositivas = variablesPositivas;
		this.objetivo = objetivo;
		this.restricciones = new ArrayList<LinearConstraint>();
		this.simplex = new SimplexSolver();
	}*/
	
	public MetodoSimplex(){}
	
	public void crearFuncionEconomica(double ... coeficientes) {
		this.funcionEconomica = new LinearObjectiveFunction(coeficientes, 0);
	}
	
	public void agregarRestriccion(Relationship unComparador, double valorAcomprar, double ... coeficientes) {
		this.restricciones.add(new LinearConstraint(coeficientes,unComparador, valorAcomprar));
	}
	
	public PointValuePair resolver() throws TooManyIterationsException{
		return this.simplex.optimize(
										new MaxIter(100),
										this.funcionEconomica,
										new LinearConstraintSet(this.restricciones),
										this.objetivo,
										new NonNegativeConstraint(this.variablesPositivas)
				);
	}
	
	public PointValuePair aplicarMetodoSimplex(List<DispositivoInteligente> disp){ 
		crearFuncionEconomica(generarListaCoeficiente(disp.size(),1));
		agregarRestricciones(disp);
		return resolver();
	}
	
	public double[] generarListaCoeficiente(int cantCoef,int numero){
		
		double[] coeficientes = new double[cantCoef];
		
		for(int i=0; i<cantCoef; i++){
			coeficientes[i] = numero;
		}
		return coeficientes;
	}
	
	public void agregarRestricciones(List<DispositivoInteligente> dispositivos){
		List<Double> horasUsoMax = new ArrayList<Double>();
		List<Double> horasUsoMin = new ArrayList<Double>();
		double[] listaKWH = null;
		List<double[]> posicionCanonica = null;
		generarArgumentos(dispositivos,horasUsoMax,horasUsoMin,listaKWH,posicionCanonica);
		
		agregarRestriccion(Relationship.LEQ, 440640, listaKWH); // posicion CERO: total kwh y los kwh de los dispositivos
		
		int j =0; int k = 0; int tam = dispositivos.size()*2;
		for(int i=1; i<=tam; i++){
			if(i%2==0){ //par --> lim superior, <=
				agregarRestriccion(Relationship.LEQ,horasUsoMax.get(j),posicionCanonica.get(j));
				j++;
			}else{ //impra --> lim inferior, >=
				agregarRestriccion(Relationship.GEQ,horasUsoMin.get(k),posicionCanonica.get(k));
				k++;
			}
		}
	}
	
	public void generarArgumentos(List<DispositivoInteligente> dispositivos, List<Double> horasUsoMax, 
			List<Double> horasUsoMin, double[] listaKWH, List<double[]> posicionCanonica){
		
		dispositivos.stream().forEach(d -> horasUsoMax.add(d.getHorasUsoMax()));
		
		dispositivos.stream().forEach(d -> horasUsoMin.add(d.getHorasUsoMin()));
		
		dispositivos.stream().forEach(d -> listaKWH[dispositivos.indexOf(d)] = d.getkWh());
		
		double[] versor = null;
		for(int ii=0; ii<dispositivos.size(); ii++){
			versor = generarVersorCanonica(dispositivos.size(),ii);
			posicionCanonica.add(versor);
		}
		
	}
	
	public double[] generarVersorCanonica(int cantDisp, int posicion){
		
		double[] versor = null;
		for(int i=0; i<cantDisp; i++){
			versor[i] = 0;
		} //genero un versor de todos ceros con la cantidad de dispositivos dado
		
		versor[posicion] = 1;
		return versor;
	}
}
