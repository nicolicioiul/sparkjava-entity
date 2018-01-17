package ro.enode.api.Exceptions;

/**
 * Expception interface.
 */
public interface ApiResponseExceptionInterface {

    /**
     * Get the http status code.
     *
     * @return int
     */
    public int getStatusCode();
}
