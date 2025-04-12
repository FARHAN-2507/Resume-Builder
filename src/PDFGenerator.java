package src;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;

public class PDFGenerator {
    public static void generatePDF(ResumeData data, String filePath) throws Exception {
        Document doc = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(doc, new FileOutputStream(filePath));
        doc.open();

        Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.DARK_GRAY);
        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);

        // === NAME & CONTACT ===
        Paragraph name = new Paragraph(data.fullName + "\n", titleFont);
        name.setAlignment(Element.ALIGN_CENTER);
        doc.add(name);

        Paragraph contact = new Paragraph(data.email + " | " + data.phone + " | " + data.address + "\n", contentFont);
        contact.setAlignment(Element.ALIGN_CENTER);
        doc.add(contact);
        doc.add(Chunk.NEWLINE);

        // === EDUCATION ===
        doc.add(new Paragraph("Education", headingFont));
        for (Education edu : data.educationList) {
            doc.add(new Paragraph(edu.degree + " - " + edu.university, labelFont));
            doc.add(new Paragraph("Year: " + edu.year + " | CGPA: " + edu.CGPA + "\n", contentFont));
        }

        // === EXPERIENCE ===
        if (!data.experienceList.isEmpty()) {
            doc.add(new Paragraph("Experience", headingFont));
            for (Experience exp : data.experienceList) {
                doc.add(new Paragraph(exp.role + " at " + exp.company, labelFont));
                doc.add(new Paragraph(exp.duration, contentFont));
                doc.add(new Paragraph(exp.description + "\n", contentFont));
            }
        }

        // === SKILLS ===
        doc.add(new Paragraph("Skills", headingFont));
        doc.add(new Paragraph(String.join(", ", data.skills) + "\n", contentFont));

        // === PROJECTS ===
        if (!data.projects.isEmpty()) {
            doc.add(new Paragraph("Projects", headingFont));
            for (Project proj : data.projects) {
                doc.add(new Paragraph(proj.title, labelFont));
                doc.add(new Paragraph(proj.description, contentFont));
                if (!proj.link.isEmpty()) {
                    doc.add(new Paragraph("Link: " + proj.link + "\n", contentFont));
                }
            }
        }

        // === CERTIFICATIONS ===
        if (!data.certifications.isEmpty()) {
            doc.add(new Paragraph("Certifications", headingFont));
            for (String cert : data.certifications) {
                doc.add(new Paragraph("• " + cert, contentFont));
            }
        }

        doc.close();
    }
}
