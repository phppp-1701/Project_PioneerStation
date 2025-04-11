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
    soDienThoai VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    gioiTinh VARCHAR(5) CHECK (gioiTinh IN ('nam', 'nu')),
    CCCD_HoChieu VARCHAR(20) UNIQUE NOT NULL,
    chucVu VARCHAR(20) CHECK (chucVu IN ('quanLy', 'banVe')),
    trangThaiNhanVien VARCHAR(20) CHECK (trangThaiNhanVien IN ('hoatDong','voHieuHoa')),
    linkAnh VARCHAR(255)
    
);
go

INSERT INTO NhanVien (maNhanVien, tenNhanVien, ngaySinh, soDienThoai, email, gioiTinh, CCCD_HoChieu, chucVu, trangThaiNhanVien, linkAnh)
 VALUES
 ('2022NV000001', N'Phạm Trương Hoàng Phương', '2004-01-17', '0947991755', 'hphuong17@gmail.com', 'nam', '083204007207', 'quanLy', 'hoatDong','image/phuong.jpg'),
 ('2022NV000002', N'Phạm Viết Quân', '2004-09-29', '0987654321', 'pvquan29@gmail.com', 'nam', '002190654321', 'quanLy', 'hoatDong','image/quan.jpg'),
 ('2022NV000003', N'Bùi Tấn Quang Trung', '2004-08-10', '0978123456', 'trinhbuc08@gmail.com', 'nam', '003095987654', 'quanLy', 'hoatDong','image/trung.jpg'),
 ('2022NV000004', N'Trần Minh Tuấn', '2004-09-18', '0965432187', 'tuantran18@gmail.com', 'nam', '004092456789', 'quanLy', 'hoatDong','image/tuan.jpg'),
 ('2023NV000001',N'Nguyễn Thị Trinh','2006-03-24', '0932342234','trinhwork24@gmail.com','nu','029323131234','banVe','hoatDong','image/trinh.jpg');
 go

 CREATE TABLE TaiKhoan(
 	tenTaiKhoan VARCHAR(20),
 	matKhau NVARCHAR(100),
 	maNhanVien VARCHAR(20) FOREIGN KEY REFERENCES NhanVien(maNhanVien)
 )
 go

INSERT INTO TaiKhoan (tenTaiKhoan, matKhau, maNhanVien)
VALUES
('hphuong17', 'phuong123', '2022NV000001'),
('pvquan29', 'quan456', '2022NV000002'),
('trungbtq', 'trung789', '2022NV000003'),
('tuanminh', 'tuan101', '2022NV000004'),
('trinhnt', 'trinh202', '2023NV000001');
go

CREATE TABLE Ga (
    maGa VARCHAR(20) PRIMARY KEY,
    tenGa NVARCHAR(100),
    diaChi NVARCHAR(255)
);
go

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

CREATE TABLE TuyenTau (
    maTuyen VARCHAR(20) PRIMARY KEY,
    maGaDi VARCHAR(20),
    maGaDen VARCHAR(20),
    khoangCach FLOAT, 
    CONSTRAINT FK_maGaDi FOREIGN KEY (maGaDi) REFERENCES Ga(maGa),
    CONSTRAINT FK_maGaDen FOREIGN KEY (maGaDen) REFERENCES Ga(maGa),
    CONSTRAINT CHK_DifferentStations CHECK (maGaDi <> maGaDen),
    CONSTRAINT CHK_PositiveDistance CHECK (khoangCach > 0)
);
GO	

