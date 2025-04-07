CREATE DATABASE pioneer_station;
GO

USE pioneer_station;
GO
CREATE TABLE KhachHang (
    maKhachHang VARCHAR(20) PRIMARY KEY,
    tenKhachHang NVARCHAR(100) NOT NULL,
    CCCD_HoChieu VARCHAR(20) UNIQUE NOT NULL,
    soDienThoai VARCHAR(15) NOT NULL,
    email NVARCHAR(100),
    loaiThanhVien VARCHAR(10) CHECK (loaiThanhVien IN ('thanThiet', 'vip')),
    trangThaiKhachHang VARCHAR(20) CHECK (trangThaiKhachHang IN ('hoatDong','voHieuHoa')) 
);
go

INSERT INTO KhachHang (maKhachHang, tenKhachHang, CCCD_HoChieu, soDienThoai, email, loaiThanhVien, trangThaiKhachHang)
VALUES
-- Khách hàng thân thiết với CCCD 12 số (theo quy định mới) và email hợp lệ
('2024KH000001', N'Nguyễn Thị Mai Anh', '079123000001', '0912345678', 'nguyenthimai.anh@gmail.com', 'thanThiet', 'hoatDong'),
('2024KH000002', N'Trần Văn Bảo', '079123000002', '0987654321', 'tranvan.bao@gmail.com', 'thanThiet', 'hoatDong'),
('2024KH000003', N'Lê Thị Cẩm Tú', '079123000003', '0905111222', 'lethicam.tu@gmail.com', 'thanThiet', 'hoatDong'),
('2024KH000004', N'Phạm Đình Dũng', '079123000004', '0977123456', 'phamdinh.dung@gmail.com', 'thanThiet', 'hoatDong'),
('2024KH000005', N'Hoàng Thị Thu Hiền', '079123000005', '0911222333', 'hoangthithu.hien@gmail.com', 'thanThiet', 'voHieuHoa'),
('2024KH000006', N'Vũ Minh Phúc', '079123000006', '0988999888', 'vuminh.phuc@gmail.com', 'thanThiet', 'hoatDong'),
('2024KH000007', N'Đặng Thị Thu Giang', '079123000007', '0903123123', 'dangthithu.giang@gmail.com', 'thanThiet', 'hoatDong'),
('2024KH000008', N'Bùi Xuân Hoàng', '079123000008', '0914455667', 'buixuan.hoang@gmail.com', 'thanThiet', 'voHieuHoa'),
('2024KH000009', N'Ngô Thị Kim Ngân', '079123000009', '0988123456', 'ngothikim.ngan@gmail.com', 'thanThiet', 'hoatDong'),
('2024KH000010', N'Mai Văn Thanh Long', '079123000010', '0909876543', 'maivanthanh.long@gmail.com', 'thanThiet', 'hoatDong');
go

CREATE TABLE NhanVien (
    maNhanVien VARCHAR(20) PRIMARY KEY,
    tenNhanVien NVARCHAR(100) NOT NULL,
    ngaySinh DATE NOT NULL,
    gioiTinh VARCHAR(5) CHECK (gioiTinh IN ('nam', 'nu')),
    CCCD_HoChieu VARCHAR(20) UNIQUE NOT NULL,
    chucVu VARCHAR(20) CHECK (chucVu IN ('quanLy', 'banVe')),
    trangThaiNhanVien VARCHAR(20) CHECK (trangThaiNhanVien IN ('hoatDong','voHieuHoa'))
);
go

INSERT INTO NhanVien (maNhanVien, tenNhanVien, ngaySinh, gioiTinh, CCCD_HoChieu, chucVu, trangThaiNhanVien)
VALUES
('2024NV000001', N'Nguyễn Văn An', '1985-05-15', 'nam', '001085123456', 'quanLy', 'hoatDong'),
('2024NV000002', N'Trần Thị Bình', '1990-08-22', 'nu', '002190654321', 'quanLy', 'hoatDong'),
('2024NV000003', N'Lê Văn Cường', '1995-03-10', 'nam', '003095987654', 'banVe', 'hoatDong'),
('2024NV000004', N'Phạm Minh Đức', '1992-11-25', 'nam', '004092456789', 'banVe', 'hoatDong'),
('2024NV000005', N'Hoàng Văn Em', '1993-07-18', 'nam', '005093789123', 'banVe', 'voHieuHoa'),
('2024NV000006', N'Vũ Thị Phương', '1994-09-30', 'nu', '006194321654', 'banVe', 'hoatDong'),
('2024NV000007', N'Đặng Thị Quỳnh', '1996-02-14', 'nu', '007196987321', 'banVe', 'hoatDong'),
('2024NV000008', N'Bùi Thị Hương', '1991-12-05', 'nu', '008191654987', 'banVe', 'hoatDong'),
('2024NV000009', N'Mai Thị Lan', '1997-04-20', 'nu', '009197321456', 'banVe', 'voHieuHoa'),
('2024NV000010', N'Ngô Thị Mai', '1989-06-08', 'nu', '010189789654', 'banVe', 'hoatDong');
go
CREATE TABLE Tau (
    maTau VARCHAR(20) PRIMARY KEY,
    tenTau NVARCHAR(100) NOT NULL,
    trangThai VARCHAR(10) CHECK (trangThai IN ('hoatDong', 'baoTri'))
);
go

