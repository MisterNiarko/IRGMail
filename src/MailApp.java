import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

public class MailApp extends AbstractVerticle {
  static class Mail {
    private final String subject;
    private final String from;
    private final String body;
    
    public Mail(String subject, String from, String body) {
      this.subject = requireNonNull(subject);
      this.from = requireNonNull(from);
      this.body = requireNonNull(body);
    }
    
    public String getSubject() {
      return subject;
    }
    
    public String toSimpleJSONString() {
      return "{" +
         "\"subject\": \"" + subject + "\", " +
         "\"from\": \"" + from + "\"" +
       "}";
    }
    
    public String toFullJSONString() {
      return "{" +
         "\"subject\": \"" + subject + "\", " +
         "\"from\": \"" + from + "\", " +
         "\"body\": \"" + body + "\"" +
       "}";
    }
  }

  private static void addFakeMails(ArrayList<Mail> mails) {
    mails.add(new Mail("hello",  "bob@foo.com", "hello boy !"));
    mails.add(new Mail("hello2", "bob@foo.com", "Are you there, old boy !"));
    mails.add(new Mail("hello",  "bob@foo.com", "knock knock, who's there ?"));
  }
  
  private final ArrayList<Mail> mails = new ArrayList<>();

  @Override
  public void start() {
    addFakeMails(mails);

    Router router = Router.router(vertx);
    // route to JSON REST APIs 
    router.get("/mails").handler(this::getAllMails);
    router.get("/mails/:id").handler(this::getAMail);
    // otherwise serve static pages
    router.route().handler(StaticHandler.create());

    vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    System.out.println("listen on port 8080");
  }
  
  private void getAMail(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    String id = routingContext.request().getParam("id");
    int index;
    if (id == null || (index = Integer.parseInt(id)) < 0 || index >= mails.size()) {
      response.setStatusCode(404).end();
      return;
    } 
    routingContext.response()
       .putHeader("content-type", "application/json")
       .end(mails.get(index).toFullJSONString());
  }

  private void getAllMails(RoutingContext routingContext) {
    routingContext.response()
       .putHeader("content-type", "application/json")
       .end(mails.stream().map(Mail::toSimpleJSONString).collect(joining(", ", "[", "]")));
    // a modifier?
  }
  
  public static void main(String[] args) {
    // development option, avoid caching to see changes of
    // static files without having to reload the application,
    // obviously, this line should be commented in production
    System.setProperty("vertx.disableFileCaching", "true");
    
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MailApp());
  }
}
