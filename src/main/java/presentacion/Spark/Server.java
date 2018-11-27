package presentacion.Spark;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) {
<<<<<<< HEAD:tpAnual/src/main/java/presentacion/Spark/Server.java
		Spark.port(8080);
=======
		Spark.port(Integer.valueOf(System.getenv("PORT")));//9001);
>>>>>>> herokubdd:src/main/java/presentacion/Spark/Server.java
		DebugScreen.enableDebugScreen();
		Router.configure();
	}

}
