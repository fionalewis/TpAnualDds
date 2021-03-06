package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import modelo.DAOJson;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.IntervaloDispositivo;
import modelo.devices.IntervaloDispositivo.modo;
import modelo.geoLocation.Transformador;
import modelo.repositories.AdministradorRepository;
import modelo.repositories.ClienteRepository;
import modelo.repositories.DispositivoRepository;
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
		
		System.out.println(inicioPeriodo);
		System.out.println(finPeriodo);
		if(inicioPeriodo != "" && finPeriodo != ""){
			String[] outputI = inicioPeriodo.split("-");
			LocalDateTime fechaInicio = LocalDateTime.of(Integer.parseInt(outputI[0]), 
					Integer.parseInt(outputI[1]), Integer.parseInt(outputI[2]), 0, 0);
			String[] outputF = finPeriodo.split("-");
			LocalDateTime fechaFin= LocalDateTime.of(Integer.parseInt(outputF[0]), 
					Integer.parseInt(outputF[1]), Integer.parseInt(outputF[2]), 0, 0);
			
			
			System.out.println(fechaInicio);
			System.out.println(fechaFin);
			
			TransformadorRepository tr = new TransformadorRepository();
			List<Transformador> trans = tr.getListaTranformadores();
			System.out.println(trans);
			//if(trans.size() != 0){
			//trans.forEach(t -> t.suministroActual());}
			
			model.put("transformadores",trans);
		}
		
		return new ModelAndView(model, "reportes.hbs");
	}
	
	public ModelAndView carga(Request req, Response res){
		return new ModelAndView(null, "carga.hbs");
	}
	
	public ModelAndView cargarArchivo(Request req, Response res) throws FileNotFoundException, InstantiationException, IllegalAccessException{
		String ruta = req.queryParams("ruta");
		System.out.println(ruta);
		List<Dispositivo> dispositivos = DAOJson.deserializarDispositivos(Dispositivo.class, ruta);
		DAOJson.serializarListDisp(dispositivos);
		
		for (Dispositivo d : dispositivos) {
			DispositivoRepository.addDispositivo(d);
			}
		return null;
	}
	
	public ModelAndView nuevoDisp(Request req, Response res) throws InstantiationException, IllegalAccessException, IOException{
		DAOJson js = new DAOJson();
		if(req.queryParams("tipo").equals("INTELIGENTE"))
		{
			DispositivoInteligente disp1 = new DispositivoInteligente(req.queryParams("nombre"),req.queryParams("descripcion"));
			disp1.setEsInteligente(true);
			disp1.setkWh(Double.parseDouble(req.queryParams("kWh")));
			disp1.setEsBajoConsumo(Boolean.valueOf(req.queryParams("bajoConsumo")));
			disp1.setHorasUsoMax(Double.valueOf(req.queryParams("horasUsoMax")));
			disp1.setHorasUsoMin(Double.valueOf(req.queryParams("horasUsoMin")));
			DispositivoRepository d = new DispositivoRepository();
			disp1.encender();
			js.serializar_disp(disp1);
			d.addDispositivo(disp1);
			res.redirect("/carga");
			return null;

		}
		else{
		DispositivoEstandar disp1 = new DispositivoEstandar();
		disp1.setNombreDisp(req.queryParams("nombre"));
		disp1.setEquipoConcreto(req.queryParams("descripcion"));
		disp1.setEsInteligente(false);
		disp1.setkWh(Double.parseDouble(req.queryParams("kWh")));
		disp1.setEsBajoConsumo(Boolean.valueOf(req.queryParams("bajoConsumo")));
		disp1.setHorasUsoMax(Double.valueOf(req.queryParams("horasUsoMax")));
		disp1.setHorasUsoMin(Double.valueOf(req.queryParams("horasUsoMin")));
		DispositivoRepository d = new DispositivoRepository();
		js.serializar_disp(disp1);
		d.addDispositivo(disp1);
		res.redirect("/carga");
		return null;
		}
		
	}
	
}
