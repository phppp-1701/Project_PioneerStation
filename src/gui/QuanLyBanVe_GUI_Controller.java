package gui;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dao.ChoNgoi_DAO;
import dao.ChuyenTau_DAO;
import dao.Ga_DAO;
import dao.NhanVien_DAO;
import dao.Tau_DAO;
import dao.Toa_DAO;
import dao.TuyenTau_DAO;
import entity.ChoNgoi;
import entity.ChoNgoi.TrangThaiChoNgoi;
import entity.ChuyenTau;
import entity.Ga;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.Tau;
import entity.Tau.LoaiTau;
import entity.Toa;
import entity.VeTam;
import entity.VeTam.LoaiKhachHang;
import entity.Toa.LoaiToa;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.scene.control.DateCell;

public class QuanLyBanVe_GUI_Controller {
    private String maNhanVien;

    @FXML
    private Label lblQuanLyVe;
    
    @FXML
    private Label lblTrangChu;
    
    @FXML
    private Label lblQuanLyHoaDon;
    
    @FXML
    private Label lblQuanLyKhachHang;
    
    @FXML
    private Label lblQuanLyNhanVien;
    
    @FXML
    private Label lblThongKe;
    
    @FXML
    private Label lblQuanLyChuyenTau;
    
    @FXML 
    private Button btnThemVe;
    
    @FXML
    private Button btnTiepTheo;
    
    private List<VeTam> danhSachVeTam = new ArrayList<VeTam>();
    
