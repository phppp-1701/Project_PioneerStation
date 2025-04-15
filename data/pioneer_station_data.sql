-- Tạo cơ sở dữ liệu
CREATE DATABASE pioneer_station;
GO

USE pioneer_station;
GO

-- Tạo bảng KhachHang
CREATE TABLE KhachHang (
    maKhachHang VARCHAR(20) PRIMARY KEY,
    tenKhachHang NVARCHAR(100),
    CCCD_HoChieu VARCHAR(20) ,
    soDienThoai VARCHAR(15),
    email NVARCHAR(100),
    loaiThanhVien VARCHAR(20) CHECK (loaiThanhVien IN ('thanThiet', 'vip','khachVangLai')),
    trangThaiKhachHang VARCHAR(20) CHECK (trangThaiKhachHang IN ('hoatDong','voHieuHoa'))
);
GO

-- Chèn dữ liệu vào bảng KhachHang (300 khách hàng)
INSERT INTO KhachHang (maKhachHang, tenKhachHang, CCCD_HoChieu, soDienThoai, email, loaiThanhVien, trangThaiKhachHang)
VALUES
('KHVL',null, null, null, null, 'khachVangLai', 'hoatDong'),
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
('2021GA0009', N'Ga Hải Phòng', N'75 Lương Khánh Thiện, Hải Phòng');
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
('2025TT0008', '2020GA0001', '2021GA0009', 100),  -- Hà Nội to Hải Phòng
-- Sài Gòn (2018GA0002) to others
('2025TT0010', '2018GA0002', '2020GA0001', 1726), -- Sài Gòn to Hà Nội
('2025TT0011', '2018GA0002', '2019GA0003', 900),  -- Sài Gòn to Đà Nẵng
('2025TT0012', '2018GA0002', '2021GA0004', 1076), -- Sài Gòn to Huế
('2025TT0017', '2018GA0002', '2021GA0009', 1826), -- Sài Gòn to Hải Phòng
-- Đà Nẵng (2019GA0003) to others
('2025TT0019', '2019GA0003', '2020GA0001', 800),  -- Đà Nẵng to Hà Nội
('2025TT0020', '2019GA0003', '2018GA0002', 900),  -- Đà Nẵng to Sài Gòn
('2025TT0021', '2019GA0003', '2021GA0004', 100),  -- Đà Nẵng to Huế
('2025TT0026', '2019GA0003', '2021GA0009', 900),  -- Đà Nẵng to Hải Phòng
-- Huế (2021GA0004) to others
('2025TT0028', '2021GA0004', '2020GA0001', 650),  -- Huế to Hà Nội
('2025TT0029', '2021GA0004', '2018GA0002', 1076), -- Huế to Sài Gòn
('2025TT0030', '2021GA0004', '2019GA0003', 100),  -- Huế to Đà Nẵng
('2025TT0035', '2021GA0004', '2021GA0009', 750),  -- Huế to Hải Phòng
-- Hải Phòng (2021GA0009) to others
('2025TT0073', '2021GA0009', '2020GA0001', 100),  -- Hải Phòng to Hà Nội
('2025TT0074', '2021GA0009', '2018GA0002', 1826), -- Hải Phòng to Sài Gòn
('2025TT0075', '2021GA0009', '2019GA0003', 900),  -- Hải Phòng to Đà Nẵng
('2025TT0076', '2021GA0009', '2021GA0004', 750);  -- Hải Phòng to Huế
GO

-- Tạo bảng Tau
CREATE TABLE Tau (
    maTau VARCHAR(20) PRIMARY KEY,
    tenTau NVARCHAR(100) NOT NULL,
    loaiTau VARCHAR(20) CHECK (loaiTau IN ('SE', 'TN')),
    trangThai VARCHAR(20) CHECK (trangThai IN ('hoatDong', 'baoTri'))
);
GO

-- Chèn 15 tàu SE (Sài Gòn Express)
DECLARE @i INT = 1;
DECLARE @year INT;
DECLARE @maTau VARCHAR(20);
DECLARE @tenTau NVARCHAR(100);
DECLARE @trangThai VARCHAR(20);

WHILE @i <= 15
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

-- Chèn 15 tàu TN (Thống Nhất)
DECLARE @j INT = 1;
DECLARE @yearTN INT;
DECLARE @maTauTN VARCHAR(20);
DECLARE @tenTauTN NVARCHAR(100);
DECLARE @trangThaiTN VARCHAR(20);

