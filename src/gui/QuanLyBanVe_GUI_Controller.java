package gui;

import java.io.File;

import dao.NhanVien_DAO;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class QuanLyBanVe_GUI_Controller {
    private String maNhanVien;

    @FXML
    public void initialize() {
        // Khởi tạo giao diện, có thể sử dụng maNhanVien nếu cần
    	
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
        updateNhanVienInfo(); // Cập nhật thông tin sau khi gán
    }
    
    @FXML 
    private Label lblMaNhanVien;
    
    @FXML
    private Label lblTenNhanVien;
    
    @FXML
    private Label lblChucVu;
    
    @FXML 
    private ImageView imgAnhNhanVien;
    
    // Phương thức để cập nhật thông tin nhân viên sau khi setMaNhanVien
    public void updateNhanVienInfo() {
        if (maNhanVien != null && !maNhanVien.isEmpty()) {
            NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
            NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(maNhanVien);
            if (nv != null) {
                lblMaNhanVien.setText(nv.getMaNhanVien());
                lblTenNhanVien.setText(nv.getTenNhanVien());
                if (nv.getChucVu().equals(ChucVu.banVe)) {
                    lblChucVu.setText("Bán vé");
                } else {
                    lblChucVu.setText("Quản lý");
                }
                File imageFile = new File(nv.getLinkAnh());
                Image image = new Image(imageFile.toURI().toString());
                imgAnhNhanVien.setImage(image);
            } else {
                lblMaNhanVien.setText("Mã nhân viên: Không tìm thấy");
                lblTenNhanVien.setText("Tên nhân viên: Không tìm thấy");
                lblChucVu.setText("Chức vụ: Không xác định");
            }
        }
    }
    
    
}