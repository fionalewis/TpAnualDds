package presentacion.Spark;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) {
		Spark.port(9001);
		DebugScreen.enableDebugScreen();
		Router.configure();
	}

}
