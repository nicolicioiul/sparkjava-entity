
package ro.enode.entities.Storage;

import ro.enode.entities.Entity.EntityModel;
import com.mongodb.*;
import org.bson.types.ObjectId;
import ro.enode.api.Transformer.Transformer;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongodbStorage implements StorageInterface{

    /**
     * DB connection.
     */
    private final DB db;

    /**
     * Transformer object.
     */
    private final Transformer transformer;

    /**
     * Constructor
     * @param db DB
     *           The db object manager.
     */
    public MongodbStorage(DB db, Transformer transformer){
        this.db = db;
        this.transformer = transformer;
    }

    /**
     * Implement save.
     *
     * @param entity EntityModel
     * @return Map
     */
    public Map save(EntityModel entity){
        DBCollection collection = db.getCollection(entity.getType());
        BasicDBObject basicDBObject = new BasicDBObject(entity.getData());
        String id = null;
        if (entity.getId() != null) {
            BasicDBObject searchQuery = new BasicDBObject().append("_id",  new ObjectId(entity.getId()));
            collection.update(searchQuery, basicDBObject);
            id = entity.getId();
        } else {
            collection.insert(basicDBObject);
            id = basicDBObject.get("_id").toString();
        }
        return findById(id, entity.getType());
    }

    /**
     * Implement delete.
     *
     * @param id String
     * @param type String
     * @return boolean
     */
    public boolean delete(String id, String type) {
        DBCollection collection = db.getCollection(type);
        BasicDBObject basicDBObject = (BasicDBObject)collection.findOne(new BasicDBObject("_id", new ObjectId(id)));
        collection.remove(basicDBObject);
        return true;
    }

    /**
     * Implement findById.
     *
     * @param id String
     * @param type String
     * @return Map
     */
    public Map findById(String id, String type){
        DBCollection collection = db.getCollection(type);
        BasicDBObject basicDBObject = (BasicDBObject)collection.findOne(new BasicDBObject("_id", new ObjectId(id)));
        if(basicDBObject != null) {
            basicDBObject.remove("_id");
            Map map = transformer.toMap(basicDBObject);
            map.put("id", id);
            return map;
        }
        return  null;
    }

    /**
     * Implement findAll.
     * @param type
     * @return
     */
    public List<Map> findAll(String type){
        List<Map> entities = new ArrayList<>();
        DBCollection collection = db.getCollection(type);
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            entities.add(transformer.toMap(dbObjects.next()));
        }
        return entities;

    }
}