CREATE TABLE Ga (
    maGa VARCHAR(20) PRIMARY KEY,
    tenGa NVARCHAR(100),
    diaChi NVARCHAR(255)
);
go

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

CREATE TABLE LichTrinh (
    maLichTrinh VARCHAR(20) PRIMARY KEY,
    gioKhoiHanh TIME NOT NULL,
    ngayKhoiHanh VARCHAR(20) CHECK (ngayKhoiHanh IN ('thuHai', 'thuBa', 'thuTu', 'thuNam', 'thuSau', 'thuBay', 'chuNhat'))
);

CREATE TABLE Toa (
    maToa VARCHAR(20) PRIMARY KEY,
    tenToa NVARCHAR(100) NOT NULL,
    loaiToa VARCHAR(20) CHECK (loaiToa IN ('ngoiMemDieuHoa', 'giuongNamDieuHoa', 'gheCungDieuHoa')),
    maTau VARCHAR(20) FOREIGN KEY REFERENCES Tau(maTau)
);

CREATE TABLE ChoNgoi (
    maCho VARCHAR(20) PRIMARY KEY,
    tenCho NVARCHAR(50) NOT NULL,
    trangThaiCho VARCHAR(20) CHECK (trangThaiCho IN ('daDat', 'conTrong', 'dangChon')),
    maToa VARCHAR(20) FOREIGN KEY REFERENCES Toa(maToa)
);

CREATE TABLE KhuyenMai (
    maKhuyenMai VARCHAR(20) PRIMARY KEY,
    tenKhuyenMai NVARCHAR(100) NOT NULL,
    phanTramGiamGia FLOAT NOT NULL,
    ngayBatDau DATE NOT NULL,
    ngayKetThuc DATE NOT NULL
);

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

CREATE TABLE ChuyenTau (
    maTuyenTau VARCHAR(20) NOT NULL,
    maTau VARCHAR(20) NOT NULL,
    maLichTrinh VARCHAR(20) NOT NULL,
    ngayKhoiHanh DATE NOT NULL,
    gioKhoiHanh TIME NOT NULL,
    giaTien FLOAT NOT NULL,
    trangThaiChuyenTau VARCHAR(20) CHECK (trangThaiChuyenTau IN ('hoatDong', 'khongHoatDong')),
    PRIMARY KEY (maTuyenTau, maTau, maLichTrinh),
    FOREIGN KEY (maTuyenTau) REFERENCES TuyenTau(maTuyenTau),
    FOREIGN KEY (maTau) REFERENCES Tau(maTau),
    FOREIGN KEY (maLichTrinh) REFERENCES LichTrinh(maLichTrinh)
);


CREATE TABLE Ve (
    maVe VARCHAR(20) PRIMARY KEY,
    ngayTaoVe DATE NOT NULL,
    trangThaiVe VARCHAR(20) CHECK (trangThaiVe IN ('daHuy', 'hoatDong', 'daHoanThanh')),
    tenKhachHang NVARCHAR(100) NOT NULL,
    CCCD_HoChieu VARCHAR(20) NOT NULL,
    loaiKhachHang VARCHAR(20) CHECK (loaiKhachHang IN ('treEm', 'nguoiLon', 'nguoiCaoTuoi', 'sinhVien')),
    giaVe FLOAT NOT NULL,
    maHoaDon VARCHAR(20) FOREIGN KEY REFERENCES HoaDon(maHoaDon),
    maTuyenTau VARCHAR(20) NOT NULL,
    maTau VARCHAR(20) NOT NULL,
    maLichTrinh VARCHAR(20) NOT NULL,
    
    FOREIGN KEY (maTuyenTau, maTau, maLichTrinh) REFERENCES ChuyenTau(maTuyenTau, maTau, maLichTrinh),
);

CREATE TABLE CaLam (
    maCaLam VARCHAR(20) PRIMARY KEY,
    gioBatDau TIME NOT NULL,
    gioKetThuc TIME NOT NULL,
    ngay VARCHAR(20) CHECK (ngay IN ('thuHai', 'thuBa', 'thuTu', 'thuNam', 'thuSau', 'thuBay', 'chuNhat'))
);

CREATE TABLE ChiTietCaLam (
    ngay DATE NOT NULL,
    trangThaiCaLam VARCHAR(20) CHECK (trangThaiCaLam IN ('ChuaHoanThanh', 'ChuaDenGio', 'DaHoanThanh')),
    ghiChu NVARCHAR(255),
    maNhanVien VARCHAR(20) FOREIGN KEY REFERENCES NhanVien(maNhanVien),
    maCaLam VARCHAR(20) FOREIGN KEY REFERENCES CaLam(maCaLam)
);