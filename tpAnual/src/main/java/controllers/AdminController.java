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
	
	public ModelAndView reportes(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		
		//TODO ir a buscar el cliente posta a la base de datos
		//Cliente user = ClienteFactory.getCliente(req.session().id());
		
		Cliente cliente = new Cliente();
		DispositivoInteligente disp1 = new DispositivoInteligente("Televisor","LED 24'");
		DispositivoEstandar disp2 = new DispositivoEstandar("Ventilador",0.45,3,"Ventilador",1,4,true);
		DispositivoEstandar disp3 = new DispositivoEstandar("Heladera",0.55,2,"Heladera",1,3,true);
		
		LocalDateTime inicio = LocalDateTime.of(2018,10,7,0,0,0);
		LocalDateTime fin = LocalDateTime.now();
		IntervaloDispositivo int1 = new IntervaloDispositivo(inicio,modo.AHORRO);
		cliente.setNombre("Marina");
		disp1.setIntervalo(int1);
		
		cliente.agregarDispositivo(disp1);
		cliente.agregarDispositivo(disp2);
		cliente.agregarDispositivo(disp3);
		
		double horasDeConsumo = cliente.consumoXPeriodo(inicio, fin);
		
		//Reporte de consumo por hogar/periodo 
		model.put("nombre",cliente.getNombre());
		model.put("periodoHogarInicio",inicio);
		model.put("periodoHogarFin",fin);
		model.put("consumoHogar",horasDeConsumo);
		
		//Reporte de consumo promedio por tipo de dispositivos
		/*model.put("tipoDisp",  );
		model.put("periodoTipoDisp",  );
		model.put("consumoDisp",  );
		
		//Reporte de transformadores por periodo
		model.put("Trans",  );
		model.put("periodoTrans",  );
		model.put("consumoTrans",  );
		
		
		
		<div class="table-responsive">
		<h3>Reporte de consumo promedio por tipo de dispositivos </h3>
	<table class="table">
		<tr>
			<th>Tipo dispositivos</th>
			<th>Periodo inicio</th>
			<th>Periodo fin</th>
			<th>Consumo</th>
		</tr>
		
		<tr>
			<td>{{tipoDisp}}</td>
			<td>{{periodoTipoDisp}}</td>
			<td>{{consumoDisp}}</td>
		</tr>
	</table>
	
	<div class="table-responsive">
		<h3>Reporte de transformadores por periodo </h3>
	<table class="table">
		<tr>
			<th>Transformadores</th>
			<th>Periodo</th>
			<th>Consumo</th>
		</tr>
		
		<tr>
			<td>{{Trans}}</td>
			<td>{{periodoTrans}}</td>
			<td>{{consumoTrans}}</td>
		</tr>
	</table>
		*/
		
		return new ModelAndView(model, "reportes.hbs");
	}
}