INSERT INTO TuyenTau (maTuyen, maGaDi, maGaDen, khoangCach)
VALUES
    -- Hà Nội (2020GA0001) to others
    ('2025TT0001', '2020GA0001', '2018GA0002', 1726), -- Hà Nội to Sài Gòn
    ('2025TT0002', '2020GA0001', '2019GA0003', 800),  -- Hà Nội to Đà Nẵng
    ('2025TT0003', '2020GA0001', '2021GA0004', 650),  -- Hà Nội to Huế
    ('2025TT0004', '2020GA0001', '2017GA0005', 1300), -- Hà Nội to Nha Trang
    ('2025TT0005', '2020GA0001', '2022GA0006', 500),  -- Hà Nội to Đồng Hới
    ('2025TT0006', '2020GA0001', '2020GA0007', 150),  -- Hà Nội to Thanh Hóa
    ('2025TT0007', '2020GA0001', '2019GA0008', 300),  -- Hà Nội to Vinh
    ('2025TT0008', '2020GA0001', '2021GA0009', 100),  -- Hà Nội to Hải Phòng
    ('2025TT0009', '2020GA0001', '2018GA0010', 1500), -- Hà Nội to Đà Lạt
    -- Sài Gòn (2018GA0002) to others
    ('2025TT0010', '2018GA0002', '2020GA0001', 1726), -- Sài Gòn to Hà Nội
    ('2025TT0011', '2018GA0002', '2019GA0003', 900),  -- Sài Gòn to Đà Nẵng
    ('2025TT0012', '2018GA0002', '2021GA0004', 1076), -- Sài Gòn to Huế
    ('2025TT0013', '2018GA0002', '2017GA0005', 400),  -- Sài Gòn to Nha Trang
    ('2025TT0014', '2018GA0002', '2022GA0006', 1226), -- Sài Gòn to Đồng Hới
    ('2025TT0015', '2018GA0002', '2020GA0007', 1576), -- Sài Gòn to Thanh Hóa
    ('2025TT0016', '2018GA0002', '2019GA0008', 1426), -- Sài Gòn to Vinh
    ('2025TT0017', '2018GA0002', '2021GA0009', 1826), -- Sài Gòn to Hải Phòng
    ('2025TT0018', '2018GA0002', '2018GA0010', 600),  -- Sài Gòn to Đà Lạt
    -- Đà Nẵng (2019GA0003) to others
    ('2025TT0019', '2019GA0003', '2020GA0001', 800),  -- Đà Nẵng to Hà Nội
    ('2025TT0020', '2019GA0003', '2018GA0002', 900),  -- Đà Nẵng to Sài Gòn
    ('2025TT0021', '2019GA0003', '2021GA0004', 100),  -- Đà Nẵng to Huế
    ('2025TT0022', '2019GA0003', '2017GA0005', 500),  -- Đà Nẵng to Nha Trang
    ('2025TT0023', '2019GA0003', '2022GA0006', 300),  -- Đà Nẵng to Đồng Hới
    ('2025TT0024', '2019GA0003', '2020GA0007', 650),  -- Đà Nẵng to Thanh Hóa
    ('2025TT0025', '2019GA0003', '2019GA0008', 500),  -- Đà Nẵng to Vinh
    ('2025TT0026', '2019GA0003', '2021GA0009', 900),  -- Đà Nẵng to Hải Phòng
    ('2025TT0027', '2019GA0003', '2018GA0010', 700),  -- Đà Nẵng to Đà Lạt
    -- Huế (2021GA0004) to others
    ('2025TT0028', '2021GA0004', '2020GA0001', 650),  -- Huế to Hà Nội
    ('2025TT0029', '2021GA0004', '2018GA0002', 1076), -- Huế to Sài Gòn
    ('2025TT0030', '2021GA0004', '2019GA0003', 100),  -- Huế to Đà Nẵng
    ('2025TT0031', '2021GA0004', '2017GA0005', 650),  -- Huế to Nha Trang
    ('2025TT0032', '2021GA0004', '2022GA0006', 150),  -- Huế to Đồng Hới
    ('2025TT0033', '2021GA0004', '2020GA0007', 500),  -- Huế to Thanh Hóa
    ('2025TT0034', '2021GA0004', '2019GA0008', 350),  -- Huế to Vinh
    ('2025TT0035', '2021GA0004', '2021GA0009', 750),  -- Huế to Hải Phòng
    ('2025TT0036', '2021GA0004', '2018GA0010', 850),  -- Huế to Đà Lạt
    -- Nha Trang (2017GA0005) to others
    ('2025TT0037', '2017GA0005', '2020GA0001', 1300), -- Nha Trang to Hà Nội
    ('2025TT0038', '2017GA0005', '2018GA0002', 400),  -- Nha Trang to Sài Gòn
    ('2025TT0039', '2017GA0005', '2019GA0003', 500),  -- Nha Trang to Đà Nẵng
    ('2025TT0040', '2017GA0005', '2021GA0004', 650),  -- Nha Trang to Huế
    ('2025TT0041', '2017GA0005', '2022GA0006', 800),  -- Nha Trang to Đồng Hới
    ('2025TT0042', '2017GA0005', '2020GA0007', 1150), -- Nha Trang to Thanh Hóa
    ('2025TT0043', '2017GA0005', '2019GA0008', 1000), -- Nha Trang to Vinh
    ('2025TT0044', '2017GA0005', '2021GA0009', 1400), -- Nha Trang to Hải Phòng
    ('2025TT0045', '2017GA0005', '2018GA0010', 200),  -- Nha Trang to Đà Lạt
    -- Đồng Hới (2022GA0006) to others
    ('2025TT0046', '2022GA0006', '2020GA0001', 500),  -- Đồng Hới to Hà Nội
    ('2025TT0047', '2022GA0006', '2018GA0002', 1226), -- Đồng Hới to Sài Gòn
    ('2025TT0048', '2022GA0006', '2019GA0003', 300),  -- Đồng Hới to Đà Nẵng
    ('2025TT0049', '2022GA0006', '2021GA0004', 150),  -- Đồng Hới to Huế
    ('2025TT0050', '2022GA0006', '2017GA0005', 800),  -- Đồng Hới to Nha Trang
    ('2025TT0051', '2022GA0006', '2020GA0007', 350),  -- Đồng Hới to Thanh Hóa
    ('2025TT0052', '2022GA0006', '2019GA0008', 200),  -- Đồng Hới to Vinh
    ('2025TT0053', '2022GA0006', '2021GA0009', 600),  -- Đồng Hới to Hải Phòng
    ('2025TT0054', '2022GA0006', '2018GA0010', 1000), -- Đồng Hới to Đà Lạt
    -- Thanh Hóa (2020GA0007) to others
    ('2025TT0055', '2020GA0007', '2020GA0001', 150),  -- Thanh Hóa to Hà Nội
    ('2025TT0056', '2020GA0007', '2018GA0002', 1576), -- Thanh Hóa to Sài Gòn
    ('2025TT0057', '2020GA0007', '2019GA0003', 650),  -- Thanh Hóa to Đà Nẵng
    ('2025TT0058', '2020GA0007', '2021GA0004', 500),  -- Thanh Hóa to Huế
    ('2025TT0059', '2020GA0007', '2017GA0005', 1150), -- Thanh Hóa to Nha Trang
    ('2025TT0060', '2020GA0007', '2022GA0006', 350),  -- Thanh Hóa to Đồng Hới
    ('2025TT0061', '2020GA0007', '2019GA0008', 150),  -- Thanh Hóa to Vinh
    ('2025TT0062', '2020GA0007', '2021GA0009', 250),  -- Thanh Hóa to Hải Phòng
    ('2025TT0063', '2020GA0007', '2018GA0010', 1350), -- Thanh Hóa to Đà Lạt
    -- Vinh (2019GA0008) to others
    ('2025TT0064', '2019GA0008', '2020GA0001', 300),  -- Vinh to Hà Nội
    ('2025TT0065', '2019GA0008', '2018GA0002', 1426), -- Vinh to Sài Gòn
    ('2025TT0066', '2019GA0008', '2019GA0003', 500),  -- Vinh to Đà Nẵng
    ('2025TT0067', '2019GA0008', '2021GA0004', 350),  -- Vinh to Huế
    ('2025TT0068', '2019GA0008', '2017GA0005', 1000), -- Vinh to Nha Trang
    ('2025TT0069', '2019GA0008', '2022GA0006', 200),  -- Vinh to Đồng Hới
    ('2025TT0070', '2019GA0008', '2020GA0007', 150),  -- Vinh to Thanh Hóa
    ('2025TT0071', '2019GA0008', '2021GA0009', 400),  -- Vinh to Hải Phòng
    ('2025TT0072', '2019GA0008', '2018GA0010', 1200), -- Vinh to Đà Lạt
    -- Hải Phòng (2021GA0009) to others
    ('2025TT0073', '2021GA0009', '2020GA0001', 100),  -- Hải Phòng to Hà Nội
    ('2025TT0074', '2021GA0009', '2018GA0002', 1826), -- Hải Phòng to Sài Gòn
    ('2025TT0075', '2021GA0009', '2019GA0003', 900),  -- Hải Phòng to Đà Nẵng
    ('2025TT0076', '2021GA0009', '2021GA0004', 750),  -- Hải Phòng to Huế
    ('2025TT0077', '2021GA0009', '2017GA0005', 1400), -- Hải Phòng to Nha Trang
    ('2025TT0078', '2021GA0009', '2022GA0006', 600),  -- Hải Phòng to Đồng Hới
    ('2025TT0079', '2021GA0009', '2020GA0007', 250),  -- Hải Phòng to Thanh Hóa
    ('2025TT0080', '2021GA0009', '2019GA0008', 400),  -- Hải Phòng to Vinh
    ('2025TT0081', '2021GA0009', '2018GA0010', 1600), -- Hải Phòng to Đà Lạt
    -- Đà Lạt (2018GA0010) to others
    ('2025TT0082', '2018GA0010', '2020GA0001', 1500), -- Đà Lạt to Hà Nội
    ('2025TT0083', '2018GA0010', '2018GA0002', 600),  -- Đà Lạt to Sài Gòn
    ('2025TT0084', '2018GA0010', '2019GA0003', 700),  -- Đà Lạt to Đà Nẵng
    ('2025TT0085', '2018GA0010', '2021GA0004', 850),  -- Đà Lạt to Huế
    ('2025TT0086', '2018GA0010', '2017GA0005', 200),  -- Đà Lạt to Nha Trang
    ('2025TT0087', '2018GA0010', '2022GA0006', 1000), -- Đà Lạt to Đồng Hới
    ('2025TT0088', '2018GA0010', '2020GA0007', 1350), -- Đà Lạt to Thanh Hóa
    ('2025TT0089', '2018GA0010', '2019GA0008', 1200), -- Đà Lạt to Vinh
    ('2025TT0090', '2018GA0010', '2021GA0009', 1600); -- Đà Lạt to Hải Phòng
