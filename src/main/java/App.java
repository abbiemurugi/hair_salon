import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;


public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String layout = "templates/layout.vtl";

        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        port(port);

        // do this
        get("/", (req, res) -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("template", "templates/index.vtl");
                    return new VelocityTemplateEngine().render(
                            new ModelAndView(model, layout)
                    );
                });

        get("/clients", (req, res) ->{
            Map<String, Object> model = new HashMap<>();
            model.put("clients", Client.all());
            model.put("template", "templates/client.vtl");
            return new VelocityTemplateEngine().render(
                   new ModelAndView(model, layout)
            );
        });

//        get("/stylists/:Stylist_id/Clients/:id", (request, response) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//            Stylist Stylist = Stylist.find(Integer.parseInt(request.params(":Stylist_id")));
//           Client Client = Client.find(Integer.parseInt(request.params(":id")));
//           model.put("Stylist", Stylist);
//           model.put("Client", Client);
//           model.put("template", "templates/Client.vtl");
//            return new ModelAndView(model, layout);
//        }, new VelocityTemplateEngine());

//        post("/stylists/:stylists_id/clients/:id", (request, response) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//            Clients client = Clients.find(Integer.parseInt(request.params("id")));
//            String firstname = request.queryParams("firstname");
//            String lastname = request.queryParams("lastname");
//            String gender = request.queryParams("gender");
//            Stylist stylists = Stylist.find(client.getStylistId());
//            client.update(firstname, lastname, gender);
//            String url = String.format("/stylists/%d/clients/%d", stylists.getId(), client.getId());
//            response.redirect(url);
//            return new ModelAndView(model, layout);
//        }, new VelocityTemplateEngine());
//
//        post("/stylists/:stylist_id/clients/:id/delete", (request, response) -> {
//            HashMap<String, Object> model = new HashMap<String, Object>();
//            Client client = Client.find(Integer.parseInt(request.params("id")));
//            Stylist stylist = Stylist.find(client.getStylistId());
//            client.delete();
//            model.put("stylist", stylist);
//            model.put("template", "templates/stylist.vtl");
//            return new ModelAndView(model, layout);
//        }, new VelocityTemplateEngine());
}
}
