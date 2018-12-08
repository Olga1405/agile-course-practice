package ru.unn.agile.dijkstra.viewModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.dijkstra.model.Edge;
import ru.unn.agile.dijkstra.model.Graph;
import ru.unn.agile.dijkstra.model.Vertex;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ViewModel {

    private final StringProperty matrix = new SimpleStringProperty();
    private final StringProperty startVertex = new SimpleStringProperty();
    private final StringProperty finishVertex = new SimpleStringProperty();

    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty log = new SimpleStringProperty();

    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    private ILogger logger;

    private final ObjectMapper mapper = new ObjectMapper();

    public String getLog() {
        return log.get();
    }

    public List<String> getLoggerList() {
        return logger.getLog();
    }

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger can't be empthy");
        }
        this.logger = logger;
    }

    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        result.set("");
        matrix.set("");
        startVertex.set("");
        finishVertex.set("");
        log.set("");
        status.set("Click Calculate Button after populating data");

        initListeners();
    }

    public void calculate() {
        try {
            Graph graph = graphInit();
            Vertex startVertex = parseVertex(startVertexProperty());
            Vertex finishVertex = parseVertex(finishVertexProperty());

            int weight = graph.dijkstra(startVertex, finishVertex);

            result.set(String.valueOf(weight));
            status.set(new Status(StatusType.SUCCESS).toString());
            StringBuilder message = new StringBuilder(LogMessages.CALCULATED_DISTANCE);
            message.append("Start vertex = ").append(startVertex.getId())
                    .append("; Finish vertex = ").append(finishVertex.getId())
                    .append("; Result = ").append(weight)
                    .append(".");
            logger.log(message.toString());
            updateLogs();

        } catch (IllegalArgumentException e) {
            status.set(new Status(StatusType.BAD_REQUEST, e.getMessage()).toString());
        }
    }

    private void initListeners() {
        final List<StringProperty> fields = new ArrayList<StringProperty>() {
            {
                add(matrix);
                add(startVertex);
                add(finishVertex);
            }
        };

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public Vertex parseVertex(final StringProperty value) {
        try {
            StringBuilder message = new StringBuilder(LogMessages.VALUE_CHANGE);
            message.append("Change value = ").append(value.getValue()).append(".");
            logger.log(message.toString());
            updateLogs();
            return new Vertex(Integer.parseInt(value.getValue()));
        } catch (NumberFormatException e) {
            status.setValue(new Status(StatusType.BAD_REQUEST,
                    String.format("Invalid Input Value: %s", value.getValue()))
                    .toString());
        }
        return null;
    }

    public Graph graphInit() {
        try {
            List<Edge> edges = mapper.readValue(matrix.get(),
                    mapper.getTypeFactory().constructCollectionType(List.class, Edge.class));

            Graph graph = new Graph(edges);
            status.set(new Status(StatusType.SUCCESS).toString());


            StringBuilder message = new StringBuilder(LogMessages.VALUE_CHANGE);
            message.append("Graph = ").append(matrix.get()).append(".");
            logger.log(message.toString());
            updateLogs();

            return graph;

        } catch (IOException e) {
            status.set(new Status(StatusType.BAD_REQUEST, "Invalid Input Json").toString());
        } catch (IllegalArgumentException e) {
            status.set(new Status(StatusType.BAD_REQUEST, e.getMessage()).toString());
        }
        return null;
    }

    public StringProperty matrixProperty() {
        return matrix;
    }

    public final String getMatrixProperty() {
        return matrix.get();
    }

    public StringProperty startVertexProperty() {
        return startVertex;
    }

    public final String getStartVertexProperty() {
        return startVertex.get();
    }

    public StringProperty finishVertexProperty() {
        return finishVertex;
    }

    public final String getFinishVertexProperty() {
        return finishVertex.get();
    }

    public final String getResult() {
        return result.get();
    }

    public final String getStatus() {
        return status.get();
    }

    public StringProperty logProperty() {
        return log;
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String record = new String("");
        for (String log : fullLog) {
            record += log + "\n";
        }
        log.set(record);
    }

    public static final class LogMessages {
        public static final String CALCULATED_DISTANCE = "Button click: ";
        public static final String VALUE_CHANGE = "Input changed: ";
    }

    private class ValueChangeListener implements ChangeListener<Object> {
        @Override
        public void changed(final ObservableValue<?> observable,
                            final Object oldValue, final Object newValue) {
            status.set(getStatus());

            StringBuilder message = new StringBuilder(LogMessages.VALUE_CHANGE);
            message.append("Input arguments are: [")
                    .append(startVertex.get()).append("; ")
                    .append(finishVertex.get()).append("; ")
                    .append(matrix.get()).append("] ");
            logger.log(message.toString());
            updateLogs();

        }
    }
}
