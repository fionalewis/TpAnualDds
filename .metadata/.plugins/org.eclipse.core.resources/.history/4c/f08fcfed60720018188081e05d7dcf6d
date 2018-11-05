package entregas;

import java.time.LocalDateTime;
import java.util.List;

import exceptions.ExceptionsHandler;
import modelo.DAOJson;
import modelo.devices.Dispositivo;
//import modelo.devices.DispositivoConvertido;
//import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.IntervaloDispositivo;
//import modelo.devices.IntervaloDispositivo.modo;
import modelo.users.Categoria;
import modelo.users.Cliente;
import modelo.users.Cliente.TipoDocumento;

public class Programa {
	
	//Para los frames
	public static Cliente cprueba = new Cliente("bart","simpson","elbarto","12345",TipoDocumento.DNI,"4444444","11111111","Avenida Siempreviva 742");
	static List<Dispositivo> disp = cprueba.getDispositivos();
	
	
	//Metodos que usamos para categorias
	
	public static boolean isBetween(double n,double min,double max) {
		return (n>=min)&&(n<=max);
	}

	public static Categoria categoria(double consumo) {
		List<Categoria> categorias = null;
		Categoria categoria = null;
		try {
			categorias = DAOJson.deserializarLista(Categoria.class,"\\C:\\Users\\Salome\\git\\TpAnualDds\\tpAnual\\JSONs\\jsonCategorias.json");
			} catch (Exception e) {
			ExceptionsHandler.catchear(e);
		}
		for(Categoria c: categorias) {
			if(isBetween(consumo,c.getMin(),c.getMax())) {
				categoria = c;
			}
		}
		return categoria;
	}
	
