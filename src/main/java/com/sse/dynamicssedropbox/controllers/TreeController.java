package com.sse.dynamicssedropbox.controllers;

import com.sse.dynamicssedropbox.resources.Tree;
import com.sse.dynamicssedropbox.resources.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("api")
public class TreeController {

    @Resource
    private Tree tree;

    @PostMapping(value = "/search", consumes = "application/json")
    public Set<BigInteger> search(@RequestBody BigInteger[] tokens) {
        return tree.search(tokens);
    }

    @PostMapping(value = "/update", consumes = "application/json")
    public HashMap<Integer, Value[]> update(@RequestBody BigInteger[] values) {
        if(!tree.update(values)) return tree.levelsToRebuild();
        return null;
    }

    @GetMapping(value = "/test", produces = "application/json")
    public String test() {
        return tree.toString();
    }

}
