package dao;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import connectDB.ConnectDB;
//import entity.Ve;

public class Ve_DAO {
//	public List<Ve> timVeTheoTen(String ten) {
//	    List<Ve> ds = new ArrayList<>();
//	    String sql = "SELECT * FROM Ve WHERE tenKhachHang LIKE ?";
//
//	    try (Connection con = ConnectDB.getConnection();
//	         PreparedStatement ps = con.prepareStatement(sql)) {
//
//	        ps.setString(1, "%" + ten + "%");
//	        ResultSet rs = ps.executeQuery();
//
//	        while (rs.next()) {
//	            Ve v = new Ve();
//	            v.setMaVe(rs.getString("maVe"));
//	            v.setTenKhachHang(rs.getString("tenKhachHang"));
//	            v.setCCCD_HoChieu(rs.getString("CCCD_HoChieu"));
//	            v.setLoaiKhachHang(rs.getString("loaiKhachHang"));
//	            v.setTrangThaiVe(rs.getString("trangThaiVe"));
//	            v.setMaChuyenTau(rs.getString("loaiKhachHang"));
//	            v.setToa(rs.getString("toa"));
//	            v.setChoNgoi(rs.getString("choNgoi"));
//	            v.setGaDi(rs.getString("gaDi"));
//	            v.setGaDen(rs.getString("gaDen"));
//	            v.setGioKhoiHanh(rs.getString("gioKhoiHanh"));
//	            v.setLoaiVe(rs.getString("loaiVe"));
//	            v.setNgayTaoVe(rs.getDate("ngayTaoVe").toLocalDate());
//	            v.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
//	            v.setNgayKhoiHanh(rs.getDate("ngayKhoiHanh").toLocalDate());
//	            v.setGiaVe(rs.getDouble("giaVe"));
//	            v.setMaHoaDon(rs.getString("maHoaDon"));
//	            ds.add(v);
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    return ds;
//	}

}
