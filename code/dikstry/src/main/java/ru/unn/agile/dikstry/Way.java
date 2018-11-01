package ru.unn.agile.dikstry;

import java.util.List;

public class Way {
    private static final int INF = Integer.MAX_VALUE;
    private List<Vertex> vertexes;
    private int weight;

    public Way(final List<Vertex> vertexes, final int weight) {
        this.vertexes = vertexes;
        this.weight = weight;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(final int weight) {
        this.weight = weight;
    }

    private void addVertex(final Vertex vertex) {
        vertexes.add(vertex);
    }

    public void addWeight(final int weight) {
        if (this.weight == INF) {
            this.weight = weight;
        } else {
            this.weight += weight;
        }
    }

    public void upDateWay(final Edge edge, final Way wayToRightVertex) {
        int actualWeight = weight + edge.getWeight();
        if (actualWeight < wayToRightVertex.getWeight()) {
            wayToRightVertex.setWeight(actualWeight);
            wayToRightVertex.addVertex(edge.getVertexLeft());
        }
    }

}
