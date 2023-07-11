package at.technikum.planner.view;

import at.technikum.planner.viewmodel.SearchBarViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Data;

import java.util.logging.Logger;

@Data
public class SearchBarController {

    @FXML
    private Button searchButton;
    @FXML
    private TextField searchTextField;
    private final SearchBarViewModel searchBarViewModel;
    Logger LOGGER = Logger.getLogger(SearchBarController.class.getName());

    public SearchBarController(SearchBarViewModel searchBarViewModel) {
        this.searchBarViewModel = searchBarViewModel;
    }

    @FXML
    void initialize() {
    }

    public void onSearchButton() {
        searchBarViewModel.search(searchTextField);
    }

}
