package entregas;

import java.util.Scanner;

import exceptions.CaracterInvalidoException;
import modelo.Actuador.Actuador;
import modelo.Reglas.Condicion;
import modelo.Reglas.CondicionSensorYValor;
import modelo.Reglas.Regla;
import modelo.devices.DispositivoInteligente;
import modelo.devices.Sensor;
import modelo.factories.CondicionFactory;
import modelo.factories.ReglaFactory;

public class EntregaPersistencia {
	public static void main(String[] args) throws CaracterInvalidoException{
		menuPrincipal();
	}
	
	@SuppressWarnings("resource")
	public static void menuPrincipal() throws CaracterInvalidoException{
		System.out.println("\n Por favor elija una opcion:"
				
				+ "\n1. Prueba 3 paso 1: crear nueva regla"
				+ "\n2. Prueba 3 paso 2: modificar una condicion"
				+ "\n3. Prueba 3 paso 3: verificar modificacion de condicion");
			
		Scanner in = new Scanner(System.in);
			switch(in.nextInt()){
			
			case 1:
				p3_crearRegla();
				break;
			case 2:
				p3_modificarCondicion();
				break;
			case 3:
				p3_verCondicionMdificada();
				break;
			
			default: menuPrincipal();
			}
	}
	
	public static void p3_crearRegla(){
		//**1 nueva regla
		
		String criterio=null;
		DispositivoInteligente dispositivo = new DispositivoInteligente("Televisor","LED 24'");
		//condiciones x2
		//actuador
		
		Scanner in = new Scanner(System.in);
		System.out.println("Ingrese un nombre para la regla");
		String nombreRegla = in.nextLine();
		System.out.println("Ingrese criterio de comparacion:\n"
				+ "1 para AND\n"
				+ "2 para OR\n");
		
		switch(in.nextInt()){
		case 1: criterio = "AND";
		break;
		case 2: criterio = "OR";
		break;
		}
		
		//asociar dispositivo
		//agregar acciones y condiciones
		Sensor sensor = new Sensor("Humedad",30,10);
		dispositivo.agregarSensor(sensor);
		
		CondicionSensorYValor condicion = new CondicionSensorYValor(sensor,50,"MENOR");
		condicion.setNombreCondicion("Humedad_menor_a_50");
		Actuador actuador = new Actuador(2,"APAGAR");
		
		System.out.println("Se asociara un dispositivo sobre la cual actuar: "
				+ "Televisor LED 24 con un sensor de humedad \n"
				+ "Se agregara una condicion: 'Humedad menor a 50%'"
				+ "Se agregara un actuador con id fabricante 2, con la accion de apagar el dispositivo");
		//persistir la regla
		Regla regla = new Regla(nombreRegla,dispositivo,criterio);
		regla.agregarCondicion(condicion);
		regla.agregarActuador(actuador);
		ReglaFactory.addRegla(regla);
	}
	
	public static void p3_modificarCondicion() throws CaracterInvalidoException{
		//**2 modificar condicion --> (factory) update
				//recuperar y ejecutar
		Scanner in = new Scanner(System.in);
		System.out.println("Ingrese el nombre de la regla");
		String nombreRegla = in.nextLine();
		Regla unaRegla = ReglaFactory.getRegla(nombreRegla);
		//cambiar despues de persistir los dispositivos
		DispositivoInteligente dispositivo = new DispositivoInteligente("Televisor","LED 24'");
		unaRegla.setDisp(dispositivo);
		System.out.println("Se ejecutara la regla creada");
		unaRegla.aplicarRegla();
		
				//modificar condicion
		System.out.println("Se modificara la condicion 'Humedad menor a 50%' a 'Humedad mayor a 50%'\n");
		//Esto no estoy segura 
		//( (CondicionSensorYValor) unaRegla.getCondiciones().get(0)).setComparacion("MENOR");
		//( (CondicionSensorYValor) unaRegla.getCondiciones().get(0)).setNombreCondicion("Humedad_menor_a_50");
		CondicionFactory.updateCondicion("MENOR", "Humedad_menor_a_50");
	}
	
	public static void p3_verCondicionMdificada(){
		//**3 
		//recuperar la condicion y ver si se guardaron los cambios
		
	}
	
	
}
