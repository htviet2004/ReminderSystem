package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\HOan\\Desktop\\PROJECT\\remindersystem.db"; // sửa
 
    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            System.out.println("Kết nối thành công tới SQLite!");
            return conn;
        } catch (Exception e) {
            System.out.println("Không thể kết nối tới DB: " + DB_URL);
            e.printStackTrace();
            return null;
        }
    }
}
