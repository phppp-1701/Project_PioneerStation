-- Tạo cơ sở dữ liệu
CREATE DATABASE pioneer_station;
GO

USE pioneer_station;
GO

-- Tạo bảng KhachHang
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

-- Chèn dữ liệu vào bảng KhachHang (300 khách hàng)
INSERT INTO KhachHang (maKhachHang, tenKhachHang, CCCD_HoChieu, soDienThoai, email, loaiThanhVien, trangThaiKhachHang)
VALUES
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

-- Thêm 290 khách hàng nữa với tên đa dạng hơn
DECLARE @i INT = 11;
DECLARE @maKH VARCHAR(12);
DECLARE @cccd VARCHAR(12);
DECLARE @sdt VARCHAR(10);
DECLARE @email VARCHAR(50);
DECLARE @trangThai VARCHAR(10);
DECLARE @loaiTV VARCHAR(10);

-- Danh sách họ phổ biến
DECLARE @ho TABLE (id INT, ho NVARCHAR(20));
INSERT INTO @ho VALUES 
(1, N'Nguyễn'), (2, N'Trần'), (3, N'Lê'), (4, N'Phạm'), (5, N'Hoàng'),
(6, N'Phan'), (7, N'Vũ'), (8, N'Võ'), (9, N'Đặng'), (10, N'Bùi'),
(11, N'Đỗ'), (12, N'Hồ'), (13, N'Ngô'), (14, N'Dương'), (15, N'Lý'),
(16, N'Đào'), (17, N'Đinh'), (18, N'Mai'), (19, N'Trương'), (20, N'Chu');

-- Danh sách tên đệm và tên nam
DECLARE @tenNam TABLE (id INT, ten NVARCHAR(20));
INSERT INTO @tenNam VALUES
(1, N'Văn Anh'), (2, N'Văn Bình'), (3, N'Mạnh Cường'), (4, N'Đức Duy'),
(5, N'Văn Đạt'), (6, N'Quang Hải'), (7, N'Minh Hiếu'), (8, N'Văn Khánh'),
(9, N'Bảo Long'), (10, N'Thanh Nam'), (11, N'Văn Phúc'), (12, N'Quang Sáng'),
(13, N'Văn Tài'), (14, N'Minh Thành'), (15, N'Văn Trường'), (16, N'Đình Tuấn'),
(17, N'Văn Việt'), (18, N'Xuân Vũ'), (19, N'Văn Quân'), (20, N'Tiến Đạt');

-- Danh sách tên đệm và tên nữ
DECLARE @tenNu TABLE (id INT, ten NVARCHAR(20));
INSERT INTO @tenNu VALUES
(1, N'Thị Ánh'), (2, N'Thị Bích'), (3, N'Thị Cẩm'), (4, N'Phương Diễm'),
(5, N'Thị Giang'), (6, N'Thị Hà'), (7, N'Thị Hương'), (8, N'Thị Kiều'),
(9, N'Thị Lan'), (10, N'Thị Mai'), (11, N'Phương Nga'), (12, N'Thị Ngọc'),
(13, N'Thị Nhung'), (14, N'Thị Oanh'), (15, N'Thị Phương'), (16, N'Thị Quỳnh'),
(17, N'Thị Thảo'), (18, N'Thị Uyên'), (19, N'Thị Vân'), (20, N'Thị Yến');

WHILE @i <= 300
BEGIN
    SET @maKH = '2024KH' + RIGHT('000000' + CAST(@i AS VARCHAR(6)), 6);
    SET @cccd = '079123' + RIGHT('000000' + CAST(@i AS VARCHAR(6)), 6);
    SET @sdt = '09' + RIGHT('00000000' + CAST(CAST(RAND() * 99999999 AS INT) AS VARCHAR(8)), 8);
    
    -- Chọn ngẫu nhiên giới tính (50% nam, 50% nữ)
    DECLARE @gioiTinh INT = CAST(RAND() * 2 AS INT) + 1;
    
    -- Tạo tên khách hàng
    DECLARE @ten NVARCHAR(50);
    DECLARE @hoKhach NVARCHAR(20);
    DECLARE @tenDemVaTen NVARCHAR(20);
    
    -- Chọn họ ngẫu nhiên
    SELECT @hoKhach = ho FROM @ho WHERE id = CAST(RAND() * 20 AS INT) + 1;
    
    -- Chọn tên theo giới tính
    IF @gioiTinh = 1 -- Nam
    BEGIN
        SELECT @tenDemVaTen = ten FROM @tenNam WHERE id = CAST(RAND() * 20 AS INT) + 1;
    END
    ELSE -- Nữ
    BEGIN
        SELECT @tenDemVaTen = ten FROM @tenNu WHERE id = CAST(RAND() * 20 AS INT) + 1;
    END
    
    SET @ten = @hoKhach + ' ' + @tenDemVaTen;
    
