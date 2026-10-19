💊 PharmaDeskApp - Smart Pharmacy Management & Electronic Prescription System

PharmaDeskApp là giải pháp phần mềm Desktop hiện đại giúp tối ưu hóa toàn bộ quy trình vận hành nhà thuốc, từ quản lý kho hàng, theo dõi hạn sử dụng, bán hàng đến kê đơn thuốc điện tử thông minh. Sản phẩm giúp tự động hóa nghiệp vụ, giảm thiểu rủi ro sai sót trong kê đơn thủ công và nâng cao trải nghiệm chăm sóc sức khỏe khách hàng.

🌟 Tính Năng Cốt Lõi

🏥 1. Kê Đơn Thuốc Điện Tử & Gợi Ý Thông Minh

Tự động gợi ý thuốc: Hệ thống phân tích triệu chứng lâm sàng để đề xuất danh mục thuốc, liều dùng và hướng dẫn chi tiết.

Tự động điền dữ liệu: Truy vấn nhanh hồ sơ khách hàng cũ qua Số điện thoại/BHYT.

Xuất & In đơn thuốc: Hỗ trợ render đơn thuốc chuẩn định dạng PDF và gửi trực tiếp tới máy in.

📦 2. Quản Lý Kho Hàng & Cảnh Báo Thông Minh

Cảnh báo tồn kho & Hạn sử dụng:

🟡 Cảnh báo gần hết hạn: Tự động highlight mã hàng có HSD < 30 ngày.

🔴 Cảnh báo hết hàng: Tự động highlight mã hàng có số lượng tồn kho < 10 unit.

Trừ kho tự động: Cập nhật số lượng tồn kho theo thời gian thực (Real-time) ngay khi hóa đơn/đơn thuốc được khởi tạo.

🧾 3. Bán Hàng & Quản Lý Hóa Đơn

Tính toán tự động: Tự động tính tổng tiền, thuế VAT và chiết khấu.

Lưu trữ & Truy xuất lịch sử: Quản lý danh sách hóa đơn chi tiết, hỗ trợ xuất bản in PDF hóa đơn tài chính.

👥 4. Quản Lý Khách Hàng & Đối Tác

Hồ sơ khách hàng: Lưu trữ lịch sử khám/mua thuốc, mã BHYT và thông tin liên hệ.

Quản lý Nhà cung cấp: Giao diện Card UI trực quan hỗ trợ quản lý danh mục đối tác và lịch sử nhập hàng.

👔 5. Quản Lý Nhân Sự & Chấm Công

Quản lý hồ sơ dược sĩ/nhân viên bán hàng.

Tích hợp hệ thống chấm công tự động theo ca/ngày làm việc.

🏗️ Kiến Trúc Hệ Thống & Công Nghệ

Dự án được xây dựng theo mô hình Phân lớp 3 tầng (3-Tier Architecture) kết hợp pattern MVC đảm bảo tính mở rộng và dễ bảo trì:

Language: Java 22

UI Framework: JavaFX 22 + FXML (Styled with Custom CSS)

Database: Microsoft SQL Server / MySQL

Data Access: JDBC (Java Database Connectivity)

PDF Engine: iText PDF Library

Cấu trúc thư mục mã nguồn:

PharmaDeskApp/
└── src/
    └── main/
        ├── java/
        │   ├── DAO/     # Data Access Objects (Thao tác CSDL)
        │   ├── BUS/     # Business Logic Layer (Xử lý nghiệp vụ)
        │   ├── DTO/     # Data Transfer Objects (Mô hình dữ liệu)
        │   └── GUI/     # Controllers & UI Event Handlers
        └── resources/
            ├── css/     # Stylesheet & Themes
            ├── fxml/    # Layouts & UI Components
            ├── img/     # Icons & Assets
            └── font/    # Custom Typography


🚀 Hướng Dẫn Cài Đặt & Khởi Chạy

Yêu Cầu Hệ Thống

Java Development Kit (JDK): Version 22 trở lên

Database Engine: Microsoft SQL Server (hoặc MySQL)

IDE: IntelliJ IDEA / Eclipse (Đã cài JavaFX Plugin)

Các Bước Cài Đặt

Clone mã nguồn:

git clone https://github.com/JayNguyen0906/Do-an-co-so-1.git
cd Do-an-co-so-1


Cấu hình Cơ sở Dữ liệu:

Thực thi file script SQL (đi kèm trong thư mục /database) trên SQL Server.

Cập nhật chuỗi kết nối trong tệp src/main/java/DAO/DB_CONECTION.java:

String url = "jdbc:sqlserver://localhost:1433;databaseName=PharmaDeskDB;encrypt=false;";
String user = "YOUR_USERNAME";
String pass = "YOUR_PASSWORD";


Build & Chạy ứng dụng:

Mở dự án trong IDE và tiến hành Reload Maven/Gradle dependencies.

Khởi chạy file chính: src/main/java/GUI/Login_Controller.java.

📄 License

Dự án được phát hành dưới bản quyền MIT License
