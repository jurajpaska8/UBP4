import java.sql.*;

public class DatabaseAPI {

    protected static void insertUser(String name, String hash, String salt, String path) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(path);
        String sql = "INSERT INTO USERS (NAME,HASH,SALT) " +
                "VALUES ('" + name + "','" + hash + "','" + salt + "');";

        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
        conn.close();
    }

    protected static void printUsers(String path) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(path);
        String sql = "SELECT * FROM USERS;";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while ( rs.next() ) {
            String name = rs.getString("name");
            String hash = rs.getString("hash");
            String salt = rs.getString("salt");

            System.out.println( "NAME = " + name );
            System.out.println( "HASH = " + hash );
            System.out.println( "SALT = " + salt );
            System.out.println();
        }
        stmt.close();
        conn.close();
    }

    protected static String returnRow(String name, String path) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(path);
        String sql = "SELECT * FROM USERS WHERE NAME='" + name + "';";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        String row = rs.getString("name") + ":" + rs.getString("hash") + ":" + rs.getString("salt");
        rs.close();
        stmt.close();
        conn.close();
        return row;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //test databazy
        Connection conn = DriverManager.getConnection("jdbc:sqlite:usersDB");

        String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
                + ");";

        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
        conn.close();

        //insertUser("feroDB", "1", "1", "jdbc:sqlite:usersDB");
        printUsers("jdbc:sqlite:usersDB");

        System.out.println(returnRow("feri", "jdbc:sqlite:usersDB"));
    }
}
