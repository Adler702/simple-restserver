// Created by Torben R.
package de.youarefckinqcute.api.access;

import lombok.Getter;

import java.util.List;

/**
 * The type Database access entity.
 */
@Getter
public class DatabaseAccessEntity {

    /**
     * The Name.
     */
    String name;
    /**
     * The Accessed collections.
     */
    List<CollectionAccessEntity> accessedCollections;
}
