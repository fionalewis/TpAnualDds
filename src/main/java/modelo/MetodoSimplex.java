package modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileNotFoundException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.function.*;
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

import exceptions.ExceptionsHandler;
import modelo.devices.DeviceFactory;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.users.Cliente;
import modelo.users.Cliente.TipoDocumento;

//
	import java.util.HashMap;
	import java.util.Map;
	import java.util.Map.Entry;
	import java.util.*;

@SuppressWarnings("unused")
public class MetodoSimplex {
	
	private SimplexSolver simplex = new SimplexSolver();
	private LinearObjectiveFunction funcionEconomica;
	private Collection<LinearConstraint> restricciones = new ArrayList<LinearConstraint>();
	private GoalType objetivo = GoalType.MAXIMIZE;
	private boolean variablesPositivas = true;
	
	private List<Dispositivo> dispositivos = new ArrayList<>();
	
	public MetodoSimplex(){}
	
	// --------------------- METODOS DADOS --------------------------------------
	
	public void crearFuncionEconomica(double ... coeficientes) {
		this.funcionEconomica = new LinearObjectiveFunction(coeficientes, 0);
	}
	
	public void agregarRestriccion(Relationship unComparador, double valorAcomparar, double ... coeficientes) {
		this.restricciones.add(new LinearConstraint(coeficientes,unComparador, valorAcomparar));
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
	
// ----------------------Metodos para armar la funcion economica con cantidad variable de argumentos -------------------------------

	public PointValuePair aplicarMetodoSimplex(List<Dispositivo> disp) throws FileNotFoundException, InstantiationException, IllegalAccessException{ 
		dispositivos = disp;
		/*List<String> tipos = listaDeDescrip();
		Map<String,Integer> cantPorTipoDescrip = countFrequencies(tipos); 
		double[] listaCantidades = arrayCantidades(cantPorTipoDescrip);    
		listaCantidades = revertirArray(listaCantidades);
        crearFuncionEconomica(listaCantidades);*/
		//double[] posicionesMatriz = inicializarPos(disp.size());
		double[] arraykWhs = conseguirkWhs(disp);
		double[] coefFuncEcon = new double[disp.size()];
		for(int i = 0;i<coefFuncEcon.length;i++) {
			coefFuncEcon[i] = 1;
		}
		crearFuncionEconomica(coefFuncEcon);
		agregarRestricciones(arraykWhs);
        return resolver();
	}
	
	public void agregarRestricciones(double[] arraykWhs){
		agregarRestriccion(Relationship.LEQ,612,arraykWhs); // kWh0.x0 + kWh1.x1 + kWh2.x2 + kwh2.x2 + ... + kWhn.xn <= 612
		int contMax = 0; int contMin = 0; int tam = dispositivos.size()*2;
		for(int i=1; i<=tam; i++){
			if(i%2==0){ //par --> lim superior, <=
				agregarRestriccion(Relationship.LEQ,dispositivos.get(contMax).getHorasUsoMax(),posActual(contMax));
				contMax++;
			} else { //impar --> lim inferior, >=
				agregarRestriccion(Relationship.GEQ,dispositivos.get(contMin).getHorasUsoMin(),posActual(contMin));
				contMin++;
			}
		}
	}
	
	//Para conseguir el array con la posicion actual:
	
	public double[] inicializarPos(int tam) {
		double[] p = new double[tam];
		for(int i = 0;i<p.length;i++) {
			p[i] = 0;
		}
		return p;
	}
	
	public double[] posActual(int posicion) {
		double[] posicionActual = inicializarPos(dispositivos.size());
		posicionActual[posicion] = 1;
		return posicionActual;
	}

//---------------------------------------------------- Otros metodos que use
	
	public double[] conseguirkWhs(List<Dispositivo> disps) {
		double[] kWhs = new double[disps.size()];
		for(int i = 0;i<kWhs.length;i++) {
			kWhs[i] = disps.get(i).getkWh();			
		}		
		return kWhs;
	}
	
	//Para obtener el Map de horas maximas por dispositivo
	
	public Map<String,Double> horasMaxXDisp() throws FileNotFoundException, InstantiationException, IllegalAccessException{
		Map<String,Double> horasXDisp = new HashMap<>();
		PointValuePair simplex = aplicarMetodoSimplex(dispositivos);
		for(int i = 0;i<dispositivos.size();i++) {
			horasXDisp.put(dispositivos.get(i).getNombreDisp() + ", " + dispositivos.get(i).getEquipoConcreto(),
					simplex.getPoint()[i]);
		}
		return horasXDisp;
	}

}