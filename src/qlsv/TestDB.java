package qlsv;

import java.sql.Connection;
import qlsv.db.DBConnection;

public class TestDB {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con != null) {

            System.out.println("KET NOI THANH CONG SQL SERVER");

        } else {

            System.out.println("KET NOI THAT BAI");

        }
    }
}