// Created by Torben R.
package de.youarefckinqcute.application;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RestServer {

    private final SimpleServer simpleServer;

    public RestServer(SimpleServer simpleServer) throws IOException {
        this.simpleServer = simpleServer;
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(Integer.parseInt(simpleServer.getConfig().getServerPort())), 0);
        httpServer.createContext("/", new RestHandler(simpleServer));
        httpServer.setExecutor(null);
        httpServer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            httpServer.stop(200);
        }));
    }
}
