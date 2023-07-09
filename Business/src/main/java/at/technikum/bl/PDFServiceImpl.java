package at.technikum.bl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class PDFServiceImpl implements PDFService {

    public static final String dir = "at.technikum.planner.PDF/";

    @Override
    public void generateReport() throws FileNotFoundException {
        try {
            PdfWriter writer = new PdfWriter(dir + "Tour_Report_" + LocalDate.now() + ".pdf");
            PdfDocument pdf = new PdfDocument(writer);
            try (Document document = new Document(pdf)) {
                ImageData imageData = ImageDataFactory.create("resources/at.technikum.planner/images/dora.png");
                document.add(new Image(imageData));
                document.add(generateTourHeader());
                document.add(generateTableHeader("Overview of Tours"));
                Table table = setupTable();
                document.add(table);
                Table logTable = setupLogTable();
                document.add(logTable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Table setupLogTable() {
        Table table = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
        table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);
        table.addHeaderCell(getHeaderCell("Comment"));
        table.addHeaderCell(getHeaderCell("Date"));
        table.addHeaderCell(getHeaderCell("Difficulty"));
        table.addHeaderCell(getHeaderCell("Rating"));
        table.addHeaderCell(getHeaderCell("Time"));
        return table;
    }

    private Table setupTable() {
        Table table = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth().setPaddingTop(20);
        table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);
        table.addHeaderCell(getHeaderCell("Name"));
        table.addHeaderCell(getHeaderCell("Start"));
        table.addHeaderCell(getHeaderCell("Goal"));
        table.addHeaderCell(getHeaderCell("Distance"));
        table.addHeaderCell(getHeaderCell("Duration"));
        table.addHeaderCell(getHeaderCell("Description"));
        return table;
    }

    private static Cell getHeaderCell(String s) {
        return new Cell().add(new Paragraph(s)).setBold().setBackgroundColor(ColorConstants.GRAY);
    }

    private Paragraph generateTourHeader() throws IOException {
        return new Paragraph("Tours Report from " + LocalDate.now())
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(26)
                .setBold()
                .setFontColor(ColorConstants.LIGHT_GRAY);
    }

    private Paragraph generateTableHeader(String title) throws IOException {
        return new Paragraph(title)
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                .setFontSize(18)
                .setBold();
    }
}
