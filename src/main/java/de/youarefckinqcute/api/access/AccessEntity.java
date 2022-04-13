// Created by Torben R.
package de.youarefckinqcute.api.access;

import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * The type Access entity.
 */
@Getter
public class AccessEntity {

    /**
     * The Name.
     */
// bsp "ranksystem"
    String name;
    /**
     * The Key.
     */
// bsp ""
    String key;
    /**
     * The Granted.
     */
// databaseName, Map<collectionName,methodlist>
    Map<String, Map<String, List<String>>> granted;

}
