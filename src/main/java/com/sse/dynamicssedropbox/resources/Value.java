package com.sse.dynamicssedropbox.resources;

import java.math.BigInteger;

public class Value {

    BigInteger hkey, c1, c2;

    public Value(BigInteger hkey, BigInteger c1, BigInteger c2) {
        this.hkey = hkey;
        this.c1 = c1;
        this.c2 = c2;
    }

    public String toString() {
        String hkey, c1, c2;
        if(this.hkey == null) {
            hkey=c1=c2="null";
        }
        else {
            hkey = this.hkey.toString();
            c1 = this.c1.toString();
            c2 = this.c2.toString();
        }
        return String.format("(%s, %s, %s}", hkey, c1, c2);
    }
}
