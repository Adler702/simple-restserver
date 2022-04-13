// Created by Torben R.
package de.youarefckinqcute.application;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * The type Rest server.
 */
public class RestServer {

    private final SimpleServer simpleServer;

    /**
     * Instantiates a new Rest server.
     *
     * @param simpleServer the simple server
     * @throws IOException the io exception
     */
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
