package mailServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.mail.MessagingException;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import utilities.Config;

public class VertxMailClient extends AbstractVerticle{
	/*
	 * ADDRESS:irgmail@gmx.fr
SERVER:imap.gmx.fr
PROTOCOL:imaps
LOGIN:irgmail@gmx.fr
PASSWORD:irgmail/ir2
	*/
	@Override
	public void start() throws MessagingException, IOException{
		
		final Path path = Paths.get("src/Files/config.txt");
		//System.out.println(path.toAbsolutePath());
		//Files.lines(path).forEach(System.out::println);
		
		System.out.println("--1--");
		MailStorage mailStore = new MailStorage(new Config(path));
		System.out.println("--2--");
		mailStore.fetchHeaders();
		System.out.println("--3--");
		Router router = Router.router(vertx);
		// route to JSON REST APIs
		// Load possible services
		//router.get("/mails/").handler()
		
		// otherwise serve static pages
		router.route().handler(StaticHandler.create());

		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
		System.out.println("listen on port 8080");
	}

	public static void main(String[] args) {
		// development option, avoid caching to see changes of
		// static files without having to reload the application,
		// obviously, this line should be commented in production
		System.setProperty("vertx.disableFileCaching", "true");

		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new VertxMailClient());
	}

}
