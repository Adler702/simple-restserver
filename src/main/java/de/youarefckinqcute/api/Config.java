// Created by Torben R.
package de.youarefckinqcute.api;

import lombok.Getter;

/**
 * The type Config.
 */
@Getter
public class Config {

    /**
     * The Server port.
     */
    String serverPort = "5005";
    /**
     * The Mongo host.
     */
    String mongoHost = "localhost";
    /**
     * The Mongo port.
     */
    String mongoPort = "27017";
    /**
     * The Mongo user.
     */
    String mongoUser = "user";
    /**
     * The Mongo password.
     */
    String mongoPassword = "password";

}
