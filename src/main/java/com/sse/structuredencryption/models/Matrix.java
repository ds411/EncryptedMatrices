package com.sse.structuredencryption.models;

import java.util.Base64;

public class Matrix {

    private static Base64.Decoder dec = Base64.getDecoder();
    private static Base64.Encoder enc = Base64.getEncoder();
    private String[] ciphertext;
    private String[][] matrix;

    public Matrix() {

    }

    public String[] getCiphertext() {
        return ciphertext;
    }

    public Matrix setCiphertext(String[] ciphertext) {
        this.ciphertext = ciphertext;
        return this;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public Matrix setMatrix(String[][] matrix) {
        this.matrix = matrix;
        return this;
    }

    public String lookup(Token token) {
        String c = matrix[token.getA()][token.getB()];
        byte[] sBytes = dec.decode(token.getS());
        byte[] cBytes = dec.decode(c);
        for(int i = 0; i < sBytes.length; i++) {
            cBytes[i] ^= sBytes[i];
        }
        return enc.encodeToString(cBytes);
    }

    public String get(int index) {
        return ciphertext[index];
    }

}