-- Phần tạo email sửa lại như sau:
SET @email = LOWER(
    REPLACE(
        REPLACE(
            REPLACE(
                REPLACE(
                    REPLACE(
                        REPLACE(
                            REPLACE(
                                REPLACE(@ten, N' ', ''),
                                N'Đ', 'D'
                            ),
                            N'đ', 'd'
                        ),
                        N'áàảãạâấầẩẫậăắằẳẵặ', 'a'
                    ),
                    N'éèẻẽẹêếềểễệ', 'e'
                ),
                N'íìỉĩị', 'i'
            ),
            N'óòỏõọôốồổỗộơớờởỡợ', 'o'
        ),
        N'úùủũụưứừửữự', 'u'
    ) 
    + CAST(@i % 100 AS VARCHAR(2)) + '@gmail.com'
);
    
    -- Xác định loại thành viên (80% thành thật, 20% thường)
    SET @loaiTV = CASE WHEN @i % 5 = 0 THEN 'vip' ELSE 'thanThiet' END;
    
    -- Xác định trạng thái (90% hoạt động, 10% vô hiệu hóa)
    SET @trangThai = CASE WHEN @i % 10 = 0 THEN 'voHieuHoa' ELSE 'hoatDong' END;
    
    INSERT INTO KhachHang (maKhachHang, tenKhachHang, CCCD_HoChieu, soDienThoai, email, loaiThanhVien, trangThaiKhachHang)
    VALUES (@maKH, @ten, @cccd, @sdt, @email, @loaiTV, @trangThai);
    
    SET @i = @i + 1;
END;
GO

-- Tạo bảng NhanVien
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

-- Chèn dữ liệu vào bảng NhanVien
INSERT INTO NhanVien (maNhanVien, tenNhanVien, ngaySinh, soDienThoai, email, gioiTinh, CCCD_HoChieu, chucVu, trangThaiNhanVien, linkAnh)
VALUES
('2022NV000001', N'Phạm Trương Hoàng Phương', '2004-01-17', '0947991755', 'hphuong17@gmail.com', 'nam', '083204007207', 'quanLy', 'hoatDong', 'image/phuong.jpg'),
('2022NV000002', N'Phạm Viết Quân', '2004-09-29', '0987654321', 'pvquan29@gmail.com', 'nam', '002190654321', 'quanLy', 'hoatDong', 'image/quan.jpg'),
('2022NV000003', N'Bùi Tấn Quang Trung', '2004-08-10', '0978123456', 'trinhbuc08@gmail.com', 'nam', '003095987654', 'quanLy', 'hoatDong', 'image/trung.jpg'),
('2022NV000004', N'Trần Minh Tuấn', '2004-09-18', '0965432187', 'tuantran18@gmail.com', 'nam', '004092456789', 'quanLy', 'hoatDong', 'image/tuan.jpg'),
('2023NV000001', N'Nguyễn Thị Trinh', '2006-03-24', '0932342234', 'trinhwork24@gmail.com', 'nu', '029323131234', 'banVe', 'hoatDong', 'image/trinh.jpg');
GO

-- Tạo bảng TaiKhoan
CREATE TABLE TaiKhoan (
    tenTaiKhoan VARCHAR(20),
    matKhau NVARCHAR(100),
    maNhanVien VARCHAR(20) FOREIGN KEY REFERENCES NhanVien(maNhanVien)
);
GO

