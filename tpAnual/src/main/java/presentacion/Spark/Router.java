package presentacion.Spark;

import java.util.HashSet;
import java.util.Set;

import controllers.AdminController;
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
 		AdminController adminController = new AdminController();
//		IndicadoresController indicadoresController = new IndicadoresController();
// 		MetodologiasController metodologiasController = new MetodologiasController();

 		Spark.get("/", homeController::home, engine);
 		Spark.get("/login", homeController::login, engine);
		Spark.post("/login", homeController::newSession);
		  
		Spark.get("/login-admin", homeController::loginAdmin, engine);
		Spark.post("/login-admin", homeController::newSessionAdmin);

		Spark.get("/wrong-user-or-pass", homeController::wrongLogin, engine);
		Spark.post("/wrong-user-or-pass", homeController::wrongLogin, engine);
		Spark.get("/logout", homeController::logout,engine);

		Spark.get("/map", homeController::map, engine);
		
		Spark.get("/hogar_consumo", adminController::hogar_consumo,engine);
		//Spark.post("/hogar_consumo", adminController::hogar_consumo);
		Spark.get("/reportes", adminController::reportes,engine);
		Spark.post("/reporteHogar", adminController::reporteHogar,engine);
		Spark.post("/reporteTransformador", adminController::reporteTransformador,engine);
		Spark.get("/carga", adminController::carga,engine);
		Spark.post("/carga", adminController::cargarArchivo,engine);
		Spark.post("/nuevo_disp", adminController::nuevoDisp,engine);
		
		Spark.get("/consumo", clienteController::consumo,engine);
		Spark.post("/consumo", clienteController::calcularConsumo,engine);
		Spark.get("/hogar", clienteController::hogar,engine);
		//Spark.get("/carga", clienteController::carga,engine);
		//Spark.post("/carga", clienteController::carga,engine);
		Spark.get("/simplex", clienteController::simplex,engine);
		Spark.get("/reglas", clienteController::reglasydisp,engine);
		Spark.get("/agregar-disp", clienteController::agregarDispPantalla,engine);
		Spark.post("/agregar-disp", clienteController::agregarDisp,engine);
		Spark.post("/eliminar-disp", clienteController::eliminarDisp,engine);
		Spark.post("/crear-regla", clienteController::crearRegla,engine);
		
	}
	
	public static Boolean isPublic(String route){
		return publicRoutes.contains(route);
	}
	
	private static void setPublicRoutes(Set<String> publicRoutes){
		publicRoutes.add("/login");
		publicRoutes.add("/login-admin");
		publicRoutes.add("/wrong-user-or-pass");
		publicRoutes.add("/map");
	}

}