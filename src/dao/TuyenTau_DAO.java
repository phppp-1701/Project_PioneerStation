package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Ga;
import entity.TuyenTau;

public class TuyenTau_DAO {
    public List<TuyenTau> getDanhSachTuyenTauKhacMa(String maTuyen) {
        List<TuyenTau> danhSachTuyenTau = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT t.maTuyen, t.maGaDi, t.maGaDen, t.khoangCach, " +
                         "g1.tenGa AS tenGaDi, g1.diaChi AS diaChiDi, " +
                         "g2.tenGa AS tenGaDen, g2.diaChi AS diaChiDen " +
                         "FROM TuyenTau t " +
                         "INNER JOIN Ga g1 ON t.maGaDi = g1.maGa " +
                         "INNER JOIN Ga g2 ON t.maGaDen = g2.maGa " +
                         "WHERE t.maTuyen != ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maTuyen);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Ga gaDi = new Ga(
                    rs.getString("maGaDi"),
                    rs.getString("tenGaDi"),
                    rs.getString("diaChiDi")
                );
                Ga gaDen = new Ga(
                    rs.getString("maGaDen"),
                    rs.getString("tenGaDen"),
                    rs.getString("diaChiDen")
                );
                TuyenTau tuyenTau = new TuyenTau(
                    rs.getString("maTuyen"),
                    gaDi,
                    gaDen,
                    rs.getDouble("khoangCach")
                );
                danhSachTuyenTau.add(tuyenTau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhSachTuyenTau;
    }
    
    public TuyenTau timTuyenTauTheoMaTuyen(String maTuyen) {
        TuyenTau tuyenTau = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT t.maTuyen, t.maGaDi, t.maGaDen, t.khoangCach, " +
                         "g1.tenGa AS tenGaDi, g1.diaChi AS diaChiDi, " +
                         "g2.tenGa AS tenGaDen, g2.diaChi AS diaChiDen " +
                         "FROM TuyenTau t " +
                         "INNER JOIN Ga g1 ON t.maGaDi = g1.maGa " +
                         "INNER JOIN Ga g2 ON t.maGaDen = g2.maGa " +
                         "WHERE t.maTuyen = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maTuyen);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Ga gaDi = new Ga(
                    rs.getString("maGaDi"),
                    rs.getString("tenGaDi"),
                    rs.getString("diaChiDi")
                );
                Ga gaDen = new Ga(
                    rs.getString("maGaDen"),
                    rs.getString("tenGaDen"),
                    rs.getString("diaChiDen")
                );
                tuyenTau = new TuyenTau(
                    rs.getString("maTuyen"),
                    gaDi,
                    gaDen,
                    rs.getDouble("khoangCach")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tuyenTau;
    }

    public List<Ga> getDanhSachGaDenTheoGaDi(String maGaDi) {
        List<Ga> danhSachGaDen = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT g.maGa, g.tenGa, g.diaChi " +
                        "FROM TuyenTau t " +
                        "INNER JOIN Ga g ON t.maGaDen = g.maGa " +
                        "WHERE t.maGaDi = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maGaDi);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Ga ga = new Ga(
                    rs.getString("maGa"),
                    rs.getString("tenGa"),
                    rs.getString("diaChi")
                );
                danhSachGaDen.add(ga);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhSachGaDen;
    }

    public List<Ga> getDanhSachGaDiTheoGaDen(String maGaDen) {
        List<Ga> danhSachGaDi = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT g.maGa, g.tenGa, g.diaChi " +
                        "FROM TuyenTau t " +
                        "INNER JOIN Ga g ON t.maGaDi = g.maGa " +
                        "WHERE t.maGaDen = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maGaDen);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Ga ga = new Ga(
                    rs.getString("maGa"),
                    rs.getString("tenGa"),
                    rs.getString("diaChi")
                );
                danhSachGaDi.add(ga);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhSachGaDi;
    }

    public String getMaGaDiTheoMaTuyen(String maTuyen) {
        String maGaDi = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT maGaDi FROM TuyenTau WHERE maTuyen = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maTuyen);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                maGaDi = rs.getString("maGaDi");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return maGaDi;
    }

    public String getMaGaDenTheoMaTuyen(String maTuyen) {
        String maGaDen = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT maGaDen FROM TuyenTau WHERE maTuyen = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maTuyen);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                maGaDen = rs.getString("maGaDen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return maGaDen;
    }
    
    public String getMaTuyenTheoGa(String maGaDi, String maGaDen) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String maTuyen = null;

        try {
            conn = ConnectDB.getConnection();
            String sql = "SELECT maTuyen FROM TuyenTau WHERE maGaDi = ? AND maGaDen = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maGaDi);
            pstmt.setString(2, maGaDen);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                maTuyen = rs.getString("maTuyen");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return maTuyen;
    }
}