WHILE @j <= 15
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
DECLARE cursorChuyenTau CURSOR LOCAL FOR
SELECT ct.maChuyenTau, ct.maTau, t.loaiTau
FROM ChuyenTau ct
JOIN Tau t ON ct.maTau = t.maTau;

OPEN cursorChuyenTau;
FETCH NEXT FROM cursorChuyenTau INTO @maChuyenTau, @maTau, @loaiTau;

WHILE @@FETCH_STATUS = 0
BEGIN
    PRINT 'Đang xử lý chuyến tàu: ' + @maChuyenTau;
    
    -- Cursor để duyệt qua các toa hoạt động của tàu
    DECLARE cursorToa CURSOR LOCAL FOR
    SELECT maToa, tenToa, loaiToa
    FROM Toa
    WHERE maTau = @maTau AND trangThai = 'hoatDong'
    ORDER BY maToa;

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
    DEALLOCATE cursorToa;

    FETCH NEXT FROM cursorChuyenTau INTO @maChuyenTau, @maTau, @loaiTau;
END;

CLOSE cursorChuyenTau;
DEALLOCATE cursorChuyenTau;

PRINT 'Hoàn thành tạo dữ liệu chỗ ngồi';
SET NOCOUNT OFF;
GO

CREATE TABLE KhuyenMai (
    maKhuyenMai VARCHAR(20) PRIMARY KEY,
    tenKhuyenMai NVARCHAR(100) NOT NULL,
    phanTramGiamGia FLOAT NOT NULL CHECK (phanTramGiamGia >= 0 AND phanTramGiamGia <= 100),
    ngayBatDau DATE NOT NULL,
    ngayKetThuc DATE NOT NULL,
    CHECK (ngayKetThuc >= ngayBatDau)
);
GO

INSERT INTO KhuyenMai (maKhuyenMai, tenKhuyenMai, phanTramGiamGia, ngayBatDau, ngayKetThuc)
VALUES 
    ('14042025KM0001', N'30/4 - 1/5 tại nhà ga', 10, '2025-04-30', '2025-05-01'),
    ('19042025KM0002', N'Tháng 4 may mắn (14/04 - 20/04)', 15, '2025-04-14', '2025-04-20'),
    ('01052025KM0003', N'Tri ân tháng 5 (01/05 - 31/05)', 20, '2025-05-01', '2025-05-31'),
    ('29052025KM0004', N'Quốc tế Thiếu nhi 1/6 tại nhà ga (29/5-3/6)', 25, '2025-05-29', '2025-06-03'),
    ('04062025KM0005', N'Đầu tháng 6 tại nhà ga', 5, '2025-06-04', '2025-06-14');
GO

CREATE TABLE HoaDon (
    maHoaDon VARCHAR(20) PRIMARY KEY,
    ngayTaoHoaDon DATE NOT NULL,
    phuongThucThanhToan VARCHAR(20) NOT NULL CHECK (phuongThucThanhToan IN ('tienMat', 'atm', 'internetBanking')),
    phanTramGiamGia DECIMAL(5,2) DEFAULT 0,
    tienKhachDua DECIMAL(18,2) NOT NULL,
    thanhTien DECIMAL(18,2) NOT NULL,
    tienTraLai DECIMAL(18,2) NOT NULL,
    maKhuyenMai VARCHAR(20),
    maNhanVien VARCHAR(20) NOT NULL,
    maKhachHang VARCHAR(20),
    FOREIGN KEY (maKhuyenMai) REFERENCES KhuyenMai(maKhuyenMai),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien),
    FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKhachHang)
);
go

CREATE TABLE Ve (
    maVe VARCHAR(50) PRIMARY KEY,
    ngayTaoVe DATE NOT NULL,
    trangThaiVe VARCHAR(20) NOT NULL CHECK (trangThaiVe IN ('hoatDong', 'daHuy_Hoan', 'daDoi')),
    tenKhachHang NVARCHAR(100) NOT NULL,
    CCCD_HoChieu VARCHAR(20),
    ngaySinh DATE NOT NULL,
    loaiKhachHang VARCHAR(20) NOT NULL CHECK (loaiKhachHang IN ('nguoiLon', 'treEm', 'sinhVien', 'nguoiCaoTuoi')),
    giaVe DECIMAL(18,2) NOT NULL,
    maHoaDon VARCHAR(20),
    maChoNgoi VARCHAR(30) NOT NULL,
    FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon),
    FOREIGN KEY (maChoNgoi) REFERENCES ChoNgoi(maChoNgoi)
);
go