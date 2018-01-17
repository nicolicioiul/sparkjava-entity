package ro.enode.api.Transformer;

import java.util.Map;

public abstract class Transformer {
   abstract public Map toMap(String json);
   abstract public Map toMap(Object object);
}
