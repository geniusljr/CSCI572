package SimHash;

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
        hash = BitSet.valueOf(hashByte);
        return hash;
    }
    
    public static void main(String[] args) {
        MD5HashFunction h = new MD5HashFunction("UTF-8");
        h.hash("asdf");
    }

    @Override
    public String getName() {
        return name;
    }

}
