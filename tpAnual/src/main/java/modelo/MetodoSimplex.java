package modelo;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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

import com.google.common.collect.Lists;

import modelo.devices.Dispositivo;
import modelo.devices.DispositivoInteligente;

public class MetodoSimplex {
	private SimplexSolver simplex = new SimplexSolver();
	private LinearObjectiveFunction funcionEconomica;
	private Collection<LinearConstraint> restricciones = new ArrayList<LinearConstraint>();
	private GoalType objetivo = GoalType.MAXIMIZE;
	private boolean variablesPositivas = true;
	private List<Double> horasUsoMax = new ArrayList<Double>();
	private List<Double> horasUsoMin = new ArrayList<Double>();
	private List<DispositivoInteligente> dispositivos = new ArrayList<DispositivoInteligente>();
	private double[] listaKWH = new double[this.dispositivos.size()];
	private List<double[]> posicionCanonica = new ArrayList<double[]>();
	
	/*public MetodoSimplex(GoalType objetivo, boolean variablesPositivas) {
		this.variablesPositivas = variablesPositivas;
		this.objetivo = objetivo;
		this.restricciones = new ArrayList<LinearConstraint>();
		this.simplex = new SimplexSolver();
	}*/
	
	public MetodoSimplex(){}
	
	// --------------------- METODOS DADOS --------------------------------------
	
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
	
	
	// ------------- Metodos para armar la funcion economica con cantidad variable de argumentos -------------- 
	public PointValuePair aplicarMetodoSimplex(List<DispositivoInteligente> disp){ 
		
		this.dispositivos = disp;
		//los x (cant horas q pueden consumir) q paso la misma cant de unos que la cant de dispositivos q tengo
		crearFuncionEconomica(generarListaCoeficiente(this.dispositivos.size(),1));  
		
		agregarRestricciones();
		return resolver();
	}
	
	// restricciones = total kwh, los kwh individuales, relacion mayor o menor, y sus posiciones como versor canonico
	// ver test para entender esto
	public void agregarRestricciones(){
		
		//cargo datos a las listas
		//generarArgumentos(dispositivos,horasUsoMax,horasUsoMin,listaKWH,posicionCanonica);
		generarArgumentos();
		
		//genero las restricciones
		agregarRestriccion(Relationship.LEQ, 440640, listaKWH); // kwh2.x2 + kwh1.x1 + kwh0.x0 <= 44064
		
		int j =0; int k = 0; int tam = dispositivos.size()*2;
		for(int i=1; i<=tam; i++){
			if(i%2==0){ //par --> lim superior, <=
				agregarRestriccion(Relationship.LEQ,horasUsoMax.get(j),posicionCanonica.get(j));
				j++;
			}else{ //impar --> lim inferior, >=
				agregarRestriccion(Relationship.GEQ,horasUsoMin.get(k),posicionCanonica.get(k));
				k++;
			}
		}
	}
	
	//genera argumentos para las agregar restricciones de mayor y menor de kwh, y su versor posicion
	public void generarArgumentos(){
		
		//llena la lista de horas uso maximo
		dispositivos.stream().forEach(d -> horasUsoMax.add(d.getHorasUsoMax()));
		
		//llena la lista de horas uso maximo
		dispositivos.stream().forEach(d -> horasUsoMin.add(d.getHorasUsoMin()));
		
		//llena la lista de versores canonicos
		double[] versor = null;
		for(int i=0; i<dispositivos.size(); i++){
			versor = generarVersorCanonica(dispositivos.size(),i);
			posicionCanonica.add(versor);
		}
		//al reves porque lo necesita en este orden: f(x0,x1,x2) = x2 + x1 + x0
		List<double[]> reverseView = Lists.reverse(posicionCanonica); 
		posicionCanonica = reverseView;
		
		double[] listaKWHTemp = new double[dispositivos.size()];		
		for(int i=0; i<dispositivos.size(); i++){
			listaKWHTemp[i] = dispositivos.get(i).getkWh();
		}
		//dispositivos.stream().forEach(d -> lista[dispositivos.indexOf(d)] = d.getkWh());
		listaKWH = revertirArray(listaKWHTemp);
		
	}
	
	public double[] revertirArray(double[] lista){
		
		for(int i = 0; i < lista.length / 2; i++)
		{
		    double temp = lista[i];
		    lista[i] = lista[lista.length - i - 1];
		    lista[lista.length - i - 1] = temp;
		}
		
		return lista;
	}
	
	public double[] generarVersorCanonica(int cantDisp, int posicion){
		
		double[] versor = null;
		versor = generarListaCoeficiente(cantDisp,0);
		versor[posicion] = 1;
		return versor;
	
	}
	
	public double[] generarListaCoeficiente(int cantCoef,int numero){
		
		double[] coeficientes = new double[cantCoef];
		
		for(int i=0; i<cantCoef; i++){
			coeficientes[i] = numero;
		}
		return coeficientes;
	}
}
