package gui;

import entity.KhachHang;
import entity.KhachHang.LoaiThanhVien;
import entity.KhuyenMai;
import entity.VeTam;
import entity.HoaDon.PhuongThucThanhToan;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import dao.KhachHang_DAO;
import dao.KhuyenMai_DAO;
import entity.ChoNgoi;
import entity.ChuyenTau;
import entity.TuyenTau;
import entity.Tau;
import entity.Toa;
import entity.Toa.LoaiToa;
import entity.VeTam.LoaiKhachHang;
import dao.Tau_DAO;
import dao.ChuyenTau_DAO;
import dao.Toa_DAO;
import dao.Ga_DAO;
import dao.ChoNgoi_DAO;
import dao.TuyenTau_DAO;

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

    @FXML
    private Button btnCapNhatKhachHang;

    @FXML
    private AnchorPane pnGioVe;

    @FXML
    private Button btnBanVe;

    @FXML
    private Button btnQuayLai;

    @FXML
    private ComboBox<KhuyenMai> cboKhuyenMai;

    @FXML
    private TextField txtGiamGia;

    @FXML
    private TextField txtThanhTien;

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public void setDanhSachVeXacNhan(List<VeTam> danhSachVeXacNhan) {
        this.danhSachVeXacNhan = danhSachVeXacNhan;
        try {
            initializeTicketPanes();
            updateDiscountAndPrice();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTienTraLai() {
        try {
            String khachDuaText = txtTienKhachDua.getText().replaceAll("[^0-9]", "");
            BigDecimal khachDua = new BigDecimal(khachDuaText.isEmpty() ? "0" : khachDuaText);

            String thanhTienText = txtThanhTien.getText().replaceAll("[^0-9]", "");
            BigDecimal thanhTien = new BigDecimal(thanhTienText.isEmpty() ? "0" : thanhTienText);

            BigDecimal traLai = khachDua.subtract(thanhTien);
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            txtTienTraLai.setText(currencyFormatter.format(traLai));
        } catch (NumberFormatException e) {
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            txtTienTraLai.setText(currencyFormatter.format(BigDecimal.ZERO));
        }
    }
    
    public void initializeData() {
    	// Set up TextFields
        txtTienKhachDua.setEditable(true);
        txtTienTraLai.setEditable(false);

        // Vietnamese currency denominations
        BigDecimal[] denominations = new BigDecimal[] {
            new BigDecimal("500"),
            new BigDecimal("1000"),
            new BigDecimal("2000"),
            new BigDecimal("5000"),
            new BigDecimal("10000"),
            new BigDecimal("20000"),
            new BigDecimal("50000"),
            new BigDecimal("100000"),
            new BigDecimal("200000"),
            new BigDecimal("500000")
        };

        // Array of buttons
        Button[] giaButtons = new Button[] {
            btnGia1, btnGia2, btnGia3,
            btnGia4, btnGia5, btnGia6,
            btnGia7, btnGia8, btnGia9
        };

        // NumberFormat for VND
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Function to update button denominations based on txtThanhTien
        Runnable updateDenominations = () -> {
            BigDecimal thanhTien;
            try {
                String thanhTienText = txtThanhTien.getText().replaceAll("[^0-9]", "");
                thanhTien = new BigDecimal(thanhTienText.isEmpty() ? "0" : thanhTienText);
            } catch (NumberFormatException e) {
                thanhTien = BigDecimal.ZERO;
            }

            // Filter denominations >= thanhTien
            List<BigDecimal> availableDenominations = new ArrayList<>();
            for (BigDecimal denom : denominations) {
                if (denom.compareTo(thanhTien) >= 0) {
                    availableDenominations.add(denom);
                }
            }

            // Update buttons
            for (int i = 0; i < giaButtons.length; i++) {
                if (i < availableDenominations.size()) {
                    BigDecimal denom = availableDenominations.get(i);
                    giaButtons[i].setText(currencyFormatter.format(denom));
                    giaButtons[i].setVisible(true);
                    giaButtons[i].setManaged(true);
                    // Set action
                    giaButtons[i].setOnAction(event -> {
                        txtTienKhachDua.setText(currencyFormatter.format(denom));
                    });
                } else {
                    giaButtons[i].setVisible(false);
                    giaButtons[i].setManaged(false);
                }
            }
        };

        // Update denominations when txtThanhTien changes
        txtThanhTien.textProperty().addListener((obs, oldValue, newValue) -> {
            updateDenominations.run();
            // Also update txtTienTraLai
            updateTienTraLai();
        });

        // Update txtTienTraLai when txtTienKhachDua changes
        txtTienKhachDua.textProperty().addListener((obs, oldValue, newValue) -> {
            updateTienTraLai();
        });

        // Function to update txtTienTraLai
        Runnable updateTienTraLai = () -> {
            try {
                String khachDuaText = txtTienKhachDua.getText().replaceAll("[^0-9]", "");
                BigDecimal khachDua = new BigDecimal(khachDuaText.isEmpty() ? "0" : khachDuaText);

                String thanhTienText = txtThanhTien.getText().replaceAll("[^0-9]", "");
                BigDecimal thanhTien = new BigDecimal(thanhTienText.isEmpty() ? "0" : thanhTienText);

                BigDecimal traLai = khachDua.subtract(thanhTien);
                txtTienTraLai.setText(currencyFormatter.format(traLai));
            } catch (NumberFormatException e) {
                txtTienTraLai.setText(currencyFormatter.format(BigDecimal.ZERO));
            }
        };

        // Initial update
        updateDenominations.run();
    	// Set up TabPane listener for payment method
        tabPanePhuongThucThanhToan.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == tabTienMat) {
                selectedPhuongThucThanhToan = (PhuongThucThanhToan.tienMat);
            } else if (newTab == tabATM) {
                selectedPhuongThucThanhToan = (PhuongThucThanhToan.atm);
            } else if (newTab == tabInternetBanking) {
                selectedPhuongThucThanhToan = (PhuongThucThanhToan.internetBanking);
            }
            // Debug log to verify selection
            System.out.println("Selected PhuongThucThanhToan: " + selectedPhuongThucThanhToan);
        });

        // Ensure default selection
        tabPanePhuongThucThanhToan.getSelectionModel().select(tabTienMat);
    	
        txtGiamGia.setEditable(false);
        txtThanhTien.setEditable(false);
        loadKhuyenMaiVaoComboBox();
        cboLoaiKhachHang.setValue(LoaiThanhVien.thanThiet);
        txtMaKhachHang.setEditable(false);

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

        updateCboLoaiKhachHangItems();
        cboLoaiKhachHang.setEditable(false);
        cboLoaiKhachHang.setDisable(txtMaKhachHang.getText().trim().isEmpty());

        ckcKhachVangLai.selectedProperty().addListener((obs, oldValue, newValue) -> {
            updateCboLoaiKhachHangItems();
            if (newValue) {
                cboLoaiKhachHang.setValue(LoaiThanhVien.khachVangLai);
            } else {
                cboLoaiKhachHang.setValue(LoaiThanhVien.thanThiet);
            }
            btnThemKhachHang.setDisable(newValue);
            btnCapNhatKhachHang.setDisable(newValue);
            updateDiscountAndPrice();
        });

        txtMaKhachHang.textProperty().addListener((obs, oldValue, newValue) -> {
            boolean isEmpty = newValue.trim().isEmpty();
            ckcKhachVangLai.setDisable(!isEmpty);
            cboLoaiKhachHang.setDisable(isEmpty);
        });

        cboKhuyenMai.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> updateDiscountAndPrice());
        cboLoaiKhachHang.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> updateDiscountAndPrice());

        setupTableSelectionListener();

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

        try {
            initializeTicketPanes();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        updateDiscountAndPrice();
    }

    private BigDecimal updateDiscountAndPrice() {
        txtGiamGia.setText("0%");
        txtThanhTien.setText("0 VNĐ");

        if (danhSachVeXacNhan == null || danhSachVeXacNhan.isEmpty()) {
            return BigDecimal.ZERO;
        }

        try {
            // Calculate total ticket price using BigDecimal
            BigDecimal tongGia = danhSachVeXacNhan.stream()
                    .filter(ve -> ve != null)
                    .map(ve -> {
                        try {
                            // Assuming getGiaTien() returns double; convert to BigDecimal
                            return new BigDecimal(String.valueOf(ve.getGiaTien()));
                        } catch (Exception e) {
                            return BigDecimal.ZERO;
                        }
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Get promotion discount
            double khuyenMaiPercent = 0.0;
            KhuyenMai selectedKhuyenMai = cboKhuyenMai.getValue();
            if (selectedKhuyenMai != null) {
                khuyenMaiPercent = selectedKhuyenMai.getPhanTramGiamGia();
            }

            // Get customer type discount
            double loaiKhachHangPercent = 0.0;
            LoaiThanhVien loaiThanhVien = cboLoaiKhachHang.getValue();
            if (loaiThanhVien != null && !ckcKhachVangLai.isSelected()) {
                if (loaiThanhVien == LoaiThanhVien.thanThiet) {
                    loaiKhachHangPercent = 5.0;
                } else if (loaiThanhVien == LoaiThanhVien.vip) {
                    loaiKhachHangPercent = 10.0;
                }
            }

            // Calculate total discount percentage
            double tongGiamGiaPercent = khuyenMaiPercent + loaiKhachHangPercent;

            // Update txtGiamGia
            String giamGiaText;
            if (tongGiamGiaPercent == 0) {
                giamGiaText = "0%";
            } else if (khuyenMaiPercent == 0) {
                giamGiaText = String.format("%.0f%% = %.0f%%", loaiKhachHangPercent, tongGiamGiaPercent);
            } else if (loaiKhachHangPercent == 0) {
                giamGiaText = String.format("%.0f%% = %.0f%%", khuyenMaiPercent, tongGiamGiaPercent);
            } else {
                giamGiaText = String.format("%.0f%% + %.0f%% = %.0f%%", khuyenMaiPercent, loaiKhachHangPercent, tongGiamGiaPercent);
            }
            txtGiamGia.setText(giamGiaText);

            // Calculate discount amount and final price using BigDecimal
            BigDecimal discountPercent = new BigDecimal(tongGiamGiaPercent).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
            BigDecimal discountAmount = tongGia.multiply(discountPercent).setScale(2, RoundingMode.HALF_UP);
            BigDecimal thanhTien = tongGia.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);

            // Update txtThanhTien
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            txtThanhTien.setText(currencyFormatter.format(thanhTien));

            // Return final price
            return thanhTien;
        } catch (Exception e) {
            e.printStackTrace();
            showWarningAlert("Lỗi khi tính toán giá vé!", "image/canhBao.png");
            return BigDecimal.ZERO;
        }
    }

	private void initializeTicketPanes() throws SQLException {
	    pnGioVe.getChildren().clear();
	    if (danhSachVeXacNhan == null || danhSachVeXacNhan.isEmpty()) {
	        pnGioVe.setPrefHeight(0);
	        return;
	    }
	
	    double paneHeight = 180.0;
	    double spacing = 10.0;
	    double padding = 10.0;
	    double yOffset = padding;
	    int ticketCount = danhSachVeXacNhan.size();
	
	    double totalHeight = (ticketCount * paneHeight) + ((ticketCount - 1) * spacing) + (2 * padding);
	    pnGioVe.setPrefHeight(totalHeight);
	
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
	
	    for (int i = 0; i < danhSachVeXacNhan.size(); i++) {
	        VeTam ve = danhSachVeXacNhan.get(i);
	        Tau_DAO tau_DAO = new Tau_DAO();
	        ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO();
	        Toa_DAO toa_dao = new Toa_DAO();
	        Ga_DAO ga_DAO = new Ga_DAO();
	        ChoNgoi_DAO choNgoi_DAO = new ChoNgoi_DAO();
	        TuyenTau_DAO tuyenTau_DAO = new TuyenTau_DAO();
	
	        ChoNgoi choNgoi = choNgoi_DAO.timChoNgoiTheoMaChoNgoi(ve.getMaChoNgoi());
	        ChuyenTau chuyenTau = chuyenTau_DAO.timChuyenTauTheoMaChuyenTau(choNgoi.getMaChuyenTau());
	        TuyenTau tuyenTau = tuyenTau_DAO.timTuyenTauTheoMaTuyen(chuyenTau.getMaTuyen());
	        Tau tau = tau_DAO.timTauTheoMa(chuyenTau.getMaTau());
	        Toa toa = toa_dao.getToaByMaToa(choNgoi.getMaToa());
	
	        String gaDi = ga_DAO.timTenGaTheoMa(tuyenTau.getGaDi().getMaGa());
	        String gaDen = ga_DAO.timTenGaTheoMa(tuyenTau.getGaDen().getMaGa());
	        String ngayKhoiHanh = chuyenTau.getNgayKhoiHanh().format(dateFormatter);
	        String gioKhoiHanh = chuyenTau.getGioKhoiHanh().format(timeFormatter);
	        String tenTau = tau.getTenTau();
	        String tenToa = toa.getTenToa();
	        String loaiToa = loaiToaToString(toa.getLoaiToa());
	        String loaiKhachHang = loaiKhachHangToString(ve.getLoaiKhachHang());
	        String tenCho = choNgoi.getTenChoNgoi();
	        String giaCho = currencyFormatter.format(ve.getGiaTien());
	
	        AnchorPane ticketPane = new AnchorPane();
	        ticketPane.setPrefHeight(paneHeight);
	        ticketPane.setPrefWidth(pnGioVe.getPrefWidth() - 20);
	        ticketPane.setLayoutX(10.0);
	        ticketPane.setLayoutY(yOffset);
	        ticketPane.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-background-color: #f9f9f9;");
	
	        double leftX = 10.0;
	        double rightMargin = 10.0;
	        double rowHeight = paneHeight / 8;
	        double labelYBase = 2.0;
	
	        Label gaDiLabel = new Label("Ga đi: " + gaDi);
	        gaDiLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333;");
	        AnchorPane.setLeftAnchor(gaDiLabel, leftX);
	        AnchorPane.setTopAnchor(gaDiLabel, labelYBase);
	
	        Label gaDenLabel = new Label("Ga đến: " + gaDen);
	        gaDenLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333;");
	        AnchorPane.setRightAnchor(gaDenLabel, rightMargin);
	        AnchorPane.setTopAnchor(gaDenLabel, labelYBase);
	
	        Label ngayKhoiHanhLabel = new Label("Ngày khởi hành: " + ngayKhoiHanh);
	        ngayKhoiHanhLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333;");
	        AnchorPane.setLeftAnchor(ngayKhoiHanhLabel, leftX);
	        AnchorPane.setTopAnchor(ngayKhoiHanhLabel, rowHeight + labelYBase);
	
	        Label gioKhoiHanhLabel = new Label("Giờ: " + gioKhoiHanh);
	        gioKhoiHanhLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333;");
	        AnchorPane.setRightAnchor(gioKhoiHanhLabel, rightMargin);
	        AnchorPane.setTopAnchor(gioKhoiHanhLabel, rowHeight + labelYBase);
	
	        Label tenTauLabel = new Label("Tàu: " + tenTau);
	        tenTauLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333;");
	        AnchorPane.setLeftAnchor(tenTauLabel, leftX);
	        AnchorPane.setTopAnchor(tenTauLabel, 2 * rowHeight + labelYBase);
	
	        Label tenToaLabel = new Label("Toa: " + tenToa);
	        tenToaLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333;");
	        AnchorPane.setRightAnchor(tenToaLabel, rightMargin);
	        AnchorPane.setTopAnchor(tenToaLabel, 2 * rowHeight + labelYBase);
	
	        Label loaiToaLabel = new Label("Loại: " + loaiToa);
	        loaiToaLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333;");
	        AnchorPane.setLeftAnchor(loaiToaLabel, leftX);
	        AnchorPane.setTopAnchor(loaiToaLabel, 3 * rowHeight + labelYBase);
	
	        Label loaiKhachHangLabel = new Label("Loại khách hàng: " + loaiKhachHang);
	        loaiKhachHangLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333;");
	        AnchorPane.setRightAnchor(loaiKhachHangLabel, rightMargin);
	        AnchorPane.setTopAnchor(loaiKhachHangLabel, 3 * rowHeight + labelYBase);
	
	        Label tenChoLabel = new Label("Chỗ: " + tenCho);
	        tenChoLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333;");
	        AnchorPane.setLeftAnchor(tenChoLabel, leftX);
	        AnchorPane.setTopAnchor(tenChoLabel, 4 * rowHeight + labelYBase);
	
	        Label giaChoLabel = new Label("Giá: " + giaCho);
	        giaChoLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333;");
	        AnchorPane.setRightAnchor(giaChoLabel, rightMargin);
	        AnchorPane.setTopAnchor(giaChoLabel, 4 * rowHeight + labelYBase);
	
	        double fieldWidth = (ticketPane.getPrefWidth() - 40) / 3;
	        double fieldSpacing = 10.0;
	
	        TextField txtTenKhachHangVe = new TextField();
	        txtTenKhachHangVe.setPromptText("Tên khách hàng");
	        txtTenKhachHangVe.setStyle("-fx-font-size: 12px;");
	        txtTenKhachHangVe.setPrefWidth(fieldWidth);
	        txtTenKhachHangVe.setId("tenKhachHang_" + i); // Use setId instead of setUserData
	        AnchorPane.setLeftAnchor(txtTenKhachHangVe, leftX);
	        AnchorPane.setTopAnchor(txtTenKhachHangVe, 5 * rowHeight + labelYBase);
	
	        DatePicker dpNgaySinhVe = new DatePicker();
	        dpNgaySinhVe.setPromptText("Ngày sinh");
	        dpNgaySinhVe.setStyle("-fx-font-size: 12px;");
	        dpNgaySinhVe.setPrefWidth(fieldWidth);
	        dpNgaySinhVe.setId("ngaySinh_" + i); // Use setId
	        AnchorPane.setLeftAnchor(dpNgaySinhVe, leftX + fieldWidth + fieldSpacing);
	        AnchorPane.setTopAnchor(dpNgaySinhVe, 5 * rowHeight + labelYBase);
	
	        TextField txtCCCDVe = new TextField();
	        txtCCCDVe.setPromptText("CCCD/Hộ chiếu");
	        txtCCCDVe.setStyle("-fx-font-size: 12px;");
	        txtCCCDVe.setPrefWidth(fieldWidth);
	        txtCCCDVe.setVisible(!loaiKhachHang.equals("Trẻ em"));
	        txtCCCDVe.setManaged(!loaiKhachHang.equals("Trẻ em"));
	        txtCCCDVe.setId("cccd_" + i); // Use setId
	        AnchorPane.setLeftAnchor(txtCCCDVe, leftX + 2 * (fieldWidth + fieldSpacing));
	        AnchorPane.setTopAnchor(txtCCCDVe, 5 * rowHeight + labelYBase);
	
	        Label errorLabel = new Label();
	        errorLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #ff0000;");
	        AnchorPane.setLeftAnchor(errorLabel, leftX);
	        AnchorPane.setTopAnchor(errorLabel, 6 * rowHeight + labelYBase);
	        errorLabel.setVisible(false);
	
	        String regexTen = "^[A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*(?: [A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*)+$";
	        txtTenKhachHangVe.focusedProperty().addListener((obs, oldValue, newValue) -> {
	            if (!newValue) {
	                String ten = txtTenKhachHangVe.getText().trim();
	                if (ten.isEmpty()) {
	                    errorLabel.setText("Tên khách hàng không hợp lệ!");
	                    errorLabel.setVisible(true);
	                } else {
	                    Pattern pt = Pattern.compile(regexTen);
	                    Matcher mc = pt.matcher(ten);
	                    if (!mc.matches()) {
	                        errorLabel.setText("Tên khách hàng không hợp lệ!");
	                        errorLabel.setVisible(true);
	                    } else {
	                        errorLabel.setVisible(false);
	                    }
	                }
	            }
	        });
	
	        dpNgaySinhVe.focusedProperty().addListener((obs, oldValue, newValue) -> {
	            if (!newValue) {
	                LocalDate ngaySinh = dpNgaySinhVe.getValue();
	                if (ngaySinh == null) {
	                    errorLabel.setText("Ngày sinh không hợp lệ!");
	                    errorLabel.setVisible(true);
	                } else {
	                    errorLabel.setVisible(false);
	                }
	            }
	        });
	
	        String regexCCCD = "^(\\d{12}|[A-Z]\\d{7})$";
	        txtCCCDVe.focusedProperty().addListener((obs, oldValue, newValue) -> {
	            if (!newValue && txtCCCDVe.isVisible()) {
	                String cccd = txtCCCDVe.getText().trim();
	                if (cccd.isEmpty()) {
	                    errorLabel.setText("CCCD/Hộ chiếu không hợp lệ!");
	                    errorLabel.setVisible(true);
	                } else {
	                    Pattern pt = Pattern.compile(regexCCCD);
	                    Matcher mc = pt.matcher(cccd);
	                    if (!mc.matches()) {
	                        errorLabel.setText("CCCD/Hộ chiếu không hợp lệ!");
	                        errorLabel.setVisible(true);
	                    } else {
	                        errorLabel.setVisible(false);
	                    }
	                }
	            }
	        });
	
	        ticketPane.getChildren().addAll(
	                gaDiLabel, gaDenLabel,
	                ngayKhoiHanhLabel, gioKhoiHanhLabel,
	                tenTauLabel, tenToaLabel,
	                loaiToaLabel, loaiKhachHangLabel,
	                tenChoLabel, giaChoLabel,
	                txtTenKhachHangVe, dpNgaySinhVe, txtCCCDVe,
	                errorLabel
	        );
	
	        pnGioVe.getChildren().add(ticketPane);
	
	        yOffset += paneHeight + spacing;
	    }
	}
    
	private String loaiToaToString(LoaiToa loaiToa) {
        if (loaiToa == null) return "Không xác định";
        switch (loaiToa) {
            case giuongNamDieuHoa: return "Giường nằm điều hòa";
            case ngoiMemDieuHoa: return "Ngồi mềm điều hòa";
            case gheCungDieuHoa: return "Ghế cứng điều hòa";
            default: return loaiToa.toString();
        }
    }

    private String loaiKhachHangToString(LoaiKhachHang loaiKhachHang) {
        if (loaiKhachHang == null) return "Không xác định";
        switch (loaiKhachHang) {
            case nguoiLon: return "Người lớn";
            case treEm: return "Trẻ em";
            case sinhVien: return "Sinh viên";
            case nguoiCaoTuoi: return "Người cao tuổi";
            default: return loaiKhachHang.toString();
        }
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

    private void showSuccessAlert(String message, String icon) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
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
        updateDiscountAndPrice();
    }

    @FXML
    private void btnThemKhachHangClicked() {
        if (!txtMaKhachHang.getText().trim().isEmpty()) {
            showWarningAlert("Bạn đang chọn một khách hàng, vui lòng làm rỗng trước khi thêm!", "image/canhBao.png");
            return;
        }
        cboLoaiKhachHang.setDisable(false);
        if (kiemTraTxtKhachHang()) {
            KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
            String tenKhachHang = txtTenKhachHang.getText().trim();
            String soDienThoai = txtSoDienThoai.getText().trim();
            String email = txtEmail.getText().trim();
            String cccdHoChieu = txtCCCD_HoChieu.getText().trim();
            LoaiThanhVien loaiThanhVien = cboLoaiKhachHang.getValue();

            if (khachHang_DAO.kiemTraCCCD(cccdHoChieu)) {
                showWarningAlert("CCCD/Hộ chiếu đã tồn tại!", "image/canhBao.png");
                txtCCCD_HoChieu.requestFocus();
                txtCCCD_HoChieu.selectAll();
                cboLoaiKhachHang.setDisable(txtMaKhachHang.getText().trim().isEmpty());
                return;
            }

            String maKhachHang = khachHang_DAO.taoMaKhachHangMoi();
            KhachHang khachHang = new KhachHang(
                    maKhachHang,
                    tenKhachHang,
                    cccdHoChieu,
                    soDienThoai,
                    email,
                    loaiThanhVien,
                    KhachHang.TrangThaiKhachHang.hoatDong
            );

            if (khachHang_DAO.themKhachHang(khachHang)) {
                showSuccessAlert("Thêm khách hàng thành công!\nMã khách hàng: " + maKhachHang, "image/thanhCong.png");
                txtMaKhachHang.setText(maKhachHang);
                txtTenKhachHang.setText(tenKhachHang);
                txtSoDienThoai.setText(soDienThoai);
                txtEmail.setText(email);
                txtCCCD_HoChieu.setText(cccdHoChieu);
                cboLoaiKhachHang.setValue(loaiThanhVien);
                ckcKhachVangLai.setSelected(loaiThanhVien == LoaiThanhVien.khachVangLai);
                List<KhachHang> dsKhachHang = khachHang_DAO.timKhachHangTheoCCCD_HoChieu(cccdHoChieu);
                themDuLieuVaoTbDanhSachKhachHang(dsKhachHang);
            } else {
                showWarningAlert("Lỗi khi thêm khách hàng!", "image/canhBao.png");
            }
            cboLoaiKhachHang.setDisable(txtMaKhachHang.getText().trim().isEmpty());
        } else {
            cboLoaiKhachHang.setDisable(txtMaKhachHang.getText().trim().isEmpty());
        }
        updateDiscountAndPrice();
    }

    @FXML
    private void btnCapNhatKhachHangClicked() {
        if (txtMaKhachHang.getText().trim().isEmpty()) {
            showWarningAlert("Vui lòng chọn một khách hàng để cập nhật!", "image/canhBao.png");
            return;
        }
        if (kiemTraTxtKhachHang()) {
            KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
            String maKhachHang = txtMaKhachHang.getText().trim();
            String tenKhachHang = txtTenKhachHang.getText().trim();
            String soDienThoai = txtSoDienThoai.getText().trim();
            String email = txtEmail.getText().trim();
            String cccdHoChieu = txtCCCD_HoChieu.getText().trim();
            LoaiThanhVien loaiThanhVien = cboLoaiKhachHang.getValue();

            KhachHang existingKhachHang = khachHang_DAO.timKhachHangTheoMa(maKhachHang);
            if (!existingKhachHang.getCCCD_HoChieu().equals(cccdHoChieu) && khachHang_DAO.kiemTraCCCD(cccdHoChieu)) {
                showWarningAlert("CCCD/Hộ chiếu đã tồn tại!", "image/canhBao.png");
                txtCCCD_HoChieu.requestFocus();
                txtCCCD_HoChieu.selectAll();
                return;
            }

            KhachHang khachHang = new KhachHang(
                    maKhachHang,
                    tenKhachHang,
                    cccdHoChieu,
                    soDienThoai,
                    email,
                    loaiThanhVien,
                    existingKhachHang.getTrangThaiKhachHang()
            );

            if (khachHang_DAO.capNhatKhachHang(khachHang)) {
                showSuccessAlert("Cập nhật khách hàng thành công!", "image/thanhCong.png");
                List<KhachHang> dsKhachHang = khachHang_DAO.timKhachHangTheoTen(tenKhachHang);
                themDuLieuVaoTbDanhSachKhachHang(dsKhachHang);
            } else {
                showWarningAlert("Lỗi khi cập nhật khách hàng!", "image/canhBao.png");
            }
        }
        updateDiscountAndPrice();
    }

    private boolean kiemTraTxtKhachHang() {
        String tenKhachHang = txtTenKhachHang.getText();
        String soDienThoai = txtSoDienThoai.getText();
        String email = txtEmail.getText();
        String cccdHoChieu = txtCCCD_HoChieu.getText();

        String regexTen = "^[A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*(?: [A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*)+$";
        String regexCCCD_HoChieu = "^(\\d{12}|[A-Z]\\d{7})$";
        String regexSoDienThoai = "^(0|\\+84)(3|5|7|8|9)\\d{8}$";
        String regexEmail = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$";

        if (tenKhachHang.trim().isEmpty()) {
            showWarningAlert("Tên khách hàng không được rỗng!", "image/canhBao.png");
            txtTenKhachHang.requestFocus();
            return false;
        } else {
            Pattern pt = Pattern.compile(regexTen);
            Matcher mc = pt.matcher(tenKhachHang);
            if (!mc.matches()) {
                showWarningAlert("Tên khách hàng phải từ 2 tiếng trở lên, viết hoa chữ đầu!", "image/canhBao.png");
                txtTenKhachHang.requestFocus();
                txtTenKhachHang.selectAll();
                return false;
            }
        }

        if (soDienThoai.trim().isEmpty()) {
            showWarningAlert("Số điện thoại khách hàng không được rỗng!", "image/canhBao.png");
            txtSoDienThoai.requestFocus();
            return false;
        } else {
            Pattern pt = Pattern.compile(regexSoDienThoai);
            Matcher mc = pt.matcher(soDienThoai);
            if (!mc.matches()) {
                showWarningAlert("Số điện thoại khách hàng phải có 10 số, bắt đầu bằng 03|05|07|08|09 và 8 số còn lại!", "image/canhBao.png");
                txtSoDienThoai.requestFocus();
                txtSoDienThoai.selectAll();
                return false;
            }
        }

        if (email.trim().isEmpty()) {
            showWarningAlert("Email khách hàng không được rỗng!", "image/canhBao.png");
            txtEmail.requestFocus();
            return false;
        } else {
            Pattern pt = Pattern.compile(regexEmail);
            Matcher mc = pt.matcher(email);
            if (!mc.matches()) {
                showWarningAlert("Email khách hàng không đúng định dạng!", "image/canhBao.png");
                txtEmail.requestFocus();
                txtEmail.selectAll();
                return false;
            }
        }

        if (cccdHoChieu.trim().isEmpty()) {
            showWarningAlert("CCCD/Hộ chiếu khách hàng không được rỗng!", "image/canhBao.png");
            txtCCCD_HoChieu.requestFocus();
            return false;
        } else {
            Pattern pt = Pattern.compile(regexCCCD_HoChieu);
            Matcher mc = pt.matcher(cccdHoChieu);
            if (!mc.matches()) {
                showWarningAlert("CCCD phải có 12 số hoặc Hộ chiếu phải có 1 chữ cái in hoa theo sau 7 số!", "image/canhBao.png");
                txtCCCD_HoChieu.requestFocus();
                txtCCCD_HoChieu.selectAll();
                return false;
            }
        }

        return true;
    }

    private void setupTableSelectionListener() {
        tbDanhSachKhachHang.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtMaKhachHang.setText(newSelection.getMaKhachHang());
                txtTenKhachHang.setText(newSelection.getTenKhachHang());
                txtSoDienThoai.setText(newSelection.getSoDienThoai());
                txtEmail.setText(newSelection.getEmail());
                txtCCCD_HoChieu.setText(newSelection.getCCCD_HoChieu());
                boolean isKhachVangLai = newSelection.getLoaiThanhVien() == LoaiThanhVien.khachVangLai;
                ckcKhachVangLai.setSelected(isKhachVangLai);
                updateCboLoaiKhachHangItems();
                cboLoaiKhachHang.setValue(newSelection.getLoaiThanhVien());
            } else {
                txtMaKhachHang.setText("");
                txtTenKhachHang.setText("");
                txtSoDienThoai.setText("");
                txtEmail.setText("");
                txtCCCD_HoChieu.setText("");
                ckcKhachVangLai.setSelected(false);
                updateCboLoaiKhachHangItems();
                cboLoaiKhachHang.setValue(LoaiThanhVien.thanThiet);
            }
            updateDiscountAndPrice();
        });
    }
    
	@FXML
	private void btnBanVeClicked() {
	    // Check if there are tickets to process
	    if (danhSachVeXacNhan == null || danhSachVeXacNhan.isEmpty()) {
	        showWarningAlert("Không có vé nào để bán!", "image/canhBao.png");
	        return;
	    }
	
	    // Check if ckcKhachVangLai is not checked and txtMaKhachHang is empty
	    if (!ckcKhachVangLai.isSelected() && txtMaKhachHang.getText().trim().isEmpty()) {
	        showWarningAlert("Bạn chưa chọn khách hàng thanh toán hóa đơn!", "image/canhBao.png");
	        txtMaKhachHang.requestFocus();
	        return;
	    }
	
	    // Validate txtTienKhachDua vs txtThanhTien
	    try {
	        String khachDuaText = txtTienKhachDua.getText().replaceAll("[^0-9]", "");
	        BigDecimal khachDua = new BigDecimal(khachDuaText.isEmpty() ? "0" : khachDuaText);
	
	        String thanhTienText = txtThanhTien.getText().replaceAll("[^0-9]", "");
	        BigDecimal thanhTien = new BigDecimal(thanhTienText.isEmpty() ? "0" : thanhTienText);
	
	        if (khachDua.compareTo(thanhTien) < 0) {
	            showWarningAlert("Tiền khách đưa phải lớn hơn hoặc bằng thành tiền!", "image/canhBao.png");
	            txtTienKhachDua.requestFocus();
	            return;
	        }
	    } catch (NumberFormatException e) {
	        showWarningAlert("Vui lòng nhập số tiền hợp lệ!", "image/canhBao.png");
	        txtTienKhachDua.requestFocus();
	        return;
	    }
	
	    // Log the selected payment method (for debugging)
	    System.out.println("Payment method during sale: " + selectedPhuongThucThanhToan);
	
	    // Validation patterns
	    String regexTen = "^[A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*(?: [A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*)+$";
	    String regexCCCD = "^(\\d{12}|[A-Z]\\d{7})$";
	    LocalDate today = LocalDate.now();
	
	    // Validate each ticket pane
	    for (int i = 0; i < danhSachVeXacNhan.size(); i++) {
	        VeTam ve = danhSachVeXacNhan.get(i);
	        LoaiKhachHang loaiKhachHang = ve.getLoaiKhachHang();
	
	        if (i >= pnGioVe.getChildren().size()) {
	            showWarningAlert("Lỗi: Số lượng pane vé không khớp với danh sách vé!", "image/canhBao.png");
	            return;
	        }
	        AnchorPane ticketPane = (AnchorPane) pnGioVe.getChildren().get(i);
	
	        TextField txtTenKhachHangVe = (TextField) ticketPane.lookup("#tenKhachHang_" + i);
	        DatePicker dpNgaySinhVe = (DatePicker) ticketPane.lookup("#ngaySinh_" + i);
	        TextField txtCCCDVe = (TextField) ticketPane.lookup("#cccd_" + i);
	
	        System.out.println("Vé " + (i + 1) + ": txtTenKhachHangVe=" + (txtTenKhachHangVe != null ? txtTenKhachHangVe.getText() : "null") +
	                ", dpNgaySinhVe=" + (dpNgaySinhVe != null ? dpNgaySinhVe.getValue() : "null") +
	                ", txtCCCDVe=" + (txtCCCDVe != null ? txtCCCDVe.getText() : "null"));
	
	        if (txtTenKhachHangVe == null) {
	            showWarningAlert("Lỗi: Không tìm thấy trường tên cho vé " + (i + 1) + "!", "image/canhBao.png");
	            return;
	        }
	        String ten = txtTenKhachHangVe.getText() != null ? txtTenKhachHangVe.getText().trim() : "";
	        if (ten.isEmpty()) {
	            showWarningAlert("Tên khách hàng cho vé " + (i + 1) + " không được rỗng!", "image/canhBao.png");
	            txtTenKhachHangVe.requestFocus();
	            return;
	        }
	        Pattern ptTen = Pattern.compile(regexTen);
	        Matcher mcTen = ptTen.matcher(ten);
	        if (!mcTen.matches()) {
	            showWarningAlert("Tên khách hàng cho vé " + (i + 1) + " không hợp lệ! Phải từ 2 tiếng trở lên, viết hoa chữ đầu.", "image/canhBao.png");
	            txtTenKhachHangVe.requestFocus();
	            return;
	        }
	
	        if (dpNgaySinhVe == null) {
	            showWarningAlert("Lỗi: Không tìm thấy trường ngày sinh cho vé " + (i + 1) + "!", "image/canhBao.png");
	            return;
	        }
	        LocalDate ngaySinh = dpNgaySinhVe.getValue();
	        if (ngaySinh == null) {
	            showWarningAlert("Ngày sinh cho vé " + (i + 1) + " không được rỗng!", "image/canhBao.png");
	            dpNgaySinhVe.requestFocus();
	            return;
	        }
	
	        int age = Period.between(ngaySinh, today).getYears();
	
	        switch (loaiKhachHang) {
	            case treEm:
	                if (age < 6 || age > 10) {
	                    showWarningAlert("Vé " + (i + 1) + ": Trẻ em phải từ 6 đến 10 tuổi!", "image/canhBao.png");
	                    dpNgaySinhVe.requestFocus();
	                    return;
	                }
	                break;
	            case nguoiLon:
	                if (age < 11) {
	                    showWarningAlert("Vé " + (i + 1) + ": Người lớn phải từ 11 tuổi trở lên!", "image/canhBao.png");
	                    dpNgaySinhVe.requestFocus();
	                    return;
	                }
	                break;
	            case sinhVien:
	                if (age <= 18) {
	                    showWarningAlert("Vé " + (i + 1) + ": Sinh viên phải trên 18 tuổi!", "image/canhBao.png");
	                    dpNgaySinhVe.requestFocus();
	                    return;
	                }
	                break;
	            case nguoiCaoTuoi:
	                if (age < 60) {
	                    showWarningAlert("Vé " + (i + 1) + ": Người cao tuổi phải từ 60 tuổi trở lên!", "image/canhBao.png");
	                    dpNgaySinhVe.requestFocus();
	                    return;
	                }
	                break;
	            default:
	                showWarningAlert("Loại khách hàng không hợp lệ cho vé " + (i + 1) + "!", "image/canhBao.png");
	                return;
	        }
	
	        if (loaiKhachHang != LoaiKhachHang.treEm) {
	            if (txtCCCDVe == null) {
	                showWarningAlert("Lỗi: Không tìm thấy trường CCCD cho vé " + (i + 1) + "!", "image/canhBao.png");
	                return;
	            }
	            String cccd = txtCCCDVe.getText() != null ? txtCCCDVe.getText().trim() : "";
	            if (cccd.isEmpty()) {
	                showWarningAlert("CCCD/Hộ chiếu cho vé " + (i + 1) + " không được rỗng!", "image/canhBao.png");
	                txtCCCDVe.requestFocus();
	                return;
	            }
	            Pattern ptCCCD = Pattern.compile(regexCCCD);
	            Matcher mcCCCD = ptCCCD.matcher(cccd);
	            if (!mcCCCD.matches()) {
	                showWarningAlert("CCCD/Hộ chiếu cho vé " + (i + 1) + " không hợp lệ! Phải là 12 số hoặc 1 chữ cái in hoa theo sau 7 số.", "image/canhBao.png");
	                txtCCCDVe.requestFocus();
	                return;
	            }
	        }
	    }
	
	    // Calculate final price
	    BigDecimal finalPrice = updateDiscountAndPrice();
	    if (finalPrice == null || finalPrice.compareTo(BigDecimal.ZERO) <= 0) {
	        showWarningAlert("Không thể bán vé với giá 0 hoặc lỗi tính toán!", "image/canhBao.png");
	        return;
	    }
	
	    // TODO: Proceed with ticket sale logic (e.g., save to database, update ChoNgoi status)
	    showSuccessAlert("Bán vé thành công! Tổng tiền: " + NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(finalPrice), "image/thanhCong.png");
	}
	
	@FXML
	private void btnQuayLaiClicked() {
        Stage stage = (Stage) btnQuayLai.getScene().getWindow();
        stage.close();
    }

    private void loadKhuyenMaiVaoComboBox() {
        LocalDate today = LocalDate.now();
        KhuyenMai_DAO khuyenMaiDAO = new KhuyenMai_DAO();
        List<KhuyenMai> danhSachKhuyenMai = khuyenMaiDAO.layDanhSachKhuyenMai();

        List<KhuyenMai> khuyenMaiHople = danhSachKhuyenMai.stream()
                .filter(km -> !today.isBefore(km.getNgayBatDau()) && !today.isAfter(km.getNgayKetThuc()))
                .sorted(Comparator.comparingDouble(KhuyenMai::getPhanTramGiamGia).reversed())
                .collect(Collectors.toList());

        cboKhuyenMai.setItems(FXCollections.observableArrayList(khuyenMaiHople));

        cboKhuyenMai.setCellFactory(new Callback<ListView<KhuyenMai>, ListCell<KhuyenMai>>() {
            @Override
            public ListCell<KhuyenMai> call(ListView<KhuyenMai> param) {
                return new ListCell<KhuyenMai>() {
                    @Override
                    protected void updateItem(KhuyenMai item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getTenKhuyenMai() + " - Giảm " + item.getPhanTramGiamGia() + "%");
                        }
                    }
                };
            }
        });

        cboKhuyenMai.setButtonCell(new ListCell<KhuyenMai>() {
            @Override
            protected void updateItem(KhuyenMai item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Không áp dụng khuyến mãi");
                } else {
                    setText(item.getTenKhuyenMai() + " - Giảm " + item.getPhanTramGiamGia() + "%");
                }
            }
        });

        if (!khuyenMaiHople.isEmpty()) {
            cboKhuyenMai.setValue(khuyenMaiHople.get(0));
        } else {
            cboKhuyenMai.setValue(null);
        }
    }
    
    @FXML
    private TabPane tabPanePhuongThucThanhToan;

    @FXML
    private Tab tabTienMat;

    @FXML
    private Tab tabATM;

    @FXML
    private Tab tabInternetBanking;

    // Variable to store the selected payment method
    private PhuongThucThanhToan selectedPhuongThucThanhToan = PhuongThucThanhToan.tienMat;
    
    @FXML
    private TextField txtTienKhachDua;

    @FXML
    private TextField txtTienTraLai;

    @FXML
    private Button btnGia1;

    @FXML
    private Button btnGia2;

    @FXML
    private Button btnGia3;

    @FXML
    private Button btnGia4;

    @FXML
    private Button btnGia5;

    @FXML
    private Button btnGia6;

    @FXML
    private Button btnGia7;

    @FXML
    private Button btnGia8;

    @FXML
    private Button btnGia9;
}