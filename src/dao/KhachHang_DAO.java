package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhachHang;
import entity.KhachHang.LoaiThanhVien;
import entity.KhachHang.TrangThaiKhachHang;

public class KhachHang_DAO {
	//Xuat toan bo danh sach
    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setTenKhachHang(rs.getString("tenKhachHang"));
                kh.setCCCD_HoChieu(rs.getString("CCCD_HoChieu"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                
                // Xử lý enum LoaiThanhVien
                String loaiTV = rs.getString("loaiThanhVien");
                kh.setLoaiThanhVien("vip".equals(loaiTV) ? LoaiThanhVien.vip : LoaiThanhVien.thanThiet);
                
                // Xử lý enum TrangThaiKhachHang
                String trangThai = rs.getString("trangThaiKhachHang");
                kh.setTrangThaiKhachHang("voHieuHoa".equals(trangThai) 
                    ? TrangThaiKhachHang.voHieuHoa 
                    : TrangThaiKhachHang.hoatDong);
                
                dsKhachHang.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return dsKhachHang;
    }
    
    //Tim theo so dien thoai
    public List<KhachHang> timKhachHangTheoSoDienThoai(String soDienThoai) {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE soDienThoai LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + soDienThoai + "%"); // Tìm kiếm gần đúng
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setTenKhachHang(rs.getString("tenKhachHang"));
                kh.setCCCD_HoChieu(rs.getString("CCCD_HoChieu"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                
                // Xử lý enum LoaiThanhVien
                String loaiTV = rs.getString("loaiThanhVien");
                kh.setLoaiThanhVien("vip".equals(loaiTV) ? LoaiThanhVien.vip : LoaiThanhVien.thanThiet);
                
                // Xử lý enum TrangThaiKhachHang
                String trangThai = rs.getString("trangThaiKhachHang");
                kh.setTrangThaiKhachHang("voHieuHoa".equals(trangThai) 
                    ? TrangThaiKhachHang.voHieuHoa 
                    : TrangThaiKhachHang.hoatDong);
                
                dsKhachHang.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return dsKhachHang;
    } 
    
    //Tim khach hang theo ten
    public List<KhachHang> timKhachHangTheoTen(String tenKhachHang) {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE tenKhachHang LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + tenKhachHang + "%"); // Tìm kiếm gần đúng
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setTenKhachHang(rs.getString("tenKhachHang"));
                kh.setCCCD_HoChieu(rs.getString("CCCD_HoChieu"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                
                // Xử lý enum LoaiThanhVien
                String loaiTV = rs.getString("loaiThanhVien");
                kh.setLoaiThanhVien("vip".equals(loaiTV) ? LoaiThanhVien.vip : LoaiThanhVien.thanThiet);
                
                // Xử lý enum TrangThaiKhachHang
                String trangThai = rs.getString("trangThaiKhachHang");
                kh.setTrangThaiKhachHang("voHieuHoa".equals(trangThai) 
                    ? TrangThaiKhachHang.voHieuHoa 
                    : TrangThaiKhachHang.hoatDong);
                
                dsKhachHang.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return dsKhachHang;
    }
    
  //Tim khach hang theo ten
    public List<KhachHang> timKhachHangTheoCCCD_HoChieu(String CCCD_HoChieu) {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE CCCD_HoChieu LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + CCCD_HoChieu + "%"); // Tìm kiếm gần đúng
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setTenKhachHang(rs.getString("tenKhachHang"));
                kh.setCCCD_HoChieu(rs.getString("CCCD_HoChieu"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                
                // Xử lý enum LoaiThanhVien
                String loaiTV = rs.getString("loaiThanhVien");
                kh.setLoaiThanhVien("vip".equals(loaiTV) ? LoaiThanhVien.vip : LoaiThanhVien.thanThiet);
                
                // Xử lý enum TrangThaiKhachHang
                String trangThai = rs.getString("trangThaiKhachHang");
                kh.setTrangThaiKhachHang("voHieuHoa".equals(trangThai) 
                    ? TrangThaiKhachHang.voHieuHoa 
                    : TrangThaiKhachHang.hoatDong);
                
                dsKhachHang.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return dsKhachHang;
    }
    
    public List<KhachHang> timKhachHangTheoTenVaSoDienThoai(String tenKhachHang, String soDienThoai) {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE tenKhachHang LIKE ? AND soDienThoai LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + tenKhachHang + "%");
            stmt.setString(2, "%" + soDienThoai + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setTenKhachHang(rs.getString("tenKhachHang"));
                kh.setCCCD_HoChieu(rs.getString("CCCD_HoChieu"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                
                // Xử lý enum LoaiThanhVien
                String loaiTV = rs.getString("loaiThanhVien");
                kh.setLoaiThanhVien("vip".equals(loaiTV) ? LoaiThanhVien.vip : LoaiThanhVien.thanThiet);
                
                // Xử lý enum TrangThaiKhachHang
                String trangThai = rs.getString("trangThaiKhachHang");
                kh.setTrangThaiKhachHang("voHieuHoa".equals(trangThai) 
                    ? TrangThaiKhachHang.voHieuHoa 
                    : TrangThaiKhachHang.hoatDong);
                
                dsKhachHang.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return dsKhachHang;
    }
    
    //Them khach hang
    public boolean themKhachHang(KhachHang kh) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;

        try {
            con = ConnectDB.getConnection(); // Lấy kết nối từ lớp ConnectDB
            if (con == null) {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu");
            }

            String sql = "INSERT INTO dbo.KhachHang (maKhachHang, tenKhachHang, CCCD_HoChieu, soDienThoai, email, loaiThanhVien, trangThaiKhachHang) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);

            // Gán giá trị cho các tham số
            stmt.setString(1, kh.getMaKhachHang());
            stmt.setString(2, kh.getTenKhachHang());
            stmt.setString(3, kh.getCCCD_HoChieu());
            stmt.setString(4, kh.getSoDienThoai());
            stmt.setString(5, kh.getEmail());

            // Ánh xạ LoaiThanhVien sang giá trị cơ sở dữ liệu
            String loaiThanhVien;
            if (kh.getLoaiThanhVien() == KhachHang.LoaiThanhVien.thanThiet) {
                loaiThanhVien = "thanThiet";
            } else if (kh.getLoaiThanhVien() == KhachHang.LoaiThanhVien.vip) {
                loaiThanhVien = "vip";
            } else {
                throw new IllegalArgumentException("Loại thành viên không hợp lệ");
            }
            stmt.setString(6, loaiThanhVien);

            // Ánh xạ TrangThaiKhachHang sang giá trị cơ sở dữ liệu
            String trangThaiKhachHang;
            if (kh.getTrangThaiKhachHang() == KhachHang.TrangThaiKhachHang.hoatDong) {
                trangThaiKhachHang = "hoatDong";
            } else if (kh.getTrangThaiKhachHang() == KhachHang.TrangThaiKhachHang.voHieuHoa) {
                trangThaiKhachHang = "voHieuHoa";
            } else {
                throw new IllegalArgumentException("Trạng thái khách hàng không hợp lệ");
            }
            stmt.setString(7, trangThaiKhachHang);

            // Thực thi câu lệnh INSERT
            int rowsAffected = stmt.executeUpdate();
            thanhCong = rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            thanhCong = false;
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi dữ liệu: " + e.getMessage());
            thanhCong = false;
        } finally {
            // Đóng PreparedStatement và Connection
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return thanhCong;
    }
    
    //Cap nhat khach hang
    public boolean capNhatKhachHang(KhachHang khachHang) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE KhachHang SET tenKhachHang = ?, CCCD_HoChieu = ?, soDienThoai = ?, "
                       + "email = ?, loaiThanhVien = ?, trangThaiKhachHang = ? WHERE maKhachHang = ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setString(1, khachHang.getTenKhachHang());
            stmt.setString(2, khachHang.getCCCD_HoChieu());
            stmt.setString(3, khachHang.getSoDienThoai());
            stmt.setString(4, khachHang.getEmail());
         // Ánh xạ LoaiThanhVien sang giá trị cơ sở dữ liệu
            String loaiThanhVien;
            if (khachHang.getLoaiThanhVien() == KhachHang.LoaiThanhVien.thanThiet) {
                loaiThanhVien = "thanThiet";
            } else if (khachHang.getLoaiThanhVien() == KhachHang.LoaiThanhVien.vip) {
                loaiThanhVien = "vip";
            } else {
                throw new IllegalArgumentException("Loại thành viên không hợp lệ");
            }
            stmt.setString(5, loaiThanhVien);

            // Ánh xạ TrangThaiKhachHang sang giá trị cơ sở dữ liệu
            String trangThaiKhachHang;
            if (khachHang.getTrangThaiKhachHang() == KhachHang.TrangThaiKhachHang.hoatDong) {
                trangThaiKhachHang = "hoatDong";
            } else if (khachHang.getTrangThaiKhachHang() == KhachHang.TrangThaiKhachHang.voHieuHoa) {
                trangThaiKhachHang = "voHieuHoa";
            } else {
                throw new IllegalArgumentException("Trạng thái khách hàng không hợp lệ");
            }
            stmt.setString(6, trangThaiKhachHang);
            stmt.setString(7, khachHang.getMaKhachHang());
            
            int rowsAffected = stmt.executeUpdate();
            thanhCong = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return thanhCong;
    }

    // Phương thức kiểm tra trùng CCCD/Hộ chiếu
    public boolean kiemTraCCCD(String cccd) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM KhachHang WHERE CCCD_HoChieu = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, cccd);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public KhachHang timKhachHangTheoMa(String maKhachHang) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        KhachHang kh = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maKhachHang);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("maKhachHang"));
                kh.setTenKhachHang(rs.getString("tenKhachHang"));
                kh.setCCCD_HoChieu(rs.getString("CCCD_HoChieu"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                
                // Xử lý enum LoaiThanhVien
                String loaiTV = rs.getString("loaiThanhVien");
                kh.setLoaiThanhVien("vip".equals(loaiTV) ? LoaiThanhVien.vip : LoaiThanhVien.thanThiet);
                
                // Xử lý enum TrangThaiKhachHang
                String trangThai = rs.getString("trangThaiKhachHang");
                kh.setTrangThaiKhachHang("voHieuHoa".equals(trangThai) 
                    ? TrangThaiKhachHang.voHieuHoa 
                    : TrangThaiKhachHang.hoatDong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return kh;
    }
    
    public String layMaKhachHangCuoiCung() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String maCuoiCung = null;

        try {
            con = ConnectDB.getConnection();
            // Sửa thành TOP 1 cho SQL Server
            String sql = "SELECT TOP 1 maKhachHang FROM KhachHang ORDER BY maKhachHang DESC";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                maCuoiCung = rs.getString("maKhachHang");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return maCuoiCung;
    }
    
    public String taoMaKhachHangMoi() {
		String maCuoiCung = layMaKhachHangCuoiCung();
		int namHienTai = Year.now().getValue();
		if(maCuoiCung.isEmpty()) {
			return namHienTai+"KH"+"000001";
		}else {
			int namTrongMa = Integer.parseInt(maCuoiCung.substring(0, 4));
			if(namTrongMa!=namHienTai) {
				return namHienTai+"KH"+"000001";
			}else {
				int soCuoi = Integer.parseInt(maCuoiCung.substring(6));
				soCuoi++;
				String soCuoiFormat = String.format("%06d", soCuoi);
				return namHienTai+"KH"+soCuoiFormat;
			}
		}
	}
}
