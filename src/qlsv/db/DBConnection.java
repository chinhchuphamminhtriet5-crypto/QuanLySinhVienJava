package qlsv.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
        "jdbc:sqlserver://localhost:1433;"
      + "databaseName=QuanLySinhVien;"
      + "encrypt=false;"
      + "trustServerCertificate=true";

    private static final String USER =
            "QuanLySinhVien";

    private static final String PASSWORD =
            "123456";

    public static Connection getConnection() {

        try {

            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

        } catch (Exception e) {

            System.out.println(e.getMessage());

            return null;
        }
    }
}