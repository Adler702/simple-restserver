// Created by Torben R.
package de.youarefckinqcute.api.access;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.youarefckinqcute.application.SimpleServer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * The type Access provider.
 */
public class AccessProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setLenient().create();

    private final SimpleServer simpleServer;

    List<AccessEntity> entities;

    /**
     * Instantiates a new Access provider.
     *
     * @param simpleServer the simple server
     */
    public AccessProvider(SimpleServer simpleServer) throws IOException {

        this.simpleServer = simpleServer;
        File file = new File("access.json");
        if (!file.exists()) file.createNewFile();
        entities = GSON.fromJson(new FileReader(file), List.class);
    }

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
        return key.equals("*admin") || entities.stream().filter(accessEntity -> accessEntity.getKey().equals(key))
                .filter(accessEntity -> accessEntity.getGranted().containsKey(database))
                .filter(accessEntity -> accessEntity.getGranted().get(database).containsKey(collection))
                .anyMatch(accessEntity -> accessEntity.getGranted().get(database).get(collection).contains(method));
    }
}
