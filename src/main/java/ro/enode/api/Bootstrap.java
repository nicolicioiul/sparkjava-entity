package ro.enode.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.enode.api.Exceptions.AccessDeniedException;
import ro.enode.api.Exceptions.NotFoundException;
import ro.enode.entities.Loader;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class Bootstrap {
    /**
     *  Running server ip address
     */
    private static String IP_ADDRESS;
    /**
     *  Running server port
     */
    private static int PORT;
    /**
     * App logger.
     */
    protected static Logger log;

    public static void main(String[] args) {
        // Get server setup.
        IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "localhost";
        PORT = System.getenv("OPENSHIFT_DIY_PORT") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) : 8080;
        // Setup the server env.
        ipAddress(IP_ADDRESS);
        port(PORT);
        // Setup Log.
        log = LoggerFactory.getLogger("Api logger");

        before("/*", (req, res) -> {
            res.type("application/json");
            // Log the request
            log.info("Received api call method:" + req.requestMethod() + " path: " + req.uri());

        });

        after((req, res) -> {
            log.info("Response api call method:" + req.requestMethod() + " path: " + req.uri() + " response:" + res.body());
        });

        // Using route.
        notFound((req, res) -> {
            log.info("Route not found for: " + req.requestMethod() + " path: " + req.uri());
            return res.body();
        });

        // Not found.
        exception(NotFoundException.class, (NotFoundException e, Request req, Response res) -> {
            res.status(e.getStatusCode());
            res.body("{\"message\":" + "\"" + e.getMessage() + "\"}");
            log.info("Exception not found for: " + req.requestMethod() + " path: " + req.uri() + " message: " + e.getMessage());
        });

        // Access denied.
        exception(AccessDeniedException.class, (AccessDeniedException e, Request req, Response res) -> {
            res.status(e.getStatusCode());
            res.body("{\"message\":" + "\"" + e.getMessage() + "\"}");
            log.info("Exception forbidden: " + req.requestMethod() + " path: " + req.uri() + " message: " + e.getMessage());
        });

        // Internal server error.
        exception(Exception.class, (Exception e, Request req, Response res) -> {
            res.status(500);
            res.body("{\"message\":" + "\"" + e.getMessage() + "\"}");
            log.info("Internal server error: " + req.requestMethod() + " path: " + req.uri() + " message: " + e.getMessage());
        });

        // Load auth.
        // com.epoint.auth.Loader authLoader = new com.epoint.auth.Loader();

        // Load entities.
        Loader entitiesLoader = new Loader();
        // Continue to add, if we need.
    }
}
