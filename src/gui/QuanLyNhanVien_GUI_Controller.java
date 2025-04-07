package gui;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import dao.NhanVien_DAO;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.NhanVien.GioiTinh;
import entity.NhanVien.TrangThaiNhanVien;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class QuanLyNhanVien_GUI_Controller {
    @FXML
    private Button btnXuatDanhSach;
    
    @FXML
    private TableView<NhanVien> tbDanhSachNhanVien;
    
    @FXML
    private TableColumn<NhanVien, String> colStt;
    
    @FXML 
    private TableColumn<NhanVien, String> colMaNV;
    
    @FXML 
    private TableColumn<NhanVien, String> colTenNV;
    
    @FXML
    private TableColumn<NhanVien, String> colNgaySinh;
    
    @FXML 
    private TableColumn<NhanVien, String> colGioiTinh;
    
    @FXML 
    private TableColumn<NhanVien, String> colCCCD;
    
    @FXML 
    private TableColumn<NhanVien, String> colChucVu;
    
    @FXML 
    private TableColumn<NhanVien, String> colTrangThai;
    
    @FXML
    private void btnXuatDanhSachClicked() {
        try {
            List<NhanVien> danhSachNhanVien = new NhanVien_DAO().getAllNhanVien();
            if (danhSachNhanVien.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Danh sách nhân viên trống!");
                alert.showAndWait();
                return;
            }
            
            // Xóa dữ liệu cũ trong table (nếu có)
            tbDanhSachNhanVien.getItems().clear();
            
            // Thêm dữ liệu mới vào table
            tbDanhSachNhanVien.getItems().addAll(danhSachNhanVien);
            
            // Thiết lập giá trị cho các cột
            colStt.setCellValueFactory(cellData -> 
                new SimpleStringProperty(String.valueOf(tbDanhSachNhanVien.getItems().indexOf(cellData.getValue()) + 1)));
            colMaNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaNhanVien()));
            colTenNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenNhanVien()));
            colNgaySinh.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNgaySinh().toString()));
            colGioiTinh.setCellValueFactory(cellData -> {
                GioiTinh gioiTinh = cellData.getValue().getGioiTinh();
                String displayValue = gioiTinh == GioiTinh.nam ? "Nam" : "Nữ";
                return new SimpleStringProperty(displayValue);
            });
            colCCCD.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCccd_HoChieu()));
            colChucVu.setCellValueFactory(cellData -> {
                ChucVu chucVu = cellData.getValue().getChucVu();
                String displayValue = "";
                if (chucVu == ChucVu.quanLy) {
                    displayValue = "Quản Lý";
                } else if (chucVu == ChucVu.banVe) {
                    displayValue = "Bán Vé";
                }
                return new SimpleStringProperty(displayValue);
            });
            colTrangThai.setCellValueFactory(cellData -> {
                TrangThaiNhanVien trangThaiNhanVien = cellData.getValue().getTrangThaiNhanVien();
                String displayValue = "";
                if (trangThaiNhanVien == TrangThaiNhanVien.hoatDong) {
                    displayValue = "Hoạt Động";
                } else if (trangThaiNhanVien == TrangThaiNhanVien.voHieuHoa) {
                    displayValue = "Vô Hiệu Hóa";
                    

                }
                return new SimpleStringProperty(displayValue);
            });
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Không thể tải danh sách nhân viên!");
        }
    }
    @FXML
    private Button btnTim;

    @FXML
    private TextField txtTimTenNhanVien;

    @FXML
    private TextField txtTimMaNhanVien;

    @FXML
    private void btnTimClicked() {
        // Lấy giá trị từ các TextField
        String tenNhanVien = txtTimTenNhanVien.getText().trim();
        String maNhanVien = txtTimMaNhanVien.getText().trim();
        
        // Kiểm tra nếu cả hai trường đều trống
        if (tenNhanVien.isEmpty() && maNhanVien.isEmpty()) {
            showWarningAlert("Vui lòng nhập tên nhân viên hoặc mã nhân viên để tìm kiếm!");
            return;
        }
        
        try {
            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
            List<NhanVien> ketQuaTimKiem;
            
            // Kiểm tra các trường nhập vào và thực hiện tìm kiếm theo yêu cầu
            if (!maNhanVien.isEmpty() && !tenNhanVien.isEmpty()) {
                ketQuaTimKiem = nhanVienDAO.timNhanVienTheoMaVaTen(maNhanVien, tenNhanVien);
            } else if (!maNhanVien.isEmpty()) {
                ketQuaTimKiem = nhanVienDAO.timNhanVienTheoMa(maNhanVien);
            } else {
                ketQuaTimKiem = nhanVienDAO.timNhanVienTheoTen(tenNhanVien);
            }
            
            // Kiểm tra kết quả tìm kiếm
            if (ketQuaTimKiem == null || ketQuaTimKiem.isEmpty()) {
                showInformationAlert("Không tìm thấy nhân viên phù hợp!");
                tbDanhSachNhanVien.getSelectionModel().selectAll();
                // Di chuyển chuột đến dòng đầu tiên (hoặc dòng nào bạn muốn)
                tbDanhSachNhanVien.scrollTo(0); 
         
                return;
            }
            // Hiển thị kết quả trên TableView
            tbDanhSachNhanVien.getItems().clear();
            tbDanhSachNhanVien.setItems(FXCollections.observableArrayList(ketQuaTimKiem));
            
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        setAlertIcon(alert);
        alert.showAndWait();
    }

    private void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        setAlertIcon(alert);
        alert.showAndWait();
    }

    private void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        setAlertIcon(alert);
        alert.showAndWait();
    }

    private void setAlertIcon(Alert alert) {
        File file = new File("image/canhBao.png");
        if (file.exists()) {
            try {
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(file.toURI().toString()));
            } catch (Exception e) {
                System.err.println("Lỗi khi tải ảnh: " + e.getMessage());
            }
        } else {
            System.err.println("Không tìm thấy file tại: " + file.getAbsolutePath());
        }
    }


    
    @FXML
    private Button btnThem;
    @FXML
    private Button btnXoa;
    @FXML
    private Button btnCapNhat;
    @FXML
    private TextField txtMaNV;
    @FXML
    private TextField txtTenNV;
    @FXML
    private DatePicker datePickerNgaySinh;
    @FXML
    private ComboBox<GioiTinh> cboGioiTinh;  // ComboBox thay cho TextField
    @FXML
    private TextField txtCCCD;
    @FXML
    private ComboBox<ChucVu> cboChucVu;  // ComboBox thay cho TextField
    @FXML
    private ComboBox<TrangThaiNhanVien> cboTrangThaiNhanVien;  // ComboBox cho trạng thái nhân viên

    @FXML
    private void initialize() {
        // Gán giá trị cho ComboBox giới tính
        cboGioiTinh.setItems(FXCollections.observableArrayList(GioiTinh.values()));

        // Gán giá trị cho ComboBox chức vụ
        cboChucVu.setItems(FXCollections.observableArrayList(ChucVu.values()));

        // Gán giá trị cho ComboBox trạng thái nhân viên
        cboTrangThaiNhanVien.setItems(FXCollections.observableArrayList(TrangThaiNhanVien.values()));

        // Thiết lập sự kiện khi chọn 1 hàng trong bảng
        tbDanhSachNhanVien.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    hienThiThongTinNhanVien(newSelection);
                }
            });
    }


    // Hiển thị thông tin nhân viên lên form
    private void hienThiThongTinNhanVien(NhanVien nv) {
        txtMaNV.setText(nv.getMaNhanVien());
        txtTenNV.setText(nv.getTenNhanVien());
        datePickerNgaySinh.setValue(nv.getNgaySinh());
        cboGioiTinh.setValue(nv.getGioiTinh());
        txtCCCD.setText(nv.getCccd_HoChieu());
        cboChucVu.setValue(nv.getChucVu());
        cboTrangThaiNhanVien.setValue(nv.getTrangThaiNhanVien());
    }

 // Lấy thông tin từ form
    private NhanVien layThongTinNhanVienTuForm() throws Exception {
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(txtMaNV.getText());
        nv.setTenNhanVien(txtTenNV.getText());
        nv.setNgaySinh(datePickerNgaySinh.getValue());
        nv.setGioiTinh(cboGioiTinh.getValue());  // Lấy giá trị từ ComboBox
        nv.setCccd_HoChieu(txtCCCD.getText());
        nv.setChucVu(cboChucVu.getValue());  // Lấy giá trị từ ComboBox
        nv.setTrangThaiNhanVien(cboTrangThaiNhanVien.getValue());
        return nv;
    }
    
    // Xử lý thêm nhân viên
    @FXML
    private void btnThemClicked() {
        try {
            NhanVien nv = layThongTinNhanVienTuForm();
            if (!kiemTraHopLe(nv)) return;

            NhanVien_DAO dao = new NhanVien_DAO();
            if (dao.tonTaiNhanVien(nv.getMaNhanVien(), nv.getCccd_HoChieu())) {
                showErrorAlert("Mã nhân viên hoặc CCCD đã tồn tại!");
                return;
            }

            if (dao.themNhanVien(nv)) {
                showInformationAlert("Thêm thành công!");
                btnXuatDanhSachClicked();
                xoaForm();
            } else {
                showErrorAlert("Thêm thất bại!");
            }
        } catch (SQLException e) {
            showErrorAlert("Lỗi database: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            showErrorAlert("Lỗi hệ thống: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnXoaClicked() {
        NhanVien nv = tbDanhSachNhanVien.getSelectionModel().getSelectedItem();
        if (nv == null) {
            showWarningAlert("Vui lòng chọn nhân viên cần xóa!");
            return;
        }

        // Hộp thoại xác nhận YES/NO
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc muốn xóa nhân viên \"" + nv.getTenNhanVien() + "\"?");

        ButtonType buttonYes = new ButtonType("Có", ButtonData.YES);
        ButtonType buttonNo = new ButtonType("Không", ButtonData.NO);

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonYes) {
            try {
                NhanVien_DAO dao = new NhanVien_DAO();
                if (dao.xoaNhanVien(nv.getMaNhanVien())) {
                    showInformationAlert("Xóa nhân viên thành công!");
                    btnXuatDanhSachClicked();
                    xoaForm();
                } else {
                    showErrorAlert("Xóa nhân viên thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorAlert("Lỗi khi xóa nhân viên: " + e.getMessage());
            }
        }
    }


    // Xử lý cập nhật nhân viên
    @FXML
    private void btnCapNhatClicked() {
        NhanVien nvSelected = tbDanhSachNhanVien.getSelectionModel().getSelectedItem();
        if (nvSelected == null) {
            showWarningAlert("Vui lòng chọn nhân viên cần cập nhật!");
            return;
        }

        try {
            // Lấy thông tin nhân viên từ form
            NhanVien nv = layThongTinNhanVienTuForm();
            nv.setMaNhanVien(nvSelected.getMaNhanVien()); // Giữ nguyên mã NV

            // Kiểm tra tính hợp lệ của thông tin
            if (!kiemTraHopLe(nv)) {
                return;
            }

            // Cập nhật thông tin từ form (trực tiếp sử dụng enum)
            nv.setGioiTinh(cboGioiTinh.getValue()); // Dùng giá trị trực tiếp từ ComboBox
            nv.setChucVu(cboChucVu.getValue()); // Dùng giá trị trực tiếp từ ComboBox
            nv.setTrangThaiNhanVien(cboTrangThaiNhanVien.getValue()); // Dùng giá trị trực tiếp từ ComboBox

            // Cập nhật nhân viên vào cơ sở dữ liệu
            NhanVien_DAO dao = new NhanVien_DAO();
            if (dao.capNhatNhanVien(nv)) {
                showInformationAlert("Cập nhật nhân viên thành công!");

                // Cập nhật trực tiếp vào TableView
                // Tìm nhân viên trong List và thay thế
                for (int i = 0; i < tbDanhSachNhanVien.getItems().size(); i++) {
                    NhanVien current = tbDanhSachNhanVien.getItems().get(i);
                    if (current.getMaNhanVien().equals(nv.getMaNhanVien())) {
                        // Cập nhật nhân viên đã thay đổi trong danh sách
                        tbDanhSachNhanVien.getItems().set(i, nv);
                        break;
                    }
                }
            } else {
                showErrorAlert("Cập nhật nhân viên thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Lỗi khi cập nhật nhân viên: " + e.getMessage());
        }
    }
    // Kiểm tra dữ liệu hợp lệ
    private boolean kiemTraHopLe(NhanVien nv) {
        if (nv.getMaNhanVien().isEmpty() || nv.getTenNhanVien().isEmpty() || 
            nv.getCccd_HoChieu().isEmpty()) {
            showWarningAlert("Vui lòng nhập đầy đủ thông tin!");
            return false;
        }
        
        if (!nv.getCccd_HoChieu().matches("\\d{12}")) {
            showWarningAlert("CCCD phải có 12 chữ số!");
            return false;
        }
        
        return true;
    }

    // Xóa form nhập liệu
    private void xoaForm() {
        txtMaNV.clear();
        txtTenNV.clear();
        datePickerNgaySinh.setValue(null);
        cboGioiTinh.setValue(null);
        txtCCCD.clear();
        cboChucVu.setValue(null);
        cboTrangThaiNhanVien.setValue(null);
    }
}