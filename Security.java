//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha1: Vytvorit funkciu na bezpecne generovanie saltu.              //
// Uloha2: Vytvorit funkciu na hashovanie.                              //
// Je vhodne vytvorit aj dalsie pomocne funkcie napr. na porovnavanie   //
// hesla ulozeneho v databaze so zadanym heslom.                        //
//////////////////////////////////////////////////////////////////////////


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
    //vyriesit parametre
    protected static long getSalt() {
        SecureRandom random = new SecureRandom();
        return random.nextLong();
//        long ret = min + ((random.nextLong()) % (max - min + 1));
//        return ret < 0 ? ret + (max - min + 1) : ret;
    }
}

