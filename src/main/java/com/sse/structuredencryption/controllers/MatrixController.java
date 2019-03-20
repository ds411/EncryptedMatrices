package com.sse.structuredencryption.controllers;

import com.sse.structuredencryption.models.Matrix;
import com.sse.structuredencryption.models.Token;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class MatrixController {

    private Matrix matrix;

    //Uploads an encrypted matrix of pointers and ciphertext
    @PostMapping(value = "/upload", consumes = "application/json")
    public void upload(Matrix matrix) {
        this.matrix = matrix;
    }

    //Returns the tuple (j, v) corresponding to token
    @PostMapping(value = "/lookup", consumes = "application/json", produces = "application/json")
    public String lookup(Token token) {
        return matrix.lookup(token);
    }

    //Gets the ciphertext at index j
    @PostMapping(value = "/get", consumes = "application/json", produces = "plain/text")
    public String get(int j) {
        return matrix.get(j);
    }
}
