package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.google.gson.Gson;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import modelo.geoLocation.Transformador;
import modelo.repositories.ClienteRepository;
import modelo.repositories.ReporteRepository;
import modelo.repositories.TransformadorRepository;
import modelo.users.Cliente;
import modelo.users.*;
//import model.Usuario;
//import repositories.Repositorios;
//import scala.Console;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

public class HomeController implements WithGlobalEntityManager, TransactionalOps{
	
	public ModelAndView home(Request req, Response res){
		
		return new ModelAndView(null, "/home.hbs");
	}
	
	public ModelAndView login (Request req, Response res){
	
		return new ModelAndView(null, "/login.hbs");
	}
		
	public ModelAndView wrongLogin (Request req, Response res){
		
		return new ModelAndView(null, "/wrongLogin.hbs");
	}
	
	public Void newSession(Request req, Response res){
		
		String username = req.queryParams("user");
		String pass = req.queryParams("password");
		Cliente user = new Cliente();
		try
		{
			//user = ClienteFactory.getCliente(username);
			user.setPassword("pass");
			user.setUserName("user");
		}
		catch (NoResultException e)
		{
			res.redirect("/archivo-incorrecto");
		}
		if(user.loginCorrecto(pass))
		{
			Session sesion = req.session(true);
			sesion.attribute("user", username);
			res.redirect("/");
		}
		else
			res.redirect("/wrong-user-or-pass");
		return null;
	}

	public ModelAndView map (Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		List<Transformador> t = TransformadorRepository.getListaTranformadores();
		String s = new String();
		for(Transformador tt : t){
			s += tt.getIdTransformador() + ";" + tt.suministroActual() + ";" + tt.getUbicacion().toString() + ";";
		}
		model.put("transformadores", s);
		return new ModelAndView(model, "/map.hbs");
		// para mandar las cosas sería en vez de null model ahí.
	}
}
