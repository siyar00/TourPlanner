package at.technikum.planner.view;

import at.technikum.planner.viewmodel.SearchBarViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Data;

@Data
public class SearchBarController {

    @FXML
    Button searchButton;
    @FXML
    TextField searchTextField;
    private final SearchBarViewModel searchBarViewModel;

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