GO

CREATE TABLE Tau(
	maTau VARCHAR(20) PRIMARY KEY,
	tenTau NVARCHAR(100) NOT NULL,
	loaiTau VARCHAR(20) CHECK (loaiTau IN ('SE', 'TN')),
	trangThai VARCHAR(20) CHECK (trangThai IN ('hoatDong', 'baoTri'))
)
go

CREATE TABLE Toa (
    maToa VARCHAR(20) PRIMARY KEY,
    tenToa NVARCHAR(100) NOT NULL,
    loaiToa VARCHAR(20) CHECK (loaiToa IN ('ngoiMemDieuHoa', 'giuongNamDieuHoa', 'gheCungDieuHoa')),
	trangThai VarChar(20) Check (trangThai in ('hoatDong', 'voHieuHoa')),
    maTau VARCHAR(20) FOREIGN KEY REFERENCES Tau(maTau)
);

INSERT INTO Tau (maTau, tenTau, loaiTau, trangThai) VALUES
-- 10 SE trains (Tàu Thống Nhất 1 to 10)
('2025SE000001', N'Tàu Thống Nhất 1', 'SE', 'hoatDong'),
('2025SE000002', N'Tàu Thống Nhất 2', 'SE', 'baoTri'),
('2025SE000003', N'Tàu Thống Nhất 3', 'SE', 'hoatDong'),
('2025SE000004', N'Tàu Thống Nhất 4', 'SE', 'baoTri'),
('2025SE000005', N'Tàu Thống Nhất 5', 'SE', 'hoatDong'),
('2025SE000006', N'Tàu Thống Nhất 6', 'SE', 'baoTri'),
('2025SE000007', N'Tàu Thống Nhất 7', 'SE', 'hoatDong'),
('2025SE000008', N'Tàu Thống Nhất 8', 'SE', 'baoTri'),
('2025SE000009', N'Tàu Thống Nhất 9', 'SE', 'hoatDong'),
('2025SE000010', N'Tàu Thống Nhất 10', 'SE', 'baoTri'),
-- 10 TN trains (Tàu Miền Trung 1 to 10)
('2025TN000001', N'Tàu Nhanh 1', 'TN', 'hoatDong'),
('2025TN000002', N'Tàu Nhanh 2', 'TN', 'baoTri'),
('2025TN000003', N'Tàu Nhanh 3', 'TN', 'hoatDong'),
('2025TN000004', N'Tàu Nhanh 4', 'TN', 'baoTri'),
('2025TN000005', N'Tàu Nhanh 5', 'TN', 'hoatDong'),
('2025TN000006', N'Tàu Nhanh 6', 'TN', 'baoTri'),
('2025TN000007', N'Tàu Nhanh 7', 'TN', 'hoatDong'),
('2025TN000008', N'Tàu Nhanh 8', 'TN', 'baoTri'),
('2025TN000009', N'Tàu Nhanh 9', 'TN', 'hoatDong'),
('2025TN000010', N'Tàu Nhanh 10', 'TN', 'baoTri');
GO

