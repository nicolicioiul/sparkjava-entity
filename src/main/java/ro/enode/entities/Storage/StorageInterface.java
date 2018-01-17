package ro.enode.entities.Storage;

import ro.enode.entities.Entity.EntityModel;

import java.util.Map;
import java.util.List;

public interface StorageInterface {
    public Map save(EntityModel entity);
    public boolean delete(String id, String type);
    public Map findById(String id, String type);
    public List<Map> findAll(String type);
}
