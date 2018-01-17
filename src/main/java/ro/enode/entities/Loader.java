package ro.enode.entities;

import ro.enode.api.Resource;
import ro.enode.entities.Entity.EntityResource;
import ro.enode.entities.Entity.EntityService;
import ro.enode.entities.Storage.MongodbStorage;
import ro.enode.entities.Storage.StorageInterface;
import com.mongodb.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Loader {
    final private Logger logger = LoggerFactory.getLogger(Loader.class);

    /**
     * Constructor.
     */
    public Loader(){
        try {
            StorageInterface storage = new MongodbStorage(mongo(), Resource.getTransformer());
            new EntityResource(new EntityService(storage, Resource.getTransformer()));
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    /**
     * Database connection.
     * @return DB
     * @throws Exception
     * @throws RuntimeException
     */
    private static DB mongo() throws Exception {
        String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
        if (host == null) {
            MongoClient mongoClient = new MongoClient("localhost");
            return mongoClient.getDB("entitiesapp");
        }
        int port = System.getenv("OPENSHIFT_MONGODB_DB_PORT") != null
                ?  Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT")) : 27017;

        String dbname = System.getenv("OPENSHIFT_APP_NAME");
        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
        MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), mongoClientOptions);
        mongoClient.setWriteConcern(WriteConcern.SAFE);
        DB db = mongoClient.getDB(dbname);
        if (db.authenticate(username, password.toCharArray())) {
            return db;
        } else {
            throw new RuntimeException("Not able to authenticate with MongoDB");
        }
    }
    /**
     * Storage manager.
     * @return StorageInterface
     * @throws RuntimeException
     */
    private static StorageInterface Storage(){
        try {
            return new MongodbStorage(mongo(), Resource.getTransformer());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
