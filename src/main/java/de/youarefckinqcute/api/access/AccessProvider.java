// Created by Torben R.
package de.youarefckinqcute.api.access;

import de.youarefckinqcute.application.SimpleServer;

import java.util.ArrayList;
import java.util.List;

public class AccessProvider {

    private final SimpleServer simpleServer;

    public AccessProvider(SimpleServer simpleServer) {
        this.simpleServer = simpleServer;
    }

    /**
     * DONT FORGET TO LOAD THE ENTITES IN DER CONFIG
     */
    static List<AccessEntity> entities = new ArrayList<>();

    public boolean check(String key, String database, String collection, String method) {
        return entities.stream().filter(accessEntity -> accessEntity.getKey().equals(key))
                .filter(accessEntity -> accessEntity.getGranted().containsKey(database))
                .filter(accessEntity -> accessEntity.getGranted().get(database).containsKey(collection))
                .anyMatch(accessEntity -> accessEntity.getGranted().get(database).get(collection).contains(method));
    }
}
