package qlsv.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import qlsv.db.DBConnection;

public class FormSinhVien extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtMaSV;
    private JTextField txtHoTen;
    private JSpinner spnNgaySinh; 
    private JTextField txtEmail;
    private JTextField txtSoDienThoai;
    private JTextField txtTimKiem; // Ô nhập từ khóa tìm kiếm

    private JComboBox<String> cboGioiTinh;
    private JComboBox<String> cboTinh;
    private JComboBox<String> cboMaLop;
    private JComboBox<String> cboTrangThai;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnTimKiem; // Nút thực hiện tìm kiếm
    private JButton btnLamMoi;  // Nút làm mới danh sách

    public FormSinhVien() {
        setTitle("HỆ THỐNG QUẢN LÝ SINH VIÊN");
        setSize(1250, 750);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Khởi tạo các thành phần nhập liệu
        txtMaSV = new JTextField(12);
        txtHoTen = new JTextField(12);
        
        spnNgaySinh = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnNgaySinh, "yyyy-MM-dd");
        spnNgaySinh.setEditor(dateEditor);
        spnNgaySinh.setValue(new Date());

        txtEmail = new JTextField(12);
        txtSoDienThoai = new JTextField(12);
        txtTimKiem = new JTextField(15);

        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cboTrangThai = new JComboBox<>(new String[]{"Đang học", "Bảo lưu"});
        
        String[] dsTinh = {
            "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", 
            "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", 
            "Cần Thơ", "Cao Bằng", "Đà Nẵng", "Đắk Lắk", "Đắk Nông", "Điện Biên", 
            "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Nội", 
            "Hà Tĩnh", "Hải Dương", "Hải Phòng", "Hậu Giang", "Hòa Bình", "Hưng Yên", 
            "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", 
            "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", 
            "Phú Thọ", "Phú Yên", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", 
            "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", 
            "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", 
            "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "TP Hồ Chí Minh"
        };
        cboTinh = new JComboBox<>(dsTinh);
        
        cboMaLop = new JComboBox<>();
        loadMaLop();

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTimKiem = new JButton("Tìm kiếm");
        btnLamMoi = new JButton("Làm mới");

        // Bố cục form thông tin (GridLayout 3x3)
        JPanel panelForm = new JPanel(new GridLayout(3, 3, 15, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        panelForm.add(createFieldPanel("Mã SV:", txtMaSV));
        panelForm.add(createFieldPanel("Họ tên:", txtHoTen));
        panelForm.add(createFieldPanel("Ngày sinh:", spnNgaySinh));
        
        panelForm.add(createFieldPanel("Giới tính:", cboGioiTinh));
        panelForm.add(createFieldPanel("Địa chỉ:", cboTinh));
        panelForm.add(createFieldPanel("Email:", txtEmail));
        
        panelForm.add(createFieldPanel("SĐT:", txtSoDienThoai));
        panelForm.add(createFieldPanel("Mã lớp:", cboMaLop));
        panelForm.add(createFieldPanel("Trạng thái:", cboTrangThai));

        // Panel chứa các nút CRUD và thanh tìm kiếm
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelButton.add(btnThem);
        panelButton.add(btnSua);
        panelButton.add(btnXoa);
        
        // Thêm khu vực tìm kiếm trực quan
        panelButton.add(new JLabel(" | Tìm kiếm (Mã/Tên):"));
        panelButton.add(txtTimKiem);
        panelButton.add(btnTimKiem);
        panelButton.add(btnLamMoi);

        JPanel panelNorth = new JPanel(new BorderLayout());
        panelNorth.add(panelForm, BorderLayout.CENTER);
        panelNorth.add(panelButton, BorderLayout.SOUTH);

        add(panelNorth, BorderLayout.NORTH);

        String[] cot = {
            "MaSV", "HoTen", "NgaySinh", "GioiTinh", "DiaChi", "Email", "SoDienThoai", "MaLop", "TrangThai"
        };

        model = new DefaultTableModel(cot, 0);
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Sự kiện click chọn dòng trên bảng để đổ dữ liệu lên form
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtMaSV.setText(model.getValueAt(row, 0) != null ? model.getValueAt(row, 0).toString() : "");
                txtHoTen.setText(model.getValueAt(row, 1) != null ? model.getValueAt(row, 1).toString() : "");
                
                Object ngaySinhObj = model.getValueAt(row, 2);
                if (ngaySinhObj != null) {
                    try {
                        Date date = java.sql.Date.valueOf(ngaySinhObj.toString());
                        spnNgaySinh.setValue(date);
                    } catch (Exception ex) {
                        spnNgaySinh.setValue(new Date());
                    }
                }

                cboGioiTinh.setSelectedItem(model.getValueAt(row, 3));
                cboTinh.setSelectedItem(model.getValueAt(row, 4));
                txtEmail.setText(model.getValueAt(row, 5) != null ? model.getValueAt(row, 5).toString() : "");
                txtSoDienThoai.setText(model.getValueAt(row, 6) != null ? model.getValueAt(row, 6).toString() : "");
                cboMaLop.setSelectedItem(model.getValueAt(row, 7));
                cboTrangThai.setSelectedItem(model.getValueAt(row, 8));
                
                txtMaSV.setEnabled(false);
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadSinhVien();

        // Gán sự kiện cho các nút
        btnThem.addActionListener(e -> themSinhVien());
        btnSua.addActionListener(e -> suaSinhVien());
        btnXoa.addActionListener(e -> xoaSinhVien());
        btnTimKiem.addActionListener(e -> timKiemSinhVien());
        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadSinhVien();
            lamMoiForm();
        });
    }

    private JPanel createFieldPanel(String labelText, JComponent comp) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        JLabel lbl = new JLabel(labelText);
        lbl.setPreferredSize(new Dimension(100, 25));
        p.add(lbl);
        
        if (comp instanceof JTextField || comp instanceof JComboBox || comp instanceof JSpinner) {
            comp.setPreferredSize(new Dimension(200, 25));
        }
        p.add(comp);
        return p;
    }

    private void loadMaLop() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT MaLop FROM Lop";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            cboMaLop.removeAllItems();
            while (rs.next()) {
                cboMaLop.addItem(rs.getString("MaLop"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSinhVien() {
        model.setRowCount(0);
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM SinhVien";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("MaSV"),
                    rs.getString("HoTen"),
                    rs.getDate("NgaySinh"),
                    rs.getString("GioiTinh"),
                    rs.getString("DiaChi"),
                    rs.getString("Email"),
                    rs.getString("SoDienThoai"),
                    rs.getString("MaLop"),
                    rs.getString("TrangThai")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Chức năng tìm kiếm sinh viên theo Mã SV hoặc Họ tên
    private void timKiemSinhVien() {
        String keyword = txtTimKiem.getText().trim();
        if (keyword.isEmpty()) {
            loadSinhVien();
            return;
        }

        model.setRowCount(0);
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM SinhVien WHERE MaSV LIKE ? OR HoTen LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                model.addRow(new Object[]{
                    rs.getString("MaSV"),
                    rs.getString("HoTen"),
                    rs.getDate("NgaySinh"),
                    rs.getString("GioiTinh"),
                    rs.getString("DiaChi"),
                    rs.getString("Email"),
                    rs.getString("SoDienThoai"),
                    rs.getString("MaLop"),
                    rs.getString("TrangThai")
                });
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên phù hợp!");
                loadSinhVien();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getNgaySinhFormatted() {
        try {
            Date selectedDate = (Date) spnNgaySinh.getValue();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(selectedDate);
        } catch (Exception e) {
            return "";
        }
    }

    private void themSinhVien() {
        if (txtMaSV.getText().trim().isEmpty() || txtHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã SV và Họ tên!");
            return;
        }

        if (txtMaSV.getText().trim().length() > 15) {
            JOptionPane.showMessageDialog(this, "Mã sinh viên tối đa 15 ký tự!");
            return;
        }

        String ngaySinhStr = getNgaySinhFormatted();
        if (ngaySinhStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ!");
            return;
        }

        if (txtEmail.getText().trim().isEmpty() || !txtEmail.getText().trim().contains("@")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!");
            return;
        }

        if (txtSoDienThoai.getText().trim().isEmpty() || !txtSoDienThoai.getText().trim().matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại chỉ được nhập số!");
            return;
        }

        if (cboMaLop.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp!");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, Email, SoDienThoai, MaLop, TrangThai) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtMaSV.getText().trim());
            ps.setString(2, txtHoTen.getText().trim());
            ps.setString(3, ngaySinhStr);
            ps.setString(4, cboGioiTinh.getSelectedItem().toString());
            ps.setString(5, cboTinh.getSelectedItem().toString());
            ps.setString(6, txtEmail.getText().trim());
            ps.setString(7, txtSoDienThoai.getText().trim());
            ps.setString(8, cboMaLop.getSelectedItem().toString());
            ps.setString(9, cboTrangThai.getSelectedItem().toString());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công!");
            loadSinhVien();
            lamMoiForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void suaSinhVien() {
        if (txtMaSV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần sửa!");
            return;
        }

        String ngaySinhStr = getNgaySinhFormatted();
        if (ngaySinhStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ!");
            return;
        }

        if (txtEmail.getText().trim().isEmpty() || !txtEmail.getText().trim().contains("@")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!");
            return;
        }

        if (txtSoDienThoai.getText().trim().isEmpty() || !txtSoDienThoai.getText().trim().matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại chỉ được nhập số!");
            return;
        }

        if (cboMaLop.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp!");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE SinhVien SET HoTen=?, NgaySinh=?, GioiTinh=?, DiaChi=?, Email=?, SoDienThoai=?, MaLop=?, TrangThai=? "
                       + "WHERE MaSV=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtHoTen.getText().trim());
            ps.setString(2, ngaySinhStr);
            ps.setString(3, cboGioiTinh.getSelectedItem().toString());
            ps.setString(4, cboTinh.getSelectedItem().toString());
            ps.setString(5, txtEmail.getText().trim());
            ps.setString(6, txtSoDienThoai.getText().trim());
            ps.setString(7, cboMaLop.getSelectedItem().toString());
            ps.setString(8, cboTrangThai.getSelectedItem().toString());
            ps.setString(9, txtMaSV.getText().trim());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
            loadSinhVien();
            lamMoiForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void xoaSinhVien() {
        if (txtMaSV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa!");
            return;
        }

        int chon = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa sinh viên này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (chon != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM SinhVien WHERE MaSV=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtMaSV.getText().trim());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            loadSinhVien();
            lamMoiForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void lamMoiForm() {
        txtMaSV.setText("");
        txtHoTen.setText("");
        spnNgaySinh.setValue(new Date());
        txtEmail.setText("");
        txtSoDienThoai.setText("");
        cboGioiTinh.setSelectedIndex(0);
        cboTinh.setSelectedIndex(0);
        if (cboMaLop.getItemCount() > 0) {
            cboMaLop.setSelectedIndex(0);
        }
        cboTrangThai.setSelectedIndex(0);
        
        table.clearSelection();
        txtMaSV.setEnabled(true);
        txtMaSV.requestFocus();
    }
}