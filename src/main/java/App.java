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

        get("/stylists", (req, res) ->{
            Map<String, Object> model = new HashMap<>();
            model.put("stylists", Stylist.all());
            model.put("template", "templates/stylist.vtl");
            return new VelocityTemplateEngine().render(
                   new ModelAndView(model, layout)
            );
        });

        get("/stylist/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/stylist-form.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        post("/stylists", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String firstName = req.queryParams("firstName");
            String middleName = req.queryParams("middleName");
            String lastName = req.queryParams("lastName");
            String residence = req.queryParams("residence");
            String age = req.queryParams("age");
            String email = req.queryParams("email");
            Stylist stylist = new Stylist(firstName, middleName, lastName, residence,Integer.parseInt(age), email);
            stylist.save();
//            model.put("template", "templates/stylist.vtl");
            res.redirect("/stylists");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/stylist/:id/details", (req, res) ->{
            Map<String, Object> model = new HashMap<>();
            Stylist stylist = Stylist.find(Integer.parseInt(req.params(":id")));
            model.put("clients", stylist.every());
            model.put("stylist", stylist);
            model.put("template", "templates/stylist-details.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });


        post("/stylist/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Stylist stylist = Stylist.find(Integer.parseInt(req.params(":id")));
            stylist.delete();
            model.put("template", "templates/stylist-detail-success-delete.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/stylist/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Stylist stylist = Stylist.find(Integer.parseInt(req.params(":id")));
            model.put("stylist", stylist);
            model.put("template", "templates/stylist-edit.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        post("/stylists/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String firstName = req.queryParams("firstName");
            String middleName = req.queryParams("middleName");
            String lastName = req.queryParams("lastName");
            String residence = req.queryParams("residence");
            String age = req.queryParams("age");
            String email = req.queryParams("email");
            int id = Integer.parseInt(req.queryParams(":id"));
            Stylist stylist = Stylist.find(Integer.parseInt(req.params(":id")));
            stylist.update(firstName, middleName, lastName, residence, Integer.parseInt(age), email,id);
            String url = String.format("/stylist/%d",stylist.getId());
            res.redirect(url);
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/clients", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("clients", Client.all());
            model.put("template", "templates/clients.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/clients/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("stylists",Stylist.all());
            model.put("template", "templates/client-form.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        post("/clients", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            String gender = req.queryParams("gender");
            String stylistId = req.queryParams("stylistId");
            Client client = new Client(firstName, lastName, gender,Integer.parseInt(stylistId));
            client.save();
            model.put("template", "templates/client-success.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/client/:id/details", (req, res) ->{
            Map<String, Object> model = new HashMap<>();
            Client client = Client.find(Integer.parseInt(req.params(":id")));
            model.put("client", client);
            model.put("template", "templates/client-details.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });


        get("/client/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Client client = Client.find(Integer.parseInt(req.params(":id")));
            model.put("client", client);
            model.put("template", "templates/client.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        post("/client/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Client client = Client.find(Integer.parseInt(req.params(":id")));
            client.delete();
            model.put("template", "templates/client-delete-success.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/client/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Client client = Client.find(Integer.parseInt(req.params(":id")));
            model.put("client", client);
            model.put("stylists",Stylist.all());
            model.put("template", "templates/client-edit.vtl");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        post("/client/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            String gender = req.queryParams("gender");
            String stylistId = req.queryParams("stylistId");
            Client client = Client.find(Integer.parseInt(req.params(":id")));
           // client.update(firstName,lastName,gender,Integer.parseInt(stylistId));
            String url = String.format("/client/%d",client.getId());
            res.redirect(url);
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
