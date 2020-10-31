//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha1: Vytvorit funkciu na bezpecne generovanie saltu.              //
// Uloha2: Vytvorit funkciu na hashovanie.                              //
// Je vhodne vytvorit aj dalsie pomocne funkcie napr. na porovnavanie   //
// hesla ulozeneho v databaze so zadanym heslom.                        //
//////////////////////////////////////////////////////////////////////////


import org.passay.dictionary.Dictionary;
import org.passay.dictionary.DictionaryBuilder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Security {
    private static final String algorithm = "PBKDF2WithHmacSHA1";
    private static final int iterationCount = 65536;
    private static final int keyLen = 128;

    protected static String hash(String password, long salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        /*
         *   Pred samotnym hashovanim si najskor musite ulozit instanciu hashovacieho algoritmu.
         *   Hash sa uklada ako bitovy retazec, takze ho nasledne treba skonvertovat na String (napr. cez BigInteger);
         */
        //salt
        ByteBuffer buffer = ByteBuffer
                .allocate(Long.BYTES)
                .putLong(salt);
        //key
        KeySpec spec = new PBEKeySpec(password.toCharArray(), buffer.array(), iterationCount, keyLen);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
        //hash
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        //log and return
        System.out.println("hash generated = " + enc.encodeToString(hash));
        return enc.encodeToString(hash);
    }

    //TODO podla bezpcnostnych standardov je dobre mat salt aspon taky velky - v bitoch - ako hash
    protected static long getSalt() {
        SecureRandom random = new SecureRandom();
        return random.nextLong();
//        long ret = min + ((random.nextLong()) % (max - min + 1));
//        return ret < 0 ? ret + (max - min + 1) : ret;
    }

    protected static boolean isPwdStrong(String pwd)
    {
        boolean hasDigit = false;
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;

        if (pwd.length() < 8) {
            System.err.println("PWD has to be at least 8 char long.");
            return false;
        }

        for (char c : pwd.toCharArray()) {
            if (!hasDigit && Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isLetter(c)) {
                if (!hasUpperCase && Character.isUpperCase(c)) {
                    hasUpperCase = true;
                } else if (!hasLowerCase && Character.isLowerCase(c)) {
                    hasLowerCase = true;
                }
            }
            if (hasDigit && hasLowerCase && hasUpperCase) {
                System.out.println("Password contains at least 8 characters and each of Digit, Upper Case, Lower Case");
                return true;
            }
        }
        System.err.println("Password has to contain digit, lower case and upper case.");
        return false;
    }

    protected static boolean isInDict(String pwd)
    {
        DictionaryBuilder db = new DictionaryBuilder();
        db.addFile("./10k-most-common.txt");
        Dictionary dic = db.build();
        boolean res = dic.search(pwd);
        System.out.println("PWD " + (res? "is " : "is not ") + "in Most common password dic.");
        return res;
    }

    protected static boolean isPwdSecure(String pwd)
    {
        return isPwdStrong(pwd) && !isInDict(pwd);
    }

    public static void main(String[] args) {
        boolean res1 = isPwdSecure("abcdeffhnjsdfifh");
        boolean res2 = isPwdSecure("1234567892");
        boolean res3 = isPwdSecure("AAFFFFFFFFAAEEEEE");
        boolean res4 = isPwdSecure("Abcdefgh1");
        boolean res5 = isPwdSecure("Mwq6qlzo");
    }
}

