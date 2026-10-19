package GUI;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import java.util.List;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import DTO.Bill;
import DTO.Customer;
import DTO.Medicine;
import DTO.Prescription;
import DTO.ProductInBill;
import DAO.BillDetail_DAO;
import DAO.Cus_DAO;

public class ProcessPrint {

	 public boolean exportPrescriptionToPDF(String fileName, Prescription prescription, Customer customer) {
	        try {
	        	Rectangle customSize = new Rectangle(400, 500);

		        Document document = new Document(customSize);
	            PdfWriter.getInstance(document, new FileOutputStream(fileName));
	            document.open();
	            
	        	addLogoToPdf(document, "/img/MainLogo.png");
	        	
	            if (customer == null || prescription == null) {
	                ShowDialog.showAlert("Thông tin bệnh nhân hoặc đơn thuốc không hợp lệ!");
	                return false;
	            }

	            Font fontTitle = ProcessPrint.loadFont(20, Font.BOLD);
	            Font fontNormal = ProcessPrint.loadFont(12, Font.NORMAL);
	            Font boldFont = ProcessPrint.loadFont(12, Font.BOLD);
	            
        	    Paragraph text = new Paragraph("PharmaDeskApp", fontNormal);
        	    text.setAlignment(Paragraph.ALIGN_LEFT);
        	    document.add(text);
        	    document.add(new Paragraph(" "));

	            Paragraph title = new Paragraph("ĐƠN THUỐC", fontTitle);
	            title.setAlignment(Paragraph.ALIGN_CENTER);
	            document.add(title);
	            document.add(new Paragraph(" "));

	            document.add(new Paragraph("Họ tên: " + customer.getName_Cus(), fontNormal));
	            document.add(new Paragraph("Năm sinh: " + customer.getBirthYear(), fontNormal));
	            document.add(new Paragraph("Giới tính: " + customer.getGender(), fontNormal));
	            document.add(new Paragraph("Địa chỉ: " + customer.getAddress_Cus(), fontNormal));
	            document.add(new Paragraph("Mã BHYT: " + customer.getHealthInsuranceNumber(), fontNormal));
	            document.add(new Paragraph("Chẩn đoán: " + prescription.getDiagnosis(), fontNormal));

	            document.add(new Paragraph(" "));
	            PdfPTable table = new PdfPTable(3);  
	            table.setWidthPercentage(90);
	            table.setWidths(new float[]{5, 3, 3});

	            table.addCell(new Paragraph("Tên thuốc", fontNormal));
	            table.addCell(new Paragraph("Liều lượng", fontNormal));
	            table.addCell(new Paragraph("Ghi chú", fontNormal));

	            for (Medicine m : prescription.getMedicineList()) {
	                table.addCell(new Paragraph(m.getName(), fontNormal));
	                table.addCell(new Paragraph(m.getDosage(), fontNormal));
	                table.addCell(new Paragraph(m.getNote(), fontNormal));
	            }

	            document.add(table);
	            
	            document.add(new Paragraph(" "));
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'ngày' dd 'tháng' MM 'năm' yyyy");
	            String formattedDate = LocalDate.now().format(formatter);
	            Paragraph p1 = new Paragraph("PharmarDeskApp, " + formattedDate, fontNormal);
	            p1.setAlignment(Paragraph.ALIGN_RIGHT);
	            document.add(p1);
	            
	            Paragraph p2 = new Paragraph("Người kê đơn", fontNormal);
	            p2.setAlignment(Paragraph.ALIGN_RIGHT);
	            document.add(p2);
	            document.add(new Paragraph(" "));
	            Paragraph p3 = new Paragraph(GetNameLogin.getLoggedInFullName(), boldFont);
	            p3.setAlignment(Paragraph.ALIGN_RIGHT);
	            document.add(p3);
	            
	            document.close();
	            return true; 
	        } catch (Exception e) {
	            e.printStackTrace();
	            ShowDialog.showAlert("Lỗi khi xuất PDF: " + e.getMessage());
	            return false; 
	        }
	    }
	 
