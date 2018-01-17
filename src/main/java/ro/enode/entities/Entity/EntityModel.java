package ro.enode.entities.Entity;

import java.util.Map;

public class EntityModel {
    /**
     * Data map value.
     */
    private Map data;

    /**
     * Get id
     *
     * @return String
     */
    public String getId() {
        String id = null;
        if (data.get("id") != null) {
            id = data.get("id").toString();
        }
        return id;
    }

    /**
     * Get entity type
     *
     * @return String
     */

    public String getType() {
        String type = null;
        if (data.get("type") != null) {
            type = data.get("type").toString();
        }
        return type;
    }

    /**
     * Get data.
     *
     * @return Map
     */
    public Map getData() {
        return data;
    }

    /**
     * Set id.
     *
     * @param id String
     */
    public void setId(String id) {
        data.put("id", id);
    }

    /**
     * Set data into entity.
     *
     * @param data Map
     */
    public EntityModel(Map data) {
        if (data.get("type") == null) {
            throw new IllegalArgumentException("The entity type is missing.");
        }
        this.data = data;
    }
}
