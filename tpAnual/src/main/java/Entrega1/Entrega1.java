package Entrega1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import modelo.Actuador.Actuador;
import modelo.Reglas.Condicion;
import modelo.Reglas.Regla;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.Sensor;
import modelo.users.Cliente;

public class Entrega1 {
	
	private static Scanner in;
	private static Cliente nico = new Cliente();
	private static DispositivoInteligente aircon = new DispositivoInteligente("Aire Acondicionado",0.14,0.1);
	private static DispositivoEstandar tele = new DispositivoEstandar("Televisor",0.14,5);
	private static DispositivoEstandar ventilador = new DispositivoEstandar("Ventilador",0.14,5);
	private static Sensor sensorTempIN = new Sensor("temperaturaIN",aircon);
	private static Sensor sensorTempOUT = new Sensor("temperaturaOUT",aircon);
	private static Sensor sensorHumedad = new Sensor("humedad",aircon);
	private static Sensor sensorMovimiento = new Sensor("movimiento",aircon);
	
	private static Condicion tempIgual30;
	private static Condicion humedadMenor50;
	private static Condicion hayGenteCasa;
	private static Condicion tempInMayorOut;
	
	private static Actuador prenderAire = new Actuador(1,"Prender el aire");
	private static Actuador prenderLuz = new Actuador(2,"Prender la luz");
	
	private static Regla reglaCopada = new Regla("Una regla muy copada",aircon,"AND");
	private static List<Regla> reglasExistentes = new ArrayList<Regla>();
	
	public static void main(String[] args){
		init();
		menuPrincipal();
	}
	
	public static void menuPrincipal(){
		System.out.print("Bienvenido Nico! \nPor favor elija una opcion:"
			+ "\n1. Ver tus dispositivos estandares"
			+ "\n2. Ver tus dispositivos inteligentes"
			+ "\n3. Ver tus puntajes"
			+ "\n4. Ver Reglas\n");
			
		in = new Scanner(System.in);
		
		switch(in.nextInt()){
		case 1:
			dispoEstandares();
			break;
		case 2:
			dispoInteligentes();
			break;
		case 3:
			System.out.print(nico.getPuntos());
			break;
		case 4:
			reglas();
		default: menuPrincipal();
		}
	}
	
	//dispositivos estandares
	public static void dispoEstandares(){
		List<DispositivoEstandar> dispos = nico.getDispEstandar();
		dispos.stream().forEach(d -> System.out.print(d.getNombreDisp() + "\n"));
		
		System.out.print("\nPor favor elija una opcion:"
			+ "\n1. Convertir un dispositivo"
			+ "\n2. Agregar un nuevo dispositivo"
			+ "\n3. Quitar un dispositivo."
			+ "\n4. Volver al menu principal\n");
				
		in = new Scanner(System.in);
			
		switch(in.nextInt()){
		case 1:
			convertirDispEstandar();
			break;
		case 2:
			agregarDispo(false);
			break;
		case 3:
			quitarDispo(false);
			break;
		case 4:
			menuPrincipal();
			break;
		default: dispoEstandares();
		}
	}
	
	public static void convertirDispEstandar(){
		
		List<DispositivoEstandar> dispos = nico.getDispEstandar();
		System.out.println("Elija un dispositivo: \n");
		imprimirDispoS();
		
		in = new Scanner(System.in);
		DispositivoEstandar dis = dispos.get(in.nextInt());
		nico.agregarModuloAdaptador(dis, 0.05);
		
		System.out.println("\nTus dispositivos inteligentes son: \n");
		imprimirDispoI();
		System.out.println("\nTus dispositivos Estandares son: \n");
		imprimirDispoS();
		
		posConvertir();
		
	}
	
	public static void posConvertir(){
		
		System.out.print("\nPor favor elija una opcion:"
				+ "\n1. Convertir otro dispositivo"
				+ "\n2. Volver al menu principal\n");
			
			in = new Scanner(System.in);
				
			switch(in.nextInt()){
			case 1:
				convertirDispEstandar();
				break;
			case 2:
				menuPrincipal();
				break;
			default: posConvertir();
			}
			
	}
	