-- Chèn dữ liệu vào bảng TaiKhoan
INSERT INTO TaiKhoan (tenTaiKhoan, matKhau, maNhanVien)
VALUES
('hphuong17', 'phuong123', '2022NV000001'),
('pvquan29', 'quan456', '2022NV000002'),
('trungbtq', 'trung789', '2022NV000003'),
('tuanminh', 'tuan101', '2022NV000004'),
('trinhnt', 'trinh202', '2023NV000001');
GO

-- Tạo bảng Ga
CREATE TABLE Ga (
    maGa VARCHAR(20) PRIMARY KEY,
    tenGa NVARCHAR(100),
    diaChi NVARCHAR(255)
);
GO

-- Chèn dữ liệu vào bảng Ga
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
GO

-- Tạo bảng TuyenTau
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

-- Chèn dữ liệu vào bảng TuyenTau
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

-- Tạo bảng Tau
CREATE TABLE Tau (
    maTau VARCHAR(20) PRIMARY KEY,
    tenTau NVARCHAR(100) NOT NULL,
    loaiTau VARCHAR(20) CHECK (loaiTau IN ('SE', 'TN')),
    trangThai VARCHAR(20) CHECK (trangThai IN ('hoatDong', 'baoTri'))
);
GO

-- Chèn 30 tàu SE (Sài Gòn Express)
DECLARE @i INT = 1;
DECLARE @year INT;
DECLARE @maTau VARCHAR(20);
DECLARE @tenTau NVARCHAR(100);
DECLARE @trangThai VARCHAR(20);

WHILE @i <= 30
BEGIN
    -- Chọn năm ngẫu nhiên từ 2015 đến 2025
    SET @year = 2015 + CAST(RAND() * 11 AS INT);
    
    SET @maTau = CAST(@year AS VARCHAR(4)) + 'SE' + RIGHT('000000' + CAST(@i AS VARCHAR(6)), 6);
    SET @tenTau = 'SE' + RIGHT('00' + CAST(@i AS VARCHAR(2)), 2);
    
    -- 85% hoạt động, 15% bảo trì
    SET @trangThai = CASE WHEN @i % 7 = 0 THEN 'baoTri' ELSE 'hoatDong' END;
    
    INSERT INTO Tau (maTau, tenTau, loaiTau, trangThai)
    VALUES (@maTau, @tenTau, 'SE', @trangThai);
    
    SET @i = @i + 1;
END;
GO

-- Chèn 30 tàu TN (Thống Nhất)
DECLARE @j INT = 1;
DECLARE @yearTN INT;
DECLARE @maTauTN VARCHAR(20);
DECLARE @tenTauTN NVARCHAR(100);
DECLARE @trangThaiTN VARCHAR(20);

WHILE @j <= 30
BEGIN
    -- Chọn năm ngẫu nhiên từ 2010 đến 2025
    SET @yearTN = 2010 + CAST(RAND() * 16 AS INT);
    
    SET @maTauTN = CAST(@yearTN AS VARCHAR(4)) + 'TN' + RIGHT('000000' + CAST(@j AS VARCHAR(6)), 6);
    SET @tenTauTN = 'TN' + RIGHT('00' + CAST(@j AS VARCHAR(2)), 2);
    
    -- 90% hoạt động, 10% bảo trì
    SET @trangThaiTN = CASE WHEN @j % 10 = 0 THEN 'baoTri' ELSE 'hoatDong' END;
    
    INSERT INTO Tau (maTau, tenTau, loaiTau, trangThai)
    VALUES (@maTauTN, @tenTauTN, 'TN', @trangThaiTN);
    
    SET @j = @j + 1;
END;
GO

-- Tạo bảng Toa
CREATE TABLE Toa (
    maToa VARCHAR(20) PRIMARY KEY,
    tenToa NVARCHAR(100) NOT NULL,
    loaiToa VARCHAR(20) CHECK (loaiToa IN ('ngoiMemDieuHoa', 'giuongNamDieuHoa', 'gheCungDieuHoa')),
    trangThai VARCHAR(20) CHECK (trangThai IN ('hoatDong', 'voHieuHoa')),
    maTau VARCHAR(20) FOREIGN KEY REFERENCES Tau(maTau)
);
GO

