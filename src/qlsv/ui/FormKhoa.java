package qlsv.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import qlsv.db.DBConnection;

public class FormKhoa extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtMaKhoa;
    private JTextField txtTenKhoa;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;

    public FormKhoa() {

        setTitle("Quản lý khoa");

        setSize(700, 400);

        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        txtMaKhoa = new JTextField(10);
        txtTenKhoa = new JTextField(20);

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        panel.add(new JLabel("Mã khoa"));
        panel.add(txtMaKhoa);

        panel.add(new JLabel("Tên khoa"));
        panel.add(txtTenKhoa);

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);

        add(panel, BorderLayout.NORTH);

        String[] cot = {
            "MaKhoa",
            "TenKhoa"
        };

        model = new DefaultTableModel(cot, 0);

        table = new JTable(model);

        table.getSelectionModel().addListSelectionListener(e -> {

            int row = table.getSelectedRow();

            if (row >= 0) {

                txtMaKhoa.setText(
                        model.getValueAt(row, 0).toString());

                txtTenKhoa.setText(
                        model.getValueAt(row, 1).toString());
            }

        });

        add(new JScrollPane(table),
                BorderLayout.CENTER);

        loadKhoa();

        btnThem.addActionListener(e -> themKhoa());
        btnSua.addActionListener(e -> suaKhoa());
        btnXoa.addActionListener(e -> xoaKhoa());
    }

    private void loadKhoa() {

        model.setRowCount(0);

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM Khoa";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[] {

                    rs.getString("MaKhoa"),
                    rs.getString("TenKhoa")

                });

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    private void themKhoa() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "INSERT INTO Khoa(MaKhoa, TenKhoa) VALUES (?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, txtMaKhoa.getText());
            ps.setString(2, txtTenKhoa.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Thêm thành công");

            loadKhoa();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    private void suaKhoa() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "UPDATE Khoa SET TenKhoa=? WHERE MaKhoa=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, txtTenKhoa.getText());
            ps.setString(2, txtMaKhoa.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Sửa thành công");

            loadKhoa();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    private void xoaKhoa() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "DELETE FROM Khoa WHERE MaKhoa=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, txtMaKhoa.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Xóa thành công");

            loadKhoa();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }
}