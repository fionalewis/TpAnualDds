package modelo;
<<<<<<< HEAD

import java.io.FileNotFoundException;
import java.io.FileReader;
=======
import java.io.FileNotFoundException;
>>>>>>> af51f5c11f174cf8936e2b42b0e0a51995997726
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
	private List<Double> horasUsoMax = new ArrayList<Double>();
	private List<Double> horasUsoMin = new ArrayList<Double>();
	private static List<DispositivoInteligente> dispositivos = new ArrayList<DispositivoInteligente>();
	private double[] listaKWH = new double[this.dispositivos.size()];
	private List<double[]> posicionCanonica = new ArrayList<double[]>();

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
	
<<<<<<< HEAD
// ----------------------Metodos para armar la funcion economica con cantidad variable de argumentos -------------------------------
<<<<<<< HEAD
=======
	
	// ------------- Metodos para armar la funcion economica con cantidad variable de argumentos -------------- 
>>>>>>> af51f5c11f174cf8936e2b42b0e0a51995997726

	@SuppressWarnings("unchecked")
	public PointValuePair aplicarMetodoSimplex(List<DispositivoInteligente> disp) throws FileNotFoundException, InstantiationException, IllegalAccessException{ 
		
		DispositivosRepository repoDispo = new DispositivosRepository();
		repoDispo.importarDispoDeJson();
		
		//this.dispositivos = (List<DispositivoInteligente>) (List<?>) repoDispo.filtrarRepresentatesDeTipos((List<Dispositivo>) (List<?>) disp);
<<<<<<< HEAD
		this.dispositivos = disp;		
=======
		this.dispositivos = disp;
>>>>>>> af51f5c11f174cf8936e2b42b0e0a51995997726
		
		List<Integer> listCantidadDeCadaTipo = new ArrayList<Integer>();
		//los x (cant horas q pueden consumir) q paso la misma cant de unos que la cant de dispositivos q tengo
		listCantidadDeCadaTipo = repoDispo.generarListaDeCantDeCadaTipo((List<Dispositivo>) (List<?>) disp);
		
		double[] listaDeCantidades = new double[listCantidadDeCadaTipo.size()];
		listaDeCantidades = convertirListToCoef(listCantidadDeCadaTipo);
		listaDeCantidades = revertirArray(listaDeCantidades);
		crearFuncionEconomica(listaDeCantidades);  
<<<<<<< HEAD
=======
		
		
>>>>>>> af51f5c11f174cf8936e2b42b0e0a51995997726
		
=======
	public PointValuePair aplicarMetodoSimplex(List<DispositivoInteligente> disp) throws FileNotFoundException, InstantiationException, IllegalAccessException{ 
		
		dispositivos = disp;
		List<String> tipos = listaDeDescrip();
		Map<String,Integer> cantPorTipoDescrip = countFrequencies(tipos); 
		double[] listaCantidades = arrayCantidades(cantPorTipoDescrip);    
		listaCantidades = revertirArray(listaCantidades);
        crearFuncionEconomica(listaCantidades);
>>>>>>> origin/Entrega2
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
			//2. Donde decia 44064 puse 612 porque es el limite que corresponde al mes (sino los valores de los Xi nunca van a dar menores al horasMax)
			agregarRestriccion(Relationship.LEQ, 440640, listaKWH); // kwh2.x2 + kwh1.x1 + kwh0.x0 <= 44064
			
			int j =0; int k = 0; int tam = dispositivos.size()*2;
			for(int i=1; i<=tam; i++){
				if(i%2==0){ //par --> lim superior, <=
					agregarRestriccion(Relationship.LEQ,horasUsoMax.get(j),posicionCanonica.get(j));
					j++;
				} else { //impar --> lim inferior, >=
					agregarRestriccion(Relationship.GEQ,horasUsoMin.get(k),posicionCanonica.get(k));
					k++;
				}
		}
	}
	
	//genera argumentos para las agregar restricciones de mayor y menor de kwh, y su versor posicion
	
	public void generarArgumentos(){
		
		//llena la lista de horas uso maximo
		dispositivos.stream().forEach(d -> horasUsoMax.add(d.getHorasUsoMax()));
		
		//llena la lista de horas uso minimo
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
	
//--------------------------------------------------------
	
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
	
	public double[] convertirListToCoef(List<Integer> list){
		
		double[] target = new double[list.size()];
		 for (int i = 0; i < target.length; i++) {
		    target[i] = list.get(i).doubleValue();
		 }
		 return target;
	}
<<<<<<< HEAD
	
<<<<<<< HEAD
// ---------------------------------------------------- Duplicados de Mari para probar otra forma (tienen explicado que cambie, son pocas cosas igual)

	public void agregarRestriccionesSALO(){
		
		//Lo unico cambie en este metodo es:
		//1. generarArgumentos() por generarArgumentosSALO() para no pisar tu metodo y poder cambiar detalles del otro
		//2. Donde decia 44064 puse 612 porque es el limite que corresponde al mes (sino los valores de los Xi nunca van a dar menores al horasMax)
		
		//cargo datos a las listas
		generarArgumentosSALO();
		//genero las restricciones
		// kwh2.x2 + kwh1.x1 + kwh0.x0 <= 44064, puse 612 por el ejemplo del TP, en un mes tiene que gastar menos de 612kWh
		agregarRestriccion(Relationship.LEQ,612, listaKWH); // kwh2.x2 + kwh1.x1 + kwh0.x0 <= 612
		int j =0; int k = 0; int tam = dispo.size()*2;//cantidadesxtipo.size()*2;//dispo.size()*2;
		for(int i=1; i<=tam; i++){
			if(i%2==0){  //par --> lim inferior, >=
				agregarRestriccion(Relationship.LEQ,horasUsoMax.get(k),posicionCanonica.get(k)); //GEQ
				k++;
			} else {//impar --> lim superior, <=
				agregarRestriccion(Relationship.GEQ,horasUsoMin.get(j),posicionCanonica.get(j)); //LEQ
				j++;
			}
		}
	}

	public void generarArgumentosSALO(){
		
		//Lo que cambie en este metodo fue que use la lista 'dispo' que es de <Dispositivo> en vez de 'dispositivos' que es de <DispositivoInteligente>
	
		//llena la lista de horas uso maximo

		dispo.stream().forEach(d -> horasUsoMax.add(d.getHorasUsoMax()));
	
		//llena la lista de horas uso minimo
		dispo.stream().forEach(d -> horasUsoMin.add(d.getHorasUsoMin()));
	
		//llena la lista de versores canonicos
		double[] versor = null;
		for(int i=0; i<dispo.size(); i++){
			versor = generarVersorCanonica(dispo.size(),i);
			posicionCanonica.add(versor);
		}
		//al reves porque lo necesita en este orden: f(x0,x1,x2) = x2 + x1 + x0
		List<double[]> reverseView = Lists.reverse(posicionCanonica); 
		posicionCanonica = reverseView;
			
		double[] listaKWHTemp = new double[dispo.size()];
		for(int i=0; i<dispo.size(); i++){
			listaKWHTemp[i] = dispo.get(i).getkWh();
		}
		listaKWH = revertirArray(listaKWHTemp);
	}

//---------------------------------------------------- Otros metodos que use
=======
>>>>>>> origin/Entrega2

	public Map<String,Integer> countFrequencies(List<String> tipos) {
 		Map<String,Integer> cantportipo = new HashMap<String,Integer>();
        Set<String> st = new HashSet<String>(tipos);
        for(String tipo : tipos) {
        	if(!cantportipo.containsKey(tipo)) {
        	cantportipo.put(tipo,Collections.frequency(tipos,tipo));
        	}
        }
        return cantportipo;
    }
	
	public static List<String> listaDeDescrip(){
		List<String> tiposDescrip = new ArrayList<>();
		
		for(int i =0;i<dispositivos.size();i++) {
			String unaDescrip = dispositivos.get(i).getEquipoConcreto();
			tiposDescrip.add(unaDescrip);
		}
		
		return tiposDescrip;
	}
		
	public double[] arrayCantidades(Map<String,Integer> cantPorTipoDesc) {
		double[] arrayCantidades = new double[cantPorTipoDesc.size()];
		int i = 0;
		for(Entry<String,Integer> unValor : cantPorTipoDesc.entrySet()) {
			arrayCantidades[i] = unValor.getValue().doubleValue();
			i++;
		}
		return arrayCantidades;
	}

}
=======
}
>>>>>>> af51f5c11f174cf8936e2b42b0e0a51995997726
