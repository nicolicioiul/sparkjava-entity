package ro.enode.auth;
public class Loader {
    /**
     * Constructor.
     */
    public Loader(){
        new AuthResource(new AuthService());
    }
}
