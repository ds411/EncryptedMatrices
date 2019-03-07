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

    //Searches for file IDs by token list
    public Set<String> search(HashMap<Integer, String> tokens) {
        assert tokens.size() == levels.size();

        HashSet<String> idSet = new HashSet<>();

        for(int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i) != null) idSet.add(levels.get(i).lookup(tokens.get(i), ADD));
        }
        for(int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i) != null) idSet.remove(levels.get(i).lookup(tokens.get(i), DEL));
        }

        idSet.remove(null);

        return idSet;
    }

    //Updates tree with new values
    public boolean update(String[] values) {
        if (!levels.get(0).isEmpty()) return false;

        levels.get(0).put(values[0], new Value(values[0], values[1], values[2]));
        return true;
    }

    //Rebuilds tree from list of values
    public void simpleRebuild(Value[] values) {
        int level = firstEmptyLevel();
        for(int i = 0; i < level; i++) {
            levels.set(i, new TreeLevel(i));
        }
        TreeLevel newLevel = new TreeLevel(level);
        for(int i = 0; i < newLevel.MAX; i++) {
            newLevel.put(values[i].hkey, values[i]);
        }
        if(level == levels.size()) levels.add(newLevel);
        else levels.set(level, newLevel);
    }

    //Finds first empty level in tree
    public int firstEmptyLevel() {
        for(int i = 0; i < levels.size(); i++) {
            if(levels.get(i).isEmpty()) return i;
        }
        return levels.size();
    }

    //Returns levels before first empty level
    public Value[] levelsToRebuild() {
        ArrayList<Value> rows = new ArrayList<>();
        for(int i = 0; i < levels.size(); i++) {
            if(levels.get(i).isEmpty()) break;
            rows.addAll(levels.get(i).values());
        }
        Value[] arr = new Value[rows.size()];
        System.out.println(Arrays.toString(rows.toArray()));
        return rows.toArray(arr);
    }

    public Value[] levelsToRegenerate() {
        HashSet<Value> values = new HashSet<>();
        for(int i = 0; i < levels.size(); i++) {
            values.addAll(levels.get(i).values());
        }
        values.remove(null);
        Value[] arr = new Value[values.size()];
        return values.toArray(arr);
    }

    public ArrayList<TreeLevel> getLevels() {
        return levels;
    }
}
