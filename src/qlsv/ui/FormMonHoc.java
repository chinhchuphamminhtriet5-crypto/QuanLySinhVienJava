package qlsv.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import qlsv.db.DBConnection;

public class FormMonHoc extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtMaMon;
    private JTextField txtTenMon;
    private JTextField txtSoTinChi;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;

    public FormMonHoc() {

        setTitle("Quan Ly Mon Hoc");

        setSize(800, 500);

        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        txtMaMon = new JTextField(10);
        txtTenMon = new JTextField(15);
        txtSoTinChi = new JTextField(5);

        btnThem = new JButton("Them");
        btnSua = new JButton("Sua");
        btnXoa = new JButton("Xoa");

        panel.add(new JLabel("Ma Mon"));
        panel.add(txtMaMon);

        panel.add(new JLabel("Ten Mon"));
        panel.add(txtTenMon);

        panel.add(new JLabel("So Tin Chi"));
        panel.add(txtSoTinChi);

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);

        add(panel, BorderLayout.NORTH);

        String[] cot = {
            "MaMon",
            "TenMon",
            "SoTinChi"
        };

        model = new DefaultTableModel(cot,0);

        table = new JTable(model);

        table.getSelectionModel()
             .addListSelectionListener(e -> {

                 int row = table.getSelectedRow();

                 if(row >= 0){

                     txtMaMon.setText(
                         model.getValueAt(row,0).toString());

                     txtTenMon.setText(
                         model.getValueAt(row,1).toString());

                     txtSoTinChi.setText(
                         model.getValueAt(row,2).toString());
                 }

             });

        add(new JScrollPane(table),
                BorderLayout.CENTER);

        loadMonHoc();

        btnThem.addActionListener(e -> themMonHoc());
        btnSua.addActionListener(e -> suaMonHoc());
        btnXoa.addActionListener(e -> xoaMonHoc());
    }

    private void loadMonHoc() {

        model.setRowCount(0);

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM MonHoc";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                model.addRow(new Object[]{

                    rs.getString("MaMon"),
                    rs.getString("TenMon"),
                    rs.getInt("SoTinChi")

                });

            }

        } catch(Exception e) {

            e.printStackTrace();

        }
    }

    private void themMonHoc() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "INSERT INTO MonHoc VALUES(?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, txtMaMon.getText());
            ps.setString(2, txtTenMon.getText());
            ps.setInt(3,
                    Integer.parseInt(
                            txtSoTinChi.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Them thanh cong");

            loadMonHoc();

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    private void suaMonHoc() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "UPDATE MonHoc "
                  + "SET TenMon=?, SoTinChi=? "
                  + "WHERE MaMon=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, txtTenMon.getText());

            ps.setInt(
                    2,
                    Integer.parseInt(
                            txtSoTinChi.getText()));

            ps.setString(3, txtMaMon.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Sua thanh cong");

            loadMonHoc();

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    private void xoaMonHoc() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "DELETE FROM MonHoc WHERE MaMon=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    txtMaMon.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Xoa thanh cong");

            loadMonHoc();

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }
}
