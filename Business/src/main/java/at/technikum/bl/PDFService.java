package at.technikum.bl;

import java.io.FileNotFoundException;

public interface PDFService {
    void generateReport() throws FileNotFoundException;
    // void generateTourReport(Tour tour);
}
