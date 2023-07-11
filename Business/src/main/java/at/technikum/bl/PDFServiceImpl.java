package at.technikum.bl;

import at.technikum.bl.model.TourBl;
import at.technikum.bl.model.TourLogBl;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PDFServiceImpl implements PDFService {
    Logger LOGGER = Logger.getLogger(PDFServiceImpl.class.getName());

    public static final String dir = System.getProperty("user.dir") + File.separator + "PDF" + File.separator;
    private final TourBl tourBl;
    private final List<TourBl> tourBlList;
    private final int FONTSIZE = 10;

    public PDFServiceImpl(TourBl tourBl) {
        this.tourBl = tourBl;
        this.tourBlList = new ArrayList<>();
    }

    public PDFServiceImpl(List<TourBl> tourBlList) {
        this.tourBlList = tourBlList;
        this.tourBl = null;
    }

    @Override
    public boolean generateSummarizeReport() {
        try {
            PdfWriter writer = new PdfWriter(dir + "Tour_Summarize_Report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy'T'HH-mm-ss")) + ".pdf");
            PdfDocument pdf = new PdfDocument(writer);
            try (Document document = new Document(pdf)) {
                ImageData imageData = ImageDataFactory.create("Application/src/main/resources/at/technikum/planner/images/dora.png");
                document.add(new Image(imageData, 0, 0, 50));
                document.add(generateTourHeader());
                document.add(generateTableHeader("Summarize Tours and Logs"));
                document.add(setupSummarizeTable());
                return true;
            }
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            return false;
        }
    }

    private Table setupSummarizeTable() {
        Table table = new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth();
        table.setFontSize(FONTSIZE).setBackgroundColor(ColorConstants.WHITE);
        table.addHeaderCell(getHeaderCell("Name"));
        table.addHeaderCell(getHeaderCell("Start"));
        table.addHeaderCell(getHeaderCell("End"));
        table.addHeaderCell(getHeaderCell("Distance"));
        table.addHeaderCell(getHeaderCell("Transportation"));
        table.addHeaderCell(getHeaderCell("Avg. Time"));
        table.addHeaderCell(getHeaderCell("Avg. Difficulty"));
        table.addHeaderCell(getHeaderCell("Avg. Rating"));
        for (TourBl tourBl : tourBlList) {
            table.addCell(tourBl.getName());
            table.addCell(tourBl.getStartAddress());
            table.addCell(tourBl.getEndAddress());
            table.addCell(tourBl.getDistance());
            table.addCell(tourBl.getTransportation());

            List<LocalTime> timeList = new ArrayList<>();
            for (TourLogBl tourLogBl : tourBl.getTourLogBl()) {
                String[] parts = tourLogBl.getDuration().split("h|min");
                timeList.add(LocalTime.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }
            long avgTime = (long) timeList.stream().mapToLong(LocalTime::toSecondOfDay).average().orElse(0.0);

            table.addCell(LocalTime.ofSecondOfDay(avgTime).toString());
            table.addCell(Float.toString((float) tourBl.getTourLogBl().stream().mapToDouble(TourLogBl::getDifficulty).average().orElse(0.0)));
            table.addCell(String.format("%.2f", (float) tourBl.getTourLogBl().stream().mapToDouble(TourLogBl::getRating).average().orElse(0.0)));
        }
        return table;
    }

    @Override
    public boolean generateReport() {
        try {
            PdfWriter writer = new PdfWriter(dir + "Tour_Report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy!!HH-mm-ss")) + ".pdf");
            PdfDocument pdf = new PdfDocument(writer);
            try (Document document = new Document(pdf)) {
                ImageData imageData = ImageDataFactory.create("Application/src/main/resources/at/technikum/planner/images/dora.png");
                document.add(new Image(imageData, 0, 0, 50));
                document.add(generateTourHeader());
                document.add(generateTableHeader("Overview of Tours and Logs"));
                document.add(setupTable());
                document.add(setupLogTable());
                return true;
            }
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            return false;
        }
    }

    private Table setupLogTable() {
        Table table = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
        table.setFontSize(FONTSIZE).setBackgroundColor(ColorConstants.WHITE);
        table.addHeaderCell(getHeaderCell("Comment"));
        table.addHeaderCell(getHeaderCell("Date"));
        table.addHeaderCell(getHeaderCell("Difficulty"));
        table.addHeaderCell(getHeaderCell("Rating"));
        table.addHeaderCell(getHeaderCell("Time"));
        if (tourBl != null && tourBl.getTourLogBl() != null && !tourBl.getTourLogBl().isEmpty()) {
            tourBl.getTourLogBl().forEach(tourLogBl -> {
                table.addCell(tourLogBl.getComment());
                table.addCell(tourLogBl.getDate());
                table.addCell(String.valueOf(tourLogBl.getDifficulty()));
                table.addCell(String.valueOf(tourLogBl.getRating()));
                table.addCell(tourLogBl.getDuration());
            });
        }
        return table;
    }

    private Table setupTable() {
        Table table = new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth().setPaddingTop(20);
        table.setFontSize(FONTSIZE).setBackgroundColor(ColorConstants.WHITE);
        table.addHeaderCell(getHeaderCell("Name"));
        table.addHeaderCell(getHeaderCell("Start"));
        table.addHeaderCell(getHeaderCell("End"));
        table.addHeaderCell(getHeaderCell("Distance"));
        table.addHeaderCell(getHeaderCell("Time"));
        table.addHeaderCell(getHeaderCell("Description"));
        table.addHeaderCell(getHeaderCell("Transportation"));
        table.addHeaderCell(getHeaderCell("Route"));
        if (tourBl != null) {
            table.addCell(tourBl.getName() == null ? "" : tourBl.getName());
            table.addCell(tourBl.getStartAddress() == null ? "" : tourBl.getStartAddress());
            table.addCell(tourBl.getEndAddress() == null ? "" : tourBl.getEndAddress());
            table.addCell(tourBl.getDistance() == null ? "" : tourBl.getDistance());
            table.addCell(tourBl.getTime() == null ? "" : tourBl.getTime());
            table.addCell(tourBl.getTourDescription() == null ? "" : tourBl.getTourDescription());
            table.addCell(tourBl.getTransportation() == null ? "" : tourBl.getTransportation());
            if (tourBl.getImageBytes() != null)
                table.addCell(new Image(new PdfImageXObject(ImageDataFactory.create(tourBl.getImageBytes())), 70));
        }
        return table;
    }

    private static Cell getHeaderCell(String s) {
        return new Cell().add(new Paragraph(s)).setBold().setBackgroundColor(ColorConstants.GRAY);
    }

    private Paragraph generateTourHeader() throws IOException {
        return new Paragraph("Tours Report from " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(20).setBold().setFontColor(ColorConstants.LIGHT_GRAY);
    }

    private Paragraph generateTableHeader(String title) throws IOException {
        return new Paragraph(title).setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN)).setFontSize(18).setBold();
    }
}
