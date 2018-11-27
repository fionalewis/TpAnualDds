package presentacion.Spark;

import spark.Filter;
import spark.Request;
import spark.Response;

public class SessionHandler {
	
	public static Filter allowed(){

		    return new Filter() {
		    	@Override
		    	public void handle(Request req, Response res){
		    	boolean authenticated = req.session().attribute("user") != null;
		    
			    if (!authenticated && !Router.isPublic(req.pathInfo())) {
			    	res.redirect("/login");
			    }
	    	}
	    };
	}


}
