package presentacion.Spark;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) {
		Spark.host("0.0.0.0");
		Spark.port(Integer.valueOf(System.getenv("PORT")));//9001);
		DebugScreen.enableDebugScreen();
		Router.configure();
	}

}
