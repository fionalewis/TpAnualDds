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
		//TODO ir a buscar a la base de datos al cliente posta
		//Cliente cliente = ClienteFactory.getCliente(nroDoc);
		//user = ClienteFactory.getCliente(username);
		user = ClienteRepository.obtenerCliente(username);
		//	user.setUserName("user");
		//	user.setPassword("pass");
		}
		catch (NoResultException e)
		{
			res.redirect("/archivo-incorrecto");
		}
		if(user!= null)
		{
			if(user.loginCorrecto(pass))
			{
				Session sesion = req.session(true);
				sesion.attribute("user", username);
				sesion.attribute("esAdmin",false);
				res.redirect("/");
			}
			else { res.redirect("/wrong-user-or-pass"); }
		}
		else
		{
			res.redirect("/wrong-user-or-pass");
		}
		
		return null;
	}

	public Void newSessionAdmin(Request req, Response res){
		//creo que se podría reusar la otra pero por las dudas hoy no lo quiero tocar
		String username = req.queryParams("user");
		String pass = req.queryParams("password");
		Administrador user = new Administrador();
		try
		{
		//TODO ir a buscar a la base de datos al cliente posta
		//Cliente cliente = ClienteFactory.getCliente(nroDoc);
		//user = ClienteFactory.getCliente(username);
		user = AdministradorRepository.getAdminConNombre(username);
		//	user.setUserName("user");
		//	user.setPassword("pass");
		}
		catch (NoResultException e)
		{
			res.redirect("/archivo-incorrecto");
		}
		if(user.loginCorrecto(pass))
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
		return new ModelAndView(null, "/map.hbs");
	}

	public ModelAndView mapi (Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		System.out.println("Entra");
		List<Transformador> t = TransformadorRepository.getListaTranformadores();
		System.out.println("lee transf ");
		List<Zona> z = DispositivoRepository.getListaZonas();
		System.out.println("lee disps");
		String s = new String();
		String sz = new String();
		int clearbd = 0;
		// for(Transformador tt : t){
		// 	s += tt.getIdTransformador() + ";" + tt.suministroActual() + ";" + tt.getUbicacion().toString() + ";";
		// }
		while (clearbd<9){
			s += t.get(clearbd).getIdTransformador() + ";" + t.get(clearbd).suministroActual() + ";" + t.get(clearbd).getUbicacion().toString() + ";";
			sz += z.get(clearbd).getRadius() + ";" + z.get(clearbd).getCenter().getLatitude() + ";" + z.get(clearbd).getCenter().getLongitude() + ";";
			System.out.println("sum act "+ t.get(clearbd).suministroActual() );
			clearbd++;
		}
		System.out.println("Salió del loop");
		model.put("transformadores", s);
		// for(Zona tt : z){
		// 	sz += tt.getRadius() + ";" + tt.getCenter().getLatitude() + ";" + tt.getCenter().getLongitude() + ";";
		// }
		model.put("zonas",sz);
		return new ModelAndView(model, "/map.hbs");
	}
}
