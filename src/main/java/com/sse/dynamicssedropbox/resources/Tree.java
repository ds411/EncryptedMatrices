package com.sse.dynamicssedropbox.resources;

import java.math.BigInteger;
import java.util.*;

public class Tree {

    TreeLevel[] levels;

    final short ADD = 0;
    final short DEL = 1;

    public Tree(int height) {
        levels = new TreeLevel[height + 1];
        for(int i = 0; i <= height; i++) {
            levels[i] = new TreeLevel(i);
        }
    }

    public Set<BigInteger> search(BigInteger[] tokens) {
        assert tokens.length == levels.length;

        Set<BigInteger> idSet = new HashSet<>();

        for(int i = 0; i < tokens.length; i++) {
            idSet.add(levels[i].lookup(tokens[i], ADD));
        }
        for(int i = 0; i < tokens.length; i++) {
            idSet.remove(levels[i].lookup(tokens[i], DEL));
        }

        idSet.remove(null);

        return idSet;
    }

}
