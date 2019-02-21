package com.sse.dynamicssedropbox.resources;

import java.math.BigInteger;

public class Value {

    public String hkey, c1, c2;

    public Value(String hkey, String c1, String c2) {
        this.hkey = hkey;
        this.c1 = c1;
        this.c2 = c2;
    }

    public String toString() {
        return String.format("(%s, %s, %s)", hkey, c1, c2);
    }
}
