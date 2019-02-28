package com.sse.dynamicssedropbox.controllers;

import com.sse.dynamicssedropbox.resources.Tree;
import com.sse.dynamicssedropbox.resources.TreeLevel;
import com.sse.dynamicssedropbox.resources.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("api")
public class TreeController {

    @Resource
    private Tree tree; //Tree

    @Autowired
    private HttpServletRequest request; //Http request

    //Searches the tree by a dictionary of tokens
    @PostMapping(value = "/search", consumes = "application/json", produces = "application/json")
    public Set<String> search(@RequestBody HashMap<Integer, String> tokens) {
        return tree.search(tokens);
    }

    //Inserts a value into the tree
    @PostMapping(value = "/update")
    public boolean update(@RequestBody String[] values) {
        //if(!tree.update(values)) return tree.levelsToRebuild();
        //return null;
        tree.update(values);
        return true;
    }

    //Returns whether tree must be rebuilt on new update
    @GetMapping(value = "/needsRebuild")
    public boolean needsRebuild() {
        return tree.firstEmptyLevel() != 0;
    }

    //Rebuilds tree using a list of values
    @PostMapping(value = "/rebuild", consumes = "application/json")
    public boolean rebuild(@RequestBody String[][] values) {
        Value[] vals = new Value[values.length];
        for(int i = 0; i < values.length; i++) {
            vals[i] = new Value(values[i][0], values[i][1], values[i][2]);
        }
        tree.simpleRebuild(vals);
        return true;
    }

    //Uploads and saves a file
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

    //Downloads a file
    @GetMapping(value = "/download/{filename}")
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable String filename) {
        String uploadDir = "/uploads/";
        String dirName = request.getServletContext().getRealPath(uploadDir);
        Path filepath = Paths.get(dirName, filename);
        if(Files.exists(filepath)) {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=" + filename);
            try {
                Files.copy(filepath, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch(IOException ex) {}
        }
    }

    //Returns the tree as JSON
    @GetMapping(value = "/table", produces = "application/json")
    public Value[] table() {
        return tree.levelsToRebuild();
    }

    //Returns the first empty level
    @GetMapping(value = "/level")
    public int level() {
        return tree.firstEmptyLevel();
    }

    //Returns the tree as JSON
    @GetMapping(value = "/test", produces = "application/json")
    public ArrayList<TreeLevel> test() {
        return tree.getLevels();
    }

}
