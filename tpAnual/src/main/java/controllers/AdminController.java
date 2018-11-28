package controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.IntervaloDispositivo;
import modelo.devices.IntervaloDispositivo.modo;
import modelo.geoLocation.Transformador;
import modelo.repositories.AdministradorRepository;
import modelo.repositories.ClienteRepository;
import modelo.repositories.TransformadorRepository;
import modelo.users.Administrador;
import modelo.users.Cliente;
import modelo.users.Reporte;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class AdminController {
	
	public ModelAndView hogar_consumo(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		
		List<Cliente> cli = new ClienteRepository().getTodosLosClientes();
		cli.forEach(c -> c.calcularConsumo());
		model.put("clientes",cli);
		
		return new ModelAndView(model, "hogar_consumo.hbs");
	}
	
	public ModelAndView reportes(Request req, Response res){
		
		return new ModelAndView(null, "reportes.hbs");
	}
	
	public ModelAndView reporteHogar(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		
		String inicioPeriodo = req.queryParams("inicioPeriodo");
		String finPeriodo = req.queryParams("finPeriodo");
		
		System.out.println("inicio" + inicioPeriodo);
		System.out.println("fin" + finPeriodo);
		
		if(inicioPeriodo != "" && finPeriodo != ""){
			String[] outputI = inicioPeriodo.split("-");
			LocalDateTime fechaInicio = LocalDateTime.of(Integer.parseInt(outputI[0]), 
					Integer.parseInt(outputI[1]), Integer.parseInt(outputI[2]), 0, 0);
			String[] outputF = finPeriodo.split("-");
			LocalDateTime fechaFin= LocalDateTime.of(Integer.parseInt(outputF[0]), 
					Integer.parseInt(outputF[1]), Integer.parseInt(outputF[2]), 0, 0);
			
			List<Cliente> cli = new ClienteRepository().getTodosLosClientes();
			cli.forEach(c -> c.consumoXPeriodo(fechaInicio,fechaFin));
			model.put("clientes",cli);
		}
		
		return new ModelAndView(model, "reportes.hbs");
	}
	
	public ModelAndView reporteTransformador(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		String inicioPeriodo = req.queryParams("inicio");
		String finPeriodo = req.queryParams("fin");
		
		if(inicioPeriodo != "" && finPeriodo != ""){
			String[] outputI = inicioPeriodo.split("-");
			LocalDateTime fechaInicio = LocalDateTime.of(Integer.parseInt(outputI[0]), 
					Integer.parseInt(outputI[1]), Integer.parseInt(outputI[2]), 0, 0);
			String[] outputF = finPeriodo.split("-");
			LocalDateTime fechaFin= LocalDateTime.of(Integer.parseInt(outputF[0]), 
					Integer.parseInt(outputF[1]), Integer.parseInt(outputF[2]), 0, 0);
			
			List<Transformador> trans = new TransformadorRepository().getListaTranformadores();
			trans.forEach(t -> t.suministroActual());
			
			model.put("transformadores",trans);
		}
		
		return new ModelAndView(model, "reportes.hbs");
	}
	
	
}
