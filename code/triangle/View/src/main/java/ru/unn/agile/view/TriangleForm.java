package ru.unn.agile.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.triangle.viewmodel.ViewModel;

public class TriangleForm {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField aX;
    @FXML
    private TextField bX;
    @FXML
    private TextField cX;
    @FXML
    private TextField aY;
    @FXML
    private TextField bY;
    @FXML
    private TextField cY;

    @FXML
    void initialize() {

        // Two-way binding hasn't supported by FXML yet, so place it in code-behind
        aX.textProperty().bindBidirectional(viewModel.aXProperty());
        bX.textProperty().bindBidirectional(viewModel.bXProperty());
        cX.textProperty().bindBidirectional(viewModel.cXProperty());
        aY.textProperty().bindBidirectional(viewModel.aYProperty());
        bY.textProperty().bindBidirectional(viewModel.bYProperty());
        cY.textProperty().bindBidirectional(viewModel.cYProperty());
    }
}