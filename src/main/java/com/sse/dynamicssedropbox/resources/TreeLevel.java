package com.sse.dynamicssedropbox.resources;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class TreeLevel {

    Value[] values;

    public TreeLevel(int level) {
        values = new Value[(int)Math.pow(2, level)];
    }

    public BigInteger lookup(BigInteger token, short op) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec keySpec = new SecretKeySpec(token.toByteArray(), "HmacSHA512");
            hmac.init(keySpec);
            BigInteger hkey;
            for (int i = 0; i < values.length; i++) {
                hkey = new BigInteger(hmac.doFinal(new BigInteger(String.format("0%d%s", op, Integer.toBinaryString(i)), 2).toByteArray()));
                if(values[i].hkey.equals(hkey)) {
                    BigInteger c1 = new BigInteger(String.format("1%d%s", op, Integer.toBinaryString(i)), 2);
                    return c1.xor(values[i].c1);
                }
            }
        }
        catch(NoSuchAlgorithmException nsae) {}
        catch(InvalidKeyException ike) {}
        return null;
    }

}
