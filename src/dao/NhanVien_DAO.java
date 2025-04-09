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
import entity.NhanVien;
import entity.KhachHang.LoaiThanhVien;
import entity.KhachHang.TrangThaiKhachHang;
import entity.NhanVien.ChucVu;
import entity.NhanVien.GioiTinh;
import entity.NhanVien.TrangThaiNhanVien;

public class NhanVien_DAO {
    // Lấy toàn bộ danh sách nhân viên
	public List<NhanVien> getAllNhanVien() {
	    List<NhanVien> dsNhanVien = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	        con = ConnectDB.getConnection();
	        String sql = "SELECT * FROM NhanVien";
	        stmt = con.prepareStatement(sql);
	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            NhanVien nv = new NhanVien();
	            nv.setMaNhanVien(rs.getString("maNhanVien"));
	            nv.setTenNhanVien(rs.getString("tenNhanVien"));
	            nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
	            nv.setSoDienThoai(rs.getString("soDienThoai"));
	            nv.setEmail(rs.getString("email"));
	            // Xử lý enum GioiTinh
	            String gioiTinh = rs.getString("gioiTinh");
	            nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);
	            
	            nv.setCccd_HoChieu(rs.getString("CCCD_HoChieu"));
	            
	            // Xử lý enum ChucVu
	            String chucVu = rs.getString("chucVu");
	            if ("quanLy".equals(chucVu)) {
	                nv.setChucVu(ChucVu.quanLy);
	            } else {
	                nv.setChucVu(ChucVu.banVe);
	            }
	            
	            String trangThaiNhanVien = rs.getString("trangThaiNhanVien");
	            if ("hoatDong".equals(trangThaiNhanVien)) {
	                nv.setTrangThaiNhanVien(TrangThaiNhanVien.hoatDong);
	            } else {
	                nv.setTrangThaiNhanVien(TrangThaiNhanVien.voHieuHoa);
	            }
	            
	            // Thêm ánh xạ cho cột linkAnh
	            nv.setLinkAnh(rs.getString("linkAnh"));
	            
	            dsNhanVien.add(nv);
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
	    
