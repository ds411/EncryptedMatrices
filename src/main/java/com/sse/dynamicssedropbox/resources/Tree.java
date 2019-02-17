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

    public void simpleRebuild(BigInteger[][] values) {
        //lg(n) + 1 levels
        TreeLevel[] newLevels = new TreeLevel[(int)Math.ceil(Math.log(values.length) / Math.log(2)) + 1];
        for(int i = 0; i < newLevels.length; i++) {
            newLevels = new TreeLevel[i];
        }
        Value[] newValues = new Value[values.length];
        for(int i = 0; i < newValues.length; i++) {
            newValues[i] = new Value(values[i][0], values[i][1], values[i][2]);
        }
        newLevels[newLevels.length-1].values = newValues;
        levels = newLevels;
    }
}
