package gui;

import entity.KhachHang;
import entity.KhachHang.LoaiThanhVien;
import entity.VeTam;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;
import dao.KhachHang_DAO;

public class ThanhToan_GUI_Controller {
    private String maNhanVien;
    private List<VeTam> danhSachVeXacNhan;

    @FXML
    private TextField txtTimTenKhachHang;

    @FXML
    private TextField txtTimSoDienThoai;

    @FXML
    private Button btnTimKhachHang;

    @FXML
    private TableView<KhachHang> tbDanhSachKhachHang;

    @FXML
    private TableColumn<KhachHang, Integer> colSoThuTu;

    @FXML
    private TableColumn<KhachHang, String> colMaKhachHang;

    @FXML
    private TableColumn<KhachHang, String> colCCCDHoChieu;

    @FXML
    private TableColumn<KhachHang, String> colTenKhachHang;

    @FXML
    private TableColumn<KhachHang, String> colSoDienThoai;

    @FXML
    private TableColumn<KhachHang, String> colEmail;

    @FXML
    private TableColumn<KhachHang, String> colLoaiThanhVien;

    @FXML
    private TextField txtMaKhachHang;

    @FXML
    private TextField txtTenKhachHang;

    @FXML
    private TextField txtSoDienThoai;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCCCD_HoChieu;

    @FXML
    private ComboBox<LoaiThanhVien> cboLoaiKhachHang;

    @FXML
    private CheckBox ckcKhachVangLai;

    @FXML
    private Button btnLamRong;

    @FXML
    private Button btnThemKhachHang;

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public void setDanhSachVeXacNhan(List<VeTam> danhSachVeXacNhan) {
        this.danhSachVeXacNhan = danhSachVeXacNhan;
    }

