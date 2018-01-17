package ro.enode.api;
import ro.enode.api.Transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service {
    /**
     * Api transformer content.
     */
    protected static final Transformer transformer = Resource.getTransformer();

    /**
     * Api logger object.
     */
    protected static final Logger log = LoggerFactory.getLogger("Api logger");
}
