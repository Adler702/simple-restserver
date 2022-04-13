// Created by Torben R.
package de.youarefckinqcute.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.youarefckinqcute.api.Config;
import de.youarefckinqcute.api.MongoConnection;
import de.youarefckinqcute.api.access.AccessProvider;
import lombok.Getter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

@Getter
public class SimpleServer {

    public static final Gson GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();
    private final MongoConnection mongoConnection;
    private final AccessProvider accessProvider;
    private Config config;

    public SimpleServer() throws IOException {
        loadConfig();
        this.mongoConnection = new MongoConnection(this);
        this.accessProvider = new AccessProvider(this);
        new RestServer(this);
        readConsole();
    }

    private void loadConfig() throws IOException {
        File file = new File("config.json");
        if (!file.exists()) {
            boolean newFile = file.createNewFile();
            if (!newFile) {
                System.exit(1);
            }
            config = new Config();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(GSON.toJson(config));
            fileWriter.close();
        } else {
            config = GSON.fromJson(String.join("", Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8)), Config.class);
        }
    }

    private void readConsole() {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        if (string.startsWith("exit")) {
            mongoConnection.getMongoClient().close();
            try {
                // Debug purposes only
                Runtime.getRuntime().exec("screen -X -S dataserver kill");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