	    return dsNhanVien;
	}
    
    ///Tim theo so dien thoai
	public List<NhanVien> timNhanVienTheoSoDienThoai(String soDienThoai) {
	    List<NhanVien> dsNhanVien = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	        con = ConnectDB.getConnection();
	        String sql = "SELECT * FROM NhanVien WHERE soDienThoai LIKE ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, "%" + soDienThoai + "%");
	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            NhanVien nv = new NhanVien();
	            nv.setMaNhanVien(rs.getString("maNhanVien"));
	            nv.setTenNhanVien(rs.getString("tenNhanVien"));
	            nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
	            nv.setSoDienThoai(rs.getString("soDienThoai"));
	            nv.setEmail(rs.getString("email"));
	            String gioiTinh = rs.getString("gioiTinh");
	            nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);
	            
	            nv.setCccd_HoChieu(rs.getString("CCCD_HoChieu"));
	            
	            String chucVu = rs.getString("chucVu");
	            if ("quanLy".equals(chucVu)) {
	                nv.setChucVu(ChucVu.quanLy);
	            } else {
	                nv.setChucVu(ChucVu.banVe);
	            }
	            
	            String trangThaiNhanVien = rs.getString("trangThaiNhanVien");
	            if ("hoatDong".equals(trangThaiNhanVien)) {
	                nv.setTrangThaiNhanVien(TrangThaiNhanVien.hoatDong);
	            } else {
	                nv.setTrangThaiNhanVien(TrangThaiNhanVien.voHieuHoa);
	            }
	            
	            // Thêm ánh xạ cho cột linkAnh
	            nv.setLinkAnh(rs.getString("linkAnh"));
	            
	            dsNhanVien.add(nv);
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
	    
	    return dsNhanVien;
	}
    
    //Tim khach hang theo ten
    public List<NhanVien> timNhanVienTheoTen(String tenNhanVien) {
        List<NhanVien> dsNhanVien = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien WHERE tenNhanVien LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + tenNhanVien + "%"); // Tìm kiếm gần đúng
            rs = stmt.executeQuery();
            
            while (rs.next()) {
            	NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNhanVien"));
                nv.setTenNhanVien(rs.getString("tenNhanVien"));
                nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
                nv.setSoDienThoai(rs.getString("soDienThoai"));
                nv.setEmail(rs.getString("email"));
                // Xử lý enum GioiTinh
                String gioiTinh = rs.getString("gioiTinh");
                nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);
                
                nv.setCccd_HoChieu(rs.getString("CCCD_HoChieu"));
                
                // Xử lý enum ChucVu
                String chucVu = rs.getString("chucVu");
                if ("quanLy".equals(chucVu)) {
                    nv.setChucVu(ChucVu.quanLy);
                } else {
                    nv.setChucVu(ChucVu.banVe);
                }
                
                String trangThaiNhanVien = rs.getString("trangThaiNhanVien");
                if ("hoatDong".equals(trangThaiNhanVien)) {
                    nv.setTrangThaiNhanVien(TrangThaiNhanVien.hoatDong);
                } else {
                    nv.setTrangThaiNhanVien(TrangThaiNhanVien.voHieuHoa);
                }
                nv.setLinkAnh(rs.getString("linkAnh"));
                dsNhanVien.add(nv);
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
        
        return dsNhanVien;
    }
    
    
    public List<NhanVien> timNhanVienTheoTenVaSoDienThoai(String tenNhanVien, String soDienThoai) {
        List<NhanVien> dsNhanVien = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien WHERE tenNhanVien LIKE ? AND soDienThoai LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + tenNhanVien + "%");
            stmt.setString(2, "%" + soDienThoai + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
            	NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNhanVien"));
                nv.setTenNhanVien(rs.getString("tenNhanVien"));
                nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
                nv.setSoDienThoai(rs.getString("soDienThoai"));
                nv.setEmail(rs.getString("email"));
                // Xử lý enum GioiTinh
                String gioiTinh = rs.getString("gioiTinh");
                nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);
                
                nv.setCccd_HoChieu(rs.getString("CCCD_HoChieu"));
                
                // Xử lý enum ChucVu
                String chucVu = rs.getString("chucVu");
                if ("quanLy".equals(chucVu)) {
                    nv.setChucVu(ChucVu.quanLy);
                } else {
                    nv.setChucVu(ChucVu.banVe);
                }
                
                String trangThaiNhanVien = rs.getString("trangThaiNhanVien");
                if ("hoatDong".equals(trangThaiNhanVien)) {
                    nv.setTrangThaiNhanVien(TrangThaiNhanVien.hoatDong);
                } else {
                    nv.setTrangThaiNhanVien(TrangThaiNhanVien.voHieuHoa);
                } 
                nv.setLinkAnh(rs.getString("linkAnh"));
                dsNhanVien.add(nv);
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
        
        return dsNhanVien;
    }
    
    
  //Tim khach hang theo ten
    public List<NhanVien> timNhanVienTheoCCCD_HoChieu(String CCCD_HoChieu) {
        List<NhanVien> dsNhanVien = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien WHERE CCCD_HoChieu LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + CCCD_HoChieu + "%"); // Tìm kiếm gần đúng
            rs = stmt.executeQuery();
            
            while (rs.next()) {
            	NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNhanVien"));
                nv.setTenNhanVien(rs.getString("tenNhanVien"));
                nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
                nv.setSoDienThoai(rs.getString("soDienThoai"));
                nv.setEmail(rs.getString("email"));
                // Xử lý enum GioiTinh
                String gioiTinh = rs.getString("gioiTinh");
                nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);
                
                nv.setCccd_HoChieu(rs.getString("CCCD_HoChieu"));
                
                // Xử lý enum ChucVu
                String chucVu = rs.getString("chucVu");
                if ("quanLy".equals(chucVu)) {
                    nv.setChucVu(ChucVu.quanLy);
                } else {
                    nv.setChucVu(ChucVu.banVe);
                }
                
                String trangThaiNhanVien = rs.getString("trangThaiNhanVien");
                if ("hoatDong".equals(trangThaiNhanVien)) {
                    nv.setTrangThaiNhanVien(TrangThaiNhanVien.hoatDong);
                } else {
                    nv.setTrangThaiNhanVien(TrangThaiNhanVien.voHieuHoa);
                } 
                nv.setLinkAnh(rs.getString("linkAnh"));
                dsNhanVien.add(nv);
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
        
        return dsNhanVien;
    }
    
    // Thêm nhân viên mới
    public boolean themNhanVien(NhanVien nv) throws SQLException {
        String sql = "INSERT INTO NhanVien(maNhanVien, tenNhanVien, ngaySinh, soDienThoai, email, "
                   + "gioiTinh, CCCD_HoChieu, chucVu, trangThaiNhanVien, linkAnh) "
                   + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, nv.getMaNhanVien());
            stmt.setString(2, nv.getTenNhanVien());
            stmt.setDate(3, java.sql.Date.valueOf(nv.getNgaySinh()));
            stmt.setString(4, nv.getSoDienThoai());
            stmt.setString(5, nv.getEmail());
            
            // Xử lý giới tính (phải là 'nam' hoặc 'nu')
            String gioiTinh = (nv.getGioiTinh() == NhanVien.GioiTinh.nam) ? "nam" : "nu";
            stmt.setString(6, gioiTinh);
            
            stmt.setString(7, nv.getCccd_HoChieu());
            
            // Xử lý chức vụ (phải là 'quanLy' hoặc 'banVe')
            String chucVu = (nv.getChucVu() == NhanVien.ChucVu.quanLy) ? "quanLy" : "banVe";
            stmt.setString(8, chucVu);

            // Xử lý trạng thái (phải là 'hoatDong' hoặc 'voHieuHoa')
            String trangThai = (nv.getTrangThaiNhanVien() == NhanVien.TrangThaiNhanVien.hoatDong) 
                              ? "hoatDong" : "voHieuHoa";
            stmt.setString(9, trangThai);
            
            // Xử lý link ảnh (có thể null)
            stmt.setString(10, nv.getLinkAnh());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    // Cập nhật thông tin nhân viên
    public boolean capNhatNhanVien(NhanVien nv) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE NhanVien SET tenNhanVien = ?, ngaySinh = ?, soDienThoai = ?, email = ?, gioiTinh = ?, "
                       + "CCCD_HoChieu = ?, chucVu = ?, trangThaiNhanVien = ?, linkAnh = ? WHERE maNhanVien = ?";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, nv.getTenNhanVien());
            stmt.setDate(2, java.sql.Date.valueOf(nv.getNgaySinh()));
            stmt.setString(3, nv.getSoDienThoai());
            stmt.setString(4, nv.getEmail());
            
            // Xử lý giới tính (phải là 'nam' hoặc 'nu')
            String gioiTinh = (nv.getGioiTinh() == NhanVien.GioiTinh.nam) ? "nam" : "nu";
            stmt.setString(5, gioiTinh);
            
            stmt.setString(6, nv.getCccd_HoChieu());
            
            // Xử lý chức vụ (phải là 'quanLy' hoặc 'banVe')
            String chucVu = (nv.getChucVu() == NhanVien.ChucVu.quanLy) ? "quanLy" : "banVe";
            stmt.setString(7, chucVu);

            // Xử lý trạng thái (phải là 'hoatDong' hoặc 'voHieuHoa')
            String trangThai = (nv.getTrangThaiNhanVien() == NhanVien.TrangThaiNhanVien.hoatDong) 
                              ? "hoatDong" : "voHieuHoa";
            stmt.setString(8, trangThai);
            
            // Xử lý link ảnh (có thể null)
            stmt.setString(9, nv.getLinkAnh());
            
            stmt.setString(10, nv.getMaNhanVien());

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

    
    public boolean kiemTraCCCD(String cccd) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM NhanVien WHERE CCCD_HoChieu = ?";
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
    
    public String layMaNhanVienCuoiCung() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String maCuoiCung = null;

        try {
            con = ConnectDB.getConnection();
            // Sửa thành TOP 1 cho SQL Server
            String sql = "SELECT TOP 1 maNhanVien FROM NhanVien ORDER BY maNhanVien DESC";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                maCuoiCung = rs.getString("maNhanVien");
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
    
    public String taoMaNhanVienMoi() {
		String maCuoiCung = layMaNhanVienCuoiCung();
		int namHienTai = Year.now().getValue();
		if(maCuoiCung.isEmpty()) {
			return namHienTai+"NV"+"000001";
		}else {
			int namTrongMa = Integer.parseInt(maCuoiCung.substring(0, 4));
			if(namTrongMa!=namHienTai) {
				return namHienTai+"NV"+"000001";
			}else {
				int soCuoi = Integer.parseInt(maCuoiCung.substring(6));
				soCuoi++;
				String soCuoiFormat = String.format("%06d", soCuoi);
				return namHienTai+"NV"+soCuoiFormat;
			}
		}
	}
    
    public NhanVien timNhanVienTheoMa(String maNhanVien) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        NhanVien nv = null;
        
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien WHERE maNhanVien = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maNhanVien);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("maNhanVien"));
                nv.setTenNhanVien(rs.getString("tenNhanVien"));
                nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
                nv.setSoDienThoai(rs.getString("soDienThoai"));
                nv.setEmail(rs.getString("email"));
                
                // Xử lý enum GioiTinh
                String gioiTinh = rs.getString("gioiTinh");
                nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);
                
                nv.setCccd_HoChieu(rs.getString("CCCD_HoChieu"));
                
                // Xử lý enum ChucVu
                String chucVu = rs.getString("chucVu");
                if ("quanLy".equals(chucVu)) {
                    nv.setChucVu(ChucVu.quanLy);
                } else {
                    nv.setChucVu(ChucVu.banVe);
                }
                
                String trangThaiNhanVien = rs.getString("trangThaiNhanVien");
                if ("hoatDong".equals(trangThaiNhanVien)) {
                    nv.setTrangThaiNhanVien(TrangThaiNhanVien.hoatDong);
                } else {
                    nv.setTrangThaiNhanVien(TrangThaiNhanVien.voHieuHoa);
                }
                
                // Thêm ánh xạ cho cột linkAnh
                nv.setLinkAnh(rs.getString("linkAnh"));
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
        
        return nv;
    }
}