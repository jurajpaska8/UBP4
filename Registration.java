//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha1: Do suboru s heslami ulozit aj sal.                           //
// Uloha2: Pouzit vytvorenu funkciu na hashovanie a ulozit heslo        //
//         v zahashovanom tvare.                                        //
//////////////////////////////////////////////////////////////////////////

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Registration {
    protected static Database.MyResult registracia(String meno, String heslo) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (Database.exist("jdbc:sqlite:usersDB", meno)){
            System.out.println("Meno je uz zabrate.");
            return new Database.MyResult(false, "Meno je uz zabrate.");
        }
        else if(!Security.isPwdSecure(heslo))
        {
            return new Database.MyResult(false, "Slabe heslo.");
        }
        else {
            long salt = Security.getSalt();
            String hash = Security.hash(heslo, salt);
            Database.add("jdbc:sqlite:usersDB", meno, hash, Long.toString(salt));
        }
        return new Database.MyResult(true, "");
    }
    
}
