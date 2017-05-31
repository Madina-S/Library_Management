import org.apache.commons.dbutils.DbUtils;

import javax.swing.*;
import java.sql.*;

public class DBManager {
    private static Connection connection = null;
    private static String DBName = "library_management.db";

    private DBManager(){}

    private static boolean connect(){
        if(connection != null)
            return true;

        try {
            String url = "jdbc:sqlite:" + DBName;
            connection = DriverManager.getConnection(url);

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
                    "`edition` INTEGER DEFAULT 0);";
            execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS `bookCopy` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "`bookID` INTEGER," +
                    "`borrowerID` INTEGER);";
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

    public static boolean delete(String query){
        if(!connect()){
            JOptionPane.showMessageDialog(null, "There was error with database connection");
            return false;
        }

        Statement stmt = null;
        int res = 0;
        try {
            stmt = connection.createStatement();
            res = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(stmt);
            return res != 0;
        }
    }

    public static Result insertWithID(String query){
        if(!connect()){
            JOptionPane.showMessageDialog(null, "There was error with database connection");
            return null;
        }

        Result result = null;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            ResultSet resultSet = statement.getGeneratedKeys();
            result = new Result();
            result.resultSet = resultSet;
            result.statement = statement;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static boolean insert(String query){
        if(!connect()){
            JOptionPane.showMessageDialog(null, "There was error with database connection");
            return false;
        }

        int res = -1;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            res = statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(statement);
            return res != -1;
        }
    }

    public static boolean edit(String query){
        if(!connect()){
            JOptionPane.showMessageDialog(null, "There was error with database connection");
            return false;
        }

        int res = -1;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            res = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(stmt);
            return res != -1;
        }
    }
}

