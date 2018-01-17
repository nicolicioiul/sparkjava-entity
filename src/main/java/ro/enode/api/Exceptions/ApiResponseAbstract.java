package ro.enode.api.Exceptions;

public class ApiResponseAbstract extends Exception {
    /**
     * Default status code, internal error.
     */
    int statusCode = 500;

    ApiResponseAbstract(String message) {
        super(message);
    }

    /**
     * Return status.
     *
     * @return int
     */
    public int getStatusCode() {
        return statusCode;
    }
}
