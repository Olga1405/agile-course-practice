package ru.unn.agile.dijkstra.infrastructure;


import ru.unn.agile.dijkstra.viewModel.ViewModel;
import ru.unn.agile.dijkstra.viewModel.ViewModelTest;

public class ViewModelWithTxtLogger extends ViewModelTest {
    public void setUp() {
        TxtLogger realLogger =
                new TxtLogger("./ViewModelWithTxtLoggerTest.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}

