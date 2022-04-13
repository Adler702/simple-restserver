// Created by Torben R.
package de.youarefckinqcute.api.access;

import lombok.Getter;

import java.util.List;

@Getter
public class CollectionAccessEntity {

    String name;
    List<String> allowedMethods;

}