    private List<VeTam> danhSachVeXacNhan = new ArrayList<>();
    
// Sửa phương thức initialize để vô hiệu hóa btnTiepTheo ban đầu
public void initialize() {
    btnTiepTheo.setDisable(true); // Vô hiệu hóa btnTiepTheo ban đầu
    cboLoaiKhachHang.setItems(FXCollections.observableArrayList(VeTam.LoaiKhachHang.values()));
    cboLoaiKhachHang.setConverter(new StringConverter<VeTam.LoaiKhachHang>() {
        @Override
        public String toString(VeTam.LoaiKhachHang loai) {
            if (loai == null) return "";
            switch (loai) {
                case treEm:
                    return "Trẻ em";
                case nguoiLon:
                    return "Người lớn";
                case nguoiCaoTuoi:
                    return "Người cao tuổi";
                case sinhVien:
                    return "Sinh viên";
                default:
                    return loai.toString();
            }
        }

        @Override
        public VeTam.LoaiKhachHang fromString(String string) {
            return null;
        }
    });
    cboLoaiKhachHang.setValue(VeTam.LoaiKhachHang.nguoiLon);
    
    cboLoaiKhachHang.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
        if (newValue != null && !danhSachVeTam.isEmpty()) {
            List<VeTam> newDanhSachVeTam = new ArrayList<>();
            for (VeTam veTam : danhSachVeTam) {
                ChoNgoi choNgoi = danhSachChoNgoi.stream()
                    .filter(cn -> cn.getMaChoNgoi().equals(veTam.getMaChoNgoi()))
                    .findFirst().orElse(null);
                if (choNgoi != null) {
                    VeTam newVeTam = new VeTam(veTam.getMaChoNgoi(), choNgoi.getGiaCho(), newValue);
                    newDanhSachVeTam.add(newVeTam);
                }
            }
            danhSachVeTam.clear();
            danhSachVeTam.addAll(newDanhSachVeTam);
            capNhatGiaTamTinh();
        }
    });
    
    txtGiaTamTinh.setEditable(false);
    dpNgayDi.setEditable(false);
    dpNgayVe.setEditable(false);
    initializeDatePickersAndCheckBox();
    initializeComboBoxes();
    btnChonTatCa.setDisable(true);
    btnBoChonTatCa.setDisable(true);
    btnThemVe.setDisable(true);
    
    // Các xử lý sự kiện cho menu điều hướng
    lblQuanLyChuyenTau.setOnMouseClicked(event -> {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            new QuanLyChuyenTau_GUI(currentStage, maNhanVien);
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
    
    lblQuanLyVe.setOnMouseClicked(event -> {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            new QuanLyVe_GUI(currentStage, maNhanVien);
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
    
    lblQuanLyHoaDon.setOnMouseClicked(event -> {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            new QuanLyHoaDon_GUI(currentStage, maNhanVien);
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
    
    lblQuanLyKhachHang.setOnMouseClicked(event -> {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            new QuanLyKhachHang_GUI(currentStage, maNhanVien);
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
    
    lblQuanLyNhanVien.setOnMouseClicked(event -> {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            new QuanLyNhanVien_GUI(currentStage, maNhanVien);
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
    
    lblThongKe.setOnMouseClicked(event -> {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            new ThongKe_GUI(currentStage, maNhanVien);
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
    
    lblTrangChu.setOnMouseClicked(event -> {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            new Home_GUI(currentStage, maNhanVien);
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
}
    private void initializeComboBoxes() {
        Ga_DAO gaDAO = new Ga_DAO();
        TuyenTau_DAO tuyenTauDAO = new TuyenTau_DAO();

        List<Ga> allGa = gaDAO.timGaTheoTen("");
        cboGaDi.setItems(FXCollections.observableArrayList(allGa));
        cboGaDen.setItems(FXCollections.observableArrayList(allGa));

        cboGaDi.setConverter(new StringConverter<Ga>() {
            @Override
            public String toString(Ga ga) {
                return ga != null ? ga.getTenGa() : "";
            }

            @Override
            public Ga fromString(String string) {
                return null;
            }
        });

        cboGaDen.setConverter(new StringConverter<Ga>() {
            @Override
            public String toString(Ga ga) {
                return ga != null ? ga.getTenGa() : "";
            }

            @Override
            public Ga fromString(String string) {
                return null;
            }
        });

        cboGaDi.getSelectionModel().selectedItemProperty().addListener((obs, oldGa, newGa) -> {
            if (newGa != null) {
                List<Ga> validGaDen = tuyenTauDAO.getDanhSachGaDenTheoGaDi(newGa.getMaGa());
                validGaDen.removeIf(ga -> ga.getMaGa().equals(newGa.getMaGa()));
                cboGaDen.setItems(FXCollections.observableArrayList(validGaDen));
                if (cboGaDen.getValue() != null && 
                    !validGaDen.contains(cboGaDen.getValue())) {
                    cboGaDen.getSelectionModel().clearSelection();
                }
            } else {
                cboGaDen.setItems(FXCollections.observableArrayList(allGa));
            }
        });

        cboGaDen.getSelectionModel().selectedItemProperty().addListener((obs, oldGa, newGa) -> {
            if (newGa != null) {
                List<Ga> validGaDi = tuyenTauDAO.getDanhSachGaDiTheoGaDen(newGa.getMaGa());
                validGaDi.removeIf(ga -> ga.getMaGa().equals(newGa.getMaGa()));
                cboGaDi.setItems(FXCollections.observableArrayList(validGaDi));
                if (cboGaDi.getValue() != null && 
                    !validGaDi.contains(cboGaDi.getValue())) {
                    cboGaDi.getSelectionModel().clearSelection();
                }
            } else {
                cboGaDi.setItems(FXCollections.observableArrayList(allGa));
            }
        });
    }
    
    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
        updateNhanVienInfo();
    }
    
    @FXML 
    private Label lblMaNhanVien;
    
    @FXML
    private Label lblTenNhanVien;
    
    @FXML
    private Label lblChucVu;
    
    @FXML 
    private ImageView imgAnhNhanVien;
    
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
    
    @FXML
    private ComboBox<Ga> cboGaDi;
    
    @FXML
    private ComboBox<Ga> cboGaDen;
    
    @FXML
    private DatePicker dpNgayDi;
    
    @FXML
    private DatePicker dpNgayVe;
    
    @FXML
    private CheckBox ckcKhuHoi;
    
    private void initializeDatePickersAndCheckBox() {
        Callback<DatePicker, DateCell> dayCellFactoryNgayDi = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        };
        dpNgayDi.setDayCellFactory(dayCellFactoryNgayDi);

        Callback<DatePicker, DateCell> dayCellFactoryNgayVe = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate ngayDi = dpNgayDi.getValue();
                setDisable(empty || date.isBefore(LocalDate.now()) || 
                           (ngayDi != null && date.isBefore(ngayDi)));
            }
        };
        dpNgayVe.setDayCellFactory(dayCellFactoryNgayVe);

        dpNgayVe.setDisable(true);
        dpNgayVe.setEditable(false);

        ckcKhuHoi.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            dpNgayVe.setDisable(!isSelected);
            dpNgayVe.setEditable(isSelected);
            if (!isSelected) {
                dpNgayVe.setValue(null);
            }
        });

        dpNgayDi.valueProperty().addListener((obs, oldDate, newDate) -> {
            LocalDate ngayVe = dpNgayVe.getValue();
            if (newDate != null && ngayVe != null && ngayVe.isBefore(newDate)) {
                dpNgayVe.setValue(null);
            }
        });
    }
    
    @FXML
    private Button btnTim;
    
    @FXML
    private void btnTimClicked() throws SQLException {
        if(kiemTraThongTinTim()) {
            pnToa.getChildren().clear();
            pnChoNgoi.getChildren().clear();
            selectedChoNgoiAnchorPanes.clear();

            String tenGaDi = cboGaDi.getValue().getTenGa();
            String tenGaDen = cboGaDen.getValue().getTenGa();
            LocalDate ngayKhoiHanh = dpNgayDi.getValue();
            
            List<ChuyenTau> dsct = timChuyenTauTheo(tenGaDi, tenGaDen, ngayKhoiHanh);
            if(dsct.isEmpty()) {
                showWarningAlert("Không tìm thấy chuyến tàu phù hợp!", "image/canhBao.png");
                return;
            }
            int soLuongChuyenTau = dsct.size();
            pnChuyenTau.setPrefWidth(160*soLuongChuyenTau + 14);
            taoPaneChuyenTau(dsct);
        }
    }
    
    @FXML
    private Pane pnToa;
    
    private List<Toa> dstoa;
    
    private double tinhPaneToa(List<Toa> toaList) {
        double widthPerPane = 200.0;
        double spacing = 5.0;
        double initialX = 82.0;
        double finalSpacing = 10.0;

        int soLuongToa = (toaList != null) ? toaList.size() : 0;

        double totalWidth = initialX + (soLuongToa * widthPerPane) + (Math.max(0, soLuongToa - 1) * spacing) + finalSpacing;

        return totalWidth;
    }
    
    private boolean kiemTraThongTinTim() {
        if(cboGaDi.getValue() == null) {
            showWarningAlert("Bạn chưa chọn ga đi!", "image/canhBao.png");
            return false;
        }
        
        if(cboGaDen.getValue() == null) {
            showWarningAlert("Bạn chưa chọn ga đến!", "image/canhBao.png");
            return false;
        }
        
        if(dpNgayDi.getValue() == null) {
            showWarningAlert("Bạn chưa chọn ngày khởi hành!", "image/canhBao.png");
            return false;
        }
        
        return true;
    }
    
    private List<ChuyenTau> timChuyenTauTheo(String tenGaDi, String tenGaDen, LocalDate ngayKhoiHanh) {
        List<ChuyenTau> dsct = new ArrayList<ChuyenTau>();
        ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO();
        dsct = chuyenTau_DAO.timChuyenTauTheoTenGaVaNgay(tenGaDi, tenGaDen, ngayKhoiHanh);
        return dsct;
    }
    
    private void showErrorAlert(String message, String icon) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        setAlertIcon(alert, icon);
        alert.showAndWait();
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
            }
        }
    }
    
    private AnchorPane selectedAnchorPane = null;
    private ChuyenTau chuyenTauDangChon = null;
    private AnchorPane selectedToaAnchorPane = null;
    private List<AnchorPane> selectedChoNgoiAnchorPanes = new ArrayList<>();

    private void taoPaneChuyenTau(List<ChuyenTau> dsct) {
        int layoutX = 14;
        int layoutY = 14;

        pnChuyenTau.getChildren().clear();

        if (dsct == null || dsct.isEmpty()) {
            return;
        }

        Tau_DAO tauDAO = new Tau_DAO();

        String imagePathUnselected = "image/QuanLyBanVe_TauLuaChuaChon.png";
        String imagePathSelected = "image/QuanLyBanVe_TauLuaDaChon.png";

        int index = 0;
        for (ChuyenTau chuyenTau : dsct) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefSize(160, 160);
            anchorPane.setLayoutX(layoutX);
            anchorPane.setLayoutY(layoutY);

            layoutX += 160;

            ImageView imageView = new ImageView();
            try {
                File file = new File(imagePathUnselected);
                if (!file.exists()) {
                    throw new IllegalArgumentException("Không tìm thấy file ảnh tại: " + file.getAbsolutePath());
                }
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(140);
                imageView.setFitHeight(140);
                imageView.setPreserveRatio(false);

                AnchorPane.setTopAnchor(imageView, 0.0);
                AnchorPane.setLeftAnchor(imageView, 10.0);
                AnchorPane.setRightAnchor(imageView, 10.0);
            } catch (Exception e) {
                anchorPane.setStyle("-fx-background-color: red;");
            }

            Label kyHieuLabel = new Label();
            Tau tau = tauDAO.timTauTheoMa(chuyenTau.getMaTau());
            if (tau != null) {
                kyHieuLabel.setText(tau.getKyHieuTau());
            } else {
                kyHieuLabel.setText("N/A");
            }
            kyHieuLabel.setLayoutX(68);
            kyHieuLabel.setLayoutY(52);

            Label gioKhoiHanhLabel = new Label();
            gioKhoiHanhLabel.setText(chuyenTau.getGioKhoiHanh().toString());
            gioKhoiHanhLabel.setLayoutX(68);
            gioKhoiHanhLabel.setLayoutY(135);

            anchorPane.getChildren().addAll(imageView, kyHieuLabel, gioKhoiHanhLabel);

            anchorPane.setOnMouseEntered(event -> anchorPane.setCursor(Cursor.HAND));
            anchorPane.setOnMouseExited(event -> anchorPane.setCursor(Cursor.DEFAULT));

            anchorPane.setOnMouseClicked(event -> {
                // Kiểm tra vé tạm trước khi chuyển
                if (!xacNhanChuyenToaHoacChuyenTau()) {
                    return; // Người dùng chọn quay lại, không làm gì
                }

                if (selectedAnchorPane != null && selectedAnchorPane != anchorPane) {
                    ImageView previousImageView = (ImageView) selectedAnchorPane.getChildren().get(0);
                    try {
                        File file = new File(imagePathUnselected);
                        if (!file.exists()) {
                            throw new IllegalArgumentException("Không tìm thấy file ảnh tại: " + file.getAbsolutePath());
                        }
                        Image image = new Image(file.toURI().toString());
                        previousImageView.setImage(image);
                    } catch (Exception e) {
                    }
                }

                try {
                    File file = new File(imagePathSelected);
                    if (!file.exists()) {
                        throw new IllegalArgumentException("Không tìm thấy file ảnh tại: " + file.getAbsolutePath());
                    }
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
                } catch (Exception e) {
                }

                selectedAnchorPane = anchorPane;
                chuyenTauDangChon = chuyenTau;

                btnBoChonTatCa.setDisable(true);
                btnChonTatCa.setDisable(true);
                btnThemVe.setDisable(true);

                try {
                    Toa_DAO toa_DAO = new Toa_DAO();
                    dstoa = toa_DAO.getToaByMaTau(chuyenTauDangChon.getMaTau());
                    if (dstoa == null || dstoa.isEmpty()) {
                        showWarningAlert("Không tìm thấy toa cho chuyến tàu này!", "image/canhBao.png");
                        return;
                    }
                    taoPaneToa(dstoa);
                } catch (SQLException e) {
                    e.printStackTrace();
                    showErrorAlert("Lỗi khi lấy danh sách toa: " + e.getMessage(), "image/canhBao.png");
                }
            });

            pnChuyenTau.getChildren().add(anchorPane);
            index++;
        }
    }
    
    @FXML
    private Pane pnChuyenTau;
    
    private void taoPaneToa(List<Toa> dstoa) throws SQLException {
        pnToa.getChildren().clear();

        selectedToaAnchorPane = null;
        selectedChoNgoiAnchorPanes.clear();

        pnToa.setPrefWidth(tinhPaneToa(dstoa));

        try {
            File file = new File("image/QuanLyBanVe_ToaTauLai.png");
            if (!file.exists()) {
                throw new IllegalArgumentException("Không tìm thấy file ảnh tại: " + file.getAbsolutePath());
            }
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);

            imageView.setFitWidth(70.0);
            imageView.setFitHeight(70.0);
            imageView.setPreserveRatio(false);

            imageView.setLayoutX(0.0);
            imageView.setLayoutY(5.0);
            imageView.setScaleX(-1);

            pnToa.getChildren().add(imageView);
        } catch (Exception e) {
            Rectangle errorRect = new Rectangle(70.0, 70.0);
            errorRect.setFill(javafx.scene.paint.Color.RED);
            errorRect.setLayoutX(0.0);
            errorRect.setLayoutY(5.0);
            pnToa.getChildren().add(errorRect);
        }

        if (dstoa == null || dstoa.isEmpty()) {
            return;
        }

        double x = 82.0;
        double y = 6.0;
        double width = 200.0;
        double height = 70.0;
        double spacing = 5.0;

        for (Toa toa : dstoa) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefSize(width, height);
            anchorPane.setLayoutX(x);
            anchorPane.setLayoutY(y);

            Rectangle whiteRect = new Rectangle(width, height);
            whiteRect.setFill(javafx.scene.paint.Color.WHITE);

            Rectangle blueRect = new Rectangle(200.0, 15.0);
            blueRect.setFill(javafx.scene.paint.Color.web("#ccdaf5"));
            blueRect.setLayoutX(0.0);
            blueRect.setLayoutY(0.0);

            anchorPane.getChildren().addAll(whiteRect, blueRect);

            anchorPane.setOnMouseEntered(event -> anchorPane.setCursor(Cursor.HAND));
            anchorPane.setOnMouseExited(event -> anchorPane.setCursor(Cursor.DEFAULT));

            anchorPane.setOnMouseClicked(event -> {
                // Kiểm tra vé tạm trước khi chuyển
                if (!xacNhanChuyenToaHoacChuyenTau()) {
                    return; // Người dùng chọn quay lại, không làm gì
                }

                btnChonTatCa.setDisable(false);
                toaDangChon = toa;
                ChoNgoi_DAO choNgoi_DAO = new ChoNgoi_DAO();
                danhSachChoNgoi = choNgoi_DAO.timChoNgoiTheoMaToaVaMaChuyenTau(toa.getMaToa(), chuyenTauDangChon.getMaChuyenTau());
                Tau_DAO tau_DAO = new Tau_DAO();
                Tau tauDangChon = tau_DAO.timTauTheoMa(chuyenTauDangChon.getMaTau());
                if(tauDangChon.getLoaiTau() == LoaiTau.SE) {
                    if(toaDangChon.getLoaiToa() == LoaiToa.giuongNamDieuHoa) {
                        taoChoNgoi7x5(danhSachChoNgoi);
                        ckcChonTheoDay.setSelected(false);
                    } else {
                        taoChoNgoi5x10(danhSachChoNgoi);
                        ckcChonTheoDay.setSelected(false);
                    }
                } else {
                    if(toaDangChon.getLoaiToa() == LoaiToa.gheCungDieuHoa) {
                        taoChoNgoi5x10(danhSachChoNgoi);
                        ckcChonTheoDay.setSelected(false);
                    } else {
                        taoChoNgoi4x10(danhSachChoNgoi);
                        ckcChonTheoDay.setSelected(false);
                    }
                }

                if (selectedToaAnchorPane != null && selectedToaAnchorPane != anchorPane) {
                    Rectangle previousBlueRect = (Rectangle) selectedToaAnchorPane.getChildren().get(1);
                    previousBlueRect.setFill(javafx.scene.paint.Color.web("#ccdaf5"));
                }

                blueRect.setFill(javafx.scene.paint.Color.web("#2e7d32"));
                selectedToaAnchorPane = anchorPane;

                btnBoChonTatCa.setDisable(true);
                btnThemVe.setDisable(true);
            });

            String tenToa = "Toa: " + toa.getTenToa(); 
            Label lblTenToa = new Label(tenToa);
            lblTenToa.setLayoutX(14);
            lblTenToa.setLayoutY(19);
            lblTenToa.setFont(Font.font("Tahoma"));
            anchorPane.getChildren().add(lblTenToa);

            String loaiToa = "";
            if(toa.getLoaiToa().equals(LoaiToa.gheCungDieuHoa)) {
                loaiToa = "Ghế cứng điều hòa";
            } else if(toa.getLoaiToa().equals(LoaiToa.ngoiMemDieuHoa)) {
                loaiToa = "Ngồi mềm điều hòa";
            } else {
                loaiToa = "Giường nằm điều hòa";
            }
            Label lblLoaiToa = new Label(loaiToa);
            lblLoaiToa.setFont(Font.font("Tahoma"));
            lblLoaiToa.setLayoutX(14);
            lblLoaiToa.setLayoutY(37);
            anchorPane.getChildren().add(lblLoaiToa);

            ChoNgoi_DAO choNgoi_DAO = new ChoNgoi_DAO();
            List<ChoNgoi> tinhGia = choNgoi_DAO.timChoNgoiTheoMaToaVaMaChuyenTau(toa.getMaToa(), chuyenTauDangChon.getMaChuyenTau());
            BigDecimal giaCho = tinhGia.get(0).getGiaCho();
            Label lblGiaCho = new Label(giaCho+ " vnđ");
            lblGiaCho.setFont(Font.font("Tahoma"));
            lblGiaCho.setLayoutX(14);
            lblGiaCho.setLayoutY(53);
            anchorPane.getChildren().add(lblGiaCho);

            int soCho = choNgoi_DAO.demSoChoNgoiChuaDat(chuyenTauDangChon.getMaChuyenTau(), toa.getMaToa());
            Label lblSoCho = new Label("Còn: " + soCho + " chỗ");
            lblSoCho.setFont(Font.font("Tahoma"));
            lblSoCho.setLayoutX(119);
            lblSoCho.setLayoutY(18);
            anchorPane.getChildren().add(lblSoCho);

            pnToa.getChildren().add(anchorPane);
            x += width + spacing;
        }
    }
    
    private Toa toaDangChon = null;
    
    @FXML
    private AnchorPane pnChoNgoi;
    
    @FXML
    private Button btnChonTatCa;
    
    @FXML
    private Button btnBoChonTatCa;
    
    private List<ChoNgoi> danhSachChoNgoi = null;
    
    private void taoChoNgoi7x5(List<ChoNgoi> danhSachChoNgoi) {
        pnChoNgoi.getChildren().clear();
        selectedChoNgoiAnchorPanes.clear();

        double width = 38.0;
        double height = 18.0;
        double spacingX = 8.0;
        double spacingY = 5.0;
        double startX = 15.0;
        double startY = 10.0;

        int rows = 7;
        int cols = 5;
        int choNgoiIndex = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (choNgoiIndex >= danhSachChoNgoi.size()) {
                    break;
                }
                ChoNgoi choNgoi = danhSachChoNgoi.get(choNgoiIndex);

                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefSize(width, height);
                double x = startX + col * (width + spacingX);
                double y = startY + row * (height + spacingY);
                anchorPane.setLayoutX(x);
                anchorPane.setLayoutY(y);

                Rectangle rect = new Rectangle(width, height);
                switch (choNgoi.getTrangThai()) {
                    case daDat:
                        rect.setFill(javafx.scene.paint.Color.web("#992b15"));
                        break;
                    case dangDat:
                        rect.setFill(javafx.scene.paint.Color.web("#2e7d32"));
                        break;
                    case chuaDat:
                        rect.setFill(javafx.scene.paint.Color.web("#ccdaf5"));
                        break;
                }
                rect.setStroke(javafx.scene.paint.Color.BLACK);
                rect.setStrokeWidth(1.0);

                Label lblSoGhe = new Label(choNgoi.getTenChoNgoi());
                lblSoGhe.setLayoutX(5);
                lblSoGhe.setLayoutY(1);
                lblSoGhe.setFont(Font.font("Tahoma", 10));

                anchorPane.getChildren().addAll(rect, lblSoGhe);
                anchorPane.setUserData(choNgoi);

                anchorPane.setOnMouseEntered(event -> anchorPane.setCursor(Cursor.HAND));
                anchorPane.setOnMouseExited(event -> anchorPane.setCursor(Cursor.DEFAULT));

                if (choNgoi.getTrangThai() == TrangThaiChoNgoi.chuaDat) {
                    anchorPane.setOnMouseClicked(event -> xuLyChonChoNgoi(anchorPane, choNgoi));
                }

                pnChoNgoi.getChildren().add(anchorPane);
                choNgoiIndex++;
            }
        }

        capNhatTrangThaiNut();
    }
    
    private void taoChoNgoi5x10(List<ChoNgoi> danhSachChoNgoi) {
        pnChoNgoi.getChildren().clear();
        selectedChoNgoiAnchorPanes.clear();

        double width = 30.0;
        double height = 22.0;
        double spacingX = 6.0;
        double spacingY = 6.0;
        double startX = 10.0;
        double startY = 15.0;

        int rows = 5;
        int cols = 10;
        int choNgoiIndex = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (choNgoiIndex >= danhSachChoNgoi.size()) {
                    break;
                }
                ChoNgoi choNgoi = danhSachChoNgoi.get(choNgoiIndex);

                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefSize(width, height);
                double x = startX + col * (width + spacingX);
                double y = startY + row * (height + spacingY);
                anchorPane.setLayoutX(x);
                anchorPane.setLayoutY(y);

                Rectangle rect = new Rectangle(width, height);
                switch (choNgoi.getTrangThai()) {
                    case daDat:
                        rect.setFill(javafx.scene.paint.Color.web("#992b15"));
                        break;
                    case dangDat:
                        rect.setFill(javafx.scene.paint.Color.web("#2e7d32"));
                        break;
                    case chuaDat:
                        rect.setFill(javafx.scene.paint.Color.web("#ccdaf5"));
                        break;
                }
                rect.setStroke(javafx.scene.paint.Color.BLACK);
                rect.setStrokeWidth(1.0);

                Label lblSoGhe = new Label(choNgoi.getTenChoNgoi());
                lblSoGhe.setLayoutX(5);
                lblSoGhe.setLayoutY(3);
                lblSoGhe.setFont(Font.font("Tahoma", 9));

                anchorPane.getChildren().addAll(rect, lblSoGhe);
                anchorPane.setUserData(choNgoi);

                anchorPane.setOnMouseEntered(event -> anchorPane.setCursor(Cursor.HAND));
                anchorPane.setOnMouseExited(event -> anchorPane.setCursor(Cursor.DEFAULT));

                if (choNgoi.getTrangThai() == TrangThaiChoNgoi.chuaDat) {
                    anchorPane.setOnMouseClicked(event -> xuLyChonChoNgoi(anchorPane, choNgoi));
                }

                pnChoNgoi.getChildren().add(anchorPane);
                choNgoiIndex++;
            }
        }

        capNhatTrangThaiNut();
    }
    
    private void taoChoNgoi4x10(List<ChoNgoi> danhSachChoNgoi) {
        pnChoNgoi.getChildren().clear();
        selectedChoNgoiAnchorPanes.clear();

        double width = 32.0;
        double height = 28.0;
        double spacingX = 5.0;
        double spacingY = 6.0;
        double startX = 10.0;
        double startY = 15.0;

        int rows = 4;
        int cols = 10;
        int choNgoiIndex = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (choNgoiIndex >= danhSachChoNgoi.size()) {
                    break;
                }
                ChoNgoi choNgoi = danhSachChoNgoi.get(choNgoiIndex);

                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefSize(width, height);
                double x = startX + col * (width + spacingX);
                double y = startY + row * (height + spacingY);
                anchorPane.setLayoutX(x);
                anchorPane.setLayoutY(y);

                Rectangle rect = new Rectangle(width, height);
                switch (choNgoi.getTrangThai()) {
                    case daDat:
                        rect.setFill(javafx.scene.paint.Color.web("#992b15"));
                        break;
                    case dangDat:
                        rect.setFill(javafx.scene.paint.Color.web("#2e7d32"));
                        break;
                    case chuaDat:
                        rect.setFill(javafx.scene.paint.Color.web("#ccdaf5"));
                        break;
                }
                rect.setStroke(javafx.scene.paint.Color.BLACK);
                rect.setStrokeWidth(1.0);

                Label lblSoGhe = new Label(choNgoi.getTenChoNgoi());
                lblSoGhe.setLayoutX(5);
                lblSoGhe.setLayoutY(5);
                lblSoGhe.setFont(Font.font("Tahoma", 9));

                anchorPane.getChildren().addAll(rect, lblSoGhe);
                anchorPane.setUserData(choNgoi);

                anchorPane.setOnMouseEntered(event -> anchorPane.setCursor(Cursor.HAND));
                anchorPane.setOnMouseExited(event -> anchorPane.setCursor(Cursor.DEFAULT));

                if (choNgoi.getTrangThai() == TrangThaiChoNgoi.chuaDat) {
                    anchorPane.setOnMouseClicked(event -> xuLyChonChoNgoi(anchorPane, choNgoi));
                }

                pnChoNgoi.getChildren().add(anchorPane);
                choNgoiIndex++;
            }
        }

        capNhatTrangThaiNut();
    }
    
    private AnchorPane timAnchorPaneTheoChoNgoi(ChoNgoi choNgoi) {
        for (Node node : pnChoNgoi.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane pane = (AnchorPane) node;
                Label lbl = (Label) pane.getChildren().get(1);
                if (lbl.getText().equals(choNgoi.getTenChoNgoi())) {
                    return pane;
                }
            }
        }
        return null;
    }
    
    private void xuLyChonChoNgoi(AnchorPane anchorPane, ChoNgoi choNgoi) {
        boolean daXacNhan = danhSachVeXacNhan.stream()
            .anyMatch(ve -> ve.getMaChoNgoi().equals(choNgoi.getMaChoNgoi()));
        if (daXacNhan) {
            showWarningAlert("Chỗ ngồi này đã được xác nhận!", "image/canhBao.png");
            return;
        }

        Rectangle rect = (Rectangle) anchorPane.getChildren().get(0);

        if (selectedChoNgoiAnchorPanes.contains(anchorPane)) {
            rect.setFill(javafx.scene.paint.Color.web("#ccdaf5"));
            selectedChoNgoiAnchorPanes.remove(anchorPane);
            // Sửa lại để xóa dựa trên maChoNgoi
            danhSachVeTam.removeIf(ve -> ve.getMaChoNgoi().equals(choNgoi.getMaChoNgoi()));
        } else {
            rect.setFill(javafx.scene.paint.Color.web("#ffa500"));
            selectedChoNgoiAnchorPanes.add(anchorPane);
            VeTam veTam = new VeTam(choNgoi.getMaChoNgoi(), choNgoi.getGiaCho(), cboLoaiKhachHang.getValue());
            danhSachVeTam.add(veTam);
        }
        capNhatTrangThaiNut();
        capNhatGiaTamTinh();
    }
    
    @FXML
    private CheckBox ckcChonTheoDay;

    private void capNhatTrangThaiNut() {
        boolean coChoChuaDat = danhSachChoNgoi != null && 
                               danhSachChoNgoi.stream().anyMatch(cho -> cho.getTrangThai() == TrangThaiChoNgoi.chuaDat);
        btnChonTatCa.setDisable(!coChoChuaDat);
        btnBoChonTatCa.setDisable(selectedChoNgoiAnchorPanes.isEmpty());
        btnThemVe.setDisable(selectedChoNgoiAnchorPanes.isEmpty());
        btnTiepTheo.setDisable(danhSachVeXacNhan.isEmpty()); // Bật/tắt btnTiepTheo dựa trên danhSachVeXacNhan
    }
    
    private List<ChoNgoi> choNgoiDangChon = new ArrayList<ChoNgoi>();
    
    @FXML
    private void btnChonTatCaClicked() {
        if (danhSachChoNgoi == null || danhSachChoNgoi.isEmpty()) {
            showWarningAlert("Không có chỗ ngồi nào để chọn!", "image/canhBao.png");
            return;
        }

        selectedChoNgoiAnchorPanes.clear();
        danhSachVeTam.clear();

        for (Node node : pnChoNgoi.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane choPane = (AnchorPane) node;
                ChoNgoi choNgoi = (ChoNgoi) choPane.getUserData();

                if (choNgoi != null && choNgoi.getTrangThai() == TrangThaiChoNgoi.chuaDat) {
                    Rectangle rect = (Rectangle) choPane.getChildren().get(0);
                    rect.setFill(javafx.scene.paint.Color.web("#FFA500"));
                    selectedChoNgoiAnchorPanes.add(choPane);
                    VeTam veTam = new VeTam(choNgoi.getMaChoNgoi(), choNgoi.getGiaCho(), cboLoaiKhachHang.getValue());
                    danhSachVeTam.add(veTam);
                }
            }
        }

        capNhatTrangThaiNut();
        capNhatGiaTamTinh();

    }
    
    @FXML
    private void btnBoChonTatCaClicked() {
        if (selectedChoNgoiAnchorPanes.isEmpty() && danhSachVeTam.isEmpty()) {
            showWarningAlert("Không có chỗ ngồi hoặc vé nào được chọn để bỏ!", "image/canhBao.png");
            return;
        }

        for (AnchorPane choPane : selectedChoNgoiAnchorPanes) {
            ChoNgoi choNgoi = (ChoNgoi) choPane.getUserData();
            if (choNgoi != null) {
                Rectangle rect = (Rectangle) choPane.getChildren().get(0);
                choNgoi.setTrangThai(TrangThaiChoNgoi.chuaDat); // Cập nhật trạng thái
                rect.setFill(javafx.scene.paint.Color.web("#ccdaf5")); // Đặt màu chưa đặt
            }
        }

        selectedChoNgoiAnchorPanes.clear();
        danhSachVeTam.clear();

        pnGioVe.getChildren().clear();
        pnGioVe.setPrefHeight(0);

        capNhatTrangThaiNut();
        capNhatGiaTamTinh();
    }
    
    @FXML
    private TextField txtGiaTamTinh;
    
    private BigDecimal tinhTongGiaTien() {
        BigDecimal giaTien = BigDecimal.ZERO;
        for (VeTam veTam : danhSachVeTam) {
            giaTien = giaTien.add(veTam.getGiaTien());
        }
        return giaTien;
    }
    
    private void capNhatGiaTamTinh() {
        BigDecimal tongGia = BigDecimal.ZERO;
        if (danhSachVeTam != null) {
            for (VeTam veTam : danhSachVeTam) {
                if (veTam.getGiaTien() != null) {
                    tongGia = tongGia.add(veTam.getGiaTien());
                }
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String giaFormatted = decimalFormat.format(tongGia) + " VNĐ";
        txtGiaTamTinh.setText(giaFormatted);
    }
    
    @FXML
    private ComboBox<LoaiKhachHang> cboLoaiKhachHang;
    
    @FXML
    private AnchorPane pnGioVe;
    
    @FXML
    private void btnThemVeClicked() {
        if (danhSachVeTam.isEmpty()) {
            showWarningAlert("Không có vé tạm nào được chọn để thêm!", "image/canhBao.png");
            return;
        }
        
        ChoNgoi_DAO choNgoiDAO = new ChoNgoi_DAO();
        for (VeTam veTam : danhSachVeTam) {
            ChoNgoi choNgoi = danhSachChoNgoi.stream()
                .filter(cn -> cn.getMaChoNgoi().equals(veTam.getMaChoNgoi()))
                .findFirst()
                .orElse(null);
            if (choNgoi != null) {
                boolean updated = choNgoiDAO.capNhatTrangThaiChoNgoi(choNgoi.getMaChoNgoi(), TrangThaiChoNgoi.dangDat, chuyenTauDangChon.getMaChuyenTau());
                if (updated) {
                    choNgoi.setTrangThai(TrangThaiChoNgoi.dangDat);
                } else {
                    showErrorAlert("Không thể cập nhật trạng thái chỗ ngồi: " + choNgoi.getTenChoNgoi(), "image/canhBao.png");
                    return;
                }
            }
        }

        danhSachVeXacNhan.addAll(danhSachVeTam);

        pnGioVe.getChildren().clear();

        int soVeXacNhan = danhSachVeXacNhan.size();
        double chieuCao = 10 + (soVeXacNhan * 200) + (Math.max(0, soVeXacNhan - 1) * 10) + 10;
        pnGioVe.setPrefHeight(chieuCao);

        Tau_DAO tauDAO = new Tau_DAO();
        Tau tau = tauDAO.timTauTheoMa(chuyenTauDangChon.getMaTau());

        double y = 10.0;
        for (VeTam veTam : danhSachVeXacNhan) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefSize(450, 200);
            anchorPane.setLayoutX(10);
            anchorPane.setLayoutY(y);

            Rectangle rect = new Rectangle(450, 200);
            rect.setFill(javafx.scene.paint.Color.WHITE);
            rect.setStroke(javafx.scene.paint.Color.BLACK);
            rect.setStrokeWidth(1.0);

            String loaiKhachHang = "";
            switch (veTam.getLoaiKhachHang()) {
                case treEm:
                    loaiKhachHang = "Trẻ em";
                    break;
                case nguoiLon:
                    loaiKhachHang = "Người lớn";
                    break;
                case nguoiCaoTuoi:
                    loaiKhachHang = "Người cao tuổi";
                    break;
                case sinhVien:
                    loaiKhachHang = "Sinh viên";
                    break;
            }
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            String giaVe = decimalFormat.format(veTam.getGiaTien()) + " VNĐ";
            String gaDi = cboGaDi.getValue() != null ? cboGaDi.getValue().getTenGa() : "N/A";
            String ngayKhoiHanh = dpNgayDi.getValue() != null ? dpNgayDi.getValue().toString() : "N/A";
            String tenTau = (tau != null) ? tau.getKyHieuTau() : "N/A";
            String tenToa = toaDangChon != null ? toaDangChon.getTenToa() : "N/A";
            ChoNgoi chn = choNgoiDAO.timChoNgoiTheoMaChoNgoi(veTam.getMaChoNgoi());
            String thongTinTrai = "Ga đi: " + gaDi +
                                 "\nNgày khởi hành: " + ngayKhoiHanh +
                                 "\nTên tàu: " + tenTau +
                                 "\nTên toa: " + tenToa +
                                 "\nLoại khách hàng: " + loaiKhachHang +
                                 "\nGiá vé: " + giaVe +
                                 "\nChỗ ngồi: " + chn.getTenChoNgoi();
            Label labelTrai = new Label(thongTinTrai);
            labelTrai.setLayoutX(10);
            labelTrai.setLayoutY(10);
            labelTrai.setFont(Font.font("Tahoma", 11));

            String gaDen = cboGaDen.getValue() != null ? cboGaDen.getValue().getTenGa() : "N/A";
            String gioKhoiHanh = chuyenTauDangChon.getGioKhoiHanh() != null 
                ? chuyenTauDangChon.getGioKhoiHanh().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) 
                : "N/A";
            String thongTinPhai = "Ga đến: " + gaDen +
                                 "\nGiờ khởi hành: " + gioKhoiHanh;
            Label labelPhai = new Label(thongTinPhai);
            labelPhai.setLayoutX(300);
            labelPhai.setLayoutY(10);
            labelPhai.setFont(Font.font("Tahoma", 11));

            Button btnXoa = new Button("Xóa");
            btnXoa.setLayoutX(400);
            btnXoa.setLayoutY(165);
            btnXoa.setPrefSize(40, 25);
            btnXoa.setOnAction(e -> {
                ChoNgoi_DAO choNgoiDAOXoa = new ChoNgoi_DAO();
                ChoNgoi choNgoi = danhSachChoNgoi.stream()
                    .filter(cn -> cn.getMaChoNgoi().equals(veTam.getMaChoNgoi()))
                    .findFirst()
                    .orElse(null);
                if (choNgoi != null) {
                    boolean updated = choNgoiDAOXoa.capNhatTrangThaiChoNgoi(choNgoi.getMaChoNgoi(), TrangThaiChoNgoi.chuaDat, chuyenTauDangChon.getMaChuyenTau());
                    if (updated) {
                        choNgoi.setTrangThai(TrangThaiChoNgoi.chuaDat);
                    } else {
                        showErrorAlert("Không thể cập nhật trạng thái chỗ ngồi: " + choNgoi.getTenChoNgoi(), "image/canhBao.png");
                        return;
                    }
                }

                danhSachVeXacNhan.remove(veTam);
                capNhatTongVeVaTien();
                pnGioVe.getChildren().remove(anchorPane);
                
                double newY = 10.0;
                for (Node node : pnGioVe.getChildren()) {
                    if (node instanceof AnchorPane) {
                        node.setLayoutY(newY);
                        newY += 200 + 10;
                    }
                }
                
                double newChieuCao = 10 + (danhSachVeXacNhan.size() * 200) + (Math.max(0, danhSachVeXacNhan.size() - 1) * 10) + 10;
                pnGioVe.setPrefHeight(newChieuCao);
                
                capNhatGiaTamTinh();
                capNhatTrangThaiNut(); // Cập nhật trạng thái nút, bao gồm btnTiepTheo

                if (toaDangChon != null && chuyenTauDangChon != null) {
                    ChoNgoi_DAO choNgoiDAORefresh = new ChoNgoi_DAO();
                    danhSachChoNgoi = choNgoiDAORefresh.timChoNgoiTheoMaToaVaMaChuyenTau(toaDangChon.getMaToa(), chuyenTauDangChon.getMaChuyenTau());
                    
                    Tau_DAO tau_DAO = new Tau_DAO();
                    Tau tauDangChon = tau_DAO.timTauTheoMa(chuyenTauDangChon.getMaTau());
                    if (tauDangChon.getLoaiTau() == LoaiTau.SE) {
                        if (toaDangChon.getLoaiToa() == LoaiToa.giuongNamDieuHoa) {
                            ckcChonTheoDay.setSelected(false);
                            taoChoNgoi7x5(danhSachChoNgoi);
                        } else {
                            ckcChonTheoDay.setSelected(false);
                            taoChoNgoi5x10(danhSachChoNgoi);
                        }
                    } else {
                        if (toaDangChon.getLoaiToa() == LoaiToa.gheCungDieuHoa) {
                            ckcChonTheoDay.setSelected(false);
                            taoChoNgoi5x10(danhSachChoNgoi);
                        } else {
                            ckcChonTheoDay.setSelected(false);
                            taoChoNgoi4x10(danhSachChoNgoi);
                        }
                    }
                }
            });

            anchorPane.getChildren().addAll(rect, labelTrai, labelPhai, btnXoa);
            pnGioVe.getChildren().add(anchorPane);
            y += 200 + 10;
        }

        if (toaDangChon != null && chuyenTauDangChon != null) {
            danhSachChoNgoi = choNgoiDAO.timChoNgoiTheoMaToaVaMaChuyenTau(toaDangChon.getMaToa(), chuyenTauDangChon.getMaChuyenTau());
            
            Tau_DAO tau_DAO = new Tau_DAO();
            Tau tauDangChon = tau_DAO.timTauTheoMa(chuyenTauDangChon.getMaTau());
            if (tauDangChon.getLoaiTau() == LoaiTau.SE) {
                if (toaDangChon.getLoaiToa() == LoaiToa.giuongNamDieuHoa) {
                    taoChoNgoi7x5(danhSachChoNgoi);
                    ckcChonTheoDay.setSelected(false);
                } else {
                    taoChoNgoi5x10(danhSachChoNgoi);
                    ckcChonTheoDay.setSelected(false);
                }
            } else {
                if (toaDangChon.getLoaiToa() == LoaiToa.gheCungDieuHoa) {
                    taoChoNgoi5x10(danhSachChoNgoi);
                    ckcChonTheoDay.setSelected(false);
                } else {
                    taoChoNgoi4x10(danhSachChoNgoi);
                    ckcChonTheoDay.setSelected(false);
                }
            }
        }

        selectedChoNgoiAnchorPanes.clear();
        danhSachVeTam.clear();

        capNhatTrangThaiNut(); // Cập nhật trạng thái nút sau khi thêm vé
        capNhatGiaTamTinh();
        capNhatTongVeVaTien();
    }
    
    @FXML 
    private TextField txtTongSoVe;
    
    @FXML 
    private TextField txtTongTienTamTinh;
    

    private void capNhatTongVeVaTien() {
        int soLuongVe = danhSachVeXacNhan.size();
        
        BigDecimal tongTien = BigDecimal.ZERO;
        for (VeTam veTam : danhSachVeXacNhan) {
            tongTien = tongTien.add(veTam.getGiaTien());
        }
        
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String tongTienFormatted = decimalFormat.format(tongTien) + " VNĐ";
        
        txtTongSoVe.setText(String.valueOf(soLuongVe));
        txtTongTienTamTinh.setText(tongTienFormatted);
    }
    
    private boolean xacNhanChuyenToaHoacChuyenTau() {
        if (!danhSachVeTam.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận chuyển đổi");
            alert.setHeaderText("Bạn đang có vé tạm chưa xác nhận!");
            alert.setContentText("Nếu chuyển sang toa hoặc chuyến tàu khác, danh sách vé tạm sẽ bị xóa. Bạn có muốn tiếp tục?");
            
            ButtonType btnTiepTuc = new ButtonType("Vẫn chuyển");
            ButtonType btnQuayLai = new ButtonType("Quay lại");
            alert.getButtonTypes().setAll(btnTiepTuc, btnQuayLai);
            
            setAlertIcon(alert, "image/canhBao.png");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == btnTiepTuc) {
                // Người dùng chọn tiếp tục, xóa vé tạm
                danhSachVeTam.clear();
                selectedChoNgoiAnchorPanes.clear();
                capNhatGiaTamTinh();
                capNhatTrangThaiNut();
                return true;
            } else {
                // Người dùng chọn quay lại
                return false;
            }
        }
        return true; // Không có vé tạm, cho phép chuyển
    }
    
    @FXML
    private void btnTiepTheoClicked() {
        try {
            // Tạo Stage mới cho ThanhToan_GUI
            Stage thanhToanStage = new Stage();
            
            // Khởi tạo ThanhToan_GUI với hai tham số
            new ThanhToan_GUI(thanhToanStage, maNhanVien, danhSachVeXacNhan);
            
            // Căn giữa màn hình
            thanhToanStage.setOnShown(event -> {
                thanhToanStage.setX((Screen.getPrimary().getVisualBounds().getWidth() - thanhToanStage.getWidth()) / 2);
                thanhToanStage.setY((Screen.getPrimary().getVisualBounds().getHeight() - thanhToanStage.getHeight()) / 2);
            });
            
            // Không cho phép thay đổi kích thước
            thanhToanStage.setResizable(false);
            
            // Hiển thị cửa sổ
            thanhToanStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Lỗi khi mở giao diện thanh toán: " + e.getMessage(), "image/canhBao.png");
        }
    }
}