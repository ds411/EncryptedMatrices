package com.sse.dynamicssedropbox.resources;

import org.apache.commons.collections4.map.FixedSizeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
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
        /*try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(token.toByteArray(), "HmacSHA256");
            hmac.init(keySpec);
            BigInteger hkey;
            for (int i = 0; i < values.length; i++) {
                hkey = new BigInteger(hmac.doFinal(new BigInteger(String.format("0%d%s", op, Integer.toBinaryString(i)), 2).toByteArray()));
                if(get(i) != null && get(i).hkey.equals(hkey)) {
                    BigInteger c1 = new BigInteger(String.format("1%d%s", op, Integer.toBinaryString(i)), 2);
                    return c1.xor(values[i].c1);
                }
            }
        }
        catch(NoSuchAlgorithmException nsae) {}
        catch(InvalidKeyException ike) {}*/
        return null;
    }
}
