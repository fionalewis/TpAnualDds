package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.logging.FileHandler;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.NoResultException;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import modelo.JsonManager;
import modelo.Actuador.Actuador;
import modelo.Reglas.CondicionSensorYValor;
import modelo.Reglas.Regla;
import modelo.devices.Dispositivo;
import modelo.devices.DispositivoEstandar;
import modelo.devices.DispositivoInteligente;
import modelo.devices.IntervaloDispositivo;
import modelo.devices.IntervaloDispositivo.modo;
import modelo.devices.RecomendacionDTO;
import modelo.devices.Sensor;
import modelo.repositories.ActuadorRepository;
import modelo.repositories.ClienteRepository;
import modelo.repositories.DispositivoRepository;
import modelo.repositories.ReglaRepository;
import modelo.repositories.SensorRepository;
import modelo.users.Cliente;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ClienteController implements WithGlobalEntityManager, TransactionalOps{
	
	public ModelAndView consumo(Request req, Response res){
		//Map<String, List<Empresa>> model = new HashMap<>();
		//Repositorios.repositorioEmpresas.obtenerEmpresas();
		//model.put("empresas",  Repositorios.repositorioEmpresas.getEmpresas());
		return new ModelAndView(null, "consumo.hbs");
	}
	
	public ModelAndView calcularConsumo(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		//String nombreEmpresa = req.queryParams("nombreEmpresa");
		//Empresa empresa = Repositorios.repositorioEmpresas.buscarEmpresa(nombreEmpresa);
		String inicioPeriodo = req.queryParams("inicioPeriodo");
		String finPeriodo = req.queryParams("finPeriodo");
		System.out.println(inicioPeriodo == "");
		System.out.println(finPeriodo != "");
		if(inicioPeriodo != "" && finPeriodo != ""){
		String[] outputI = inicioPeriodo.split("-");
		LocalDateTime fechaInicio= LocalDateTime.of(Integer.parseInt(outputI[0]), Integer.parseInt(outputI[1]), Integer.parseInt(outputI[2]), 0, 0);
		String[] outputF = finPeriodo.split("-");
		LocalDateTime fechaFin= LocalDateTime.of(Integer.parseInt(outputF[0]), Integer.parseInt(outputF[1]), Integer.parseInt(outputF[2]), 0, 0);
		
		//TODO ir a buscar el cliente posta a la base de datos
		//Cliente user = ClienteFactory.getCliente(req.session().id());
		
		Cliente cliente = new Cliente();
		cliente = new ClienteRepository().obtenerCliente(req.session().attribute("user"));
		DispositivoRepository disp = new DispositivoRepository();
		List <Dispositivo> d = disp.getDispositivosDeUnCliente(cliente.getNroDoc());
		List <Dispositivo> de = d.stream().filter(UnDisp -> UnDisp.getEsInteligente()).collect(Collectors.toList()); 
		model.put("consumo", de.stream().mapToDouble(unDisp ->((DispositivoInteligente) unDisp).consumoTotalEntre(fechaInicio,fechaFin)).sum());
		}
		return new ModelAndView(model, "consumo.hbs");
	}
	
	
	public ModelAndView hogar(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		
		
		
		//TODO ir a buscar el cliente posta a la base de datos
		//Cliente user = ClienteFactory.getCliente(req.session().id());
		

		List<Cliente> cli = new ClienteRepository().getTodosLosClientes();
		Cliente cliente = new Cliente();
		cliente = new ClienteRepository().obtenerCliente(req.session().attribute("user"));
		/*
		DispositivoInteligente disp1 = new DispositivoInteligente("Televisor","LED 24'");
		DispositivoEstandar disp2 = new DispositivoEstandar("Ventilador",0.45,3,"Ventilador",1,4,true);
		DispositivoEstandar disp3 = new DispositivoEstandar("Heladera",0.55,2,"Heladera",1,3,true);
		cliente.agregarDispositivo(disp1);
		cliente.agregarDispositivo(disp2);
		cliente.agregarDispositivo(disp3);
		*/
		//DispositivoRepository disp = new DispositivoRepository();
		//List <Dispositivo> d = disp.getDispositivosDeUnCliente(cliente.getNroDoc());
		//List <Dispositivo> di = d.stream().filter(UnDisp -> UnDisp.getEsInteligente()).collect(Collectors.toList()); 
		//List <Dispositivo> de = d.stream().filter(UnDisp -> UnDisp.getEsInteligente() != true).collect(Collectors.toList());
		//double consumoDE = di.stream().mapToDouble(unDisp ->((DispositivoInteligente) unDisp).consumoTotalEntre(LocalDateTime.now().minusMonths(1),LocalDateTime.now())).sum();
		//double consumoDS = de.stream().mapToDouble(UnDisp -> ((DispositivoEstandar) UnDisp).consumoXPeriodo(LocalDateTime.now().minusMonths(1), LocalDateTime.now())).sum();
		//model.put("consumo", cliente.consumoXPeriodoNuevo(LocalDateTime.now().minusMonths(1), LocalDateTime.now(),disp));
		List<Dispositivo> disps = DispositivoRepository.getDispositivosDeUnCliente(cliente.getNroDoc()).stream().collect(Collectors.toList());//.filter(x-> Dispositivo.esAmbos(x)).collect(Collectors.toList());//filtar i y c;
		double consum = disps.stream().mapToDouble(unDisp -> unDisp.consumoTotal()).sum();
		
		model.put("consumo", consum);//consumoDE + consumoDS);
		model.put("dispositivos", disps);
		
		return new ModelAndView(model, "hogar.hbs");
		
	}
	
	public ModelAndView carga(Request req, Response res){
		return new ModelAndView(null, "carga.hbs");
	}
	
	public ModelAndView cargarAchivo(Request req, Response res) throws IOException{
		String ruta = req.queryParams("ruta");
		if("csv".equals(getFileExtension(ruta))){
		JsonManager.inicializarFactory(ruta);
		res.redirect("/");
		}else{
		res.redirect("/archivo-invalido.hbs");
		}
		return null;
	}
	
	public ModelAndView simplex(Request req, Response res){
		Map<String, Object> model = new HashMap<>();

		Cliente cliente = new Cliente();
		cliente = new ClienteRepository().obtenerCliente(req.session().attribute("user"));
			try {
				if(cliente.hogarEficiente()){
				model.put("eficiente","SI");
				}else{model.put("eficiente","NO");}
				
				//model.put("recomendacion", cliente.obtenerRecomendacionString());
				List<String> recHoras = new ArrayList();
				for(Entry<String, Double> unValor : cliente.horasXDisp().entrySet()) {
					recHoras.add("La recomendaci�n de horas m�ximas para el dispositivo '" + unValor.getKey() + "' es de " + unValor.getValue() + "hs.");
				}		
				
				//System.out.println(cliente.horasXDisp());
				//System.out.println(((RecomendacionDTO) cliente.obtenerRecomendacionDTO().get(0)).getDispositivo());
				RecomendacionDTO rec = cliente.obtenerRecomendacionDTO().get(0);
				System.out.println(rec.getDispositivo());
				
				
				
				model.put("recHoras",cliente.obtenerRecomendacionDTO());
				
			} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		
		return new ModelAndView(model, "simplex.hbs");
	}
	
	public void actualizarEstadosDisp(List <Dispositivo> disp){
		for(Dispositivo d : disp){
			d.actualizarEstadoActual();
		}
	}
	
	public ModelAndView reglasydisp(Request req, Response res){
		Map<String, Object> model = new HashMap<>();

		Cliente cliente = new Cliente();
		cliente = new ClienteRepository().obtenerCliente(req.session().attribute("user"));
		List <Dispositivo> ldisp = new DispositivoRepository().getDispositivosDeUnCliente(cliente.getNroDoc());
		//ingreso el estado correcto de cada dispositivo
		this.actualizarEstadosDisp(ldisp);
		model.put("dispositivos",ldisp);
		ReglaRepository reg = new ReglaRepository();
		model.put("reglas", reg.getTodasLasReglas());
		List <String> listaDeEstados = (List<String>) ldisp.stream().map(m -> m.getEstado()).collect(Collectors.toList());
		return new ModelAndView(model, "reglas.hbs");
	}
	
	public ModelAndView eliminarDisp(Request req, Response res){
		Map<String, Object> model = new HashMap<>();

		Cliente cliente = new Cliente();
		cliente = new ClienteRepository().obtenerCliente(req.session().attribute("user"));
		//DispositivoInteligente disp = new DispositivoInteligente(req.queryParams("nombre"),req.queryParams("descripciom"));
		String documento = cliente.getNroDoc();
		new DispositivoRepository().deleteDispositivo(documento, Long.parseLong(req.queryParams("id")));
		res.redirect("/reglas");
		return null;
	}
	
	public List<DispositivoInteligente> retornarSoloInteligentes(List<Dispositivo> ldisp){
		for(Dispositivo dispositivo : ldisp){
			if(dispositivo instanceof DispositivoInteligente){
				return (List<DispositivoInteligente>) dispositivo;
			}
		}
		return null;
	}
	
	public DispositivoInteligente retornarDispositivo(List<DispositivoInteligente> ldisp,String id){
		for(DispositivoInteligente dispositivo : ldisp){
			if(dispositivo.getId() == Long.parseLong(id)){
				return dispositivo;
			}
		}
		return null;
	}
	
	public void cambiarEstado(Dispositivo disp){
		DispositivoRepository repositorio = new DispositivoRepository();
		if(disp.getEstadoActual() == "Apagado"){
			List <IntervaloDispositivo> listaIntervalos = new ArrayList<IntervaloDispositivo>();
			listaIntervalos.add(new IntervaloDispositivo(LocalDateTime.now(),modo.NORMAL));
			repositorio.addIntervaloDispositivo(disp.getId(),listaIntervalos);
		}
		else
		{
			List <IntervaloDispositivo> intervalos = repositorio.getIntervalosDispositivo(disp.getId());
			IntervaloDispositivo ultimoIntervalo = intervalos.get(intervalos.size() - 1);
			repositorio.actualizarIntervaloDispositivo(ultimoIntervalo.getId(),LocalDateTime.now());
		}

	}
	
	@SuppressWarnings("static-access")
	public ModelAndView accionDisp(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		
		Cliente cliente = new Cliente();
		cliente = new ClienteRepository().obtenerCliente(req.session().attribute("user"));
		DispositivoRepository repository = new DispositivoRepository();
		List<Dispositivo> listaDisp = repository.getDispositivosDeUnCliente(cliente.getNroDoc());
		this.actualizarEstadosDisp(listaDisp);
		List<DispositivoInteligente> ldisp = listaDisp.stream().filter(d -> d instanceof DispositivoInteligente).map(d -> (DispositivoInteligente) d).collect(Collectors.toList());
		DispositivoInteligente disp = this.retornarDispositivo(ldisp, req.queryParams("id"));	
		this.cambiarEstado(disp);
		res.redirect("/reglas");
		return null;
	}
	
	public ModelAndView crearRegla(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		
		String nombreRegla = req.queryParams("nombre");
		String criterio = req.queryParams("criterio");
		Sensor sensor = new Sensor(req.queryParams("magnitud"),Double.parseDouble(req.queryParams("valor")),Integer.parseInt(req.queryParams("intervalo")));
		CondicionSensorYValor condicion = new CondicionSensorYValor(sensor,Double.parseDouble(req.queryParams("valorCondicion")),req.queryParams("comparacion"));
		condicion.setNombreCondicion(req.queryParams("nombreCondicion"));
		// hace falta agregar el nombre a la condicion
		Actuador actuador = new Actuador(Integer.parseInt(req.queryParams("idFabricante")),req.queryParams("orden"));
		Regla regla = new Regla(nombreRegla,null,criterio);
		regla.agregarCondicion(condicion);
		regla.agregarActuador(actuador);
		new ReglaRepository();
		ReglaRepository.addRegla(regla);
		model.put("mensajeRegla", "Nueva Regla creada exitosamente");
		res.redirect("/reglas");
		return null;
	}


	

public ModelAndView agregarDispPantalla(Request req, Response res){
		return new ModelAndView(null, "agregar-disp.hbs");
	}

public ModelAndView agregarReglaPantalla(Request req, Response res){
	return new ModelAndView(null, "agregar-regla.hbs");
}

	public ModelAndView agregarDisp(Request req, Response res){
		Cliente cliente = new Cliente();
		cliente = new ClienteRepository().obtenerCliente(req.session().attribute("user"));
		if(req.queryParams("tipo").equals("INTELIGENTE"))
		{
			DispositivoInteligente disp1 = new DispositivoInteligente(req.queryParams("nombre"),req.queryParams("descripcion"));
			disp1.setkWh(Double.parseDouble(req.queryParams("kWh")));
			disp1.setHorasDeUso(Double.parseDouble(req.queryParams("horasDiarias")));
			disp1.setEsBajoConsumo(Boolean.valueOf(req.queryParams("bajoConsumo")));
			DispositivoRepository d = new DispositivoRepository();
			disp1.encender();
			d.addDispositivo(disp1);
			d.addDispositivoConCliente(cliente.getNroDoc(),disp1);
			//TODO Persistir este dispositivo en la base de datos y agregarlo al cliente
			res.redirect("/reglas");
			return null;

		}
		else{
			DispositivoEstandar disp1 = new DispositivoEstandar();
			disp1.setNombreDisp(req.queryParams("nombre"));
			disp1.setEquipoConcreto(req.queryParams("descripcion"));
		disp1.setkWh(Double.parseDouble(req.queryParams("kWh")));
		disp1.setHorasDeUso(Double.parseDouble(req.queryParams("horasDiarias")));
		disp1.setEsBajoConsumo(Boolean.valueOf(req.queryParams("bajoConsumo")));
		DispositivoRepository d = new DispositivoRepository();
		d.addDispositivo(disp1);
		d.addDispositivoConCliente(cliente.getNroDoc(),disp1);
		//TODO Persistir este dispositivo en la base de datos y agregarlo al cliente
		res.redirect("/reglas");
		return null;
		}
		
	}

	
	/*public ModelAndView archivoInvalido (Request req, Response res){
		
		return new ModelAndView(null, "empresas/archivo-invalido.hbs");
	}*/
	
	public static String getFileExtension(String fullName) {
	    String fileName = new File(fullName).getName();
	    int dotIndex = fileName.lastIndexOf('.');
	    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}
}