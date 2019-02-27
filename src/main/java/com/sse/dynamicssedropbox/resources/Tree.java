package com.sse.dynamicssedropbox.resources;

import java.math.BigInteger;
import java.util.*;

public class Tree {

    private ArrayList<TreeLevel> levels;

    private final short ADD = 0;
    private final short DEL = 1;

    public Tree() {
        levels = new ArrayList<>();
        levels.add(new TreeLevel(0));
    }

    public Set<String> search(HashMap<Integer, String> tokens) {
        assert tokens.size() == levels.size();

        HashSet<String> idSet = new HashSet<>();

        for(int i = 0; i < tokens.size(); i++) {
            idSet.add(levels.get(i).lookup(tokens.get(i), ADD));
        }
        for(int i = 0; i < tokens.size(); i++) {
            idSet.remove(levels.get(i).lookup(tokens.get(i), DEL));
        }

        idSet.remove(null);

        return idSet;
    }

    public boolean update(String[] values) {
        System.out.println("Updating...");
        if (!levels.get(0).isEmpty()) return false;

        levels.get(0).put(values[0], new Value(values[0], values[1], values[2]));
        return true;
    }

    public void simpleRebuild(Value[] values) {
        System.out.println("Rebuilding...");
        int level = firstEmptyLevel();
        for(int i = 0; i < level; i++) {
            levels.set(i, new TreeLevel(i));
        }
        TreeLevel newLevel = new TreeLevel(level);
        System.out.println(newLevel.MAX);
        for(int i = 0; i < newLevel.MAX; i++) {
            newLevel.put(values[i].hkey, values[i]);
        }
        if(level == levels.size()) levels.add(newLevel);
        else levels.set(level, newLevel);
    }

    public int firstEmptyLevel() {
        for(int i = 0; i < levels.size(); i++) {
            if(levels.get(i).isEmpty()) return i;
        }
        return levels.size();
    }

    public Value[] levelsToRebuild() {
        ArrayList<Value> rows = new ArrayList<>();
        for(int i = 0; i < levels.size(); i++) {
            if(levels.get(i).isEmpty()) break;
            rows.addAll(levels.get(i).values());
        }
        Value[] arr = new Value[rows.size()];
        return rows.toArray(arr);
    }

    public String toString() {
        return levels.toString();
    }
}
