// Created by Torben R.
package de.youarefckinqcute.api.access;

import lombok.Getter;

import java.util.List;

/**
 * The type Collection access entity.
 */
@Getter
public class CollectionAccessEntity {

    /**
     * The Name.
     */
    String name;
    /**
     * The Allowed methods.
     */
    List<String> allowedMethods;

}
