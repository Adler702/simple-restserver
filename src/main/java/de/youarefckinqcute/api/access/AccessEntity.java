// Created by Torben R.
package de.youarefckinqcute.api.access;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class AccessEntity {

    // bsp "ranksystem"
    String name;
    // bsp ""
    String key;
    // databaseName, Map<collectionName,methodlist>
    Map<String, Map<String, List<String>>> granted;

}
