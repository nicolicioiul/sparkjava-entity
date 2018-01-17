package ro.enode.api.Exceptions;

/**
 * Access denied exception.
 */
public class AccessDeniedException extends ApiResponseAbstract implements ApiResponseExceptionInterface {
    {
        statusCode = 403;
    }

    /**
     * Default constructor.
     */
    public AccessDeniedException() {
        super("Resource forbidden.");
    }

    /**
     * Constructor with message.
     */
    public AccessDeniedException(String message) {
        super(message);
    }
}