	//dispositivos inteligentes
	public static void dispoInteligentes(){
		
		List<DispositivoInteligente> dispos = nico.getDispInteligente();
		System.out.println("\nTus dispositivos inteligentes son: \n");
		imprimirDispoI();
		
		System.out.print("\nPor favor elija una opcion:"
			+ "\n1. Ver dispositivos encendidos"
			+ "\n2. Ver dispositivos apagados"
			+ "\n3. Agregar dispositivo nuevo"
			+ "\n4. Quitar un dispositivo"
			+ "\n5. Seleccionar un dispositivo"
			+ "\n6. Volver al menu principal\n");
				
		in = new Scanner(System.in);
			
		switch(in.nextInt()){
		case 1:
			dispos.stream().forEach(d -> {
				if(d.estaEncendido())
					System.out.println(d.getNombreDisp() + "\n");
			});
			break;
		case 2:
			dispos.stream().forEach(d -> {
				if(d.estaApagado())
					System.out.println(d.getNombreDisp() + "\n");
			});
			break;
		case 3:
			agregarDispo(true);
			break;
		case 4:
			quitarDispo(true);
			break;
		case 5:
			SeleccionarDispo();
			break;
		case 6:
			menuPrincipal();
			break;
		default:dispoInteligentes();
		}
	}
	
	public static void quitarDispo(boolean tipoDispo){
		
		if(tipoDispo){
			System.out.println("\nElija un dispositivo: ");
			imprimirDispoI();
			in = new Scanner(System.in);
			DispositivoInteligente dis = nico.getDispInteligente().get(in.nextInt());
			nico.quitarDispInteligente(dis);
			System.out.println("Tus dispositivos inteligentes son:\n");
			imprimirDispoI();
			System.out.println("Tus dispositivos estandares son:\n");
			imprimirDispoS();
		}else{
			System.out.println("\nElija un dispositivo: ");
			imprimirDispoS();
			in = new Scanner(System.in);
			DispositivoEstandar dis = nico.getDispEstandar().get(in.nextInt());
			nico.quitarDispEstandar(dis);
			System.out.println("Tus dispositivos inteligentes son:\n");
			imprimirDispoI();
			System.out.println("Tus dispositivos estandares son:\n");
			imprimirDispoS();
		}
		
		posQuitar(tipoDispo);
		
	}
	
	public static void posQuitar(boolean tipoDispo){
		System.out.print("\nPor favor elija una opcion:"
			+ "\n1. Quitar otro dispositivo"
			+ "\n2. Volver al menu principal\n");
			
		in = new Scanner(System.in);
				
		switch(in.nextInt()){
		case 1:
			quitarDispo(tipoDispo);
			break;
		case 2:
			menuPrincipal();
			break;
		default: posAgregarDispo(tipoDispo);
		}
	}
	
	public static void SeleccionarDispo(){
		System.out.println("Elige un dispositivo:\n");
		imprimirDispoI();
		in = new Scanner(System.in);
		
		DispositivoInteligente dis = nico.getDispInteligente().get(in.nextInt());
		if(dis.estaApagado()){
			System.out.println("\nDispositivo apagado");
		}else if(dis.estaEncendido() && !dis.estaEnAhorro()){
			System.out.println("\nDispositivo encendido");
		}else if(dis.estaEncendido() && dis.estaEnAhorro()){
			System.out.println("\nDispositivo encendido y en modo ahorro de energia");
		}
		
		System.out.println("\nDispositivo apagado"
			+ "\nElija una opcion:"
			+ "\n1. Encenderme"
			+ "\n2. Apagarme"
			+ "\n3. Activar Modo Ahorro de Energia"
			+ "\n4. Ver sensores"
			+ "\n5. Volver al menu anterior"
			+ "\n6. Volver al menu principal");
			
		in = new Scanner(System.in);
			
		switch(in.nextInt()){
		case 1:
			dis.encender();
			break;
		case 2:
			dis.apagar();
			break;
		case 3:
			dis.ahorroEnergia();
			break;
		case 4:
			sensores(dis);
			break;
		case 5:
			dispoInteligentes();
			break;
		case 6:
			menuPrincipal();
			break;
		default:SeleccionarDispo();
		}		
	}
	
