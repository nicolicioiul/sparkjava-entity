package ro.enode.auth;

import ro.enode.api.Exceptions.AccessDeniedException;
import ro.enode.api.Resource;
import spark.Spark;

import static spark.Spark.*;

public class AuthResource extends Resource {
    /**
     * Auth service.
     */
    private AuthService authService;

    /**
     * Constructor
     * @param authService
     */
    public AuthResource(AuthService authService){
        this.authService = authService;
        setupEndPoints();
    }

    /**
     * Implement Resource endpoints, add routes
     */
    private void setupEndPoints(){
        // Before any request, ask for auth.
        before("/*", (req, res) -> {
            // Open session.
            req.session();
            // If it not requested the auth path.
            if(! (API_CONTEXT_BASE + API_CONTEXT_VERSION + "/auth").equals(req.uri())){
                if(req.session().attribute("uid") == null){
                    throw new AccessDeniedException("Missing auth identifier");
                }
            }
        });
        // Login auth path.
        path(API_CONTEXT_BASE, () -> {
            path(API_CONTEXT_VERSION, ()->{
                Spark.post("/auth", API_CONTENT_ACCEPT, (req, res) -> {
                            if(authService.create(req.body())){
                                req.session(true);
                                req.session().attribute("uid", 1);
                                return "Auth succesfull";
                            }
                            req.session().removeAttribute("uid");
                            req.session().invalidate();
                            return "Auth unsuccesfull";
                        }
                        , getTransformer());
            });
        });
    }
}
