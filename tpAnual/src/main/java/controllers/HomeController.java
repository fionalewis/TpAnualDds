package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.google.gson.Gson;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import modelo.geoLocation.Transformador;
import modelo.geoLocation.Zona;
import modelo.repositories.AdministradorRepository;
import modelo.repositories.ClienteRepository;
import modelo.repositories.DispositivoRepository;
import modelo.repositories.ReporteRepository;
import modelo.repositories.TransformadorRepository;
import modelo.users.Cliente;
import modelo.users.*;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import modelo.repositories.ClienteRepository;
import modelo.users.Cliente;
import modelo.users.Usuario;
//import model.Usuario;
//import repositories.Repositorios;
//import scala.Console;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

public class HomeController implements WithGlobalEntityManager, TransactionalOps{
	
	public ModelAndView home(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		model.put("esAdmin", req.session().attribute("esAdmin"));		
		return new ModelAndView(model, "/home.hbs");
	}
	
	public ModelAndView login (Request req, Response res){
	
		return new ModelAndView(null, "/login.hbs");
	}

	public ModelAndView loginAdmin (Request req, Response res){
	
		return new ModelAndView(null, "/login-admin.hbs");
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
		user = ClienteRepository.obtenerCliente(username);
		}
		catch (NoResultException e)
		{
			res.redirect("/archivo-incorrecto");
		}
		if(user != null && user.loginCorrecto(pass))
		{
			Session sesion = req.session(true);
			sesion.attribute("user", username);
			sesion.attribute("esAdmin",false);
			res.redirect("/");
		}
		else
			res.redirect("/wrong-user-or-pass");
		return null;
	}

	public Void newSessionAdmin(Request req, Response res) {
		//creo que se podría reusar la otra pero por las dudas hoy no lo quiero tocar
		String username = req.queryParams("user");
		String pass = req.queryParams("password");
		Administrador user = new Administrador();
		try
		{
			user = AdministradorRepository.getAdminConNombre(username);
		}
		catch (NoResultException e)
		{
			res.redirect("/archivo-incorrecto");
		}
		if(user != null && user.loginCorrecto(pass))
		{
			Session sesion = req.session(true);
			sesion.attribute("user", username);
			sesion.attribute("esAdmin",true);
			res.redirect("/");
		}
		else
			res.redirect("/wrong-user-or-pass");
		return null;
	}
	
	public ModelAndView logout(Request req, Response res){
		Session sesion = req.session(false);
		sesion.attribute("user", null);
		System.out.println("bla bla i crasy now");
		res.redirect("/login");
		return null;
	}

	public ModelAndView map (Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		List<Transformador> t = TransformadorRepository.getListaTranformadores();
		List<Zona> z = DispositivoRepository.getListaZonas();
		String s = new String();
		String sz = new String();
		for(Transformador tt : t){
			s += tt.getIdTransformador() + ";" + tt.suministroActual() + ";" + tt.getUbicacion().toString() + ";";
		}
		model.put("transformadores", s);
		for(Zona tt : z){
			sz += tt.getRadius() + ";" + tt.getCenter().getLatitude() + ";" + tt.getCenter().getLongitude() + ";";
		}
		model.put("zonas",sz);
		return new ModelAndView(model, "/map.hbs");
	}
}
