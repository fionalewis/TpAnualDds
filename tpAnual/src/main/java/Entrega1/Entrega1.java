package Entrega1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import Exceptions.CaracterInvalidoException;
import modelo.Actuador.Actuador;
import modelo.Reglas.Condicion;
import modelo.Reglas.Regla;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.Sensor;
import modelo.users.Cliente;

public class Entrega1 {
	
	private static Scanner in = new Scanner(System.in);
	private static int a = 0;
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
	private static List<Condicion> condicionesExistentes = new ArrayList<Condicion>();
	
	private static Actuador prenderAire = new Actuador(1,"Prender el aire");
	private static Actuador prenderLuz = new Actuador(2,"Prender la luz");
	private static List<Actuador> actuadoresExistentes = new ArrayList<Actuador>();
	
	private static Regla reglaCopada = new Regla("Una regla muy copada",aircon,"OR");
	private static List<Regla> reglasExistentes = new ArrayList<Regla>();
	
	public static void main(String[] args) throws CaracterInvalidoException{
		init();
		menuPrincipal();
	}
	
	public static void menuPrincipal() throws CaracterInvalidoException{
		System.out.print("Bienvenido Nico! \nPor favor elija una opcion:"
			+ "\n1. Ver tus dispositivos estandares"
			+ "\n2. Ver tus dispositivos inteligentes"
			+ "\n3. Ver tus puntajes"
			+ "\n4. Ver Reglas\n");
		
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
	public static void dispoEstandares() throws CaracterInvalidoException{
		List<DispositivoEstandar> dispos = nico.getDispEstandar();
		dispos.stream().forEach(d -> System.out.print(d.getNombreDisp() + "\n"));
		
		System.out.print("\nPor favor elija una opcion:"
			+ "\n1. Convertir un dispositivo"
			+ "\n2. Agregar un nuevo dispositivo"
			+ "\n3. Quitar un dispositivo."
			+ "\n4. Volver al menu principal\n");
			
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
	
	public static void convertirDispEstandar() throws CaracterInvalidoException{
		
		List<DispositivoEstandar> dispos = nico.getDispEstandar();
		System.out.println("\nElija un dispositivo: \n");
		imprimirDispoS();
		
		DispositivoEstandar dis = dispos.get(in.nextInt());
		nico.agregarModuloAdaptador(dis, 0.05);
		
		System.out.println("\nTus dispositivos inteligentes son: \n");
		imprimirDispoI();
		System.out.println("\nTus dispositivos Estandares son: \n");
		imprimirDispoS();
		
		posConvertir();
		
	}
	
	public static void posConvertir() throws CaracterInvalidoException{
		
		System.out.print("\nPor favor elija una opcion:"
				+ "\n1. Convertir otro dispositivo"
				+ "\n2. Volver al menu principal\n");
				
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
	public static void dispoInteligentes() throws CaracterInvalidoException{
		
		List<DispositivoInteligente> dispos = nico.getDispInteligente();
		System.out.println("\nMENU DISPOSITICO INTELIGENTES \n" + 
							"Tus dispositivos inteligentes son: \n");
		imprimirDispoI();
		
		System.out.print("\nPor favor elija una opcion:"
			+ "\n1. Ver dispositivos encendidos"
			+ "\n2. Ver dispositivos apagados"
			+ "\n3. Agregar dispositivo nuevo"
			+ "\n4. Quitar un dispositivo"
			+ "\n5. Seleccionar un dispositivo"
			+ "\n6. Volver al menu principal\n");
			
		switch(in.nextInt()){
		case 1:
			System.out.println("\nDispositivos encendidos: \n");
			dispos.stream().forEach(d -> {
				if(d.estaEncendido())
					System.out.println(d.getNombreDisp() + "\n");
			});
			break;
		case 2:
			System.out.println("\nDispositivos apagados: \n");
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
		dispoInteligentes();
		
	}
	
	public static void quitarDispo(boolean tipoDispo) throws CaracterInvalidoException{
		
		if(tipoDispo){
			System.out.println("\nElija un dispositivo: ");
			imprimirDispoI();
			DispositivoInteligente dis = nico.getDispInteligente().get(in.nextInt());
			nico.quitarDispInteligente(dis);
			System.out.println("\nTus dispositivos inteligentes son:\n");
			imprimirDispoI();
			System.out.println("\nTus dispositivos estandares son:\n");
			imprimirDispoS();
		}else{
			System.out.println("\nElija un dispositivo: ");
			imprimirDispoS();
			DispositivoEstandar dis = nico.getDispEstandar().get(in.nextInt());
			nico.quitarDispEstandar(dis);
			System.out.println("\nTus dispositivos inteligentes son:\n");
			imprimirDispoI();
			System.out.println("\nTus dispositivos estandares son:\n");
			imprimirDispoS();
		}
		
		posQuitar(tipoDispo);
		
	}
	
	public static void posQuitar(boolean tipoDispo) throws CaracterInvalidoException{
		System.out.print("\nPor favor elija una opcion:"
			+ "\n1. Quitar otro dispositivo"
			+ "\n2. Volver al menu principal\n");
				
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
	
	public static void SeleccionarDispo() throws CaracterInvalidoException{
		System.out.println("\nElija un dispositivo:\n");
		imprimirDispoI();
		DispositivoInteligente dis = nico.getDispInteligente().get(in.nextInt());
		imprimirEstado(dis);
		operarConDispoSelected(dis);
	}
	
	public static void imprimirEstado(DispositivoInteligente dis){
		System.out.println("\nEl estado del dispositivo es: \n"
				+ dis.estadoDispo() + "\n");
		
	}
	
	public static void operarConDispoSelected(DispositivoInteligente dis) throws CaracterInvalidoException{
		System.out.println("\nElija una opcion:"
				+ "\n1. Encenderme"
				+ "\n2. Apagarme"
				+ "\n3. Activar Modo Ahorro de Energia"
				+ "\n4. Ver sensores"
				+ "\n5. Ver energia consumida en las ultimas n horas"
				+ "\n6. Ver energia consumida en un periodo"
				+ "\n7. Volver al menu anterior"
				+ "\n8. Volver al menu principal");
				
			switch(in.nextInt()){
			case 1:dis.encender();
				 System.out.println(dis.estadoDispo());
				break;
			case 2:
				dis.apagar();
				System.out.println(dis.estadoDispo());
				break;
			case 3:
				dis.ahorroEnergia();
				System.out.println(dis.estadoDispo());
				break;
			case 4:
				sensores(dis);
				break;
			case 5:
				consumoNhoras(dis);
				break;
			case 6:
				consumoPeriodo(dis);
				break;
			case 7:
				dispoInteligentes();
				break;
			case 8:
				menuPrincipal();
				break;
			default:SeleccionarDispo();
			}	
			dispoInteligentes();
	}
	
	public static void consumoNhoras(DispositivoInteligente dis) throws CaracterInvalidoException{
		System.out.println("\nIngrese la cantidad de horas que desea evaluar\n");
		int horas = in.nextInt();
		double consumo = dis.consumoEnUltimasHoras(horas);
		System.out.println("\nEl consumo fue de: " + consumo + "\n");
		operarConDispoSelected(dis);
	}
	
	public static void consumoPeriodo(DispositivoInteligente dis) throws CaracterInvalidoException{
		System.out.println("\nFecha inicial:\n");
		//int year,int month,int day,int hour,int min,int sec
		System.out.println("Ingrese anio inicial\n");
		int yearI = in.nextInt();
		System.out.println("Ingrese mes inicial\n");
		int monI = in.nextInt();
		System.out.println("Ingrese dia inicial\n");
		int dayI = in.nextInt();
		System.out.println("Ingrese hora inicial\n");
		int hourI = in.nextInt();
		System.out.println("Ingrese minuto inicial\n");
		int minI = in.nextInt();
		System.out.println("Ingrese segundo inicial\n");
		int secI = in.nextInt();
		
		System.out.println("Ingrese fecha final: \n");
		System.out.println("Ingrese anio final\n");
		int yearF = in.nextInt();
		System.out.println("Ingrese mes final\n");
		int monF = in.nextInt();
		System.out.println("Ingrese dia final\n");
		int dayF = in.nextInt();
		System.out.println("Ingrese hora final\n");
		int hourF = in.nextInt();
		System.out.println("Ingrese minuto final\n");
		int minF = in.nextInt();
		System.out.println("Ingrese segundo final\n");
		int secF = in.nextInt();
		
		LocalDateTime fechaI = LocalDateTime.of(yearI,monI,dayI,hourI,minI,secI);
		LocalDateTime fechaF = LocalDateTime.of(yearF,monF,dayF,hourF,minF,secF);
		double consumo = dis.consumoTotal(fechaI,fechaF);
		System.out.println("\nEl consumo fue de: " + consumo + "\n");
		operarConDispoSelected(dis);
		
	}
	
	//dispositivos en general
	public static void agregarDispo(boolean tipoDispo) throws CaracterInvalidoException{//0 estandar 1 inteligente
		
		String nombre = " ";
		double kwh;
		double ahorroOConsumo;
		System.out.println("\nIngrese un nombre:");
		in.nextLine();
		nombre = in.nextLine();
		
		System.out.println("\nIngrese un kwh:");
		kwh = in.nextFloat();
		
		if(tipoDispo){
			System.out.println("\nIngrese un kwhAhorro:");
			ahorroOConsumo = in.nextFloat();		
			DispositivoInteligente dispo = new DispositivoInteligente(nombre,kwh,ahorroOConsumo);
			nico.agregarDispInteligente(dispo);
		} else{
			System.out.println("\nIngrese horas consumidas:");
			ahorroOConsumo = in.nextFloat();
			DispositivoEstandar dispo = new DispositivoEstandar(nombre,kwh,(int) ahorroOConsumo);
			nico.agregarADispEstandar(dispo);
		}
		
		System.out.println("\nTus dispositivos inteligentes son: \n");
		imprimirDispoI();
		System.out.println("\nTus dispositivos Estandares son: \n");
		imprimirDispoS();
		
		posAgregarDispo(tipoDispo);
	}
	
	public static void posAgregarDispo(boolean tipoDispo) throws CaracterInvalidoException{
		
		System.out.print("\nPor favor elija una opcion:"
				+ "\n1. Agregar otro dispositivo"
				+ "\n2. Volver al menu principal\n");
				
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
		dispos.stream().forEach(d -> System.out.print(dispos.indexOf(d) + ". " + d.getNombreDisp() + "\n"));
	}
	
	public static void imprimirDispoS(){
		List<DispositivoEstandar> dispos = nico.getDispEstandar();
		dispos.stream().forEach(d -> System.out.print(dispos.indexOf(d) + ". " + d.getNombreDisp() + "\n"));
	}
	
	//sensores
	public static void sensores(DispositivoInteligente dispo) throws CaracterInvalidoException{
		System.out.println("\nElija una opcion:"
			+ "\n1. Seleccionar un sensor para mas operaciones"
			+ "\n2. Agregar un nuevo sensor"
			+ "\n3. Quitar un sensor"
			+ "\n4. Volver al menu anterior"
			+ "\n5. Volver al menu principal");
		
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
	
	public static void seleccionesSensor(DispositivoInteligente dispo) throws CaracterInvalidoException{
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
		
		String nombre = sensoresList.get(in.nextInt());
		return dispo.getSensores().get(nombre);
	}
	
	public static void posSeleccionarSensor(Sensor sensor, DispositivoInteligente dispo) throws CaracterInvalidoException{
		System.out.println("\nElija una opcion: (tener en cuenta que movimiento es un sensor binario)"
				+ "\n Aca se permitira subir y bajar pero no se debe hacerlo"
				+ "\n1. Ver valor"
				+ "\n2. Ver subscriptores"
				+ "\n3. Ver intervalo de medicion"
				+ "\n4. Notificar a sus subscriptores"
				+ "\n5. Volver al menu anterior"
				+ "\n6. Volver al menu principal");
		
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
			notifyFans(sensor,dispo);
			break;
		case 5:
			sensores(dispo);
			break;
		case 6:
			menuPrincipal();
			break;
		default:posSeleccionarSensor(sensor,dispo);
		}
		sensores(dispo);
		
	}
	
	public static void valorSensor(Sensor sensor, DispositivoInteligente dispo) throws CaracterInvalidoException{
		System.out.println(sensor.getMagnitud());
		
		System.out.println("\nElija una opcion:"
				+ "\n1. Subir valor"
				+ "\n2. Bajar valor"
				+ "\n3. Volver al menu anterior"
				+ "\n4. Volver al menu principal");
			
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
	
	public static void subirValorSensor(Sensor sensor,DispositivoInteligente dispo) throws CaracterInvalidoException{
		System.out.println("\nIngrese cuanto desea subir:\n");
		sensor.aumentarMagnitud(in.nextInt());
		System.out.println("\nEl valor final del sensor es de: "
				+ sensor.getMagnitud());
		posSubirValorSensor(sensor,dispo);
	}

	public static void posSubirValorSensor(Sensor sensor,DispositivoInteligente dispo) throws CaracterInvalidoException{
		System.out.println("\nElija una opcion:"
				+ "\n1. Seguir subiendo"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
			
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

	public static void bajarValorSensor(Sensor sensor,DispositivoInteligente dispo) throws CaracterInvalidoException{
		System.out.println("\nIngrese cuanto desea bajar:\n");
		sensor.disminuirMagnitud(in.nextInt());
		System.out.println("\nEl valor final del sensor es de: "
				+ sensor.getMagnitud());
		posBajarValorSensor(sensor,dispo);
	}

	public static void posBajarValorSensor(Sensor sensor,DispositivoInteligente dispo) throws CaracterInvalidoException{
		System.out.println("\nElija una opcion:"
				+ "\n1. Seguir bajando"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
			
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
	
	public static void agregarSensor(DispositivoInteligente dispo) throws CaracterInvalidoException{
		String nombre;
		double valor;
		System.out.println("\nIngrese un nombre:");
		in.nextLine();
		nombre = in.nextLine();
		System.out.println("\nIngrese un valor inicial para el sensor:");
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
	
	public static void posAgregarSensor(DispositivoInteligente dispo) throws CaracterInvalidoException{
		System.out.println("\nElija una opcion:"
				+ "\n1. Agregar otro sensor"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
		
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
	
	public static void quitarSensor(DispositivoInteligente dispo) throws CaracterInvalidoException{
		System.out.println("\nElija un sensor para quitar:");
		Set<String> sensoresSet = dispo.getSensores().keySet();
	    int i = 0;
		for(String sen:sensoresSet){
			System.out.println(i + ". " + sen + "\n");
			i++;
		}
		List<String> sensoresList = new ArrayList<>(sensoresSet);
		
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
	
	public static void posQuitarSensor(DispositivoInteligente dispo) throws CaracterInvalidoException{
		System.out.println("\nElija una opcion:"
				+ "\n1. Quitar otro sensor"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
		
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
	
	public static void notifyFans(Sensor sen,DispositivoInteligente dispo) throws CaracterInvalidoException{
		System.out.println("\nLos subscriptores son: \n");
		sen.getSubscribers().forEach(s -> System.out.println(s.getExpresion()));
		sen.notificar();
		posSeleccionarSensor(sen,dispo);
	}
	
	//reglas
	public static void reglas() throws CaracterInvalidoException{
		System.out.println("\nElija una opcion:"
				+ "\n1. Seleccionar una regla para mas opciones"
				+ "\n2. Agregar una regla nueva"
				+ "\n3. Quitar una regla"
				+ "\n4. Volver al menu principal");
			
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
	
	public static void seleccionarRegla() throws CaracterInvalidoException{
		Regla reg = seleccionarUnaRegla();
		operacConReglaSelected(reg);
	}
	
	public static Regla seleccionarUnaRegla(){
		System.out.println("\nSeleccione una regla: \n");
		int i = 0;
		for(Regla reg:reglasExistentes){
			System.out.println(i + ". " + reg.getNombreRegla());
			i++;
		}
		
		return reglasExistentes.get(in.nextInt());
	}
	
	public static void operacConReglaSelected(Regla reg) throws CaracterInvalidoException{
		imprimirCondiciones(reg);
		imprimirActuadores(reg);
		
		System.out.println("\nEl criterio de comparacion es: ");
		System.out.println(reg.getComparacionCondiciones());
		
		System.out.println("\nElija una opcion:"
				+ "\n1. Seleccionar Condicion"
				+ "\n2. Agregar Condicion"
				+ "\n3. Quitar Condicion"
				+ "\n4. Seleccionar Actuador"
				+ "\n5. Agregar Actuador"
				+ "\n6. Quitar Actuador"
				+ "\n7. Cambiar criterio de comparacion"
				+ "\n8. Aplicar regla"
				+ "\n9. Volver al menu anterior"
				+ "\n10. Volver al menu principal");
		
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
			seleccionarActuador(reg);
			break;
		case 5:
			agregarActuador(reg);
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
	public static void imprimirCondiciones(Regla reg){
		System.out.println("\nLas condiciones son: \n");
		List<Condicion> conds = reg.getCondiciones();
		conds.stream().forEach(c -> System.out.println(conds.indexOf(c) + ". " 
								+ c.getExpresion()));
	}
	
	public static void seleccionarCondicion(Regla reg) throws CaracterInvalidoException{
		System.out.println("\nSeleccione una condicion: ");
		imprimirCondiciones(reg);
		Condicion con = reg.getCondiciones().get(in.nextInt());
		operacConCondSelected(con,reg);
	}
	
	public static void operacConCondSelected(Condicion con,Regla reg) throws CaracterInvalidoException{
		System.out.println("\nElija una opcion: "
				+ "\n1. Evaluar la condicion seleccionada"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
		
		switch(in.nextInt()){
		case 1:
			evaluarCondicion(con,reg);
			break;
		case 2:
			operacConReglaSelected(reg);
			break;
		case 3:
			menuPrincipal();
			break;
		default: seleccionarCondicion(reg);
		}
	}
	
	public static void evaluarCondicion(Condicion con, Regla reg) throws CaracterInvalidoException{
		con.update();
		System.out.println("\nElija una opcion: "
				+ "\n1. Volver al menu anterior"
				+ "\n2. Volver al menu principal");
		
		switch(in.nextInt()){
		case 1:
			operacConReglaSelected(reg);
			break;
		case 2:
			menuPrincipal();
			break;
		default: evaluarCondicion(con,reg);
		}
	}
	
	public static void agregarCondicion(Regla reg) throws CaracterInvalidoException{
		System.out.println("\nSeleccione un dispositivo: ");
		imprimirDispoI();
		DispositivoInteligente dispo = nico.getDispInteligente().get(in.nextInt()); 

		Sensor sen = seleccionarSen(dispo);
		
		System.out.println("\nIngrese criterio de comparacion: " 
				+ "\n(MAYOR, MENOR, IGUAL o DISTINTO)");
		in.nextLine();
		String criterio = in.nextLine();
		
		System.out.println("\nCon que desea compararlo: "
				+ "\n1. Otro Sensor"
				+ "\n2. Un valor fijo");
		int op = in.nextInt();
		if(op == 1){
			Sensor sen2 = seleccionarSen(dispo);
			reg.crearCondicionDosSensores(sen, sen2, criterio);
		} else if(op == 2){
			System.out.println("\nIngrese el valor a comparar: ");
			int valorFijo = in.nextInt();
			reg.crearCondicionSensoresYValor(sen, valorFijo, criterio);
		} else {
			System.out.println("\nOpcion invalida\n");
			agregarCondicion(reg);
		}
		
		System.out.println("\nLa lista de condiciones quedo asi: \n");
		List<Condicion> conds = reg.getCondiciones();
		conds.stream().forEach(c -> System.out.println(c.getExpresion()));
		
		posAgregarCondicion(reg);
	}

	public static void posAgregarCondicion(Regla reg) throws CaracterInvalidoException{
		System.out.println("\nElija una opcion:"
				+ "\n1. Agregar otra condicion"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
		
		switch(in.nextInt()){
		case 1:
			agregarCondicion(reg);
			break;
		case 2:
			operacConReglaSelected(reg);
			break;
		case 3:
			menuPrincipal();
			break;
		default:posAgregarCondicion(reg);
		}
	}
	
	public static void quitarCondicion(Regla reg) throws CaracterInvalidoException{
		imprimirCondiciones(reg);
		System.out.println("\nSeleccione una condicion para quitar: ");
		Condicion con = reg.getCondiciones().get(in.nextInt());
		reg.quitarCondicion(con);
		
		List<Condicion> conds = reg.getCondiciones();
		
		System.out.println("\nLa lista de condiciones quedo asi: \n");
		conds.stream().forEach(c -> System.out.println(c.getExpresion()));
		
		System.out.println("\nElija una opcion: "
				+ "\n1. Quitar otra condicion"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
		
		switch(in.nextInt()){
		case 1:
			quitarCondicion(reg);
			break;
		case 2:
			operacConReglaSelected(reg);
			break;
		case 3:
			menuPrincipal();
			break;
		default: quitarCondicion(reg);
		}
	}

	// actuadores
	public static void imprimirActuadores(Regla reg){
		List<Actuador> acts = reg.getActuadores();
		System.out.println("\nLos actuadores son: \n");
		acts.stream().forEach(a -> System.out.println(acts.indexOf(a) + ". " +a.getOrden()));
	}
	
	public static void seleccionarActuador(Regla reg) throws CaracterInvalidoException{
		imprimirActuadores(reg);
		System.out.println("\nSeleccione un actuador: ");
		Actuador act = reg.getActuadores().get(in.nextInt());
		
		System.out.println("\nElija una opcion: "
				+ "\n1. Evaluar el actuador seleccionado"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
		
		switch(in.nextInt()){
		case 1:
			evaluarActuador(act,reg);
			break;
		case 2:
			operacConReglaSelected(reg);
			break;
		case 3:
			menuPrincipal();
			break;
		default: seleccionarCondicion(reg);
		}
	}
	
	public static void evaluarActuador(Actuador act,Regla reg) throws CaracterInvalidoException{
		System.out.println("\nElija un dispositivo: ");
		imprimirDispoI();
		DispositivoInteligente dispo = nico.getDispInteligente().get(in.nextInt());
		act.execute(dispo);
		
		System.out.println("\nElija una opcion:"
				+ "\n1. Volver al menu anterior"
				+ "\n2. Volver al menu principal");
		
		switch(in.nextInt()){
		case 1:
			operacConReglaSelected(reg);
			break;
		case 2:
			menuPrincipal();
			break;
		default:evaluarActuador(act,reg);
		}
	}

	public static void agregarActuador(Regla reg) throws CaracterInvalidoException{
		
		System.out.println("\nElija un dispositivo: ");
		imprimirDispoI();
		DispositivoInteligente dispo = nico.getDispInteligente().get(in.nextInt());
		
		System.out.println("\nIngrese un ID de fabricante: (un numero entero)");
		int id = in.nextInt();
		
		System.out.println("\nIngrese una orden: ");
		in.nextLine();
		String orden = in.nextLine();
		
		Actuador act = new Actuador(id,orden);
		
		System.out.println("\nQue desea hacer con el actuador creado: "
				+ "\n1. Agregarlo a la regla"
				+ "\n2. Evaluarlo a un dispositivo"
				+ "\n3. Volver al menu anterior"
				+ "\n4. Volver al menu principal");
		
		switch(in.nextInt()){
		case 1:
			reg.agregarActuador(act);
			break;
		case 2:
			evaluarActuador(act,reg);
			break;
		case 3:
			operacConReglaSelected(reg);
			break;
		case 4:
			menuPrincipal();
			break;
		default: seleccionarCondicion(reg);
		}
		seleccionarRegla();
	}

	public static void quitarActuador(Regla reg) throws CaracterInvalidoException{
		List<Actuador> acts = reg.getActuadores();
		System.out.println("\nSeleccione un actuador para quitar: \n");
		acts.stream().forEach(a -> System.out.println(acts.indexOf(a) + ". " +a.getOrden()));
		Actuador act = reg.getActuadores().get(in.nextInt());
		reg.quitarActuador(act);
		System.out.println("\nLa lista de actuadores quedo asi: \n");
		reg.getActuadores().stream().forEach(a -> System.out.println(a.getOrden()));
		
		System.out.println("\nElija una opcion:"
				+ "\n1. quitar otro actuador"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
		
		switch(in.nextInt()){
		case 1:
			quitarActuador(reg);
			break;
		case 2:
			operacConReglaSelected(reg);
			break;
		case 3:
			menuPrincipal();
			break;
		default:quitarActuador(reg);
		}
		
	}

	public static void cambiarCriterioComp(Regla reg) throws CaracterInvalidoException{
		System.out.println("\nIngrese el nuevo criterio de comparacion (AND u OR)\n");
		in.nextLine();
		String criterio = in.nextLine();

		try{
			reg.setComparacionCondiciones(criterio);
			}catch(Exception CaracterInvalidoExcepsion) {
				System.out.println("\nOpcion invalida\n");
			}
		
		System.out.println("\nElija una opcion:"
				+ "\n1. Volver al menu anterior"
				+ "\n2. Volver al menu principal");
		
		switch(in.nextInt()){
		case 1:
			operacConReglaSelected(reg);
			break;
		case 2:
			menuPrincipal();
			break;
		default:quitarActuador(reg);
		}
		
	}

	public static void aplicarRegla(Regla reg) throws CaracterInvalidoException{
		reg.aplicarRegla();
		System.out.println("\nElija una opcion: "
				+ "\n1. Volver al menu anterior"
				+ "\n2. Volver al menu principal");
		
		switch(in.nextInt()){
		case 1:
			operacConReglaSelected(reg);
			break;
		case 2:
			menuPrincipal();
			break;
		default: aplicarRegla(reg);
		}
	}
	
	public static void agregarRegla() throws CaracterInvalidoException{
		
		System.out.println("\nIngrese un nombre para la regla\n");
		in.nextLine();
		String nombre = in.nextLine();
		
		System.out.println("\nSeleccione una condicion para agregarla a la regla\n");
		condicionesExistentes.stream().forEach(c -> System.out.println(
						condicionesExistentes.indexOf(c) + ". " + c.getExpresion()));
		Condicion con = condicionesExistentes.get(in.nextInt());
		
		System.out.println("\nSeleccione un actuador para agregarlo a la regla\n");
		actuadoresExistentes.stream().forEach(a -> System.out.println(
							actuadoresExistentes.indexOf(a) + ". " + a.getOrden()));
		Actuador act = actuadoresExistentes.get(in.nextInt());
		
		System.out.println("\nIngrese un criterio de comparacion (AND u OR)\n");
		in.nextLine();
		String criterio = in.nextLine();

		Regla regla = new Regla(nombre,aircon,criterio);
		
		regla.agregarCondicion(con);
		regla.agregarActuador(act);
		reglasExistentes.add(regla);
		
		System.out.println("\nLa lista de reglas quedo asi: \n");
		reglasExistentes.stream().forEach(r -> System.out.println(r.getNombreRegla()));
		
		posAgregarRegla();
	}
	
	public static void posAgregarRegla() throws CaracterInvalidoException{
		System.out.println("\nElija una opcion:"
				+ "\n1. Agregar otra regla"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
		
		switch(in.nextInt()){
		case 1:
			agregarRegla();
			break;
		case 2:
			reglas();
			break;
		case 3:
			menuPrincipal();
			break;
		default:agregarRegla();
		}
	}
	
	public static void quitarRegla() throws CaracterInvalidoException{
		//mostrar lista reglas existentes
		System.out.println("\nSeleccione una regla: \n");
		reglasExistentes.stream().forEach(r -> System.out.print(
				reglasExistentes.indexOf(r) + ". " + r.getNombreRegla() + "\n" ));
		
		Regla reg = reglasExistentes.get(in.nextInt());
		reglasExistentes.remove(reg);

		System.out.println("\nLa lista de reglas quedo asi: \n");
		reglasExistentes.stream().forEach(r -> System.out.println(r.getNombreRegla()));
		
		posQuitarRegla();
	}
	public static void posQuitarRegla() throws CaracterInvalidoException{
		System.out.println("\nElija una opcion:"
				+ "\n1. Quitar otra regla"
				+ "\n2. Volver al menu anterior"
				+ "\n3. Volver al menu principal");
		
		switch(in.nextInt()){
		case 1:
			quitarRegla();
			break;
		case 2:
			reglas();
			break;
		case 3:
			menuPrincipal();
			break;
		default:quitarRegla();
		}
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
		condicionesExistentes.add(tempIgual30);
		condicionesExistentes.add(humedadMenor50);
		condicionesExistentes.add(hayGenteCasa);
		actuadoresExistentes.add(prenderAire);
		actuadoresExistentes.add(prenderLuz);
	}
}
