CREATE DATABASE pioneer_station;
GO

USE pioneer_station;
GO

-- Bảng Khách Hàng
CREATE TABLE KhachHang (
    maKhachHang VARCHAR(20) PRIMARY KEY,
    tenKhachHang NVARCHAR(100) NOT NULL,
    CCCD_HoChieu VARCHAR(20) UNIQUE NOT NULL,
    soDienThoai VARCHAR(15) NOT NULL,
    email NVARCHAR(100),
    loaiThanhVien VARCHAR(10) CHECK (loaiThanhVien IN ('thanThiet', 'vip')),
    trangThaiKhachHang VARCHAR(20) CHECK (trangThaiKhachHang IN ('hoatDong','voHieuHoa'))
);
GO

-- Bảng Nhân Viên
CREATE TABLE NhanVien (
    maNhanVien VARCHAR(20) PRIMARY KEY,
    tenNhanVien NVARCHAR(100) NOT NULL,
    ngaySinh DATE NOT NULL,
    soDienThoai VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    gioiTinh VARCHAR(5) CHECK (gioiTinh IN ('nam', 'nu')),
    CCCD_HoChieu VARCHAR(20) UNIQUE NOT NULL,
    chucVu VARCHAR(20) CHECK (chucVu IN ('quanLy', 'banVe')),
    trangThaiNhanVien VARCHAR(20) CHECK (trangThaiNhanVien IN ('hoatDong','voHieuHoa')),
    linkAnh VARCHAR(255)
);
GO

-- Bảng Tài Khoản
CREATE TABLE TaiKhoan(
    tenTaiKhoan VARCHAR(20),
    matKhau NVARCHAR(100),
    maNhanVien VARCHAR(20) FOREIGN KEY REFERENCES NhanVien(maNhanVien)
);
GO

-- Bảng Ga
CREATE TABLE Ga (
    maGa VARCHAR(20) PRIMARY KEY,
    tenGa NVARCHAR(100),
    diaChi NVARCHAR(255)
);
GO

INSERT INTO Ga (maGa, tenGa, diaChi)
VALUES
    ('2020GA0001', N'Ga Hà Nội', N'120 Lê Duẩn, Hoàn Kiếm, Hà Nội'),
    ('2018GA0002', N'Ga Sài Gòn', N'1 Nguyễn Thông, Quận 3, TP.HCM'),
    ('2019GA0003', N'Ga Đà Nẵng', N'791 Hải Phòng, Thanh Khê, Đà Nẵng'),
    ('2021GA0004', N'Ga Huế', N'2 Bùi Thị Xuân, TP. Huế, Thừa Thiên Huế'),
    ('2017GA0005', N'Ga Nha Trang', N'17 Thái Nguyên, Nha Trang, Khánh Hòa'),
    ('2022GA0006', N'Ga Đồng Hới', N'20 Hữu Nghị, Đồng Hới, Quảng Bình'),
    ('2020GA0007', N'Ga Thanh Hóa', N'Đường Lê Lai, TP. Thanh Hóa'),
    ('2019GA0008', N'Ga Vinh', N'75 Quang Trung, TP. Vinh, Nghệ An'),
    ('2021GA0009', N'Ga Hải Phòng', N'75 Lương Khánh Thiện, Hải Phòng'),
    ('2018GA0010', N'Ga Đà Lạt', N'1 Quang Trung, Phường 9, Đà Lạt');
go

-- Bảng Tuyến Tàu
CREATE TABLE TuyenTau (
    maTuyenTau VARCHAR(20) PRIMARY KEY,
    tenTuyenTau NVARCHAR(100),
    khoangCach FLOAT,
    maGaDi VARCHAR(20),
    maGaDen VARCHAR(20),
    FOREIGN KEY (maGaDi) REFERENCES Ga(maGa),
    FOREIGN KEY (maGaDen) REFERENCES Ga(maGa),
    CHECK (maGaDi <> maGaDen)
);
GO

-- Bảng Lịch Trình
CREATE TABLE LichTrinh (
    maLichTrinh VARCHAR(20) PRIMARY KEY,
    gioKhoiHanh TIME NOT NULL,
    ngayKhoiHanh VARCHAR(20) CHECK (ngayKhoiHanh IN ('thuHai', 'thuBa', 'thuTu', 'thuNam', 'thuSau', 'thuBay', 'chuNhat'))
);
GO

-- Bảng Khuyến Mãi
CREATE TABLE KhuyenMai (
    maKhuyenMai VARCHAR(20) PRIMARY KEY,
    tenKhuyenMai NVARCHAR(100) NOT NULL,
    phanTramGiamGia FLOAT NOT NULL,
    ngayBatDau DATE NOT NULL,
    ngayKetThuc DATE NOT NULL
);
GO

-- Bảng Tàu
CREATE TABLE Tau (
    maTau VARCHAR(20) PRIMARY KEY,
    tenTau NVARCHAR(100) NOT NULL,
    loaiTau VARCHAR(20) CHECK (loaiTau IN ('SE', 'TN', 'DP')),
    trangThai VARCHAR(20) CHECK (trangThai IN ('hoatDong', 'baoTri'))
);
GO

