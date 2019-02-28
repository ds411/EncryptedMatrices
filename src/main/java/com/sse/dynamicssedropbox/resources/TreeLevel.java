package com.sse.dynamicssedropbox.resources;

import org.apache.commons.collections4.map.FixedSizeMap;
import org.yaml.snakeyaml.util.ArrayUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
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

    public void putV(String key, Value value) {
        if(MAX >= size()) return;
        put(key, value);
    }

    public String lookup(String token, short op) {
        try {
            Base64.Decoder dec = Base64.getDecoder();
            Base64.Encoder enc = Base64.getEncoder();
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(token), "HmacSHA256");
            hmac.init(keySpec);
            byte[] hkeyBytes, c1Bytes, valuec1Bytes;
            String hkeyJson, hkey, c1Json, c1;
            Value value;
            for (int i = 0; i < MAX; i++) {
                hkeyJson = String.format("[0,%d,%d]", op, i);
                hkeyBytes = hmac.doFinal(hkeyJson.getBytes());
                hkey = Base64.getEncoder().encodeToString(hkeyBytes);
                System.out.println(hkeyJson + " => " + hkey);
                value = get(hkey);
                if(value != null) {
                    c1Json = String.format("[0,%d,%d]", op, i);
                    c1Bytes = hmac.doFinal(c1Json.getBytes(StandardCharsets.UTF_8));
                    valuec1Bytes = dec.decode(value.c1);
                    for(int j = 0; j < c1Bytes.length; j++) {
                        valuec1Bytes[j] ^= c1Bytes[j];
                    }
                    return enc.encodeToString(valuec1Bytes);
                }
            }
        }
        catch(NoSuchAlgorithmException nsae) {}
        catch(InvalidKeyException ike) {}
        return null;
    }
}
