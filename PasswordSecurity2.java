import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Base64;


public class PasswordSecurity2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
//        GUI okno = new GUI();
//        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        okno.setVisible(true);
//        okno.setResizable(false);
//        okno.setLocationRelativeTo(null);

        long salt1 =  Security.getSalt();
        long salt2 =  Security.getSalt();
        long salt3 =  Security.getSalt();
        System.out.println(salt1 + " " + salt2 + " " + salt3);

        String hash1 = Security.hash("heslo", 0);
        String hash2 = Security.hash("heslo", 0);
        String hash3 = Security.hash("heslo", 1);
        System.out.println(hash1 + " " + hash2 + " " + hash3);

        String originalInput = "test input";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());


        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);

        //Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306",
                "app", "app");   // For MySQL only
        // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

    }

}
