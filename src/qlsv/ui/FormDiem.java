package qlsv.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import qlsv.db.DBConnection;

public class FormDiem extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FormDiem() {

        setTitle("Quan Ly Diem");

        setSize(1100, 500);

        setLocationRelativeTo(null);

        String[] cot = {
            "MaSV",
            "MaMon",
            "HocKy",
            "NamHoc",
            "DiemCC",
            "DiemGK",
            "DiemCK",
            "DiemTongKet"
        };

        model = new DefaultTableModel(cot, 0);

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadDiem();
    }

    private void loadDiem() {

        model.setRowCount(0);

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM Diem";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{

                    rs.getString("MaSV"),
                    rs.getString("MaMon"),
                    rs.getInt("HocKy"),
                    rs.getString("NamHoc"),
                    rs.getDouble("DiemCC"),
                    rs.getDouble("DiemGK"),
                    rs.getDouble("DiemCK"),
                    rs.getDouble("DiemTongKet")

                });

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}