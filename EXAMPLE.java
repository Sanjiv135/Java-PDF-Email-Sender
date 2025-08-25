
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;

import java.io.File;
import java.util.Properties;

public class EXAMPLE {
    public static void main(String[] args) {
        String pdfPath = "HelloWorld.pdf";

        // 1. Create PDF
        createPDF(pdfPath);

        // 2. Send email with PDF attachment
        sendEmailWithAttachment("shivame2011@gmail.com", pdfPath);
    }

    public static void createPDF(String filePath) {
        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Paragraph paragraph = new Paragraph("Hello World")
                    .setFontColor(ColorConstants.RED)
                    .setFontSize(16);
            document.add(paragraph);

            document.close();
            System.out.println("PDF created successfully: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendEmailWithAttachment(String toEmail, String filePath) {
        final String fromEmail = "sanjivkanth135@gmail.com"; // Sender Gmail
        final String password = "dwdu mwbb ymwt opza";          // Use Gmail App Password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Hello World PDF");

            // Email body
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Hello, please find the attached PDF file.");

            // PDF attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(filePath));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully from " + fromEmail + " to " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
