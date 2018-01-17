package ro.enode.api.Transformer;

import com.google.gson.Gson;
import spark.ResponseTransformer;

import java.util.Map;

public class JsonTransformer extends Transformer implements ResponseTransformer  {

    private static Gson gson = new Gson();

    @Override
    public String render(Object object) {
        return gson.toJson(object);
    }

    /**
     *
     * @param json
     * @return
     */
    public Map toMap(String json){
        return gson.fromJson(json, Map.class);
    }

    /**
     *
     * @param object
     * @return
     */
    public Map toMap(Object object){
        return toMap(gson.toJson(object));
    }

    /**
     *
     * @param object
     * @return
     */
    public String toString(Object object){
        return render(object);
    }

}