-- Tạo toa cho các tàu TN (Thống Nhất)
DECLARE @maTauTN VARCHAR(20);
DECLARE @soToaTN INT;
DECLARE @i INT;
DECLARE @maToa VARCHAR(20);
DECLARE @tenToa NVARCHAR(100);
DECLARE @loaiToa VARCHAR(20);
DECLARE @trangThai VARCHAR(20);

-- Lấy danh sách tàu TN
DECLARE cursorTauTN CURSOR FOR 
SELECT maTau FROM Tau WHERE loaiTau = 'TN';

OPEN cursorTauTN;
FETCH NEXT FROM cursorTauTN INTO @maTauTN;

WHILE @@FETCH_STATUS = 0
BEGIN
    -- Mỗi tàu TN có từ 4 đến 6 toa
    SET @soToaTN = 4 + CAST(RAND() * 3 AS INT); -- Random 4-6
    
    SET @i = 1;
    WHILE @i <= @soToaTN
    BEGIN
        SET @maToa = @maTauTN + RIGHT('00' + CAST(@i AS VARCHAR(2)), 2);
        SET @tenToa = RIGHT('00' + CAST(@i AS VARCHAR(2)), 2);
        
        -- Tàu TN chỉ có ngoiMemDieuHoa hoặc giuongNamDieuHoa
        SET @loaiToa = CASE 
                          WHEN @i % 2 = 0 THEN 'ngoiMemDieuHoa' 
                          ELSE 'gheCungDieuHoa' 
                        END;
        
        -- 90% hoạt động, 10% vô hiệu hóa
        SET @trangThai = CASE WHEN @i % 10 = 0 THEN 'voHieuHoa' ELSE 'hoatDong' END;
        
        INSERT INTO Toa (maToa, tenToa, loaiToa, trangThai, maTau)
        VALUES (@maToa, @tenToa, @loaiToa, @trangThai, @maTauTN);
        
        SET @i = @i + 1;
    END
    
    FETCH NEXT FROM cursorTauTN INTO @maTauTN;
END

CLOSE cursorTauTN;
DEALLOCATE cursorTauTN;
GO

-- Tạo toa cho các tàu SE (Sài Gòn Express)
DECLARE @maTauSE VARCHAR(20);
DECLARE @soToaSE INT;
DECLARE @j INT;
-- Khai báo lại các biến cần thiết
DECLARE @maToaSE VARCHAR(20);  -- Đổi tên biến để tránh trùng
DECLARE @tenToaSE NVARCHAR(100);
DECLARE @loaiToaSE VARCHAR(20);
DECLARE @trangThaiSE VARCHAR(20);

-- Lấy danh sách tàu SE
DECLARE cursorTauSE CURSOR FOR 
SELECT maTau FROM Tau WHERE loaiTau = 'SE';

OPEN cursorTauSE;
FETCH NEXT FROM cursorTauSE INTO @maTauSE;

WHILE @@FETCH_STATUS = 0
BEGIN
    -- Mỗi tàu SE có từ 8 đến 12 toa
    SET @soToaSE = 8 + CAST(RAND() * 5 AS INT); -- Random 8-12
    
    SET @j = 1;
    WHILE @j <= @soToaSE
    BEGIN
        SET @maToaSE = @maTauSE + RIGHT('00' + CAST(@j AS VARCHAR(2)), 2);
        SET @tenToaSE = RIGHT('00' + CAST(@j AS VARCHAR(2)), 2);
        
        -- Tàu SE chỉ có giuongNamDieuHoa hoặc gheCungDieuHoa
        SET @loaiToaSE = CASE 
                          WHEN @j % 2 = 0 THEN 'giuongNamDieuHoa' 
                          ELSE 'ngoiMemDieuHoa' 
                        END;
        
        -- 95% hoạt động, 5% vô hiệu hóa
        SET @trangThaiSE = CASE WHEN @j % 20 = 0 THEN 'voHieuHoa' ELSE 'hoatDong' END;
        
        INSERT INTO Toa (maToa, tenToa, loaiToa, trangThai, maTau)
        VALUES (@maToaSE, @tenToaSE, @loaiToaSE, @trangThaiSE, @maTauSE);
        
        SET @j = @j + 1;
    END
    
    FETCH NEXT FROM cursorTauSE INTO @maTauSE;