INSERT INTO Tau (maTau, tenTau, loaiTau, trangThai) VALUES
('2025SE000001', N'Tàu Thống Nhất 1', 'SE', 'hoatDong'),
('2025SE000002', N'Tàu Thống Nhất 2', 'SE', 'baoTri'),
('2025TN000001', N'Tàu Miền Trung 1', 'TN', 'hoatDong'),
('2025TN000002', N'Tàu Tây Nguyên', 'TN', 'baoTri'),
('2025DP000001', N'Tàu Địa Phương 1', 'DP', 'hoatDong'),
('2025DP000002', N'Tàu Địa Phương 2', 'DP', 'baoTri');
go

-- Bảng Toa
CREATE TABLE Toa (
    maToa VARCHAR(20) PRIMARY KEY,
    tenToa NVARCHAR(100) NOT NULL,
    loaiToa VARCHAR(20) CHECK (loaiToa IN ('ngoiMemDieuHoa', 'giuongNamDieuHoa', 'gheCungDieuHoa')),
    maTau VARCHAR(20) FOREIGN KEY REFERENCES Tau(maTau)
);
GO

-- Bảng Lịch Chuyến Tàu
CREATE TABLE ChuyenTau (
    maChuyenTau VARCHAR(20) PRIMARY KEY,
    ngayKhoiHanh DATE NOT NULL,
    gioKhoiHanh TIME NOT NULL,
    giaTien FLOAT NOT NULL,
    trangThaiChuyenTau VARCHAR(20) CHECK (trangThaiChuyenTau IN ('hoatDong', 'khongHoatDong')),
    maTuyenTau VARCHAR(20) FOREIGN KEY REFERENCES TuyenTau(maTuyenTau),
    maTau VARCHAR(20) FOREIGN KEY REFERENCES Tau(maTau),
    maLichTrinh VARCHAR(20) FOREIGN KEY REFERENCES LichTrinh(maLichTrinh)
);
GO

-- Bảng Chỗ Ngồi
CREATE TABLE ChoNgoi (
    maCho VARCHAR(20),
    maChuyenTau VARCHAR(20),
    maToa VARCHAR(20),
    tenCho NVARCHAR(50) NOT NULL,
    trangThaiCho VARCHAR(20) CHECK (trangThaiCho IN ('daDat', 'conTrong', 'dangChon')),
    PRIMARY KEY (maChuyenTau, maCho),
    FOREIGN KEY (maChuyenTau) REFERENCES ChuyenTau(maChuyenTau),
    FOREIGN KEY (maToa) REFERENCES Toa(maToa)
);
GO

-- Bảng Hóa Đơn
CREATE TABLE HoaDon (
    maHoaDon VARCHAR(20) PRIMARY KEY,
    ngayTaoHoaDon DATE NOT NULL,
    phuongThucThanhToan VARCHAR(20) CHECK (phuongThucThanhToan IN ('tienMat', 'atm', 'qrCode')),
    phanTramGiamGia FLOAT DEFAULT 0,
    thanhTien FLOAT NOT NULL,
    tienKhachDua FLOAT NOT NULL,
    tienTraLai FLOAT NOT NULL,
    maNhanVien VARCHAR(20) FOREIGN KEY REFERENCES NhanVien(maNhanVien),
    maKhachHang VARCHAR(20) FOREIGN KEY REFERENCES KhachHang(maKhachHang),
    maKhuyenMai VARCHAR(20) FOREIGN KEY REFERENCES KhuyenMai(maKhuyenMai)
);
GO

-- Bảng Vé
CREATE TABLE Ve (
    maVe VARCHAR(20) PRIMARY KEY,
    ngayTaoVe DATE NOT NULL,
    trangThaiVe VARCHAR(20) CHECK (trangThaiVe IN ('daHuy', 'hoatDong', 'daHoanThanh')),
    tenKhachHang NVARCHAR(100) NOT NULL,
    CCCD_HoChieu VARCHAR(20) NOT NULL,
    loaiKhachHang VARCHAR(20) CHECK (loaiKhachHang IN ('treEm', 'nguoiLon', 'nguoiCaoTuoi', 'sinhVien')),
    giaVe FLOAT NOT NULL,
    maHoaDon VARCHAR(20) FOREIGN KEY REFERENCES HoaDon(maHoaDon),
    maChuyenTau VARCHAR(20) FOREIGN KEY REFERENCES ChuyenTau(maChuyenTau)
);
GO

-- Bảng Ca Làm
CREATE TABLE CaLam (
    maCaLam VARCHAR(20) PRIMARY KEY,
    gioBatDau TIME NOT NULL,
    gioKetThuc TIME NOT NULL,
    ngay VARCHAR(20) CHECK (ngay IN ('thuHai', 'thuBa', 'thuTu', 'thuNam', 'thuSau', 'thuBay', 'chuNhat'))
);
GO

-- Bảng Chi Tiết Ca Làm
CREATE TABLE ChiTietCaLam (
    ngay DATE NOT NULL,
    trangThaiCaLam VARCHAR(20) CHECK (trangThaiCaLam IN ('ChuaHoanThanh', 'ChuaDenGio', 'DaHoanThanh')),
    ghiChu NVARCHAR(255),
    maNhanVien VARCHAR(20) FOREIGN KEY REFERENCES NhanVien(maNhanVien),
    maCaLam VARCHAR(20) FOREIGN KEY REFERENCES CaLam(maCaLam)
);
GO
