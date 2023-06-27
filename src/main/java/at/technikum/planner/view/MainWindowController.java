package at.technikum.planner.view;

import at.technikum.planner.viewmodel.MainWindowViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainWindowController {

    private final MainWindowViewModel viewModel;

    public MainWindowController(MainWindowViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public MainWindowViewModel getViewModel() {
        return viewModel;
    }

    @FXML
    void initialize() {
    }

    public void onMenuFileQuitClicked(ActionEvent actionEvent) {
    }

    public void onMenuHelpAboutClicked(ActionEvent actionEvent) {
    }

}
