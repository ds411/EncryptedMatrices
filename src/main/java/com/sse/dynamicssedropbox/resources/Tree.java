package com.sse.dynamicssedropbox.resources;

import java.math.BigInteger;
import java.util.*;

public class Tree {

    private ArrayList<TreeLevel> levels;

    private final short ADD = 0;
    private final short DEL = 1;

    public Tree() {
        levels = new ArrayList<>(1);
        levels.add(new TreeLevel(0));
    }

    public Set<BigInteger> search(BigInteger[] tokens) {
        assert tokens.length == levels.size();

        HashSet<BigInteger> idSet = new HashSet<>();

        for(int i = 0; i < tokens.length; i++) {
            idSet.add(levels.get(i).lookup(tokens[i], ADD));
        }
        for(int i = 0; i < tokens.length; i++) {
            idSet.remove(levels.get(i).lookup(tokens[i], DEL));
        }

        idSet.remove(null);

        return idSet;
    }

    public boolean update(BigInteger[] values) {
        if (!levels.get(0).isEmpty()) return false;

        levels.get(0).put(values[0], new Value(values[0], values[1], values[2]));
        return true;
    }

    public void simpleRebuild(BigInteger[][] values) {
        int level = firstEmptyLevel();
        for(int i = 0; i < level; i++) {
            levels.set(i, new TreeLevel(i));
        }
        TreeLevel newLevel = new TreeLevel(level);
        for(int i = 0; i < newLevel.size(); i++) {
            newLevel.put(values[i][0], new Value(values[i][0], values[i][1], values[i][2]));
        }
    }

    public int firstEmptyLevel() {
        for(int i = 0; i < levels.size(); i++) {
            if(levels.get(i).isEmpty()) return i;
        }
        return levels.size();
    }

    public ArrayList<Collection<Value>> levelsToRebuild() {
        ArrayList<Collection<Value>> rows = new ArrayList<>();
        for(int i = 0; i < levels.size(); i++) {
            if(levels.get(i).isEmpty()) return rows;
            rows.add(levels.get(i).values());
        }
        return rows;
    }

    public String toString() {
        return levels.toString();
    }
}