	 public boolean exportBillPDF(String fileName, Bill bill) {
		    try {
		    	Rectangle customSize = new Rectangle(350, 500);

		        Document document = new Document(customSize);
		        PdfWriter.getInstance(document, new FileOutputStream(fileName));
		        document.open();
		        
		        addLogoToPdf(document, "/img/MainLogo.png");

		        
		        Font fontTitle = ProcessPrint.loadFont(20, Font.BOLD);
	            Font fontNormal = ProcessPrint.loadFont(12, Font.NORMAL);

		        Paragraph title = new Paragraph("HÓA ĐƠN", fontTitle);
		        title.setAlignment(Paragraph.ALIGN_CENTER);
		        document.add(title);
		        document.add(new Paragraph(" "));

		        Cus_DAO cusDao = new Cus_DAO();
		        String customerName = cusDao.getCustomerById(bill.getID_Cus()).getName_Cus();
		        
		        document.add(new Paragraph("Mã hóa đơn: " + bill.getID_Bill(), fontNormal));
		        document.add(new Paragraph("Khách hàng: " + customerName, fontNormal));
		        document.add(new Paragraph("Ngày: " + bill.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
		        document.add(new Paragraph(" "));

		        PdfPTable table = new PdfPTable(3);
		        table.setWidthPercentage(95);
		        table.setWidths(new float[]{5, 2, 2});

		        table.addCell(new Paragraph("Tên hàng hóa", fontNormal));
		        table.addCell(new Paragraph("Đơn giá", fontNormal));
		        table.addCell(new Paragraph("Số lượng", fontNormal));
		        
		        BillDetail_DAO billDetailDao = new BillDetail_DAO();
		        List<ProductInBill> products = billDetailDao.getProductsByBillId(bill.getID_Bill());

		        for (ProductInBill p : products) {
		            table.addCell(new Paragraph(p.getProductName(), fontNormal));
		            table.addCell(new Paragraph(p.getUnitPrice() + " VNĐ", fontNormal));
		            table.addCell(new Paragraph(String.valueOf(p.getQuantity()), fontNormal));
		        }


		        document.add(table);
		        document.add(new Paragraph(" "));
		        
		        Paragraph tax = new Paragraph("Thuế VAT: 5%", fontNormal);
		        tax.setAlignment(Paragraph.ALIGN_RIGHT);
		        document.add(tax);

		        Paragraph total = new Paragraph("Tổng cộng: " + bill.getTotalAmount() + " VNĐ", fontNormal);
		        total.setAlignment(Paragraph.ALIGN_RIGHT);
		        document.add(total);
		        
		        document.add(new Paragraph(" "));
		        Paragraph thanks = new Paragraph("PharmaDeskApp xin cảm ơn quý khách đã mua hàng!", fontNormal);
		        thanks.setAlignment(Paragraph.ALIGN_CENTER);
		        document.add(thanks);


		        document.close();
		        return true;
		    } catch (Exception e) {
		        e.printStackTrace();
		        ShowDialog.showAlert("Lỗi khi xuất hóa đơn: " + e.getMessage());
		        return false;
		    }
		}

	 public void openPDF(String fileName) {
	        try {
	            File pdfFile = new File(fileName);
	            if (pdfFile.exists() && Desktop.isDesktopSupported()) {
	                Desktop.getDesktop().open(pdfFile);
	            } else {
	                ShowDialog.showAlert("Không tìm thấy file PDF hoặc không hỗ trợ mở file.");
	            }
	        } catch (IOException e) {
	            ShowDialog.showAlert("Lỗi khi mở file PDF: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	 
	 public static void addLogoToPdf(Document document, String resourcePath) {
	        try (InputStream logoStream = ProcessPrint.class.getResourceAsStream(resourcePath)) {
	            if (logoStream != null) {
	                File tempLogoFile = File.createTempFile("Mainlogo", ".png");
	                tempLogoFile.deleteOnExit();

	                try (FileOutputStream fos = new FileOutputStream(tempLogoFile)) {
	                    byte[] buffer = new byte[1024];
	                    int bytesRead;
	                    while ((bytesRead = logoStream.read(buffer)) != -1) {
	                        fos.write(buffer, 0, bytesRead);
	                    }
	                }

	                Image logo = Image.getInstance(tempLogoFile.getAbsolutePath());
	                logo.scaleToFit(40, 40);
	                logo.setAlignment(Image.ALIGN_LEFT);
	                document.add(logo);
	            } else {
	                System.err.println("Không tìm thấy logo tại: " + resourcePath);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	   public static Font loadFont(float size, int style) {
	        try (InputStream fontStream = ProcessPrint.class.getResourceAsStream("/fonts/TIMES.TTF")) {
	            File tempFontFile = File.createTempFile("tempFont", ".ttf");
	            tempFontFile.deleteOnExit();

	            try (FileOutputStream out = new FileOutputStream(tempFontFile)) {
	                byte[] buffer = new byte[1024];
	                int bytesRead;
	                while ((bytesRead = fontStream.read(buffer)) != -1) {
	                    out.write(buffer, 0, bytesRead);
	                }
	            }

	            BaseFont baseFont = BaseFont.createFont(tempFontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	            return new Font(baseFont, size, style);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new Font(); // fallback
	        }
	    }

}