    public void initializeData() {
        txtMaKhachHang.setEditable(false);
        // Initialize TableView columns
        colSoThuTu.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                tbDanhSachKhachHang.getItems().indexOf(cellData.getValue()) + 1).asObject());
        colMaKhachHang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaKhachHang()));
        colCCCDHoChieu.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCCCD_HoChieu()));
        colTenKhachHang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenKhachHang()));
        colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colLoaiThanhVien.setCellValueFactory(cellData -> {
            LoaiThanhVien loai = cellData.getValue().getLoaiThanhVien();
            String displayValue = loai == LoaiThanhVien.thanThiet ? "Thân thiết" :
                                 loai == LoaiThanhVien.vip ? "VIP" : "Khách vãng lai";
            return new SimpleStringProperty(displayValue);
        });

        // Initialize ComboBox based on CheckBox state
        updateCboLoaiKhachHangItems();

        // Setup CheckBox listener to update ComboBox items
        ckcKhachVangLai.selectedProperty().addListener((obs, oldValue, newValue) -> {
            updateCboLoaiKhachHangItems();
            // Set default value based on CheckBox state
            if (newValue) {
                cboLoaiKhachHang.setValue(LoaiThanhVien.khachVangLai);
            } else {
                cboLoaiKhachHang.setValue(LoaiThanhVien.thanThiet);
            }
        });

        // Setup listener to disable CheckBox when txtMaKhachHang is not empty
        txtMaKhachHang.textProperty().addListener((obs, oldValue, newValue) -> {
            ckcKhachVangLai.setDisable(!newValue.trim().isEmpty());
        });

        // Setup table selection listener
        setupTableSelectionListener();

        // Set StringConverter for ComboBox to display friendly names
        cboLoaiKhachHang.setConverter(new javafx.util.StringConverter<LoaiThanhVien>() {
            @Override
            public String toString(LoaiThanhVien loai) {
                if (loai == null) {
                    return "";
                }
                return loai == LoaiThanhVien.thanThiet ? "Thân thiết" :
                       loai == LoaiThanhVien.vip ? "VIP" : "Khách vãng lai";
            }

            @Override
            public LoaiThanhVien fromString(String string) {
                switch (string) {
                    case "Thân thiết": return LoaiThanhVien.thanThiet;
                    case "VIP": return LoaiThanhVien.vip;
                    case "Khách vãng lai": return LoaiThanhVien.khachVangLai;
                    default: return LoaiThanhVien.thanThiet;
                }
            }
        });
    }

    private void updateCboLoaiKhachHangItems() {
        if (ckcKhachVangLai.isSelected()) {
            cboLoaiKhachHang.setItems(FXCollections.observableArrayList(LoaiThanhVien.khachVangLai));
        } else {
            cboLoaiKhachHang.setItems(FXCollections.observableArrayList(
                LoaiThanhVien.thanThiet, LoaiThanhVien.vip
            ));
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

    private boolean kiemTraTxtTimKhachHang() {
        String tenKhachHang = txtTimTenKhachHang.getText();
        String soDienThoai = txtTimSoDienThoai.getText();

        if (tenKhachHang.trim().isEmpty() && soDienThoai.trim().isEmpty()) {
            showWarningAlert("Bạn chưa nhập tên hoặc số điện thoại để tìm!", "image/canhBao.png");
            txtTimTenKhachHang.requestFocus();
            return false;
        }
        return true;
    }

    private List<KhachHang> timKhachHang(String tenKhachHang, String soDienThoai) {
        KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
        if (tenKhachHang.trim().isEmpty()) {
            return khachHang_DAO.timKhachHangTheoSoDienThoai(soDienThoai);
        }
        if (soDienThoai.trim().isEmpty()) {
            return khachHang_DAO.timKhachHangTheoTen(tenKhachHang);
        }
        return khachHang_DAO.timKhachHangTheoTenVaSoDienThoai(tenKhachHang, soDienThoai);
    }

    private void themDuLieuVaoTbDanhSachKhachHang(List<KhachHang> dsKhachHang) {
        if (dsKhachHang == null || dsKhachHang.isEmpty()) {
            tbDanhSachKhachHang.setItems(FXCollections.observableArrayList());
            showWarningAlert("Không tìm thấy khách hàng phù hợp!", "image/canhBao.png");
            return;
        }
        tbDanhSachKhachHang.setItems(FXCollections.observableArrayList(dsKhachHang));
    }

    @FXML
    private void btnTimKhachHangClicked() {
        if (kiemTraTxtTimKhachHang()) {
            String tenKhachHang = txtTimTenKhachHang.getText();
            String soDienThoai = txtTimSoDienThoai.getText();
            List<KhachHang> dskh = timKhachHang(tenKhachHang, soDienThoai);
            themDuLieuVaoTbDanhSachKhachHang(dskh);
        }
    }

    @FXML
    private void btnLamRongClicked() {
        txtMaKhachHang.setText("");
        txtTenKhachHang.setText("");
        txtCCCD_HoChieu.setText("");
        txtEmail.setText("");
        txtSoDienThoai.setText("");
        cboLoaiKhachHang.setValue(ckcKhachVangLai.isSelected() ? LoaiThanhVien.khachVangLai : LoaiThanhVien.thanThiet);
        ckcKhachVangLai.setSelected(false);
        updateCboLoaiKhachHangItems();
        txtTenKhachHang.requestFocus();
    }

    private boolean kiemTraThemKhachHang() {
        return true;
    }

    private void setupTableSelectionListener() {
        tbDanhSachKhachHang.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Update TextFields with selected KhachHang's data
                txtMaKhachHang.setText(newSelection.getMaKhachHang());
                txtTenKhachHang.setText(newSelection.getTenKhachHang());
                txtSoDienThoai.setText(newSelection.getSoDienThoai());
                txtEmail.setText(newSelection.getEmail());
                txtCCCD_HoChieu.setText(newSelection.getCCCD_HoChieu());

                // Update CheckBox based on LoaiThanhVien
                boolean isKhachVangLai = newSelection.getLoaiThanhVien() == LoaiThanhVien.khachVangLai;
                ckcKhachVangLai.setSelected(isKhachVangLai);

                // Update ComboBox items based on CheckBox state
                updateCboLoaiKhachHangItems();

                // Set ComboBox value
                cboLoaiKhachHang.setValue(newSelection.getLoaiThanhVien());
            } else {
                // Clear fields when no selection
                txtMaKhachHang.setText("");
                txtTenKhachHang.setText("");
                txtSoDienThoai.setText("");
                txtEmail.setText("");
                txtCCCD_HoChieu.setText("");
                ckcKhachVangLai.setSelected(false);
                updateCboLoaiKhachHangItems();
                cboLoaiKhachHang.setValue(LoaiThanhVien.thanThiet);
            }
        });
    }
    
    private boolean kiemTraTxtKhachHang() {
    	
    	return true;
    }
}