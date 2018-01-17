package ro.enode.entities.Entity;

import ro.enode.api.Service;
import ro.enode.api.Transformer.Transformer;
import ro.enode.entities.Storage.StorageInterface;
import ro.enode.api.Exceptions.NotFoundException;

import java.util.List;
import java.util.Map;

public class EntityService extends Service {

    /**
     * Storage service.
     */
    private StorageInterface storage;
    /**
     * Content transformer.
     */
    private final Transformer transformer;
    /**
     * EntityModel
     */
    private EntityModel entity;
    /**
     * Constructor
     * @param storage StorageInterface
     * @param transformer Transformer
     */
    public EntityService(StorageInterface storage, Transformer transformer){
        this.storage = storage;
        this.transformer = transformer;
    }

    /**
     * Create an entity.
     * @param type String
     * @param body String
     * @return Map
     */
    public Map create(String type, String body){
        entity = toEntity(type, body);
        return storage.save(entity);
    }

    /**
     * Update entity
     * @param type String
     * @param id String
     * @param body String
     * @return Map
     */
    public Map update(String type, String id, String body) throws NotFoundException {
        if(!exists(id, type)) {
            throw new NotFoundException();
        }
        EntityModel entity = toEntity(type, body);
        entity.setId(id);
        return storage.save(entity);
    }

    /**
     * Delete entity.
     * @return boolean
     * @throws NotFoundException
     */
    public boolean delete( String type, String id) throws NotFoundException{
        if(!exists(id, type)) {
            throw new NotFoundException();
        }
        return storage.delete(id, type);
    }

    /**
     * Convert string to Map
     * @param type String
     * @param body String
     * @return
     */
    public EntityModel toEntity(String type, String body){
        Map map = transformer.toMap(body);
        map.put("type", type);
        EntityModel entityModel = new EntityModel(map);
        return entityModel;
    }

    /**
     * Convert string to Map
     * @param body String
     * @return
     */
    public EntityModel toEntity(String body){
        Map map = transformer.toMap(body);
        EntityModel entityModel = new EntityModel(map);
        return entityModel;
    }

    /**
     * Find by id.
     *
     * @param id string
     * @param type
     * @return Map
     */
    public Map findById(String id, String type) {
        return storage.findById(id, type);
    }

    /**
     * Get on item by id.
     *
     * @param id string
     * @param type
     * @return Map
     */
    public Map get(String id, String type) throws NotFoundException{
        if(!exists(id, type)) {
            throw new NotFoundException();
        }
        return storage.findById(id, type);
    }
    /**
     * Find all.
     * @return List<Map>
     */
    public List<Map> findAll(String type) {
        return storage.findAll(type);
    }

    /**
     * Check if model exists.
     * @param id String
     * @param type String
     * @return
     */
    private boolean exists(String id, String type){
        Map map = this.findById(id, type);
        if(map != null) {
           return  true;
        }
        return false;
    }
}
