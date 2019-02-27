package com.sse.dynamicssedropbox.controllers;

import com.sse.dynamicssedropbox.resources.Tree;
import com.sse.dynamicssedropbox.resources.TreeLevel;
import com.sse.dynamicssedropbox.resources.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("api")
public class TreeController {

    @Resource
    private Tree tree;

    @Autowired
    private HttpServletRequest request;

    @PostMapping(value = "/search", consumes = "application/json", produces = "application/json")
    public Set<String> search(@RequestBody HashMap<Integer, String> tokens) {
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

    @PostMapping(value = "/upload")
    public boolean upload(@RequestParam(value = "file") MultipartFile file) {
        if(!file.isEmpty()) {
            String uploadDir = "/uploads/";
            String dirName = request.getServletContext().getRealPath(uploadDir); //saves to temp directory, can specify permanent location instead
            File dir = new File(dirName);
            if(!dir.exists()) dir.mkdir();
            String filename = file.getOriginalFilename();
            System.out.println(filename);
            try {
                file.transferTo(new File(dir + "/" + filename));
                return true;
            }
            catch (IOException ex) {

            }
        }
        return false;
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
