package Entrega1;

import java.util.List;

import Exceptions.ExceptionsHandler;
import modelo.DAOJson;
import modelo.devices.DispositivoConvertido;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.users.Categoria;
import modelo.users.Cliente;
import modelo.users.Cliente.TipoDocumento;

public class Programa {
	
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
		Cliente cprueba = new Cliente("bart","simpson","elbarto","12345",TipoDocumento.DNI,"4444444","11111111","Avenida Siempreviva 742");
		/*System.out.println(cprueba.getCateg().getClasif());
		System.out.println(cprueba.obtenerTarifa());
		Categoria c = categoria(1500);
		System.out.println(c.getClasif());
		System.out.println(Categoria.perteneceACateg("R2",180));
		System.out.println(Categoria.perteneceACateg("R2",1000));
		System.out.println(cprueba.getFechaAlta());
		System.out.println(cprueba.calcularConsumo());*/
		/*System.out.println("puntos iniciales:" + cprueba.getPuntos());
		DispositivoInteligente dispI = new DispositivoInteligente("tele",0.8);cprueba.agregarDispositivo(dispI);
		System.out.println("despues de reg un inteligente:" + cprueba.getPuntos());
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
		System.out.println("puntos inic: " + cprueba.getPuntos());
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
		System.out.println("cant conv " + cprueba.obtenerLista("Convertido").size());
	}
}