package ru.unn.agile.dijkstra.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.unn.agile.dijkstra.infrastructure.TxtLogger;
import ru.unn.agile.dijkstra.viewModel.ViewModel;

public class DijkstraView {

    @FXML
    private Button calculateAction;

    @FXML
    private TextField finishVertexInput;

    @FXML
    private TextField startVertexInput;

    @FXML
    private TextArea textAreaMatrix;

    @FXML
    private ViewModel viewModel;

    @FXML
    void initialize() {
        viewModel.setLogger(new TxtLogger("./TxtLoggerTests.log"));

        textAreaMatrix.textProperty().bindBidirectional(viewModel.matrixProperty());
        startVertexInput.textProperty().bindBidirectional(viewModel.startVertexProperty());
        finishVertexInput.textProperty().bindBidirectional(viewModel.finishVertexProperty());

        calculateAction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }
}
