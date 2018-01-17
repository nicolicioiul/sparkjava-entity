package ro.enode.entities.Storage;

import ro.enode.entities.Entity.EntityModel;

import java.util.Map;
import java.util.List;

public interface StorageInterface {
    Map save(EntityModel entity);

    boolean delete(String id, String type);

    Map findById(String id, String type);

    List<Map> findAll(String type);
}
