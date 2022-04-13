// Created by Torben R.
package de.youarefckinqcute.api.access;

import lombok.Getter;

import java.util.List;

@Getter
public class DatabaseAccessEntity {

    String name;
    List<CollectionAccessEntity> accessedCollections;
}
