package gui;

import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Ve;
import entity.VeTam;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.Ve_DAO;
import dao.ChuyenTau_DAO;
import dao.Toa_DAO;
import dao.Ga_DAO;
import dao.TuyenTau_DAO;
import dao.Tau_DAO;
import dao.ChoNgoi_DAO;
import entity.ChuyenTau;
import entity.Toa;
import entity.Ga;
import entity.TuyenTau;
import entity.Tau;
import entity.ChoNgoi;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class HoaDon_GUI_Controller {
    @FXML
    private Label lblMaHoaDon;

    @FXML
    private Label lblNgayTaoHoaDon;

    @FXML
    private Label lblTenKhachHang;

    @FXML
    private Label lblSoDienThoai;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblLoaiThanhVien;

    @FXML
    private TableView<Ve> tbDanhSachVe;

    @FXML
    private TableColumn<Ve, Integer> colSTT;

    @FXML
    private TableColumn<Ve, String> colMaVe;

    @FXML
    private TableColumn<Ve, String> colNgayKhoiHanh;

    @FXML
    private TableColumn<Ve, String> colGioKhoiHanh;

    @FXML
    private TableColumn<Ve, String> colTenDichVu;

    @FXML
    private TableColumn<Ve, String> colLoaiKhachHang;

    @FXML
    private TableColumn<Ve, String> colGiaVe;

    private String maHoaDon;

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
        initializeData();
    }

    private void initializeData() {
        try {
            // Fetch invoice data
            HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
            HoaDon hoaDon = hoaDonDAO.timHoaDonTheoMa(maHoaDon);
            if (hoaDon == null) {
                System.err.println("Không tìm thấy hóa đơn với mã: " + maHoaDon);
                return;
            }
            NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
            NhanVien nhanVien = nhanVien_DAO.timNhanVienTheoMa(hoaDon.getMaNhanVien());
            lblMaNhanVien.setText("Mã nhân viên: "+hoaDon.getMaNhanVien());
            lblTenNhanVien.setText("Tên nhân viên: "+nhanVien.getTenNhanVien());


            // Set invoice details
            lblMaHoaDon.setText("Mã hóa đơn: "+hoaDon.getMaHoaDon());
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            lblNgayTaoHoaDon.setText("Ngày tạo hóa đơn: "+hoaDon.getNgayTaoHoaDon().format(dateFormatter));

            // Fetch customer data
            KhachHang_DAO khachHangDAO = new KhachHang_DAO();
            KhachHang khachHang = hoaDon.getMaKhachHang() != null 
                ? khachHangDAO.timKhachHangTheoMa(hoaDon.getMaKhachHang())
                : null;

            if (khachHang != null) {
                lblTenKhachHang.setText("Họ và tên khách hàng: "+khachHang.getTenKhachHang());
                lblSoDienThoai.setText("Số điện thoại: "+khachHang.getSoDienThoai());
                lblEmail.setText(khachHang.getEmail());
                lblLoaiThanhVien.setText("Loại thành viên: " + 
                	    (khachHang.getLoaiThanhVien() == KhachHang.LoaiThanhVien.khachVangLai 
                	        ? "Khách vãng lai" 
                	        : khachHang.getLoaiThanhVien() == KhachHang.LoaiThanhVien.thanThiet 
                	            ? "Thân thiết" 
                	            : "VIP"));

            } else {
                lblTenKhachHang.setText("Khách vãng lai");
                lblSoDienThoai.setText("N/A");
                lblEmail.setText("N/A");
                lblLoaiThanhVien.setText("Khách vãng lai");
            }

            // Fetch ticket data
            Ve_DAO veDAO = new Ve_DAO();
            List<Ve> danhSachVe = veDAO.timVeTheoMaHoaDon(maHoaDon);
            setupTableView(danhSachVe);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
        }
    }

    private void setupTableView(List<Ve> danhSachVe) throws SQLException {
        // Setup TableView columns
        colSTT.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(tbDanhSachVe.getItems().indexOf(cellData.getValue()) + 1).asObject());
        
        colMaVe.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getMaVe()));
        
        colNgayKhoiHanh.setCellValueFactory(cellData -> {
            String maChoNgoi = cellData.getValue().getMaChoNgoi();
            ChoNgoi_DAO choNgoiDAO = new ChoNgoi_DAO();
            ChuyenTau_DAO chuyenTauDAO = new ChuyenTau_DAO();
            ChoNgoi choNgoi = choNgoiDAO.timChoNgoiTheoMaChoNgoi(maChoNgoi);
			ChuyenTau chuyenTau = chuyenTauDAO.timChuyenTauTheoMaChuyenTau(choNgoi.getMaChuyenTau());
			return new SimpleStringProperty(chuyenTau.getNgayKhoiHanh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });

        colGioKhoiHanh.setCellValueFactory(cellData -> {
            String maChoNgoi = cellData.getValue().getMaChoNgoi();
            ChoNgoi_DAO choNgoiDAO = new ChoNgoi_DAO();
            ChuyenTau_DAO chuyenTauDAO = new ChuyenTau_DAO();
            ChoNgoi choNgoi = choNgoiDAO.timChoNgoiTheoMaChoNgoi(maChoNgoi);
			ChuyenTau chuyenTau = chuyenTauDAO.timChuyenTauTheoMaChuyenTau(choNgoi.getMaChuyenTau());
			return new SimpleStringProperty(chuyenTau.getGioKhoiHanh().format(DateTimeFormatter.ofPattern("HH:mm")));
        });

        colTenDichVu.setCellValueFactory(cellData -> {
            String maChoNgoi = cellData.getValue().getMaChoNgoi();
            ChoNgoi_DAO choNgoiDAO = new ChoNgoi_DAO();
            Toa_DAO toaDAO = new Toa_DAO();
            ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO();
            Tau_DAO tau_DAO = new Tau_DAO();
            try {
                ChoNgoi choNgoi = choNgoiDAO.timChoNgoiTheoMaChoNgoi(maChoNgoi);
                ChuyenTau chuyenTau = chuyenTau_DAO.timChuyenTauTheoMaChuyenTau(choNgoi.getMaChuyenTau());
                Tau tau = tau_DAO.timTauTheoMa(chuyenTau.getMaTau());
                Toa toa = toaDAO.getToaByMaToa(choNgoi.getMaToa());
                return new SimpleStringProperty("Tàu: "+tau.getTenTau()+" - Toa: "+toa.getTenToa() + " - Loại toa: " + loaiToaToString(toa.getLoaiToa()) + " - Chỗ:" + choNgoi.getTenChoNgoi());
            } catch (SQLException e) {
                e.printStackTrace();
                return new SimpleStringProperty("N/A");
            }
        });

        colLoaiKhachHang.setCellValueFactory(cellData -> 
            new SimpleStringProperty(loaiKhachHangToString(cellData.getValue().getLoaiKhachHang())));

        colGiaVe.setCellValueFactory(cellData -> {
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            return new SimpleStringProperty(currencyFormatter.format(cellData.getValue().getGiaVe()));
        });

        // Populate TableView
        tbDanhSachVe.setItems(FXCollections.observableArrayList(danhSachVe));
    }

    private String loaiToaToString(Toa.LoaiToa loaiToa) {
        if (loaiToa == null) return "Không xác định";
        switch (loaiToa) {
            case giuongNamDieuHoa: return "Giường nằm điều hòa";
            case ngoiMemDieuHoa: return "Ngồi mềm điều hòa";
            case gheCungDieuHoa: return "Ghế cứng điều hòa";
            default: return loaiToa.toString();
        }
    }

    private String loaiKhachHangToString(VeTam.LoaiKhachHang loaiKhachHang) {
        if (loaiKhachHang == null) return "Không xác định";
        switch (loaiKhachHang) {
            case nguoiLon: return "Người lớn";
            case treEm: return "Trẻ em";
            case sinhVien: return "Sinh viên";
            case nguoiCaoTuoi: return "Người cao tuổi";
            default: return loaiKhachHang.toString();
        }
    }
    
    @FXML
    private Label lblMaNhanVien;
    @FXML
    private Label lblTenNhanVien;
    
    @FXML
    private Button btnQuayVe;
	@FXML
	private void quayVeQuanLyBanVe() {
	    try {
	        // Lấy cửa sổ hiện tại (HoaDon_GUI)
	        Stage currentStage = (Stage) btnQuayVe.getScene().getWindow();
	        
	        // Đóng tất cả các cửa sổ QuanLyBanVe_GUI hiện có
	        javafx.stage.Window.getWindows().forEach(window -> {
	            if (window instanceof Stage) {
	                Stage stage = (Stage) window;
	                // Kiểm tra nếu stage là QuanLyBanVe_GUI bằng tiêu đề hoặc đặc điểm
	                if (stage.getTitle() != null && stage.getTitle().contains("Quản lý bán vé")) {
	                    System.out.println("Đóng QuanLyBanVe_GUI cũ: " + stage.getTitle());
	                    stage.close();
	                }
	            }
	        });
	
	        // Lấy mã nhân viên từ hóa đơn
	        if (maHoaDon != null) {
	            HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
	            HoaDon hoaDon = hoaDonDAO.timHoaDonTheoMa(maHoaDon);
	            if (hoaDon != null) {
	                String maNhanVien = hoaDon.getMaNhanVien();
	                if (maNhanVien != null && !maNhanVien.isEmpty()) {
	                    // Mở QuanLyBanVe_GUI mới
	                    System.out.println("Mở QuanLyBanVe_GUI mới với maNhanVien: " + maNhanVien);
	                    new QuanLyBanVe_GUI(new Stage(), maNhanVien);
	                } else {
	                    System.err.println("Mã nhân viên không hợp lệ cho hóa đơn: " + maHoaDon);
	                    showWarningAlert("Mã nhân viên không hợp lệ!", "image/canhBao.png");
	                    return;
	                }
	            } else {
	                System.err.println("Không tìm thấy hóa đơn với mã: " + maHoaDon);
	                showWarningAlert("Không tìm thấy hóa đơn!", "image/canhBao.png");
	                return;
	            }
	        } else {
	            System.err.println("Mã hóa đơn không được cung cấp!");
	            showWarningAlert("Mã hóa đơn không hợp lệ!", "image/canhBao.png");
	            return;
	        }
	
	        // Đóng cửa sổ HoaDon_GUI hiện tại
	        System.out.println("Đóng HoaDon_GUI với maHoaDon: " + maHoaDon);
	        currentStage.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        showWarningAlert("Đã xảy ra lỗi không mong muốn!", "image/canhBao.png");
	    }
	}    
	private void showWarningAlert(String message, String icon) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        setAlertIcon(alert, icon);
        alert.showAndWait();
    }
    
    private void setAlertIcon(Alert alert, String icon) {
        File file = new File(icon);
        if (file.exists()) {
            try {
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(file.toURI().toString()));
            } catch (Exception e) {
                // Ignore icon errors
            }
        }
    }
}