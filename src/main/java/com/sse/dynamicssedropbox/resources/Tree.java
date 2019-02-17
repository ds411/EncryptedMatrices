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

    public void update(BigInteger[] values) {
        assert values.length == 3;
        assert levels[0].values[0].hkey == null;

        levels[0].values[0] = new Value(values[0], values[1], values[2]);
    }

    public void simpleRebuild(BigInteger[][] values) {
        int level = firstEmptyLevel();
        for(int i = 0; i < level; i++) {
            levels[i] = new TreeLevel(i);
        }
        TreeLevel newLevel = new TreeLevel(level);
        for(int i = 0; i < newLevel.values.length; i++) {
            newLevel.values[i] = new Value(values[i][0], values[i][1], values[i][2]);
        }
    }

    public int firstEmptyLevel() {
        for(int i = 0; i < levels.length; i++) {
            if(levels[i].values[i] == null) return i;
        }
        return levels.length;
    }
}
