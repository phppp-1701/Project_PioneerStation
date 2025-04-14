package gui;
//
//import dao.Ve_DAO;
//import entity.Ve;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.stage.Stage;
//import javafx.scene.image.Image;
//
//import java.io.File;
//import java.time.LocalDate;
//import java.util.List;
//
public class QuanLyVe_GUI_Controller {
//
//    @FXML
//    private TextField txtTen;
//    @FXML
//    private Button btnTim;
//
//    @FXML
//    private TableView<VeTau> tbDanhSachVe;
//
//    @FXML
//    private TableColumn<VeTau, String> colStt, colMaVe, colMaChuyen, colTenKhach, colTenNhanVien,
//            colToa, colGhe, colGaDi, colGaDen, colGioKhoiHanh, colGiaThanh, colNgayTao;
//
//    @FXML
//    private void initialize() {
//        btnTim.setOnAction(e -> timVeTheoTen());
//    }
//
//    private void timVeTheoTen() {
//        String ten = txtTen.getText().trim();
//        if (ten.isEmpty()) {
//            hienThiThongBao("Thông báo", "Vui lòng nhập tên khách hàng để tìm kiếm!", Alert.AlertType.WARNING);
//            return;
//        }
//
//        try {
//            List<VeTau> danhSach = new VeTau_DAO().timVeTheoTen(ten);
//            tbDanhSachVe.getItems().clear();
//
//            if (danhSach.isEmpty()) {
//                hienThiThongBao("Thông báo", "Không tìm thấy vé nào phù hợp!", Alert.AlertType.INFORMATION);
//                return;
//            }
//
//            tbDanhSachVe.getItems().addAll(danhSach);
//
//            // Gán giá trị cho từng cột trong bảng
//            colStt.setCellValueFactory(cell -> new SimpleStringProperty(
//                    String.valueOf(tbDanhSachVe.getItems().indexOf(cell.getValue()) + 1)));
//            colMaVe.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMaVe()));
//            colMaChuyen.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMaChuyenTau()));
//            colTenKhach.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTenKhachHang()));
//            colTenNhanVien.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTenNhanVien()));
//            colToa.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getToa()));
//            colGhe.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGhe()));
//            colGaDi.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGaDi()));
//            colGaDen.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGaDen()));
//            colGioKhoiHanh.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGioKhoiHanh()));
//            colGiaThanh.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getGiaTien())));
//            colNgayTao.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getNgayTao())));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            hienThiThongBao("Lỗi", "Đã xảy ra lỗi khi tìm vé!", Alert.AlertType.ERROR);
//        }
//    }
//
//    private void hienThiThongBao(String tieuDe, String noiDung, Alert.AlertType loai) {
//        Alert alert = new Alert(loai);
//        alert.setTitle(tieuDe);
//        alert.setHeaderText(null);
//        alert.setContentText(noiDung);
//
//        File file = new File("image/canhBao.png");
//        if (file.exists()) {
//            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//            stage.getIcons().add(new Image(file.toURI().toString()));
//        }
//
//        alert.showAndWait();
//    }
}
