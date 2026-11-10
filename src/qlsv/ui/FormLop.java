package qlsv.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import qlsv.db.DBConnection;

public class FormLop extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtMaLop;
    private JTextField txtTenLop;
    private JTextField txtMaKhoa;
    private JTextField txtNienKhoa;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;

    public FormLop() {

        setTitle("Quan Ly Lop");

        setSize(900, 500);

        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        txtMaLop = new JTextField(8);
        txtTenLop = new JTextField(12);
        txtMaKhoa = new JTextField(8);
        txtNienKhoa = new JTextField(10);

        btnThem = new JButton("Them");
        btnSua = new JButton("Sua");
        btnXoa = new JButton("Xoa");

        panel.add(new JLabel("Ma Lop"));
        panel.add(txtMaLop);

        panel.add(new JLabel("Ten Lop"));
        panel.add(txtTenLop);

        panel.add(new JLabel("Ma Khoa"));
        panel.add(txtMaKhoa);

        panel.add(new JLabel("Nien Khoa"));
        panel.add(txtNienKhoa);

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);

        add(panel, BorderLayout.NORTH);

        String[] cot = {
            "MaLop",
            "TenLop",
            "MaKhoa",
            "NienKhoa"
        };

        model = new DefaultTableModel(cot,0);

        table = new JTable(model);

        table.getSelectionModel()
             .addListSelectionListener(e -> {

            int row = table.getSelectedRow();

            if(row >= 0){

                txtMaLop.setText(
                    model.getValueAt(row,0).toString());

                txtTenLop.setText(
                    model.getValueAt(row,1).toString());

                txtMaKhoa.setText(
                    model.getValueAt(row,2).toString());

                txtNienKhoa.setText(
                    model.getValueAt(row,3).toString());
            }

        });

        add(new JScrollPane(table),
                BorderLayout.CENTER);

        loadLop();

        btnThem.addActionListener(e -> themLop());

        btnSua.addActionListener(e -> suaLop());

        btnXoa.addActionListener(e -> xoaLop());
    }

    private void loadLop() {

        model.setRowCount(0);

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM Lop";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                model.addRow(new Object[]{

                    rs.getString("MaLop"),
                    rs.getString("TenLop"),
                    rs.getString("MaKhoa"),
                    rs.getString("NienKhoa")

                });

            }

        } catch(Exception e) {

            e.printStackTrace();

        }
    }

    private void themLop() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "INSERT INTO Lop VALUES(?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, txtMaLop.getText());
            ps.setString(2, txtTenLop.getText());
            ps.setString(3, txtMaKhoa.getText());
            ps.setString(4, txtNienKhoa.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Them thanh cong");

            loadLop();

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    private void suaLop() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "UPDATE Lop SET TenLop=?, MaKhoa=?, NienKhoa=? WHERE MaLop=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, txtTenLop.getText());
            ps.setString(2, txtMaKhoa.getText());
            ps.setString(3, txtNienKhoa.getText());
            ps.setString(4, txtMaLop.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Sua thanh cong");

            loadLop();

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    private void xoaLop() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "DELETE FROM Lop WHERE MaLop=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, txtMaLop.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Xoa thanh cong");

            loadLop();

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }
}