END

CLOSE cursorTauSE;
DEALLOCATE cursorTauSE;
GO

-- Tạo bảng ChuyenTau
CREATE TABLE ChuyenTau (
    maChuyenTau VARCHAR(30) PRIMARY KEY, -- Mã chuyến tàu, khóa chính
    ngayKhoiHanh DATE NOT NULL,         -- Ngày khởi hành
    gioKhoiHanh TIME NOT NULL,          -- Giờ khởi hành
    ngayDuKien DATE NOT NULL,           -- Ngày dự kiến đến
    gioDuKien TIME NOT NULL,            -- Giờ dự kiến đến
    maTau VARCHAR(20) NOT NULL,         -- Sửa thành VARCHAR(20)
    maTuyen VARCHAR(20) NOT NULL,       -- Sửa thành VARCHAR(20)

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

SET NOCOUNT ON;

DECLARE @ngayHienTai DATE = '2025-04-11'; -- Ngày bắt đầu
DECLARE @ngayKetThuc DATE = '2025-06-12'; -- Ngày kết thúc
DECLARE @ngayKhoiHanh DATE;
DECLARE @maTuyen VARCHAR(20);
DECLARE @khoangCach FLOAT;
DECLARE @maTau VARCHAR(20);
DECLARE @tenTau NVARCHAR(100);
DECLARE @maChuyenTau VARCHAR(30);
DECLARE @gioKhoiHanh TIME;
DECLARE @gioDuKien TIME;
DECLARE @ngayDuKien DATE;
DECLARE @thoiGianHanhTrinh FLOAT;
DECLARE @tongThoiGian INT;
DECLARE @gio INT;

-- Tạo bảng tạm để lưu lịch trình tàu
DECLARE @LichTau TABLE (
    maTau VARCHAR(20),
    ngayKhoiHanh DATE,
    gioKhoiHanh TIME,
    PRIMARY KEY (maTau, ngayKhoiHanh)
);

-- Cursor để duyệt qua các tuyến tàu
DECLARE cursorTuyenTau CURSOR FOR
SELECT maTuyen, khoangCach
FROM TuyenTau;

OPEN cursorTuyenTau;
FETCH NEXT FROM cursorTuyenTau INTO @maTuyen, @khoangCach;

WHILE @@FETCH_STATUS = 0
BEGIN
    -- Duyệt qua từng ngày
    SET @ngayKhoiHanh = @ngayHienTai;
    
    WHILE @ngayKhoiHanh <= @ngayKetThuc
    BEGIN
        -- Chọn tàu ngẫu nhiên chưa có lịch chạy trong ngày này
        SELECT TOP 1 @maTau = t.maTau, @tenTau = t.tenTau
        FROM Tau t
        WHERE t.trangThai = 'hoatDong'
          AND NOT EXISTS (
              SELECT 1 FROM @LichTau lt 
              WHERE lt.maTau = t.maTau 
                AND lt.ngayKhoiHanh = @ngayKhoiHanh
          )
        ORDER BY NEWID();
        
        -- Nếu tìm thấy tàu phù hợp
        IF @maTau IS NOT NULL
        BEGIN
            -- Chọn giờ khởi hành ngẫu nhiên từ 6h đến 18h
            SET @gio = 6 + CAST(RAND() * 12 AS INT);
            SET @gioKhoiHanh = DATEADD(HOUR, @gio, '00:00:00');
            
            -- Tính thời gian hành trình (tốc độ 50km/h)
            SET @thoiGianHanhTrinh = @khoangCach / 50.0;
            SET @tongThoiGian = CEILING(@thoiGianHanhTrinh * 60);
            
            -- Tính ngày/giờ đến
            DECLARE @thoiGianDen DATETIME = DATEADD(MINUTE, @tongThoiGian, 
                  CAST(@ngayKhoiHanh AS DATETIME) + CAST(@gioKhoiHanh AS DATETIME));
            SET @ngayDuKien = CAST(@thoiGianDen AS DATE);
            SET @gioDuKien = CAST(@thoiGianDen AS TIME);
            
            -- Tạo mã chuyến tàu: gioKhoiHanh (HHmm) + ngayKhoiHanh (yyyyMMdd) + tenTau
            SET @maChuyenTau = REPLACE(CONVERT(VARCHAR(5), @gioKhoiHanh, 108), ':', '') + 
                              FORMAT(@ngayKhoiHanh, 'yyyyMMdd') + 
                              REPLACE(@tenTau, ' ', ''); -- Loại bỏ khoảng trắng trong tenTau
            
            -- Chèn dữ liệu vào bảng ChuyenTau
            INSERT INTO ChuyenTau (
                maChuyenTau, ngayKhoiHanh, gioKhoiHanh, 
                ngayDuKien, gioDuKien, maTau, maTuyen
            )
            VALUES (
                @maChuyenTau,
                @ngayKhoiHanh,
                @gioKhoiHanh,
                @ngayDuKien,
                @gioDuKien,
                @maTau,
                @maTuyen
            );
            
            -- Ghi nhận lịch trình tàu
            INSERT INTO @LichTau (maTau, ngayKhoiHanh, gioKhoiHanh)
            VALUES (@maTau, @ngayKhoiHanh, @gioKhoiHanh);
            
            PRINT 'Đã tạo chuyến: ' + @maChuyenTau + 
                  ', Tàu: ' + @maTau + 
                  ', Ngày: ' + CONVERT(VARCHAR, @ngayKhoiHanh, 103) +
                  ', Giờ: ' + CONVERT(VARCHAR, @gioKhoiHanh, 108);
        END
        
        -- Tăng ngày
        SET @ngayKhoiHanh = DATEADD(DAY, 1, @ngayKhoiHanh);
    END
    
    FETCH NEXT FROM cursorTuyenTau INTO @maTuyen, @khoangCach;
END

CLOSE cursorTuyenTau;
DEALLOCATE cursorTuyenTau;

PRINT 'Hoàn thành tạo dữ liệu chuyến tàu';
SET NOCOUNT OFF;
GO

-- Tạo bảng ChoNgoi
CREATE TABLE ChoNgoi (
    maChoNgoi VARCHAR(30) PRIMARY KEY, -- Mã chỗ ngồi, khóa chính
    tenChoNgoi NVARCHAR(50) NOT NULL, -- Tên chỗ ngồi
    trangThai VARCHAR(10) CHECK (trangThai IN ('daDat', 'chuaDat', 'dangDat')), -- Trạng thái chỗ ngồi
    maToa VARCHAR(20) NOT NULL, -- Mã toa tham chiếu đến bảng Toa
    maChuyenTau VARCHAR(30) NOT NULL, -- Mã chuyến tàu tham chiếu đến bảng ChuyenTau
    giaCho DECIMAL(10, 2) NOT NULL CHECK (giaCho >= 0), -- Giá chỗ ngồi, không âm

    -- Ràng buộc khóa ngoại
    CONSTRAINT FK_ChoNgoi_Toa FOREIGN KEY (maToa) 
        REFERENCES Toa(maToa) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_ChoNgoi_ChuyenTau FOREIGN KEY (maChuyenTau) 
        REFERENCES ChuyenTau(maChuyenTau) ON DELETE CASCADE ON UPDATE CASCADE
);
GO

-- Script tạo dữ liệu cho bảng ChoNgoi (phiên bản đã sửa)
SET NOCOUNT ON;

DECLARE @maChuyenTau VARCHAR(30);
DECLARE @maTau VARCHAR(20);
DECLARE @loaiTau VARCHAR(20);
DECLARE @maToa VARCHAR(20);
DECLARE @tenToa NVARCHAR(100);
DECLARE @loaiToa VARCHAR(20);
DECLARE @soCho INT;
DECLARE @i INT;
DECLARE @maChoNgoi VARCHAR(30);
DECLARE @tenChoNgoi NVARCHAR(50);
DECLARE @giaCho DECIMAL(10, 2);

-- Cursor để duyệt qua các chuyến tàu
DECLARE cursorChuyenTau CURSOR LOCAL FOR  -- Thêm LOCAL để tránh xung đột
SELECT ct.maChuyenTau, ct.maTau, t.loaiTau
FROM ChuyenTau ct
JOIN Tau t ON ct.maTau = t.maTau;

OPEN cursorChuyenTau;
FETCH NEXT FROM cursorChuyenTau INTO @maChuyenTau, @maTau, @loaiTau;

WHILE @@FETCH_STATUS = 0
BEGIN
    PRINT 'Đang xử lý chuyến tàu: ' + @maChuyenTau;
    
    -- Cursor để duyệt qua các toa hoạt động của tàu
    DECLARE cursorToa CURSOR LOCAL FOR  -- Thêm LOCAL
    SELECT maToa, tenToa, loaiToa
    FROM Toa
    WHERE maTau = @maTau AND trangThai = 'hoatDong'
    ORDER BY maToa;  -- Thêm ORDER BY để đảm bảo thứ tự

    OPEN cursorToa;
    FETCH NEXT FROM cursorToa INTO @maToa, @tenToa, @loaiToa;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        PRINT '  Đang xử lý toa: ' + @maToa;
        
        -- Xác định số lượng chỗ ngồi dựa trên loại tàu và loại toa
        SET @soCho = CASE 
            WHEN @loaiTau = 'SE' AND @loaiToa = 'ngoiMemDieuHoa' THEN 50
            WHEN @loaiTau = 'SE' AND @loaiToa = 'giuongNamDieuHoa' THEN 35
            WHEN @loaiTau = 'TN' AND @loaiToa = 'ngoiMemDieuHoa' THEN 40
            WHEN @loaiTau = 'TN' AND @loaiToa = 'gheCungDieuHoa' THEN 50
            ELSE 0
        END;

        -- Xác định giá chỗ dựa trên loại toa
        SET @giaCho = CASE 
            WHEN @loaiTau = 'SE' AND @loaiToa = 'ngoiMemDieuHoa' THEN 500000.00
            WHEN @loaiTau = 'SE' AND @loaiToa = 'giuongNamDieuHoa' THEN 800000.00
            WHEN @loaiTau = 'TN' AND @loaiToa = 'ngoiMemDieuHoa' THEN 400000.00
            WHEN @loaiTau = 'TN' AND @loaiToa = 'gheCungDieuHoa' THEN 300000.00
            ELSE 0.00
        END;

        -- Tạo chỗ ngồi
        SET @i = 1;
        WHILE @i <= @soCho
        BEGIN
            -- Tạo tenChoNgoi (01, 02, ...)
            SET @tenChoNgoi = RIGHT('00' + CAST(@i AS VARCHAR(2)), 2);

            -- Tạo maChoNgoi: maChuyenTau + T + tenToa + CN + tenChoNgoi
            SET @maChoNgoi = @maChuyenTau + 'T' + @tenToa + 'CN' + @tenChoNgoi;

            -- Chèn dữ liệu vào bảng ChoNgoi
            BEGIN TRY
                INSERT INTO ChoNgoi (
                    maChoNgoi, tenChoNgoi, trangThai, maToa, maChuyenTau, giaCho
                )
                VALUES (
                    @maChoNgoi,
                    @tenChoNgoi,
                    'chuaDat',
                    @maToa,
                    @maChuyenTau,
                    @giaCho
                );
            END TRY
            BEGIN CATCH
                PRINT '    Lỗi khi tạo chỗ ngồi ' + @maChoNgoi + ': ' + ERROR_MESSAGE();
            END CATCH

            SET @i = @i + 1;
        END;

        PRINT '  Đã tạo ' + CAST(@soCho AS VARCHAR) + ' chỗ ngồi cho toa ' + @maToa;
        
        FETCH NEXT FROM cursorToa INTO @maToa, @tenToa, @loaiToa;
    END;

    CLOSE cursorToa;
    DEALLOCATE cursorToa;  -- Giải phóng cursor sau khi dùng

    FETCH NEXT FROM cursorChuyenTau INTO @maChuyenTau, @maTau, @loaiTau;
END;

CLOSE cursorChuyenTau;
DEALLOCATE cursorChuyenTau;

PRINT 'Hoàn thành tạo dữ liệu chỗ ngồi';
SET NOCOUNT OFF;
GO

