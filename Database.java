/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;

public class Database {
    
    final static class MyResult {
        private final boolean first;
        private final String second;
        
        public MyResult(boolean first, String second) {
            this.first = first;
            this.second = second;
        }
        public boolean getFirst() {
            return first;
        }
        public String getSecond() {
            return second;
        }
    }
    
    protected static MyResult add(String dbPath, String name, String hash, String salt)
    {
        try
        {
            if (exist(dbPath, name))
            {
                return new MyResult(false, "Meno uz existuje");
            }
            DatabaseAPI.insertUser(name, hash, salt, dbPath);
            return new MyResult(true, "");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return new MyResult(false, "Chyba pri vkladani do DB. Duplikat, alebo zle data");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return new MyResult(false, "");
        }
    }
    
    protected static MyResult find(String dbPath, String name){
        try
        {
            String riadok = DatabaseAPI.returnRow(name, dbPath);
            return new MyResult(true, riadok);
        }
        catch (SQLException e)
        {
            System.out.println("Zaznam nenajdeny.");
            return new MyResult(false, "Chyba pri vybere z databazy. Zle meno alebo cesta k db.");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return new MyResult(false, "");
        }
    }
    
    protected static boolean exist(String dbPath, String name)
    {
        return find(dbPath, name).getFirst();
    }
    
}
