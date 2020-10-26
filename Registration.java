//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha1: Do suboru s heslami ulozit aj sal.                           //
// Uloha2: Pouzit vytvorenu funkciu na hashovanie a ulozit heslo        //
//         v zahashovanom tvare.                                        //
//////////////////////////////////////////////////////////////////////////

import java.security.NoSuchAlgorithmException;

public class Registration {
    protected static Database.MyResult registracia(String meno, String heslo) throws NoSuchAlgorithmException, Exception{
        if (Database.exist("hesla.txt", meno)){
            System.out.println("Meno je uz zabrate.");
            return new Database.MyResult(false, "Meno je uz zabrate.");
        }
        else {
            long salt = Security.getSalt();
            String hash = Security.hash(heslo, salt);
            Database.add("hesla.txt", meno + ":" + hash + ":" + salt);
        }
        return new Database.MyResult(true, "");
    }
    
}
