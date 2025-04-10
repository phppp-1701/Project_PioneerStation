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

