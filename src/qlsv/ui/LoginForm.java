package qlsv.ui;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import qlsv.db.DBConnection;

public class LoginForm extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;

    public LoginForm() {

        setTitle("Đăng nhập");

        setSize(400, 200);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(3, 2));

        add(new JLabel("Tên đăng nhập"));

        txtUser = new JTextField();

        add(txtUser);

        add(new JLabel("Mật khẩu"));

        txtPass = new JPasswordField();

        add(txtPass);

        btnLogin = new JButton("Đăng nhập");

        add(new JLabel());

        add(btnLogin);

        btnLogin.addActionListener(e -> dangNhap());
    }

    private void dangNhap() {

        try {

            Connection con =
                    DBConnection.getConnection();

            if (con == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Không kết nối được SQL Server!"
                );

                return;
            }

            String sql =
                    "SELECT * FROM TaiKhoan "
                  + "WHERE TenDangNhap=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    txtUser.getText()
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Đăng nhập thành công\n"
                        + rs.getString("HoTen")
                );

                new MainForm().setVisible(true);

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Sai tài khoản"
                );

            }

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );

        }
    }
}