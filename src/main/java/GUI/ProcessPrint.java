package GUI;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import DTO.Customer;
import DTO.Medicine;
import DTO.Prescription;

public class ProcessPrint {

	 public boolean exportToPDF(String fileName, Prescription prescription, Customer customer) {
	        try {
	            if (customer == null || prescription == null) {
	                ShowDialog.showAlert("Thông tin bệnh nhân hoặc đơn thuốc không hợp lệ!");
	                return false;
	            }

	            Document document = new Document();
	            PdfWriter.getInstance(document, new FileOutputStream(fileName));
	            document.open();
	            
	            InputStream fontStream = getClass().getResourceAsStream("/fonts/TIMES.TTF");
	            

		         // Tạo file tạm để ghi font ra
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
	            Font fontTitle = new Font(baseFont, 20, Font.BOLD);
	            Font fontNormal = new Font(baseFont, 12, Font.NORMAL);
	            Font boldFont = new Font(baseFont, 12, Font.BOLD);

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
	            Paragraph p1 = new Paragraph("PharmarDeskApp, ngày " + LocalDate.now(), fontNormal);
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
	 
	 

}
