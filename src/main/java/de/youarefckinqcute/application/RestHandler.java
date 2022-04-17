// Created by Torben R.
package de.youarefckinqcute.application;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.Getter;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/**
 * The type Rest handler.
 */
@Getter
public class RestHandler implements HttpHandler {

    private final SimpleServer simpleServer;

    /**
     * Instantiates a new Rest handler.
     *
     * @param simpleServer the simple server
     */
    public RestHandler(SimpleServer simpleServer) {
        this.simpleServer = simpleServer;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String[] path = exchange.getRequestURI().toString().split("/");
        MongoDatabase database = simpleServer.getMongoConnection().getMongoClient().getDatabase(path[1]);
        MongoCollection<Document> collection = database.getCollection(path[2]);
        StringBuilder body = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            body.append(line);
            body.append(System.lineSeparator());
        }
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(exchange.getResponseBody());

        String response = "";
        // key, db,collection,method
        if (simpleServer.getConfig().isCheckAccess()) {
            if (!simpleServer.getAccessProvider().check(path[4], path[1], path[2], path[3])) {
                response = "401-unauthorized-request";
            } else {
                response = processMethod(path[3], body.toString(), collection);
            }
        } else {
            response = processMethod(path[3], body.toString(), collection);
        }
        exchange.sendResponseHeaders(200, response.length());
        outputStreamWriter.write(response);
        outputStreamWriter.close();
    }

    private String processMethod(String method, String body, MongoCollection collection) {
        method = method.toLowerCase();
        /* One */
        if (method.equals("insert")) {
            Document document = Document.parse(body);
            collection.insertOne(document);
            return "ok";
        }
        String[] bodyArray = body.split(" ");
        HashMap<String, Object> hashMap = SimpleServer.GSON.fromJson(bodyArray[0], HashMap.class);
        Document document = new Document(hashMap);
        /* One */
        if (method.equals("update")) {
            collection.updateOne(document, new Document("$set", Document.parse(bodyArray[1])));
            return "ok";
        }
        /* One */
        if (method.equals("find")) {
            Document first = (Document) collection.find(document).first();
            return first.toJson();
        }
        /* Many */
        if (method.equals("findmany")) {
            JsonArray jsonArray = new JsonArray();
            MongoCursor iterator = null;
            if (document.isEmpty()) {
                iterator = collection.find().iterator();
            } else {
                 iterator = collection.find(document).iterator();
            }

            while (iterator.hasNext()) {
                jsonArray.add(JsonParser.parseString(((Document) iterator.next()).toJson()));
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("array", jsonArray);
            return SimpleServer.GSON.toJson(jsonObject);
        }
        /* One */
        if (method.equals("delete")) {
            collection.findOneAndDelete(Document.parse(body));
            return "ok";
        }
        /* Many */
        if (method.equals("deletemany")) {
            collection.deleteMany(document);
            return "ok";
        }
        return "404-undefined method";
    }
}
