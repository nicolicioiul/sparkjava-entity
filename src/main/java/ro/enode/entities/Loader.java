package ro.enode.entities;

import ro.enode.api.Resource;
import ro.enode.entities.Entity.EntityResource;
import ro.enode.entities.Entity.EntityService;
import ro.enode.entities.Storage.MongodbStorage;
import ro.enode.entities.Storage.StorageInterface;
import com.mongodb.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.StringWriter;
import java.io.PrintWriter;

public class Loader {
    /**
     * Logger.
     */
    final private Logger logger = LoggerFactory.getLogger(Loader.class);

    /**
     * Constructor.
     */
    public Loader() {
        try {
            StorageInterface storage = new MongodbStorage(mongo(), Resource.getTransformer());
            new EntityResource(new EntityService(storage, Resource.getTransformer()));
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            logger.error("Error on load entity service:" + e.getMessage() + " e:"+ e.getClass() + " debug:"+exceptionAsString);
        }
    }

    /**
     * Database connection.
     *
     * @return DB
     * @throws Exception
     * @throws RuntimeException
     */
    private static DB mongo() throws Exception {
        try {
            String host = System.getenv("MONGODB_DB_HOST") != null
                   ? System.getenv("MONGODB_DB_HOST") : "localhost";
            int port = System.getenv("MONGODB_DB_PORT") != null
                    ? Integer.parseInt(System.getenv("MONGODB_DB_PORT")) : 27017;

            String dbname = System.getenv("MONGODB_DB_NAME");
            MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
            MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), mongoClientOptions);
            mongoClient.setWriteConcern(WriteConcern.SAFE);
            // Auth if is required.
            String username = System.getenv("MONGODB_DB_USERNAME");
            String password = System.getenv("MONGODB_DB_PASSWORD");

            DB db = mongoClient.getDB(dbname);
            if (username != null) {
                db.authenticate(username, password.toCharArray());
            }
            return db;
        }catch (Exception e){
            throw new RuntimeException("Not able to authenticate with MongoDB:" + e.getMessage());
        }
    }

    /**
     * Storage manager.
     *
     * @return StorageInterface
     * @throws RuntimeException
     */
    private static StorageInterface Storage() {
        try {
            return new MongodbStorage(mongo(), Resource.getTransformer());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
