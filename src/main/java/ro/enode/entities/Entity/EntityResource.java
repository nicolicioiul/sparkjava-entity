package ro.enode.entities.Entity;

import static spark.Spark.*;

import ro.enode.api.Resource;
import spark.Spark;

public class EntityResource extends Resource {
    private final EntityService entityService;

    public EntityResource(EntityService entityService) {
        this.entityService = entityService;
        setupEndpoints();
    }

    /**
     * Implement Entities endpoints, add routes
     */
    private void setupEndpoints() {
        path(API_CONTEXT_BASE, () -> {
            path(API_CONTEXT_VERSION, () -> {

                Spark.post("/entity/:type", API_CONTENT_ACCEPT, (request, response) ->
                                entityService.create(request.params(":type"), request.body())
                        , getTransformer());

                Spark.get("/entity/:type/:id", API_CONTENT_ACCEPT, (request, response)
                                -> entityService.get(request.params(":id"), request.params(":type")),
                        getTransformer());

                Spark.get("/entity/:type", API_CONTENT_ACCEPT, (request, response)
                                -> entityService.findAll(request.params(":type"))
                        , getTransformer());

                Spark.put("/entity/:type/:id", API_CONTENT_ACCEPT, (request, response)
                                -> entityService.update(request.params(":type"), request.params(":id"), request.body())
                        , getTransformer());

                Spark.delete("/entity/:type/:id", API_CONTENT_ACCEPT, (request, response)
                                -> entityService.delete(request.params(":type"), request.params(":id"))
                        , getTransformer());
            });
        });
    }
}