	public static void main(String[] args) {
		//Cliente cprueba = new Cliente("bart","simpson","elbarto","12345",TipoDocumento.DNI,"4444444","11111111","Avenida Siempreviva 742");
		/*System.out.println(cprueba.getCateg().getClasif());
		System.out.println(cprueba.obtenerTarifa());
		Categoria c = categoria(1500);
		System.out.println(c.getClasif());
		System.out.println(Categoria.perteneceACateg("R2",180));
		System.out.println(Categoria.perteneceACateg("R2",1000));
		System.out.println(cprueba.getFechaAlta());
		System.out.println(cprueba.calcularConsumo());*/
		/*System.out.println("puntos iniciales:" + cprueba.getPuntos());
		*/DispositivoInteligente dispI = new DispositivoInteligente("tele",0.8,2018,6,12,10,0,0);//cprueba.agregarDispositivo(dispI);
		/*System.out.println("despues de reg un inteligente:" + cprueba.getPuntos());
		System.out.println("datos del disp:" + dispI.getNombreDisp());
		dispI.darEstado();
		DispositivoInteligente dispI2 = new DispositivoInteligente("cocina",1.2);cprueba.agregarDispositivo(dispI2);
		System.out.println("despues de reg otro " + cprueba.getPuntos());
		System.out.println("datos del disp:" + dispI2.getNombreDisp());
		dispI2.darEstado();
		DispositivoEstandar dispE = new DispositivoEstandar("heladera",1.3,5);cprueba.agregarDispositivo(dispE);
		System.out.println("despues de un estandar:" + cprueba.getPuntos());	
		DispositivoEstandar dispE2 = new DispositivoEstandar("teleNoHD",4,9);cprueba.agregarDispositivo(dispE2);
		System.out.println("despues de otro est:" + cprueba.getPuntos());	
		DispositivoConvertido dispC = new DispositivoConvertido(dispE2);cprueba.agregarDispositivo(dispC);
		System.out.println("despues de convertir un estandar " + cprueba.getPuntos());
		System.out.println("datos del disp:" + dispC.getNombreDisp());
		dispC.darEstado();
		System.out.println("Los disp totales son:\n");		
		cprueba.mostrarLista(cprueba.getDispositivos());
		System.out.println("Los disp intel son:\n");		
		cprueba.mostrarLista(cprueba.obtenerLista("Inteligente"));
		System.out.println("Los disp est son:\n");		
		cprueba.mostrarLista(cprueba.obtenerLista("Estandar"));
		System.out.println("Los disp intl y conv son:\n");		
		cprueba.mostrarLista(cprueba.obtenerLista("IyC"));
		System.out.println("Los disp conv son:\n");		
		cprueba.mostrarLista(cprueba.obtenerLista("Convertido"));
		System.out.println("La cantidad de disp totales es de : " + cprueba.cantDispositivosTotal());	
		System.out.println("Los disp int y conv es:" + cprueba.cantDispositivos());	
		System.out.println("Los disp encendidos es:" + cprueba.cantDisp(true));
		System.out.println("Los disp apagados es:" + cprueba.cantDisp(false));
		System.out.println("si mando a apagar a la cocina tendria que cambiar el valor de cant disp:");
		dispI2.apagar();
		System.out.println("Los disp int y conv es:" + cprueba.cantDispositivos());
		cprueba.algunoEncendido();
		System.out.println("la cantidad de encend ahora es de:" + cprueba.cantDisp(true));	
		System.out.println("la cantidad de apagados ahora es de:" + cprueba.cantDisp(false));
		dispI.apagar();
		dispC.apagar();
		System.out.println("la cantidad de encend ahora es de:" + cprueba.cantDisp(true));	
		System.out.println("la cantidad de apagados ahora es de:" + cprueba.cantDisp(false));
		cprueba.algunoEncendido();*/
		
		
		/*System.out.println("puntos inic: " + cprueba.getPuntos());
		DispositivoInteligente di = new DispositivoInteligente("teleHD",0.6);cprueba.agregarDispositivo(di);
		System.out.println("puntos con di: " + cprueba.getPuntos());
		System.out.println("info di: " + di.getNombreDisp() + " " + di.getEstadoDisp());
		DispositivoEstandar de = new DispositivoEstandar("heladera",0.5,5);cprueba.agregarDispositivo(de);
		System.out.println("puntos con de: " + cprueba.getPuntos());
		System.out.println("info de: " + de.getNombreDisp());
		System.out.println("cantidad de dispositivos; " + cprueba.cantDispositivosTotal());
		System.out.println("cant di: " + cprueba.cantDispositivos());
		System.out.println("cant de: " + cprueba.obtenerLista("Estandar").size());
		System.out.println("cant conv " + cprueba.obtenerLista("Convertido").size());
		System.out.println("convertimos la heladera: ");
		cprueba.agregarModuloAdaptador(de);
		System.out.println("puntos luego de conversion " + cprueba.getPuntos());
		System.out.println("cantidad de dispositivos; " + cprueba.cantDispositivosTotal());
		System.out.println("cant di: " + cprueba.cantDispositivos());
		cprueba.mostrarLista(cprueba.obtenerLista("Inteligente"));
		cprueba.mostrarLista(cprueba.obtenerLista("Estandar"));
		cprueba.mostrarLista(cprueba.obtenerLista("Convertido"));
		System.out.println("cant de: " + cprueba.obtenerLista("Estandar").size());
		System.out.println("cant conv " + cprueba.obtenerLista("Convertido").size());*/
		
		//VentanaPresentacion vMenu = new VentanaPresentacion();
		//vMenu.setVisible(true);
		//remove(component);
		
		List<IntervaloDispositivo> l = dispI.getIntervalos();
		//cprueba.mostrarLista(cprueba.getDispositivos());
		System.out.println(dispI.getFechaRegistro());
		System.out.println("Lo apagamos a las 12");
		dispI.apagar(LocalDateTime.of(2018,6,12,12,0,0));
		System.out.println("entonces, sus horas de uso fueron 2");
		System.out.println("horas de uso totales: " + dispI.horasDeUsoTotales());
		System.out.println("el consumo total del disp deberia ser entonces de 2*0.8 = 1.6");
		System.out.println("el resultado da:" + dispI.consumoTotal());
		
		for(int i = 0;i<l.size();i++) {
			System.out.println(l.get(i).getInicio());
			System.out.println(l.get(i).getFin());
			System.out.println(l.get(i).calculoDeHoras());
			System.out.println(l.get(i).getModo()+"\n");			
		}
		
		System.out.println("\nLo prendemos en ahorro a las 13");
		dispI.ahorroEnergia(LocalDateTime.of(2018,6,12,13,0,0));
		
		System.out.println("Lo apagamos a las 16");
		dispI.apagar(LocalDateTime.of(2018,6,12,16,0,0));
		System.out.println("entonces, sus horas de uso fueron 3 + las anteriores 2 = 5");
		System.out.println("horas de uso totales: " + dispI.horasDeUsoTotales());
		System.out.println("el consumo total del disp deberia ser entonces de 3*0.64 = 1.92 esto mas lo anterior = 1.6+1.92 = 3.52");
		System.out.println("el resultado da:" + dispI.consumoTotal() + "\n");
		for(int i = 0;i<l.size();i++) {
			System.out.println(l.get(i).getInicio());
			System.out.println(l.get(i).getFin());
			System.out.println(l.get(i).calculoDeHoras());
			System.out.println(l.get(i).getModo()+"\n");			
		}
		
		System.out.println("Lo encendemos en modo normal a las 18");
		dispI.encender(LocalDateTime.of(2018,6,12,18,0,0));
		System.out.println("Lo pasamos a ahorro de energia a las 20");
		dispI.ahorroEnergia(LocalDateTime.of(2018,6,12,20,0,0));
		System.out.println("entonces, sus horas de uso fueron 2 + las anteriores 5 = 7");
		System.out.println("horas de uso totales: " + dispI.horasDeUsoTotales());
		System.out.println("el consumo total del disp deberia ser entonces de 2*0.8 = 1.6,esto mas lo de antes 1.6+3.52 = 5.12");
		System.out.println("el resultado da:" + dispI.consumoTotal() + "\n");
		
		for(int i = 0;i<l.size();i++) {
			System.out.println(l.get(i).getInicio());
			System.out.println(l.get(i).getFin());
			System.out.println(l.get(i).calculoDeHoras());
			System.out.println(l.get(i).getModo()+"\n");			
		}
		
		
		System.out.println("Lo apagamos a las 22 y calculamos las horas de uso");
		dispI.apagar(LocalDateTime.of(2018,6,12,22,0,0));
		System.out.println("entonces, sus horas de uso fueron 2 + las anteriores 7 = 9");
		System.out.println("horas de uso totales: " + dispI.horasDeUsoTotales());
		System.out.println("el consumo total del disp deberia ser entonces de 2*0.64 = 1.28,esto mas lo de antes 1.28+5.12 = 6.4");
		System.out.println("el resultado da:" + dispI.consumoTotal());
		
		System.out.println("sabemos que el disp estuvo prendido entre las 10 y las 12 encendido,las 13 y las 16 ahorro,las 18 y 20 encendido,las 20 y 22 ahorro");
		System.out.println("entonces, sus horas de uso fueron 2 + 3 +4 = 9");
		System.out.println("horas de uso totales: " + dispI.horasDeUsoTotales());
		System.out.println("el consumo total del disp deberia ser entonces de 6.4");
		System.out.println("el resultado da:" + dispI.consumoTotal()+ "\n");
		
		for(int i = 0;i<l.size();i++) {
			System.out.println(l.get(i).getInicio());
			System.out.println(l.get(i).getFin());
			System.out.println(l.get(i).calculoDeHoras());
			System.out.println(l.get(i).getModo()+"\n");
		}
			
		System.out.println("vamos a ver cuanto consume entre las 11 y las 19");
		LocalDateTime fi = LocalDateTime.of(2018,6,12,11,0,0);
		LocalDateTime ff = LocalDateTime.of(2018,6,12,19,0,0);
		System.out.println("hay 5 horas de uso, 1 normal, 3 ahorro y otra de ahorro");
		System.out.println("entonces el consumo deberia dar 0.8 + 4*0.64 = 3.36");
		System.out.println("el consumo da"+dispI.consumoTotalEntre(fi,ff));
		
		System.out.println("consumo en las ultimas 3 horas desde las 22:");
		System.out.println("o sea calcular el consumo desde las 19");
		System.out.println("a las 19 estaba en modo normal y de 20 a 22 en ahorro");
		System.out.println("entonces el consumo deberia ser 0.8 + 2*0.64 = 2.08");
		System.out.println("el consumo en esas horas da "+dispI.consumoEnUltimasHoras(3,LocalDateTime.of(2018,6,12,22,0,0)));
		
		System.out.println("consumo en las ultimas 7 horas desde las 22:");
		System.out.println("o sea calcular el consumo desde las 15");
		System.out.println("15-16 ahorro 18-20 normal 20-22 ahorro");
		System.out.println("entonces el consumo deberia ser 0.64 + 2*0.8 + 2*0.64 = 3.52");
		System.out.println("el consumo en esas horas da "+dispI.consumoEnUltimasHoras(7,LocalDateTime.of(2018,6,12,22,0,0)));
		
		
		System.out.println("veamos que pasa si pido el consumo hace 5 horas, en el que a la hora inicial el disp estaria apagado:");
		System.out.println("o sea calcular el consumo desde las 17, y recien se prende a las 18");
		System.out.println("18 a 19 normal,20 a 22 ahorro");
		System.out.println("entonces el consumo deberia ser 2*0.8 + 2*0.64 = 2.88");
		System.out.println("el consumo en esas horas da "+dispI.consumoEnUltimasHoras(5,LocalDateTime.of(2018,6,12,22,0,0)));
		

}
}