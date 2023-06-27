package at.technikum.planner.view;

import at.technikum.planner.viewmodel.SearchBarViewModel;
import javafx.fxml.FXML;

public class SearchBarController {

    private final SearchBarViewModel searchBarViewModel;

    public SearchBarController(SearchBarViewModel searchBarViewModel) {
        this.searchBarViewModel = searchBarViewModel;
    }

    public SearchBarViewModel getSearchBarViewModel() {
        return searchBarViewModel;
    }

    public void onSearchButton() {
    }

    @FXML void initialize() {
    }
}
