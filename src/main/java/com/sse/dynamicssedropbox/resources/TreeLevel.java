package com.sse.dynamicssedropbox.resources;

import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class TreeLevel extends HashMap<String, Value> {

    final int MAX;

    public TreeLevel(int level) {
        super((int)Math.pow(2, level), 1);
        MAX = (int)Math.pow(2, level);
    }

    //Puts a value in the hashmap if the hashmap size would not exceed max size
    public void putV(String key, Value value) {
        if(MAX >= size()) return;
        put(key, value);
    }

    //Looks up document id using token
    public String lookup(String token, short op) {
        try {
            Base64.Decoder dec = Base64.getDecoder();
            Base64.Encoder enc = Base64.getEncoder();

            Mac hmac = Mac.getInstance("HmacSHA256");
            System.out.println(token);
            SecretKeySpec keySpec = new SecretKeySpec(dec.decode(token), "HmacSHA256");
            hmac.init(keySpec);
            byte[] hkeyBytes, c1Bytes, valuec1Bytes;
            String hkeyJson, hkey, c1Json;
            Value value;
            for (int i = 0; i < MAX; i++) {
                hkeyJson = String.format("[0,%d,%d]", op, i);
                hkeyBytes = hmac.doFinal(hkeyJson.getBytes());
                hkey = enc.encodeToString(hkeyBytes);
                value = get(hkey);
                if(value != null) {
                    c1Json = String.format("[1,%d,%d]", op, i);
                    c1Bytes = hmac.doFinal(c1Json.getBytes());
                    valuec1Bytes = dec.decode(value.c1);
                    byte[] arr = new byte[32];
                    for(int j = 0; j < 32; j++) {
                        arr[j] = (byte)(valuec1Bytes[j] ^ c1Bytes[j]);
                    }
                    return new Base32().encodeAsString(arr);
                }
            }
        }
        catch(NoSuchAlgorithmException nsae) {}
        catch(InvalidKeyException ike) {}
        return null;
    }
}
