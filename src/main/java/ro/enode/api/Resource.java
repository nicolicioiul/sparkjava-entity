package ro.enode.api;

import ro.enode.api.Transformer.JsonTransformer;
import ro.enode.api.Transformer.Transformer;
import org.slf4j.*;

abstract public class Resource {
    /**
     * Api base entry point.
     */
    protected static final String API_CONTEXT_BASE = "/api";
    /**
     * Api version added to path.
     */
    protected static final String API_CONTEXT_VERSION = "/v1";

    /**
     * Api accept content.
     */
    protected static final String API_CONTENT_ACCEPT = "application/json";

    /**
     * Api transformer content.
     */
    protected static final Transformer transformer = new JsonTransformer();

    /**
     * Api logger object.
     */
    protected static final Logger log = LoggerFactory.getLogger("Api logger");

    /**
     * Get transformer.
     *
     * @return
     */
    public static JsonTransformer getTransformer() {
        return (JsonTransformer) transformer;
    }

}
