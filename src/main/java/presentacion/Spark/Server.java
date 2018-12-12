package presentacion.Spark;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) {
		Spark.port(9001);//Integer.valueOf(System.getenv("PORT")));//
		DebugScreen.enableDebugScreen();
		Router.configure();
	}

}