	//dispositivos en general
	public static void agregarDispo(boolean tipoDispo){//0 estandar 1 inteligente
		
		String nombre = " ";
		double kwh;
		double ahorroOConsumo;
		System.out.println("\nIngrese un nombre:");
		in = new Scanner(System.in);
		nombre = in.toString();
		
		System.out.println("\nIngrese un kwh:");
		in = new Scanner(System.in);
		kwh = in.nextFloat();
		
		if(tipoDispo){
			System.out.println("\nIngrese un kwhAhorro:");
			in = new Scanner(System.in);
			ahorroOConsumo = in.nextFloat();		
			DispositivoInteligente dispo = new DispositivoInteligente(nombre,kwh,ahorroOConsumo);
			nico.agregarDispInteligente(dispo);
		} else{
			System.out.println("\nIngrese horas consumidas:");
			in = new Scanner(System.in);
			ahorroOConsumo = in.nextFloat();
			DispositivoEstandar dispo = new DispositivoEstandar(nombre,kwh,(int) ahorroOConsumo);
		}
		
		System.out.println("\nTus dispositivos inteligentes son: \n");
		imprimirDispoI();
		System.out.println("\nTus dispositivos Estandares son: \n");
		imprimirDispoS();
		
		posAgregarDispo(tipoDispo);
	}
	
	public static void posAgregarDispo(boolean tipoDispo){
		
		System.out.print("\nPor favor elija una opcion:"
				+ "\n1. Agregar otro dispositivo"
				+ "\n2. Volver al menu principal\n");
			
			in = new Scanner(System.in);
				
			switch(in.nextInt()){
			case 1:
				agregarDispo(tipoDispo);
				break;
			case 2:
				menuPrincipal();
				break;
			default: posAgregarDispo(tipoDispo);
			}
			
	}

	public static void imprimirDispoI(){
		List<DispositivoInteligente> dispos = nico.getDispInteligente();
		int size = dispos.size();
		for(int i=1; i<size; i++){
		dispos.stream().forEach(d -> System.out.print(dispos.indexOf(d) + ". " + d.getNombreDisp() + "\n"));
		}
	}
	
	public static void imprimirDispoS(){
		List<DispositivoEstandar> dispos = nico.getDispEstandar();
		int size = dispos.size();
		for(int i=1; i<size; i++){
		dispos.stream().forEach(d -> System.out.print(dispos.indexOf(d) + ". " + d.getNombreDisp() + "\n"));
		}
	}
	
	//sensores
	public static void sensores(DispositivoInteligente dispo){
		System.out.println("Elija una opcion:"
			+ "\n1. Seleccionar un sensor para mas operaciones"
			+ "\n2. Agregar un nuevo sensor"
			+ "\n3. Quitar un sensor"
			+ "\n4. Volver al menu anterior"
			+ "\n5. Volver al menu principal");
		in = new Scanner(System.in);
		
		switch(in.nextInt()){
		case 1:
			seleccionesSensor(dispo);
			break;
		case 2:
			agregarSensor(dispo);
			break;
		case 3:
			quitarSensor(dispo);
			break;
		case 4:
			SeleccionarDispo();
			break;
		case 5:
			menuPrincipal();
			break;
		default:SeleccionarDispo();
		}	
	}
	
	public static void seleccionesSensor(DispositivoInteligente dispo){
		Sensor sen = seleccionarSen(dispo);
		posSeleccionarSensor(sen,dispo);
	}
	
