package at.technikum.planner.viewmodel;

import at.technikum.planner.model.Tour;
import at.technikum.planner.model.TourFile;
import at.technikum.planner.model.TourLog;
import at.technikum.planner.transformer.TourFileToTourTransformer;
import at.technikum.planner.transformer.TourToTourFileTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Data
public class MainWindowViewModel {
    Logger LOGGER = Logger.getLogger(MainWindowViewModel.class.getName());
    private final TourListViewModel tourListViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final TourLogsViewModel tourLogsViewModel;
    private final RouteMapViewModel routeMapViewModel;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public MainWindowViewModel(TourListViewModel tourListViewModel, SearchBarViewModel searchBarViewModel, TourLogsViewModel tourLogsViewModel, RouteMapViewModel routeMapViewModel) {
        this.tourListViewModel = tourListViewModel;
        this.searchBarViewModel = searchBarViewModel;
        this.tourLogsViewModel = tourLogsViewModel;
        this.routeMapViewModel = routeMapViewModel;
        
        this.tourListViewModel.addSelectionChangedListener(this::selectTour);
        this.tourLogsViewModel.addListener(this::added);
    }

    private void added(List<TourLog> tourLog) {
        routeMapViewModel.logAdded(tourLog);
    }

    private void selectTour(Tour selectedTour) {
        routeMapViewModel.setTour(selectedTour);
        tourLogsViewModel.setTourLog(selectedTour);
    }

    public List<String> importTour(File file) {
        try {
            var tours = objectMapper.readValue(file, TourFile[].class);
            return tourListViewModel.setTours(Arrays.stream(tours).map(tourFile -> new TourFileToTourTransformer().apply(tourFile)).collect(Collectors.toList()));
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void exportTour(File directory) {
        System.out.println(directory.getAbsolutePath());
        try {
            var jsonFile = objectMapper.writeValueAsBytes(tourListViewModel.getObservableTours().stream().map(tour -> new TourToTourFileTransformer().apply(tour)).toList());
            Path path = Paths.get(directory.getAbsolutePath() + File.separator + "tour_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy!!HH+mm+ss_")) + UUID.randomUUID() + ".json");
            Files.write(path, jsonFile);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }
}
