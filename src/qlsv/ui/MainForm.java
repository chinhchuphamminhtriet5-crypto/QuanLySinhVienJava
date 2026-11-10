package qlsv.ui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

public class MainForm extends JFrame {

    public MainForm() {

        setTitle("QUAN LY SINH VIEN");

        setSize(900, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        JMenu thoat = new JMenu("Dang xuat");
        JMenu sinhVien = new JMenu("Sinh vien");
        JMenu lop = new JMenu("Lop");
        JMenu khoa = new JMenu("Khoa");
        JMenu monHoc = new JMenu("Mon hoc");
        JMenu diem = new JMenu("Diem");

        // Dang xuat
        thoat.addMouseListener(
            new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(
                        java.awt.event.MouseEvent e) {

                    int chon = JOptionPane.showConfirmDialog(
                            null,
                            "Ban co muon dang xuat khong?",
                            "Xac nhan",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (chon == JOptionPane.YES_OPTION) {

                        new LoginForm().setVisible(true);

                        dispose();
                    }
                }
            });

        // Sinh vien
        sinhVien.addMouseListener(
            new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(
                        java.awt.event.MouseEvent e) {

                    new FormSinhVien().setVisible(true);

                }
            });

        // Lop
        lop.addMouseListener(
            new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(
                        java.awt.event.MouseEvent e) {

                    new FormLop().setVisible(true);

                }
            });

        // Khoa
        khoa.addMouseListener(
            new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(
                        java.awt.event.MouseEvent e) {

                    new FormKhoa().setVisible(true);

                }
            });

        // Mon hoc
        monHoc.addMouseListener(
            new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(
                        java.awt.event.MouseEvent e) {

                    new FormMonHoc().setVisible(true);

                }
            });

        // Diem
        diem.addMouseListener(
            new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(
                        java.awt.event.MouseEvent e) {

                    new FormDiem().setVisible(true);

                }
            });

        menuBar.add(thoat);
        menuBar.add(sinhVien);
        menuBar.add(lop);
        menuBar.add(khoa);
        menuBar.add(monHoc);
        menuBar.add(diem);

        setJMenuBar(menuBar);
    }
}
