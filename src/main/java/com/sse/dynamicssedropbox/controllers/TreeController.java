package com.sse.dynamicssedropbox.controllers;

import com.sse.dynamicssedropbox.resources.Tree;
import com.sse.dynamicssedropbox.resources.TreeLevel;
import com.sse.dynamicssedropbox.resources.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("api")
public class TreeController {

    @Resource
    private Tree tree;

    @PostMapping(value = "/search", consumes = "application/json", produces = "application/json")
    public Set<String> search(@RequestBody String[] tokens) {
        return tree.search(tokens);
    }

    @PostMapping(value = "/update")
    public boolean update(@RequestBody String[] values) {
        //if(!tree.update(values)) return tree.levelsToRebuild();
        //return null;
        tree.update(values);
        return true;
    }

    @GetMapping(value = "/needsRebuild")
    public boolean needsRebuild() {
        return tree.firstEmptyLevel() != 0;
    }

    @PostMapping(value = "/rebuild", consumes = "application/json")
    public boolean rebuild(@RequestBody String[][] values) {
        Value[] vals = new Value[values.length];
        for(int i = 0; i < values.length; i++) {
            vals[i] = new Value(values[i][0], values[i][1], values[i][2]);
        }
        tree.simpleRebuild(vals);
        return true;
    }

    @GetMapping(value = "/table", produces = "application/json")
    public Value[] table() {
        return tree.levelsToRebuild();
    }

    @GetMapping(value = "/level")
    public int level() {
        return tree.firstEmptyLevel();
    }

    @GetMapping(value = "/test", produces = "application/json")
    public String test() {
        return tree.toString();
    }

}
