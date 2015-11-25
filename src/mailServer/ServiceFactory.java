package mailServer;

import java.util.HashMap;
import java.util.Map;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

//abstract factory method
public class ServiceFactory{
	public static final Map<String, RESTService> factoryServiceMap = new HashMap<String, RESTService>();
	
	public void init(Router router){
		
	}
}
