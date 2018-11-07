package presentacion.Spark;

import java.util.HashSet;
import java.util.Set;

import controllers.ClienteController;
import controllers.HomeController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;


public class Router {
	
	static Set<String> publicRoutes = new HashSet<String>();

	public static void configure() {


		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue)
				.build();

		Spark.staticFiles.location("/public");
		setPublicRoutes(publicRoutes);

		Spark.before(SessionHandler.allowed());
		
 		HomeController homeController = new HomeController();
 		ClienteController clienteController = new ClienteController();
  //		IndicadoresController indicadoresController = new IndicadoresController();
// 		MetodologiasController metodologiasController = new MetodologiasController();

 		Spark.get("/", homeController::home, engine);
 		Spark.get("/login", homeController::login, engine);
  		Spark.post("/login", homeController::newSession);
		Spark.get("/wrong-user-or-pass", homeController::wrongLogin, engine);
		Spark.post("/wrong-user-or-pass", homeController::wrongLogin, engine);
		
		Spark.get("/consumo", clienteController::consumo,engine);
		Spark.post("/consumo", clienteController::calcularConsumo,engine);
		Spark.get("/hogar", clienteController::hogar,engine);
		Spark.get("/carga", clienteController::carga,engine);
		Spark.post("/carga", clienteController::carga,engine);
		Spark.get("/simplex", clienteController::simplex,engine);
		Spark.get("/reglas", clienteController::reglasydisp,engine);
		Spark.get("/agregar-disp", clienteController::agregarDispPantalla,engine);
		Spark.post("/agregar-disp", clienteController::agregarDisp,engine);
		Spark.get("/eliminar-disp/:id", clienteController::eliminarDisp,engine);
		Spark.post("/crear-regla", clienteController::crearRegla,engine);
  		
	/*	Spark.get("/cuentas", empresasController::verArchivos,engine);
		Spark.post("/cuentas", empresasController::cargarArchivos,engine);
		Spark.get("/archivo-invalido.hbs", empresasController::archivoInvalido,engine);
 		Spark.get("/empresas", empresasController::home,engine);
 		Spark.post("/empresas", empresasController::aplicar,engine);
  		
 		Spark.get("/metodologias", metodologiasController::home, engine);
 		Spark.post("/metodologias", metodologiasController::aplicar, engine);
 		Spark.get("/metodologias/nueva", metodologiasController::nueva, engine);
 		Spark.post("/metodologias/nueva", metodologiasController::crear, engine);
 		Spark.get("/metodologias/:id", metodologiasController::mostrar, engine);
  		
 		Spark.get("/indicadores", indicadoresController::home, engine);
 		Spark.post("/indicadores", indicadoresController::aplicar, engine);
 		Spark.get("/indicadores/nuevo", indicadoresController::nuevo, engine);
 		Spark.get("/indicadores/error", indicadoresController::error, engine);
 		Spark.post("/indicadores/nuevo", indicadoresController::crear, engine);
 		
 		
 		Spark.get("/condiciones/nueva", metodologiasController::verCondicion, engine);
 		Spark.post("/condiciones/nueva", metodologiasController::crearCondicion, engine);
		Spark.get("/condiciones/condicionTipo1", metodologiasController::verCondicionTipo1, engine);
		Spark.post("/condiciones/condicionTipo1", metodologiasController::crearCondicionTipo1, engine);
		Spark.get("/condiciones/condicionTipo2", metodologiasController::verCondicionTipo2, engine);
		Spark.post("/condiciones/condicionTipo2", metodologiasController::crearCondicionTipo2, engine);
		Spark.get("/condiciones/condicionTipo3", metodologiasController::verCondicionTipo3, engine);
		Spark.post("/condiciones/condicionTipo3", metodologiasController::crearCondicionTipo3, engine);
		Spark.get("/condiciones/condicionTipo4", metodologiasController::verCondicionTipo4, engine);
		Spark.post("/condiciones/condicionTipo4", metodologiasController::crearCondicionTipo4, engine);
*/
	}
	
	public static Boolean isPublic(String route){
		return publicRoutes.contains(route);
	}
	
	private static void setPublicRoutes(Set<String> publicRoutes){
		publicRoutes.add("/login");
		publicRoutes.add("/wrong-user-or-pass");
	}

}