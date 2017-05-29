import org.apache.commons.dbutils.DbUtils;

import javax.swing.*;
import java.sql.*;

public class DBManager {
    private static Connection connection = null;
    private static String DBName = "library_management.db";

    private DBManager(){}

    public static void main(String[] args){
    }

    private static boolean connect(){
        if(connection != null)
            return true;

        try {
            String url = "jdbc:sqlite:" + DBName;
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

            String sql = "CREATE TABLE IF NOT EXISTS `user` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "`password` TEXT NOT NULL," +
                    "`name` TEXT," +
                    "`surname` TEXT," +
                    "`phoneNumber` TEXT);";
            execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS `borrower` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT);";
            execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS `librarian` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT);";
            execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS `book` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "`title` TEXT NOT NULL," +
                    "`author` TEXT NOT NULL," +
                    "`ISBN` TEXT NOT NULL," +
                    "`edition` INTEGER DEFAULT 0," +
                    "`quantity` INTEGER DEFAULT 0);";
            execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    private static void execute(String sql){
        Statement stmt = null;

        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void closeConnection(){
        if(connection != null)
            DbUtils.closeQuietly(connection);
    }

    public static User isExist(int ID, String tableName){
        if(!connect()){
            JOptionPane.showMessageDialog(null, "There was error with database connection");
            return null;
        }

        User user = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        Statement s = null;
        ResultSet rs = null;

        try {
            stmt = connection.createStatement();
            String query = String.format("SELECT * FROM `%s` WHERE id = %d", tableName, ID);
            resultSet = stmt.executeQuery(query);
            if(resultSet.next()){
                query = String.format("SELECT * FROM `user` WHERE id = %d", ID);
                s = connection.createStatement();
                rs = s.executeQuery(query);
                if(rs.next()){
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String phoneNumber = rs.getString("phoneNumber");
                    user = new User(ID, password, name, surname, phoneNumber);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(s);
            return user;
        }
    }

    public static User isExist(int ID){
        return isExist(ID, "librarian");
    }

    public static Result getData(String query){
        if(!connect()){
            JOptionPane.showMessageDialog(null, "There was error with database connection");
            return null;
        }

        Result result = null;
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            result = new Result();
            result.statement = stmt;
            result.resultSet = resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}