INSERT INTO Toa (maToa, tenToa, loaiToa, trangThai, maTau) VALUES
-- Tàu '2025SE000001' (SE, 10 toa) - Only ngoiMemDieuHoa and giuongNamDieuHoa
('2025SE00000101', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000001'),
('2025SE00000102', N'Toa số 2', 'giuongNamDieuHoa', 'hoatDong', '2025SE000001'),
('2025SE00000103', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000001'),
('2025SE00000104', N'Toa số 4', 'giuongNamDieuHoa', 'hoatDong', '2025SE000001'),
('2025SE00000105', N'Toa số 5', 'ngoiMemDieuHoa', 'voHieuHoa', '2025SE000001'),
('2025SE00000106', N'Toa số 6', 'giuongNamDieuHoa', 'hoatDong', '2025SE000001'),
('2025SE00000107', N'Toa số 7', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000001'),
('2025SE00000108', N'Toa số 8', 'giuongNamDieuHoa', 'hoatDong', '2025SE000001'),
('2025SE00000109', N'Toa số 9', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000001'),
('2025SE00000110', N'Toa số 10', 'giuongNamDieuHoa', 'hoatDong', '2025SE000001'),

-- Tàu '2025SE000002' (SE, 9 toa)
('2025SE00000201', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000002'),
('2025SE00000202', N'Toa số 2', 'giuongNamDieuHoa', 'hoatDong', '2025SE000002'),
('2025SE00000203', N'Toa số 3', 'ngoiMemDieuHoa', 'voHieuHoa', '2025SE000002'),
('2025SE00000204', N'Toa số 4', 'giuongNamDieuHoa', 'hoatDong', '2025SE000002'),
('2025SE00000205', N'Toa số 5', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000002'),
('2025SE00000206', N'Toa số 6', 'giuongNamDieuHoa', 'hoatDong', '2025SE000002'),
('2025SE00000207', N'Toa số 7', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000002'),
('2025SE00000208', N'Toa số 8', 'giuongNamDieuHoa', 'hoatDong', '2025SE000002'),
('2025SE00000209', N'Toa số 9', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000002'),

-- Tàu '2025SE000003' (SE, 8 toa)
('2025SE00000301', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000003'),
('2025SE00000302', N'Toa số 2', 'giuongNamDieuHoa', 'hoatDong', '2025SE000003'),
('2025SE00000303', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000003'),
('2025SE00000304', N'Toa số 4', 'giuongNamDieuHoa', 'voHieuHoa', '2025SE000003'),
('2025SE00000305', N'Toa số 5', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000003'),
('2025SE00000306', N'Toa số 6', 'giuongNamDieuHoa', 'hoatDong', '2025SE000003'),
('2025SE00000307', N'Toa số 7', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000003'),
('2025SE00000308', N'Toa số 8', 'giuongNamDieuHoa', 'hoatDong', '2025SE000003'),

-- Tàu '2025SE000004' (SE, 10 toa)
('2025SE00000401', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000004'),
('2025SE00000402', N'Toa số 2', 'giuongNamDieuHoa', 'hoatDong', '2025SE000004'),
('2025SE00000403', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000004'),
('2025SE00000404', N'Toa số 4', 'giuongNamDieuHoa', 'hoatDong', '2025SE000004'),
('2025SE00000405', N'Toa số 5', 'ngoiMemDieuHoa', 'voHieuHoa', '2025SE000004'),
('2025SE00000406', N'Toa số 6', 'giuongNamDieuHoa', 'hoatDong', '2025SE000004'),
('2025SE00000407', N'Toa số 7', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000004'),
('2025SE00000408', N'Toa số 8', 'giuongNamDieuHoa', 'hoatDong', '2025SE000004'),
('2025SE00000409', N'Toa số 9', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000004'),
('2025SE00000410', N'Toa số 10', 'giuongNamDieuHoa', 'hoatDong', '2025SE000004'),

-- Tàu '2025SE000005' (SE, 9 toa)
('2025SE00000501', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000005'),
('2025SE00000502', N'Toa số 2', 'giuongNamDieuHoa', 'hoatDong', '2025SE000005'),
('2025SE00000503', N'Toa số 3', 'ngoiMemDieuHoa', 'voHieuHoa', '2025SE000005'),
('2025SE00000504', N'Toa số 4', 'giuongNamDieuHoa', 'hoatDong', '2025SE000005'),
('2025SE00000505', N'Toa số 5', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000005'),
('2025SE00000506', N'Toa số 6', 'giuongNamDieuHoa', 'hoatDong', '2025SE000005'),
('2025SE00000507', N'Toa số 7', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000005'),
('2025SE00000508', N'Toa số 8', 'giuongNamDieuHoa', 'hoatDong', '2025SE000005'),
('2025SE00000509', N'Toa số 9', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000005'),

-- Tàu '2025SE000006' (SE, 8 toa)
('2025SE00000601', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000006'),
('2025SE00000602', N'Toa số 2', 'giuongNamDieuHoa', 'hoatDong', '2025SE000006'),
('2025SE00000603', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000006'),
('2025SE00000604', N'Toa số 4', 'giuongNamDieuHoa', 'voHieuHoa', '2025SE000006'),
('2025SE00000605', N'Toa số 5', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000006'),
('2025SE00000606', N'Toa số 6', 'giuongNamDieuHoa', 'hoatDong', '2025SE000006'),
('2025SE00000607', N'Toa số 7', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000006'),
('2025SE00000608', N'Toa số 8', 'giuongNamDieuHoa', 'hoatDong', '2025SE000006'),

-- Tàu '2025SE000007' (SE, 10 toa)
('2025SE00000701', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000007'),
('2025SE00000702', N'Toa số 2', 'giuongNamDieuHoa', 'hoatDong', '2025SE000007'),
('2025SE00000703', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000007'),
('2025SE00000704', N'Toa số 4', 'giuongNamDieuHoa', 'hoatDong', '2025SE000007'),
('2025SE00000705', N'Toa số 5', 'ngoiMemDieuHoa', 'voHieuHoa', '2025SE000007'),
('2025SE00000706', N'Toa số 6', 'giuongNamDieuHoa', 'hoatDong', '2025SE000007'),
('2025SE00000707', N'Toa số 7', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000007'),
('2025SE00000708', N'Toa số 8', 'giuongNamDieuHoa', 'hoatDong', '2025SE000007'),
('2025SE00000709', N'Toa số 9', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000007'),
('2025SE00000710', N'Toa số 10', 'giuongNamDieuHoa', 'hoatDong', '2025SE000007'),

-- Tàu '2025SE000008' (SE, 9 toa)
('2025SE00000801', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000008'),
('2025SE00000802', N'Toa số 2', 'giuongNamDieuHoa', 'hoatDong', '2025SE000008'),
('2025SE00000803', N'Toa số 3', 'ngoiMemDieuHoa', 'voHieuHoa', '2025SE000008'),
('2025SE00000804', N'Toa số 4', 'giuongNamDieuHoa', 'hoatDong', '2025SE000008'),
('2025SE00000805', N'Toa số 5', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000008'),
('2025SE00000806', N'Toa số 6', 'giuongNamDieuHoa', 'hoatDong', '2025SE000008'),
('2025SE00000807', N'Toa số 7', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000008'),
('2025SE00000808', N'Toa số 8', 'giuongNamDieuHoa', 'hoatDong', '2025SE000008'),
('2025SE00000809', N'Toa số 9', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000008'),

-- Tàu '2025SE000009' (SE, 8 toa)
('2025SE00000901', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000009'),
('2025SE00000902', N'Toa số 2', 'giuongNamDieuHoa', 'hoatDong', '2025SE000009'),
('2025SE00000903', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000009'),
('2025SE00000904', N'Toa số 4', 'giuongNamDieuHoa', 'voHieuHoa', '2025SE000009'),
('2025SE00000905', N'Toa số 5', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000009'),
('2025SE00000906', N'Toa số 6', 'giuongNamDieuHoa', 'hoatDong', '2025SE000009'),
('2025SE00000907', N'Toa số 7', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000009'),
('2025SE00000908', N'Toa số 8', 'giuongNamDieuHoa', 'hoatDong', '2025SE000009'),

-- Tàu '2025SE000010' (SE, 10 toa)
('2025SE00001001', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000010'),
('2025SE00001002', N'Toa số 2', 'giuongNamDieuHoa', 'hoatDong', '2025SE000010'),
('2025SE00001003', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000010'),
('2025SE00001004', N'Toa số 4', 'giuongNamDieuHoa', 'hoatDong', '2025SE000010'),
('2025SE00001005', N'Toa số 5', 'ngoiMemDieuHoa', 'voHieuHoa', '2025SE000010'),
('2025SE00001006', N'Toa số 6', 'giuongNamDieuHoa', 'hoatDong', '2025SE000010'),
('2025SE00001007', N'Toa số 7', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000010'),
('2025SE00001008', N'Toa số 8', 'giuongNamDieuHoa', 'hoatDong', '2025SE000010'),
('2025SE00001009', N'Toa số 9', 'ngoiMemDieuHoa', 'hoatDong', '2025SE000010'),
('2025SE00001010', N'Toa số 10', 'giuongNamDieuHoa', 'hoatDong', '2025SE000010'),

-- Tàu '2025TN000001' (TN, 5 toa) - Only ngoiMemDieuHoa and gheCungDieuHoa
('2025TN00000101', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000001'),
('2025TN00000102', N'Toa số 2', 'gheCungDieuHoa', 'hoatDong', '2025TN000001'),
('2025TN00000103', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000001'),
('2025TN00000104', N'Toa số 4', 'gheCungDieuHoa', 'hoatDong', '2025TN000001'),
('2025TN00000105', N'Toa số 5', 'ngoiMemDieuHoa', 'voHieuHoa', '2025TN000001'),

-- Tàu '2025TN000002' (TN, 4 toa)
('2025TN00000201', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000002'),
('2025TN00000202', N'Toa số 2', 'gheCungDieuHoa', 'hoatDong', '2025TN000002'),
('2025TN00000203', N'Toa số 3', 'ngoiMemDieuHoa', 'voHieuHoa', '2025TN000002'),
('2025TN00000204', N'Toa số 4', 'gheCungDieuHoa', 'hoatDong', '2025TN000002'),

-- Tàu '2025TN000003' (TN, 5 toa)
('2025TN00000301', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000003'),
('2025TN00000302', N'Toa số 2', 'gheCungDieuHoa', 'hoatDong', '2025TN000003'),
('2025TN00000303', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000003'),
('2025TN00000304', N'Toa số 4', 'gheCungDieuHoa', 'voHieuHoa', '2025TN000003'),
('2025TN00000305', N'Toa số 5', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000003'),

-- Tàu '2025TN000004' (TN, 4 toa)
('2025TN00000401', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000004'),
('2025TN00000402', N'Toa số 2', 'gheCungDieuHoa', 'hoatDong', '2025TN000004'),
('2025TN00000403', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000004'),
('2025TN00000404', N'Toa số 4', 'gheCungDieuHoa', 'voHieuHoa', '2025TN000004'),

-- Tàu '2025TN000005' (TN, 5 toa)
('2025TN00000501', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000005'),
('2025TN00000502', N'Toa số 2', 'gheCungDieuHoa', 'hoatDong', '2025TN000005'),
('2025TN00000503', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000005'),
('2025TN00000504', N'Toa số 4', 'gheCungDieuHoa', 'hoatDong', '2025TN000005'),
('2025TN00000505', N'Toa số 5', 'ngoiMemDieuHoa', 'voHieuHoa', '2025TN000005'),

-- Tàu '2025TN000006' (TN, 4 toa)
('2025TN00000601', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000006'),
('2025TN00000602', N'Toa số 2', 'gheCungDieuHoa', 'hoatDong', '2025TN000006'),
('2025TN00000603', N'Toa số 3', 'ngoiMemDieuHoa', 'voHieuHoa', '2025TN000006'),
('2025TN00000604', N'Toa số 4', 'gheCungDieuHoa', 'hoatDong', '2025TN000006'),

-- Tàu '2025TN000007' (TN, 5 toa)
('2025TN00000701', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000007'),
('2025TN00000702', N'Toa số 2', 'gheCungDieuHoa', 'hoatDong', '2025TN000007'),
('2025TN00000703', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000007'),
('2025TN00000704', N'Toa số 4', 'gheCungDieuHoa', 'voHieuHoa', '2025TN000007'),
('2025TN00000705', N'Toa số 5', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000007'),

-- Tàu '2025TN000008' (TN, 4 toa)
('2025TN00000801', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000008'),
('2025TN00000802', N'Toa số 2', 'gheCungDieuHoa', 'hoatDong', '2025TN000008'),
('2025TN00000803', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000008'),
('2025TN00000804', N'Toa số 4', 'gheCungDieuHoa', 'voHieuHoa', '2025TN000008'),

-- Tàu '2025TN000009' (TN, 5 toa)
('2025TN00000901', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000009'),
('2025TN00000902', N'Toa số 2', 'gheCungDieuHoa', 'hoatDong', '2025TN000009'),
('2025TN00000903', N'Toa số 3', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000009'),
('2025TN00000904', N'Toa số 4', 'gheCungDieuHoa', 'hoatDong', '2025TN000009'),
('2025TN00000905', N'Toa số 5', 'ngoiMemDieuHoa', 'voHieuHoa', '2025TN000009'),

-- Tàu '2025TN000010' (TN, 4 toa)
('2025TN00001001', N'Toa số 1', 'ngoiMemDieuHoa', 'hoatDong', '2025TN000010'),
('2025TN00001002', N'Toa số 2', 'gheCungDieuHoa', 'hoatDong', '2025TN000010'),
('2025TN00001003', N'Toa số 3', 'ngoiMemDieuHoa', 'voHieuHoa', '2025TN000010'),
('2025TN00001004', N'Toa số 4', 'gheCungDieuHoa', 'hoatDong', '2025TN000010');
GO

CREATE TABLE ChuyenTau (
    maChuyenTau VARCHAR(12) PRIMARY KEY, -- Mã chuyến tàu, khóa chính
    ngayKhoiHanh DATE NOT NULL,         -- Ngày khởi hành
    gioKhoiHanh TIME NOT NULL,          -- Giờ khởi hành
    ngayDuKien DATE NOT NULL,           -- Ngày dự kiến đến
    gioDuKien TIME NOT NULL,            -- Giờ dự kiến đến
    maTau VARCHAR(12) NOT NULL,         -- Mã tàu, khóa ngoại
    maTuyen VARCHAR(12) NOT NULL,    -- Mã tuyến tàu, khóa ngoại

    -- Ràng buộc khóa ngoại
    CONSTRAINT FK_ChuyenTau_Tau FOREIGN KEY (maTau) 
        REFERENCES Tau(maTau) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_ChuyenTau_TuyenTau FOREIGN KEY (maTuyen) 
        REFERENCES TuyenTau(maTuyen) ON DELETE CASCADE ON UPDATE CASCADE,

    -- Ràng buộc logic: Ngày và giờ dự kiến phải sau ngày và giờ khởi hành
    CONSTRAINT CHK_NgayGioDuKien 
        CHECK (
            (ngayDuKien > ngayKhoiHanh) OR 
            (ngayDuKien = ngayKhoiHanh AND gioDuKien > gioKhoiHanh)
        )
);
GO

INSERT INTO ChuyenTau (maChuyenTau, ngayKhoiHanh, gioKhoiHanh, ngayDuKien, gioDuKien, maTau, maTuyen) VALUES
-- Tàu SE (SHIP)
('080020250411SE1', '2025-04-11', '08:00:00', '2025-04-12', '18:32:00', '2025SE000001', '2025TT0001'), -- Hà Nội - Sài Gòn (1726 km, 34.52 giờ)
('100020250412SE2', '2025-04-12', '10:00:00', '2025-04-13', '02:00:00', '2025SE000002', '2025TT0002'), -- Hà Nội - Đà Nẵng (800 km, 16 giờ)
('120020250413SE3', '2025-04-13', '12:00:00', '2025-04-14', '01:00:00', '2025SE000003', '2025TT0003'), -- Hà Nội - Huế (650 km, 13 giờ)
('140020250414SE4', '2025-04-14', '14:00:00', '2025-04-15', '16:00:00', '2025SE000004', '2025TT0004'), -- Hà Nội - Nha Trang (1300 km, 26 giờ)
('160020250415SE5', '2025-04-15', '16:00:00', '2025-04-16', '02:00:00', '2025SE000005', '2025TT0005'), -- Hà Nội - Đồng Hới (500 km, 10 giờ)
('180020250416SE6', '2025-04-16', '18:00:00', '2025-04-16', '21:00:00', '2025SE000006', '2025TT0006'), -- Hà Nội - Thanh Hóa (150 km, 3 giờ)
('200020250417SE7', '2025-04-17', '20:00:00', '2025-04-18', '02:00:00', '2025SE000007', '2025TT0007'), -- Hà Nội - Vinh (300 km, 6 giờ)
('220020250418SE8', '2025-04-18', '22:00:00', '2025-04-19', '00:00:00', '2025SE000008', '2025TT0008'), -- Hà Nội - Hải Phòng (100 km, 2 giờ)
('060020250419SE9', '2025-04-19', '06:00:00', '2025-04-20', '12:00:00', '2025SE000009', '2025TT0009'), -- Hà Nội - Đà Lạt (1500 km, 30 giờ)
('080020250420SE10', '2025-04-20', '08:00:00', '2025-04-21', '18:32:00', '2025SE000010', '2025TT0010'), -- Sài Gòn - Hà Nội (1726 km, 34.52 giờ)
-- Tàu TN
('100020250421TN1', '2025-04-21', '10:00:00', '2025-04-22', '04:00:00', '2025TN000001', '2025TT0011'), -- Sài Gòn - Đà Nẵng (900 km, 18 giờ)
('120020250422TN2', '2025-04-22', '12:00:00', '2025-04-23', '09:32:00', '2025TN000002', '2025TT0012'), -- Sài Gòn - Huế (1076 km, 21.52 giờ)
('140020250423TN3', '2025-04-23', '14:00:00', '2025-04-23', '22:00:00', '2025TN000003', '2025TT0013'), -- Sài Gòn - Nha Trang (400 km, 8 giờ)
('160020250424TN4', '2025-04-24', '16:00:00', '2025-04-25', '16:32:00', '2025TN000004', '2025TT0014'), -- Sài Gòn - Đồng Hới (1226 km, 24.52 giờ)
('180020250425TN5', '2025-04-25', '18:00:00', '2025-04-26', '19:32:00', '2025TN000005', '2025TT0015'), -- Sài Gòn - Thanh Hóa (1576 km, 31.52 giờ)
('200020250426TN6', '2025-04-26', '20:00:00', '2025-04-27', '20:32:00', '2025TN000006', '2025TT0016'), -- Sài Gòn - Vinh (1426 km, 28.52 giờ)
('220020250427TN7', '2025-04-27', '22:00:00', '2025-04-29', '00:32:00', '2025TN000007', '2025TT0017'), -- Sài Gòn - Hải Phòng (1826 km, 36.52 giờ)
('060020250428TN8', '2025-04-28', '06:00:00', '2025-04-28', '18:00:00', '2025TN000008', '2025TT0018'), -- Sài Gòn - Đà Lạt (600 km, 12 giờ)
('080020250429TN9', '2025-04-29', '08:00:00', '2025-04-30', '00:00:00', '2025TN000009', '2025TT0019'), -- Đà Nẵng - Hà Nội (800 km, 16 giờ)
('100020250430TN10', '2025-04-30', '10:00:00', '2025-04-30', '22:00:00', '2025TN000010', '2025TT0020'); -- Đà Nẵng - Sài Gòn (900 km, 18 giờ)
GO
