// Created by Torben R.
package de.youarefckinqcute.api;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import de.youarefckinqcute.application.SimpleServer;
import lombok.Getter;

/**
 * The type Mongo connection.
 */
@Getter
public class MongoConnection {

    private final SimpleServer simpleServer;
    private final MongoClient mongoClient;

    /**
     * Instantiates a new Mongo connection.
     *
     * @param simpleServer the simple server
     */
    public MongoConnection(SimpleServer simpleServer) {
        this.simpleServer = simpleServer;
        String url = "mongodb://"+simpleServer.getConfig().getMongoUser()+":"+simpleServer.getConfig().getMongoPassword()+"@"+simpleServer.getConfig().getMongoHost()+":"+simpleServer.getConfig().getMongoPort()+"/";
        this.mongoClient = new MongoClient(new MongoClientURI(url));
        mongoClient.startSession();
    }
}