	public static Sensor seleccionarSen(DispositivoInteligente dispo){
		System.out.println("\nElija un sensor:");
		Set<String> sensoresSet = dispo.getSensores().keySet();
	    int i = 0;
		for(String sen:sensoresSet){
			System.out.println(i + ". " + sen + "\n");
			i++;
		}
		List<String> sensoresList = new ArrayList<>(sensoresSet);
		in = new Scanner(System.in);
		
		String nombre = sensoresList.get(in.nextInt());
		return dispo.getSensores().get(nombre);
	}
	
	public static void posSeleccionarSensor(Sensor sensor, DispositivoInteligente dispo){
		System.out.println("Elija una opcion:"
				+ "\n1. Ver valor"
				+ "\n2. Ver subscriptores"
				+ "\n3. Ver intervalo de medicion"
				+ "\n4. Volver al menu anterior"
				+ "\n5. Volver al menu principal");
		
		in = new Scanner(System.in);
		
		switch(in.nextInt()){
		case 1:
			valorSensor(sensor, dispo);
			break;
		case 2:
			sensor.getSubscribers().forEach(s -> System.out.println(s.getExpresion()));
			break;
		case 3:
			System.out.println(sensor.getIntervalo());
			break;
		case 4:
			sensores(dispo);
			break;
		case 5:
			menuPrincipal();
			break;
		default:posSeleccionarSensor(sensor,dispo);
		}
		
	}
	
	public static void valorSensor(Sensor sensor, DispositivoInteligente dispo){
		System.out.println(sensor.getMagnitud());
		
		System.out.println("\nElija una opcion:"
				+ "\n1. Subir valor"
				+ "\n2. Bajar valor"
				+ "\n3. Volver al menu anterior"
				+ "\n4. Volver al menu principal");

		in = new Scanner(System.in);
			
		switch(in.nextInt()){
		case 1:
			subirValorSensor(sensor,dispo);
			break;
		case 2:
			bajarValorSensor(sensor,dispo);
			break;
		case 3:
			sensores(dispo);
			break;
		case 4:
			menuPrincipal();
			break;
		default:valorSensor(sensor,dispo);
		}
	}
	
	public static void subirValorSensor(Sensor sensor,DispositivoInteligente dispo){
		System.out.println("\nIngrese cuanto desea subir:\n");
		in = new Scanner(System.in);
		sensor.aumentarMagnitud(in.nextInt());
		System.out.println("\nEl valor final del sensor es de: "
				+ sensor.getMagnitud());
		posSubirValorSensor(sensor,dispo);
	}

