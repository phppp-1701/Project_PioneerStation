package gui;

import javafx.fxml.FXML;

import javafx.scene.control.MenuBar;

import javafx.scene.control.MenuItem;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;

import javafx.scene.control.Menu;

public class Home_GUI_Controller {
	@FXML
	private AnchorPane pnHome;
	@FXML
	private MenuBar menuBar;
	@FXML
	private Menu menuTroGiup;
	@FXML
	private MenuItem itemThongTinPhanMem;
	@FXML
	private MenuItem itemHuongDanSuDung;
	@FXML
	private Label lblQuanLyBanVe;
	@FXML
	private ImageView imgQuanLyBanVe;
	@FXML
	private Label lblTrangChu;
	
	public void initialize() {
	    // Thêm style class cho AnchorPane (nếu cần)
	    pnHome.getStyleClass().add("anchor-pane");
	    
	    // Thêm style cho label
	    lblTrangChu.getStyleClass().add("hover-label");
	    
	    // Debug: In ra thông tin label
	    System.out.println("Label size: " + lblTrangChu.getWidth() + "x" + lblTrangChu.getHeight());
	    System.out.println("Label insets: " + lblTrangChu.getInsets());
	}
	@FXML
	private ImageView imgTrangChu;
	@FXML
	private Label lblQuanLyLichSu;
	@FXML
	private ImageView imgQuanLyLichSu;
	@FXML
	private Label lblQuanLyVe;
	@FXML
	private ImageView imgQuanLyVe;
	@FXML
	private Label lblQuanLyHoaDon;
	@FXML
	private ImageView imgQuanLyHoaDon;
	@FXML
	private Label lblQuanLyKhachHang;
	@FXML
	private ImageView imgQuanLyKhachHang;
	@FXML
	private Label lblQuanLyNhanVien;
	@FXML
	private ImageView imgQuanLyNhanVien;
	@FXML
	private Label lblQuanLyChuyenTau;
	@FXML
	private ImageView imgQuanLyChuyenTau;
	@FXML
	private Label lblThongKe;
	@FXML
	private ImageView imgThongKe;
	@FXML
	private Label lblQuanLyTaiKhoan;
	@FXML
	private ImageView imgQuanLyTaiKhoan;
	@FXML
	private Label lblDangXuat;
	@FXML
	private ImageView imgDangXuat;

}
