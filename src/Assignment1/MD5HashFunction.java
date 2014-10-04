package Assignment1;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class MD5HashFunction extends HashFunction {
    
    private static final String name = "MD5";
    private String charSet = "";
    private MessageDigest messageDigest = null;
            
    public MD5HashFunction(String charSet) {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            this.charSet = charSet;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("There is no such algorithm");
        }
    }

    @Override
    public BitSet hash(String token) {
        BitSet hash = new BitSet();
        byte[] hashByte = null;
        try {
            hashByte = messageDigest.digest(token.getBytes(charSet));
        } catch (UnsupportedEncodingException e) {
            System.err.println("There is no such charset!!");
        }
        int digestLength = messageDigest.getDigestLength();
        for (int i = 0; i < digestLength; i++) {
            byte curByte = hashByte[i];
            for (int j = 0; j < 8; j++) {
                hash.set(i*8+j, (curByte & 1) == 1);
                curByte >>= 1;
            }
        }
        return hash;
    }

    @Override
    public String getName() {
        return name;
    }

}
