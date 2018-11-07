package controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.IntervaloDispositivo;
import modelo.devices.IntervaloDispositivo.modo;
import modelo.users.Cliente;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class AdminController {
	
	public ModelAndView hogar_consumo(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		
		//TODO ir a buscar el cliente posta a la base de datos
		//Cliente user = ClienteFactory.getCliente(req.session().id());
		
		Cliente cliente = new Cliente();
		DispositivoInteligente disp1 = new DispositivoInteligente("Televisor","LED 24'");
		DispositivoEstandar disp2 = new DispositivoEstandar("Ventilador",0.45,3,"Ventilador",1,4,true);
		DispositivoEstandar disp3 = new DispositivoEstandar("Heladera",0.55,2,"Heladera",1,3,true);
		
		LocalDateTime int01 = LocalDateTime.of(2018,10,7,0,0,0);
		IntervaloDispositivo int1 = new IntervaloDispositivo(int01,modo.AHORRO);
		cliente.setNombre("Marina");
		disp1.setIntervalo(int1);
		
		cliente.agregarDispositivo(disp1);
		cliente.agregarDispositivo(disp2);
		cliente.agregarDispositivo(disp3);
		
		model.put("hogar",  cliente);
		model.put("nombre", cliente.getNombre());
		model.put("consumo", cliente.consumoXPeriodo(LocalDateTime.now().minusMonths(1), LocalDateTime.now()));
		
		return new ModelAndView(model, "hogar_consumo.hbs");
	}
}
