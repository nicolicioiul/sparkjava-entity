package ro.enode.api.Exceptions;

public class ApiResponseAbstract extends Exception {
    /**
     * Default status code, internal error.
     */
    protected int statusCode = 500;

    public ApiResponseAbstract(String message) {
        super(message);
    }

    /**
     * Return status.
     *
     * @return int
     */
    public int getStatusCode(){
        return statusCode;
    }
}
