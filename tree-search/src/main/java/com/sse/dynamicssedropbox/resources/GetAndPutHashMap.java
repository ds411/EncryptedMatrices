package com.sse.dynamicssedropbox.resources;

import java.io.Serializable;
import java.util.HashMap;

public class GetAndPutHashMap extends HashMap<String, Tree> implements Serializable {

    public Tree getAndPut(String key) {
        Tree tree = get(key);
        if(tree == null) {
            tree = new Tree();
            put(key, tree);
        }
        return tree;
    }
}
