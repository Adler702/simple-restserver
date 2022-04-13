// Created by Torben R.
package de.youarefckinqcute.api.access;

import de.youarefckinqcute.application.SimpleServer;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Access provider.
 */
public class AccessProvider {

    private final SimpleServer simpleServer;

    /**
     * Instantiates a new Access provider.
     *
     * @param simpleServer the simple server
     */
    public AccessProvider(SimpleServer simpleServer) {
        this.simpleServer = simpleServer;
    }

    /**
     * DONT FORGET TO LOAD THE ENTITES IN DER CONFIG
     */
    static List<AccessEntity> entities = new ArrayList<>();

    /**
     * Check boolean.
     *
     * @param key        the key
     * @param database   the database
     * @param collection the collection
     * @param method     the method
     * @return the boolean
     */
    public boolean check(String key, String database, String collection, String method) {
        return entities.stream().filter(accessEntity -> accessEntity.getKey().equals(key))
                .filter(accessEntity -> accessEntity.getGranted().containsKey(database))
                .filter(accessEntity -> accessEntity.getGranted().get(database).containsKey(collection))
                .anyMatch(accessEntity -> accessEntity.getGranted().get(database).get(collection).contains(method));
    }
}
