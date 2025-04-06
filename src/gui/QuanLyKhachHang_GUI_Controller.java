package gui;

import java.util.List;

import dao.KhachHang_DAO;
import entity.KhachHang;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TableView;


import javafx.scene.control.TableColumn;

public class QuanLyKhachHang_GUI_Controller {
	@FXML
    private Button btnXuatDanhSach;
	
	@FXML
	private TableView<KhachHang> tbDanhSachKhachHang;
	
	@FXML
	private TableColumn<KhachHang, String> colStt; 
	
	@FXML 
	private TableColumn<KhachHang, String> colMaKhachHang;
	
	@FXML 
	private TableColumn<KhachHang, String> colTen;
	
	@FXML
	private TableColumn<KhachHang, String> colSoDienThoai;
	
	@FXML 
	private TableColumn<KhachHang, String> colEmail;
	
	@FXML 
	private TableColumn<KhachHang, String> colLoaiKhachHang;
	
	@FXML
	private void btnXuatDanhSachClicked() {
	    try {
	        List<KhachHang> danhSachKhachHang = new KhachHang_DAO().getAllKhachHang();
	        if (danhSachKhachHang.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Thông báo");
	            alert.setHeaderText(null);
	            alert.setContentText("Danh sách khách hàng trống!");
	            alert.showAndWait();
	            return;
	        }
	        
	        // Xóa dữ liệu cũ trong table (nếu có)
	        tbDanhSachKhachHang.getItems().clear();
	        
	        // Thêm dữ liệu mới vào table
	        tbDanhSachKhachHang.getItems().addAll(danhSachKhachHang);
	        
	        // Thiết lập giá trị cho các cột
	        colStt.setCellValueFactory(cellData -> 
	            new SimpleStringProperty(String.valueOf(tbDanhSachKhachHang.getItems().indexOf(cellData.getValue()) + 1)));
	        colMaKhachHang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaKhachHang()));
	        colTen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenKhachHang()));
	        colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
	        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
	        colLoaiKhachHang.setCellValueFactory(cellData -> 
	            new SimpleStringProperty(cellData.getValue().getLoaiThanhVien().toString()));
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Lỗi");
	        alert.setHeaderText(null);
	        alert.setContentText("Không thể tải danh sách khách hàng!");
	        alert.showAndWait();
	    }
	}
}