	public static void posSubirValorSensor(Sensor sensor,DispositivoInteligente dispo){
		System.out.println("\nElija una opcion:"
				+ "\n1. Seguir subiendo"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");

		in = new Scanner(System.in);
			
		switch(in.nextInt()){
		case 1:
			subirValorSensor(sensor,dispo);
			break;
		case 2:
			valorSensor(sensor,dispo);
			break;
		case 3:
			menuPrincipal();
			break;
		default:posSubirValorSensor(sensor,dispo);
		}
	}

	public static void bajarValorSensor(Sensor sensor,DispositivoInteligente dispo){
		System.out.println("\nIngrese cuanto desea bajar:\n");
		in = new Scanner(System.in);
		sensor.disminuirMagnitud(in.nextInt());
		System.out.println("\nEl valor final del sensor es de: "
				+ sensor.getMagnitud());
		posBajarValorSensor(sensor,dispo);
	}

	public static void posBajarValorSensor(Sensor sensor,DispositivoInteligente dispo){
		System.out.println("\nElija una opcion:"
				+ "\n1. Seguir bajando"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");

		in = new Scanner(System.in);
			
		switch(in.nextInt()){
		case 1:
			bajarValorSensor(sensor,dispo);
			break;
		case 2:
			valorSensor(sensor,dispo);
			break;
		case 3:
			menuPrincipal();
			break;
		default:posBajarValorSensor(sensor,dispo);
		}
	}
	
	public static void agregarSensor(DispositivoInteligente dispo){
		String nombre;
		double valor;
		System.out.println("\nIngrese un nombre:");
		in = new Scanner(System.in);
		nombre = in.toString();
		System.out.println("\nIngrese un valor inicial para el sensor:");
		in = new Scanner(System.in);
		valor = in.nextInt();
		
		Sensor sensor = new Sensor(nombre,dispo);
		sensor.setMagnitud(valor);
		dispo.agregarSensor(sensor);
		 
		System.out.println("\nTus sensore son:");
		Set<String> sensoresSet = dispo.getSensores().keySet();
	    int i = 0;
		for(String sen:sensoresSet){
			System.out.println(i + ". " + sen + "\n");
			i++;
		}
		
		posAgregarSensor(dispo);
	}
	
	public static void posAgregarSensor(DispositivoInteligente dispo){
		System.out.println("\nElija una opcion:"
				+ "\n1. Agregar otro sensor"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
		in = new Scanner(System.in);
		
		switch(in.nextInt()){
		case 1:
			agregarSensor(dispo);
			break;
		case 2:
			seleccionesSensor(dispo);
			break;
		case 3:
			menuPrincipal();
			break;
		default:posAgregarSensor(dispo);
		}
	}
	
	public static void quitarSensor(DispositivoInteligente dispo){
		System.out.println("\nElija un sensor para quitar:");
		Set<String> sensoresSet = dispo.getSensores().keySet();
	    int i = 0;
		for(String sen:sensoresSet){
			System.out.println(i + ". " + sen + "\n");
			i++;
		}
		List<String> sensoresList = new ArrayList<>(sensoresSet);
		in = new Scanner(System.in);
		
		String nombre = sensoresList.get(in.nextInt());
		Sensor sensor = dispo.getSensores().get(nombre);
		
		dispo.quitarSensor(sensor);
		System.out.println("\nTus sensores son: \n");
		sensoresSet = dispo.getSensores().keySet();
		for(String sen:sensoresSet){
			System.out.println(sen + "\n");
		}
		
		posQuitarSensor(dispo);
		
	}
	
	public static void posQuitarSensor(DispositivoInteligente dispo){
		System.out.println("\nElija una opcion:"
				+ "\n1. Quitar otro sensor"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
		in = new Scanner(System.in);
		
		switch(in.nextInt()){
		case 1:
			quitarSensor(dispo);
			break;
		case 2:
			seleccionesSensor(dispo);
			break;
		case 3:
			menuPrincipal();
			break;
		default:posAgregarSensor(dispo);
		}
	}
	
	//reglas
	public static void reglas(){
		System.out.println("\nElija una opcion:"
				+ "\n1. Seleccionar una regla para mas opciones"
				+ "\n2. Agregar una regla nueva"
				+ "\n3. Quitar  una regla"
				+ "\n4. Volver al menu principal");

		in = new Scanner(System.in);
			
		switch(in.nextInt()){
		case 1:
			seleccionarRegla();
			break;
		case 2:
			agregarRegla();
			break;
		case 3:
			quitarRegla();
			break;
		case 4:
			menuPrincipal();
		default:reglas();
		}
	}
	
	public static void seleccionarRegla(){
		System.out.println("Seleccione una regla para mas operaciones: \n");
		int i = 1;
		for(Regla reg:reglasExistentes){
			System.out.println(i + ". " + reg.getNombreRegla());
			i++;
		}
		
		in = new Scanner(System.in);
		
		Regla reg = reglasExistentes.get(in.nextInt());
		List<Condicion> conds = reg.getCondiciones();
		List<Actuador> acts = reg.getActuadores();
		
		
		System.out.println("Las condiciones son: \n");
		conds.stream().forEach(c -> System.out.println(conds.indexOf(c) + ". " 
								+ c.getExpresion()));
		
		System.out.println("Los actuadores son: \n");
		acts.stream().forEach(a -> System.out.println(acts.indexOf(a) + ". " +a.getOrden()));
		
		System.out.println("El criterio de comparacion es: ");
		System.out.println(reg.getComparacionCondiciones());
		
		System.out.println("\nElija una opcion:"
				+ "\n1. Seleccionar Condicion"
				+ "\n2.	Agregar Condicion"
				+ "\n3. Quitar Condicion"
				+ "\n4. Seleccionar Actuador"
				+ "\n5. Agregar Actuador"
				+ "\n6. Quitar Actuador"
				+ "\n7. Cambiar criterio de comparacion"
				+ "\n8. Aplicar regla"
				+ "\n9. Volver al menu anterior"
				+ "\n10. Volver al menu principal");

		in = new Scanner(System.in);
			
		switch(in.nextInt()){
		case 1:
			seleccionarCondicion(reg);
			break;
		case 2:
			agregarCondicion(reg);
			break;
		case 3:
			quitarCondicion(reg);
			break;
		case 4:
			agregarActuador(reg);
			break;
		case 5:
			seleccionarActuador(reg);
			break;
		case 6:
			quitarActuador(reg);
			break;
		case 7:
			cambiarCriterioComp(reg);
			break;
		case 8:
			aplicarRegla(reg);
			break;
		case 9:
			reglas();
			break;
		case 10:
			menuPrincipal();
		default:seleccionarRegla();
		}
		
	}
	
	//condiciones
	public static void seleccionarCondicion(Regla reg){
		System.out.println("\nSeleccione una condicion: ");
		in = new Scanner(System.in);
		Condicion con = reg.getCondiciones().get(in.nextInt());
		
		System.out.println("\nElija una opcion: "
				+ "\n1. Evaluar la condicion seleccionada"
				+ "\n2. Quitar la condicion seleccionada"
				+ "\n3. Volver al menu anterior"
				+ "\n4. Volver al menu principal");
		
		in = new Scanner(System.in);
		
		switch(in.nextInt()){
		case 1:
			evaluarCondicion(con,reg);
			break;
		case 2:
			quitarCondicion(reg);
			break;
		case 3:
			seleccionarRegla();
			break;
		case 4:
			menuPrincipal();
			break;
		default: seleccionarCondicion(reg);
		}
		
	}
	
	public static void evaluarCondicion(Condicion con, Regla reg){
		con.update();
		System.out.println("\nElija una opcion: "
				+ "\n1. Volver al menu anterior"
				+ "\n2. Volver al menu principal");
		
		in = new Scanner(System.in);
		
		switch(in.nextInt()){
		case 1:
			seleccionarCondicion(reg);
			break;
		case 2:
			menuPrincipal();
			break;
		default: evaluarCondicion(con,reg);
		}
	}
	
	public static void agregarCondicion(Regla reg){
		System.out.println("\nSeleccione un dispositivo: ");
		imprimirDispoI();
		in = new Scanner(System.in);
		DispositivoInteligente dispo = nico.getDispInteligente().get(in.nextInt()); 

		Sensor sen = seleccionarSen(dispo);
		
		System.out.println("\nIngrese criterio de comparacion: (MAYOR, MENOR, IGUAL o DISTINTO)");
		in = new Scanner(System.in);
		String criterio = in.toString();
		
		System.out.println("\nCon que desea compararlo: "
				+ "\n1. Otro Sensor"
				+ "\n2. Un valor fijo");
		in = new Scanner(System.in);
		
		if(in.nextInt() == 1){
			Sensor sen2 = seleccionarSen(dispo);
			reg.crearCondicionDosSensores(sen, sen2, criterio);
		} else if(in.nextInt() == 2){
			System.out.println("\nIngrese el valor a comparar: ");
			in = new Scanner(System.in);
			int valorFijo = in.nextInt();
			reg.crearCondicionSensoresYValor(sen, valorFijo, criterio);
		} else {
			System.out.println("\nOpcion invalida\n");
			agregarCondicion(reg);
		}
		
		System.out.println("La lista de condiciones quedo asi: \n");
		List<Condicion> conds = reg.getCondiciones();
		conds.stream().forEach(c -> System.out.println(c.getExpresion()));
		
		posAgregarCondicion(reg);
	}

	public static void posAgregarCondicion(Regla reg){
		System.out.println("\nElija una opcion:"
				+ "\n1. Agregar otra condicion"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
		in = new Scanner(System.in);
		
		switch(in.nextInt()){
		case 1:
			agregarCondicion(reg);
			break;
		case 2:
			seleccionarCondicion(reg);
			break;
		case 3:
			menuPrincipal();
			break;
		default:posAgregarCondicion(reg);
		}
	}
	
	public static void quitarCondicion(Regla reg){
		System.out.println("\nSeleccione una condicion para quitar: ");
		in = new Scanner(System.in);
		Condicion con = reg.getCondiciones().get(in.nextInt());
		reg.quitarCondicion(con);
		
		List<Condicion> conds = reg.getCondiciones();
		
		System.out.println("La lista de condiciones quedo asi: \n");
		conds.stream().forEach(c -> System.out.println(c.getExpresion()));
		
		System.out.println("\nElija una opcion: "
				+ "\n1. Quitar otra condicion"
				+ "\n1. Volver al menu anterior"
				+ "\n2. Volver al menu principal");
		
		in = new Scanner(System.in);
		
		switch(in.nextInt()){
		case 1:
			quitarCondicion(reg);
			break;
		case 2:
			seleccionarCondicion(reg);
			break;
		case 3:
			menuPrincipal();
			break;
		default: quitarCondicion(reg);
		}
	}

	// actuadores
	public static void seleccionarActuador(Regla reg){
		
	}

	public static void agregarActuador(Regla reg){
		
	}

	public static void quitarActuador(Regla reg){
		
	}

	public static void cambiarCriterioComp(Regla reg){
		
	}

	public static void aplicarRegla(Regla reg){
		
	}

	public static void posSeleccionarRegla(){
		
	}
	
	public static void agregarRegla(){
		
	}
	
	public static void posAgregarRegla(){
		
	}
	
	public static void quitarRegla(){
		
	}
	public static void posQuitarRegla(){
		
	}
	
	public static void init(){
		nico.setNombre("Nicolas");
		nico.setApellido("Contreras");
		nico.agregarDispInteligente(aircon);
		nico.agregarADispEstandar(tele);
		nico.agregarADispEstandar(ventilador);
		aircon.agregarSensor(sensorTempIN);
		aircon.agregarSensor(sensorTempOUT);
		aircon.agregarSensor(sensorHumedad);
		aircon.agregarSensor(sensorMovimiento);
		aircon.setMagnitudSensor("temperaturaIN",30);
		aircon.setMagnitudSensor("temperaturaOUT",25);
		aircon.setMagnitudSensor("humedad",45);
		aircon.setMagnitudSensor("movimiento",1); 
		reglaCopada.crearCondicionSensoresYValor(sensorTempIN, 30, "IGUAL");
		reglaCopada.crearCondicionDosSensores(sensorTempIN, sensorTempOUT, "MAYOR");
		reglaCopada.crearCondicionSensoresYValor(sensorHumedad,50,"MENOR");
		reglaCopada.crearCondicionSensoresYValor(sensorMovimiento,1,"IGUAL");
		reglaCopada.agregarActuador(prenderAire);
		reglaCopada.agregarActuador(prenderLuz);
		tempIgual30 = reglaCopada.getCondicionConIndice(0);
		humedadMenor50 = reglaCopada.getCondicionConIndice(1);
		hayGenteCasa = reglaCopada.getCondicionConIndice(2);
		reglasExistentes.add(reglaCopada);
	}
}
