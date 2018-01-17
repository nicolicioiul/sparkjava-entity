package ro.enode.api.Exceptions;

/**
 * Access denied exception.
 */
public class NotFoundException extends ApiResponseAbstract implements ApiResponseExceptionInterface {
    {
        statusCode = 404;
    }

    /**
     * Constructor.
     */
    public NotFoundException() {
        super("Resource not found.");
    }
}