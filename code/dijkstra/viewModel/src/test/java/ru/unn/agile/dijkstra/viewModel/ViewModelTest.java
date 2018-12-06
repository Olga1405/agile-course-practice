package ru.unn.agile.dijkstra.viewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.dijkstra.model.Vertex;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class ViewModelTest {
    private ViewModel viewModel;

    @Before
    public void init() {
        viewModel = new ViewModel(new FakeLogger());
    }

    @After
    public void destroy() {
        viewModel = null;
    }

    protected void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public  String getResourceAsString(final String path) {
        try {
            URL url = getClass().getResource(path);

            return new String(Files.readAllBytes(Paths.get(url.toURI())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void validateSimpleMatrix() {
        viewModel.matrixProperty().setValue(getResourceAsString("SimpleGraph.json"));

        viewModel.graphInit();

        assertEquals("Success", viewModel.getStatus());
    }

    @Test
    public void validateNonNumericMatrix() {
        viewModel.matrixProperty().setValue("qq");

        viewModel.graphInit();

        assertEquals("Invalid Input Json", viewModel.getStatus());
    }

    @Test
    public void validateEmptyNumericMatrix() {

        viewModel.graphInit();

        assertEquals("Invalid Input Json", viewModel.getStatus());
    }

    @Test
    public void validateGraphWithNegativeEdgesMatrix() {
        viewModel.matrixProperty().setValue(getResourceAsString("JsonWithNegativeEdge.json"));

        viewModel.graphInit();

        assertEquals("Matrix can't have negative weight", viewModel.getStatus());
    }

    @Test
    public void validateGraphWithoutEdges() {
        viewModel.matrixProperty().setValue(getResourceAsString("JsonWithoutEdges.json"));

        viewModel.graphInit();

        assertEquals("Graph don't have any edges", viewModel.getStatus());
    }

    @Test
    public void validateGraphWithoutWeightInEdge() {
        viewModel.matrixProperty().setValue(getResourceAsString(
                "JsonWithoutMandatoryParameterInEdge.json"));

        viewModel.graphInit();

        assertEquals("Vertex can't be null", viewModel.getStatus());
    }

    @Test
    public void validateStartVertex() {
        viewModel.matrixProperty().setValue(getResourceAsString("SimpleGraph.json"));
        viewModel.startVertexProperty().setValue("1");

        Vertex vertex = viewModel.parseVertex(viewModel.startVertexProperty());

        assertEquals(1, vertex.getId());
    }

    @Test
    public void validateInvalidStartVertex() {
        viewModel.matrixProperty().setValue(getResourceAsString("SimpleGraph.json"));
        viewModel.startVertexProperty().setValue("asd");

        viewModel.parseVertex(viewModel.startVertexProperty());

        assertEquals("Invalid Input Value: asd", viewModel.getStatus());
    }

    @Test
    public void canCalculateGraph() {
        viewModel.matrixProperty().setValue(getResourceAsString("SimpleGraph.json"));
        viewModel.startVertexProperty().setValue("1");
        viewModel.finishVertexProperty().setValue("3");

        viewModel.calculate();

        assertEquals("15", viewModel.getResult());
    }

    @Test
    public void canCalculateGraphByVertexOutOfGraph() {
        viewModel.matrixProperty().setValue(getResourceAsString("SimpleGraph.json"));
        viewModel.startVertexProperty().setValue("1");
        viewModel.finishVertexProperty().setValue("6");

        viewModel.calculate();

        assertEquals("Vertex id = 6 not contains in graph", viewModel.getStatus());
    }
}
